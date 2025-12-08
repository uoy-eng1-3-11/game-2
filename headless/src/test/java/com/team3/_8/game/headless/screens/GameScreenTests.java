package com.team3._8.game.headless.screens;

import com.team3._8.game.MazeGame;
import com.team3._8.game.Screens.GameScreen;
import com.team3._8.game.headless.AbstractHeadlessGdxTest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameScreenTests extends AbstractHeadlessGdxTest {
    @Test
    public void resizeChangesViewportSize() {
        MazeGame game = new MazeGame();
        game.create();
        GameScreen screen = new GameScreen(game);

        screen.resize(912, 403);

        assertEquals(912, game.viewport.getScreenWidth());
        assertEquals(403, game.viewport.getScreenHeight());
    }
}
