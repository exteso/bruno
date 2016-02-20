package com.exteso.bruno.configuration;

import java.util.EnumSet;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.exteso.bruno.configuration.support.Platform;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
public class DataSourceConfiguration {

    @Bean
    public Platform getPlatform(Environment env) {
        return EnumSet.complementOf(EnumSet.of(Platform.DEFAULT))
                .stream()
                .filter(p -> p.isHosting(env))
                .findFirst()
                .orElse(Platform.DEFAULT);
    }

    @Bean(destroyMethod = "close")
    public DataSource getDataSource(Environment env, Platform platform) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(platform.getUrl(env));
        dataSource.setUsername(platform.getUsername(env));
        dataSource.setPassword(platform.getPassword(env));
        return dataSource;
    }

    @Bean
    public Flyway migrator(Environment env, Platform platform, DataSource dataSource) {
        String sqlDialect = platform.getDialect(env);
        Flyway migration = new Flyway();
        migration.setDataSource(dataSource);

        migration.setValidateOnMigrate(false);
        migration.setTarget(MigrationVersion.LATEST);

        migration.setLocations("bruno/db/" + sqlDialect + "/");
        migration.migrate();
        return migration;
    }
}
