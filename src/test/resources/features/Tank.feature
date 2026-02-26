Feature: Tank Combat Logic

  Background:
    Given a tank named {Test_Tank} exists with 100 HP and 100 mana
    Given a warrior named {Test_Warrior} exists with 100 HP

  Scenario: Tank can cast stun
    When the tank {Test_Tank} tries to cast stun on {Test_Warrior}
    Then the entity {Test_Warrior} should have 1 active effects

  Scenario: Tank can cast shield
    # Takes 25 damage
    When an entity {Test_Warrior} attacks {Test_Tank}
    Then the entity {Test_Tank} should have 75 HP
    When the tank {Test_Tank} tries to cast shield
    Then the entity {Test_Tank} should have 1 active effects
    # Should take reduced damage
    When an entity {Test_Warrior} attacks {Test_Tank}
    Then the entity {Test_Tank} should have 63 HP

  Scenario: Tank cannot cast shield or stun if mana is too low
    Given a tank named {Test_Tank2} exists with 100 HP and 100 mana
    When an entity {Test_Tank2} mana is lowered to 1
    When the tank {Test_Tank2} tries to cast shield
    Then the error message thrown is "Cannot execute shield cast: Conditions not met (mana must be > 10)."
    When the tank {Test_Tank2} tries to cast stun on {Test_Warrior}
    Then the error message thrown is "Cannot execute stun cast: Conditions not met (mana must be > 75)."