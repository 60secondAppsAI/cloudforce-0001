package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.PriceBookEntry;
import com.cloudforce.dto.PriceBookEntryDTO;
import com.cloudforce.dto.PriceBookEntrySearchDTO;
import com.cloudforce.dto.PriceBookEntryPageDTO;
import com.cloudforce.dto.PriceBookEntryConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PriceBookEntryService extends GenericService<PriceBookEntry, Integer> {

	List<PriceBookEntry> findAll();

	ResultDTO addPriceBookEntry(PriceBookEntryDTO priceBookEntryDTO, RequestDTO requestDTO);

	ResultDTO updatePriceBookEntry(PriceBookEntryDTO priceBookEntryDTO, RequestDTO requestDTO);

    Page<PriceBookEntry> getAllPriceBookEntrys(Pageable pageable);

    Page<PriceBookEntry> getAllPriceBookEntrys(Specification<PriceBookEntry> spec, Pageable pageable);

	ResponseEntity<PriceBookEntryPageDTO> getPriceBookEntrys(PriceBookEntrySearchDTO priceBookEntrySearchDTO);
	
	List<PriceBookEntryDTO> convertPriceBookEntrysToPriceBookEntryDTOs(List<PriceBookEntry> priceBookEntrys, PriceBookEntryConvertCriteriaDTO convertCriteria);

	PriceBookEntryDTO getPriceBookEntryDTOById(Integer priceBookEntryId);


}
