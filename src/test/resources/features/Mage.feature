Feature: Mage Combat Logic

  Background:
    Given a mage named {Test_Mage} exists with 100 HP and 100 mana
    Given a warrior named {Test_Warrior} exists with 100 HP

  Scenario: Mage can cast poison
    When the mage {Test_Mage} tries to cast poison on {Test_Warrior}
    Then the entity {Test_Warrior} should have 1 active effects
    Then the entity {Test_Warrior} should have 90 HP

  Scenario: Mage can cast fireball
    When the mage {Test_Mage} tries to cast fireball on {Test_Warrior}
    Then the entity {Test_Warrior} should have 0 active effects
    Then the entity {Test_Warrior} should have 80 HP

  Scenario: Mage cannot cast poison or fireball if mana is too low
    Given a mage named {Test_Mage2} exists with 100 HP and 9 mana
    When the mage {Test_Mage2} tries to cast poison on {Test_Warrior}
    Then the error message thrown is "Cannot execute poison cast: Conditions not met (mana must be > 20)."
    When the mage {Test_Mage2} tries to cast fireball on {Test_Warrior}
    Then the error message thrown is "Cannot execute fireball cast: Conditions not met (mana must be > 10)."