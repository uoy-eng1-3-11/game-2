package com.team3._8.game.headless;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.team3._8.game.entities.Bob;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BobTests extends AbstractHeadlessGdxTest {
    @Test
    public void onlyOneBobWithFirstConstructor() {
        Texture texture = new Texture("keycard.png");
        Sprite sprite = new Sprite(texture);

        new Bob(sprite, 0.0f);

        assertThrows(RuntimeException.class, () -> {
            new Bob(sprite, 0.0f);
        });
    }

    @Test
    public void onlyOneBobWithSecondConstructor() {
        Texture texture = new Texture("keycard.png");
        Sprite sprite = new Sprite(texture);

        new Bob(sprite, 0.0f, 0.0f);

        assertThrows(RuntimeException.class, () -> {
            new Bob(sprite, 0.0f, 0.0f);
        });
    }
}

