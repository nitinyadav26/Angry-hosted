package com.angrybird.screens;

import com.angrybird.AngryBirdsGame;
import com.angrybird.screens.MainMenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameWonScreen implements Screen {
    private final AngryBirdsGame game;
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture backgroundImage;
    private Texture labelBackgroundImage;

    public GameWonScreen(AngryBirdsGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        batch = new SpriteBatch();
        backgroundImage = new Texture(Gdx.files.internal("ab bg-5.png"));
        labelBackgroundImage = new Texture(Gdx.files.internal("TestHeadingBackground.png"));


        Table table = new Table();
        table.setFillParent(true);
        table.top();

        Texture img1 = new Texture(Gdx.files.internal("Victoryimg.png"));
        Image victoryimg = new Image(img1);

        Texture img2 = new Texture(Gdx.files.internal("GameWonImg.png"));
        Image wonimg = new Image(img2);

        Texture buttonbackground = new Texture(Gdx.files.internal("NextLevel button.png"));
        TextureRegionDrawable buttonBackgroundDrawable = new TextureRegionDrawable(new TextureRegion(buttonbackground));
        buttonBackgroundDrawable.setMinHeight(70);
        buttonBackgroundDrawable.setMinWidth(200);
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.up = buttonBackgroundDrawable;
        buttonStyle.font = skin.getFont("default-font");


        Texture buttonbackground2 = new Texture(Gdx.files.internal("Back 1.png"));
        TextureRegionDrawable buttonBackgroundDrawable2 = new TextureRegionDrawable(new TextureRegion(buttonbackground2));
        buttonBackgroundDrawable2.setMinHeight(70);
        buttonBackgroundDrawable2.setMinWidth(200);
        TextButtonStyle buttonStyle2 = new TextButtonStyle();
        buttonStyle2.up = buttonBackgroundDrawable2;
        buttonStyle2.font = skin.getFont("default-font");


        TextButton NextLevel = new TextButton("", buttonStyle);
        TextButton MainMenu = new TextButton("", buttonStyle2);
        wonimg.setColor(1, 1, 1, 0);

        table.add(victoryimg).width(400).height(100).pad(20).colspan(2).row();
        table.add(wonimg).width(500).height(450).pad(20).colspan(2).row();
        table.add(NextLevel).width(220).height(100).padRight(50);
        table.add(MainMenu).width(220).height(100);

        MainMenu.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });


        // Add the table to the stage
        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Drawing the background image to fill the screen
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);  // handle window resizing
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
        stage.dispose();
        skin.dispose();
        batch.dispose();
        backgroundImage.dispose();
    }
}
