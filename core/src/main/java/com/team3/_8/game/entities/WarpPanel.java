package com.team3._8.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.team3._8.game.HUD;
import com.team3._8.game.Screens.GameScreen;

public class WarpPanel extends InteractableEntity {
    
    public WarpPanel(Sprite sprite) {
        super(sprite, 0);
    }

    public boolean startInteraction() {
        GameScreen.eventTriggered("Hidden");
        Bob.bob.setPosition(1111, 999);
        HUD.addAchievement(6, 500);
        isExpired = true;
        return true;
    }

    public boolean stopInteraction() {
        return false;
    }
}
