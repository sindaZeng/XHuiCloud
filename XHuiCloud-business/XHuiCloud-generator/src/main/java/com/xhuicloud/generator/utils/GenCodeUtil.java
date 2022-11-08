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

package com.xhuicloud.generator.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.xhuicloud.common.core.enums.base.BooleanEnum;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.generator.dto.GenCodeDto;
import com.xhuicloud.generator.entity.TableColumnsInfo;
import com.xhuicloud.generator.entity.TableInfo;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @program: XHuiCloud
 * @description: GenCodeUtil
 * @author: Sinda
 * @create: 2020-06-23 10:06
 */
@UtilityClass
public class GenCodeUtil {

    private static final String SERVER_PREFIX = "server/";
    private static final String ENTITY_JAVA = "Entity.ftl";
    private static final String CONTROLLER_JAVA = "Controller.ftl";
    private static final String SERVICE_JAVA = "Service.ftl";
    private static final String SERVICE_IMPL_JAVA = "ServiceImpl.ftl";
    private static final String MAPPER_JAVA = "Mapper.ftl";
    private static final String MAPPER_XML = "Mapper.xml.ftl";

    private static final String VUE_PREFIX = "vue/";

    private static final String TS_PREFIX = "ts/";
    private static final String DTO_INDEX_JS = "ApiDtoIndex.js.ftl";

    private static final String DTO_INDEX_TS = "entity.d.ts.ftl";

    private static final String COLUMN_INDEX_TS = "index.ts.ftl";
    private static final String API_INDEX_JS = "ApiIndex.js.ftl";
    private static final String API_INDEX_TS = "apiIndex.ts.ftl";
    private static final String VUE_INDEX = "index.vue.ftl";

    private static final String JAVA_TYPE_PROPERTIES = "javaType.properties";

    private static final String DB_TYPE_PROPERTIES = "dbType.properties";

    private static final String TYPESCRIPT_PROPERTIES = "typeScript.properties";

    private static Configuration javaTypeConfiguration;

    private static Configuration dbTypeConfiguration;

    private static Configuration typeScriptConfiguration;

    private static List<String> serverTemplates;

    private static List<String> webJavaScriptTemplates;

    private static List<String> webTypeScriptTemplates;

    static {
        System.out.println("11111111111111111111");
        initServerTemplates();
        initWebJavaScriptTemplates();
        initWebTypeScriptTemplates();
        try {
            if (ObjectUtil.isNull(javaTypeConfiguration)) {
                synchronized (GenCodeUtil.class) {
                    if (ObjectUtil.isNull(javaTypeConfiguration)) {
                        javaTypeConfiguration = new PropertiesConfiguration(JAVA_TYPE_PROPERTIES);
                    }
                }
            }

            if (ObjectUtil.isNull(dbTypeConfiguration)) {
                synchronized (GenCodeUtil.class) {
                    if (ObjectUtil.isNull(dbTypeConfiguration)) {
                        dbTypeConfiguration = new PropertiesConfiguration(DB_TYPE_PROPERTIES);
                    }
                }
            }

            if (ObjectUtil.isNull(typeScriptConfiguration)) {
                synchronized (GenCodeUtil.class) {
                    if (ObjectUtil.isNull(typeScriptConfiguration)) {
                        typeScriptConfiguration = new PropertiesConfiguration(TYPESCRIPT_PROPERTIES);
                    }
                }
            }
        } catch (ConfigurationException e) {
            throw SysException.sysFail("获取配置文件失败，", e);
        }

    }

