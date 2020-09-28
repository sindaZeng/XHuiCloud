package com.xhuicloud.generator.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xhuicloud.generator.dto.GenCodeDto;
import com.xhuicloud.generator.entity.TableColumnsInfo;
import com.xhuicloud.generator.entity.TableInfo;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

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

    private final String ENTITY_JAVA_VM = "Entity.java.vm";
    private final String CONTROLLER_JAVA_VM = "Controller.java.vm";
    private final String SERVICE_JAVA_VM = "Service.java.vm";
    private final String SERVICE_IMPL_JAVA_VM = "ServiceImpl.java.vm";
    private final String MAPPER_JAVA_VM = "Mapper.java.vm";
    private final String MAPPER_XML_VM = "Mapper.xml.vm";

    private final String GENERATOR = "generator";
    private final String JAVA_TYPE = "javaType";

    private PropertiesConfiguration generatorConfiguration;

    private PropertiesConfiguration javaTypeConfiguration;

    @SneakyThrows
    public void get(GenCodeDto genCodeDto, TableInfo tableInfo, List<TableColumnsInfo> tableColumnsInfo, ZipOutputStream zip) {
        // 类名例如 SysUser
        String ClassName = tableNameToJavaClassName(tableInfo.getTableName(), "");
        // 类名例如 sysUser
        String className = StringUtils.uncapitalize(ClassName);
        Configuration generatorConfig = getGeneratorConfig(GENERATOR);
        Configuration javaTypeConfig = getGeneratorConfig(JAVA_TYPE);
        Map<String, Object> map = new HashMap<>(16);
        map.put("package", generatorConfig.getString("package"));
        map.put("projectName", generatorConfig.getString("projectName"));
        map.put("tableComment", tableInfo.getTableComment());
        map.put("author", genCodeDto.getAuthor());
        map.put("datetime", DateUtil.now());
        map.put("tableName", tableInfo.getTableName());
        map.put("ClassName", ClassName);
        map.put("className", className);
        map.put("pathName", className);
        tableColumnsInfo.forEach(tableColumns -> {
            if ("decimal".equals(tableColumns.getDataType())) {
                map.put("hasBigDecimal", true);
            }
            tableColumns.setSmallColumnName(StringUtils.uncapitalize(columnToJava(tableColumns.getColumnName())));
            tableColumns.setJavaDataType(generatorConfig.getString(tableColumns.getDataType()));
            tableColumns.setDataType(javaTypeConfig.getString(tableColumns.getDataType()));
        });
        map.put("columns", tableColumnsInfo);

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        VelocityContext context = new VelocityContext(map);


        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
            tpl.merge(context, sw);

            //添加到zip
            zip.putNextEntry(new ZipEntry(Objects
                    .requireNonNull(getFileName(template, ClassName
                            , map.get("package").toString()))));
            IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
            IoUtil.close(sw);
            zip.closeEntry();
        }
    }

    /**
     * 获取模板
     *
     * @return
     */
    private List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("template/" + ENTITY_JAVA_VM);
        templates.add("template/" + CONTROLLER_JAVA_VM);
        templates.add("template/" + SERVICE_JAVA_VM);
        templates.add("template/" + SERVICE_IMPL_JAVA_VM);
        templates.add("template/" + MAPPER_JAVA_VM);
        templates.add("template/" + MAPPER_XML_VM);
        return templates;
    }

    /**
     * 获取生成器配置信息
     */
    @SneakyThrows
    private Configuration getGeneratorConfig(String properties) {
        if (StrUtil.equals(GENERATOR, properties)) {
            if (ObjectUtil.isNull(generatorConfiguration)) {
                synchronized (GenCodeUtil.class) {
                    if (ObjectUtil.isNull(generatorConfiguration)) {
                        generatorConfiguration = new PropertiesConfiguration(GENERATOR + ".properties");
                    }
                }
            }
            return generatorConfiguration;
        } else {
            if (ObjectUtil.isNull(javaTypeConfiguration)) {
                synchronized (GenCodeUtil.class) {
                    if (ObjectUtil.isNull(javaTypeConfiguration)) {
                        javaTypeConfiguration = new PropertiesConfiguration(JAVA_TYPE + ".properties");
                    }
                }
            }
            return javaTypeConfiguration;
        }
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
     * 获取文件名
     */
    private String getFileName(String template, String className, String packageName) {
        String packagePath = getGeneratorConfig(GENERATOR).getString("projectName") + File.separator + "src"
                + File.separator + "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
            ;
        }
        if (template.contains(ENTITY_JAVA_VM)) {
            return packagePath + "entity" + File.separator + className + ".java";
        }
        if (template.contains(CONTROLLER_JAVA_VM)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }
        if (template.contains(SERVICE_JAVA_VM)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }
        if (template.contains(SERVICE_IMPL_JAVA_VM)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }
        if (template.contains(MAPPER_JAVA_VM)) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }
        if (template.contains(MAPPER_XML_VM)) {
            return getGeneratorConfig(GENERATOR).getString("projectName") + File.separator + "src" + File.separator + "main"
                    + File.separator + "resources" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
        }
        return null;
    }
}
