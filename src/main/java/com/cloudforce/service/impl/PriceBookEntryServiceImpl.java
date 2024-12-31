package com.cloudforce.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.cloudforce.dao.GenericDAO;
import com.cloudforce.service.GenericService;
import com.cloudforce.service.impl.GenericServiceImpl;
import com.cloudforce.dao.PriceBookEntryDAO;
import com.cloudforce.domain.PriceBookEntry;
import com.cloudforce.dto.PriceBookEntryDTO;
import com.cloudforce.dto.PriceBookEntrySearchDTO;
import com.cloudforce.dto.PriceBookEntryPageDTO;
import com.cloudforce.dto.PriceBookEntryConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.PriceBookEntryService;
import com.cloudforce.util.ControllerUtils;

@Service
public class PriceBookEntryServiceImpl extends GenericServiceImpl<PriceBookEntry, Integer> implements PriceBookEntryService {

    private final static Logger logger = LoggerFactory.getLogger(PriceBookEntryServiceImpl.class);

	@Autowired
	PriceBookEntryDAO priceBookEntryDao;

	@Override
	public GenericDAO<PriceBookEntry, Integer> getDAO() {
		return (GenericDAO<PriceBookEntry, Integer>) priceBookEntryDao;
	}
	
	public List<PriceBookEntry> findAll () {
		List<PriceBookEntry> priceBookEntrys = priceBookEntryDao.findAll();
		
		return priceBookEntrys;	
		
	}

	public ResultDTO addPriceBookEntry(PriceBookEntryDTO priceBookEntryDTO, RequestDTO requestDTO) {

		PriceBookEntry priceBookEntry = new PriceBookEntry();

		priceBookEntry.setPriceBookEntryId(priceBookEntryDTO.getPriceBookEntryId());

		priceBookEntry.setListPrice(priceBookEntryDTO.getListPrice());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		priceBookEntry = priceBookEntryDao.save(priceBookEntry);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PriceBookEntry> getAllPriceBookEntrys(Pageable pageable) {
		return priceBookEntryDao.findAll(pageable);
	}

	public Page<PriceBookEntry> getAllPriceBookEntrys(Specification<PriceBookEntry> spec, Pageable pageable) {
		return priceBookEntryDao.findAll(spec, pageable);
	}

	public ResponseEntity<PriceBookEntryPageDTO> getPriceBookEntrys(PriceBookEntrySearchDTO priceBookEntrySearchDTO) {
	
			Integer priceBookEntryId = priceBookEntrySearchDTO.getPriceBookEntryId(); 
  			String sortBy = priceBookEntrySearchDTO.getSortBy();
			String sortOrder = priceBookEntrySearchDTO.getSortOrder();
			String searchQuery = priceBookEntrySearchDTO.getSearchQuery();
			Integer page = priceBookEntrySearchDTO.getPage();
			Integer size = priceBookEntrySearchDTO.getSize();

	        Specification<PriceBookEntry> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, priceBookEntryId, "priceBookEntryId"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<PriceBookEntry> priceBookEntrys = this.getAllPriceBookEntrys(spec, pageable);
		
		//System.out.println(String.valueOf(priceBookEntrys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(priceBookEntrys.getTotalPages()));
		
		List<PriceBookEntry> priceBookEntrysList = priceBookEntrys.getContent();
		
		PriceBookEntryConvertCriteriaDTO convertCriteria = new PriceBookEntryConvertCriteriaDTO();
		List<PriceBookEntryDTO> priceBookEntryDTOs = this.convertPriceBookEntrysToPriceBookEntryDTOs(priceBookEntrysList,convertCriteria);
		
		PriceBookEntryPageDTO priceBookEntryPageDTO = new PriceBookEntryPageDTO();
		priceBookEntryPageDTO.setPriceBookEntrys(priceBookEntryDTOs);
		priceBookEntryPageDTO.setTotalElements(priceBookEntrys.getTotalElements());
		return ResponseEntity.ok(priceBookEntryPageDTO);
	}

	public List<PriceBookEntryDTO> convertPriceBookEntrysToPriceBookEntryDTOs(List<PriceBookEntry> priceBookEntrys, PriceBookEntryConvertCriteriaDTO convertCriteria) {
		
		List<PriceBookEntryDTO> priceBookEntryDTOs = new ArrayList<PriceBookEntryDTO>();
		
		for (PriceBookEntry priceBookEntry : priceBookEntrys) {
			priceBookEntryDTOs.add(convertPriceBookEntryToPriceBookEntryDTO(priceBookEntry,convertCriteria));
		}
		
		return priceBookEntryDTOs;

	}
	
	public PriceBookEntryDTO convertPriceBookEntryToPriceBookEntryDTO(PriceBookEntry priceBookEntry, PriceBookEntryConvertCriteriaDTO convertCriteria) {
		
		PriceBookEntryDTO priceBookEntryDTO = new PriceBookEntryDTO();

		priceBookEntryDTO.setPriceBookEntryId(priceBookEntry.getPriceBookEntryId());

		priceBookEntryDTO.setListPrice(priceBookEntry.getListPrice());
		
		return priceBookEntryDTO;
	}

	public ResultDTO updatePriceBookEntry(PriceBookEntryDTO priceBookEntryDTO, RequestDTO requestDTO) {
		
		PriceBookEntry priceBookEntry = priceBookEntryDao.getById(priceBookEntryDTO.getPriceBookEntryId());
		
		priceBookEntry.setPriceBookEntryId(ControllerUtils.setValue(priceBookEntry.getPriceBookEntryId(), priceBookEntryDTO.getPriceBookEntryId()));
		
		priceBookEntry.setListPrice(ControllerUtils.setValue(priceBookEntry.getListPrice(), priceBookEntryDTO.getListPrice()));

        priceBookEntry = priceBookEntryDao.save(priceBookEntry);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PriceBookEntryDTO getPriceBookEntryDTOById(Integer priceBookEntryId) {
	
		PriceBookEntry priceBookEntry = priceBookEntryDao.getById(priceBookEntryId);
		
		PriceBookEntryConvertCriteriaDTO convertCriteria = new PriceBookEntryConvertCriteriaDTO();
		return(this.convertPriceBookEntryToPriceBookEntryDTO(priceBookEntry,convertCriteria));
	}
}
