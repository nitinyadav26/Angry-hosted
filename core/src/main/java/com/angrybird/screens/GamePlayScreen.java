//This Gameplay Screen is for Level-1.
package com.angrybird.screens;

import com.angrybird.*;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


class Game1State implements Serializable {
    // bird healths arraylist
    ArrayList<Integer> BirdHealths = new ArrayList<>();
    ArrayList<Integer> PigHealth = new ArrayList<>();
    ArrayList<Pair> Positions = new ArrayList<>();
    Integer currentindex = 0;
    // Redbird, BlueBird,PinkBird,PigType1,Left Block,Right Block positions

    public void updateGameState(ArrayList<Integer> birdHealths, ArrayList<Integer> pigHealths, ArrayList<Pair> Positions, Integer currentindex) {
        this.BirdHealths = birdHealths;
        this.PigHealth = pigHealths;
        this.Positions = Positions;
        this.currentindex = currentindex;
    }
}


public class GamePlayScreen extends HelperFunctions implements Screen {
    private final AngryBirdsGame game;
    private Stage stage;
    private SpriteBatch batch;
    private float elapsedTime = 0;
    private Texture backgroundImage;
    private Texture bodyTexture; // the first bodytexture
    private Texture bodyTexture2;
    private Texture bodyTexture3;
    private Texture bodyTexture4;
    private Texture bodyTexture5;
    private Texture bodyTexture6;
    private Texture bodyTexture7;
    private TextureRegion bodyTexture8;
    private Body RightBoundary;

    public boolean lastbirdlaunched = false;


    float currentdragx = 0;
    float currentdragy = 0;

    boolean placed = false;
    boolean pigexists = true;


    RedBird r1;
    BlueBird b1;
    PinkBird p1;
    WoodenBlock w1;
    PigType1 pig;


    private boolean switchScreen;
    private World world;
    private Body body;
    private Body body2;
    private Body body3;
    private Body body4;
    private Body body5;
    private Body body6;
    private Body body7;
    private Body body8;
    private Body Groundbody;
    private Texture PauseButtonActive;
    private Texture PauseButtonInactive;
    private Box2DDebugRenderer debugRenderer;

    private MyContactListener1 contactListener1;
    Game1State game1State = new Game1State();

    boolean validbird = false; // to indicate that the bird set is valid
    private Body currentbird;// this will be used to indicate the bird that is currently being launched
    int currentindex = 2;


    public boolean gamewon = false;
    public boolean gamelost = false;


    // we need some kind of list of all bodies so that the controller knows which bodies refer to a bird
    ArrayList<Body> BirdList = new ArrayList<>();

    ArrayList<Body> pigBodies = new ArrayList<>();
    ArrayList<Body> blockBodies = new ArrayList<>();


    private ShapeRenderer shapeRenderer; // allows us to plot the dots we need for trijectory
    //now we will set some parameters that allow us to handle triggers and other events

    private boolean triggered = false;// used to check if the velocity vector being drawn is valid
    private int initialX = 0;
    private int initialY = 0;
    private int velocityX = 0;
    private int velocityY = 0;

    private boolean isButtonHovered(int buttonWidth, int buttonHeight, int x, int y) {
        return Gdx.input.getX() > x && Gdx.input.getX() < x + buttonWidth &&
            Gdx.graphics.getHeight() - Gdx.input.getY() > y &&
            Gdx.graphics.getHeight() - Gdx.input.getY() < y + buttonHeight;
    }

