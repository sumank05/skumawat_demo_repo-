package com.qa.automation.tests;

import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/resources/features/HrOne_Account.feature"},
		glue={"com.qa.automation.api.stepDefinations"},
			plugin = { "pretty","html:target/cucumber-report.html","junit:target/cucumber-reports-xml/Cucumber.xml","json:target/json_output/cucumber.json"},
		monochrome = true)
public class CucumberRunnerTest {

}

