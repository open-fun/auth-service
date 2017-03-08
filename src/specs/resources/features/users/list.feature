Feature: Ability to list available users considering pagination and sorting


  Background: Clean up all data
    Given authorize with admin@treaba.me and admin
    And delete all users except admin@treaba.me

  Scenario: For one user has to be just one page
    Given authorize with admin@treaba.me and admin
    When do get /users
    Then status code is OK
    And parameter page.totalElements equals to 1
    And parameter page.totalPages equals to 1

  Scenario: For twenty one users has to be two pages
    Given authorize with admin@treaba.me and admin
    And have 20 more users
    When do get /users
    Then status code is OK
    And parameter page.totalElements equals to 21
    And parameter page.totalPages equals to 2
    And parameter page.size equals to 20
    When do get /users?page=1
    Then status code is OK
    And parameter page.totalElements equals to 21
    And parameter page.totalPages equals to 2
    And parameter page.size equals to 20