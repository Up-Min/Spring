package com.trable.dto;

import java.util.List;

import com.trable.entity.Alert;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertDto {

	private Long Memid;
	
	private List<Alert> alertList;
	
}
