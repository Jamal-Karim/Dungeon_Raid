Feature: Game Logic

  Scenario: Initial team setup and turn progression
    Given the raid exists with the following entities:
      | entity  | name          | team    | user |
      | Warrior | SpeedyWarrior | hero    | cpu  |
      | Mage    | SlowMage      | monster | cpu  |
    When the game progresses one turn
    Then SpeedyWarrior should be the entity that acted
    When the game progresses one turn
    Then SlowMage should be the entity that acted

  Scenario: Mage casts poison and reduces target HP and adds effect
    Given the raid exists with the following entities:
      | entity  | name   | team  | user | hp  | mana |
      | Mage    | MyMage | hero  | cpu  | 100 | 100  |
      | Warrior | EnemyW | enemy | cpu  | 100 |      |
    When the mage {MyMage} tries to cast poison on {EnemyW}
    Then the entity {EnemyW} should have 90 HP
    And the entity {EnemyW} should have 1 active effects

  Scenario: Mage casts fireball and reduces target HP
    Given the raid exists with the following entities:
      | entity  | name   | team  | user | hp  | mana |
      | Mage    | MyMage | hero  | cpu  | 100 | 100  |
      | Warrior | EnemyW | enemy | cpu  | 100 |      |
    When the mage {MyMage} tries to cast fireball on {EnemyW}
    Then the entity {EnemyW} should have 80 HP
    And the entity {EnemyW} should have 0 active effects

  Scenario: Tank casts shield and takes reduced damage
    Given the raid exists with the following entities:
      | entity  | name   | team  | user | hp  | mana |
      | Tank    | MyTank | hero  | cpu  | 100 | 100  |
      | Warrior | EnemyW | enemy | cpu  | 100 |      |
    When an entity {EnemyW} attacks {MyTank}
    Then the entity {MyTank} should have 75 HP
    When the tank {MyTank} tries to cast shield
    Then the entity {MyTank} should have 1 active effects
    When an entity {EnemyW} attacks {MyTank}
    Then the entity {MyTank} should have 63 HP

  Scenario: Tank casts stun and affects target
    Given the raid exists with the following entities:
      | entity  | name   | team  | user | hp  | mana |
      | Tank    | MyTank | hero  | cpu  | 100 | 100  |
      | Warrior | EnemyW | enemy | cpu  | 100 |      |
    When the tank {MyTank} tries to cast stun on {EnemyW}
    Then the entity {EnemyW} should have 1 active effects

  Scenario: Warrior executes super attack when HP is low
    Given the raid exists with the following entities:
      | entity  | name      | team  | user | hp  |
      | Warrior | MyWarrior | hero  | cpu  | 9   |
      | Tank    | EnemyTank | enemy | cpu  | 100 |
    When the warrior {MyWarrior} tries to execute a super attack on {EnemyTank}
    Then the entity {EnemyTank} should have 50 HP

  Scenario: Mage cannot cast spell if mana is too low
    Given the raid exists with the following entities:
      | entity  | name   | team  | user | hp  | mana |
      | Mage    | MyMage | hero  | cpu  | 100 | 9    |
      | Warrior | EnemyW | enemy | cpu  | 100 |      |
    When the mage {MyMage} tries to cast poison on {EnemyW}
    Then the error message thrown is "Cannot execute poison cast: Conditions not met (mana must be > 20)."
    When the mage {MyMage} tries to cast fireball on {EnemyW}
    Then the error message thrown is "Cannot execute fireball cast: Conditions not met (mana must be > 10)."

  Scenario: Tank cannot cast shield if mana is too low
    Given the raid exists with the following entities:
      | entity  | name   | team  | user | hp  | mana |
      | Tank    | MyTank | hero  | cpu  | 100 | 1    |
      | Warrior | EnemyW | enemy | cpu  | 100 |      |
    When the tank {MyTank} tries to cast shield
    Then the error message thrown is "Cannot execute shield cast: Conditions not met (mana must be > 10)."

  Scenario: Tank cannot cast stun if mana is too low
    Given the raid exists with the following entities:
      | entity  | name    | team  | user | hp  | mana |
      | Tank    | MyTank2 | hero  | cpu  | 100 | 1    |
      | Warrior | EnemyW2 | enemy | cpu  | 100 |      |
    When the tank {MyTank2} tries to cast stun on {EnemyW2}
    Then the error message thrown is "Cannot execute stun cast: Conditions not met (mana must be > 75)."
