package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.response.DashboardReportResponseDto;
import com.emranhss.GarmentsManagementSystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {


    private final ReportService reportService;

    @GetMapping("/dashboard")
    public DashboardReportResponseDto getDashboardReport() {

        return reportService.getDashboardReport();

    }
}
