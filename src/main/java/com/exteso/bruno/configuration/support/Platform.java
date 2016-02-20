package com.exteso.bruno.configuration.support;

import org.springframework.core.env.Environment;

public enum Platform {
    DEFAULT;

    public String getUrl(Environment env) {
        return "jdbc:hsqldb:mem:bruno";
    }

    public String getUsername(Environment env) {
        return "sa";
    }

    public String getPassword(Environment env) {
        return "";
    }

    public String getDialect(Environment env) {
        return "HSQLDB";
    }

    public boolean isHosting(Environment env) {
        return true;
    }
}
