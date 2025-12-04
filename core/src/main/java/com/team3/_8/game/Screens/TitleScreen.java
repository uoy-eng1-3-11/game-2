package com.team3._8.game.Screens;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team3._8.game.MazeGame;
import com.team3._8.game.Utils;

public class TitleScreen implements Screen {
    final MazeGame GAME;

    final static String TOP_TEXT = "Welcome to ... The Life of Bob";
    final static String CENTER_TEXT = "Press space to start";
    final static String BOTTOM_TEXT = "Press T to see tutorial";


    public TitleScreen(MazeGame game) {
        GAME = game;
    }

    @Override
    public void render(float delta) {
        input();
        draw(GAME.batch);
    }

    private void input() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            GAME.setScreen(new GameScreen(GAME));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            GAME.setScreen(new TutorialScreen(GAME));
        }
    } 

    private void draw(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen

        GlyphLayout topLayout = new GlyphLayout(GAME.font, TOP_TEXT);
        GlyphLayout centreLayout = new GlyphLayout(GAME.font, CENTER_TEXT);
        GlyphLayout bottomLayout = new GlyphLayout(GAME.font, BOTTOM_TEXT);
        GlyphLayout[] textLayout = {topLayout, centreLayout, bottomLayout};
        Map<String, Float> layoutValues = Utils.positionText(GAME.viewport, textLayout);
        
        GAME.viewport.apply();
        batch.setProjectionMatrix(GAME.viewport.getCamera().combined);
        batch.begin();
        
        // Draw centered text
        GAME.font.draw(batch, TOP_TEXT, layoutValues.get("x1"), layoutValues.get("y1"));
        GAME.font.draw(batch, CENTER_TEXT, layoutValues.get("x2"), layoutValues.get("y2"));
        GAME.font.draw(batch, BOTTOM_TEXT, layoutValues.get("x3"), layoutValues.get("y3"));
        
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
