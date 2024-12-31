package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePriceBookEntryDTO {

	private Integer ownerId;

	private Integer priceBookEntryId;

	private Integer priceBookEntryStatus;
	
	private Integer nextOrPrevious;
}

