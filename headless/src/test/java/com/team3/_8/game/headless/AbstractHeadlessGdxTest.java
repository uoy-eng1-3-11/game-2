package com.team3._8.game.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.team3._8.game.EntityManager;
import com.team3._8.game.entities.Bob;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public abstract class AbstractHeadlessGdxTest {
    @BeforeEach
    public void setup() {
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        HeadlessLauncher.main(new String[0]);

        EntityManager.clear();

        Bob.bob = null;
    }
}
