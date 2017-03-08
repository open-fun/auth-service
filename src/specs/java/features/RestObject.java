package features;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Stanislav on 08.03.17.
 */
public class RestObject {

  private RequestSpecification requestSpecification = given();
  private Response response;

  public void doGet(ContentType contentType, String path) {
    response = requestSpecification.accept(contentType).get(path);
  }

  public void assertParameterValue(String parameterName, String value) {
    try {
      response.then().body(parameterName, anyOf(equalTo(value), equalTo(Integer.parseInt(value))));
    } catch (NumberFormatException ex) {
      response.then().body(parameterName, anyOf(equalTo(value)));
    }
  }

  public void assertStatusCode(String statusCode) {
    HttpStatus httpStatus = HttpStatus.valueOf(statusCode.toUpperCase().replace(" ", "_"));
    int code = httpStatus.value();
    assertStatusCode(code);
  }

  public void assertStatusCode(int code) {
    response.then().statusCode(code);
    response.then().log().all();
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

  public void doPost(String contentType, String body, String path) {
    response = requestSpecification
        .contentType(ContentType.valueOf(contentType.toUpperCase()))
        .body(body)
        .post(path);
  }

  public <T> List<T> list(String url, String jsonPath) {
    Response response = requestSpecification.get(url);
    response.then().log().body();
    return response.jsonPath().getList(jsonPath);
  }

  public void doDelete(String path) {
    response = requestSpecification.delete(path);
  }
}
