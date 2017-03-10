@grant_implicit
Feature: OAuth considering grant type to be implicit


  Background: Clean up all data
    Given authorize with admin@treaba.me and admin
    And delete all users except admin%2540treaba%252Eme
    And delete resources clientApplications
    But do JSON post /clientApplications with
    """
    {
      "clientId": "normal_app",
      "resourceIds": ["spring-boot-application"],
      "authorizedGrantTypes": ["implicit"],
      "authorityList": ["ROLE_TRUSTED_CLIENT"],
      "scopes": ["read"],
      "redirectUris": ["http://example.com"]
    }
    """

  Scenario: Authorization code forbidden if user not authenticated
    When not authorized
    And do get /oauth/authorize?client_id=normal_app&response_type=token&scope=read
    Then status code is Unauthorized

  Scenario: Authorization code works if user is authenticated
    When authorize with admin@treaba.me and admin
    And do get /oauth/authorize?client_id=normal_app&response_type=token&scope=read
    Then status code is OK

  Scenario: Authorization code received as part of url after app gets authorized
    #need to be opened in browser
    When open in browser /oauth/authorize?client_id=normal_app&response_type=token&scope=read and authorize with admin@treaba.me and admin
    And click on "authorize"
    Then browser url must have parameter access_token
    And browser url must have parameter token_type
    And browser url must have parameter expires_in