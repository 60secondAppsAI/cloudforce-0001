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
import com.cloudforce.dao.DashboardDAO;
import com.cloudforce.domain.Dashboard;
import com.cloudforce.dto.DashboardDTO;
import com.cloudforce.dto.DashboardSearchDTO;
import com.cloudforce.dto.DashboardPageDTO;
import com.cloudforce.dto.DashboardConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.DashboardService;
import com.cloudforce.util.ControllerUtils;

@Service
public class DashboardServiceImpl extends GenericServiceImpl<Dashboard, Integer> implements DashboardService {

    private final static Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);

	@Autowired
	DashboardDAO dashboardDao;

	@Override
	public GenericDAO<Dashboard, Integer> getDAO() {
		return (GenericDAO<Dashboard, Integer>) dashboardDao;
	}
	
	public List<Dashboard> findAll () {
		List<Dashboard> dashboards = dashboardDao.findAll();
		
		return dashboards;	
		
	}

	public ResultDTO addDashboard(DashboardDTO dashboardDTO, RequestDTO requestDTO) {

		Dashboard dashboard = new Dashboard();

		dashboard.setDashboardId(dashboardDTO.getDashboardId());

		dashboard.setName(dashboardDTO.getName());

		dashboard.setDescription(dashboardDTO.getDescription());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		dashboard = dashboardDao.save(dashboard);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Dashboard> getAllDashboards(Pageable pageable) {
		return dashboardDao.findAll(pageable);
	}

	public Page<Dashboard> getAllDashboards(Specification<Dashboard> spec, Pageable pageable) {
		return dashboardDao.findAll(spec, pageable);
	}

	public ResponseEntity<DashboardPageDTO> getDashboards(DashboardSearchDTO dashboardSearchDTO) {
	
			Integer dashboardId = dashboardSearchDTO.getDashboardId(); 
 			String name = dashboardSearchDTO.getName(); 
 			String description = dashboardSearchDTO.getDescription(); 
 			String sortBy = dashboardSearchDTO.getSortBy();
			String sortOrder = dashboardSearchDTO.getSortOrder();
			String searchQuery = dashboardSearchDTO.getSearchQuery();
			Integer page = dashboardSearchDTO.getPage();
			Integer size = dashboardSearchDTO.getSize();

	        Specification<Dashboard> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, dashboardId, "dashboardId"); 
			
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

		Page<Dashboard> dashboards = this.getAllDashboards(spec, pageable);
		
		//System.out.println(String.valueOf(dashboards.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(dashboards.getTotalPages()));
		
		List<Dashboard> dashboardsList = dashboards.getContent();
		
		DashboardConvertCriteriaDTO convertCriteria = new DashboardConvertCriteriaDTO();
		List<DashboardDTO> dashboardDTOs = this.convertDashboardsToDashboardDTOs(dashboardsList,convertCriteria);
		
		DashboardPageDTO dashboardPageDTO = new DashboardPageDTO();
		dashboardPageDTO.setDashboards(dashboardDTOs);
		dashboardPageDTO.setTotalElements(dashboards.getTotalElements());
		return ResponseEntity.ok(dashboardPageDTO);
	}

	public List<DashboardDTO> convertDashboardsToDashboardDTOs(List<Dashboard> dashboards, DashboardConvertCriteriaDTO convertCriteria) {
		
		List<DashboardDTO> dashboardDTOs = new ArrayList<DashboardDTO>();
		
		for (Dashboard dashboard : dashboards) {
			dashboardDTOs.add(convertDashboardToDashboardDTO(dashboard,convertCriteria));
		}
		
		return dashboardDTOs;

	}
	
	public DashboardDTO convertDashboardToDashboardDTO(Dashboard dashboard, DashboardConvertCriteriaDTO convertCriteria) {
		
		DashboardDTO dashboardDTO = new DashboardDTO();

		dashboardDTO.setDashboardId(dashboard.getDashboardId());

		dashboardDTO.setName(dashboard.getName());

		dashboardDTO.setDescription(dashboard.getDescription());
		
		return dashboardDTO;
	}

	public ResultDTO updateDashboard(DashboardDTO dashboardDTO, RequestDTO requestDTO) {
		
		Dashboard dashboard = dashboardDao.getById(dashboardDTO.getDashboardId());
		
		dashboard.setDashboardId(ControllerUtils.setValue(dashboard.getDashboardId(), dashboardDTO.getDashboardId()));
		
		dashboard.setName(ControllerUtils.setValue(dashboard.getName(), dashboardDTO.getName()));
		
		dashboard.setDescription(ControllerUtils.setValue(dashboard.getDescription(), dashboardDTO.getDescription()));

        dashboard = dashboardDao.save(dashboard);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public DashboardDTO getDashboardDTOById(Integer dashboardId) {
	
		Dashboard dashboard = dashboardDao.getById(dashboardId);
		
		DashboardConvertCriteriaDTO convertCriteria = new DashboardConvertCriteriaDTO();
		return(this.convertDashboardToDashboardDTO(dashboard,convertCriteria));
	}
}
