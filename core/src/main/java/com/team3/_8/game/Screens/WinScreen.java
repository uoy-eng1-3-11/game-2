package com.team3._8.game.Screens;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team3._8.game.MazeGame;
import com.team3._8.game.Utils;

public class WinScreen implements Screen {
    final MazeGame GAME;

    private static final String TOP_TEXT = "Well done!!";
    private static final String BOTTOM_TEXT = "You won the game";

    Map<String, Float> layoutValues;

    public WinScreen(MazeGame game) {
        GAME = game;
        
        // Prepare text layouts for measurement
        GlyphLayout topLayout = new GlyphLayout(GAME.font, TOP_TEXT);
        GlyphLayout bottomLayout = new GlyphLayout(GAME.font, BOTTOM_TEXT);
        GlyphLayout[] textLayout = {topLayout, bottomLayout};
        layoutValues = Utils.positionText(GAME.viewport, textLayout);
    }

    @Override
    public void render(float delta){
        draw(GAME.batch);
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen
        
        GAME.viewport.apply();
        batch.setProjectionMatrix(GAME.viewport.getCamera().combined);
        batch.begin();
        
        // Draws centered text
        GAME.font.draw(batch, TOP_TEXT, layoutValues.get("x1"), layoutValues.get("y1"));
        GAME.font.draw(batch, BOTTOM_TEXT, layoutValues.get("x2"), layoutValues.get("y2"));
        batch.end();
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
