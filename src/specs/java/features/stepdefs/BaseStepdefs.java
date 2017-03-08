package features.stepdefs;

import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Stanislav on 08.03.17.
 */
@Slf4j
public abstract class BaseStepdefs {

  private String baseUrl;

  @Before
  public void setUp() throws Exception {
    baseUrl = System.getProperty("serviceURL");
  }


  protected String computePath(String path) {
    return baseUrl + path;
  }

}
