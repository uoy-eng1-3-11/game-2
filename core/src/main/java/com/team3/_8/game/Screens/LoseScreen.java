package com.team3._8.game.Screens;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.team3._8.game.MazeGame;
import com.team3._8.game.Utils;

public class LoseScreen implements Screen {
    final MazeGame GAME;

    final static String TOP_TEXT = "Time's Up!";
    final static String CENTER_TEXT = "You lost the game :(";
    final static String BOTTOM_TEXT = "Press Space to restart";

    Animation<TextureRegion> bobSquashAnimation;
    Map<String, Float> layoutValues;
    float stateTime = 0f;
    Sprite bobSprite;

    public LoseScreen(MazeGame game) {
        GAME = game;

        TextureAtlas atlas = new TextureAtlas("atlas/squash_bob.atlas");
        bobSquashAnimation = new Animation<TextureRegion>(0.1f, atlas.findRegions("squash-bob"), PlayMode.LOOP);

        GlyphLayout topLayout = new GlyphLayout(GAME.font, TOP_TEXT);
        GlyphLayout centerLayout = new GlyphLayout(GAME.font, CENTER_TEXT);
        GlyphLayout bottomLayout = new GlyphLayout(GAME.font, BOTTOM_TEXT);
        GlyphLayout[] textLayout = {topLayout, centerLayout, bottomLayout};
        layoutValues = Utils.positionText(GAME.viewport, textLayout);

        bobSprite = new Sprite();

        bobSprite.setPosition(
            GAME.viewport.getWorldWidth() / 2f - 16f,
            GAME.viewport.getWorldHeight() / 2f + 30f);

        bobSprite.setSize(2 * MazeGame.BOB_WIDTH, 2 * MazeGame.BOB_HEIGHT);
    }

    @Override
    public void render(float delta) {
        input();
        draw(GAME.batch);
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) { 
            GAME.setScreen(new GameScreen(GAME));
        }
    }

    private void draw(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen

        stateTime += Gdx.graphics.getDeltaTime();
        bobSprite.setRegion(bobSquashAnimation.getKeyFrame(stateTime));
        
        GAME.viewport.apply();
        batch.setProjectionMatrix(GAME.viewport.getCamera().combined);
        batch.begin();

        bobSprite.draw(batch);

        System.out.println();

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
