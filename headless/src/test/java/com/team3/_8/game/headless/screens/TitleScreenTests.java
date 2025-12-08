package com.team3._8.game.headless.screens;

import com.team3._8.game.MazeGame;
import com.team3._8.game.Screens.TitleScreen;
import com.team3._8.game.headless.AbstractHeadlessGdxTest;
import org.junit.jupiter.api.Test;

public class TitleScreenTests extends AbstractHeadlessGdxTest {
    @Test
    public void constructorWorks() {
        new TitleScreen(new MazeGame());
    }
}
