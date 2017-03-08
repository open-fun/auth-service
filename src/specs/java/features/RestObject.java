package features;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by stanislav on 08.03.17.
 */
public class RestObject {

  private RequestSpecification requestSpecification = given();
  private Response response;

  public void doGet(ContentType contentType, String path) {
    response = requestSpecification.accept(contentType).get(path);
  }

  public void assertParameterValue(String parameterName, String value) {
    response.then().body(parameterName, equalTo(value));
  }

  public void assertStatusCode(String statusCode) {
    HttpStatus httpStatus = HttpStatus.valueOf(statusCode.toUpperCase().replace(" ", "_"));
    response.then().statusCode(httpStatus.value());
  }

  public void basicAuth(String username, String password) {
    requestSpecification = given().auth().basic(username, password);
  }

  public void param(String paramName, String paramValue) {
    requestSpecification = requestSpecification.param(paramName, paramValue);
  }

  public void doPost(String path) {
    response = requestSpecification.post(path);
  }

  public void assertExistParameter(String parameter) {
    response.then().body(parameter, Matchers.anything());
  }

  public void resetAuthorization() {
    requestSpecification = given();
  }
}
