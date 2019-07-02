package ru.javawebinar.topjava.rules;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class MyJUnitStopWatch extends Stopwatch {
    private static void logInfo(Description description, String status, long nanos, String exception){
        String testName = description.getMethodName();
        if (exception.isEmpty())
            System.out.println(String.format("Test %s %s, spent %d milliseconds", testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
        else
            System.out.println(String.format("Exception - %s, test %s %s , spent %d milliseconds", exception, testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos, "");
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos, e.getMessage());
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos, e.getMessage());
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "succeeded", nanos, "");
    }
}
