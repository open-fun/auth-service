Feature: Ability to list and page items


  Background: Clean up all data
    Given authorize with admin@treaba.me and admin
    And delete all users except admin%2540treaba%252Eme
    And delete resources clientApplications


  Scenario: If no any client applications then total pages are zero
    Given authorize with admin@treaba.me and admin
    When do get /clientApplications
    Then status code is OK
    And parameter page.totalElements equals to 0
    And parameter page.totalPages equals to 0


  Scenario: For twenty one users has to be two pages
    Given authorize with admin@treaba.me and admin
    And have 25 more client applications
    When do get /clientApplications
    Then status code is OK
    And parameter page.totalElements equals to 25
    And parameter page.totalPages equals to 2
    And parameter page.size equals to 20
    When do get /clientApplications?page=1
    Then status code is OK
    And parameter page.totalElements equals to 25
    And parameter page.totalPages equals to 2
    And parameter page.size equals to 20


  Scenario: Check HATEOAS links
    Given authorize with admin@treaba.me and admin
    And have 25 more client applications
    When do get /clientApplications?page=1
    Then status code is OK
    And have parameter _links.self.href
    And have parameter _links.first.href
    And have parameter _links.prev.href
