package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.Dashboard;
import com.cloudforce.dto.DashboardDTO;
import com.cloudforce.dto.DashboardSearchDTO;
import com.cloudforce.dto.DashboardPageDTO;
import com.cloudforce.dto.DashboardConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface DashboardService extends GenericService<Dashboard, Integer> {

	List<Dashboard> findAll();

	ResultDTO addDashboard(DashboardDTO dashboardDTO, RequestDTO requestDTO);

	ResultDTO updateDashboard(DashboardDTO dashboardDTO, RequestDTO requestDTO);

    Page<Dashboard> getAllDashboards(Pageable pageable);

    Page<Dashboard> getAllDashboards(Specification<Dashboard> spec, Pageable pageable);

	ResponseEntity<DashboardPageDTO> getDashboards(DashboardSearchDTO dashboardSearchDTO);
	
	List<DashboardDTO> convertDashboardsToDashboardDTOs(List<Dashboard> dashboards, DashboardConvertCriteriaDTO convertCriteria);

	DashboardDTO getDashboardDTOById(Integer dashboardId);


}
