package com.team3._8.game.Screens;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team3._8.game.GameController;
import com.team3._8.game.HUD;
import com.team3._8.game.Maze;
import com.team3._8.game.MazeGame;
import com.team3._8.game.entities.Bob;
import com.team3._8.game.entities.CollectableEntity;
import com.team3._8.game.entities.EvilBob;

public class GameScreen implements Screen {
    final MazeGame GAME;

    private boolean paused;

    private OrthographicCamera camera;
    private Bob bob;
    private EvilBob evilBob;
    private CollectableEntity keycard;
    private float timer;
    private Maze maze;
    private HUD hud;

    private Map<String, Integer> eventTracker;

    public GameScreen(MazeGame game) {
        GAME = game;

        hud = new HUD(new SpriteBatch());
        String[] collidable_layers = {"Collision", "Doors"};
        maze = new Maze("Map/CSE_map.tmx", collidable_layers, "WinDoors", "EventTrigger");
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
        
        // Configures game if player collects keycard
        if (keycard.collected(bob)) {
            eventTriggered("Positive");
            evilBob.setPlayerHasKeycard(true);
            maze.removeCollisionLayer("Doors");
            maze.removeVisibleLayer("ClosedDoors");
        }
        
        if (!paused) {
            boolean[] movement_halter = maze.hitsWall(bob, Gdx.graphics.getDeltaTime());
            
            // Checks for collision with evilBob
            evilBobReturnData = evilBob.collision(bob);
            
            // Handles interaction with characters
            handleInteraction();
            
            // Moves bob in player direction (if not hitting a wall)
            bob.move(movement_halter);
            timer += delta;
        }
        
        // Centres the camera on Bob and then updates it
        camera.position.set(
            bobSprite.getX() + bobSprite.getWidth() / 2,
            bobSprite.getY() + bobSprite.getHeight() / 2,
            0);
        camera.update();
        
        
            if (maze.HitsWinLayer(bob)) {
                GAME.setScreen(new WinScreen(GAME));
            }
            if (maze.HitsEventLayer(bob)) {
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
    private void eventTriggered(String eventName) {
        eventTracker.put(eventName, eventTracker.get(eventName) + 1);
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen
        batch.setProjectionMatrix(camera.combined);
        
        // Sprite batch drawing
        maze.renderMap(camera);
        
        GAME.batch.begin();
        evilBob.draw(batch, 1000, 1050);
        bob.draw(batch);
        keycard.draw(batch);
        
        if (campusSecurityCreated) {
            int mod = 0;
            for (int i = 0; i < 5; i++) {
                allCampusSecuritySprites[i].draw(
                    batch,
                    870 + mod,
                    1150,
                    maze.hitsWall(allCampusSecuritySprites[i], Gdx.graphics.getDeltaTime()));
                mod += 35;
            }
        }
        
            
        batch.end();
            
        // The drawing of the HUD of the game
        hud.draw(GAME.font, GameController.formatTime(timer), eventTracker, bob, paused, GAME.viewport);
            
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
    public void dispose() {}
    @Override
    public void resize(int width, int height) {}
}
