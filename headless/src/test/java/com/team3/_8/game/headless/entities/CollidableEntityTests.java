package com.team3._8.game.headless;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.team3._8.game.entities.CollidableEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}

