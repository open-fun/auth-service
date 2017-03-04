Feature: Ability to use ops endpoints

  Scenario: Check health status
    When do get /health.json
    Then result must contain status parameter with value UP