package com.jamal_karim.dungeon.utils;

import com.jamal_karim.dungeon.engine.BattleContext;

public class TestContext {
    private final TestVariables testVariables;
    private final BattleContext battleContext;

    public TestContext() {
        this.testVariables = new TestVariables();
        this.battleContext = new BattleContext();
    }

    public TestVariables getTestVariables() { return testVariables; }
    public BattleContext getBattleContext() { return battleContext; }
}