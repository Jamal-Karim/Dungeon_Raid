package com.jamal_karim.dungeon;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Warrior;

public class Main {
    public static void main(String[] args) {
        Entity warrior = new Warrior("warrior", "monster");
        Entity mage = new Mage("mage", "hero");

        BattleContext context = new BattleContext();
        context.addToTeam(warrior, warrior.getTeam());
        context.addToTeam(mage, mage.getTeam());

        while (true){
            if(context.checkForWinner()){
                return;
            }
            mage.playTurn(context);
            if(context.checkForWinner()){
                return;
            }
            warrior.playTurn(context);
        }
    }
}