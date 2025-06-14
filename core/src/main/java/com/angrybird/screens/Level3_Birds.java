package com.angrybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.angrybird.AngryBirdsGame;

public class Level3_Birds implements Screen {
    private static final float BUTTON_WIDTH_PERCENTAGE = 0.25f;
    private static final float BUTTON_HEIGHT_PERCENTAGE = 0.15f;
    private static final float BIRD_BUTTON_WIDTH_PERCENTAGE = 0.15f;
    private static final float BIRD_BUTTON_HEIGHT_PERCENTAGE = 0.26f;
    private static final float BIRD_BUTTON_Y_PERCENTAGE = 0.24f;
    private static final float BUTTON_VERTICAL_SPACING = 0.08f;

    private AngryBirdsGame game;
    private Texture background;
    private Texture bird1Active;
    private Texture bird1Inactive;
    private Texture bird2Active;
    private Texture bird2Inactive;
    private Texture bird3Active;
    private Texture bird3Inactive;
    private Texture proceedButtonActive;
    private Texture proceedButtonInactive;
    private Texture titleImage;
    private Texture backButtonInactive;
    private Texture backButtonActive;

    public Level3_Birds(AngryBirdsGame game) {
        this.game = game;
        background = new Texture("levelbg.jpeg");
        titleImage = new Texture("textLevel1.jpg");
    }

    @Override
    public void show() {
        bird1Inactive = new Texture("Salma1.png");
        bird1Active = new Texture("Salma2.png");
        bird2Inactive = new Texture("Mallu1.png");
        bird2Active = new Texture("Mallu2.png");
        bird3Inactive = new Texture("Diggi1.jpg");
        bird3Active = new Texture("Diggi2.jpg");

        proceedButtonInactive = new Texture("Proceed1.png");
        proceedButtonActive = new Texture("Proceed2.png");

        backButtonInactive = new Texture("Back 1.png");
        backButtonActive = new Texture("Back 2.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int buttonWidth = (int) (BUTTON_WIDTH_PERCENTAGE * Gdx.graphics.getWidth());
        int buttonHeight = (int) (BUTTON_HEIGHT_PERCENTAGE * Gdx.graphics.getHeight());

        int birdButtonWidth = (int) (BIRD_BUTTON_WIDTH_PERCENTAGE * Gdx.graphics.getWidth());
        int birdButtonHeight = (int) (BIRD_BUTTON_HEIGHT_PERCENTAGE * Gdx.graphics.getHeight());

        int birdButtonY = (int) (BIRD_BUTTON_Y_PERCENTAGE * Gdx.graphics.getHeight());

        int totalBirdButtonsWidth = 3 * birdButtonWidth + 2 * (int) (BUTTON_VERTICAL_SPACING * Gdx.graphics.getHeight());

        int bird1X = (Gdx.graphics.getWidth() - totalBirdButtonsWidth) / 2;
        int bird2X = bird1X + birdButtonWidth + (int) (BUTTON_VERTICAL_SPACING * Gdx.graphics.getHeight());
        int bird3X = bird2X + birdButtonWidth + (int) (BUTTON_VERTICAL_SPACING * Gdx.graphics.getHeight());

        int proceedButtonY = birdButtonY - buttonHeight - (int) (BUTTON_VERTICAL_SPACING * Gdx.graphics.getHeight());
        int proceedButtonX = (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2);

        int titleImageWidth = (int) (0.5f * Gdx.graphics.getWidth());
        int titleImageHeight = (int) (0.12f * Gdx.graphics.getHeight());
        int titleImageX = (Gdx.graphics.getWidth() - titleImageWidth) / 2;
        int titleImageY = birdButtonY + birdButtonHeight + 145;

        int backButtonWidth = (int) (0.15f * Gdx.graphics.getWidth());
        int backButtonHeight = (int) (0.1f * Gdx.graphics.getHeight());
        int backButtonX = 25;
        int backButtonY = Gdx.graphics.getHeight() - backButtonHeight - 10;

        game.getBatch().begin();

        drawBackground();
        game.getBatch().draw(titleImage, titleImageX, titleImageY, titleImageWidth, titleImageHeight);

        if (isButtonHovered(backButtonWidth, backButtonHeight, backButtonX, backButtonY)) {
            game.getBatch().draw(backButtonActive, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
            if (Gdx.input.isTouched()) {
                game.setScreen(new LevelSelectionScreen(game));
            }
        } else {
            game.getBatch().draw(backButtonInactive, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        }

        if (isButtonHovered(birdButtonWidth, birdButtonHeight, bird1X, birdButtonY)) {
            game.getBatch().draw(bird1Active, bird1X, birdButtonY, birdButtonWidth, birdButtonHeight);
        } else {
            game.getBatch().draw(bird1Inactive, bird1X, birdButtonY, birdButtonWidth, birdButtonHeight);
        }

        if (isButtonHovered(birdButtonWidth, birdButtonHeight, bird2X, birdButtonY)) {
            game.getBatch().draw(bird2Active, bird2X, birdButtonY, birdButtonWidth, birdButtonHeight);
        } else {
            game.getBatch().draw(bird2Inactive, bird2X, birdButtonY, birdButtonWidth, birdButtonHeight);
        }

        if (isButtonHovered(birdButtonWidth, birdButtonHeight, bird3X, birdButtonY)) {
            game.getBatch().draw(bird3Active, bird3X, birdButtonY, birdButtonWidth, birdButtonHeight);
        } else {
            game.getBatch().draw(bird3Inactive, bird3X, birdButtonY, birdButtonWidth, birdButtonHeight);
        }

        if (isButtonHovered(buttonWidth, buttonHeight, proceedButtonX, proceedButtonY)) {
            game.getBatch().draw(proceedButtonActive, proceedButtonX, proceedButtonY, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                GamePlayScreen3 g3 = new GamePlayScreen3(game);
                game.setScreen(g3);
            }
        } else {
            game.getBatch().draw(proceedButtonInactive, proceedButtonX, proceedButtonY, buttonWidth, buttonHeight);
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
        game.backgroundMusic.stop();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird1Inactive.dispose();
        bird1Active.dispose();
        bird2Inactive.dispose();
        bird2Active.dispose();
        bird3Inactive.dispose();
        bird3Active.dispose();
        proceedButtonInactive.dispose();
        proceedButtonActive.dispose();
        titleImage.dispose();
        backButtonInactive.dispose();
        backButtonActive.dispose();
        game.backgroundMusic.dispose();
    }
}
