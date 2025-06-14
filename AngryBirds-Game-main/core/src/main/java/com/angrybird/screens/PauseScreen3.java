//Pause Screen for Level-3  (GamePlayScreen-1).

package com.angrybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys; // Import for keyboard input
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.angrybird.AngryBirdsGame;
import com.badlogic.gdx.utils.Json;

import java.io.FileWriter;

public class PauseScreen3 implements Screen {
    private static final float BUTTON_WIDTH_PERCENTAGE = 0.25f;
    private static final float BUTTON_HEIGHT_PERCENTAGE = 0.15f;
    private static final float RESUME_BUTTON_Y_PERCENTAGE = 0.45f;
    private static final float SAVE_BUTTON_Y_PERCENTAGE = 0.35f;
    private static final float EXIT_BUTTON_Y_PERCENTAGE = 0.25f;
    private static final float BUTTON_VERTICAL_SPACING = 0.05f;

    private AngryBirdsGame game;
    private SpriteBatch batch;
    private Texture background;
    private Texture resumeButtonActive;
    private Texture resumeButtonInactive;

    private boolean switchscreen1 = false;
    private boolean switchscreen2 = false;
    private boolean switchscreen3 = false;
    private boolean switchscreen4 = false;
    private Texture saveButtonActive;
    private Texture saveButtonInactive;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;
    private Texture backButtonActive;
    private Texture backButtonInactive;
    private Texture pauseTitleImage;
    Game3State gameState;

    public PauseScreen3(AngryBirdsGame game, Game3State game3State) {
        this.game = game;
        this.batch = new SpriteBatch();
        background = new Texture("AngryBirdsBackground.jpg");
        gameState = game3State;

    }

    @Override
    public void show() {
        pauseTitleImage = new Texture("textPause.jpg");
        resumeButtonInactive = new Texture("Resume1.png");
        resumeButtonActive = new Texture("Resume2.png");
        saveButtonInactive = new Texture("Save1.png");
        saveButtonActive = new Texture("Save2.png");
        exitButtonInactive = new Texture("exit-2.png");
        exitButtonActive = new Texture("exit-1.png");
        backButtonInactive = new Texture("Back 1.png");
        backButtonActive = new Texture("Back 2.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int buttonWidth = (int) (BUTTON_WIDTH_PERCENTAGE * Gdx.graphics.getWidth());
        int buttonHeight = (int) (BUTTON_HEIGHT_PERCENTAGE * Gdx.graphics.getHeight());
        int resumeButtonY = (int) (RESUME_BUTTON_Y_PERCENTAGE * Gdx.graphics.getHeight());
        int saveButtonY = resumeButtonY - buttonHeight - (int) (BUTTON_VERTICAL_SPACING * Gdx.graphics.getHeight());
        int exitButtonY = saveButtonY - buttonHeight - (int) (BUTTON_VERTICAL_SPACING * Gdx.graphics.getHeight());
        int backButtonWidth = (int) (0.15f * Gdx.graphics.getWidth());
        int backButtonHeight = (int) (0.1f * Gdx.graphics.getHeight());
        int backButtonX = 25;
        int backButtonY = Gdx.graphics.getHeight() - backButtonHeight - 10;

        batch.begin();
        drawBackground();

        int titleImageWidth = (int) (0.5f * Gdx.graphics.getWidth());
        int titleImageHeight = (int) (0.12f * Gdx.graphics.getHeight());
        int titleImageX = (Gdx.graphics.getWidth() - titleImageWidth) / 2;
        int titleImageY = Gdx.graphics.getHeight() - titleImageHeight - 150;
        batch.draw(pauseTitleImage, titleImageX, titleImageY, titleImageWidth, titleImageHeight);

        // Resume button
        if (isButtonHovered(buttonWidth, buttonHeight, resumeButtonY)) {
            batch.draw(resumeButtonActive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), resumeButtonY, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                switchscreen1 = true;
            }
        } else {
            batch.draw(resumeButtonInactive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), resumeButtonY, buttonWidth, buttonHeight);
        }

        // Save button
        if (isButtonHovered(buttonWidth, buttonHeight, saveButtonY)) {
            batch.draw(saveButtonActive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), saveButtonY, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                switchscreen4 = true;
            }
        } else {
            batch.draw(saveButtonInactive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), saveButtonY, buttonWidth, buttonHeight);
        }

        // Exit button
        if (isButtonHovered(buttonWidth, buttonHeight, exitButtonY)) {
            batch.draw(exitButtonActive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), exitButtonY, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                switchscreen2 = true;
            }
        } else {
            batch.draw(exitButtonInactive, (Gdx.graphics.getWidth() / 2) - (buttonWidth / 2), exitButtonY, buttonWidth, buttonHeight);
        }

        // Back button
        if (isButtonHovered(backButtonWidth, backButtonHeight, backButtonX, backButtonY)) {
            batch.draw(backButtonActive, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
            if (Gdx.input.isTouched()) {
                switchscreen3 = true;
            }
        } else {
            batch.draw(backButtonInactive, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        }

        batch.end();

        // Check for screen switches
        if (switchscreen1) {
            game.setScreen(new GamePlayScreen3(game, gameState));
        } else if (switchscreen2) {
            game.setScreen(new LevelSelectionScreen(game));
        } else if (switchscreen3) {
            game.setScreen(new MainMenuScreen(game));
        } else if (Gdx.input.isKeyPressed(Keys.W)) {
            game.setScreen(new LevelUpScreen2(game));
        } else if (switchscreen4) {
            // Disabled for browser build: FileWriter-based saving
            // Json json = new Json();
            // try (FileWriter writer = new FileWriter("C:\\Users\\Arpit Raj\\Desktop\\AngryBirds-Game\\core\\src\\main\\java\\com\\angrybird\\screens\\SavedGamesLvl3.json")) {
            //     json.toJson(gameState, writer);
            //     System.out.println("Game states saved to " + "C:\\Users\\Arpit Raj\\Desktop\\AngryBirds-Game\\core\\src\\main\\java\\com\\angrybird\\screens\\SavedGamesLvl3.json");
            // } catch (Exception e) {
            //     e.printStackTrace();
            // }
            game.setScreen(new LoadSavedGames(game));
        }
    }

    private boolean isButtonHovered(int buttonWidth, int buttonHeight, int buttonY) {
        return Gdx.input.getX() > (Gdx.graphics.getWidth() / 2 - buttonWidth / 2) &&
            Gdx.input.getX() < (Gdx.graphics.getWidth() / 2 + buttonWidth / 2) &&
            Gdx.graphics.getHeight() - Gdx.input.getY() > buttonY &&
            Gdx.graphics.getHeight() - Gdx.input.getY() < buttonY + buttonHeight;
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
        batch.draw(background, (screenWidth - scaledBgWidth) / 2, (screenHeight - scaledBgHeight) / 2, scaledBgWidth, scaledBgHeight);
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
        resumeButtonInactive.dispose();
        resumeButtonActive.dispose();
        saveButtonInactive.dispose();
        saveButtonActive.dispose();
        exitButtonInactive.dispose();
        exitButtonActive.dispose();
        backButtonInactive.dispose();
        backButtonActive.dispose();
        pauseTitleImage.dispose();
    }
}
