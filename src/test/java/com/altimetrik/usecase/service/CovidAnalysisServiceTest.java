package com.altimetrik.usecase.service;

import static com.altimetrik.usecase.model.Criteria.AVG_MONTHLY_TESTS_IN_APR_10XRATE;
import static com.altimetrik.usecase.model.Criteria.AVG_MONTHLY_TESTS_IN_MAY_10XRATE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.altimetrik.usecase.model.CovidTrackingDto;
import com.altimetrik.usecase.model.Criteria;
import com.altimetrik.usecase.model.DataAnalysisByCriteria;
import com.altimetrik.usecase.proxy.CovidTrackingProxy;

@RunWith(MockitoJUnitRunner.class)
public class CovidAnalysisServiceTest {

    @Mock
    private CovidTrackingProxy covidTrackingProxy;

    @InjectMocks
    private CovidAnalysisService service;

    private List<CovidTrackingDto> covidTrackingDtoList = new ArrayList<>();

    @Before
    public void setup() {
        when(covidTrackingProxy.getCovidTrackingData()).thenReturn(covidTrackingDtoList);
    }

    @Test
    public void testAverageTestsInJan() {
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 1, 5), 10, 15));
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 1, 20), 20, 40));

        List<DataAnalysisByCriteria> result = service.getCovidTrackingAnalysis();

        assertEquals(42, result.get(0).getNoOfTests());
    }

    @Test
    public void testAverageTestsInFeb() {
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 2, 5), 30, 30));
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 2, 5), 50, 100));

        List<DataAnalysisByCriteria> result = service.getCovidTrackingAnalysis();

        assertEquals(105, result.get(0).getNoOfTests());
    }

    @Test
    public void testAverageTestsInMar() {
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 3, 5), 75, 75));
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 3, 5), 110, 105));

        List<DataAnalysisByCriteria> result = service.getCovidTrackingAnalysis();

        assertEquals(182, result.get(0).getNoOfTests());
    }

    @Test
    public void testAverageTestsInApr() {
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 4, 5), 1005, 500));
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 4, 5), 4000, 1000));

        List<DataAnalysisByCriteria> result = service.getCovidTrackingAnalysis();

        assertEquals(3252, result.get(0).getNoOfTests());
    }

    @Test
    public void testAverageTestsInMay() {
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 4, 5), 1005, 500));
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 4, 5), 4000, 1000));

        List<DataAnalysisByCriteria> result = service.getCovidTrackingAnalysis();

        assertEquals(3252, result.get(0).getNoOfTests());
    }

    @Test
    public void testAverageTestsInAprIn10xRate() {
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 4, 5), 1005, 500));
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 4, 5), 4000, 1000));

        List<DataAnalysisByCriteria> results = service.getCovidTrackingAnalysis();

        DataAnalysisByCriteria result = results.stream().filter(data -> data.getCriteria().equalsIgnoreCase(
                AVG_MONTHLY_TESTS_IN_APR_10XRATE.toString())).findAny().orElse(null);

        assertEquals(3252, result.getNoOfTests());
    }

    @Test
    public void testAverageTestsInMayIn10xRate() {
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 4, 5), 1005, 500));
        covidTrackingDtoList.add(buildCovidTrackingDto(new LocalDate(2020, 4, 5), 4000, 1000));

        List<DataAnalysisByCriteria> results = service.getCovidTrackingAnalysis();

        DataAnalysisByCriteria result = results.stream().filter(data -> data.getCriteria().equalsIgnoreCase(
                AVG_MONTHLY_TESTS_IN_MAY_10XRATE.toString())).findAny().orElse(null);

        assertEquals(3252, result.getNoOfTests());

    }

    private CovidTrackingDto buildCovidTrackingDto(LocalDate localDate, int cdcLabs, int usPubHealthLabs) {
        CovidTrackingDto covidTrackingDto = new CovidTrackingDto();
        covidTrackingDto.setDateCollected(localDate);
        covidTrackingDto.setCdcLabs(cdcLabs);
        covidTrackingDto.setUsPubHealthLabs(usPubHealthLabs);
        covidTrackingDto.setDailyTotal(cdcLabs + usPubHealthLabs);

        return covidTrackingDto;
    }

}
