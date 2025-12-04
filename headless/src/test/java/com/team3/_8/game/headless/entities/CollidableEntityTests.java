package com.team3._8.game.headless.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.team3._8.game.entities.CollidableEntity;
import com.team3._8.game.headless.AbstractHeadlessGdxTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CollidableEntityTests extends AbstractHeadlessGdxTest {
    class TestEntity extends CollidableEntity {
        public TestEntity(Sprite sprite, float speed) {
            super(sprite, speed);
        }

        public TestEntity(Sprite sprite, float speed, float sizeChange) {
            super(sprite, speed, sizeChange);
        }

        public void move(boolean[] movement_halter) {
        }

        public void setPosition(float x, float y) {
            sprite.setPosition(x, y);
        }
    }

    @Test
    public void constructsWithSizeChange() {
        Texture texture = new Texture("keycard.png");
        Sprite sprite = new Sprite(texture);
        TestEntity entity_1 = new TestEntity(sprite, 0.0f);
        TestEntity entity_2 = new TestEntity(sprite, 0.0f, 0.21f);

        assertEquals(
            entity_1.getCollisionBox().x - 0.21f,
            entity_2.getCollisionBox().x
        );
        assertEquals(
            entity_1.getCollisionBox().y - 0.21f,
            entity_2.getCollisionBox().y
        );
        assertEquals(
            entity_1.getCollisionBox().width + 0.42f,
            entity_2.getCollisionBox().width
        );
        assertEquals(
            entity_1.getCollisionBox().height + 0.42f,
            entity_2.getCollisionBox().height
        );
    }

    @Test
    public void disposeMinimisesCollisionBox() {
        Texture texture = new Texture("keycard.png");
        Sprite sprite = new Sprite(texture);
        TestEntity entity = new TestEntity(sprite, 0.0f);

        entity.dispose();

        assertEquals(0, entity.getCollisionBox().width);
        assertEquals(0, entity.getCollisionBox().height);
    }

    @Test
    public void isColliding() {
        Texture texture = new Texture("sprites/sprite_images/evil-bob_1.png");
        Sprite sprite = new Sprite(texture);

        TestEntity a = new TestEntity(sprite, 0.0f);
        a.setPosition(0.0f, 0.0f);
        a.update(0.0f);

        TestEntity b = new TestEntity(sprite, 0.0f);
        b.setPosition(sprite.getWidth() - 0.1f, 0.0f);
        b.update(0.0f);
        assertTrue(a.isColliding(b));

        TestEntity c = new TestEntity(sprite, 0.0f);
        c.setPosition(0.0f, sprite.getHeight() - 0.1f);
        c.update(0.0f);
        assertTrue(a.isColliding(c));
    }

    @Test
    public void isNotColliding() {
        Texture texture = new Texture("sprites/sprite_images/evil-bob_1.png");
        Sprite sprite = new Sprite(texture);

        TestEntity a = new TestEntity(sprite, 0.0f);
        a.setPosition(0.0f, 0.0f);
        a.update(0.0f);

        TestEntity b = new TestEntity(sprite, 0.0f);
        b.setPosition(sprite.getWidth(), 0.0f);
        b.update(0.0f);
        assertFalse(a.isColliding(b));

        TestEntity c = new TestEntity(sprite, 0.0f);
        c.setPosition(0.0f, sprite.getHeight());
        c.update(0.0f);
        assertFalse(a.isColliding(c));
    }
}

