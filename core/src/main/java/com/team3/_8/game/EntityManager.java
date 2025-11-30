package com.team3._8.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team3._8.game.entities.Bob;
import com.team3._8.game.entities.CollidableEntity;
import com.team3._8.game.entities.Entity;

public final class EntityManager {
    static ArrayList<Entity> entities = new ArrayList<Entity>();

    static ArrayList<Entity> addedEntities = new ArrayList<Entity>();

    static boolean isUpdating = false;

    /** Adds an entity to the entity manager.
     * 
     * @param entity the entity to add
     */
    public static void add(Entity entity){
        // Delays adding an entity if the manager is updating
        if (!isUpdating){
            entities.add(entity);
        } else {
            addedEntities.add(entity);
        }
    }

    /** Updates all entities.
     * 
     * @param delta the time since the last frame
     */
    public static void update(float delta) {
        isUpdating = true;
        ArrayList<Entity> removedEntities = new ArrayList<Entity>();
        for (Entity entity : entities) {
            entity.update(delta);

            if (entity.isExpired()) {
                removedEntities.add(entity);
            }
        }
        checkCollisions();
        isUpdating = false;

        // Adds all entities that where attempted to be added while updating.
        for (Entity entity : addedEntities) {
            entities.add(entity);
        }

        // Removes all expired entities
        for (Entity entity : removedEntities) {
            entities.remove(entity);
        }
    }

    private static void checkCollisions() {
        for (Entity entity : entities) {
            if (entity instanceof CollidableEntity && entity != Bob.bob) {
                CollidableEntity cEntity = (CollidableEntity) entity;
                cEntity.isColliding(Bob.bob);
            }
        }
    }

    /** Draws all the entities.
     * 
     * @param batch the {@code SpriteBatch} used for drawing 
     */
    public static void draw(SpriteBatch batch){
        for (Entity entity : entities){
            entity.draw(batch);
        }
    }
}
