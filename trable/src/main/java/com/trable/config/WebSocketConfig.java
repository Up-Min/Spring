//package com.trable.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//import com.Tingle.G4hosp.config.MyWebSocketHandler;
//
//import lombok.RequiredArgsConstructor;
//
//@Configuration
//@EnableWebSocket
//@RequiredArgsConstructor
//public class WebSocketConfig implements WebSocketConfigurer{
//
//
//    private final MyWebSocketHandler myWebSocketHandler;
//	
//	@Override
//	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		registry.addHandler(myWebSocketHandler, "/websocket").setAllowedOrigins("*");
//	}
//
//	
//
//	 
//	
//}
