Feature: Update user details API

  Background: Clean up all data
    Given authorize with admin@treaba.me and admin
    And delete all users except admin%2540treaba%252Eme
    But do JSON post /users with
    """
    {
      "email": "test@example.com",
      "password":"password"
    }
    """

  @check
  Scenario: Update displayed name
    When do JSON put /users/test%2540example%252Ecom with
    """
    {
      "email": "test@example.com",
      "displayName": "Super User"
    }
    """
    Then status code is OK
    And parameter email equals to test@example.com
    And parameter displayName equals to Super User

  Scenario: Update password
    When do JSON put /users/test%2540example%252Ecom with
    """
    {
      "password": "NewPassword"
    }
    """
    Then status code is OK
    And parameter email equals to test@example.com
    And parameter displayName equals to test@example.com

  Scenario: Update returns hypermedia link
    When do JSON put /users/test%2540example%252Ecom with
    """
    {
      "displayName": "Hateoas check"
    }
    """
    Then have parameter _links.self.href

