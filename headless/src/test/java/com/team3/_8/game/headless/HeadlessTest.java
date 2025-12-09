package com.team3._8.game.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.team3._8.game.EntityManager;
import com.team3._8.game.entities.Bob;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

/**
 * Abstract class that all tests should inherit from.
 *
 * <p>This automatically loads libGDX before each test, and cleans up static state after each test.</p>
 */
public abstract class HeadlessTest {
    @BeforeEach
    public void beforeEach() {
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        HeadlessLauncher.main(new String[0]);
    }

    @AfterEach
    public void afterEach() {
        EntityManager.clear();
        Bob.bob = null;
    }
}
