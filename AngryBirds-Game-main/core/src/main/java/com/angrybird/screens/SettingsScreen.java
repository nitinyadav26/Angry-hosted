package com.angrybird.screens;

import com.angrybird.AngryBirdsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


////This screen is not in actual use  and has been made just for testing purposes,or future functionality,
//That we might need after deadline 2

public class SettingsScreen implements Screen {
    private final AngryBirdsGame game;
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture backgroundImage;
    private Texture labelBackgroundImage;

    private TextureRegionDrawable createColorDrawable(Color color, float opacity) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(color.r, color.g, color.b, opacity));
        pixmap.fill();

        Texture texture = new Texture(pixmap);

        pixmap.dispose();
        return new TextureRegionDrawable(texture);
    }

    public SettingsScreen(AngryBirdsGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        batch = new SpriteBatch();
        backgroundImage = new Texture(Gdx.files.internal("ab bg-7.png"));

        // setting backgroud for heading
        labelBackgroundImage = new Texture(Gdx.files.internal("settings.png"));
        TextureRegionDrawable labelBackgroundDrawable = new TextureRegionDrawable(new TextureRegion(labelBackgroundImage));
        BitmapFont font = skin.getFont("default-font");
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        labelStyle.background = labelBackgroundDrawable;
        Label l1 = new Label("", labelStyle);
        l1.setAlignment(Align.center);

        //back button with background
        Texture buttonBackgroundTexture = new Texture(Gdx.files.internal("BACK 1.png"));
        TextureRegionDrawable buttonBackgroundDrawable = new TextureRegionDrawable(new TextureRegion(buttonBackgroundTexture));
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = buttonBackgroundDrawable;
        buttonStyle.down = buttonBackgroundDrawable;
        buttonStyle.font = skin.getFont("default-font");
        TextButton b1 = new TextButton("", buttonStyle);

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        table.add(l1).width(400).height(110).pad(10).expandX().left().row();

        b1.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        labelBackgroundImage.dispose();
    }
}
