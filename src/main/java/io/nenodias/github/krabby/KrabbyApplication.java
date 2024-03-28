package io.nenodias.github.krabby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
public class KrabbyApplication {

    public static void main(String[] args) {
        SpringApplication.run(KrabbyApplication.class, args);
    }

}
