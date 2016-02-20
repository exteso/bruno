package com.exteso.bruno.configuration;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class SpringBootLauncher {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootInitializer.class, DataSourceConfiguration.class)
            .headless(System.getProperty("startDBManager") == null)
            .run(args);

        if (System.getProperty("startDBManager") != null) {
            Class<?> cls;
            try {
                cls = ClassUtils.getClass("org.hsqldb.util.DatabaseManagerSwing");
                MethodUtils.invokeStaticMethod(cls, "main", new Object[] { new String[] { "--url", "jdbc:hsqldb:mem:bruno", "--noexit" } });
            } catch (ReflectiveOperationException e) {
            }
        }
    }
}
