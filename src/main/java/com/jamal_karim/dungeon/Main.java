package com.jamal_karim.dungeon;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Warrior;

public class Main {
    public static void main(String[] args) {
        Entity warrior = new Warrior("warrior", "hero");
        Entity mage = new Mage("mage", "monster");

        BattleContext context = new BattleContext();
        context.addToTeam(warrior, warrior.getTeam());
        context.addToTeam(mage, mage.getTeam());

        warrior.playTurn(context);
        System.out.println(mage.getHp());
        mage.playTurn(context);
        System.out.println(warrior.getHp());
    }
}