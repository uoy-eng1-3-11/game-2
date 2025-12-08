package com.team3._8.game.headless;

import com.team3._8.game.TextBubble;
import com.team3._8.game.headless.AbstractHeadlessGdxTest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TextBubbleTests extends AbstractHeadlessGdxTest {
    @Test
    public void hideShow() {
        TextBubble bubble = new TextBubble(
            mock(Texture.class),
            mock(BitmapFont.class),
            5, 5
        );

        // Should alternate, being hidden by default.
        assertTrue(bubble.hideShow());
        assertFalse(bubble.hideShow());
        assertTrue(bubble.hideShow());
        assertFalse(bubble.hideShow());
    }

    @Test
    public void notDrawnByDefault() {
        Texture texture = mock(Texture.class);
        BitmapFont font = mock(BitmapFont.class);
        SpriteBatch batch = mock(SpriteBatch.class);
        TextBubble bubble = new TextBubble(texture, font, 5, 7);

        bubble.setText("Hello, world!");
        bubble.draw(batch, 10, 20);

        verify(font, never()).draw(
            batch,
            "Hello, world!",
            10.882353f,
            13.958334f
        );
    }

    @Test
    public void drawnWhenVisible() {
        Texture texture = mock(Texture.class);
        BitmapFont font = mock(BitmapFont.class);
        SpriteBatch batch = mock(SpriteBatch.class);
        TextBubble bubble = new TextBubble(texture, font, 5, 5);

        bubble.setText("Hello, world!");
        bubble.hideShow();
        bubble.draw(batch, 10, 10);

        verify(font, times(1)).setColor(Color.BLACK);
        verify(font, times(1)).draw(
            batch,
            "Hello, world!",
            10.882353f,
            13.958334f
        );
        verify(font, times(1)).setColor(Color.WHITE);
    }
}

