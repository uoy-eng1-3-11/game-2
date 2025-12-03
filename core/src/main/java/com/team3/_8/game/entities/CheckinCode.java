package com.team3._8.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team3._8.game.HUD;
import com.team3._8.game.Screens.GameScreen;

public class CheckinCode extends InteractableEntity {

    private BitmapFont font;
    
    public CheckinCode(Sprite sprite) {
        super(sprite, 0);
        font = new BitmapFont();
    }

    public boolean startInteraction() {
        if (Gdx.input.isKeyJustPressed(Keys.E)) {
            GameScreen.eventTriggered("Hidden");
            HUD.addAchievement("Checked out: so we leave now, right", 500);
            Bob.bob.setPosition(1550, 730);
            isExpired = true;
        }
        return true;
    }

    public boolean stopInteraction() {
        return false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        this.sprite.draw(batch);
        if (Bob.bob.isColliding(this)) {
            font.draw(batch, "Press E", getOriginX(), getOriginY());
        }
    }
    
}
