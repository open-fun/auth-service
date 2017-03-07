Feature: All resources must be authorized using JWT


  Scenario: Restricted access if no auth details provided
    When do get /
    Then status code is Forbidden


  Scenario: Authorize by credentials
    When authorize with trusted-app and secret
    And set parameter grant_type to client_credentials
    And do post /oauth/token
    Then status code is OK
    And have parameter access_token
    And have parameter scope
    And have parameter expires_in
    And parameter token_type equals to bearer


  Scenario: Authorize an user
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