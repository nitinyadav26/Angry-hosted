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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

// Disabled for browser build: FileReader-based loading
// import java.io.FileReader;

public class LoadSavedGames implements Screen {
    private final AngryBirdsGame game;
    private boolean switchScreen = false;

    private Texture BackButtonActive;
    private Texture BackButtonInactive;
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture backgroundImage;
    private Texture labelBackgroundImage;

    public Game1State game1State = null;
    public Game2State game2State = null;
    public Game3State game3State = null;

    private TextureRegionDrawable createColorDrawable(Color color, float opacity) {
        // Creating pixmap
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);


        pixmap.setColor(new Color(color.r, color.g, color.b, opacity));
        pixmap.fill();


        Texture texture = new Texture(pixmap);


        pixmap.dispose();


        return new TextureRegionDrawable(texture);
    }

    public LoadSavedGames(AngryBirdsGame game) {


        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        skin = new Skin(Gdx.files.internal("uiskin.json"));

        BackButtonActive = new Texture("Back 2.png");
        BackButtonInactive = new Texture("Back 1.png");


        batch = new SpriteBatch();
        backgroundImage = new Texture(Gdx.files.internal("ab bg-3blur.jpg"));


        labelBackgroundImage = new Texture(Gdx.files.internal("saved-2.png"));  // image for the heading background
        TextureRegionDrawable labelBackgroundDrawable = new TextureRegionDrawable(new TextureRegion(labelBackgroundImage));
        BitmapFont font = skin.getFont("default-font");
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        labelStyle.background = labelBackgroundDrawable;
        Label l1 = new Label("", labelStyle);
        l1.setAlignment(Align.center);


        //wooden label style
        Texture Infobackgroundimg = new Texture(Gdx.files.internal("woodenbutton2.png"));  // image for the heading background
        TextureRegionDrawable labelBackgroundDrawable2 = new TextureRegionDrawable(new TextureRegion(Infobackgroundimg));
        BitmapFont font2 = skin.getFont("default-font");
        Label.LabelStyle labelStyle2 = new Label.LabelStyle(font2, Color.WHITE);
        labelStyle2.background = labelBackgroundDrawable2;


        //Back button
        Texture buttonBackgroundTexture = new Texture(Gdx.files.internal("Back 1.png"));
        TextureRegionDrawable buttonBackgroundDrawable = new TextureRegionDrawable(new TextureRegion(buttonBackgroundTexture));
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = buttonBackgroundDrawable;
        buttonStyle.down = buttonBackgroundDrawable;
        buttonStyle.font = skin.getFont("default-font");
        TextButton b1 = new TextButton("", buttonStyle);

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        TextureRegionDrawable backgroundDrawable = createColorDrawable(Color.GRAY, 0.7f);

        // create tables for layout
        Table table1 = new Table();
        table1.center();
        Table table2 = new Table();
        Table table3 = new Table();
        Table table4 = new Table();

        table1.setHeight(Gdx.graphics.getHeight() / 6f);
        table1.setWidth(Gdx.graphics.getWidth() * 0.8f);

        table2.setBackground(backgroundDrawable);
        table2.setHeight(Gdx.graphics.getHeight() * 0.8f);
        table2.setWidth(Gdx.graphics.getWidth());

        table3.setBackground(backgroundDrawable);
        table3.setHeight(Gdx.graphics.getHeight() * 0.8f);
        table3.setWidth(Gdx.graphics.getWidth());

        table4.setBackground(backgroundDrawable);
        table4.setHeight(Gdx.graphics.getHeight() * 0.8f);
        table4.setWidth(Gdx.graphics.getWidth());


        Texture PlayButtonTexture = new Texture(Gdx.files.internal("play button-2.png"));
        Image PlayButton1 = new Image(PlayButtonTexture);
        Image PlayButton2 = new Image(PlayButtonTexture);
        Image PlayButton3 = new Image(PlayButtonTexture);

        Texture logo1 = new Texture(Gdx.files.internal("logo1img.png"));
        Image logo1image = new Image(logo1);

        Texture logo2 = new Texture(Gdx.files.internal("logo2img.png"));
        Image logo2image = new Image(logo2);

        Texture logo3 = new Texture(Gdx.files.internal("logo3img.png"));
        Image logo3image = new Image(logo3);


        Texture gname1 = new Texture(Gdx.files.internal("GAME 1.png"));
        Image gname1image = new Image(gname1);

        Texture gname2 = new Texture(Gdx.files.internal("GAME 2.png"));
        Image gname2image = new Image(gname2);

        Texture gname3 = new Texture(Gdx.files.internal("GAME 3.png"));
        Image gname3image = new Image(gname3);


        table2.add(logo1image).width(310).height(Gdx.graphics.getHeight() / 4.3f);
        table2.add(gname1image).width(500).height(Gdx.graphics.getHeight() * 0.6f / 5f).pad(50);
        table2.add(PlayButton1).width(400).height(Gdx.graphics.getHeight() * 0.6f / 5f).pad(10).center();


        table3.add(logo2image).width(310).height(Gdx.graphics.getHeight() / 4.3f);
        table3.add(gname2image).width(500).height(Gdx.graphics.getHeight() * 0.6f / 5f).pad(50);
        table3.add(PlayButton2).width(400).height(Gdx.graphics.getHeight() * 0.6f / 5f).pad(10).center();


        table4.add(logo3image).width(310).height(Gdx.graphics.getHeight() / 4.3f);
        table4.add(gname3image).width(500).height(Gdx.graphics.getHeight() * 0.6f / 5f).pad(50);
        table4.add(PlayButton3).width(400).height(Gdx.graphics.getHeight() * 0.6f / 5f).pad(10).center();

        table1.add(l1).width(400).height(table1.getHeight() * 0.8f).pad(10).row();
        table.add(table1).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() / 6f).pad(10).expandX().center().row();
        table.add(table2).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() / 5f).pad(10).expandX().center().row();
        table.add(table3).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() / 5f).pad(10).expandX().center().row();
        table.add(table4).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() / 5f).pad(10).expandX().center().row();


        b1.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        PlayButton1.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Json json = new Json();

                // Disabled for browser build: FileReader-based loading
                // try (FileReader reader = new FileReader("C:\\Users\\Arpit Raj\\Desktop\\AngryBirds-Game\\core\\src\\main\\java\\com\\angrybird\\screens\\savedgames.json")) {
                //     game1State = json.fromJson(Game1State.class, reader);
                //     System.out.println("Game states loaded from " + "C:\\Users\\Arpit Raj\\Desktop\\AngryBirds-Game\\core\\src\\main\\java\\com\\angrybird\\screens\\savedgames.json");
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }

                if (game1State != null) {
                    game.setScreen(new GamePlayScreen(game, game1State));
                }

            }
        });


        PlayButton2.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Json json = new Json();

                // Disabled for browser build: FileReader-based loading
                // try (FileReader reader = new FileReader("C:\\Users\\Arpit Raj\\Desktop\\AngryBirds-Game\\core\\src\\main\\java\\com\\angrybird\\screens\\SavedGamesLvl2.json")) {
                //     game2State = json.fromJson(Game2State.class, reader);
                //     System.out.println("Game states loaded from " + "C:\\Users\\Arpit Raj\\Desktop\\AngryBirds-Game\\core\\src\\main\\java\\com\\angrybird\\screens\\SavedGamesLvl2.json");
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }

                if (game2State != null) {
                    game.setScreen(new GamePlayScreen2(game, game2State));
                }
            }
        });

        PlayButton3.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Json json = new Json();

                // Disabled for browser build: FileReader-based loading
                // try (FileReader reader = new FileReader("C:\\Users\\Arpit Raj\\Desktop\\AngryBirds-Game\\core\\src\\main\\java\\com\\angrybird\\screens\\SavedGamesLvl3.json")) {
                //     game3State = json.fromJson(Game3State.class, reader);
                //     System.out.println("Game states loaded from " + "C:\\Users\\Arpit Raj\\Desktop\\AngryBirds-Game\\core\\src\\main\\java\\com\\angrybird\\screens\\SavedGamesLvl3.json");
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }

                if (game3State != null) {
                    game.setScreen(new GamePlayScreen3(game, game3State));
                }
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
        int backButtonWidth = (int) (0.15f * Gdx.graphics.getWidth());
        int backButtonHeight = (int) (0.1f * Gdx.graphics.getHeight());
        int backButtonX = 25;
        int backButtonY = 10;


        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (isButtonHovered(backButtonWidth, backButtonHeight, backButtonX, backButtonY)) {
            batch.draw(BackButtonActive, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
            if (Gdx.input.justTouched()) {
                switchScreen = true;
            }
        } else {
            batch.draw(BackButtonInactive, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        }
        batch.end();

        stage.act(delta);
        stage.draw();
        if (switchScreen) {
            game.setScreen(new MainMenuScreen(game));
        }


    }

    private boolean isButtonHovered(int buttonWidth, int buttonHeight, int x, int y) {
        return Gdx.input.getX() > x && Gdx.input.getX() < x + buttonWidth &&
            Gdx.graphics.getHeight() - Gdx.input.getY() > y &&
            Gdx.graphics.getHeight() - Gdx.input.getY() < y + buttonHeight;
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);  // Handle window resizing
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
        stage.dispose();  // Disposing everything
        skin.dispose();
        batch.dispose();
        backgroundImage.dispose();
        labelBackgroundImage.dispose();
    }
}
