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
import com.cloudforce.dao.PriceBookDAO;
import com.cloudforce.domain.PriceBook;
import com.cloudforce.dto.PriceBookDTO;
import com.cloudforce.dto.PriceBookSearchDTO;
import com.cloudforce.dto.PriceBookPageDTO;
import com.cloudforce.dto.PriceBookConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.PriceBookService;
import com.cloudforce.util.ControllerUtils;

@Service
public class PriceBookServiceImpl extends GenericServiceImpl<PriceBook, Integer> implements PriceBookService {

    private final static Logger logger = LoggerFactory.getLogger(PriceBookServiceImpl.class);

	@Autowired
	PriceBookDAO priceBookDao;

	@Override
	public GenericDAO<PriceBook, Integer> getDAO() {
		return (GenericDAO<PriceBook, Integer>) priceBookDao;
	}
	
	public List<PriceBook> findAll () {
		List<PriceBook> priceBooks = priceBookDao.findAll();
		
		return priceBooks;	
		
	}

	public ResultDTO addPriceBook(PriceBookDTO priceBookDTO, RequestDTO requestDTO) {

		PriceBook priceBook = new PriceBook();

		priceBook.setPriceBookId(priceBookDTO.getPriceBookId());

		priceBook.setName(priceBookDTO.getName());

		priceBook.setDescription(priceBookDTO.getDescription());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		priceBook = priceBookDao.save(priceBook);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PriceBook> getAllPriceBooks(Pageable pageable) {
		return priceBookDao.findAll(pageable);
	}

	public Page<PriceBook> getAllPriceBooks(Specification<PriceBook> spec, Pageable pageable) {
		return priceBookDao.findAll(spec, pageable);
	}

	public ResponseEntity<PriceBookPageDTO> getPriceBooks(PriceBookSearchDTO priceBookSearchDTO) {
	
			Integer priceBookId = priceBookSearchDTO.getPriceBookId(); 
 			String name = priceBookSearchDTO.getName(); 
 			String description = priceBookSearchDTO.getDescription(); 
 			String sortBy = priceBookSearchDTO.getSortBy();
			String sortOrder = priceBookSearchDTO.getSortOrder();
			String searchQuery = priceBookSearchDTO.getSearchQuery();
			Integer page = priceBookSearchDTO.getPage();
			Integer size = priceBookSearchDTO.getSize();

	        Specification<PriceBook> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, priceBookId, "priceBookId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<PriceBook> priceBooks = this.getAllPriceBooks(spec, pageable);
		
		//System.out.println(String.valueOf(priceBooks.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(priceBooks.getTotalPages()));
		
		List<PriceBook> priceBooksList = priceBooks.getContent();
		
		PriceBookConvertCriteriaDTO convertCriteria = new PriceBookConvertCriteriaDTO();
		List<PriceBookDTO> priceBookDTOs = this.convertPriceBooksToPriceBookDTOs(priceBooksList,convertCriteria);
		
		PriceBookPageDTO priceBookPageDTO = new PriceBookPageDTO();
		priceBookPageDTO.setPriceBooks(priceBookDTOs);
		priceBookPageDTO.setTotalElements(priceBooks.getTotalElements());
		return ResponseEntity.ok(priceBookPageDTO);
	}

	public List<PriceBookDTO> convertPriceBooksToPriceBookDTOs(List<PriceBook> priceBooks, PriceBookConvertCriteriaDTO convertCriteria) {
		
		List<PriceBookDTO> priceBookDTOs = new ArrayList<PriceBookDTO>();
		
		for (PriceBook priceBook : priceBooks) {
			priceBookDTOs.add(convertPriceBookToPriceBookDTO(priceBook,convertCriteria));
		}
		
		return priceBookDTOs;

	}
	
	public PriceBookDTO convertPriceBookToPriceBookDTO(PriceBook priceBook, PriceBookConvertCriteriaDTO convertCriteria) {
		
		PriceBookDTO priceBookDTO = new PriceBookDTO();

		priceBookDTO.setPriceBookId(priceBook.getPriceBookId());

		priceBookDTO.setName(priceBook.getName());

		priceBookDTO.setDescription(priceBook.getDescription());
		
		return priceBookDTO;
	}

	public ResultDTO updatePriceBook(PriceBookDTO priceBookDTO, RequestDTO requestDTO) {
		
		PriceBook priceBook = priceBookDao.getById(priceBookDTO.getPriceBookId());
		
		priceBook.setPriceBookId(ControllerUtils.setValue(priceBook.getPriceBookId(), priceBookDTO.getPriceBookId()));
		
		priceBook.setName(ControllerUtils.setValue(priceBook.getName(), priceBookDTO.getName()));
		
		priceBook.setDescription(ControllerUtils.setValue(priceBook.getDescription(), priceBookDTO.getDescription()));

        priceBook = priceBookDao.save(priceBook);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PriceBookDTO getPriceBookDTOById(Integer priceBookId) {
	
		PriceBook priceBook = priceBookDao.getById(priceBookId);
		
		PriceBookConvertCriteriaDTO convertCriteria = new PriceBookConvertCriteriaDTO();
		return(this.convertPriceBookToPriceBookDTO(priceBook,convertCriteria));
	}
}
