package com.altimetrik.usecase.model;

public enum Criteria {

    AVG_MONTHLY_TESTS_IN_JAN("AvgMonthlyTestsInJan", 1),
    AVG_MONTHLY_TESTS_IN_FEB("AvgMonthlyTestsInFeb", 2),
    AVG_MONTHLY_TESTS_IN_MAR("AvgMonthlyTestsInMar", 3),
    AVG_MONTHLY_TESTS_IN_APR("AvgMonthlyTestsInApr", 4),
    AVG_MONTHLY_TESTS_IN_MAY("AvgMonthlyTestsInMay", 5),
    AVG_MONTHLY_TESTS_IN_APR_10XRATE("AvgMonthlyTestsInApr10xRate", 6),
    AVG_MONTHLY_TESTS_IN_MAY_10XRATE("AvgMonthlyTestsInMay10xRate", 7);

    private final String criteriaName;
    private final int priority;

    Criteria(String criteriaName, int priority){
        this.criteriaName = criteriaName;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public String getCriteriaName() {
        return criteriaName;
    }
}
