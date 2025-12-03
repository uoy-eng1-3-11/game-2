package com.team3._8.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * An object to create an entity which can be interacted with on collision.
 */
abstract class InteractableEntity extends CollidableEntity {
    
    public InteractableEntity(Sprite sprite, float speed) {
        super(sprite, speed);
    }

    @Override
    public boolean isColliding(CollidableEntity other) {
        if (other == Bob.bob) {
            return collision(other);
        }
        return collisionBox.overlaps(other.getCollisionBox());
    } 
    
    /**
    * Used to check for collision & runs interaction based on collision
    *
    * @param entity the other entity to be considered in the collision
    * @return a map of any event IDs and whether they are triggered
    */
    public boolean collision(CollidableEntity entity) {
        if (entity.getCollisionBox().overlaps(this.collisionBox)) {
            // Returns any data from the interaction back to the call instance
            return startInteraction();
        } else {
            return stopInteraction();
        }
    }
    
    /**
    * Starts the interaction for when the player interacts with the entity
    *
    * @return Map of eventIDs and whether it is triggered
    */
    public abstract boolean startInteraction();
    
    /**
    * Stops the interaction with entity if the interaction can be stopped early
    *
    * @return map of eventIDs and whether they have been stopped
    */
    public abstract boolean stopInteraction();
    
    //@Override
    //public void move(boolean[] movement_halter) {
    //    // Not currently implemented, but useful if you want the entity to move
    //    throw new UnsupportedOperationException("Unimplemented method 'move'");
    //}
}
