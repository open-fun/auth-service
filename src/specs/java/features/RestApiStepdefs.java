package features;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by stanislav on 04.03.17.
 */
@Slf4j
public class RestApiStepdefs {

  private RequestSpecification requestSpecification;
  private Response response;
  private String baseUrl;

  @Before
  public void setUp() throws TimeoutException {
    baseUrl = "http://" + DockerHelper.getIpWhenIsHealthy("authservice_auth_") + ":80";
    log.info("Service URL is: " + baseUrl);
    requestSpecification = given();
  }

  @When("^do get (.+)$")
  public void doGet(String path) {
    response = requestSpecification.accept(ContentType.JSON).get(computePath(path));
  }

  @Then("^result must contain (.+) parameter with value (.*)$")
  public void resultMustContainParameter(String parameterName, String value) throws Throwable {
    response.then().body(parameterName, equalTo(value));
  }

  private String computePath(String path) {
    return baseUrl + path;
  }
}
