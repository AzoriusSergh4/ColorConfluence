@Backend
@Deck
@DeckBackend
Feature: Backend Scenarios for Decks

  Scenario: DeckCreation
    Given I can connect to the database
    And I am logged in
    When I add the deck information
    Then the deck is stored successfully