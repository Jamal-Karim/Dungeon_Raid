package com.jamal_karim.dungeon;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Warrior;

public class Main {
    public static void main(String[] args) {
        Entity warrior = new Warrior("warrior");
        Entity mage = new Mage("mage");

        BattleContext enemyOfWarrior = new BattleContext(mage);
        BattleContext enemyOfMage = new BattleContext(warrior);

        warrior.playTurn(enemyOfWarrior);
        System.out.println(mage.getHp());
        mage.playTurn(enemyOfMage);
        System.out.println(warrior.getHp());

    }
}