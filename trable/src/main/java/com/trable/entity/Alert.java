package com.trable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "alert")
@Getter
@Setter
@ToString
public class Alert {

	@Id
	@Column(name = "alert_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	private String alerttype;
	
	private String alertdate;
	
	private String alertdetail;
	
	private String alertcheck;
	
	public static Alert createAlert(Member member, String alerttype, String alertdate, String alertdetail, String altchk) {
		Alert alert = new Alert();
		alert.setMember(member);
		alert.setAlerttype(alerttype);
		alert.setAlertdate(alertdate);
		alert.setAlertdetail(alertdetail);
		alert.setAlertcheck(altchk);
		return alert;
	}
	
}
