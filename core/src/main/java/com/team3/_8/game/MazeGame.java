package com.team3._8.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.team3._8.game.Screens.TitleScreen;

import java.util.HashMap;
import java.util.Map;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MazeGame extends Game {

    // Constants in arbitrary units for the camera
    static final int WORLD_WIDTH = 200;
    static final int WORLD_HEIGHT = 200;

    // Constants in arbitrary units for Bob's size
    public static final int BOB_WIDTH = 15;
    public static final int BOB_HEIGHT = 15;

    // Map to store return data for interactable entities
    // The data can be used to control other objects in this program
    Map<String, Boolean> evilBobReturnData = new HashMap<>();
    Map<String, Boolean> campusSecurityReturnData = new HashMap<>();
    boolean campusSecurityCreated;

    // Screen manager
    public BitmapFont font;
    public ExtendViewport viewport;

    // The two sprite batches -> ones for the main game, and one for the HUD
    public SpriteBatch batch;

    @Override
    public void create() {
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);

        this.setScreen(new TitleScreen(this));
    }

    /** Renders different screens based on activeScreen configuration */
    @Override
    public void render() {
        if (batch == null) batch = new SpriteBatch();
        if (font == null) font = new BitmapFont();

        super.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // Changes the viewport's size to the sizes passed
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
    }
}
