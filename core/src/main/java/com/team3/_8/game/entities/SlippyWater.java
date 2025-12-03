package com.team3._8.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.team3._8.game.Screens.GameScreen;

public class SlippyWater extends InteractableEntity {
    
    public SlippyWater(Sprite sprite) {
        super(sprite, 0);
    }

    public boolean startInteraction() {
        GameScreen.eventTriggered("Negative");
        if (Bob.bob.hasItem("GoldenIdol")) {
            Bob.bob.setSlipping(true);
        }
        isExpired = true;
        return true;
    }

    public boolean stopInteraction() {
        return false;
    }
}
