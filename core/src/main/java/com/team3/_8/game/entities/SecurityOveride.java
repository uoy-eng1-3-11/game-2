package com.team3._8.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.team3._8.game.EntityManager;
import com.team3._8.game.HUD;
import com.team3._8.game.Screens.GameScreen;

public class SecurityOveride extends CollectableEntity{

    public SecurityOveride(Sprite sprite) {
        super(sprite, 0, "SecurityOverride");
    }
    
    @Override
    public boolean collected(Bob bob) {
        if (super.collected(bob)) {
            GameScreen.eventTriggered("Positive");
            HUD.addAchievement("Master of security: with this, you are secure", 300);
            GameScreen.createEvilBob();
            for (Entity entity : EntityManager.entities) {
                if (entity instanceof CampusSecurity) {
                    entity.isExpired = true;
                }
            }
            isExpired = true;
            return true;
        }
        return false;
    }
}
