package features.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.net.URI;
import java.net.URISyntaxException;

import static features.selenium.DriverBase.getDriver;
import static org.junit.Assert.assertTrue;

/**
 * Created by Stanislav on 08.03.17.
 */
public class PageObject {
  private WebDriver driver = getDriver();

  public PageObject() throws Exception {
  }

  public void open(String url) {
    driver.get(url);
  }

  public void clickOn(String text) {
    driver.findElement(By.name(text)).click();
  }

  public void assertExistsQueryParameter(String parameterName) throws URISyntaxException {
    String url = driver.getCurrentUrl();
    String query = new URI(url).getQuery();
    boolean exists = query.contains(parameterName + "=");
    assertTrue("Current query is " + query, exists);
  }

  public String getQueryParameter(String parameterName) throws URISyntaxException {
    String url = driver.getCurrentUrl();
    String query = new URI(url).getQuery();
    String parameterPattern = parameterName + "=";
    if (query.contains(parameterPattern)) {
      String substring = query.substring(query.indexOf(parameterPattern) + parameterPattern.length());
      if (substring.contains("&")) {
        return substring.substring(0, substring.indexOf("&"));
      }
      return substring;
    }
    throw new IllegalStateException("Cannot access parameter " + parameterName + " from query " + query);
  }
}
