package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.BattleContext;

public class Tank extends Entity {

    public Tank(String name, int hp, int mana, int damage) {
        super(name, hp, mana, damage);
    }

    @Override
    public void playTurn(BattleContext context) {

    }

    @Override
    protected void attack(Entity enemy, int amount) {

    }
}
