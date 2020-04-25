package com.altimetrik.usecase.service;

import static com.altimetrik.usecase.model.Criteria.AVG_MONTHLY_TESTS_IN_APR;
import static com.altimetrik.usecase.model.Criteria.AVG_MONTHLY_TESTS_IN_APR_10XRATE;
import static com.altimetrik.usecase.model.Criteria.AVG_MONTHLY_TESTS_IN_FEB;
import static com.altimetrik.usecase.model.Criteria.AVG_MONTHLY_TESTS_IN_JAN;
import static com.altimetrik.usecase.model.Criteria.AVG_MONTHLY_TESTS_IN_MAR;
import static com.altimetrik.usecase.model.Criteria.AVG_MONTHLY_TESTS_IN_MAY;
import static com.altimetrik.usecase.model.Criteria.AVG_MONTHLY_TESTS_IN_MAY_10XRATE;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altimetrik.usecase.model.CovidTrackingDto;
import com.altimetrik.usecase.model.Criteria;
import com.altimetrik.usecase.model.DataAnalysisByCriteria;
import com.altimetrik.usecase.proxy.CovidTrackingProxy;

@Service
public class CovidAnalysisService {

    @Autowired
    private CovidTrackingProxy covidTrackingProxy;

    public List<DataAnalysisByCriteria> getCovidTrackingAnalysis() {
        List<CovidTrackingDto> trackingDtos = covidTrackingProxy.getCovidTrackingData();
        Map<Integer, List<CovidTrackingDto>> monthAndDataMap =
                trackingDtos.stream().collect(groupingBy(data -> data.getDateCollected().getMonthOfYear()));
        List<DataAnalysisByCriteria> criteriaList = new ArrayList<>();

        buildAvgTests(monthAndDataMap, Integer.valueOf(1), criteriaList, AVG_MONTHLY_TESTS_IN_JAN);
        buildAvgTests(monthAndDataMap, Integer.valueOf(2), criteriaList, AVG_MONTHLY_TESTS_IN_FEB);
        buildAvgTests(monthAndDataMap, Integer.valueOf(3), criteriaList, AVG_MONTHLY_TESTS_IN_MAR);
        buildAvgTests(monthAndDataMap, Integer.valueOf(4), criteriaList, AVG_MONTHLY_TESTS_IN_APR);
        buildAvgTests(monthAndDataMap, Integer.valueOf(4), criteriaList, AVG_MONTHLY_TESTS_IN_MAY);
        buildAvgTestsInAprWith10xRate(monthAndDataMap, Integer.valueOf(4), criteriaList,
                                      AVG_MONTHLY_TESTS_IN_APR_10XRATE);
        buildAvgTestsInMayWith10xRate(monthAndDataMap, Integer.valueOf(4), criteriaList,
                                      AVG_MONTHLY_TESTS_IN_MAY_10XRATE);

        criteriaList.sort(Comparator.comparing(DataAnalysisByCriteria::getPriority));

        return criteriaList;
    }

    private void buildAvgTestsInMayWith10xRate(Map<Integer, List<CovidTrackingDto>> monthAndDataMap, Integer key,
                                               List<DataAnalysisByCriteria> criteriaList, Criteria criteria) {
        if (!monthAndDataMap.containsKey(key)) {
            return;
        }

        List<CovidTrackingDto> covidTrackingDtos = monthAndDataMap.get(key);
        DataAnalysisByCriteria dataAnalysisByCriteria = new DataAnalysisByCriteria();
        int sum = covidTrackingDtos.stream().collect(summingInt(CovidTrackingDto::getDailyTotal));
        int average = sum / covidTrackingDtos.size();
        int total = (average * 31 * 10);

        dataAnalysisByCriteria.setCriteria(criteria.getCriteriaName());
        dataAnalysisByCriteria.setNoOfTests(total / 31);
        dataAnalysisByCriteria.setPriority(criteria.getPriority());

        criteriaList.add(dataAnalysisByCriteria);
    }

    private void buildAvgTestsInAprWith10xRate(Map<Integer, List<CovidTrackingDto>> monthAndDataMap, Integer key,
                                               List<DataAnalysisByCriteria> criteriaList, Criteria criteria) {
        if (!monthAndDataMap.containsKey(key)) {
            return;
        }
        List<CovidTrackingDto> covidTrackingDtos = monthAndDataMap.get(key);
        DataAnalysisByCriteria dataAnalysisByCriteria = new DataAnalysisByCriteria();
        int sum = covidTrackingDtos.stream().collect(summingInt(CovidTrackingDto::getDailyTotal));
        int average = sum / covidTrackingDtos.size();
        int noOfDaysToCalculate = 30 - covidTrackingDtos.size();
        int total = sum + (average * noOfDaysToCalculate * 10);

        dataAnalysisByCriteria.setCriteria(criteria.getCriteriaName());
        dataAnalysisByCriteria.setNoOfTests(total / 30);
        dataAnalysisByCriteria.setPriority(criteria.getPriority());

        criteriaList.add(dataAnalysisByCriteria);
    }

    private void buildAvgTests(Map<Integer, List<CovidTrackingDto>> monthAndDataMap, Integer key,
                               List<DataAnalysisByCriteria> criteriaList, Criteria criteria) {
        if (!monthAndDataMap.containsKey(key)) {
            return;
        }

        List<CovidTrackingDto> covidTrackingDtos = monthAndDataMap.get(key);
        DataAnalysisByCriteria dataAnalysisByCriteria = new DataAnalysisByCriteria();
        int sum = covidTrackingDtos.stream().collect(summingInt(CovidTrackingDto::getDailyTotal));

        dataAnalysisByCriteria.setCriteria(criteria.getCriteriaName());
        dataAnalysisByCriteria.setNoOfTests(sum / covidTrackingDtos.size());
        dataAnalysisByCriteria.setPriority(criteria.getPriority());

        criteriaList.add(dataAnalysisByCriteria);
    }
}
