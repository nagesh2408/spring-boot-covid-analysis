package com.altimetrik.usecase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.usecase.model.CovidTrackingDto;
import com.altimetrik.usecase.model.DataAnalysisByCriteria;
import com.altimetrik.usecase.service.CovidAnalysisService;

import io.swagger.annotations.ApiOperation;

@RestController
public class CovidAnalysisRestController {

    @Autowired
    private CovidAnalysisService service;

    @GetMapping("/covidanalysis")
    @ApiOperation(tags = "Covid Data Analysis", value = "Covid Data Analysis")
    public List<DataAnalysisByCriteria> getCovidTrackingAnalysis(){
        return service.getCovidTrackingAnalysis();
    }
}
