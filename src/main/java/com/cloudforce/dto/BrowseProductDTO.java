package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseProductDTO {

	private Integer ownerId;

	private Integer productId;

	private Integer productStatus;
	
	private Integer nextOrPrevious;
}

