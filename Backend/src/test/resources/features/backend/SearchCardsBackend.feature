@Backend
@SearchCards
Feature: Search cards in the database based on specified criteria

  @SearchCardsByName
  Scenario: SearchCardsByName
    Given I can connect to the database
    When I search for a card which has "Dimir" in its name
    Then There are results
    And All results match the specified criteria

  @SearchCardsComplete
  Scenario: SearchCardsComplete
    Given I can connect to the database
    When I search for cards with specified criteria
      | name   | text            | lore                    | type                               | colors | manaCost  | cmc | power | toughness | standard | duel | legacy | modern | vintage | commander | historic | pioneer | penny | set | lang    | rarity |
      | Dimir  |                 |                         |                                    |        |           |     |       |           |          |      |        |        |         |           |          |         |       |     | English | Common |
      |        | Draw four cards |                         |                                    |        |           |     |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      |        |                 | Rara vez uno se percata |                                    |        |           |     |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      |        |                 |                         | God                                |        |           |     |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      |        |                 |                         |                                    | WUBRG  |           |     |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      |        |                 |                         |                                    |        | {7}{G}    | =7  |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      |        |                 |                         |                                    |        |           |     | >=4   |           |          |      |        |        |         |           |          |         |       |     |         |        |
      |        |                 |                         |                                    |        |           |     |       | =3        |          |      |        |        |         |           |          |         |       |     |         |        |
      |        |                 |                         |                                    |        |           |     |       |           | Legal    |      |        |        |         |           |          |         |       | M21 |         |        |
      |        |                 |                         |                                    |        |           |     |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      | Ephara | draw            |                         | Legendary,Enchantment,Creature,God | WU     | {2}{W}{U} | =4  | >=3   | =5        |          |      |        |        |         |           | Legal    |         |       | BNG | English | Mythic |

    Then There are results
    And All results match the specified criteria