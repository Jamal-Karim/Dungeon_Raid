package com.jamal_karim.dungeon.controllers;

import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.engine.BattleContext;

public interface ActionController {

    void decideAction(Entity user, BattleContext context);
}
