Feature: Warrior Combat Logic

  Scenario: Basic Warrior Attack
    Given a warrior named {Test_Warrior} exists with 100 HP
    Given a warrior named {Test_Warrior2} exists with 50 HP
    When an entity {Test_Warrior} attacks {Test_Warrior2}
    Then the entity {Test_Warrior2} should have 25 HP
