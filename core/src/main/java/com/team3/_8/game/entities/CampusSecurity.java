package com.team3._8.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.team3._8.game.Maze;
import com.team3._8.game.Screens.GameScreen;

import java.util.HashMap;
import java.util.Map;

/** 
 * Defines the campus security geese characters. 
 */
public class CampusSecurity extends InteractableEntity {
    
    // Return data map used to send data to calling instance
    final Map<String, Boolean> returnData = new HashMap<>();
    private Animation<TextureRegion> campusSecurity;
    
    // Variables used to configure sprite
    private float stateTime = 0f;
    private boolean up = false;
    
    /**
    * Instantiates campus security object
    *
    * @param sprite - sprite to draw
    * @param speed - movement speed
    */
    public CampusSecurity(Sprite sprite, float speed) {
        super(sprite, speed);
        // Loads animation textures
        loadTextures();
    }

    public void update(float delta) {
        move(delta);
        updateCollisionBox();
    }
    
    /** Configures interaction with entity */
    @Override
    public boolean startInteraction() {
        // Sets command to MazeGame.java to reset
        // player position on collision with this sprite
        if (!Bob.bob.hasItem("GoldenIdol")) {
            Bob.bob.setPosition(100, 500);
        }
        if (!Bob.bob.hasReset()) {
            GameScreen.eventTriggered("Negative");
            Bob.bob.reset();
        }
        return true;
    }
    
    /** Configures end of interaction with entity */
    @Override
    public boolean stopInteraction() {
        // Stops reset of player position, when interaction has ended
        return false;
    }
    
    /** Loads the animation textures from atlas into the animation variables */
    private void loadTextures() {
        // Loads sprites from Texture atlas
        // Used to hold animation textures
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/security_geese.atlas"));
        
        // Loads animation frames
        Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions("walking");
        
        // Creates animation object
        this.campusSecurity = new Animation<TextureRegion>(0.1f, frames);
    }
    
    /**
    * Draws sprite and configures movement
    *
    * @param batch - Sprite to draw
    */
   @Override
    public void draw(SpriteBatch batch) {
        // Timer for animation
        stateTime += Gdx.graphics.getDeltaTime();
        
        // Sets animation frame
        TextureRegion current_animation = campusSecurity.getKeyFrame(stateTime, true);
        sprite.setRegion(current_animation);
        sprite.draw(batch);
    }
    
    /**
    * Moves the campus security sprites up or down based in passed boolean value
    */
    private void move(float delta) {
        // Configure the sprite to move up or down
        boolean[] movement_halter = Maze.hitsWall(this, delta);
        if (movement_halter[3]) {
            up = false;
        } else if (movement_halter[1]) {
            up = true;
        }
        
        // Moves sprite up or down
        if (up) {
            sprite.translateY(this.speed * delta);
        } else {
            sprite.translateY(-this.speed * delta);
        }
    }

    @Override
    public void updateCollisionBox(){
        // Corrects collision box size
        float inset = 8f;
        float newWidth = Math.max(0f, this.sprite.getWidth() - inset * 2f);
        this.collisionBox.setX(this.sprite.getX() + inset);
        this.collisionBox.setY(this.sprite.getY());
        this.collisionBox.setWidth(newWidth);
        this.collisionBox.setHeight(this.sprite.getHeight());
    } 
}
