package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.Activity;
import com.cloudforce.dto.ActivityDTO;
import com.cloudforce.dto.ActivitySearchDTO;
import com.cloudforce.dto.ActivityPageDTO;
import com.cloudforce.dto.ActivityConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ActivityService extends GenericService<Activity, Integer> {

	List<Activity> findAll();

	ResultDTO addActivity(ActivityDTO activityDTO, RequestDTO requestDTO);

	ResultDTO updateActivity(ActivityDTO activityDTO, RequestDTO requestDTO);

    Page<Activity> getAllActivitys(Pageable pageable);

    Page<Activity> getAllActivitys(Specification<Activity> spec, Pageable pageable);

	ResponseEntity<ActivityPageDTO> getActivitys(ActivitySearchDTO activitySearchDTO);
	
	List<ActivityDTO> convertActivitysToActivityDTOs(List<Activity> activitys, ActivityConvertCriteriaDTO convertCriteria);

	ActivityDTO getActivityDTOById(Integer activityId);


}
