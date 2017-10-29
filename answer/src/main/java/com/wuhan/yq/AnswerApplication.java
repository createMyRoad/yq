package com.wuhan.yq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages={"com.wuhan.yq"})
@ImportResource("classpath:springContext.xml")
public class AnswerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnswerApplication.class, args);
	}
}
