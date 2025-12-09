package com.team3._8.game.headless.screens;

import com.team3._8.game.MazeGame;
import com.team3._8.game.Screens.WinScreen;
import com.team3._8.game.headless.AbstractHeadlessGdxTest;
import org.junit.jupiter.api.Test;

public class WinScreenTests extends AbstractHeadlessGdxTest {
    @Test
    public void constructorWorks() {
        new WinScreen(new MazeGame(), 100, 5);
    }
}
