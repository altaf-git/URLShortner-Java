package com.urlShortner.persistence.common;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories(basePackages = {"com.urlShortner.persistence"})
@EnableR2dbcAuditing
@Log
class PostgresConfiguration {

    @Bean
    ReactiveTransactionManager transactionManager(final ConnectionFactory connectionFactory) {
        log.info("Transaction Manager being created...");
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    TransactionalOperator transactionalOperator(final ReactiveTransactionManager manager) {
        log.info("Transaction Operator being created....");
        return TransactionalOperator.create(manager);
    }

    @Bean
    public ConnectionFactoryInitializer initializer(final ConnectionFactory connectionFactory) {

        log.info("ConnectionFactory Initializer being requested...");
        final ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        final CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        final ClassPathResource cpr = new ClassPathResource("schema.sql");
        if (cpr.exists()) {
            populator.addPopulators(new ResourceDatabasePopulator(cpr));
        }
        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}
