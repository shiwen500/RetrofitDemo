package com.example.retrofitserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.retrofitserver.mapper")
public class RetrofitserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetrofitserverApplication.class, args);
	}

}
