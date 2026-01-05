package com.luis.helpdesk.config;

import com.luis.helpdesk.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public CommandLineRunner instanciaDB(){
        return args -> this.dbService.instanciaDB();
    }
}