    public GamePlayScreen(AngryBirdsGame game) {    //constructor for gameplay screen
        switchScreen = false;
        PauseButtonActive = new Texture("Pause2.png");
        PauseButtonInactive = new Texture("Pause1.png");
        this.game = game;


        shapeRenderer = new ShapeRenderer();

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        backgroundImage = new Texture(Gdx.files.internal("ab bg-8.jpg"));

        bodyTexture = new Texture(Gdx.files.internal("redbird.png"));
        bodyTexture2 = new Texture(Gdx.files.internal("bluebird.png"));
        bodyTexture3 = new Texture(Gdx.files.internal("pinkbird.png"));
        bodyTexture4 = new Texture(Gdx.files.internal("slingshot.png"));
        bodyTexture5 = new Texture(Gdx.files.internal("woodblock.png"));
        bodyTexture6 = new Texture(Gdx.files.internal("woodblock.png"));
        bodyTexture7 = new Texture(Gdx.files.internal("pig1.png"));
        Texture bodyTextureBase8 = new Texture(Gdx.files.internal("woodblock.png"));
        bodyTexture8 = new TextureRegion(bodyTextureBase8);

        world = new World(new Vector2(0, -9.8f), true);

        // declaring bird class objects for the access of inital position,friction and density
        // characteristics
        r1 = new RedBird();
        b1 = new BlueBird();
        p1 = new PinkBird();
        w1 = new WoodenBlock();
        pig = new PigType1();

        r1.setHealth(1);
        b1.setHealth(1);
        p1.setHealth(1);
        pig.setHealth(1);

        //we need some sort of a ground body so that the objects placed on the screen do not go down

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody; // making it as static as we do not want it to react to collisions
        groundBodyDef.position.set(0f, 0f);
        Groundbody = world.createBody(groundBodyDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(Gdx.graphics.getWidth(), 2.5f);
        FixtureDef f1 = new FixtureDef();
        f1.shape = groundShape;
        f1.friction = 0.5f;
        f1.restitution = 0.5f;
        Groundbody.createFixture(f1);
        groundShape.dispose();


        Integer screenWidth = Gdx.graphics.getWidth();
        Integer screenHeight = Gdx.graphics.getHeight();

        BodyDef boundarybodyDef = new BodyDef();
        boundarybodyDef.type = BodyDef.BodyType.StaticBody;
        boundarybodyDef.position.set(20f, 2f);
        RightBoundary = world.createBody(boundarybodyDef);
        PolygonShape boundaryBodyShape = new PolygonShape();
        boundaryBodyShape.setAsBox(0.001f, screenHeight);
        FixtureDef boundaryfixtureDef = new FixtureDef();
        boundaryfixtureDef.shape = boundaryBodyShape;
        boundaryfixtureDef.density = 0f; //as static bodies don't need density
        boundaryfixtureDef.friction = 0.5f;
        boundaryfixtureDef.restitution = 0.5f;
        RightBoundary.createFixture(boundaryfixtureDef);
        boundaryBodyShape.dispose();

        //for red bird
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(r1.getInitialX(), r1.getInitialY());
        body = world.createBody(bodyDef);
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(0.3f, 0.3f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = r1.getDensity();
        fixtureDef.friction = r1.getFriction();
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef);
        boxShape.dispose();
//        body.setLinearVelocity(6f,10f);

        //for bluebird
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.DynamicBody;
        bodyDef2.position.set(b1.getInitialX(), b1.getInitialY());
        body2 = world.createBody(bodyDef2);
        PolygonShape boxShape2 = new PolygonShape();
        boxShape2.setAsBox(0.1f, 0.1f);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = boxShape2;
        fixtureDef2.density = b1.getDensity();
        fixtureDef2.friction = b1.getFriction();
        fixtureDef2.restitution = 0.5f;
        body2.createFixture(fixtureDef2);
        boxShape2.dispose();


        BodyDef bodyDef3 = new BodyDef();
        bodyDef3.type = BodyDef.BodyType.DynamicBody;
        bodyDef3.position.set(p1.getInitialX(), p1.getInitialY());
        body3 = world.createBody(bodyDef3);
        PolygonShape boxShape3 = new PolygonShape();
        boxShape3.setAsBox(0.3f, 0.3f);
        FixtureDef fixtureDef3 = new FixtureDef();
        fixtureDef3.shape = boxShape3;
        fixtureDef3.density = p1.getDensity();
        fixtureDef3.friction = p1.getFriction();
        fixtureDef3.restitution = 0.5f;
        body3.createFixture(fixtureDef3);
        boxShape3.dispose();

        BodyDef bodyDef4 = new BodyDef();
        bodyDef4.type = BodyDef.BodyType.StaticBody;
        bodyDef4.position.set(4.5f, 3.39f);
        body4 = world.createBody(bodyDef4);
        PolygonShape boxShape4 = new PolygonShape();
        boxShape4.setAsBox(0.2f, 0.8f);
        FixtureDef fixtureDef4 = new FixtureDef();
        fixtureDef4.shape = boxShape4;
        fixtureDef4.restitution = 0f;

        body4.createFixture(fixtureDef4);
        boxShape4.dispose();

        BirdList.add(body);
        BirdList.add(body2);
        BirdList.add(body3);

        currentbird = BirdList.get(currentindex);
        validbird = true;


        GameData.BodytoObjectMap.put(body, r1);
        GameData.BodytoObjectMap.put(body2, b1);
        GameData.BodytoObjectMap.put(body3, p1);
        // stored all the bodies in a body to object map


        // a wooden block
        BodyDef bodyDef5 = new BodyDef();
        bodyDef5.type = BodyDef.BodyType.DynamicBody;
        bodyDef5.position.set(14f, 0.3f);
        body5 = world.createBody(bodyDef5);
        PolygonShape boxShape5 = new PolygonShape();
        boxShape5.setAsBox(0.2f, 1.6f);
        FixtureDef fixtureDef5 = new FixtureDef();
        fixtureDef5.shape = boxShape5;
        fixtureDef5.restitution = 0f;

        body5.createFixture(fixtureDef5);
        boxShape5.dispose();


        BodyDef bodyDef6 = new BodyDef();
        bodyDef6.type = BodyDef.BodyType.DynamicBody;
        bodyDef6.position.set(17f, 0.3f);
        body6 = world.createBody(bodyDef6);
        PolygonShape boxShape6 = new PolygonShape();
        boxShape6.setAsBox(0.2f, 1.6f);
        FixtureDef fixtureDef6 = new FixtureDef();
        fixtureDef6.shape = boxShape6;
        fixtureDef6.restitution = 0f;

        body6.createFixture(fixtureDef6);
        boxShape6.dispose();


        BodyDef bodyDef7 = new BodyDef();
        bodyDef7.type = BodyDef.BodyType.DynamicBody;
        bodyDef7.position.set(16.3f, 2f);
        body7 = world.createBody(bodyDef7);
        PolygonShape boxShape7 = new PolygonShape();
        boxShape7.setAsBox(1f, 0.5f);
        FixtureDef fixtureDef7 = new FixtureDef();
        fixtureDef7.shape = boxShape7;
        fixtureDef7.density = pig.getDensity();
        fixtureDef7.friction = pig.getFriction();
        fixtureDef7.restitution = 0.5f;
        fixtureDef7.friction = 0.05f;
        body7.createFixture(fixtureDef7);
        boxShape7.dispose();

        GameData.BodytoObjectMap.put(body7, pig);

        // to handle launching we will set method

        // we will now provide all the information to the contact listener

        contactListener1 = new MyContactListener1();
        pigBodies = new ArrayList<>();
        pigBodies.add(body7);
        blockBodies = new ArrayList<>();
        contactListener1.setInformation(BirdList, pigBodies, blockBodies);


        // this will allow the contact listners to make changes in the body health
        debugRenderer = new Box2DDebugRenderer();
    }

    public GamePlayScreen(AngryBirdsGame game, Game1State game1Statein) // constructor for loading saved games , the game state is
    // is provided using the game1Statein

    {

        // in this constructor we will load all information from the game1state;
        switchScreen = false;
        currentindex = game1Statein.currentindex;
        PauseButtonActive = new Texture("Pause2.png");
        PauseButtonInactive = new Texture("Pause1.png");
        this.game = game;


        shapeRenderer = new ShapeRenderer();

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        backgroundImage = new Texture(Gdx.files.internal("ab bg-8.jpg"));


        bodyTexture = new Texture(Gdx.files.internal("redbird.png"));
        bodyTexture2 = new Texture(Gdx.files.internal("bluebird.png"));
        bodyTexture3 = new Texture(Gdx.files.internal("pinkbird.png"));
        bodyTexture4 = new Texture(Gdx.files.internal("slingshot.png"));
        bodyTexture5 = new Texture(Gdx.files.internal("woodblock.png"));
        bodyTexture6 = new Texture(Gdx.files.internal("woodblock.png"));
        bodyTexture7 = new Texture(Gdx.files.internal("pig1.png"));


        world = new World(new Vector2(0, -9.8f), true);

        // declaring bird class objects for the access of inital position,friction and density
        // characteristics
        r1 = new RedBird();
        b1 = new BlueBird();
        p1 = new PinkBird();
        w1 = new WoodenBlock();
        pig = new PigType1();

        r1.setHealth(1);
        b1.setHealth(1);
        p1.setHealth(1);
        pig.setHealth(1);

        //we need some sort of a ground body so that the objects placed on the screen do not go down

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody; // making it as static as we do not want it to react to collisions
        groundBodyDef.position.set(0f, 0f);
        Groundbody = world.createBody(groundBodyDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(Gdx.graphics.getWidth(), 2.5f);
        FixtureDef f1 = new FixtureDef();
        f1.shape = groundShape;
        f1.friction = 0.5f;
        f1.restitution = 0.5f;
        Groundbody.createFixture(f1);
        groundShape.dispose();


        Integer screenWidth = Gdx.graphics.getWidth();
        Integer screenHeight = Gdx.graphics.getHeight();

        BodyDef boundarybodyDef = new BodyDef();
        boundarybodyDef.type = BodyDef.BodyType.StaticBody;
        boundarybodyDef.position.set(20f, 2f);
        RightBoundary = world.createBody(boundarybodyDef);
        PolygonShape boundaryBodyShape = new PolygonShape();
        boundaryBodyShape.setAsBox(0.001f, screenHeight);
        FixtureDef boundaryfixtureDef = new FixtureDef();
        boundaryfixtureDef.shape = boundaryBodyShape;
        boundaryfixtureDef.density = 0f;
        boundaryfixtureDef.friction = 0.5f;
        boundaryfixtureDef.restitution = 0.5f;
        RightBoundary.createFixture(boundaryfixtureDef);
        boundaryBodyShape.dispose();


        //for red bird
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(game1Statein.Positions.get(0).x, game1Statein.Positions.get(0).y);
        body = world.createBody(bodyDef);
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(0.3f, 0.3f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = r1.getDensity();
        fixtureDef.friction = r1.getFriction();
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef);
        boxShape.dispose();
//        body.setLinearVelocity(6f,10f);

        //for bluebird
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.DynamicBody;
        bodyDef2.position.set(game1Statein.Positions.get(1).x, game1Statein.Positions.get(1).y);
        body2 = world.createBody(bodyDef2);
        PolygonShape boxShape2 = new PolygonShape();
        boxShape2.setAsBox(0.1f, 0.1f);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = boxShape2;
        fixtureDef2.density = b1.getDensity();
        fixtureDef2.friction = b1.getFriction();
        fixtureDef2.restitution = 0.5f;
        body2.createFixture(fixtureDef2);
        boxShape2.dispose();


        BodyDef bodyDef3 = new BodyDef();
        bodyDef3.type = BodyDef.BodyType.DynamicBody;
        bodyDef3.position.set(game1Statein.Positions.get(2).x, game1Statein.Positions.get(2).y);
        body3 = world.createBody(bodyDef3);
        PolygonShape boxShape3 = new PolygonShape();
        boxShape3.setAsBox(0.3f, 0.3f);
        FixtureDef fixtureDef3 = new FixtureDef();
        fixtureDef3.shape = boxShape3;
        fixtureDef3.density = p1.getDensity();
        fixtureDef3.friction = p1.getFriction();
        fixtureDef3.restitution = 0.5f;
        body3.createFixture(fixtureDef3);
        boxShape3.dispose();

        BodyDef bodyDef4 = new BodyDef();
        bodyDef4.type = BodyDef.BodyType.StaticBody;
        bodyDef4.position.set(4.5f, 3.39f);
        body4 = world.createBody(bodyDef4);
        PolygonShape boxShape4 = new PolygonShape();
        boxShape4.setAsBox(0.2f, 0.8f);
        FixtureDef fixtureDef4 = new FixtureDef();
        fixtureDef4.shape = boxShape4;
        fixtureDef4.restitution = 0f;

        body4.createFixture(fixtureDef4);
        boxShape4.dispose();

        BirdList.add(body);
        BirdList.add(body2);
        BirdList.add(body3);

        currentbird = BirdList.get(currentindex);
        validbird = true;


        GameData.BodytoObjectMap.put(body, r1);
        GameData.BodytoObjectMap.put(body2, b1);
        GameData.BodytoObjectMap.put(body3, p1);
        // stored all the bodies in a body to object map


        // a wooden block
        BodyDef bodyDef5 = new BodyDef();
        bodyDef5.type = BodyDef.BodyType.DynamicBody;
        bodyDef5.position.set(game1Statein.Positions.get(4).x, game1Statein.Positions.get(4).y);
        body5 = world.createBody(bodyDef5);
        PolygonShape boxShape5 = new PolygonShape();
        boxShape5.setAsBox(0.2f, 1.6f);
        FixtureDef fixtureDef5 = new FixtureDef();
        fixtureDef5.shape = boxShape5;
        fixtureDef5.restitution = 0f;

        body5.createFixture(fixtureDef5);
        boxShape5.dispose();


        BodyDef bodyDef6 = new BodyDef();
        bodyDef6.type = BodyDef.BodyType.DynamicBody;
        bodyDef6.position.set(game1Statein.Positions.get(5).x, game1Statein.Positions.get(5).y);
        body6 = world.createBody(bodyDef6);
        PolygonShape boxShape6 = new PolygonShape();
        boxShape6.setAsBox(0.2f, 1.6f);
        FixtureDef fixtureDef6 = new FixtureDef();
        fixtureDef6.shape = boxShape6;
        fixtureDef6.restitution = 0f;

        body6.createFixture(fixtureDef6);
        boxShape6.dispose();


        BodyDef bodyDef7 = new BodyDef();
        bodyDef7.type = BodyDef.BodyType.DynamicBody;
        bodyDef7.position.set(game1Statein.Positions.get(3).x, game1Statein.Positions.get(3).y);
        body7 = world.createBody(bodyDef7);
        PolygonShape boxShape7 = new PolygonShape();
        boxShape7.setAsBox(1f, 0.5f);
        FixtureDef fixtureDef7 = new FixtureDef();
        fixtureDef7.shape = boxShape7;
        fixtureDef7.density = pig.getDensity();
        fixtureDef7.friction = pig.getFriction();
        fixtureDef7.restitution = 0.5f;
        fixtureDef7.friction = 0.05f;
        body7.createFixture(fixtureDef7);
        boxShape7.dispose();

        GameData.BodytoObjectMap.put(body7, pig);

        // to handle launching we will set method
        // we will now provide all the information to the contact listener

        contactListener1 = new MyContactListener1();
        pigBodies = new ArrayList<>();
        pigBodies.add(body7);
        blockBodies = new ArrayList<>();
        contactListener1.setInformation(BirdList, pigBodies, blockBodies);
        // this will allow the contact listeners to make changes in the body health

        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
    }

    @Override

    public void render(float delta) {
        int pauseButtonWidth = (int) (0.15f * Gdx.graphics.getWidth());
        int pauseButtonHeight = (int) (0.1f * Gdx.graphics.getHeight());
        int pauseButtonX = 25;
        int pauseButtonY = Gdx.graphics.getHeight() - pauseButtonHeight - 10;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.setContactListener(contactListener1);
        // setting up the valid bird and current bird
        currentbird = BirdList.get(currentindex);
        validbird = true; // here we can write any code we want to execute when we are setting the bird
        if (!placed) {

            if (!placed) {
                elapsedTime += delta;
                if (elapsedTime >= 4.5) { //check if 4.5 seconds have passed
                    currentbird.setTransform(4.66f, 4.89f, 0);
                    placed = true;
                }
            }

        }
        PigType1 pigtocheck;
        pigtocheck = (PigType1) GameData.BodytoObjectMap.get(pigBodies.get(0));
        if (pigtocheck.health == 0 && pigexists) {
            System.out.println("Collision detected");
            world.destroyBody(body7);
            pigexists = false;

            gamewon = true;

        }

        world.step(1 / 60f, 6, 2);
        debugRenderer.render(world, stage.getCamera().combined);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Vector2 bodyPosition4 = body4.getPosition();
        batch.draw(bodyTexture4, bodyPosition4.x * 100f - (bodyTexture4.getWidth() / 2), bodyPosition4.y * 100f - (bodyTexture4.getHeight() / 2), bodyTexture4.getWidth() / 1.5f, bodyTexture4.getHeight() / 1.5f);


        Vector2 bodyPosition = body.getPosition();
        batch.draw(bodyTexture, bodyPosition.x * 100f - (bodyTexture.getWidth() / 2), bodyPosition.y * 100f - (bodyTexture.getHeight() / 2), bodyTexture.getWidth() / 3, bodyTexture.getHeight() / 3);
        Vector2 bodyPosition2 = body2.getPosition();
        batch.draw(bodyTexture2, bodyPosition2.x * 100f - (bodyTexture2.getWidth() / 2), bodyPosition2.y * 100f - (bodyTexture2.getHeight() / 2), bodyTexture2.getWidth() / 3, bodyTexture2.getHeight() / 3);


        Vector2 bodyPosition3 = body3.getPosition();
        batch.draw(bodyTexture3, bodyPosition3.x * 100f - (bodyTexture3.getWidth() / 2), bodyPosition3.y * 100f - (bodyTexture3.getHeight() / 2), bodyTexture3.getWidth() / 3, bodyTexture3.getHeight() / 3);
        Vector2 bodyPosition5 = body5.getPosition();
        batch.draw(bodyTexture5, bodyPosition5.x * 100f - (bodyTexture5.getWidth() / 0.5f), bodyPosition5.y * 100f - (bodyTexture5.getHeight() / 0.6f), bodyTexture5.getWidth() / 0.5f, bodyTexture5.getHeight() / 0.5f);
        Vector2 bodyPosition6 = body6.getPosition();
        batch.draw(bodyTexture6, bodyPosition6.x * 100f - (bodyTexture6.getWidth() / 0.5f), bodyPosition6.y * 100f - (bodyTexture6.getHeight() / 0.6f), bodyTexture6.getWidth() / 0.5f, bodyTexture6.getHeight() / 0.5f);

        if (pigexists) {
            Vector2 bodyPosition7 = body7.getPosition();
            batch.draw(bodyTexture7, bodyPosition7.x * 100f - (bodyTexture7.getWidth() / 2), bodyPosition7.y * 100f - (bodyTexture7.getHeight() / 2), bodyTexture7.getWidth() / 2.5f, bodyTexture7.getHeight() / 2.5f);
        }

        if (isButtonHovered(pauseButtonWidth, pauseButtonHeight, pauseButtonX, pauseButtonY)) {
            batch.draw(PauseButtonActive, pauseButtonX, pauseButtonY, pauseButtonWidth, pauseButtonHeight);
            if (Gdx.input.justTouched()) {
                switchScreen = true;
            }
        } else {
            batch.draw(PauseButtonInactive, pauseButtonX, pauseButtonY, pauseButtonWidth, pauseButtonHeight);
        }


        batch.end();


        Gdx.input.setInputProcessor(new InputAdapter() // input listener
        {

            // the triggered variable allows us to move the bird only when
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (validbird) {
                    if (currentbird.getLinearVelocity().x == 0 && currentbird.getLinearVelocity().y == 0) // a touchdown is only considered if the bodies motion

                    {
                        triggered = true;
                        initialX = screenX; // stores initial point
                        initialY = screenY;
                    }

                }
                return true;

            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (validbird) {
                    currentdragx = screenX;
                    currentdragy = screenY;
                    velocityX = (int) HelperFunctions.CalcVelocityX(initialX, screenX, triggered);
                    velocityY = (int) HelperFunctions.CalcVelocityY(initialY, screenY, triggered);
                }

                return super.touchDragged(screenX, screenY, pointer);
            }

            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (validbird) {
                    if (triggered) {
                        velocityX = (int) HelperFunctions.CalcVelocityX(initialX, screenX, triggered);
                        velocityY = (int) HelperFunctions.CalcVelocityY(initialY, screenY, triggered);

                        currentbird.setLinearVelocity(velocityX, velocityY);
                        triggered = false;

                        if (currentindex == 0) {
                            lastbirdlaunched = true;
                        }

                        if (currentindex > 0) {
                            currentindex--;
                            validbird = false;
                            placed = false;
                        }
                        System.out.println("Bird changed");
                    }

                }

                return true;
            }
        });


        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(bodyPosition5.x, bodyPosition5.y, 10);


        if (triggered) {
            PinkBird p1 = new PinkBird();
            float initialx = p1.getInitialX(); // calculates wrt initial point
            float initialy = p1.getInitialY();
            //Now we have the bird position and we also have the

            float tempvx = velocityX;
            float tempvy = velocityY;

            ArrayList<Float> timepoint = new ArrayList<>();
            timepoint.add(0f);
            timepoint.add(0.2f);
            timepoint.add(0.4f);
            timepoint.add(0.6f);
            timepoint.add(0.8f);
            timepoint.add(1f);


            int initialr = 25;

            for (float t : timepoint) {
                shapeRenderer.circle(100 * HelperFunctions.CalcXcoord((int) initialx, t, (int) tempvx, triggered), 100 * HelperFunctions.CalcYcoord((int) initialy, t, (int) tempvy, triggered), initialr);
                initialr = initialr - 5;
            }

        }
        shapeRenderer.end();


        ArrayList<Integer> BirdHealths = new ArrayList<>();
        BirdHealths.add(p1.health);
        BirdHealths.add(b1.health);
        BirdHealths.add(r1.health);

        ArrayList<Integer> PigHealths = new ArrayList<>();
        PigHealths.add(p1.health);

        ArrayList<Pair> Positions = new ArrayList<>();
        Positions.add(new Pair(body.getPosition().x, body.getPosition().y));
        Positions.add(new Pair(body2.getPosition().x, body2.getPosition().y));
        Positions.add(new Pair(body3.getPosition().x, body3.getPosition().y));
        Positions.add(new Pair(body7.getPosition().x, body7.getPosition().y));
        Positions.add(new Pair(body5.getPosition().x, body5.getPosition().y));
        Positions.add(new Pair(body6.getPosition().x, body6.getPosition().y));


        game1State.updateGameState(BirdHealths, PigHealths, Positions, currentindex);


        if (switchScreen) {
            game.setScreen(new PauseScreen(game, game1State));

        }

        if (gamewon) {
            game.setScreen(new LevelUpScreen2(game));
        }

        if (lastbirdlaunched && body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0 && currentindex == 0) {
            game.setScreen(new DefeatedScreen(game));
        }


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
    public void dispose() {  // executed when the screen is disposed
        batch.dispose();
        backgroundImage.dispose();
        bodyTexture.dispose();
        bodyTexture2.dispose();
        bodyTexture3.dispose();
        bodyTexture4.dispose();
        bodyTexture5.dispose();
        bodyTexture6.dispose();
        bodyTexture7.dispose();
        PauseButtonActive.dispose();
        PauseButtonInactive.dispose();
        world.destroyBody(body);
        world.destroyBody(body2);
        world.destroyBody(body3);
        world.destroyBody(body4);
        world.destroyBody(body5);
        world.destroyBody(body6);
        if (pigexists) {
            world.destroyBody(body7);
        }

        world.destroyBody(RightBoundary);
        world.dispose();
    }
}