    @SneakyThrows
    public void get(GenCodeDto genCodeDto, TableInfo tableInfo, List<TableColumnsInfo> tableColumnsInfo, ZipOutputStream zip) {

        // 类名例如 SysUser
        String ClassName = tableNameToJavaClassName(tableInfo.getTableName(), "");
        // 类名例如 sysUser
        String className = StringUtils.uncapitalize(ClassName);

        String ClassNameServer = ClassName;
        String classNameServer = className;
        String ClassNameVue = ClassName;
        String classNameVue = className;

        if (CollectionUtil.isNotEmpty(genCodeDto.getToReplace()) && StrUtil.isNotEmpty(genCodeDto.getTablePrefix())) {
            // 去除前缀 类名例如User
            if (genCodeDto.getToReplace().contains(0)) {
                ClassNameServer = tableNameToJavaClassName(tableInfo.getTableName(), genCodeDto.getTablePrefix());
                classNameServer = StringUtils.uncapitalize(ClassNameServer);
            }
            if (genCodeDto.getToReplace().contains(1)) {
                ClassNameVue = tableNameToJavaClassName(tableInfo.getTableName(), genCodeDto.getTablePrefix());
                classNameVue = StringUtils.uncapitalize(ClassNameVue);
            }
        }

        Map<String, Object> map = new HashMap<>(16);
        map.put("package", genCodeDto.getPackagePath());
        map.put("moduleName", genCodeDto.getModuleName());
        map.put("tableComment", tableInfo.getTableComment());
        map.put("author", genCodeDto.getAuthor());
        map.put("datetime", DateUtil.now());
        map.put("tableName", tableInfo.getTableName());
        map.put("ClassName", ClassNameServer);
        map.put("className", classNameServer);
        map.put("pathName", classNameServer);
        map.put("contextPath", genCodeDto.getContextPath());
        tableColumnsInfo.forEach(tableColumn -> {
            if ("decimal".equals(tableColumn.getDataType())) {
                map.put("hasBigDecimal", true);
            }
            if (StrUtil.containsAny(tableColumn.getDataType(), "datetime", "date")) {
                map.put("datePath", "java.util.Date");
            } else if ("timestamp".equals(tableColumn.getDataType())) {
                map.put("datePath", "java.time.LocalDateTime");
            }

            if ("auto_increment".equals(tableColumn.getExtra())) {
                map.put("auto", true);
            }
            tableColumn.setSmallColumnName(StringUtils.uncapitalize(columnToJava(tableColumn.getColumnName())));
            tableColumn.setJavaDataType(javaTypeConfiguration.getString(tableColumn.getDataType()));
            tableColumn.setTsType(typeScriptConfiguration.getString(tableColumn.getJavaDataType()));
            String dataType = dbTypeConfiguration.getString(tableColumn.getDataType());
            tableColumn.setDataType(StrUtil.isBlank(dataType) ? tableColumn.getDataType() : dataType);
        });
        map.put("columns", tableColumnsInfo);

        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));

        for (String templateName : serverTemplates) {
            Template template = engine.getTemplate(templateName);
            // 渲染模板
            StringWriter sw = new StringWriter();
            template.render(map, sw);
            //添加到zip
            zip.putNextEntry(new ZipEntry(Objects
                    .requireNonNull(getServerFileName(templateName, ClassNameServer
                            , map.get("package").toString()))));
            IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
            IoUtil.close(sw);
            zip.closeEntry();
        }
        if (genCodeDto.getGenWeb() == BooleanEnum.YES.getCode()) {
            map.put("ClassName", ClassNameVue);
            map.put("className", classNameVue);
            map.put("pathName", classNameVue);
            for (String templateName : genCodeDto.getIsTs()  == 0 ? webTypeScriptTemplates : webJavaScriptTemplates) {
                Template template = engine.getTemplate(templateName);
                // 渲染模板
                StringWriter sw = new StringWriter();
                template.render(map, sw);
                //添加到zip
                zip.putNextEntry(new ZipEntry(Objects
                        .requireNonNull(genCodeDto.getIsTs()  == 0 ?
                                getVueTSFileName(templateName, classNameVue, genCodeDto.getModuleName())
                                : getVueJSFileName(templateName, classNameVue))));
                IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
                IoUtil.close(sw);
                zip.closeEntry();
            }
        }
    }

    /**
     * 初始化后端模板
     *
     * @return
     */
    private void initServerTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add(SERVER_PREFIX + ENTITY_JAVA);
        templates.add(SERVER_PREFIX + CONTROLLER_JAVA);
        templates.add(SERVER_PREFIX + SERVICE_JAVA);
        templates.add(SERVER_PREFIX + SERVICE_IMPL_JAVA);
        templates.add(SERVER_PREFIX + MAPPER_JAVA);
        templates.add(SERVER_PREFIX + MAPPER_XML);
        serverTemplates = templates;
    }

    /**
     * 初始化JavaScript前端模板
     *
     * @return
     */
    private void initWebJavaScriptTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add(VUE_PREFIX + DTO_INDEX_JS);
        templates.add(VUE_PREFIX + API_INDEX_JS);
        templates.add(VUE_PREFIX + VUE_INDEX);
        webJavaScriptTemplates = templates;
    }

    /**
     * 初始化TypeScript前端模板
     *
     * @return
     */
    private void initWebTypeScriptTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add(TS_PREFIX + VUE_INDEX);
        templates.add(TS_PREFIX + API_INDEX_TS);
        templates.add(TS_PREFIX + DTO_INDEX_TS);
        templates.add(TS_PREFIX + COLUMN_INDEX_TS);
        webTypeScriptTemplates = templates;
    }

    /**
     * 表名转换成Java类名
     */
    private String tableNameToJavaClassName(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 列名转换成Java属性名
     */
    public String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 获取后端文件名
     */
    private String getServerFileName(String template, String className, String packageName) {
        String packagePath = "XHuiCloud" + File.separator + "src"
                + File.separator + "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }
        if (template.contains(ENTITY_JAVA)) {
            return packagePath + "entity" + File.separator + className + ".java";
        }
        if (template.contains(CONTROLLER_JAVA)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }
        if (template.contains(SERVICE_JAVA)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }
        if (template.contains(SERVICE_IMPL_JAVA)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }
        if (template.contains(MAPPER_JAVA)) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }
        if (template.contains(MAPPER_XML)) {
            return "XHuiCloud" + File.separator + "src" + File.separator + "main"
                    + File.separator + "resources" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
        }
        return null;
    }

    /**
     * 获取前端文件名
     */
    private String getVueJSFileName(String template, String className) {
        String packagePath = "xhuicloud-ui" + File.separator + "src"
                + File.separator;
        if (template.contains(DTO_INDEX_JS)) {
            return packagePath + className + File.separator + "api" + File.separator + "dto" + File.separator + "index.js";
        }
        if (template.contains(API_INDEX_JS)) {
            return packagePath + className + File.separator + "api" + File.separator + "index.js";
        }
        if (template.contains(VUE_INDEX)) {
            return packagePath + className + File.separator + "index.vue";
        }
        return null;
    }

    /**
     * 获取前端文件名
     */
    private String getVueTSFileName(String template, String className, String moduleName) {
        String packagePath = "xhuicloud-ui" + File.separator + "src"
                + File.separator;

        if (template.contains(VUE_INDEX)) {
            return packagePath + className + File.separator + "index.vue";
        }

        if (template.contains(COLUMN_INDEX_TS)) {
            return packagePath + className + File.separator + "index.ts";
        }

        if (template.contains(API_INDEX_TS)) {
            return packagePath + className + File.separator + "api" + File.separator + moduleName + File.separator + className + ".ts";
        }

        if (template.contains(DTO_INDEX_TS)) {
            return packagePath + className + File.separator + "api" + File.separator + moduleName + File.separator + "entity" + File.separator + className + ".d.ts";
        }
        return null;
    }
}
