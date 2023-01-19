package com.myshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration //bean을 하나 만들거라서 필요함.
@EnableJpaAuditing //JPA의 auditing 기능을 활성화 시킨다

//AuditorAwareImpl을 사용하게 해주는 AuditConfig
public class AuditConfig {

	@Bean //springcontext에서 관리할 수 있는 객체가 된다. 의존성 주입이 가능해진다. 
	public AuditorAware<String> auditorProvider(){
		return new AuditorAwareImpl();
	}
	
}
