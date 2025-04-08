package com.flightIQ.Navigation.config;


import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jConfig {
    
    @Bean
    public org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {
        // Use the latest dialect or specify a version
        return org.neo4j.cypherdsl.core.renderer.Configuration.newConfig()
            .withDialect(Dialect.NEO4J_5)  // Or whatever version you're using
            .build();
    }
}
