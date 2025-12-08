package com.team3._8.game.headless.entities;

import com.team3._8.game.entities.Bob;
import com.team3._8.game.headless.AbstractHeadlessGdxTest;

import com.badlogic.gdx.graphics.g2d.Sprite;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BobTests extends AbstractHeadlessGdxTest {
    @Test
    public void onlyOneBobWithFirstConstructor() {
        Sprite sprite = mock(Sprite.class);

        new Bob(sprite, 0.0f);

        assertThrows(RuntimeException.class, () -> {
            new Bob(sprite, 0.0f);
        });
    }

    @Test
    public void onlyOneBobWithSecondConstructor() {
        Sprite sprite = mock(Sprite.class);

        new Bob(sprite, 0.0f, 0.0f);

        assertThrows(RuntimeException.class, () -> {
            new Bob(sprite, 0.0f, 0.0f);
        });
    }
}

