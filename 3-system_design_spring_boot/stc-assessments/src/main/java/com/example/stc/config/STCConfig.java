package com.example.stc.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.example.stc.domain")
@EnableJpaRepositories("com.example.stc.repositories")
@EnableTransactionManagement
public class STCConfig {
}
