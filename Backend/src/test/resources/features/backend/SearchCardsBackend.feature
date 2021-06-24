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
      | page | name   | text            | lore                    | type                               | colors | manaCost  | cmc | power | toughness | standard | duel | legacy | modern | vintage | commander | historic | pioneer | penny | set | lang    | rarity |
      | 0    | Dimir  |                 |                         |                                    |        |           |     |       |           |          |      |        |        |         |           |          |         |       |     | English | Common |
      | 0    |        | Draw four cards |                         |                                    |        |           |     |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      | 0    |        |                 | Rara vez uno se percata |                                    |        |           |     |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      | 0    |        |                 |                         | God                                |        |           |     |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      | 0    |        |                 |                         |                                    | =WUBRG  |           |     |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      | 0    |        |                 |                         |                                    |        | {7}{G}    | =7  |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      | 0    |        |                 |                         |                                    |        |           |     | >=9   |           |          |      |        |        |         |           |          |         |       |     |         |        |
      | 0    |        |                 |                         |                                    |        |           |     |       | =3        |          |      |        |        |         |           |          |         |       |     |         |        |
      | 0    |        |                 |                         |                                    |        |           |     |       |           | Legal    |      |        |        |         |           |          |         |       | M21 |         |        |
      | 0    |        |                 |                         |                                    |        |           |     |       |           |          |      |        |        |         |           |          |         |       |     |         |        |
      | 0    | Ephara | draw            |                         | Legendary,Enchantment,Creature,God | =WU     | {2}{W}{U} | =4  | >=3   | =5        |          |      |        |        |         |           | Legal    |         |       | BNG | English | Mythic |

    Then There are results
    And All results match the specified criteria