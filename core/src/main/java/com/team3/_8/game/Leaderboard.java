package com.team3._8.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class Leaderboard {
    private static final int[] leaderboard = new int[5];

    /** Create a new {@link Leaderboard} loaded from GDX preferences. */
    public Leaderboard() {
        load();
    }

    /** Load the leaderboard from GDX preferences. */
    public void load() {
        Preferences preferences = Gdx.app.getPreferences("leaderboard");

        for (int i = 0; i < leaderboard.length; ++i) {
            String key = "score" + (i + 1);
            leaderboard[i] = preferences.getInteger(key);
        }
    }

    /** Save the leaderboard to GDX preferences. */
    public void save() {
        Preferences preferences = Gdx.app.getPreferences("leaderboard");

        for (int i = 0; i < leaderboard.length; ++i) {
            String key = "score" + (i + 1);
            preferences.putInteger(key, leaderboard[i]);
        }

        preferences.flush();
    }

    /**
     * Insert a new score into the leaderboard.
     *
     * @param score the score to insert
     */
    public void insert(int score) {
        int previous = -1;

        for (int i = 0; i < leaderboard.length; ++i) {
            if (previous >= 0) {
                int current = leaderboard[i];
                leaderboard[i] = previous;
                previous = current;
            } else if (score > leaderboard[i]) {
                previous = leaderboard[i];
                leaderboard[i] = score;
            }
        }
    }

    /** {@return an array representation of the leaderboard} */
    public int[] getArray() {
        return leaderboard;
    }

    /** {@return a string representation of the leaderboard} */
    public String getText() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < leaderboard.length; ++i) {
            builder.append(i + 1);
            builder.append(". ");
            builder.append(leaderboard[i]);
            builder.append("\n");
        }

        return builder.toString();
    }
}
