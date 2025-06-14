package com.angrybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.angrybird.AngryBirdsGame;

public class VictoryScreen implements Screen {
    private static final float BUTTON_WIDTH_PERCENTAGE = 0.25f;
    private static final float BUTTON_HEIGHT_PERCENTAGE = 0.15f;
    private static final float BUTTON_VERTICAL_SPACING = 0.05f;

    private AngryBirdsGame game;
    private SpriteBatch batch;
    private Texture background;
    private Texture backButtonActive;
    private Texture backButtonInactive;

    private boolean switchToMainMenu = false;

    public VictoryScreen(AngryBirdsGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.background = new Texture("Victory.png");
    }

    @Override
    public void show() {
        backButtonInactive = new Texture("Back 1.png");
        backButtonActive = new Texture("Back 2.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int buttonWidth = (int) (BUTTON_WIDTH_PERCENTAGE * Gdx.graphics.getWidth());
        int buttonHeight = (int) (BUTTON_HEIGHT_PERCENTAGE * Gdx.graphics.getHeight());

        int backButtonX = 20;
        int backButtonY = Gdx.graphics.getHeight() - buttonHeight - 15;

        batch.begin();
        drawBackground();

        if (isButtonHovered(buttonWidth, buttonHeight, backButtonX, backButtonY)) {
            batch.draw(backButtonActive, backButtonX, backButtonY, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                switchToMainMenu = true;
            }
        } else {
            batch.draw(backButtonInactive, backButtonX, backButtonY, buttonWidth, buttonHeight);
        }

        batch.end();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.D)) {
            game.setScreen(new DefeatedScreen(game));
        }

        if (switchToMainMenu) {
            game.setScreen(new MainMenuScreen(game));
        }
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

        batch.draw(background, (screenWidth - scaledBgWidth) / 2, (screenHeight - scaledBgHeight) / 2,
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
        batch.dispose();
        background.dispose();
        backButtonInactive.dispose();
        backButtonActive.dispose();
    }
}
