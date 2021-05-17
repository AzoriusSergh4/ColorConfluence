@Frontend
@Deck
@DeckFrontend
Feature: Deck Frontend Feature

  @DeckCreationFrontend
  Scenario: DeckCreationFrontend
    Given I get into the Main Page
    And I log in
    And I get into the Deck Creation Page
    When I enter the cards and I save them
    Then the deck is saved successfully
    And I the deck is displayed in my profile