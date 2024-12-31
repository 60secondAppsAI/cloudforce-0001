package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.PriceBook;
import com.cloudforce.dto.PriceBookDTO;
import com.cloudforce.dto.PriceBookSearchDTO;
import com.cloudforce.dto.PriceBookPageDTO;
import com.cloudforce.dto.PriceBookConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PriceBookService extends GenericService<PriceBook, Integer> {

	List<PriceBook> findAll();

	ResultDTO addPriceBook(PriceBookDTO priceBookDTO, RequestDTO requestDTO);

	ResultDTO updatePriceBook(PriceBookDTO priceBookDTO, RequestDTO requestDTO);

    Page<PriceBook> getAllPriceBooks(Pageable pageable);

    Page<PriceBook> getAllPriceBooks(Specification<PriceBook> spec, Pageable pageable);

	ResponseEntity<PriceBookPageDTO> getPriceBooks(PriceBookSearchDTO priceBookSearchDTO);
	
	List<PriceBookDTO> convertPriceBooksToPriceBookDTOs(List<PriceBook> priceBooks, PriceBookConvertCriteriaDTO convertCriteria);

	PriceBookDTO getPriceBookDTOById(Integer priceBookId);


}
