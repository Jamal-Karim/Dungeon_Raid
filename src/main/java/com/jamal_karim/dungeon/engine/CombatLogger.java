package com.jamal_karim.dungeon.engine;

import com.jamal_karim.dungeon.models.entities.Entity;

public class CombatLogger {

    public static void logStartOfTurn(Entity entity){
        System.out.println("--- [" + entity.getName() + "] turn ---");
    }

    public static void logAction(Entity entity, String message){
        System.out.println("-> [" + entity.getName() + "]: " + message);
    }

    public static void logAttack(Entity user, Entity target, String message){
        System.out.println("-> [" + user.getName() + "]: " + message + " on " + target.getName());
    }

    public static void logDamage(Entity target, int amount) {
        System.out.println("   * " + target.getName() + " takes " + amount + " damage!");
        if (!target.isAlive()) {
            System.out.println("   *** " + target.getName() + " has been defeated! ***");
        }
    }

    public static void logEffect(Entity target, String effectName, int amount) {
        System.out.println("   ! " + target.getName() + " is affected by " + effectName + " and takes " + amount + " damage");
    }
}
