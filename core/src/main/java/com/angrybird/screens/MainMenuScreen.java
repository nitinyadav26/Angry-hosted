package com.angrybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.angrybird.AngryBirdsGame;

public class MainMenuScreen implements Screen {
    private static final float BUTTON_WIDTH_PERCENTAGE = 0.25f;
    private static final float BUTTON_HEIGHT_PERCENTAGE = 0.15f;
    private static final float PLAY_BUTTON_Y_PERCENTAGE = 0.45f;
    private static final float SAVED_BUTTON_Y_PERCENTAGE = 0.35f;
    private static final float EXIT_BUTTON_Y_PERCENTAGE = 0.25f;
    private static final float BUTTON_VERTICAL_SPACING = 0.05f;

    private AngryBirdsGame game;
    private Texture background;
    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture savedButtonInactive;
    private Texture saveduttonActive;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;
    private Texture logoImage;

    public MainMenuScreen(AngryBirdsGame game) {
        this.game = game;
        background = new Texture("ab bg-3.png");

        Pixmap pixmap = new Pixmap(Gdx.files.internal("cursor-3.png"));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);
    }

    @Override
    public void show() {
        playButtonInactive = new Texture("play button-2.png");
        playButtonActive = new Texture("play button-1.png");
        savedButtonInactive = new Texture("saved-2.png");
        saveduttonActive = new Texture("saved-1.png");
        exitButtonInactive = new Texture("exit-2.png");
        exitButtonActive = new Texture("exit-1.png");
        logoImage = new Texture("textAB.png");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int buttonWidth = (int) (BUTTON_WIDTH_PERCENTAGE * Gdx.graphics.getWidth());
        int buttonHeight = (int) (BUTTON_HEIGHT_PERCENTAGE * Gdx.graphics.getHeight());
        int playButtonY = (int) (PLAY_BUTTON_Y_PERCENTAGE * Gdx.graphics.getHeight());
        int savedButtonY = playButtonY - buttonHeight - (int) (BUTTON_VERTICAL_SPACING * Gdx.graphics.getHeight());
        int exitButtonY = savedButtonY - buttonHeight - (int) (BUTTON_VERTICAL_SPACING * Gdx.graphics.getHeight());

        int logoWidth = logoImage.getWidth();
        int logoHeight = logoImage.getHeight();
        int logoX = (Gdx.graphics.getWidth() - logoWidth) / 2;
        int logoY = playButtonY + buttonHeight + (int) (BUTTON_VERTICAL_SPACING * Gdx.graphics.getHeight() * 0.5f);

        game.getBatch().begin();
        drawBackground();

        game.getBatch().draw(logoImage, logoX, logoY, logoWidth, logoHeight);

        if (isButtonHovered(buttonWidth, buttonHeight, playButtonY)) {
            game.getBatch().draw(playButtonActive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), playButtonY, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                game.setScreen(new LevelSelectionScreen(game));
            }
        } else {
            game.getBatch().draw(playButtonInactive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), playButtonY, buttonWidth, buttonHeight);
        }

        if (isButtonHovered(buttonWidth, buttonHeight, savedButtonY)) {
            game.getBatch().draw(saveduttonActive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), savedButtonY, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                game.setScreen(new LoadSavedGames(game));
            }
        } else {
            game.getBatch().draw(savedButtonInactive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), savedButtonY, buttonWidth, buttonHeight);
        }

        if (isButtonHovered(buttonWidth, buttonHeight, exitButtonY)) {
            game.getBatch().draw(exitButtonActive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), exitButtonY, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.getBatch().draw(exitButtonInactive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), exitButtonY, buttonWidth, buttonHeight);
        }

        game.getBatch().end();
    }

    private boolean isButtonHovered(int buttonWidth, int buttonHeight, int buttonY) {
        return Gdx.input.getX() > (Gdx.graphics.getWidth() / 2 - buttonWidth / 2) &&
            Gdx.input.getX() < (Gdx.graphics.getWidth() / 2 + buttonWidth / 2) &&
            Gdx.graphics.getHeight() - Gdx.input.getY() > buttonY &&
            Gdx.graphics.getHeight() - Gdx.input.getY() < buttonY + buttonHeight;
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
        playButtonInactive.dispose();
        playButtonActive.dispose();
        savedButtonInactive.dispose();
        saveduttonActive.dispose();
        exitButtonInactive.dispose();
        exitButtonActive.dispose();
        logoImage.dispose();
    }
}
