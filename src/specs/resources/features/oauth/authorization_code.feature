@grant_authorization_code
Feature: OAuth considering grant type to be Authorization code


  Background: Clean up all data
    Given authorize with admin@treaba.me and admin
    And delete all users except admin%2540treaba%252Eme
    And delete resources clientApplications
    But do JSON post /clientApplications with
    """
    {
      "clientId": "normal_app",
      "resourceIds": ["spring-boot-application"],
      "authorizedGrantTypes": ["authorization_code"],
      "authorityList": ["ROLE_TRUSTED_CLIENT"],
      "scopes": ["read", "write"],
      "redirectUris": ["http://example.com"]
    }
    """

  Scenario: Authorization code forbidden if user not authenticated
    When not authorized
    And do get /oauth/authorize?client_id=normal_app&response_type=code
    Then status code is Unauthorized

  Scenario: Authorization code works if user is authenticated
    When authorize with admin@treaba.me and admin
    And do get /oauth/authorize?client_id=normal_app&response_type=code
    Then status code is OK

  Scenario: Authorization code received as part of url after app gets authorized
    #need to be opened in browser
    When open in browser /oauth/authorize?client_id=normal_app&response_type=code and authorize with admin@treaba.me and admin
    And click on "authorize"
    Then browser url must have parameter code

  Scenario: With authorization code can be accessed oauth token
    #need to be opened in browser
    Given open in browser /oauth/authorize?client_id=normal_app&response_type=code and authorize with admin@treaba.me and admin
    But click on "authorize"
    And keep browser url parameter code
    When authorize without password using normal_app
    And set parameter grant_type to authorization_code
    And set parameter client_id to normal_app
    And set parameter code to %code%
    And do post /oauth/token
    Then status code is OK
    And have parameter access_token
    And have parameter refresh_token
    And have parameter scope
    And have parameter expires_in
    And parameter token_type equals to bearer



