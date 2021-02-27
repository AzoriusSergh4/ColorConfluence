@Frontend
@SearchCards
Feature: Search cards in the database based on specified criteria

  @SearchCardsByName
  Scenario: SearchByName
    Given I get into the Main Page
    When I enter the name of a card
    Then The results page is loaded
    And I can see all results in block

  @SearchCardsComplete
  Scenario: SearchCardsComplete:
    Given I get into the Main Page
    When I go into the Advance Search Page
    And The Advance Search Page is loaded
    And I enter all the information
      | name   | text           | type | colors | cmc | power | toughness | lang    | rarity |
      | Ephara | Indestructible | God  | W      | 4   | 6     | 5         | English | Mythic |
    Then The results page is loaded
    And I can see all results in block