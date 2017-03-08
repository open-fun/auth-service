package features.stepdefs;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import features.RestObject;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Stanislav on 04.03.17.
 */
@Slf4j
public class RestApiStepdefs extends BaseStepdefs {
  private RestObject restObject;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    restObject = new RestObject();
  }

  @When("^do get (.+)$")
  public void doGet(String path) {
    restObject.doGet(ContentType.JSON, computePath(path));
  }

  @Then("^result must contain (.+) parameter with value (.*)$")
  public void resultMustContainParameter(String parameterName, String value) throws Throwable {
    restObject.assertParameterValue(parameterName, value);
  }

  @Then("^status code is (.+)$")
  public void checkStatusCode(String statusCode) throws Throwable {
    restObject.assertStatusCode(statusCode);
  }

  @When("^authorize with (.+) and (.*)$")
  public void authorizeWith(String username, String password) throws Throwable {
    restObject.basicAuth(username, password);
  }

  @And("^set parameter (.*) to (.*)$")
  public void setParameter(String paramName, String paramValue) {
    restObject.param(paramName, paramValue);
  }

  @And("^do post (.*)$")
  public void doPost(String url) throws Throwable {
    restObject.doPost(computePath(url));
  }

  @And("^have parameter (.*)$")
  public void have(String parameter) throws Throwable {
    restObject.assertExistParameter(parameter);
  }

  @And("^parameter (.+) equals to (.+)$")
  public void parameterEqualsTo(String parameter, String value) throws Throwable {
    restObject.assertParameterValue(parameter, value);
  }

  @When("^not authorized$")
  public void notAuthorized() throws Throwable {
    restObject.resetAuthorization();
  }
}
