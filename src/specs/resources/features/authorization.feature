Feature: All resources must be authorized using JWT


  Scenario: Restricted access if no auth details provided
    When do get /
    Then status code is Unauthorized


  Scenario: Authorize by credentials
    When authorize with trusted-app and secret
    And set parameter grant_type to client_credentials
    And do post /oauth/token
    Then status code is OK
    And have parameter access_token
    And have parameter scope
    And have parameter expires_in
    And parameter token_type equals to bearer


  Scenario: Authorize an user through client
    When authorize with trusted-app and secret
    And set parameter grant_type to password
    And set parameter username to admin@treaba.me
    And set parameter password to admin
    And do post /oauth/token
    Then status code is OK
    And have parameter access_token
    And have parameter scope
    And have parameter expires_in
    And parameter token_type equals to bearer

  @authorization_code
  Scenario: Authorization code forbidden if user not authenticated
    When not authorized
    And do get /oauth/authorize?client_id=normal_app&response_type=code&redirect_uri=example.com
    Then status code is Unauthorized

  @authorization_code
  Scenario: Authorization code works if user is authenticated
    When authorize with admin@treaba.me and admin
    And do get /oauth/authorize?client_id=normal_app&response_type=code&redirect_uri=http://example.com
    Then status code is OK

  @authorization_code @ignore
  Scenario: Authorization code received as part of url after app gets authorized
    #need to be opened in browser
    When open in browser /oauth/authorize?client_id=normal_app&response_type=code&redirect_uri=http://google.com
    And authorize with admin@treaba.me and admin
    And press "Authorize"
    Then redirected url must have parameter code

  @authorization_code @ignore
  Scenario: With authorization code can be accessed oauth token
    #need to be opened in browser
    When open in browser /oauth/authorize?client_id=normal_app&response_type=code&redirect_uri=http://google.com
    And authorize with admin@treaba.me and admin
    And press "Authorize"
    Then redirected url must have parameter code
    When authorize with normal_app and
    And set parameter grant_type to authorization_code
    And set parameter client_id to normal_app
    And set parameter redirect_uri to example.com
    And set parameter code to %code%
    Then status code is OK
    And have parameter access_token
    And have parameter refresh_token
    And have parameter scope
    And have parameter expires_in
    And parameter token_type equals to bearer



