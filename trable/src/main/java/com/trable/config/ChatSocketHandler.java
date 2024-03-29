package com.trable.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatSocketHandler extends TextWebSocketHandler{

    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
    
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
       super.afterConnectionEstablished(session);
    	sessionMap.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
       sessionMap.remove(session.getId());
    	super.afterConnectionClosed(session, status);
    	
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	String msg = message.getPayload();
    	for(String key : sessionMap.keySet()) {
    		WebSocketSession wss = sessionMap.get(key);
    		try {
				wss.sendMessage(new TextMessage(msg));
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    }
	
}
