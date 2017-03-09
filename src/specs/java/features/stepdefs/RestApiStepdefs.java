package features.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import features.RestObject;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Stanislav on 04.03.17.
 */
@Slf4j
public class RestApiStepdefs extends BaseStepdefs {
  private static final String USER_BODY_PATTERN = "{ \"email\": \"user_%s@treaba.me\", \"password\":\"password\" }";
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
    restObject.param(paramName, tryProcess(paramValue));
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

  @When("^authorize without password using (.+)$")
  public void authorizeWithoutPasswordUsing(String username) throws Throwable {
    restObject.basicAuth(username, "");
  }

  @When("^do (.+) post (.+) with$")
  public void doPost(String contentType, String path, String body) throws Throwable {
    restObject.doPost(contentType, body, computePath(path));
  }

  @When("^do (.+) put (.+) with$")
  public void doPut(String contentType, String path, String body) throws Throwable {
    restObject.doPut(contentType, body, computePath(path));
  }

  @Given("^delete all users except (.+)$")
  public void deleteAllUsersExcept(String userToExcept) throws Throwable {
    List<String> items = restObject.list(computePath("/users"), "_embedded.userResources._links.self.href");
    if (items != null)
      items.stream()
          .filter(item -> !item.endsWith(userToExcept))
          .forEach(restObject::doDelete);
  }

  @And("^have (\\d+) more users$")
  public void haveMoreUsers(int usersCount) throws Throwable {
    for (int i = 0; i < usersCount; i++) {
      doPost("JSON", "/users", String.format(USER_BODY_PATTERN, System.currentTimeMillis()));
    }
  }

  @And("^delete resources (.+)$")
  public void deleteAll(String resource) throws Throwable {
    List<String> items = restObject.list(computePath("/" + resource), "_embedded." + resource + "._links.self.href");
    if (items != null)
      items.forEach(restObject::doDelete);
  }
}
