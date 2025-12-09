package com.team3._8.game.Screens;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team3._8.game.MazeGame;
import com.team3._8.game.Utils;

public class WinScreen implements Screen {
    final MazeGame GAME;

    private static final String TOP_TEXT = "Well done!!";
    private static final String CENTER_TEXT = "You won the game";
    private final String BOTTOM_TEXT;

    private String leaderboardText = "";

    Map<String, Float> layoutValues;

    public WinScreen(MazeGame game, int score, int timer) {
        GAME = game;

        int totalScore = score+(300-timer)*10;

        BOTTOM_TEXT = "score: "+totalScore;

        leaderboard(totalScore);
    }

    @Override
    public void render(float delta){
        draw(GAME.batch);
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            GAME.setScreen(new TitleScreen(GAME));
        }
    }

    public void draw(SpriteBatch batch) {
        if (layoutValues == null) {
            GlyphLayout topLayout = new GlyphLayout(GAME.font, TOP_TEXT);
            GlyphLayout centerLayout = new GlyphLayout(GAME.font, CENTER_TEXT);
            GlyphLayout bottomLayout = new GlyphLayout(GAME.font, BOTTOM_TEXT);
            GlyphLayout leaderboardLayout = new GlyphLayout(GAME.font, leaderboardText);
            GlyphLayout[] textLayout = {topLayout, centerLayout, bottomLayout, leaderboardLayout};
            layoutValues = Utils.positionText(GAME.viewport, textLayout);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen

        GAME.viewport.apply();
        batch.setProjectionMatrix(GAME.viewport.getCamera().combined);
        batch.begin();

        // Draws centered text
        GAME.font.draw(batch, TOP_TEXT, layoutValues.get("x1"), layoutValues.get("y1"));
        GAME.font.draw(batch, CENTER_TEXT, layoutValues.get("x2"), layoutValues.get("y2"));
        GAME.font.draw(batch, BOTTOM_TEXT, layoutValues.get("x3"), layoutValues.get("y3"));
        GAME.font.draw(batch, leaderboardText, layoutValues.get("x4"), layoutValues.get("y4"));
        batch.end();
    }

    private void leaderboard(int score) {
        Preferences prefs = Gdx.app.getPreferences("leaderboard");

        int scoreBuffer = -1;
        for (int i = 1; i < 6; i++) {
            String key = "score" + i;
            if (scoreBuffer != -1) {
                scoreBuffer = prefs.getInteger(key);
                leaderboardText += i + ". " + scoreBuffer + "\n";
                prefs.putInteger(key, scoreBuffer);
            }
            else if (score > prefs.getInteger(key)) {
                scoreBuffer = prefs.getInteger(key);
                leaderboardText += i + ". " + score + "\n";
                prefs.putInteger(key, score);
            } else {
                leaderboardText += i + ". " + prefs.getInteger(key) + "\n";
            }

            prefs.flush();
        }
    }

    public String getLeaderboardText() {
        return leaderboardText;
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void show() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
    @Override
    public void resize(int width, int height) {}
}
