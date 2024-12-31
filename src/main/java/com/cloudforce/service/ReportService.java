package com.cloudforce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.cloudforce.domain.Report;
import com.cloudforce.dto.ReportDTO;
import com.cloudforce.dto.ReportSearchDTO;
import com.cloudforce.dto.ReportPageDTO;
import com.cloudforce.dto.ReportConvertCriteriaDTO;
import com.cloudforce.service.GenericService;
import com.cloudforce.dto.common.RequestDTO;
import com.cloudforce.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ReportService extends GenericService<Report, Integer> {

	List<Report> findAll();

	ResultDTO addReport(ReportDTO reportDTO, RequestDTO requestDTO);

	ResultDTO updateReport(ReportDTO reportDTO, RequestDTO requestDTO);

    Page<Report> getAllReports(Pageable pageable);

    Page<Report> getAllReports(Specification<Report> spec, Pageable pageable);

	ResponseEntity<ReportPageDTO> getReports(ReportSearchDTO reportSearchDTO);
	
	List<ReportDTO> convertReportsToReportDTOs(List<Report> reports, ReportConvertCriteriaDTO convertCriteria);

	ReportDTO getReportDTOById(Integer reportId);


}
