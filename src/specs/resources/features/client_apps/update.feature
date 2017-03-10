Feature: Patch client application

  Background: Clean up all data
    Given authorize with admin@treaba.me and admin
    And delete all users except admin%2540treaba%252Eme
    And delete resources clientApplications

  Scenario: Patch secret
    Given authorize with admin@treaba.me and admin
    And do JSON post /clientApplications with
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
    When do JSON patch /clientApplications/an_app with
    """
    {
      "secret": "An password",
      "secretRequired": "true"
    }
    """
    Then status code is OK