package com.altimetrik.usecase.model;

import org.joda.time.LocalDate;

import com.altimetrik.usecase.DateHandler;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class CovidTrackingDto {

    @JsonDeserialize(using = DateHandler.class )
    private LocalDate dateCollected;
    private Integer cdcLabs;
    private Integer usPubHealthLabs;
    private Integer dailyTotal;
    private Integer lag;

    public LocalDate getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(LocalDate dateCollected) {
        this.dateCollected = dateCollected;
    }

    public Integer getCdcLabs() {
        return cdcLabs;
    }

    public void setCdcLabs(Integer cdcLabs) {
        this.cdcLabs = cdcLabs;
    }

    public Integer getUsPubHealthLabs() {
        return usPubHealthLabs;
    }

    public void setUsPubHealthLabs(Integer usPubHealthLabs) {
        this.usPubHealthLabs = usPubHealthLabs;
    }

    public Integer getDailyTotal() {
        return dailyTotal;
    }

    public void setDailyTotal(Integer dailyTotal) {
        this.dailyTotal = dailyTotal;
    }

    public Integer getLag() {
        return lag;
    }

    public void setLag(Integer lag) {
        this.lag = lag;
    }
}
