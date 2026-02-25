package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.CombatLogger;
import com.jamal_karim.dungeon.models.effects.ShieldEffect;
import com.jamal_karim.dungeon.models.effects.StunEffect;

public class Tank extends Entity {

    private final int manaForShield = 10;
    private final int manaForStun;

    public Tank(String name, String team) {
        super(name, 250, 100, 20, team, 10);
        this.manaForStun = (int) (this.getMaxMana() * 0.75);
    }

    public int getManaForShield() {
        return manaForShield;
    }

    public int getManaForStun() {
        return manaForStun;
    }

    public void castShield(){
        if(this.getMana() >= manaForShield){
            ShieldEffect shield = new ShieldEffect();
            this.addEffect(shield);
            this.reduceMana(manaForShield);
            CombatLogger.logAction(this, "raises a heavy shield!");
        } else{
            System.out.println("Not enough mana for shield");
        }
    }

    public void castStun(Entity enemy){
        if(this.getMana() >= manaForStun){
            StunEffect stun = new StunEffect();
            enemy.addEffect(stun);
            this.reduceMana(manaForStun);
            CombatLogger.logAttack(this, enemy, "launches stun");
        } else{
            System.out.println("Not enough mana for stun");
        }
    }

    @Override
    public void attack(Entity enemy, int amount) {
        enemy.takeDamage(amount);
        CombatLogger.logAttack(this, enemy, "launches an attack");
        CombatLogger.logDamage(enemy, this.getDamage());
    }

    @Override
    public void takeDamage(int damage) {
        if(this.getActiveEffects().stream().anyMatch(effect -> effect.getName().equals("Shield Effect"))){
            this.setHp((int) (this.getHp() - damage * 0.5));
        } else{
            this.setHp(this.getHp() - damage);
        }
    }
}
