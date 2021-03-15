@Frontend
@GetCardInfo
Feature: Get card Information

  @GetCardInfoOneResult
  Scenario: GetCardInfoOneResult
    Given I get into the Main Page
    When I search for a card with name "Ephara, God of the Polis"
    Then The card page is loaded
    And I can see all the information from the card

  @GetCardInfoMultipleResults
  Scenario:
    Given I get into the Main Page
    When I search for a card with name "Ephara"
    And The results page is loaded
    And I can see all results in block
    And I click on a card
    Then The card page is loaded
    And I can see all the information from the card
