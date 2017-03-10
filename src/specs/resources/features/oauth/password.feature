@grant_password
Feature: OAuth considering grant type to be password


  Background: Clean up all data
    Given authorize with admin@treaba.me and admin
    And delete all users except admin%2540treaba%252Eme
    And delete resources clientApplications


  Scenario: Authorize by password with secret
    Given  do JSON post /clientApplications with
    """
    {
      "clientId": "an_app",
      "secret": "password",
      "secretRequired": "true",
      "resourceIds": ["spring-boot-application"],
      "authorizedGrantTypes": ["password"],
      "authorityList": ["ROLE_TRUSTED_CLIENT"],
      "scopes": ["read", "write"],
      "redirectUris": ["http://example.com"]
    }
    """
    And status code is Created
    When authorize with an_app and password
    And set parameter grant_type to password
    And set parameter username to admin@treaba.me
    And set parameter password to admin
    And do post /oauth/token
    Then status code is OK
    And have parameter access_token
    And have parameter scope
    And have parameter jti
    And have parameter expires_in
    And parameter token_type equals to bearer

  Scenario: Authorize by password without any secret
    Given  do JSON post /clientApplications with
    """
    {
      "clientId": "another_app",
      "resourceIds": ["spring-boot-application"],
      "authorizedGrantTypes": ["password"],
      "authorityList": ["ROLE_TRUSTED_CLIENT"],
      "scopes": ["read", "write"],
      "redirectUris": ["http://example.com"]
    }
    """
    And status code is Created
    When authorize without password using another_app
    And set parameter grant_type to password
    And set parameter username to admin@treaba.me
    And set parameter password to admin
    And do post /oauth/token
    Then status code is OK
    And have parameter access_token
    And have parameter scope
    And have parameter jti
    And have parameter expires_in
    And parameter token_type equals to bearer