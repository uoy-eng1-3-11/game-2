package com.team3._8.game.headless.screens;

import com.badlogic.gdx.Gdx;
import com.team3._8.game.MazeGame;
import com.team3._8.game.Screens.WinScreen;
import com.team3._8.game.headless.AbstractHeadlessGdxTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WinScreenTests extends AbstractHeadlessGdxTest {
    @BeforeEach
    public void resetPreferences() {
        Gdx.app.getPreferences("leaderboard").clear();
    }

    @Test
    public void leaderboardUpdatesCorrectly() {
        WinScreen screen;

        screen = new WinScreen(new MazeGame(), 100, 10);
        assertEquals(
            "1. 3000\n2. 0\n3. 0\n4. 0\n5. 0\n",
            screen.getLeaderboardText(),
            "The initial score should be inserted to the first slot"
        );

        screen = new WinScreen(new MazeGame(), 2, 25);
        assertEquals(
            "1. 3000\n2. 2752\n3. 0\n4. 0\n5. 0\n",
            screen.getLeaderboardText(),
            "A new score should be inserted between higher and lower scores"
        );

        screen = new WinScreen(new MazeGame(), 53, 7);
        assertEquals(
            "1. 3000\n2. 2983\n3. 2752\n4. 0\n5. 0\n",
            screen.getLeaderboardText(),
            "Inserting a new score should shift lower scores downwards"
        );

        screen = new WinScreen(new MazeGame(), 53, 7);
        assertEquals(
            "1. 3000\n2. 2983\n3. 2752\n4. 2752\n5. 0\n",
            screen.getLeaderboardText(),
            "Duplicate scores should be allowed"
        );

        screen = new WinScreen(new MazeGame(), 53, 7);
        assertEquals(
            "1. 3000\n2. 2983\n3. 2752\n4. 2752\n5. 2752\n",
            screen.getLeaderboardText(),
            "Duplicate scores should be allowed"
        );

        screen = new WinScreen(new MazeGame(), 0, 15);
        assertEquals(
            "1. 3000\n2. 2983\n3. 2752\n4. 2752\n5. 2752\n",
            screen.getLeaderboardText(),
            "Scores that do not fit on the leaderboard should be discarded"
        );
    }
}
