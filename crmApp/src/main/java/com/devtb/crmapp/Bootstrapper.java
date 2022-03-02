package com.devtb.crmapp;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;


public class Bootstrapper {

    private final static Logger log = LoggerFactory.getLogger(Bootstrapper.class);

    @Autowired
    Environment environment;

    @Autowired
    private StartupOperations startupOperations;

    @Autowired
    ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        try {
            startupOperations.executeStartupOperations();
        } catch (Exception e) {
            log.error("error on startup {}",e.getMessage());
            e.printStackTrace();
        }

    }

}
