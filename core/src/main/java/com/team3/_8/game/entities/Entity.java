package com.team3._8.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This is the class for all the entities in the game, to inherit from.
 */
public abstract class Entity {
    
    protected Sprite sprite;
    protected float speed;
    protected boolean isExpired;
    
    /**
    * Creates the entity based on the sprite given and creates its collision box
    *
    * @param sprite This is the sprite passed through, containing the position and the texture
    * @param speed The speed of the entity
    */
    public Entity(Sprite sprite, float speed) {
        this.sprite = sprite;
        this.speed = speed;
    }

    public abstract void update(float delta);
    
    /**
    * Draws the sprite on the spritebatch provided
    *
    * @param batch sprite batch to be drawn
    */
    public void draw(SpriteBatch batch) {
        this.sprite.draw(batch);
    }
    
    /** Disposes of the sprite texture */
    public void dispose() {
        this.sprite.getTexture().dispose();
    }
    
    public Sprite getSprite() {
        return sprite;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public float getX() {
        return sprite.getX();
    }
    
    public float getY() {
        return sprite.getY();
    }

    public float getOriginX() {
        return sprite.getX() + sprite.getWidth()/2f;
    }

    public float getOriginY() {
        return sprite.getY() + sprite.getHeight()/2f;
    }

    public boolean isExpired() {
        return isExpired;
    }
}
