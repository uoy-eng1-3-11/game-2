package com.team3._8.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team3._8.game.MazeGame;

public class TitleScreen implements Screen {
    final MazeGame GAME;

    public TitleScreen(MazeGame game) {
        GAME = game;
    }

    @Override
    public void render(float delta){}

    public void draw(SpriteBatch batch) {

    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void show() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
    @Override
    public void resize(int width, int height) {}
}
