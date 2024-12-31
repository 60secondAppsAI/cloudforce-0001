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
import com.cloudforce.dao.LoginHistoryDAO;
import com.cloudforce.domain.LoginHistory;
import com.cloudforce.dto.LoginHistoryDTO;
import com.cloudforce.dto.LoginHistorySearchDTO;
import com.cloudforce.dto.LoginHistoryPageDTO;
import com.cloudforce.dto.LoginHistoryConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.LoginHistoryService;
import com.cloudforce.util.ControllerUtils;

@Service
public class LoginHistoryServiceImpl extends GenericServiceImpl<LoginHistory, Integer> implements LoginHistoryService {

    private final static Logger logger = LoggerFactory.getLogger(LoginHistoryServiceImpl.class);

	@Autowired
	LoginHistoryDAO loginHistoryDao;

	@Override
	public GenericDAO<LoginHistory, Integer> getDAO() {
		return (GenericDAO<LoginHistory, Integer>) loginHistoryDao;
	}
	
	public List<LoginHistory> findAll () {
		List<LoginHistory> loginHistorys = loginHistoryDao.findAll();
		
		return loginHistorys;	
		
	}

	public ResultDTO addLoginHistory(LoginHistoryDTO loginHistoryDTO, RequestDTO requestDTO) {

		LoginHistory loginHistory = new LoginHistory();

		loginHistory.setLoginHistoryId(loginHistoryDTO.getLoginHistoryId());

		loginHistory.setLoginTime(loginHistoryDTO.getLoginTime());

		loginHistory.setIpAddress(loginHistoryDTO.getIpAddress());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		loginHistory = loginHistoryDao.save(loginHistory);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<LoginHistory> getAllLoginHistorys(Pageable pageable) {
		return loginHistoryDao.findAll(pageable);
	}

	public Page<LoginHistory> getAllLoginHistorys(Specification<LoginHistory> spec, Pageable pageable) {
		return loginHistoryDao.findAll(spec, pageable);
	}

	public ResponseEntity<LoginHistoryPageDTO> getLoginHistorys(LoginHistorySearchDTO loginHistorySearchDTO) {
	
			Integer loginHistoryId = loginHistorySearchDTO.getLoginHistoryId(); 
   			String ipAddress = loginHistorySearchDTO.getIpAddress(); 
 			String sortBy = loginHistorySearchDTO.getSortBy();
			String sortOrder = loginHistorySearchDTO.getSortOrder();
			String searchQuery = loginHistorySearchDTO.getSearchQuery();
			Integer page = loginHistorySearchDTO.getPage();
			Integer size = loginHistorySearchDTO.getSize();

	        Specification<LoginHistory> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, loginHistoryId, "loginHistoryId"); 
			
 			
			spec = ControllerUtils.andIfNecessary(spec, ipAddress, "ipAddress"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("ipAddress")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<LoginHistory> loginHistorys = this.getAllLoginHistorys(spec, pageable);
		
		//System.out.println(String.valueOf(loginHistorys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(loginHistorys.getTotalPages()));
		
		List<LoginHistory> loginHistorysList = loginHistorys.getContent();
		
		LoginHistoryConvertCriteriaDTO convertCriteria = new LoginHistoryConvertCriteriaDTO();
		List<LoginHistoryDTO> loginHistoryDTOs = this.convertLoginHistorysToLoginHistoryDTOs(loginHistorysList,convertCriteria);
		
		LoginHistoryPageDTO loginHistoryPageDTO = new LoginHistoryPageDTO();
		loginHistoryPageDTO.setLoginHistorys(loginHistoryDTOs);
		loginHistoryPageDTO.setTotalElements(loginHistorys.getTotalElements());
		return ResponseEntity.ok(loginHistoryPageDTO);
	}

	public List<LoginHistoryDTO> convertLoginHistorysToLoginHistoryDTOs(List<LoginHistory> loginHistorys, LoginHistoryConvertCriteriaDTO convertCriteria) {
		
		List<LoginHistoryDTO> loginHistoryDTOs = new ArrayList<LoginHistoryDTO>();
		
		for (LoginHistory loginHistory : loginHistorys) {
			loginHistoryDTOs.add(convertLoginHistoryToLoginHistoryDTO(loginHistory,convertCriteria));
		}
		
		return loginHistoryDTOs;

	}
	
	public LoginHistoryDTO convertLoginHistoryToLoginHistoryDTO(LoginHistory loginHistory, LoginHistoryConvertCriteriaDTO convertCriteria) {
		
		LoginHistoryDTO loginHistoryDTO = new LoginHistoryDTO();

		loginHistoryDTO.setLoginHistoryId(loginHistory.getLoginHistoryId());

		loginHistoryDTO.setLoginTime(loginHistory.getLoginTime());

		loginHistoryDTO.setIpAddress(loginHistory.getIpAddress());
		
		return loginHistoryDTO;
	}

	public ResultDTO updateLoginHistory(LoginHistoryDTO loginHistoryDTO, RequestDTO requestDTO) {
		
		LoginHistory loginHistory = loginHistoryDao.getById(loginHistoryDTO.getLoginHistoryId());
		
		loginHistory.setLoginHistoryId(ControllerUtils.setValue(loginHistory.getLoginHistoryId(), loginHistoryDTO.getLoginHistoryId()));
		
		loginHistory.setLoginTime(ControllerUtils.setValue(loginHistory.getLoginTime(), loginHistoryDTO.getLoginTime()));
		
		loginHistory.setIpAddress(ControllerUtils.setValue(loginHistory.getIpAddress(), loginHistoryDTO.getIpAddress()));

        loginHistory = loginHistoryDao.save(loginHistory);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public LoginHistoryDTO getLoginHistoryDTOById(Integer loginHistoryId) {
	
		LoginHistory loginHistory = loginHistoryDao.getById(loginHistoryId);
		
		LoginHistoryConvertCriteriaDTO convertCriteria = new LoginHistoryConvertCriteriaDTO();
		return(this.convertLoginHistoryToLoginHistoryDTO(loginHistory,convertCriteria));
	}
}
