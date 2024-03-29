/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.common.core.utils;

import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Type;
import org.springframework.cglib.core.*;
import org.springframework.cglib.core.ReflectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

/**
 * spring cglib 魔改
 *
 * <p>
 *     1. 支持链式 bean
 *     2. 自定义的 BeanCopier 解决 spring boot 和 cglib ClassLoader classLoader 不一致的问题
 * </p>
 */
public abstract class BaseBeanCopier {
	private static final BeanCopierKey KEY_FACTORY = (BeanCopierKey) KeyFactory.create(BeanCopierKey.class);
	private static final Type CONVERTER = TypeUtils.parseType("org.springframework.cglib.core.Converter");
	private static final Type BEAN_COPIER = TypeUtils.parseType(BaseBeanCopier.class.getName());
	private static final Signature COPY = new Signature("copy", Type.VOID_TYPE, new Type[]{Constants.TYPE_OBJECT, Constants.TYPE_OBJECT, CONVERTER});
	private static final Signature CONVERT = TypeUtils.parseSignature("Object convert(Object, Class, Object)");

	interface BeanCopierKey {
		/**
		 * 实例化
		 * @param source 源
		 * @param target 目标
		 * @param useConverter 是否使用转换
		 * @return
		 */
		Object newInstance(String source, String target, boolean useConverter);
	}

	public static BaseBeanCopier create(Class source, Class target, boolean useConverter) {
		return BaseBeanCopier.create(source, target, null, useConverter);
	}

	public static BaseBeanCopier create(Class source, Class target, ClassLoader classLoader, boolean useConverter) {
		Generator gen;
		if (classLoader == null) {
			gen = new Generator();
		} else {
			gen = new Generator(classLoader);
		}
		gen.setSource(source);
		gen.setTarget(target);
		gen.setUseConverter(useConverter);
		return gen.create();
	}

	/**
	 * 拷贝
	 * @param from 源
	 * @param to 目标
	 * @param converter 转换器
	 */
	abstract public void copy(Object from, Object to, Converter converter);

	public static class Generator extends AbstractClassGenerator {
		private static final Source SOURCE = new Source(BaseBeanCopier.class.getName());
		private final ClassLoader classLoader;
		private Class source;
		private Class target;
		private boolean useConverter;

		Generator() {
			super(SOURCE);
			this.classLoader = null;
		}

		Generator(ClassLoader classLoader) {
			super(SOURCE);
			this.classLoader = classLoader;
		}

		public void setSource(Class source) {
			if (!Modifier.isPublic(source.getModifiers())) {
				setNamePrefix(source.getName());
			}
			this.source = source;
		}

		public void setTarget(Class target) {
			if (!Modifier.isPublic(target.getModifiers())) {
				setNamePrefix(target.getName());
			}

			this.target = target;
		}

		public void setUseConverter(boolean useConverter) {
			this.useConverter = useConverter;
		}

		@Override
		protected ClassLoader getDefaultClassLoader() {
			return target.getClassLoader();
		}

		@Override
		protected ProtectionDomain getProtectionDomain() {
			return ReflectUtils.getProtectionDomain(source);
		}

		public BaseBeanCopier create() {
			Object key = KEY_FACTORY.newInstance(source.getName(), target.getName(), useConverter);
			return (BaseBeanCopier) super.create(key);
		}

		@Override
		public void generateClass(ClassVisitor v) {
			Type sourceType = Type.getType(source);
			Type targetType = Type.getType(target);
			ClassEmitter ce = new ClassEmitter(v);
			ce.begin_class(Constants.V1_2,
				Constants.ACC_PUBLIC,
				getClassName(),
				BEAN_COPIER,
				null,
				Constants.SOURCE_FILE);

			EmitUtils.null_constructor(ce);
			CodeEmitter e = ce.begin_method(Constants.ACC_PUBLIC, COPY, null);

			// 2018.12.27 by L.cm 支持链式 bean
			PropertyDescriptor[] getters = BeanUtils.getBeanGetters(source);
			PropertyDescriptor[] setters = BeanUtils.getBeanSetters(target);
			Map<String, Object> names = new HashMap<>(16);
			for (PropertyDescriptor getter : getters) {
				names.put(getter.getName(), getter);
			}

			Local targetLocal = e.make_local();
			Local sourceLocal = e.make_local();
			e.load_arg(1);
			e.checkcast(targetType);
			e.store_local(targetLocal);
			e.load_arg(0);
			e.checkcast(sourceType);
			e.store_local(sourceLocal);

			for (int i = 0; i < setters.length; i++) {
				PropertyDescriptor setter = setters[i];
				PropertyDescriptor getter = (PropertyDescriptor) names.get(setter.getName());
				if (getter != null) {
					MethodInfo read = ReflectUtils.getMethodInfo(getter.getReadMethod());
					MethodInfo write = ReflectUtils.getMethodInfo(setter.getWriteMethod());
					if (useConverter) {
						Type setterType = write.getSignature().getArgumentTypes()[0];
						e.load_local(targetLocal);
						e.load_arg(2);
						e.load_local(sourceLocal);
						e.invoke(read);
						e.box(read.getSignature().getReturnType());
						EmitUtils.load_class(e, setterType);
						e.push(write.getSignature().getName());
						e.invoke_interface(CONVERTER, CONVERT);
						e.unbox_or_zero(setterType);
						e.invoke(write);
					} else if (compatible(getter, setter)) {
						// 2018.12.27 by L.cm 支持链式 bean
						e.load_local(targetLocal);
						e.load_local(sourceLocal);
						e.invoke(read);
						e.invoke(write);
					}
				}
			}
			e.return_value();
			e.end_method();
			ce.end_class();
		}

		private static boolean compatible(PropertyDescriptor getter, PropertyDescriptor setter) {
			return setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
		}

		@Override
		protected Object firstInstance(Class type) {
			return ReflectUtils.newInstance(type);
		}

		@Override
		protected Object nextInstance(Object instance) {
			return instance;
		}
	}
}
