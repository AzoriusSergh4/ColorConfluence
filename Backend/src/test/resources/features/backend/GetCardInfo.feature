@Backend
@GetCardInfo
Feature: Get all info from a card

  Scenario: GetCardInfo
    Given I can connect to the database
    When I search a card by its id with id 49474
    Then There is a result
    And All the information is loaded