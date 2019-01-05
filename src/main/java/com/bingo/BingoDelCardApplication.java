package com.bingo;

import com.bingo.encrypt.annotation.EnableEncrypt;
import org.mybatis.spring.annotation.MapperScan;
import org.n3r.idworker.Sid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableEncrypt
@EnableTransactionManagement
@MapperScan("com.bingo.poker.mapper")
@SpringBootApplication
public class BingoDelCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BingoDelCardApplication.class, args);
    }
    @Bean
    public Sid sid() {
        return new Sid();
    }
}
