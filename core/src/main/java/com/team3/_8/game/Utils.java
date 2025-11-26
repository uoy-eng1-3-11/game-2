package com.team3._8.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public final class Utils {

    /**
    * Positions text passed in to format nicely on the screen
    *
    * @param textLayout Text objects to position
    * @param top Position top
    * @param bottom Position bottom If top and bottom are both false, defaults to centre
    * @return map x,y values for positioning. Accessed via x1, x2 etc.
    */
    public static Map<String, Float> positionText(ExtendViewport viewport, GlyphLayout[] textLayout, boolean top, boolean bottom) {
        // Holds position values to return
        Map<String, Float> returnValues = new HashMap<>();
        // Used to evenly space lines
        int numLines = textLayout.length;
        // Spacing between lines
        float spacing = 20f;
        float offset;
        
        // Offsets text based on positioning on screen
        if (top) {
            offset = 120f;
        } else if (bottom) {
            offset = -120f;
        } else {
            offset = (numLines - 1) * spacing;
        }
        
        // Centre of the screen in world coordinates
        float centerX = viewport.getWorldWidth()/2f;
        float centerY = viewport.getWorldHeight()/2f;
        returnValues.put("centreX", centerX);
        returnValues.put("centreY", centerY);
        
        // Positions each line and store position values
        for (int i = 1; i <= numLines; i++) {
            float x = centerX - textLayout[i - 1].width / 2f;
            float y = centerY - i * spacing + offset;
            returnValues.put(("x" + i), x);
            returnValues.put(("y" + i), y);
        }
        
        return returnValues;
    }
}
