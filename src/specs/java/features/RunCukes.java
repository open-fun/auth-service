package features;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import features.environment.DockerHelper;
import features.selenium.DriverBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by Stanislav on 04.03.17.
 */
@Slf4j
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber"}, tags = "~@ignore")
public class RunCukes {

  @BeforeClass
  public static void waitEnvironmentAndSetUpUrls() throws TimeoutException {
    if (isBlank(System.getProperty("remoteDriver"))) {
      System.setProperty("remoteDriver", "true");
    }
    if (isBlank(System.getProperty("gridURL"))) {
      String seleniumUrl = "http://" + DockerHelper.getIpWhenIsHealthy("authservice_selenium_") + ":4444/wd/hub";
      log.info("Defined Selenium URL: {}", seleniumUrl);
      System.setProperty("gridURL", seleniumUrl);
    }

    if (isBlank(System.getProperty("serviceURL"))) {
      String serviceUrl = "http://" + DockerHelper.getIp("authservice_auth_") + ":80";
      log.info("Defined Auth Service URL: {}", serviceUrl);
      System.setProperty("serviceURL", serviceUrl);
    }
    DriverBase.instantiateDriverObject();
  }

  @AfterClass
  public static void cleanUp() {
    DriverBase.closeDriverObjects();
  }

}