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
import com.cloudforce.dao.TaskDAO;
import com.cloudforce.domain.Task;
import com.cloudforce.dto.TaskDTO;
import com.cloudforce.dto.TaskSearchDTO;
import com.cloudforce.dto.TaskPageDTO;
import com.cloudforce.dto.TaskConvertCriteriaDTO;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import com.cloudforce.service.TaskService;
import com.cloudforce.util.ControllerUtils;

@Service
public class TaskServiceImpl extends GenericServiceImpl<Task, Integer> implements TaskService {

    private final static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	TaskDAO taskDao;

	@Override
	public GenericDAO<Task, Integer> getDAO() {
		return (GenericDAO<Task, Integer>) taskDao;
	}
	
	public List<Task> findAll () {
		List<Task> tasks = taskDao.findAll();
		
		return tasks;	
		
	}

	public ResultDTO addTask(TaskDTO taskDTO, RequestDTO requestDTO) {

		Task task = new Task();

		task.setTaskId(taskDTO.getTaskId());

		task.setSubject(taskDTO.getSubject());

		task.setDueDate(taskDTO.getDueDate());

		task.setPriority(taskDTO.getPriority());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		task = taskDao.save(task);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Task> getAllTasks(Pageable pageable) {
		return taskDao.findAll(pageable);
	}

	public Page<Task> getAllTasks(Specification<Task> spec, Pageable pageable) {
		return taskDao.findAll(spec, pageable);
	}

	public ResponseEntity<TaskPageDTO> getTasks(TaskSearchDTO taskSearchDTO) {
	
			Integer taskId = taskSearchDTO.getTaskId(); 
 			String subject = taskSearchDTO.getSubject(); 
   			String priority = taskSearchDTO.getPriority(); 
 			String sortBy = taskSearchDTO.getSortBy();
			String sortOrder = taskSearchDTO.getSortOrder();
			String searchQuery = taskSearchDTO.getSearchQuery();
			Integer page = taskSearchDTO.getPage();
			Integer size = taskSearchDTO.getSize();

	        Specification<Task> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, taskId, "taskId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, subject, "subject"); 
			
 			
			spec = ControllerUtils.andIfNecessary(spec, priority, "priority"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("subject")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("priority")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Task> tasks = this.getAllTasks(spec, pageable);
		
		//System.out.println(String.valueOf(tasks.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(tasks.getTotalPages()));
		
		List<Task> tasksList = tasks.getContent();
		
		TaskConvertCriteriaDTO convertCriteria = new TaskConvertCriteriaDTO();
		List<TaskDTO> taskDTOs = this.convertTasksToTaskDTOs(tasksList,convertCriteria);
		
		TaskPageDTO taskPageDTO = new TaskPageDTO();
		taskPageDTO.setTasks(taskDTOs);
		taskPageDTO.setTotalElements(tasks.getTotalElements());
		return ResponseEntity.ok(taskPageDTO);
	}

	public List<TaskDTO> convertTasksToTaskDTOs(List<Task> tasks, TaskConvertCriteriaDTO convertCriteria) {
		
		List<TaskDTO> taskDTOs = new ArrayList<TaskDTO>();
		
		for (Task task : tasks) {
			taskDTOs.add(convertTaskToTaskDTO(task,convertCriteria));
		}
		
		return taskDTOs;

	}
	
	public TaskDTO convertTaskToTaskDTO(Task task, TaskConvertCriteriaDTO convertCriteria) {
		
		TaskDTO taskDTO = new TaskDTO();

		taskDTO.setTaskId(task.getTaskId());

		taskDTO.setSubject(task.getSubject());

		taskDTO.setDueDate(task.getDueDate());

		taskDTO.setPriority(task.getPriority());
		
		return taskDTO;
	}

	public ResultDTO updateTask(TaskDTO taskDTO, RequestDTO requestDTO) {
		
		Task task = taskDao.getById(taskDTO.getTaskId());
		
		task.setTaskId(ControllerUtils.setValue(task.getTaskId(), taskDTO.getTaskId()));
		
		task.setSubject(ControllerUtils.setValue(task.getSubject(), taskDTO.getSubject()));
		
		task.setDueDate(ControllerUtils.setValue(task.getDueDate(), taskDTO.getDueDate()));
		
		task.setPriority(ControllerUtils.setValue(task.getPriority(), taskDTO.getPriority()));

        task = taskDao.save(task);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public TaskDTO getTaskDTOById(Integer taskId) {
	
		Task task = taskDao.getById(taskId);
		
		TaskConvertCriteriaDTO convertCriteria = new TaskConvertCriteriaDTO();
		return(this.convertTaskToTaskDTO(task,convertCriteria));
	}
}
