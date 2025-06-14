package com.angrybird;

import com.angrybird.screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.angrybird.screens.PlayGameScreen;

public class AngryBirdsGame extends ApplicationAdapter {
    public static final int WIDTH = 800;  // Set a default width
    public static final int HEIGHT = 600; // Set a default height

    private SpriteBatch batch;
    private Screen currentScreen;  // Variable to keep track of the current screen
    private MainMenuScreen mainMenuScreen;
    private BitmapFont font; // Add BitmapFont here

    public Music backgroundMusic;

//    private PlayGameScreen playGameScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // Initialize the font
        MainMenuScreen m1 = new MainMenuScreen(this);
//        GameWonScreen g1 = new GameWonScreen(this);
//        SettingsScreen s1 = new SettingsScreen(this);
//        playGameScreen = new PlayGameScreen(this)

        LoadSavedGames l1 = new LoadSavedGames(this);
        GamePlayScreen2 g2 = new GamePlayScreen2(this);
        GamePlayScreen3 g3 = new GamePlayScreen3(this);
//        GamePlayScreen4 g4 = new GamePlayScreen4(this);


//          LoadSavedGames l1 = new LoadSavedGames(this);

//        setScreen((Screen) g4);  // Start with the main menu screen
        // setScreen(m1);


//        LevelSelectionScreen lev1 = new LevelSelectionScreen(this);
//        setScreen(lev1);
//        PauseScreen p1= new PauseScreen(this);
//        setScreen(mainMenuScreen);
//        GamePlayScreen3 g3=new GamePlayScreen3(this);

        setScreen(m1);

//        LevelUpScreen2 lu= new LevelUpScreen2(this);
//        setScreen(lu);

//        DefeatedScreen df = new DefeatedScreen(this);
//        setScreen(df);
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("angry_birds.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play(); // Play the music when the game starts
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    // Method to switch screens
    public void setScreen(Screen screen) {
        // Dispose of the current screen if it exists
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        currentScreen = screen;
        currentScreen.show();
    }

//    public PlayGameScreen getPlayScreen() {
//        return playGameScreen;
//    }

    @Override
    public void render() {
        // Delegate rendering to the current screen
        if (currentScreen != null) {
            currentScreen.render(Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        font.dispose();
    }

    public BitmapFont getFont() {
        return font; // Provide access to the font
    }
}
