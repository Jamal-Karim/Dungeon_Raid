package com.jamal_karim.dungeon.engine.ui;

import com.jamal_karim.dungeon.models.entities.Entity;

import java.util.List;

public interface GameUI {
    void waitForPlayerInput();
    void logStartOfTurn(Entity entity);
    void logBattleStatus(List<Entity> actives, List<Entity> fallen);
    void logWinner(String teamName);
}
