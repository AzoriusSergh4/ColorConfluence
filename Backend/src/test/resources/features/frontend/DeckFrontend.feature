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

  @DeckAnalysis
  Scenario: DeckAnalysis
    Given I get into the Main Page
    And I go to the Deck List Page
    And I go into a deck
    And The deck page is rendered
    When I get into the "Analytics" tab
    Then the analysis tab is loaded and I can see the charts

  @DeckProbabilities
  Scenario: DeckProbabilities
    Given I get into the Main Page
    And I go to the Deck List Page
    And I go into a deck
    And The deck page is rendered
    When I get into the "Probabilities" tab
    Then the probabilities tab is loaded and I can see the percentages

  @DeckOpeningHand
  Scenario: DeckOpeningHand
    Given I get into the Main Page
    And I go to the Deck List Page
    And I go into a deck
    And The deck page is rendered
    When I get into the "Opening Hand" tab
    Then the opening hand tab is loaded and I can see seven cards

    @DeckExport
    Scenario: Deck Export
      Given I get into the Main Page
      And I go to the Deck List Page
      And I go into a deck
      And The deck page is rendered
      When I get into the "Export" tab
      Then the export tab is loaded and I can see the deck list ready to copy
