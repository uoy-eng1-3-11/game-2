package com.team3._8.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.team3._8.game.Screens.GameScreen;

public class TripWire extends InteractableEntity {
    
    public TripWire(Sprite sprite) {
        super(sprite, 0);
    }

    public boolean startInteraction() {
        GameScreen.eventTriggered("Negative");
        if (!Bob.bob.hasItem("GoldenIdol")) {
            Bob.bob.setAnimation("");
            Bob.bob.setSpeed(50);
        }
        isExpired = true;
        return true;
    }

    public boolean stopInteraction() {
        return false;
    }
}
