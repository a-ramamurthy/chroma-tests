package com.mygdx.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class TestGameMain extends ApplicationAdapter {

	private static final int FRAME_COLS = 6; // #1
	private static final int FRAME_ROWS = 5; // #2
	private float xPos, yPos;
	Animation walkAnimation, swa, sja; // #3
	Texture walkSheet, soldierSheet; // #4
	TextureRegion[] walkFrames, soldierWalk, soldierJump; // #5
	SpriteBatch spriteBatch; // #6
	TextureRegion currentFrame; // #7

	float stateTime; // #8

	@Override
	public void create() {
		walkSheet = new Texture(Gdx.files.internal("sprite-animation1.png")); // #9
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS); // #10
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		walkAnimation = new Animation(0.025f, walkFrames); // #11
		spriteBatch = new SpriteBatch(); // #12
		stateTime = 0f; // #13
		Music music = Gdx.audio.newMusic(Gdx.files
				.internal("Wii Shop Channel Music.mp3"));
		music.play();
		xPos = 50f;
		yPos = 50f;

		soldierSheet = new Texture(Gdx.files.internal("soldiersheet.png"));
		TextureRegion[][] temp = TextureRegion.split(soldierSheet,
				soldierSheet.getWidth() / 5, soldierSheet.getHeight() / 4);
		soldierWalk = new TextureRegion[5];
		for (int i = 0; i < 5; i++)
			soldierWalk[i] = temp[0][i];
		soldierJump = new TextureRegion[3];
		for (int i = 0; i < 3; i++)
			soldierJump[i] = temp[1][i];
		swa = new Animation(0.25f, soldierWalk);
		sja = new Animation(0.25f, soldierJump);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // #14
		TextureRegion currentFrame;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (xPos < 600)
				xPos++;
			else
				xPos = 0;
			stateTime += Gdx.graphics.getDeltaTime(); // #15
		}

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			if (yPos < 500)
				yPos++;
			else
				yPos = 0;
			stateTime += Gdx.graphics.getDeltaTime();
		}

		spriteBatch.begin();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			currentFrame = swa.getKeyFrame(stateTime, true);
			spriteBatch.draw(currentFrame, xPos, yPos);
		} else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			currentFrame = sja.getKeyFrame(stateTime, true);
			spriteBatch.draw(currentFrame, xPos, yPos);
		} else
			spriteBatch.draw(soldierWalk[0], xPos, yPos);
		spriteBatch.end();
	}

	public void dispose() {
		spriteBatch.dispose();
		walkSheet.dispose();
	}
}
