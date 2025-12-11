package com.team3._8.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/** 
 * This is the class for all the entities with collision boxes, to inherit from. 
 */
abstract public class CollidableEntity extends Entity {
    
    protected Rectangle collisionBox;
    
    /**
    * creates a collision box around the entity
    *
    * @param sprite Sprite: the sprite of the entity
    * @param speed float: the speed of the entity
    */
    public CollidableEntity(Sprite sprite, float speed) {
        super(sprite, speed);
        this.createBox();
    }
    
    /**
    * Creates a collision box around the entity
    *
    * @param sprite Sprite: the sprite of the entity
    * @param speed float: the speed of the entity
    * @param Collision__size_change float: the change in collision box size (must be negative for
    *     smaller)
    */
    public CollidableEntity(Sprite sprite, float speed, float Collision__size_change) {
        super(sprite, speed);
        this.createBox(Collision__size_change);
    }
    
    /** creates a collision box for the entity with the sprite information */
    protected void createBox() {
        this.collisionBox =
        new Rectangle(
            this.sprite.getX(),
            this.sprite.getY(),
            this.sprite.getWidth(),
            this.sprite.getHeight());
    }
        
    /**
    * This is the alternative of the createBox method, allowing for the box to be altered when it is
    * creates
    */
    protected void createBox(float Collision__size_change) {
        this.collisionBox =
        new Rectangle(
            this.getX() - Collision__size_change,
            this.getY() - Collision__size_change,
            this.sprite.getWidth() + Collision__size_change * 2,
            this.sprite.getHeight() + Collision__size_change * 2);
    }

    public void update(float delta) {
        updateCollisionBox();
    }

    /** Checks whether this entity is colliding with another specified other entity.
     * 
     * @param other the other {@link CollidableEntity} the check against.
     * @return whether the two entities are colliding.
     */
    public boolean isColliding(CollidableEntity other) {
        return collisionBox.overlaps(other.getCollisionBox());
    }
        
    public Rectangle getCollisionBox() {
        return collisionBox;
    }
        
    @Override
    public void dispose() {
        this.sprite.getTexture().dispose();
        
        // This is to minimise the collision box,
        // to avoid any potential collision box issues as much as possible
        this.collisionBox.width = 0;
        this.collisionBox.height = 0;
    }
        
    /** updates the collision box of the entity, should be called whenever move finishes */
    protected void updateCollisionBox() {
        this.collisionBox.setX(this.sprite.getX());
        this.collisionBox.setY(this.sprite.getY());
    }
            
    /**
    * updates the collision box of the entity, should be called whenever move finishes
    *
    * @param collision__size_change float: the change in collision box size (must be negative for
    *     smaller)
    */
    protected void updateCollisionBox(float collision__size_change) {
        this.collisionBox.setX(this.sprite.getX() - collision__size_change);
        this.collisionBox.setY(this.sprite.getY() - collision__size_change);
    }
}
        