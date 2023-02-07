package com.myshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 업로드한 파일을 읽어올 경로를 설정 
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	
	@Value ("${uploadPath}") // 롬복이 아닌 다른 value, 프로퍼티의 값을 읽어온다.
	String uploadPath;

	//오버라이드에서 가져옴

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
				.addResourceLocations(uploadPath);
		// /images = file:///Users/l/shop/
		
		// img 폴더의 logo.png 확인해보면 localhost/images/logo.png가 원래 뜨는데,
		// images로 시작하는 폴더의 파일들은 uploadPath에 있는 걸로 가져오겠다는 의미.
		// properties의 uploadPath=file:///Users/l/shop/에 의해, shop폴더에서 사진을 가져오겠다는 의미가 된다.
		
	}	
}
