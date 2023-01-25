package com.qa.automation.tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"target/parallel/features/[CUCABLE:FEATURE].feature"},
        glue = {"com.qa.automation.api.stepDefinations"},
        plugin = {"pretty", "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"})


public class CucableJavaTemplate {
}
