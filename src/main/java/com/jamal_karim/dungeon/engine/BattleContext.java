package com.jamal_karim.dungeon.engine;

import java.util.*;
import com.jamal_karim.dungeon.models.entities.Entity;

public class BattleContext {
    private List<Entity> allies = new ArrayList<>();
    private List<Entity> enemies = new ArrayList<>();
    private Entity currentTarget;


    public BattleContext(Entity target) {
        this.currentTarget = target;
    }

    public Entity getCurrentTarget() {
        return currentTarget;
    }
}
