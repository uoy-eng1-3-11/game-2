package com.team3._8.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.team3._8.game.Maze;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** 
 * This is the class for the character (Bob), inheriting from Entity.
 */
public final class Bob extends CollidableEntity {
    
    private final Set<String> inventory = new HashSet<String>();

    public static Bob bob;
    
    // Used to control animation time
    float stateTime = 0f;
    
    // Creates an inventory, which will be filled with String IDs
    private float collision__size_change; // offset for collision size, required for move()
    
    // Holds the name of the animation linked to the animation
    private Map<String, Animation<TextureRegion>> bob_animations;
    private String animationOverride = "";

    // Stores whether the player has been caught by security 
    private boolean hasReset = false;

    // Stores information the player on the player slipping
    private boolean slipping = false;
    private Vector2 slipDirection = Vector2.Zero;
    
    /** Initialises Bob.
     * 
     * @param sprite the sprite with the texture and position.
     * @param speed how far the entity can move each second.
     */
    public Bob(Sprite sprite, float speed) {
        super(sprite, speed);
        if (bob != null) {
            throw new RuntimeException("Two bob's can not exist at the same time");
        }
        bob = this;
        loadTextures();
    }
    
    /** Initialises Bob.
     * 
     * @param sprite the sprite with the texture and position.
     * @param speed how far the entity can move each second.
     * @param collision__size_change how much to increase the collider box size.
     */
    public Bob(Sprite sprite, float speed, float collision__size_change) {
        super(sprite, speed, collision__size_change);
        if (bob != null) {
            throw new RuntimeException("Two bob's can not exist at the same time");
        }
        bob = this;
        this.collision__size_change = collision__size_change;
        loadTextures();
    }
    
    /** loads the animation files from atlas into the animation variables */
    public void loadTextures() {
        // Loads sprites from Texture atlas
        TextureAtlas atlas = new TextureAtlas("atlas/bob.atlas");
        
        bob_animations = new HashMap<String, Animation<TextureRegion>>();
        // Loads animation frames
        Array<TextureAtlas.AtlasRegion> frontFrames = atlas.findRegions("front-bob");
        Array<TextureAtlas.AtlasRegion> rightFrames = atlas.findRegions("side-bob");
        Array<TextureAtlas.AtlasRegion> upFrames = atlas.findRegions("up-bob");
        Array<TextureAtlas.AtlasRegion> squashFrames = atlas.findRegions("squash-bob");
        Array<TextureAtlas.AtlasRegion> rocketSideFrames = atlas.findRegions("rocket-side-bob");
        Array<TextureAtlas.AtlasRegion> rocketUpFrames = atlas.findRegions("rocket-up-bob");
        
        // Creates animation object
        bob_animations.put("Front", new Animation<TextureRegion>(0.5f, frontFrames));
        bob_animations.put("Right", new Animation<TextureRegion>(0.5f, rightFrames));
        bob_animations.put("Up", new Animation<TextureRegion>(0.5f, upFrames));
        bob_animations.put("Squash", new Animation<TextureRegion>(5f, squashFrames));
        bob_animations.put("RocketLeft", new Animation<TextureRegion>(0.2f, rocketSideFrames));
        bob_animations.put("RocketUp", new Animation<TextureRegion>(0.2f, rocketUpFrames));
        
        // Flips textures into to change direction
        Array<TextureRegion> leftFrames = flipFrames(rightFrames, true, false);
        bob_animations.put("Left", new Animation<TextureRegion>(0.5f, leftFrames));
        
        Array<TextureRegion> RocketRight = flipFrames(rocketSideFrames, true, false);
        bob_animations.put("RocketRight", new Animation<TextureRegion>(0.5f, RocketRight));
        
        Array<TextureRegion> RocketDown = flipFrames(rocketUpFrames, false, true);
        bob_animations.put("RocketDown", new Animation<TextureRegion>(0.5f, RocketDown));
    }

    public void update(float delta) {
        move();
        updateCollisionBox(collision__size_change);
    }
    
