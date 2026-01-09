package com.team3._8.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.team3._8.game.EntityManager;
import com.team3._8.game.HUD;
import com.team3._8.game.TextBubble;
import com.team3._8.game.Screens.GameScreen;

import java.util.HashMap;

/**
 * Defines the evil bob character used in the surprise event.
 */
public class EvilBob extends InteractableEntity {
	// Return data map used to send data to calling instance
	final HashMap<String, Boolean> returnData = new HashMap<>();
	// Conversation script for the character to use
	private final String[] script = {
		"You dare\nenter my realm",
		"With a stolen\nkeycard",
		"Give it to me\nor face the\nconsequence",
		"Y: Give Keycard\nN: Keep Keycard"
	};
	private Animation<TextureRegion> evilBob;

	// Text bubble settings for character
	private TextBubble textBubble;
	private boolean textBubbleVisible = false;
	private BitmapFont font;
	// Variables used to control character conversation
	private int conversationPointer = 0;
	private boolean conversationReset = false;
	// Used to control animation time
	private float stateTime = 0f;

	/**
	* Creates character, loads character Texture + Animation, and creates text-bubble for character
	*/
	public EvilBob(Sprite sprite, float speed) {
		super(sprite, speed);
		loadTextures();
		createTextBubble();
	}

	/**
	* Handles interaction with character when started
	*
	* @return map of commands & their state for the calling class to use useful to control effects
	*     etc. based on interaction
	*/
	@Override
	public boolean startInteraction() {
		// Controls final option section of interaction
		boolean skipChoice = false;

		// Makes textBubbleVisible if no already
		if (!textBubbleVisible) {
			textBubbleVisible = textBubble.hideShow();
		}
		// Handles interaction as player presses E
		if (Gdx.input.isKeyJustPressed(Input.Keys.E) || (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && conversationPointer > 0) && !conversationReset) {
			if (Bob.bob.hasItem("Keycard")) {
				// Loads each line of the interaction text
				textBubble.setText(script[conversationPointer]);
				conversationPointer += 1;
				// Resets conversation if end reached
				if (conversationPointer > script.length - 1) {
					conversationPointer = 0;
					conversationReset = true;
					skipChoice = true;
				}
			} else {
				// Message to player if they don't have the keycard needed for this
				textBubble.setText("Go Away!\nYou're missing\nmy keycard");
			}
		}

		if (conversationReset && !skipChoice) {
			// Final step of interaction where player given a choice
			if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
				textBubble.setText("Get out of here!");

				GameScreen.eventTriggered("Positive");
				HUD.addAchievement(2, 250);

                Bob.bob.setAnimation("Rocket");
                Bob.bob.setSpeed(150);
				Bob.bob.removeInventory("Keycard");

				createTripWire(1000, 550);

				conversationReset = false;
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.N) && !Bob.bob.hasItem("SecurityOverride")) {
				textBubble.setText("Release Security!");

				GameScreen.eventTriggered("Negative");

				createSecurityGoose(330, 180);
				createSecurityGoose(350, 740);
				createSecurityGoose(1175, 490);
				createSecurityGoose(1250, 1030);

				createSecurityOverride(1410, 490);

				isExpired = true;

				conversationReset = false;
			}
		}

		return true;
	}

	/** Creates a security goose entity on the specified position.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	private void createSecurityGoose(int x, int y) {
		TextureAtlas atlas = new TextureAtlas("atlas/security_geese.atlas");
        Sprite securitySprite = new Sprite(atlas.findRegion("walking"));
    	securitySprite.setSize(30, 30);
		securitySprite.setPosition(x, y);
		EntityManager.add(new CampusSecurity(securitySprite, 50));
	}

	/** Creates a security goose entity on the specified position.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	private void createSecurityOverride(int x, int y) {
		Texture texture = new Texture("SecurityOverride.png");
        Sprite securityOverrideSprite = new Sprite(texture);
    	securityOverrideSprite.setSize(30, 30);
		securityOverrideSprite.setPosition(x, y);
		EntityManager.add(new SecurityOverride(securityOverrideSprite));
	}

	/** Creates a security goose entity on the specified position.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	private void createTripWire(int x, int y) {
		Texture texture = new Texture("tripWire.png");
        Sprite tripwireSprite = new Sprite(texture);
    	tripwireSprite.setSize(30, 30);
		tripwireSprite.setPosition(x, y);
		EntityManager.add(new TripWire(tripwireSprite));
	}

	/**
	* Handles destruction and reset of interaction if player walks away during / after it
	*
	* @return map of commands & their state for the calling class to use useful to control effects
	*     etc. based on interaction
	*/
	@Override
	public boolean stopInteraction() {
		if (textBubbleVisible) {
			// Resets conversation & text
			conversationPointer = 0;
			textBubble.setText("Interact: E");

			// Hides text-bubble
			textBubbleVisible = textBubble.hideShow();
		}

		return false;
	}

	/**
	* Draws evilBob character animation & text-bubble
	*
	* @param batch - batch from calling class
	*/
	@Override
	public void draw(SpriteBatch batch) {
		// Timer for animation
		stateTime += Gdx.graphics.getDeltaTime();

		// Sets sprite's animation to next frame
		TextureRegion current_animation = evilBob.getKeyFrame(stateTime, true);
		sprite.setRegion(current_animation);


		// Draws text to instruct advance of conversation
		if (conversationPointer > 0) {
			font.setColor(Color.WHITE);
			font.draw(batch, "Next: E", 1105, 1085);
		}
		textBubble.draw(batch, sprite.getX(), sprite.getY());
		sprite.draw(batch);
	}

	/** Updates the collision box to the placement of the sprite */
	protected void updateCollisionBox() {
		this.collisionBox.setX(this.sprite.getX());
		this.collisionBox.setY(this.sprite.getY());
	}

	/** Creates TextBubble */
	private void createTextBubble() {
		font = new BitmapFont();
		Texture bubble = new Texture("speech_bubble.png");
		textBubble = new TextBubble(bubble, font, 170, 120);
		textBubble.setText("Interact: E");
	}

	/** Loads the sprite and animation textures from atlas into the animation variables */
	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/bob.atlas"));

		// Loads animation frames
		Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions("evil-bob");

		// Creates animation object
		this.evilBob = new Animation<TextureRegion>(0.5f, frames);
	}
}
