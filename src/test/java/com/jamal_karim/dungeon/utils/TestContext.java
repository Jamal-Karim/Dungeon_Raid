package com.jamal_karim.dungeon.utils;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.engine.TurnManager;
import com.jamal_karim.dungeon.engine.ui.GameUI;
import com.jamal_karim.dungeon.engine.ui.MockUI;

public class TestContext {
    private final TestVariables testVariables;
    private final GameUI ui = new MockUI();
    private final TurnManager turns;
    private final BattleContext battleContext;

    public TestContext() {
        this.testVariables = new TestVariables();
        this.battleContext = new BattleContext();
        this.turns = new TurnManager(battleContext, ui);
    }

    public TestVariables getTestVariables() {
        return testVariables;
    }

    public BattleContext getBattleContext() {
        return battleContext;
    }

    public TurnManager getTurns() {
        return turns;
    }

}