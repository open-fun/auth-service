Feature: Create user considering HATEOAS

  Background: Clean up all data
    Given authorize with admin@treaba.me and admin
    And delete all users except admin@treaba.me

  Scenario: Unable to create user if not get authenticated
    Given not authorized
    When do JSON post /users with
    """
    {
      "email": "test@example.com",
      "password":"password"
    }
    """
    Then status code is Unauthorized

  Scenario: Create user
    Given authorize with admin@treaba.me and admin
    When do JSON post /users with
    """
    {
      "email": "test2@example.com",
      "password":"password"
    }
    """
    Then status code is Created
    And parameter email equals to test2@example.com
    And parameter displayName equals to test2@example.com

  Scenario: Create user and check for HATEOAS link
    Given authorize with admin@treaba.me and admin
    When do JSON post /users with
    """
    {
      "email": "hypermedia@example.com",
      "password":"password"
    }
    """
    Then have parameter _links.self.href

  Scenario: Unable to create user without password
    Given authorize with admin@treaba.me and admin
    When do JSON post /users with
    """
    {
      "email": "hypermedia@example.com"
    }
    """
    Then status code is Unprocessable Entity

  Scenario: Create user with custom display name
    Given authorize with admin@treaba.me and admin
    When do JSON post /users with
    """
    {
      "displayName": "Some name to display",
      "email": "naming@example.com",
      "password":"password"
    }
    """
    Then status code is Created
    And parameter displayName equals to Some name to display

