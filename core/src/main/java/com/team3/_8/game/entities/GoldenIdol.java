package com.team3._8.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.team3._8.game.HUD;
import com.team3._8.game.Screens.GameScreen;

public class GoldenIdol extends InteractableEntity {
    
    public GoldenIdol(Sprite sprite) {
        super(sprite, 0);
    }

    public boolean startInteraction() {
        if (Gdx.input.isKeyJustPressed(Keys.E)) {
            GameScreen.eventTriggered("Hidden");
            HUD.addAchievement(5, 500);
            Bob.bob.addInventory("GoldenIdol");
            isExpired = true;
            return true;
        }
        return false;
    }

    public boolean stopInteraction() {
        return false;
    }
    
}
