package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.abilities.Ability;
import com.jamal_karim.dungeon.models.effects.Effect;

import java.util.*;

abstract public class Entity {
    private final int maxHp;
    private String name;
    private int hp;
    private int mana;
    private int damage;
    private List<Effect> activeEffects;

    public Entity(String name, int hp, int mana, int damage) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.mana = mana;
        this.damage = damage;
        this.activeEffects = new ArrayList<>();
    }

    abstract public void playTurn(BattleContext context);

    abstract protected void attack(Entity enemy, int amount);

    public int getMaxHp(){
        return this.maxHp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public void addEffect(Effect effect){
        this.activeEffects.add(effect);
    }

    public List<Effect> getActiveEffects() {
        return activeEffects;
    }

    public void processEffects(List<Effect> effects){
        activeEffects.removeIf(e -> e.getDuration() <= 0);
        for (Effect e : activeEffects) {
            e.applyTick(this);
        }
    }

    public int getMana() {
        return mana;
    }

    public void reduceMana(int mana) {
        this.mana = mana;
    }
}
