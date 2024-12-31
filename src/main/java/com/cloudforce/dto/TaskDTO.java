package com.cloudforce.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class TaskDTO {

	private Integer taskId;

	private String subject;

	private Date dueDate;

	private String priority;






}
