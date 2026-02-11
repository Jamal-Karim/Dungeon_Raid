package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.abilities.Ability;
import com.jamal_karim.dungeon.models.effects.Effect;

import java.util.*;

abstract public class Entity {
    private final int maxHp;
    private final int maxMana;
    private String name;
    private int hp;
    private int mana;
    private int damage;
    private String team;
    private List<Effect> activeEffects;
    private int speed;

    public Entity(String name, int hp, int mana, int damage, String team, int speed) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.maxMana = mana;
        this.mana = mana;
        this.damage = damage;
        this.team = team;
        this.speed = speed;
        this.activeEffects = new ArrayList<>();
    }

    abstract public void playTurn(BattleContext context);

    abstract protected void attack(Entity enemy, int amount);

    public int getMaxHp(){
        return this.maxHp;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public void addEffect(Effect effect){

        activeEffects.removeIf(e -> e.getName().equals(effect.getName()));

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
        activeEffects.removeIf(e -> e.getDuration() <= 0);
    }

    public int getMana() {
        return mana;
    }

    public void reduceMana(int mana) {
        this.mana -= mana;
    }

    public boolean canAffordAbility(Entity caster, Ability ability){
        return ability.getManaTaken(ability) <= caster.getMana();
    }

    public String getTeam() {
        return team;
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public int getSpeed() {
        return speed;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
