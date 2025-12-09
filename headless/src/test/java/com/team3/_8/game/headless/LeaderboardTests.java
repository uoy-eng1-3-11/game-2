package com.team3._8.game.headless;

import com.badlogic.gdx.Gdx;
import com.team3._8.game.Leaderboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderboardTests extends AbstractHeadlessGdxTest {
    @BeforeEach
    public void resetPreferences() {
        Gdx.app.getPreferences("leaderboard").clear();
    }

    @Test
    public void isPersistent() {
        Leaderboard originalLeaderboard = new Leaderboard();
        originalLeaderboard.insert(5);
        originalLeaderboard.save();

        Leaderboard newLeaderboard = new Leaderboard();

        assertEquals(originalLeaderboard.getArray(), newLeaderboard.getArray());
    }

    @Test
    public void updatesCorrectly() {
        Leaderboard leaderboard = new Leaderboard();

        leaderboard.insert(3000);
        assertEquals(
            "1. 3000\n2. 0\n3. 0\n4. 0\n5. 0\n",
            leaderboard.getText(),
            "The initial score should be inserted to the first slot"
        );

        leaderboard.insert(2752);
        assertEquals(
            "1. 3000\n2. 2752\n3. 0\n4. 0\n5. 0\n",
            leaderboard.getText(),
            "A new score should be inserted between higher and lower scores"
        );

        leaderboard.insert(2983);
        assertEquals(
            "1. 3000\n2. 2983\n3. 2752\n4. 0\n5. 0\n",
            leaderboard.getText(),
            "Inserting a new score should shift lower scores downwards"
        );

        leaderboard.insert(2752);
        leaderboard.insert(2752);
        assertEquals(
            "1. 3000\n2. 2983\n3. 2752\n4. 2752\n5. 2752\n",
            leaderboard.getText(),
            "Duplicate scores should be allowed"
        );

        leaderboard.insert(123);
        assertEquals(
            "1. 3000\n2. 2983\n3. 2752\n4. 2752\n5. 2752\n",
            leaderboard.getText(),
            "Scores that do not fit on the leaderboard should be discarded"
        );
    }
}
