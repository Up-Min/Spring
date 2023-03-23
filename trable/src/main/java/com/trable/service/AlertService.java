package com.trable.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.trable.entity.Alert;
import com.trable.entity.Member;
import com.trable.repository.AlertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertService {	
	
	private final AlertRepository alertRepository;
	
	public Alert CreateAlert(Member member, String alerttype, String alertdetail) {
		String nowdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String altchk = "N";
		Alert alert = Alert.createAlert(member, alerttype, nowdate, alertdetail, altchk);
		return alert;
	}
	
	public List<Alert> Alertlistbymemid(Long memberid){
		List<Alert> alertList = alertRepository.findAlertlistbymemid(memberid);
		return alertList;
	}
	
}
