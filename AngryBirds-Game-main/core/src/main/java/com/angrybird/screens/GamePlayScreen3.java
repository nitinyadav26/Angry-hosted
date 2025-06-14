//This Gameplay Screen is for Level-3.
package com.angrybird.screens;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.angrybird.*;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

class Game3State implements Serializable {
    // bird healths arraylist
    ArrayList<Integer> BirdHealths = new ArrayList<>();
    ArrayList<Integer> PigHealth = new ArrayList<>();
    // Pigtype3,Pigtype1
    // Redbird,BlackBird,PinkBird
    ArrayList<Pair> Positions = new ArrayList<>();
    Integer currentindex = 0;

    ArrayList<Integer> BlockHealths = new ArrayList<>();
    // Redbird, BlackBird,PinkBird,Pigtype3,PigType1,Left Block,Right Block positions,topblock,currentindex

    public void updateGameState(ArrayList<Integer> birdHealths, ArrayList<Integer> pigHealths, ArrayList<Pair> Positions, Integer currentindex, ArrayList<Integer> BlockHealths) {
        this.BirdHealths = birdHealths;
        this.PigHealth = pigHealths;
        this.Positions = Positions;
        this.currentindex = currentindex;
        this.BlockHealths = BlockHealths;

    }
}

class GameData3 {
    public static HashMap<Body, Object> BodytoObjectMap = new HashMap<>();
    /// needs to be set at the starting when creating instances and bodies
}

class MyContactListener3 implements ContactListener {

    // need to set the bird bodies
    // also need to set the pigs
    // also need to set the block bodies

    ArrayList<Body> BirdBodies;
    // objects : redbird,blackbird,bluebird (for lev-3)
    ArrayList<Body> PigBodies;
    //objects : 1pig
    ArrayList<Body> BlockBodies;


    public void setInformation(ArrayList<Body> birdbodyarray, ArrayList<Body> pigbodyarray, ArrayList<Body> BlockBody) {
        BirdBodies = birdbodyarray;
        PigBodies = pigbodyarray;
        BlockBodies = BlockBody;
    }


    @Override
    public void beginContact(Contact contact) {
//        System.out.println("Detected collision");
        // This method is called when two bodies begin to collide
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

//        System.out.println("Collision detected: " + bodyA + " with " + bodyB);

        // Collision between Bird and PigType1


        if ((BirdBodies.contains(bodyA) && PigBodies.contains(bodyB)) ||
            (BirdBodies.contains(bodyB) && PigBodies.contains(bodyA))) {

            Body pigBody = PigBodies.contains(bodyA) ? bodyA : bodyB;
            Body birdBody = BirdBodies.contains(bodyA) ? bodyA : bodyB;

            Object pigObject = GameData3.BodytoObjectMap.get(pigBody);

            if (CheckCollisionValid(birdBody)) {
                if (pigObject instanceof PigType1) {
                    PigType1 collisionPig1 = (PigType1) pigObject;
                    collisionPig1.setHealth(0); // Handle PigType1-specific behavior
                    handleBirdCollision(birdBody);
                }

                if (pigObject instanceof PigType3) {

                    PigType3 collisionPig3 = (PigType3) pigObject;

                    collisionPig3.setHealth(collisionPig3.getHealth() - 1);
                    handleBirdCollision(birdBody);

                }
            }
        }

        if ((BirdBodies.contains(bodyA) && BlockBodies.contains(bodyB)) ||
            (BirdBodies.contains(bodyB) && BlockBodies.contains(bodyA))) {
            Body blockBody = BlockBodies.contains(bodyA) ? bodyA : bodyB;
            Body birdBody = BirdBodies.contains(bodyA) ? bodyA : bodyB;

            Object blockObject = GameData3.BodytoObjectMap.get(blockBody);
            if (CheckCollisionValid(birdBody)) {
                if (blockObject instanceof WoodenBlock) {
                    WoodenBlock woodenblockcontact = (WoodenBlock) blockObject;
                    woodenblockcontact.health = woodenblockcontact.health - 1;
                }

                if (blockObject instanceof CementBlock) {
                    CementBlock cementblock = (CementBlock) blockObject;
                    if (GameData3.BodytoObjectMap.get(birdBody) instanceof BlackBird) {
                        cementblock.health = cementblock.health - 3;
                    } else {
                        cementblock.health = cementblock.health - 1;
                    }
                }
                handleBirdCollision(birdBody);
            }


        }
    }

