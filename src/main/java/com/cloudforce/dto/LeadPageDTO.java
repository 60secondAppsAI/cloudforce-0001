package com.cloudforce.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LeadPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<LeadDTO> leads;
}





