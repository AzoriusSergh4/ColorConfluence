@DeckListFrontend
@Frontend
Feature: DeckListFrontend

  @SearchDeckByName
  Scenario: SearchDeckByName
    Given I get into the Main Page
    And I go to the Deck List Page
    When I search decks by name "Spirits"
    Then All decks displayed contains the name "Spirits"

  @SearchDeckByColors
  Scenario: SearchDeckByColors
    Given I get into the Main Page
    And I go to the Deck List Page
    When I search decks by color "UW"
    Then All decks displayed are of the combination of colors "UW"

  @SearchDeckByFormat
  Scenario: SearchDeckByFormat
    Given I get into the Main Page
    And I go to the Deck List Page
    When I search decks by format "Modern"
    Then All decks displayed are for the format "Modern"