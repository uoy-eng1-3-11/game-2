package com.team3._8.game.Screens;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.team3._8.game.EntityManager;
import com.team3._8.game.GameController;
import com.team3._8.game.HUD;
import com.team3._8.game.Maze;
import com.team3._8.game.MazeGame;
import com.team3._8.game.entities.Bob;
import com.team3._8.game.entities.CheckinCode;
import com.team3._8.game.entities.EvilBob;
import com.team3._8.game.entities.GoldenIdol;
import com.team3._8.game.entities.Keycard;
import com.team3._8.game.entities.SlippyWater;
import com.team3._8.game.entities.WarpPanel;

public class GameScreen implements Screen {
    final MazeGame GAME;

    // Constants in arbitrary units for Bob's size
    public static final int BOB_WIDTH = 15;
    public static final int BOB_HEIGHT = 15;

    private boolean paused;

    private OrthographicCamera camera;
    //private Bob bob;
    //private EvilBob evilBob;
    //private CollectableEntity keycard;
    private float timer;
    private Maze maze;
    private HUD hud;

    private static Map<String, Integer> eventTracker;

    public GameScreen(MazeGame game) {
        GAME = game;

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        hud = new HUD(new SpriteBatch());
        createLayers();
        createBob();
        createSlippyWater();
        createCheckin();
        createGoldenIdol();
        createWarpPanel();
        createEvilBob();
        createKeycard();
        createCamera(width, height);
        eventTracker = GameController.setEventMap();
    }

    private void createBob() {
        // The texture atlas containing Bob, and the sprite of Bob
        TextureAtlas atlas = new TextureAtlas("atlas/bob.atlas");
        Sprite bobSprite = new Sprite(atlas.findRegion("front-bob"));
        bobSprite.setPosition(100, 500);
        bobSprite.setSize(BOB_WIDTH, BOB_HEIGHT);
        EntityManager.add(new Bob(bobSprite, 50, -3));
        
    }

    private void createSlippyWater() {
        Texture texture = new Texture("slippyWater.png");
        Sprite waterSprite = new Sprite(texture);
        waterSprite.setPosition(350, 530);
        waterSprite.setSize(BOB_WIDTH, BOB_HEIGHT);
        EntityManager.add(new SlippyWater(waterSprite)); 
    }

    private void createCheckin() {
        Texture texture = new Texture("hidden.png");
        Sprite checkinSprite = new Sprite(texture);
        checkinSprite.setPosition(360, 219);
        checkinSprite.setSize(BOB_WIDTH, BOB_HEIGHT);
        EntityManager.add(new CheckinCode(checkinSprite)); 
    }

    private void createGoldenIdol() {
        Texture texture = new Texture("hidden.png");
        Sprite goldenSprite = new Sprite(texture);
        goldenSprite.setPosition(1230, 1160);
        goldenSprite.setSize(BOB_WIDTH, BOB_HEIGHT);
        EntityManager.add(new GoldenIdol(goldenSprite)); 
    }

    private void createWarpPanel() {
        Texture texture = new Texture("hidden.png");
        Sprite warpPanelSprite = new Sprite(texture);
        warpPanelSprite.setPosition(910, 345);
        warpPanelSprite.setSize(BOB_WIDTH, BOB_HEIGHT);
        EntityManager.add(new WarpPanel(warpPanelSprite));
    }

    private void createLayers() {
        String[] collidable_layers = {"Collision", "Doors"};
        maze = new Maze("Map/CSE_map.tmx", collidable_layers, "WinDoors", "EventTrigger");
    }

    static public void createEvilBob() {
        TextureAtlas atlas = new TextureAtlas("atlas/bob.atlas");
        Sprite evilBobSprite = new Sprite(atlas.findRegion("evil-bob"));
        evilBobSprite.setPosition(1000, 1050);
        evilBobSprite.setSize(BOB_WIDTH*2, BOB_HEIGHT*2);
        EntityManager.add(new EvilBob(evilBobSprite, 0));
    }
        
    private void createKeycard() {
        Texture keycardTexture = new Texture("keycard.png");
        Sprite keycardSprite = new Sprite(keycardTexture);
        keycardSprite.setPosition(20, 20);
        keycardSprite.setSize(BOB_WIDTH * 2, BOB_HEIGHT * 2);
        EntityManager.add(new Keycard(keycardSprite));
    }
    
    private void createCamera(float w, float h) {
        camera = new OrthographicCamera(w/2,h/2);
        camera.position.set(
            Bob.bob.getOriginX(),
            Bob.bob.getOriginY(),
            0);
        camera.zoom = 1f;
        camera.update();
    } 

    @Override
    public void render(float delta) {
        update(delta);
        draw(GAME.batch);
    }

    private void update(float delta) {
        // The input for the zoom in and out
        // Development modes
        boolean dev_zoom = false;
        paused = GameController.handleInput(camera, paused, dev_zoom);
        
        if (!paused) {
            
            // Checks for collision with evilBob
            //evilBobReturnData = evilBob.collision(bob);

            EntityManager.update(delta);
            
            // Handles interaction with characters
            //handleInteraction();
            
            // Moves bob in player direction (if not hitting a wall)
            timer += delta;
        }
        
        
        if (eventTracker.get("Negative") == 5 && HUD.achievments[3] == false) {
            HUD.addAchievement(3, 500);
        }
        int achievmentCount = 0;
        for (boolean get : HUD.achievments) {
            if (get) {
                achievmentCount++;
            }
        }
        if (achievmentCount == 7){
            HUD.addAchievement(7, 1500);
        }
        if (maze.HitsWinLayer(Bob.bob)) {
            GAME.setScreen(new WinScreen(GAME, HUD.score, (int) timer));
        }
        if (maze.HitsEventLayer(Bob.bob)) {
            eventTriggered("Negative");
        }
        // The code to check if the game has ended
        if (timer >= 300) {
            GAME.setScreen(new LoseScreen(GAME));
        }
    }

    /**
    * Allows event to be added to eventTracker
    *
    * @param eventName - name of event
    */
    public static void eventTriggered(String eventName) {
        eventTracker.put(eventName, eventTracker.get(eventName) + 1);
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen

        // Centres the camera on Bob and then updates it
        camera.position.set(
            Bob.bob.getOriginX(),
            Bob.bob.getOriginY(),
            0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        
        // Sprite batch drawing
        maze.renderMap(camera);
        
        GAME.batch.begin();
        EntityManager.draw(batch);
            
        batch.end();
            
        // The drawing of the HUD of the game
        hud.draw(GAME.font, GameController.formatTime(timer), eventTracker, Bob.bob, paused, GAME.viewport);
            
        // Sets rendered screens based on game state
        if (paused) {
            hud.pauseScreen(GAME.font, GAME.viewport);
        }
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
    public void dispose() {
        EntityManager.dispose();
    }
    @Override
    public void resize(int width, int height) {}
}
