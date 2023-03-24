package com.trable.config;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trable.dto.AlertDto;
import com.trable.entity.Alert;
import com.trable.entity.Member;
import com.trable.service.AlertService;
import com.trable.service.MemberService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlertWebSocketHandler extends TextWebSocketHandler{
	
	private final MemberService memberService;
	private final AlertService alertService;
	private final ObjectMapper objectMapper;


	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//Alert alert = objectMapper.readValue(message.getPayload(), Alert.class);
		Member member = objectMapper.readValue(message.getPayload(), Member.class);
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Alert> alertList = alertService.Alertlistbymemid(member.getId());
		System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.err.println("alertlisttest (handle): "+alertList);
		if(alertList != null) {
			for(Alert a : alertList) {
				System.err.println("alertList");
				String alertmessage = "확인되지 않은 알람이 있습니다.";
				String alertdate = a.getAlertdate();
				String alerttype = a.getAlerttype();
				String alertdetail = a.getAlertdetail();
				
				session.sendMessage(new TextMessage(alertmessage));
				session.sendMessage(new TextMessage(alertdate));
				session.sendMessage(new TextMessage(alerttype));
				session.sendMessage(new TextMessage(alertdetail));	
		}
		}
		
	}
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		if(session.getPrincipal() != null) {
			List<Alert> alertList = alertService.Alertlistbymemid(memberService.findMember(session.getPrincipal().getName()).getId());
			for(Alert a : alertList) {
				//System.err.println("alertList");
				String alertmessage = "확인되지 않은 알람이 있습니다.";
				String alertdate = a.getAlertdate();
				String alerttype = a.getAlerttype();
				String alertdetail = a.getAlertdetail();
				
				session.sendMessage(new TextMessage(alertmessage));
				session.sendMessage(new TextMessage(alertdate));
				session.sendMessage(new TextMessage(alerttype));
				session.sendMessage(new TextMessage(alertdetail));		
			}
			
		}
		else {
			session.close();
		}
		
		
	}







	
	
}