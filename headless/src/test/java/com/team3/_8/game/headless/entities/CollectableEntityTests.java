package com.team3._8.game.headless.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.team3._8.game.entities.Bob;
import com.team3._8.game.entities.CollectableEntity;
import com.team3._8.game.headless.AbstractHeadlessGdxTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CollectableEntityTests extends AbstractHeadlessGdxTest {
    class TestEntity extends CollectableEntity {
        public TestEntity(Sprite sprite, float speed, String type) {
            super(sprite, speed, type);
        }
    }

    @Test
    public void collectedWhenOverlapping() {
        Texture texture = new Texture("keycard.png");
        Sprite sprite = new Sprite(texture);

        Bob bob = new Bob(sprite, 0.0f);
        bob.getCollisionBox().x = 0.0f;
        bob.getCollisionBox().y = 0.0f;
        bob.getCollisionBox().width = 1.0f;
        bob.getCollisionBox().height = 1.0f;

        TestEntity cactus = new TestEntity(sprite, 0.0f, "cactus");
        cactus.getCollisionBox().x = 0.9f;
        cactus.getCollisionBox().y = -0.9f;
        cactus.getCollisionBox().width = 1.0f;
        cactus.getCollisionBox().height = 1.0f;

        assertTrue(
            cactus.collected(bob),
            "cactus should be collected"
        );
        assertTrue(
            bob.hasItem("cactus"),
            "\"cactus\" should be added to the inventory"
        );
    }

    @Test
    public void notCollectedWhenNotOverlapping() {
        Texture texture = new Texture("keycard.png");
        Sprite sprite = new Sprite(texture);

        Bob bob = new Bob(sprite, 0.0f);
        bob.getCollisionBox().x = 0.0f;
        bob.getCollisionBox().y = 0.0f;
        bob.getCollisionBox().width = 1.0f;
        bob.getCollisionBox().height = 1.0f;

        TestEntity cactus = new TestEntity(sprite, 0.0f, "cactus");
        cactus.getCollisionBox().x = 1.0f;
        cactus.getCollisionBox().y = 0.0f;
        cactus.getCollisionBox().width = 1.0f;
        cactus.getCollisionBox().height = 1.0f;

        assertFalse(
            cactus.collected(bob),
            "cactus should not be collected"
        );
        assertFalse(
            bob.hasItem("cactus"),
            "\"cactus\" should not be added to the inventory"
        );
    }
}

