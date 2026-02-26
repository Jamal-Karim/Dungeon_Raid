package com.jamal_karim.dungeon.engine.ui;

import com.jamal_karim.dungeon.models.entities.Entity;

import java.util.List;

public class MockUI implements GameUI {
    @Override
    public void waitForPlayerInput() {

    }

    @Override
    public void logStartOfTurn(Entity entity) {

    }

    @Override
    public void logBattleStatus(List<Entity> actives, List<Entity> fallen) {

    }

    @Override
    public void logWinner(String teamName) {

    }
}
