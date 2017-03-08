package features.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import features.selenium.PageObject;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Stanislav on 08.03.17.
 */
public class BrowserStepdefs extends BaseStepdefs {
  private PageObject pageObject;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    pageObject = new PageObject();
  }

  @When("^open in browser (.+) and authorize with (.+) and (.*)$")
  public void open(String url, String username, String password) {
    String computedUrl = computePath(url);
    computedUrl = computeAuthenticationUrl(username, password, computedUrl);
    pageObject.open(computedUrl);
  }

  private String computeAuthenticationUrl(String username, String password, String computedUrl) {
    computedUrl = computeAuthenticationUrl(username, password, computedUrl, "http://");
    computedUrl = computeAuthenticationUrl(username, password, computedUrl, "https://");
    return computedUrl;
  }

  private String computeAuthenticationUrl(String username, String password, String computedUrl, String prefix) {
    if (computedUrl.startsWith(prefix)) {
      computedUrl = StringUtils.replaceOnce(computedUrl, prefix, prefix + username + ":" + password + "@");
    }
    return computedUrl;
  }

  @And("^click on \"([^\"]*)\"$")
  public void clickOn(String text) throws Throwable {
    pageObject.clickOn(text);
  }

  @Then("^browser url must have parameter (.+)$")
  public void browserUrlMustHaveParameter(String parameterName) throws Throwable {
    pageObject.assertExistsQueryParameter(parameterName);
  }

  @And("^keep browser url parameter (.+)$")
  public void keepBrowserUrlParameter(String parameterName) throws Throwable {
    String value = pageObject.getQueryParameter(parameterName);
    keep(parameterName, value);
  }
}
