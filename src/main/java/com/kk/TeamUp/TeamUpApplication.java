package com.kk.TeamUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//exclude = SecurityAutoConfiguration.class : 스프링 security 잠시 꺼놓을 수 있도록 해주는 설정
@SpringBootApplication()
@EnableJpaAuditing //created_at, updated_at 자동 업데이트 위한 어노테이션
public class TeamUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamUpApplication.class, args);
	}

}
