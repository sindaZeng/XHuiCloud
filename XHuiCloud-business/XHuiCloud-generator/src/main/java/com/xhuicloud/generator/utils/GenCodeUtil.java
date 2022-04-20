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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.json.JSONUtil;
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

    private final String ENTITY_JAVA = "Entity.ftl";
    private final String CONTROLLER_JAVA = "Controller.ftl";
    private final String SERVICE_JAVA = "Service.ftl";
    private final String SERVICE_IMPL_JAVA = "ServiceImpl.ftl";
    private final String MAPPER_JAVA = "Mapper.ftl";
    private final String MAPPER_XML = "Mapper.xml.ftl";

    private final String GENERATOR = "generator.properties";

    private Configuration generatorConfiguration;

    @SneakyThrows
    public void get(GenCodeDto genCodeDto, TableInfo tableInfo, List<TableColumnsInfo> tableColumnsInfo, ZipOutputStream zip) {
        // 类名例如 SysUser
        String ClassName = tableNameToJavaClassName(tableInfo.getTableName(), "");
        // 类名例如 sysUser
        String className = StringUtils.uncapitalize(ClassName);
        Configuration generatorConfig = getGeneratorConfig();
        Map<String, Object> map = new HashMap<>(16);
        map.put("package", genCodeDto.getPackagePath());
        map.put("projectName", genCodeDto.getModuleName());
        map.put("tableComment", tableInfo.getTableComment());
        map.put("author", genCodeDto.getAuthor());
        map.put("datetime", DateUtil.now());
        map.put("tableName", tableInfo.getTableName());
        map.put("ClassName", ClassName);
        map.put("className", className);
        map.put("pathName", className);
        tableColumnsInfo.forEach(tableColumn -> {
            if ("decimal".equals(tableColumn.getDataType())) {
                map.put("hasBigDecimal", true);
            }
            if ("datetime".equals(tableColumn.getDataType())) {
                map.put("hasLocalDateTime", true);
            }
            if ("auto_increment".equals(tableColumn.getExtra())) {
                map.put("auto", true);
            }
            tableColumn.setSmallColumnName(StringUtils.uncapitalize(columnToJava(tableColumn.getColumnName())));
            tableColumn.setJavaDataType(generatorConfig.getString(tableColumn.getDataType()));
            tableColumn.setDataType(tableColumn.getDataType());
        });
        map.put("columns", tableColumnsInfo);

        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));

        List<String> templates = getTemplates();

        for (String templateName : templates) {
            //渲染模板
            Template template = engine.getTemplate(templateName);
            // 渲染模板
            StringWriter sw = new StringWriter();
            try {
                template.render(map, sw);
            } catch (Exception e) {
                System.out.println(JSONUtil.toJsonStr(map));
                throw e;
            }

            //添加到zip
            zip.putNextEntry(new ZipEntry(Objects
                    .requireNonNull(getFileName(templateName, ClassName
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
        templates.add("server/" + ENTITY_JAVA);
        templates.add("server/" + CONTROLLER_JAVA);
        templates.add("server/" + SERVICE_JAVA);
        templates.add("server/" + SERVICE_IMPL_JAVA);
        templates.add("server/" + MAPPER_JAVA);
        templates.add("server/" + MAPPER_XML);
        return templates;
    }

    /**
     * 获取生成器配置信息
     */
    @SneakyThrows
    private Configuration getGeneratorConfig() {
        try {
            if (ObjectUtil.isNull(generatorConfiguration)) {
                synchronized (GenCodeUtil.class) {
                    if (ObjectUtil.isNull(generatorConfiguration)) {
                        generatorConfiguration = new PropertiesConfiguration(GENERATOR);
                    }
                }
            }
            return generatorConfiguration;
        } catch (ConfigurationException e) {
            throw SysException.sysFail("获取配置文件失败，", e);
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
        String packagePath = "XHuiCloud" + File.separator + "src"
                + File.separator + "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
            ;
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
}
