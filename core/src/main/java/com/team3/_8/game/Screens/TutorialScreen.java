package com.team3._8.game.Screens;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team3._8.game.MazeGame;
import com.team3._8.game.Utils;

public class TutorialScreen implements Screen {
    final MazeGame GAME;
    private Sprite tutorialSprite;

    Map<String, Float> layoutValues;

    private final static String TOP_TEXT = "Press ESC to go back";

    public TutorialScreen(MazeGame game) {
        GAME = game;

        Texture tutorial_texture = new Texture("tutorial.png");
        tutorialSprite = new Sprite(tutorial_texture);
        tutorialSprite.setSize(310, 180);
        tutorialSprite.setPosition(0,0);
    }

    @Override
    public void render(float delta){
        input();
        draw(GAME.batch);
    }

    private void input() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            GAME.setScreen(new TitleScreen(GAME));
        }
    }

    private void draw(SpriteBatch batch) {
        if (layoutValues == null) {
            GlyphLayout topLayout = new GlyphLayout(GAME.font, TOP_TEXT);
            GlyphLayout[] textLayout = {topLayout};
            layoutValues = Utils.positionText(GAME.viewport, textLayout);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen

        batch.begin();

        tutorialSprite.draw(batch);
        GAME.font.draw(batch, TOP_TEXT, layoutValues.get("x1"), layoutValues.get("y1"));

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
    public void resize(int width, int height) {
        GAME.viewport.update(width, height);
    }
}
