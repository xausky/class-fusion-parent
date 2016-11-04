package io.github.xausky.cfmpe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.Iterator;

/**
 * Created by xausky on 11/1/16.
 */
@ComponentScan
@EnableAutoConfiguration
public class Example implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(Example.class);
    @Autowired
    LogRepository logRepository;

    public static void main(String[] args){
        SpringApplication.run(Example.class,args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Log log = logRepository.findOne(1);
        logger.info(log.toString());
    }
}
