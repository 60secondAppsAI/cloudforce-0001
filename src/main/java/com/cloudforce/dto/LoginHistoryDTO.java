package com.cloudforce.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class LoginHistoryDTO {

	private Integer loginHistoryId;

	private Date loginTime;

	private String ipAddress;






}
