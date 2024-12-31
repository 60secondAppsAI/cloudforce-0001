package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePriceBookDTO {

	private Integer ownerId;

	private Integer priceBookId;

	private Integer priceBookStatus;
	
	private Integer nextOrPrevious;
}