    /** This method controls the movement of Bob, by moving him around the axis, depending on the input. */
    public void move() {
        boolean movement_halter[] = Maze.hitsWall(this, Gdx.graphics.getDeltaTime());
        float delta = Gdx.graphics.getDeltaTime();
        
        // Timer for animation
        stateTime += Gdx.graphics.getDeltaTime();
        // Default animation if no movement
        TextureRegion current_animation = bob_animations.get("Front").getKeyFrame(stateTime, true);

        // If the player is slipping, they move in that direction until a wall is hit.
        if (slipping) {
            this.sprite.translate(this.speed * delta * slipDirection.x, this.speed * delta * slipDirection.y);
            if (slipDirection.x == 1 && movement_halter[0]) {
                slipping = false;
            } else if (slipDirection.x == -1 && movement_halter[2]) {
                slipping = false;
            }

            if (slipDirection.y == 1 && movement_halter[3]) {
                slipping = false;
            } else if (slipDirection.y == -1 && movement_halter[1]) {
                slipping = false;
            }
            return;
        }

        slipDirection = new Vector2(0,0);
        
        // Checks movement on the X-axis
        if (((Gdx.input.isKeyPressed(Input.Keys.RIGHT)) || (Gdx.input.isKeyPressed(Input.Keys.D)))
            && !movement_halter[0]) {
            this.sprite.translateX(this.speed * delta);
            // Sets sprite animation to right
            if (animationOverride.isEmpty()) {
                current_animation = bob_animations.get("Right").getKeyFrame(stateTime, true);
            } else {
                current_animation = bob_animations.get("RocketRight").getKeyFrame(stateTime, true);
            }
            slipDirection.x = 1;
        } else if (((Gdx.input.isKeyPressed(Input.Keys.LEFT)) || (Gdx.input.isKeyPressed(Input.Keys.A)))
            && !movement_halter[2]) {
            this.sprite.translateX(-speed * delta);
            
            if (animationOverride.isEmpty()) {
                current_animation = bob_animations.get("Left").getKeyFrame(stateTime, true);
            } else {
                current_animation = bob_animations.get("RocketLeft").getKeyFrame(stateTime, true);
            }
            
            slipDirection.x = -1;
        }
        
        // Checks the movement on the Y-axis
        if (((Gdx.input.isKeyPressed(Input.Keys.UP)) || (Gdx.input.isKeyPressed(Input.Keys.W)))
            && !movement_halter[3]) {
            this.sprite.translateY(speed * delta);
            
            if (animationOverride.isEmpty()) {
                current_animation = bob_animations.get("Up").getKeyFrame(stateTime, true);
            } else {
                current_animation = bob_animations.get("RocketUp").getKeyFrame(stateTime, true);
            }
            slipDirection.y = 1;
        } else if (((Gdx.input.isKeyPressed(Input.Keys.DOWN)) || (Gdx.input.isKeyPressed(Input.Keys.S)))
            && !movement_halter[1]) {
            this.sprite.translateY(-speed * delta);
            
            if (animationOverride.isEmpty()) {
                current_animation = bob_animations.get("Front").getKeyFrame(stateTime, true);
            } else {
                current_animation = bob_animations.get("RocketDown").getKeyFrame(stateTime, true);
            }
            slipDirection.y = -1;
        }
        
        // Updates the animation.
        sprite.setRegion(current_animation);
    }
    
    /**
    * function to add item to bobs inventory if it is not full and item is not already present
    *
    * @param item ID for the item being added to inventory
    * @return boolean to indicate success of function, true if item was added successfully
    */
    public boolean addInventory(String item) {
        return inventory.add(item);
    }
    
    /**
    * function to remove item from bobs inventory if it contains that item
    *
    * @param item to be removed from the inventory
    * @return boolean to indicate success of function
    */
    public boolean removeInventory(String item) {
        return inventory.remove(item);
    }
    

    public Set<String> getInventory() {
        return this.inventory;
    }

    public boolean hasItem(String item){
        return inventory.contains(item);
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }
    
    public void setAnimation(String animationName) {
        this.animationOverride = animationName;
    }
    
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public void reset() {
        this.hasReset = true;
    }

    public boolean hasReset() {
        return hasReset;
    }

    public void setSlipping(boolean value) {
        slipping = value;
    }
    
    /** Creates an array of texture regions all flipped.
     * 
     * @param frameArray The Texture atlas to be flipped.
     * @param flipX if it should be flipped on the x axis.
     * @param flipY if it should be flipped on the y axis.
     * @return an array of texture regions flipped on the specified axis
     */
    private Array<TextureRegion> flipFrames(
        Array<TextureAtlas.AtlasRegion> frameArray, Boolean flipX, Boolean flipY) {
            // Flips frames
            Array<TextureRegion> frames = new Array<>();
            for (TextureRegion frame : frameArray) {
                TextureRegion temp_frame = new TextureRegion(frame);
                temp_frame.flip(flipX, flipY);
                frames.add(temp_frame);
            }
            return frames;
        }
    }
    