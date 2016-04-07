package com.exteso.bruno.configuration;

import java.util.EnumSet;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ch.digitalfondue.npjt.QueryFactory;
import ch.digitalfondue.npjt.mapper.ZonedDateTimeMapper;

import com.exteso.bruno.configuration.support.Platform;
import com.exteso.bruno.repository.FileUploadRepository;
import com.exteso.bruno.repository.JobRequestBidRepository;
import com.exteso.bruno.repository.JobRequestRepository;
import com.exteso.bruno.repository.UserRepository;
import com.exteso.bruno.service.FileSystemStorageService;
import com.exteso.bruno.service.S3StorageService;
import com.exteso.bruno.service.ServicesMarker;
import com.exteso.bruno.service.StorageService;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@ComponentScan(basePackageClasses=ServicesMarker.class)
@EnableScheduling
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
    
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public QueryFactory queryFactory(Environment env, Platform platform, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        QueryFactory qf = new QueryFactory(platform.getDialect(env), namedParameterJdbcTemplate);
        qf.addColumnMapperFactory(new ZonedDateTimeMapper.Factory());
        qf.addParameterConverters(new ZonedDateTimeMapper.Converter());
        return qf;
    }
    
    @Bean
    public UserRepository userRepository(QueryFactory queryFactory) {
        return queryFactory.from(UserRepository.class);
    }
    
    @Bean
    public JobRequestRepository jobRequestRepository(QueryFactory queryFactory) {
        return queryFactory.from(JobRequestRepository.class);
    }
    
    @Bean
    public JobRequestBidRepository jobRequestBidRepository(QueryFactory queryFactory) {
        return queryFactory.from(JobRequestBidRepository.class);
    }
    
    @Bean
    public FileUploadRepository fileUploadRepository(QueryFactory queryFactory) {
        return queryFactory.from(FileUploadRepository.class);
    }
    
    @Bean
    public StorageService getStorageService(Platform platform, Environment env) {
        if(platform.useS3AsStorage(env)) {
            String accessKey = platform.getS3AccessKey();
            String secretKey = platform.getS3SecretKey();
            String endpoint = platform.getS3Endpoint();
            String bucketName = platform.getS3BucketName();
            return new S3StorageService(accessKey, secretKey, bucketName, endpoint);
        } else {
            return new FileSystemStorageService();
        }
    }
}
