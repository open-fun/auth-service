package features.stepdefs;

import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav on 08.03.17.
 */
@Slf4j
public abstract class BaseStepdefs {

  private String baseUrl;
  private static ThreadLocal<Map<String, String>> valuesMap = new ThreadLocal<>();

  @Before
  public void setUp() throws Exception {
    baseUrl = System.getProperty("serviceURL");
    valuesMap.set(new HashMap<>());
  }

  protected String computePath(String path) {
    return baseUrl + path;
  }

  protected void keep(String parameterName, String value) {
    log.info("Keeping {} with value {}", parameterName, value);
    valuesMap.get().put(parameterName, value);
  }

  protected String get(String parameterName) {
    log.info("Trying get value for: {}", parameterName);
    return valuesMap.get().get(parameterName);
  }

  protected String tryProcess(String paramValue) {
    if (paramValue.startsWith("%") && paramValue.endsWith("%") && paramValue.length() > 2) {
      String value = get(paramValue.substring(1, paramValue.length() - 1));
      log.info("Was computed {} to {}", paramValue, value);
      return value;
    }
    return paramValue;
  }

}
