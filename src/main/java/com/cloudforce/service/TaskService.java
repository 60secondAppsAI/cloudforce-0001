package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.Task;
import com.cloudforce.dto.TaskDTO;
import com.cloudforce.dto.TaskSearchDTO;
import com.cloudforce.dto.TaskPageDTO;
import com.cloudforce.dto.TaskConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TaskService extends GenericService<Task, Integer> {

	List<Task> findAll();

	ResultDTO addTask(TaskDTO taskDTO, RequestDTO requestDTO);

	ResultDTO updateTask(TaskDTO taskDTO, RequestDTO requestDTO);

    Page<Task> getAllTasks(Pageable pageable);

    Page<Task> getAllTasks(Specification<Task> spec, Pageable pageable);

	ResponseEntity<TaskPageDTO> getTasks(TaskSearchDTO taskSearchDTO);
	
	List<TaskDTO> convertTasksToTaskDTOs(List<Task> tasks, TaskConvertCriteriaDTO convertCriteria);

	TaskDTO getTaskDTOById(Integer taskId);


}