    private boolean CheckCollisionValid(Body birdBody) {
        int indexfound = -1;

        for (int i = 0; i < BirdBodies.size(); i++) {
            if (BirdBodies.get(i).equals(birdBody)) {
                indexfound = i;
                break;
            }
        }

        if (indexfound == 0) {
            RedBird r1 = (RedBird) GameData3.BodytoObjectMap.get(birdBody);
            if (r1.health > 0) {
                r1.setHealth(0);
                return true;
            }
            return false;


        } else if (indexfound == 1) {
            BlackBird b1 = (BlackBird) GameData3.BodytoObjectMap.get(birdBody);
            if (b1.health > 0) {
                b1.setHealth(0);
                return true;
            }
            return false;

        } else if (indexfound == 2) {
            PinkBird p1 = (PinkBird) GameData3.BodytoObjectMap.get(birdBody);
            if (p1.health > 0) {
                p1.setHealth(0);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    // Helper method to handle bird collision
    private void handleBirdCollision(Body birdBody) {
        int indexfound = -1;

        for (int i = 0; i < BirdBodies.size(); i++) {
            if (BirdBodies.get(i).equals(birdBody)) {
                indexfound = i;
                break;
            }
        }

        if (indexfound == 0) {
            RedBird r1 = (RedBird) GameData3.BodytoObjectMap.get(birdBody);
            r1.setHealth(0);
        } else if (indexfound == 1) {

            BlackBird b1 = (BlackBird) GameData3.BodytoObjectMap.get(birdBody);
            System.out.println("Collision detected with blackbird");
            b1.setHealth(0);

        } else if (indexfound == 2) {
            PinkBird p1 = (PinkBird) GameData3.BodytoObjectMap.get(birdBody);
            p1.setHealth(0);
        }
    }


    @Override
    public void endContact(Contact contact) {
        //this method is called when two bodies stop colliding
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        //Reset states or handle post-collision actions
        if (fixtureA.getUserData() != null && fixtureB.getUserData() != null) {
            if (fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("enemy")) {
                // Handle the end of the collision
                System.out.println("Player stopped colliding with enemy!");
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // This method is called before the physics engine processes the collision
        // You can manipulate the collision properties here
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // This method is called after the physics engine processes the collision
        // You can get impulse data or handle post-collision actions
    }
}

public class GamePlayScreen3 extends HelperFunctions implements Screen {
    private final AngryBirdsGame game;
    private Stage stage;
    private SpriteBatch batch;
    private float elapsedTime = 0;
    private Texture backgroundImage;

    boolean lastbirdlaunched = false;

    private ParticleEffect trailEffect;
    private Texture bodyTexture;
    private Texture bodyTexture2;
    private Texture bodyTexture3;
    private Texture bodyTexture4;
    private Texture bodyTexture5;
    private Texture bodyTexture6;
    private Texture bodyTexture7;
    private TextureRegion bodyTexture8;
    private PigType3 pigType3;
    private Texture pig3Texture;


    float currentdragx = 0;
    float currentdragy = 0;

    boolean placed = false;
    boolean pigexists = true;
    boolean pig3exists = true;

    RedBird r1;
    BlackBird b1;
    PinkBird p1;
    WoodenBlock w1;
    PigType1 pig1;
    boolean movingpigexists = true;
    CementBlock leftBlock;
    CementBlock rightBlock;
    WoodenBlock topBlock;

    private  Body RightBoundary;

    boolean leftblockexists = true;
    boolean rightblockexists = true;
    boolean topblockexists = true;


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
    private Body pig3Body;
    private Body Groundbody;
    private Texture PauseButtonActive;
    private Texture PauseButtonInactive;
    private Box2DDebugRenderer debugRenderer;
//    private boolean pigExists = true; // Tracks if PigType3 is active


    private MyContactListener3 contactListener3;
    Game3State game3State = new Game3State();

    boolean validbird = false; // to indicate that the bird set is valid
    private Body currentbird;// this will be used to indicate the bird that is currently being launched
    int currentindex = 2;


    boolean gamewon = false;
    boolean gamelost = false;


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

    private TextureRegionDrawable createColorDrawable(Color color, float opacity) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(color.r, color.g, color.b, opacity));
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(texture);
    }


    private boolean isButtonHovered(int buttonWidth, int buttonHeight, int x, int y) {
        return Gdx.input.getX() > x && Gdx.input.getX() < x + buttonWidth &&
            Gdx.graphics.getHeight() - Gdx.input.getY() > y &&
            Gdx.graphics.getHeight() - Gdx.input.getY() < y + buttonHeight;
    }

    public GamePlayScreen3(AngryBirdsGame game) {
        switchScreen = false;
        PauseButtonActive = new Texture("Pause2.png");
        PauseButtonInactive = new Texture("Pause1.png");
        this.game = game;


        shapeRenderer = new ShapeRenderer();

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        world = new World(new Vector2(0, -9.8f), true);

        debugRenderer = new Box2DDebugRenderer();

        trailEffect = new ParticleEffect();
        trailEffect.load(Gdx.files.internal("Flame2.p"), Gdx.files.internal(""));
        trailEffect.start(); //Start the effect

        backgroundImage = new Texture(Gdx.files.internal("ab bg-8.jpg"));
        pig3Texture = new Texture(Gdx.files.internal("pig3.png"));
        bodyTexture = new Texture(Gdx.files.internal("redbird.png"));
        bodyTexture2 = new Texture(Gdx.files.internal("blackbird.png"));
        bodyTexture3 = new Texture(Gdx.files.internal("pinkbird.png"));
        bodyTexture4 = new Texture(Gdx.files.internal("slingshot.png"));
        bodyTexture5 = new Texture(Gdx.files.internal("stoneBlock.png"));
        bodyTexture6 = new Texture(Gdx.files.internal("stoneBlock.png"));
        bodyTexture7 = new Texture(Gdx.files.internal("pig1.png"));
        Texture bodyTextureBase8 = new Texture(Gdx.files.internal("woodblock.png"));
        bodyTexture8 = new TextureRegion(bodyTextureBase8);


        // declaring bird class objects for the access of inital position,friction and density
        // characteristics
        r1 = new RedBird();
        b1 = new BlackBird();
        p1 = new PinkBird();
        w1 = new WoodenBlock();
        pig1 = new PigType1();
        leftBlock = new CementBlock();
        rightBlock = new CementBlock();
        topBlock = new WoodenBlock();

        r1.setHealth(1);
        p1.setHealth(1);
        pig1.setHealth(1);

        leftBlock.setHealth(3);
        rightBlock.setHealth(3);
        topBlock.health = 2;

        //we need some sort of ground body so that the objects placed on the screen do not go down.
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

        //initialize PigType3 (with jumping feature) at position(e.g., x = 900, y = 100)
        pigType3 = new PigType3(new Vector2(1080, 300));

        // Adding PigType3 to the Box2D world
        BodyDef pigBodyDef = new BodyDef();
        pigBodyDef.type = BodyDef.BodyType.DynamicBody;
        pigBodyDef.position.set((pigType3.getPosition().x + 62.5f) / 100f,
            (pigType3.getPosition().y + 62.5f) / 100f); // Adjust to center


        pig3Body = world.createBody(pigBodyDef);

        CircleShape pigShape = new CircleShape();
        pigShape.setRadius(0.435f);

        FixtureDef pigFixtureDef = new FixtureDef();
        pigFixtureDef.shape = pigShape;
        pigFixtureDef.density = pigType3.getDensity();
        pigFixtureDef.friction = pigType3.getFriction();
        pigFixtureDef.restitution = 0.5f;
        pig3Body.createFixture(pigFixtureDef);
        pigShape.dispose();

        GameData3.BodytoObjectMap.put(pig3Body, pigType3);

        //red bird
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
        fixtureDef.restitution = 0.3f;
        body.createFixture(fixtureDef);
        boxShape.dispose();


        Integer screenWidth = Gdx.graphics.getWidth();
        Integer screenHeight = Gdx.graphics.getHeight();

        BodyDef boundarybodyDef = new BodyDef();
        boundarybodyDef.type = BodyDef.BodyType.StaticBody;
        boundarybodyDef.position.set(20f,2f);
        RightBoundary= world.createBody(boundarybodyDef);
        PolygonShape boundaryBodyShape = new PolygonShape();
        boundaryBodyShape.setAsBox(0.001f,screenHeight);
        FixtureDef boundaryfixtureDef = new FixtureDef();
        boundaryfixtureDef.shape = boundaryBodyShape;
        boundaryfixtureDef.density = 0f; // Static bodies don't need density
        boundaryfixtureDef.friction = 0.5f;
        boundaryfixtureDef.restitution = 0.5f; // Bounciness
        RightBoundary.createFixture(boundaryfixtureDef);
        boundaryBodyShape.dispose();

        //black bird
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.DynamicBody;
        bodyDef2.position.set(b1.getInitialX(), b1.getInitialY());
        body2 = world.createBody(bodyDef2);
        PolygonShape boxShape2 = new PolygonShape();
        boxShape2.setAsBox(0.2f, 0.5f);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = boxShape2;
        fixtureDef2.density = b1.getDensity();
        fixtureDef2.friction = b1.getFriction();
        fixtureDef2.restitution = 0.3f;
        body2.createFixture(fixtureDef2);
        boxShape2.dispose();

        //pink bird
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
        fixtureDef3.restitution = 0.3f;
        body3.createFixture(fixtureDef3);
        boxShape3.dispose();

        //slingshot
        BodyDef bodyDef4 = new BodyDef();
        bodyDef4.type = BodyDef.BodyType.StaticBody;
        bodyDef4.position.set(4.5f, 3.39f);
        body4 = world.createBody(bodyDef4);
        PolygonShape boxShape4 = new PolygonShape();
        boxShape4.setAsBox(0.3f, 0.8f);
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

        GameData3.BodytoObjectMap.put(body, r1);
        GameData3.BodytoObjectMap.put(body2, b1);
        GameData3.BodytoObjectMap.put(body3, p1);

        //cement block
        BodyDef bodyDef5 = new BodyDef();
        bodyDef5.type = BodyDef.BodyType.DynamicBody;
        bodyDef5.position.set(14f, 1.3f);
        body5 = world.createBody(bodyDef5);
        PolygonShape boxShape5 = new PolygonShape();
        boxShape5.setAsBox(0.33f, 1.99f);
        FixtureDef fixtureDef5 = new FixtureDef();
        fixtureDef5.shape = boxShape5;
        fixtureDef5.restitution = 0f;
        body5.createFixture(fixtureDef5);
        boxShape5.dispose();

        //cement block
        BodyDef bodyDef6 = new BodyDef();
        bodyDef6.type = BodyDef.BodyType.DynamicBody;
        bodyDef6.position.set(17f, 1.3f);
        body6 = world.createBody(bodyDef6);
        PolygonShape boxShape6 = new PolygonShape();
        boxShape6.setAsBox(0.33f, 1.99f);
        FixtureDef fixtureDef6 = new FixtureDef();
        fixtureDef6.shape = boxShape6;
        fixtureDef6.restitution = 0f;
        body6.createFixture(fixtureDef6);
        boxShape6.dispose();

        //pig1
        BodyDef bodyDef7 = new BodyDef();
        bodyDef7.type = BodyDef.BodyType.DynamicBody;
        bodyDef7.position.set(16.3f, 2f);
        body7 = world.createBody(bodyDef7);
        PolygonShape boxShape7 = new PolygonShape();
        boxShape7.setAsBox(1f, 0.5f);
        FixtureDef fixtureDef7 = new FixtureDef();
        fixtureDef7.shape = boxShape7;
        fixtureDef7.density = pig1.getDensity();
        fixtureDef7.friction = pig1.getFriction();
        fixtureDef7.restitution = 0.5f;
        fixtureDef7.friction = 0.05f;
        body7.createFixture(fixtureDef7);
        boxShape7.dispose();

        // Adding PigType1 to mappings
        GameData3.BodytoObjectMap.put(body7, pig1);

        BodyDef bodyDef8 = new BodyDef();
        bodyDef8.type = BodyDef.BodyType.DynamicBody;
        bodyDef8.position.set(16.2f, 6.5f);
        body8 = world.createBody(bodyDef8);
        PolygonShape boxShape8 = new PolygonShape();
        boxShape8.setAsBox(2.5f, 0.089f);
        FixtureDef fixtureDef8 = new FixtureDef();
        fixtureDef8.shape = boxShape8;
        fixtureDef8.density = 1f;
        fixtureDef8.friction = 0.5f;
        fixtureDef8.restitution = 0.5f;
        body8.createFixture(fixtureDef8);
        boxShape8.dispose();

        // to handle launching we will set method

        // we will now provide all the information to the contact listener


        GameData3.BodytoObjectMap.put(body5, leftBlock);
        GameData3.BodytoObjectMap.put(body6, rightBlock);
        GameData3.BodytoObjectMap.put(body8, topBlock);

        contactListener3 = new MyContactListener3();
        pigBodies = new ArrayList<>();
        pigBodies.add(body7);
        pigBodies.add(pig3Body);
        blockBodies = new ArrayList<>();
        blockBodies.add(body5);
        blockBodies.add(body6);
        blockBodies.add(body8);

        contactListener3.setInformation(BirdList, pigBodies, blockBodies);

        body.setBullet(true);
        body2.setBullet(true);
        body3.setBullet(true);


    }


    public GamePlayScreen3(AngryBirdsGame game, Game3State gameState) {
        switchScreen = false;
        PauseButtonActive = new Texture("Pause2.png");
        PauseButtonInactive = new Texture("Pause1.png");
        this.game = game;


        shapeRenderer = new ShapeRenderer();

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        world = new World(new Vector2(0, -9.8f), true);

        debugRenderer = new Box2DDebugRenderer();

        trailEffect = new ParticleEffect();
        trailEffect.load(Gdx.files.internal("Flame2.p"), Gdx.files.internal(""));
        trailEffect.start(); // Start the effect
        currentindex = gameState.currentindex;

        backgroundImage = new Texture(Gdx.files.internal("ab bg-8.jpg"));
        pig3Texture = new Texture(Gdx.files.internal("pig3.png"));
        bodyTexture = new Texture(Gdx.files.internal("redbird.png"));
        bodyTexture2 = new Texture(Gdx.files.internal("blackbird.png"));
        bodyTexture3 = new Texture(Gdx.files.internal("pinkbird.png"));
        bodyTexture4 = new Texture(Gdx.files.internal("slingshot.png"));
        bodyTexture5 = new Texture(Gdx.files.internal("stoneBlock.png"));
        bodyTexture6 = new Texture(Gdx.files.internal("stoneBlock.png"));
        bodyTexture7 = new Texture(Gdx.files.internal("pig1.png"));
        Texture bodyTextureBase8 = new Texture(Gdx.files.internal("woodblock.png"));
        bodyTexture8 = new TextureRegion(bodyTextureBase8);


        // declaring bird class objects for the access of inital position,friction and density
        // characteristics
        r1 = new RedBird();
        b1 = new BlackBird();
        p1 = new PinkBird();
        w1 = new WoodenBlock();
        pig1 = new PigType1();
        leftBlock = new CementBlock();
        rightBlock = new CementBlock();
        topBlock = new WoodenBlock();

        r1.setHealth(gameState.BirdHealths.get(0));
        b1.setHealth(gameState.BirdHealths.get(1));
        p1.setHealth(gameState.BirdHealths.get(2));
        pig1.setHealth(gameState.PigHealth.get(1));

        leftBlock.setHealth(gameState.BlockHealths.get(0));
        rightBlock.setHealth(gameState.BlockHealths.get(1));
        topBlock.health = gameState.BlockHealths.get(2);

        //we need some sort of ground body so that the objects placed on the screen do not go down.
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

        //initialize PigType3 (with jumping feature) at position(e.g., x = 900, y = 100)
        pigType3 = new PigType3(new Vector2(1080, 300));
        pigType3.health = gameState.PigHealth.get(0);

        //Added PigType3 to the Box2D world
        BodyDef pigBodyDef = new BodyDef();
        pigBodyDef.type = BodyDef.BodyType.DynamicBody;
        pigBodyDef.position.set((pigType3.getPosition().x + 62.5f) / 100f,
            (pigType3.getPosition().y + 62.5f) / 100f); // Adjust to center


        pig3Body = world.createBody(pigBodyDef);

        CircleShape pigShape = new CircleShape();
        pigShape.setRadius(0.435f);

        FixtureDef pigFixtureDef = new FixtureDef();
        pigFixtureDef.shape = pigShape;
        pigFixtureDef.density = pigType3.getDensity();
        pigFixtureDef.friction = pigType3.getFriction();
        pigFixtureDef.restitution = 0.5f;
        pig3Body.createFixture(pigFixtureDef);
        pigShape.dispose();


        Integer screenWidth = Gdx.graphics.getWidth();
        Integer screenHeight = Gdx.graphics.getHeight();

        BodyDef boundarybodyDef = new BodyDef();
        boundarybodyDef.type = BodyDef.BodyType.StaticBody;
        boundarybodyDef.position.set(20f,2f);
        RightBoundary= world.createBody(boundarybodyDef);
        PolygonShape boundaryBodyShape = new PolygonShape();
        boundaryBodyShape.setAsBox(0.001f,screenHeight);
        FixtureDef boundaryfixtureDef = new FixtureDef();
        boundaryfixtureDef.shape = boundaryBodyShape;
        boundaryfixtureDef.density = 0f; // Static bodies don't need density
        boundaryfixtureDef.friction = 0.5f;
        boundaryfixtureDef.restitution = 0.5f; // Bounciness
        RightBoundary.createFixture(boundaryfixtureDef);
        boundaryBodyShape.dispose();


        GameData3.BodytoObjectMap.put(pig3Body, pigType3);


        //red bird
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(gameState.Positions.get(0).x, gameState.Positions.get(0).y);
        body = world.createBody(bodyDef);
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(0.3f, 0.3f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = r1.getDensity();
        fixtureDef.friction = r1.getFriction();
        fixtureDef.restitution = 0.3f;
        body.createFixture(fixtureDef);
        boxShape.dispose();

        //black bird
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.DynamicBody;
        bodyDef2.position.set(gameState.Positions.get(1).x, gameState.Positions.get(1).y);
        body2 = world.createBody(bodyDef2);
        PolygonShape boxShape2 = new PolygonShape();
        boxShape2.setAsBox(0.2f, 0.5f);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = boxShape2;
        fixtureDef2.density = b1.getDensity();
        fixtureDef2.friction = b1.getFriction();
        fixtureDef2.restitution = 0.3f;
        body2.createFixture(fixtureDef2);
        boxShape2.dispose();

        //pink bird
        BodyDef bodyDef3 = new BodyDef();
        bodyDef3.type = BodyDef.BodyType.DynamicBody;
        bodyDef3.position.set(gameState.Positions.get(2).x, gameState.Positions.get(2).y);
        body3 = world.createBody(bodyDef3);
        PolygonShape boxShape3 = new PolygonShape();
        boxShape3.setAsBox(0.3f, 0.3f);
        FixtureDef fixtureDef3 = new FixtureDef();
        fixtureDef3.shape = boxShape3;
        fixtureDef3.density = p1.getDensity();
        fixtureDef3.friction = p1.getFriction();
        fixtureDef3.restitution = 0.3f;
        body3.createFixture(fixtureDef3);
        boxShape3.dispose();

        //slingshot
        BodyDef bodyDef4 = new BodyDef();
        bodyDef4.type = BodyDef.BodyType.StaticBody;
        bodyDef4.position.set(4.5f, 3.39f);
        body4 = world.createBody(bodyDef4);
        PolygonShape boxShape4 = new PolygonShape();
        boxShape4.setAsBox(0.3f, 0.8f);
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

        GameData3.BodytoObjectMap.put(body, r1);
        GameData3.BodytoObjectMap.put(body2, b1);
        GameData3.BodytoObjectMap.put(body3, p1);

        //cement left block
        BodyDef bodyDef5 = new BodyDef();
        bodyDef5.type = BodyDef.BodyType.DynamicBody;
        bodyDef5.position.set(gameState.Positions.get(5).x, gameState.Positions.get(5).y);
        body5 = world.createBody(bodyDef5);
        PolygonShape boxShape5 = new PolygonShape();
        boxShape5.setAsBox(0.33f, 1.99f);
        FixtureDef fixtureDef5 = new FixtureDef();
        fixtureDef5.shape = boxShape5;
        fixtureDef5.restitution = 0f;
        body5.createFixture(fixtureDef5);
        boxShape5.dispose();

        //cement right block
        BodyDef bodyDef6 = new BodyDef();
        bodyDef6.type = BodyDef.BodyType.DynamicBody;
        bodyDef6.position.set(gameState.Positions.get(6).x, gameState.Positions.get(6).y);
        body6 = world.createBody(bodyDef6);
        PolygonShape boxShape6 = new PolygonShape();
        boxShape6.setAsBox(0.33f, 1.99f);
        FixtureDef fixtureDef6 = new FixtureDef();
        fixtureDef6.shape = boxShape6;
        fixtureDef6.restitution = 0f;
        body6.createFixture(fixtureDef6);
        boxShape6.dispose();

        //pig1
        BodyDef bodyDef7 = new BodyDef();
        bodyDef7.type = BodyDef.BodyType.DynamicBody;
        bodyDef7.position.set(gameState.Positions.get(4).x, gameState.Positions.get(4).y);
        body7 = world.createBody(bodyDef7);
        PolygonShape boxShape7 = new PolygonShape();
        boxShape7.setAsBox(1f, 0.5f);
        FixtureDef fixtureDef7 = new FixtureDef();
        fixtureDef7.shape = boxShape7;
        fixtureDef7.density = pig1.getDensity();
        fixtureDef7.friction = pig1.getFriction();
        fixtureDef7.restitution = 0.5f;
        fixtureDef7.friction = 0.05f;
        body7.createFixture(fixtureDef7);
        boxShape7.dispose();

        // Adding PigType1 to mappings
        GameData3.BodytoObjectMap.put(body7, pig1);

        BodyDef bodyDef8 = new BodyDef();
        bodyDef8.type = BodyDef.BodyType.DynamicBody;
        bodyDef8.position.set(gameState.Positions.get(7).x, gameState.Positions.get(7).y);
        body8 = world.createBody(bodyDef8);
        PolygonShape boxShape8 = new PolygonShape();
        boxShape8.setAsBox(2.5f, 0.089f);
        FixtureDef fixtureDef8 = new FixtureDef();
        fixtureDef8.shape = boxShape8;
        fixtureDef8.density = 1f;
        fixtureDef8.friction = 0.5f;
        fixtureDef8.restitution = 0.5f;
        body8.createFixture(fixtureDef8);
        boxShape8.dispose();

        // to handle launching we will set method

        // we will now provide all the information to the contact listener
        GameData3.BodytoObjectMap.put(body5,leftBlock);
        GameData3.BodytoObjectMap.put(body6,rightBlock);
        GameData3.BodytoObjectMap.put(body8,topBlock);

        contactListener3 = new MyContactListener3();
        pigBodies = new ArrayList<>();
        pigBodies.add(body7);
        pigBodies.add(pig3Body);
        blockBodies = new ArrayList<>();
        blockBodies.add(body5);
        blockBodies.add(body6);
        blockBodies.add(body8);

        contactListener3.setInformation(BirdList, pigBodies, blockBodies);

        body.setBullet(true);
        body2.setBullet(true);
        body3.setBullet(true);
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

        // update pig's jumping behavior
        float groundHeight = 0; // Ground level
        pigType3.updateJump(Gdx.graphics.getHeight(), groundHeight);
        // Pass screen height for boundary check

        world.setContactListener(contactListener3);
        //setting up the valid bird and current bird
        currentbird = BirdList.get(currentindex);
        validbird = true; // here we can write any code we want to execute when we are setting the bird
        if (!placed){
            elapsedTime += delta;
            if (elapsedTime >= 4.6) { // check if 6 seconds have passed
                currentbird.setTransform(4.66f, 4.89f, 0);
                placed = true; // Ensure this logic only runs once
            }
        }
        if (placed) {
            elapsedTime = 0;
        }

        Vector2 birdPosition = body2.getPosition();
        trailEffect.setPosition(birdPosition.x * 100 - 100, birdPosition.y * 100 - 140);
        trailEffect.update(delta);
        if(leftBlock.health<=0)
        {
            leftblockexists=false;
            body5.setActive(false);
            body8.setActive(false);
            topblockexists = false;
        }

        if (rightBlock.health <= 0) {
            rightblockexists = false;
            body6.setActive(false);
            body8.setActive(false);
            topblockexists = false;
        }

        if (topBlock.health <= 0) {
            topblockexists = false;
            body8.setActive(false);
        }


        PigType3 pig3check = (PigType3) GameData3.BodytoObjectMap.get(pig3Body);
        if (pig3check != null && pig3check.health <= 0) {
            movingpigexists = false;
            pig3Body.setActive(false);
            System.out.println("Pig destroyed");

        }

        PigType1 pigtocheck;
        pigtocheck = (PigType1) GameData3.BodytoObjectMap.get(pigBodies.get(0));
        if (pigtocheck.health == 0 && pigexists) {
            System.out.println("Collision detected");
            world.destroyBody(body7);
            pigexists = false;

        }

        if (!pigexists && !movingpigexists) {
            gamewon = true;
        }

        world.step(1 / 60f, 8, 3);
        debugRenderer.render(world, stage.getCamera().combined);

        if (pigType3 != null) {
            // Update PigType3 jumping logic
            pigType3.updateJump(Gdx.graphics.getHeight(), groundHeight);
            PigType3 pig3 = null;

            for (Body pigBody : pigBodies) {
                if (GameData3.BodytoObjectMap.get(pigBody) instanceof PigType3) {
                    pig3 = (PigType3) GameData3.BodytoObjectMap.get(pigBody);
                }
                if (pig3 != null) {
                    pigBody.setTransform((pig3.getPosition().x + 62.5f) / 100f, (pig3.getPosition().y + 62.5f) / 100f, 0);
                }
            }
        } else {
            System.err.println("Error: PigType3 is null during render()");
        }

        debugRenderer.render(world, batch.getProjectionMatrix().cpy().scale(100f, 100f, 1f));

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (movingpigexists) {
            Vector2 pigPosition = pigType3.getPosition();
            batch.draw(pig3Texture, pigPosition.x - 90, pigPosition.y - 140, 125, 125);
        } else {
            System.out.println("PigType3 is no longer active.");
        }

        Vector2 bodyPosition4 = body4.getPosition();
        batch.draw(bodyTexture4, bodyPosition4.x * 100f - (bodyTexture4.getWidth() / 2), bodyPosition4.y * 100f - (bodyTexture4.getHeight() / 2), bodyTexture4.getWidth() / 1.5f, bodyTexture4.getHeight() / 1.5f);


        Vector2 bodyPosition = body.getPosition();
        batch.draw(bodyTexture, bodyPosition.x * 100f - (bodyTexture.getWidth() / 2), bodyPosition.y * 100f - (bodyTexture.getHeight() / 2), bodyTexture.getWidth() / 3, bodyTexture.getHeight() / 3);
        Vector2 bodyPosition2 = body2.getPosition();
        batch.draw(bodyTexture2, bodyPosition2.x * 100f - (bodyTexture2.getWidth() / 2), bodyPosition2.y * 100f - (bodyTexture2.getHeight() / 2), bodyTexture2.getWidth() / 3, bodyTexture2.getHeight() / 3);


        Vector2 bodyPosition3 = body3.getPosition();
        batch.draw(bodyTexture3, bodyPosition3.x * 100f - (bodyTexture3.getWidth() / 2), bodyPosition3.y * 100f - (bodyTexture3.getHeight() / 2), bodyTexture3.getWidth() / 3, bodyTexture3.getHeight() / 3);

        //Stone block 1 rendering
        if(leftblockexists)
        {
            Vector2 bodyPosition5 = body5.getPosition();
            batch.draw(bodyTexture5,
                bodyPosition5.x * 100f - 80f,
                bodyPosition5.y * 100f -330f,
                66, 398);
        }

        //Stone block 2 rendering
        if(rightblockexists)
        {
            Vector2 bodyPosition6 = body6.getPosition();
            batch.draw(bodyTexture6,
                bodyPosition6.x * 100f - 75f,
                bodyPosition6.y * 100f - 330f,
                66, 398);

        }

        if(topblockexists)
        {
            Vector2 bodyPosition8 = body8.getPosition();
            batch.draw(bodyTexture8,
                bodyPosition8.x * 100f,
                bodyPosition8.y * 100f - bodyTexture3.getWidth() / 1.3f,
                bodyTexture8.getRegionWidth() / 2,
                bodyTexture8.getRegionHeight() / 2,
                bodyTexture8.getRegionWidth() / 0.45f,
                bodyTexture8.getRegionHeight() / 0.45f,
                1,
                1,
                90f
            );
        }

        if(movingpigexists)
        {
            Vector2 pigPosition = pigType3.getPosition();
            batch.draw(pig3Texture, pigPosition.x - 90, pigPosition.y - 140, 125, 125);
        }

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

        //End drawing
        trailEffect.draw(batch, delta);
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
                    if (currentindex == 1) {
                        velocityX = velocityX * 3;
                        velocityY = velocityY * 3;
                    }


                }

                return super.touchDragged(screenX, screenY, pointer);
            }

            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (validbird) {
                    if (triggered) {
                        velocityX = (int) HelperFunctions.CalcVelocityX(initialX, screenX, triggered);
                        velocityY = (int) HelperFunctions.CalcVelocityY(initialY, screenY, triggered);
                        if (currentindex == 1) {
                            velocityX = velocityX * 3;
                            velocityY = velocityY * 3;
                        }
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

        PigHealths.add(pigType3.health);
        PigHealths.add(pig1.health);

        ArrayList<Pair> Positions = new ArrayList<>();
        Positions.add(new Pair(body.getPosition().x, body.getPosition().y));
        Positions.add(new Pair(body2.getPosition().x, body2.getPosition().y));
        Positions.add(new Pair(body3.getPosition().x, body3.getPosition().y));
        Positions.add(new Pair(pig3Body.getPosition().x, pig3Body.getPosition().y));
        Positions.add(new Pair(body7.getPosition().x, body7.getPosition().y));

        Positions.add(new Pair(body5.getPosition().x, body5.getPosition().y));
        Positions.add(new Pair(body6.getPosition().x, body6.getPosition().y));
        Positions.add(new Pair(body8.getPosition().x, body8.getPosition().y));


        ArrayList<Integer> BlockHealths = new ArrayList<>();
        BlockHealths.add(leftBlock.health);
        BlockHealths.add(rightBlock.health);
        BlockHealths.add(topBlock.health);


        game3State.updateGameState(BirdHealths, PigHealths, Positions, currentindex, BlockHealths);
        if (lastbirdlaunched && currentindex == 0 && body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0 && !gamewon) {
            gamelost = true;
        }

        if (switchScreen) {
            game.setScreen(new PauseScreen3(game, game3State));
        }

        if (gamelost) {
            game.setScreen(new DefeatedScreen(game));
        }

        if (gamewon) {

            game.setScreen(new VictoryScreen(game));
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
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        bodyTexture.dispose();
        bodyTexture2.dispose();
        bodyTexture3.dispose();
        bodyTexture4.dispose();
        bodyTexture5.dispose();
        bodyTexture6.dispose();
        bodyTexture7.dispose();  // Dispose the pig texture
        pig3Texture.dispose();
        PauseButtonActive.dispose();
        PauseButtonInactive.dispose();
        world.destroyBody(body);
        world.destroyBody(body2);
        world.destroyBody(body3);
        world.destroyBody(body4);
        world.destroyBody(body5);
        world.destroyBody(body6);
        world.destroyBody(body8);
        if (pigexists) {
            world.destroyBody(body7);
        }
        world.dispose();
    }
}
