package features.stepdefs;

import cucumber.api.java.After;
import features.selenium.DriverBase;

/**
 * Created by Stanislav on 08.03.17.
 */
public class Hooks {

  @After
  public static void cleanUp() throws Exception {
    DriverBase.clearCookies();
  }
}
