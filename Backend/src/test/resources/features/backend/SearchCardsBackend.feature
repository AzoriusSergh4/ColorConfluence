@Backend
@SearchCards
Feature: Search cards in the database based on specified criteria
  @SearchCardsByName
  Scenario: SearchCardsByName
    Given I can connect to the database
    When I search for a card which has "Dimir" in its name
    Then There are results
    And All results match the specified criteria