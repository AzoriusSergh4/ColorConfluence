@DeckListFrontend
@Frontend
Feature: DeckListFrontend

  Scenario: SearchDeckByName
    Given I get into the Main Page
    And I go to the Deck List Page
    When I search decks by name "Spirits"
    Then All decks displayed contains the name "Spirits"
