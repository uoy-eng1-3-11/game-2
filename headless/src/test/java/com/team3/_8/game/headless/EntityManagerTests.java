package com.team3._8.game.headless;

import com.team3._8.game.EntityManager;
import com.team3._8.game.entities.Entity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntityManagerTests extends HeadlessTest {
    @Test
    public void entityIsAdded() {
        Entity entity = mock(Entity.class);

        EntityManager.add(entity);

        ArrayList<Entity> list = new ArrayList<Entity>();
        list.add(entity);

        assertEquals(EntityManager.entities, list);
    }

    @Test
    public void entityIsCleared() {
        Entity entity = mock(Entity.class);

        EntityManager.add(entity);
        EntityManager.clear();

        assertTrue(EntityManager.entities.isEmpty());
    }

    @Test
    public void entityIsDrawn() {
        Entity entity = mock(Entity.class);
        SpriteBatch batch = mock(SpriteBatch.class);

        EntityManager.add(entity);
        EntityManager.draw(batch);

        verify(entity, times(1)).draw(batch);
    }

    @Test
    public void entityIsDisposed() {
        Entity entity = mock(Entity.class);

        EntityManager.add(entity);
        EntityManager.dispose();

        verify(entity, times(1)).expire();
        verify(entity, times(1)).dispose();
    }
}

