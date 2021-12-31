package com.wcc.platform.mybatis;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;

@Slf4j
public class AutoGenerator {

    static String url = "jdbc:mysql://10.45.46.116:3306/zstream";
    static String username = "root";
    static String password = "bdp123";

    static {
        try {
            Class.forName(com.mysql.cj.jdbc.Driver.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateDriver() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            log.error("", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void autoGenerator(String tableName, String tablePrefix, String module) {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("wcc")
                            .enableSwagger()
                            .fileOverride()
                            .outputDir("../../mybatisplus");
                })
                .packageConfig(builder -> {
                    builder.parent("com.wcc.platform")
                            .moduleName(module)
                            .entity("bean")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "../..//mybatisplus"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName)
                            .addTablePrefix(tablePrefix);
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .templateConfig(builder -> {
//                    builder.disable(TemplateType.SERVICE)
//                            .disable(TemplateType.SERVICEIMPL)
//                            .disable(TemplateType.CONTROLLER);
                })
                .execute();

    }

    @Test
    public void AutoGenerator() {
        autoGenerator("wcc_sys_datasource", "wcc_sys", "system");
    }

}
