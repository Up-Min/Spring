package com.trable.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer{
	
	private final AlertWebSocketHandler alertWebSocketHandler;
	private final ChatSocketHandler chatSocketHandler;
	
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(alertWebSocketHandler, "/alert").setAllowedOrigins("*");
		registry.addHandler(chatSocketHandler, "/chat");
		}
	
	
	
	
}