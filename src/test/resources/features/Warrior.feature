Feature: Warrior Combat Logic

  Background:
    Given a warrior named {Test_Warrior} exists with 100 HP

  Scenario: Basic warrior attack
    Given a warrior named {Test_Warrior2} exists with 50 HP
    When an entity {Test_Warrior} attacks {Test_Warrior2}
    Then the entity {Test_Warrior2} should have 25 HP

  Scenario: Warrior can execute super attack when health is below 10
    Given a warrior named {Test_Warrior3} exists with 9 HP
    When the warrior {Test_Warrior3} executes a super attack on {Test_Warrior}
    Then the entity {Test_Warrior} should have 60 HP
