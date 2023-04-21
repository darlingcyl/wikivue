package com.frank.wiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SpringBootApplication
@RestController
public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);
//    @value("${driverName:man}")
//   String name;
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
