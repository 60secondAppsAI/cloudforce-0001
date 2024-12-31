package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.LoginHistory;
import com.cloudforce.dto.LoginHistoryDTO;
import com.cloudforce.dto.LoginHistorySearchDTO;
import com.cloudforce.dto.LoginHistoryPageDTO;
import com.cloudforce.dto.LoginHistoryConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface LoginHistoryService extends GenericService<LoginHistory, Integer> {

	List<LoginHistory> findAll();

	ResultDTO addLoginHistory(LoginHistoryDTO loginHistoryDTO, RequestDTO requestDTO);

	ResultDTO updateLoginHistory(LoginHistoryDTO loginHistoryDTO, RequestDTO requestDTO);

    Page<LoginHistory> getAllLoginHistorys(Pageable pageable);

    Page<LoginHistory> getAllLoginHistorys(Specification<LoginHistory> spec, Pageable pageable);

	ResponseEntity<LoginHistoryPageDTO> getLoginHistorys(LoginHistorySearchDTO loginHistorySearchDTO);
	
	List<LoginHistoryDTO> convertLoginHistorysToLoginHistoryDTOs(List<LoginHistory> loginHistorys, LoginHistoryConvertCriteriaDTO convertCriteria);

	LoginHistoryDTO getLoginHistoryDTOById(Integer loginHistoryId);


}
