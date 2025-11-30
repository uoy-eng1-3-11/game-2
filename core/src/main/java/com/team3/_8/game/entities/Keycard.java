package com.team3._8.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.team3._8.game.Maze;
import com.team3._8.game.Screens.GameScreen;

public class Keycard extends CollectableEntity{

    public Keycard(Sprite sprite) {
        super(sprite, 0, "Keycard");
    }

    @Override
    public boolean collected(Bob bob) {
        if (super.collected(bob)) {
            GameScreen.eventTriggered("Positive");
            Maze.removeCollisionLayer("Doors");
            Maze.removeVisibleLayer("ClosedDoors");
            isExpired = true;
            return true;
        }
        return false;
    }
    
}
