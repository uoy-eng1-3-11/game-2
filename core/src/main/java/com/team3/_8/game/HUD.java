package com.team3._8.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team3._8.game.entities.Bob;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * This class creates the HUD of the game.
 *
 * <p>The idea is that you pass textures that will be rendered through to the class, then you can
 * have all the decision-making in here.
 *
 * <p>This avoids lots of passing textures through.
 */
public class HUD {

    private Batch batch;
    private final Texture keycard;
    private final Texture securityOverride;
    private final Texture goldenIdol;
    private final Texture pause;

    public static int score = 0;

    public static boolean[] achievements;
    private static float achievementTimer;
    private static ArrayList<Integer> achievementNumber;
    private String[] achievementText = {
        "The key to the kingdom: unlimited access",
        "Master of security: your pulling the strings",
        "Rocket Power: now fly",
        "Black Bob cat: you ARE unlucky",
        "Checked out: so we leave now, right",
        "Decked out: we gangsta now",
        "Spaced out: beam me up, scotty",
        "Completionist: but couldn't get a life"
    };

    public HUD() {
        this.keycard = new Texture("keycard.png");
        this.securityOverride = new Texture("SecurityOverride.png");
        this.goldenIdol = new Texture("goldenIdol.png");
        this.pause = new Texture("libgdx.png");
        achievementNumber = new ArrayList<Integer>();
        achievementTimer = 0;
        score = 0;
        achievements = new boolean[8];
    }

    /**
    * Draws the HUD data, such as time left & items collected
    *
    * @param font BitmapFont: The font used to render the timer
    * @param timer String: The formatted timer string
    * @param events Map: Contains data to display the event status
    * @param bob Bob: The Bob character, to extract the contents of his inventory from
    * @param isPaused boolean: Whether to draw paused HUD
    * @param viewport Viewport: Used to get the windows size for arranging text
    */
    public void draw(BitmapFont font, String timer, Map<String, Integer> events, Bob bob, boolean isPaused, Viewport viewport) {
        if (batch == null) batch = new SpriteBatch();

        // Gets screen size to arrange text
        float windowX = viewport.getScreenX();
        float windowY = viewport.getScreenY();
        float windowWidth = viewport.getScreenWidth();
        float windowHeight = viewport.getScreenHeight();

        // Changes batch to use screen co-ordinates not game one
        Matrix4 ortho = new Matrix4().setToOrtho2D(0, 0, windowWidth, windowHeight);
        batch.setProjectionMatrix(ortho);

        GlyphLayout layout = new GlyphLayout();
        String[] HUDText = {
            timer,
            "Positive: " + events.get("Positive"),
            "Negative: " + events.get("Negative"),
            "Hidden: " + events.get("Hidden"),
            "Score:" + score
        };
        float y = windowHeight - 10f - font.getCapHeight();
        // Sets text scale based on window width
        // 0.0015625 is just a scaling factor that equates to 1 at initial screen render size (640)
        font.getData().setScale((0.0015625f) * windowWidth);

        batch.begin();

        // Draws each line of text
        for (String text : HUDText) {
            layout.setText(font, text);
            float x = windowWidth - 10f - layout.width;

            font.draw(batch, text, windowX + x, windowY + y);
            y -= font.getLineHeight();
        }

        if (achievementTimer > 0f) {
            GlyphLayout achievementsGlyphLayout = new GlyphLayout(font, achievementText[achievementNumber.get(0)]);
            float textX = Gdx.graphics.getWidth()/2f - achievementsGlyphLayout.width/2f;
            float textY = 50;
            font.draw(batch, achievementsGlyphLayout, textX, textY);
            achievementTimer -= Gdx.graphics.getDeltaTime();
        } else if (!achievementNumber.isEmpty()) {
            achievementNumber.remove(0);
            if (!achievementNumber.isEmpty()) {
                achievementTimer = 5;
            }
        }

        this.drawTextures(bob, font, isPaused);

        batch.end();
    }

    /**
    * This method will draw the items collected on the screen
    *
    * @param bob Bob: The bob object so we can have a look at his inventory
    */
    private void drawTextures(Bob bob, BitmapFont font, boolean isPaused) {
        Set<String> bobInventory;
        bobInventory = bob.getInventory();

        for (String item : bobInventory) {
            switch (item) { // Switch for extendability
                case "Keycard":
                    batch.draw(this.keycard, 10, 325, 50, 50);
                    if (isPaused) {
                        font.draw(batch, "Now all doors are open.", 10, 325);
                    }
                    break;
                case "SecurityOverride":
                    batch.draw(this.securityOverride, 10, 400, 50, 50);
                    if (isPaused) {
                        font.draw(batch, "Now security won't try to get you.", 10, 400);
                    }
                    break;
                case "GoldenIdol":
                    batch.draw(this.goldenIdol, 10, 250, 50, 50);
                    if (isPaused) {
                        font.draw(batch, "You are immortal", 10, 250);
                    }
                    break;
                default:
            }
        }
    }

    /**
    * Draws pause screen onto the current active window
    *
    * @param font the font of any text on the pause screen to be used
    * @param viewport the current viewport of the game
    */
    public void pauseScreen(BitmapFont font, Viewport viewport) {
        batch.begin();
        font.draw(batch, "PAUSED", 340, 375);
        batch.draw(
            this.pause, (float) viewport.getScreenX() / 2, (float) viewport.getScreenY() / 2, 100, 100);
        batch.end();
    }

    public static void addAchievement(int achievementNum, int scoreWorth){
        achievementNumber.add(achievementNum);
        achievements[achievementNum] = true;
        achievementTimer = 5;
        score += scoreWorth;
    }
}
