package com.devtb.crmapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.devtb.*"})
public class AppConfig {

    @Bean
    public Bootstrapper getBoostrapper(){

        return new Bootstrapper();
    }



}
