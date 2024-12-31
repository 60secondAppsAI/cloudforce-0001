package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseDashboardDTO {

	private Integer ownerId;

	private Integer dashboardId;

	private Integer dashboardStatus;
	
	private Integer nextOrPrevious;
}

