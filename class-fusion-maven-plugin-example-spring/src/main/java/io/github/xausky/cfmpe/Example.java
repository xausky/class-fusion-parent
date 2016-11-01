package io.github.xausky.cfmpe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by xausky on 11/1/16.
 */
@ComponentScan
@EnableAutoConfiguration
public class Example implements CommandLineRunner {
    @Autowired
    private Menu menu;

    public static void main(String[] args){
        SpringApplication.run(Example.class,args);
    }

    @Override
    public void run(String... strings) throws Exception {
        menu.setName("mine");
        menu.getUser().setId(0);
        menu.getUser().setName("xausky");
        menu.getUser().setEmail("xausky@gmail.com");
        System.out.println(menu.toString());
    }
}
