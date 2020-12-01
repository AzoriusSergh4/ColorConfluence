@Frontend
@SearchCards
Feature: Search cards in the database based on specified criteria
  @SearchCardsByName
  Scenario: SearchByName
    Given I get into the Main Page
    When I enter the name of a card
    Then The results page is loaded
    And I can see all results in block