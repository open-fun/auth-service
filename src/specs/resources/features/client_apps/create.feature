Feature: Create client apps API

  Background: Clean up all data
    Given authorize with admin@treaba.me and admin
    And delete all users except admin%2540treaba%252Eme
    And delete resources clientApplications

  Scenario: Unable to create if not get authenticated
    Given not authorized
    When do JSON post /clientApplications with
    """
    {
      "clientId": "an_app",
      "secret":"secret"
    }
    """
    Then status code is Unauthorized


  Scenario: Create client application
    Given authorize with admin@treaba.me and admin
    When do JSON post /clientApplications with
    """
    {
      "clientId": "an_app",
      "resourceIds": ["spring-boot-application"],
      "authorizedGrantTypes": ["authorization_code", "implicit"],
      "authorityList": ["ROLE_CLIENT"],
      "scopes": ["read", "write"],
      "redirectUris": ["http://example.com"]
    }
    """
    Then status code is Created
    And have parameter resourceIds
    #And parameter clientId equals to an_app
    #The clientId parameter goes as part of self reference url