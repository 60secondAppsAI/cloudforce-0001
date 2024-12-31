package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseReportDTO {

	private Integer ownerId;

	private Integer reportId;

	private Integer reportStatus;
	
	private Integer nextOrPrevious;
}

