package com.angrybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.angrybird.AngryBirdsGame;

public class LevelSelectionScreen implements Screen {
    private static final float BUTTON_WIDTH_PERCENTAGE = 0.25f;
    private static final float BUTTON_HEIGHT_PERCENTAGE = 0.15f;

    private static final float LEVEL_BUTTON_Y_PERCENTAGE = 0.45f;
    private static final float BUTTON_VERTICAL_SPACING = 0.05f;

    private AngryBirdsGame game;
    private Texture background;
    private Texture backButtonActive;
    private Texture backButtonInactive;
    private Texture[] levelButtonsActive;
    private Texture[] levelButtonsInactive;

    public LevelSelectionScreen(AngryBirdsGame game) {
        this.game = game;
        background = new Texture("levelSelector1.jpg");
    }

    @Override
    public void show() {
        // Back button
        backButtonInactive = new Texture("Back 1.png");
        backButtonActive = new Texture("Back 2.png");

        // Initialize level buttons (assuming 3 levels)
        levelButtonsActive = new Texture[3];
        levelButtonsInactive = new Texture[3];

        for (int i = 0; i < 3; i++) {
            levelButtonsActive[i] = new Texture("level" + (i + 1) + "_2.png");
            levelButtonsInactive[i] = new Texture("level" + (i + 1) + "_1.png");
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Calculate button sizes and positions
        int buttonWidth = (int) (BUTTON_WIDTH_PERCENTAGE * Gdx.graphics.getWidth());
        int buttonHeight = (int) (BUTTON_HEIGHT_PERCENTAGE * Gdx.graphics.getHeight());

        int levelButtonY = (int) (LEVEL_BUTTON_Y_PERCENTAGE * Gdx.graphics.getHeight());

        game.getBatch().begin();

        drawBackground();

        if (isButtonHovered(buttonWidth, buttonHeight, 30, 34)) {
            game.getBatch().draw(backButtonActive, 30, 34, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainMenuScreen(game));
            }
        } else {
            game.getBatch().draw(backButtonInactive, 30, 34, buttonWidth, buttonHeight);
        }

        for (int i = 0; i < 3; i++) {
            int buttonX = 100 + i * (buttonWidth + (int) (BUTTON_VERTICAL_SPACING * Gdx.graphics.getHeight()));
            if (isButtonHovered(buttonWidth, buttonHeight, buttonX, levelButtonY)) {
                game.getBatch().draw(levelButtonsActive[i], buttonX, levelButtonY, buttonWidth, buttonHeight);
                if (Gdx.input.isTouched()) {
                    if (i == 0) {
                        game.setScreen(new Level1_Birds(game));
                    } else if (i == 1) {
                        game.setScreen(new Level2_Birds(game));
                    } else if (i == 2) {
                        game.setScreen(new Level3_Birds(game));
                    } else {
                        System.out.println("Level " + (i + 1) + " Selected");
                    }
                }
            } else {
                game.getBatch().draw(levelButtonsInactive[i], buttonX, levelButtonY, buttonWidth, buttonHeight);
            }
        }

        game.getBatch().end();
    }

    private boolean isButtonHovered(int buttonWidth, int buttonHeight, int x, int y) {
        return Gdx.input.getX() > x && Gdx.input.getX() < x + buttonWidth &&
            Gdx.graphics.getHeight() - Gdx.input.getY() > y &&
            Gdx.graphics.getHeight() - Gdx.input.getY() < y + buttonHeight;
    }

    private void drawBackground() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float bgWidth = background.getWidth();
        float bgHeight = background.getHeight();

        float scale = Math.max(screenWidth / bgWidth, screenHeight / bgHeight);

        float scaledBgWidth = bgWidth * scale;
        float scaledBgHeight = bgHeight * scale;

        game.getBatch().draw(background, (screenWidth - scaledBgWidth) / 2, (screenHeight - scaledBgHeight) / 2,
            scaledBgWidth, scaledBgHeight);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        background.dispose();
        backButtonInactive.dispose();
        backButtonActive.dispose();
        for (int i = 0; i < 3; i++) {
            levelButtonsActive[i].dispose();
            levelButtonsInactive[i].dispose();
        }
    }
}
