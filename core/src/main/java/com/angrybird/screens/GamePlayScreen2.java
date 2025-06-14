//This Gameplay Screen is for Level-2.
package com.angrybird.screens;

import com.angrybird.*;
import com.badlogic.gdx.InputAdapter;
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


import com.badlogic.gdx.physics.box2d.*;


class Game2State implements Serializable {
    // bird healths arraylist
    ArrayList<Integer> BirdHealths = new ArrayList<>();
    ArrayList<Integer> PigHealth = new ArrayList<>();
    ArrayList<Integer> BlockHealths = new ArrayList<>();
    // block healths consists of left, right,glass order
    ArrayList<Pair> Positions = new ArrayList<>();
    Integer currentindex = 0;


    Boolean leftblockexists = true;
    Boolean rightblockexists = true;
    Boolean glassblockexists = true;

    Boolean upperpigexists = true;
    Boolean lowerpigexists = true;
    // Redbird,Yellow,PinkBird,PigType1,Pigtype2,Left Block,Right Block positions, GlassBlock

    public void updateGameState(ArrayList<Integer> birdHealths, ArrayList<Integer> pigHealths, ArrayList<Pair> Positions, ArrayList<Integer> blockHealths, Integer currentindex) {
        this.BirdHealths = birdHealths;
        this.PigHealth = pigHealths;
        this.Positions = Positions;
        this.currentindex = currentindex;
        this.BlockHealths = blockHealths;

    }
}


class GameData2 {
    public static HashMap<Body, Object> BodytoObjectMap = new HashMap<>();
    /// needs to be set at the starting when creating instances and bodies
}

class MyContactListener2 implements ContactListener {

    // need to set the bird bodies
    // also need to set the pigs
    // also need to set the block bodies

    ArrayList<Body> BirdBodies;
    // objects : redbird,yellow bird,pinkbird
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
        //this method is called when two bodies begin to collide
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        boolean contact1 = false;

        PigType1 pig1 = null;
        PigType2 pig2 = null;


        // handling bird and block collisions
        if (BlockBodies.contains(bodyA) && BirdBodies.contains(bodyB)) {

            contact1 = true;
            int indexfound = 0;
            for (int i = 0; i < BirdBodies.size(); i++) {
                if (BirdBodies.get(i).equals(bodyB)) {
                    indexfound = i;
                }
            }

            int blockfound = 0;
            for (int i = 0; i < BlockBodies.size(); i++) {
                if (BlockBodies.get(i).equals(bodyA)) {
                    blockfound = i;
                }
            }

            if (indexfound == 1)// checking for yellow bird seperately as it deals more damage
            {

                YellowBird yellowBird = (YellowBird) GameData2.BodytoObjectMap.get(BirdBodies.get(1));

                if (yellowBird.health > 0) {
                    yellowBird.health = yellowBird.health - 1;

                    if (blockfound == 0) {
                        WoodenBlock leftblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        leftblock.health = leftblock.health - 2;


                    } else if (blockfound == 1) {
                        WoodenBlock rightblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        rightblock.health = rightblock.health - 2;
                    } else if (blockfound == 2) {
                        GlassBlock glassBlock = (GlassBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        glassBlock.health = glassBlock.health - 2;
                    }

                }


            } else if (indexfound == 2) {
                PinkBird pinkBird = (PinkBird) GameData2.BodytoObjectMap.get(BirdBodies.get(indexfound));

                if (pinkBird.health > 0) {
                    pinkBird.health = pinkBird.health - 1;
                    if (blockfound == 0) {
                        WoodenBlock leftblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        leftblock.health = leftblock.health - 1;
                    } else if (blockfound == 1) {
                        WoodenBlock rightblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        rightblock.health = rightblock.health - 1;
                    } else if (blockfound == 2) {
                        GlassBlock glassBlock = (GlassBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        glassBlock.health = glassBlock.health - 1;
                    }
                }
            }
            else if (indexfound == 0) {
                RedBird redBird = (RedBird) GameData2.BodytoObjectMap.get(BirdBodies.get(indexfound));
                if (redBird.health > 0) {
                    redBird.health = redBird.health - 1;
                    if (blockfound == 0) {
                        WoodenBlock leftblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        leftblock.health = leftblock.health - 1;
                    } else if (blockfound == 1) {
                        WoodenBlock rightblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        rightblock.health = rightblock.health - 1;
                    } else if (blockfound == 2) {
                        GlassBlock glassBlock = (GlassBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        glassBlock.health = glassBlock.health - 1;
                    }
                }


            }
        }
        else if (BlockBodies.contains(bodyB) && BirdBodies.contains(bodyA)) {

            contact1 = true;
            int indexfound = 0;
            for (int i = 0; i < BirdBodies.size(); i++) {
                if (BirdBodies.get(i).equals(bodyA)) {
                    indexfound = i;
                }
            }

            int blockfound = 0;
            for (int i = 0; i < BlockBodies.size(); i++) {
                if (BlockBodies.get(i).equals(bodyB)) {
                    blockfound = i;
                }
            }

            if (indexfound == 1)// checking for yellow bird seperately as it deals more damage
            {

                YellowBird yellowBird = (YellowBird) GameData2.BodytoObjectMap.get(BirdBodies.get(1));

                if (yellowBird.health > 0) {
                    yellowBird.health = yellowBird.health - 1;
                    if (blockfound == 0) {
                        WoodenBlock leftblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        leftblock.health = leftblock.health - 2;


                    } else if (blockfound == 1) {
                        WoodenBlock rightblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        rightblock.health = rightblock.health - 2;
                    } else if (blockfound == 2) {
                        GlassBlock glassBlock = (GlassBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        glassBlock.health = glassBlock.health - 2;
                    }

                }


            } else if (indexfound == 2) {
                PinkBird pinkBird = (PinkBird) GameData2.BodytoObjectMap.get(BirdBodies.get(indexfound));

                if (pinkBird.health > 0) {
                    pinkBird.health = pinkBird.health - 1;
                    if (blockfound == 0) {
                        WoodenBlock leftblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        leftblock.health = leftblock.health - 1;
                    } else if (blockfound == 1) {
                        WoodenBlock rightblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        rightblock.health = rightblock.health - 1;
                    } else if (blockfound == 2) {
                        GlassBlock glassBlock = (GlassBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        glassBlock.health = glassBlock.health - 1;
                    }
                }


            } else if (indexfound == 0) {
                RedBird redBird = (RedBird) GameData2.BodytoObjectMap.get(BirdBodies.get(indexfound));

                if (redBird.health > 0) {
                    redBird.health = redBird.health - 1;
                    if (blockfound == 0) {
                        WoodenBlock leftblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        leftblock.health = leftblock.health - 1;
                    } else if (blockfound == 1) {
                        WoodenBlock rightblock = (WoodenBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        rightblock.health = rightblock.health - 1;
                    } else if (blockfound == 2) {
                        GlassBlock glassBlock = (GlassBlock) GameData2.BodytoObjectMap.get(BlockBodies.get(blockfound));
                        glassBlock.health = glassBlock.health - 1;
                    }
                }


            }
        }

        if (!contact1) {
            if ((BirdBodies.contains(bodyA) && PigBodies.contains(bodyB))) {

                int indexfound = 0;
                for (int i = 0; i < BirdBodies.size(); i++) {
                    if (BirdBodies.get(i).equals(bodyA)) {
                        indexfound = i;
                        break;
                    }
                }


                int indexfoundpig = 0;
                for (int i = 0; i < PigBodies.size(); i++) {
                    if (PigBodies.get(i).equals(bodyB)) {
                        indexfoundpig = i;
                        break;
                    }
                }
                boolean collisionvalid = false;

                if (indexfound == 0) {
                    RedBird r1 = (RedBird) GameData2.BodytoObjectMap.get(bodyA);
                    if (r1.health > 0) {
                        collisionvalid = true;
                    }
                    r1.setHealth(r1.health - 1);

                } else if (indexfound == 1) {
                    YellowBird y1 = (YellowBird) GameData2.BodytoObjectMap.get(bodyA);
                    if (y1.health > 0) {
                        collisionvalid = true;
                    }
                    y1.setHealth(y1.health - 1);

                } else if (indexfound == 2) {
                    PinkBird p1 = (PinkBird) GameData2.BodytoObjectMap.get(bodyA);
                    if (p1.health > 0) {
                        collisionvalid = true;
                    }

                    p1.setHealth(p1.health - 1);

                }


                if (indexfoundpig == 0) {
                    pig1 = (PigType1) GameData2.BodytoObjectMap.get(bodyB);
                } else if (indexfoundpig == 1) {
                    pig2 = (PigType2) GameData2.BodytoObjectMap.get(bodyB);
                }
                if (collisionvalid) {
                    if (pig1 != null) {

                        if (indexfound == 1) {
                            pig1.health = pig1.health - 3;
                        } else {
                            pig1.health = pig1.health - 1;
                        }


                        System.out.println("Collision with pig type 1");
                    } else if (pig2 != null) {

                        if (indexfound == 1) {
                            pig2.health = pig2.health - 3;
                        } else {
                            pig2.health = pig2.health - 1;
                        }

                        System.out.println("Collision with pig type 2");

                    }
                }


            } else if (BirdBodies.contains(bodyB) && PigBodies.contains(bodyA)) {


                int indexfound = 0;
                for (int i = 0; i < BirdBodies.size(); i++) {
                    if (BirdBodies.get(i).equals(bodyB)) {
                        indexfound = i;
                        break;
                    }
                }


                int indexfoundpig = 0;
                for (int i = 0; i < PigBodies.size(); i++) {
                    if (PigBodies.get(i).equals(bodyA)) {
                        indexfoundpig = i;
                        break;
                    }
                }

                if (indexfoundpig == 0) {
                    pig1 = (PigType1) GameData2.BodytoObjectMap.get(bodyA);
                } else {
                    pig2 = (PigType2) GameData2.BodytoObjectMap.get(bodyA);
                }

                boolean collisonvalid = false;

                if (indexfound == 0) {
                    RedBird r1 = (RedBird) GameData2.BodytoObjectMap.get(bodyB);
                    if (r1.health > 0) {
                        collisonvalid = true;
                    }
                    r1.setHealth(r1.health - 1);

                } else if (indexfound == 1) {
                    YellowBird y1 = (YellowBird) GameData2.BodytoObjectMap.get(bodyB);
                    if (y1.health > 0) {
                        collisonvalid = true;
                    }

                    y1.setHealth(y1.health - 1);

                } else if (indexfound == 2) {
                    PinkBird p1 = (PinkBird) GameData2.BodytoObjectMap.get(bodyB);
                    if (p1.health > 0) {
                        collisonvalid = true;
                    }
                    p1.setHealth(p1.health - 1);

                }


                if (collisonvalid) {
                    if (pig1 != null) {
                        if (indexfound == 1) {
                            pig1.health = pig1.health - 3;
                        } else {
                            pig1.health = pig1.health - 1;
                        }

                        System.out.println("Collision with pig type1");
                    } else if (pig2 != null) {
                        if (indexfound == 1) {
                            pig2.health = pig2.health - 3;
                        } else {
                            pig2.health = pig2.health - 1;
                        }

                        System.out.println("Collision with pig type2");

                    }
                }


            }


        }
    }

    @Override
    public void endContact(Contact contact) {
        // this method is called when two bodies stop colliding
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() != null && fixtureB.getUserData() != null) {
            if (fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("enemy")) {
                System.out.println("Player stopped colliding with enemy!");
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // This method is called before the physics engine processes the collision
        // we can manipulate the collision properties here
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // This method is called after the physics engine processes the collision
        // we can get impulse data or handle post-collision actions
    }
}


public class GamePlayScreen2 extends HelperFunctions implements Screen {
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
    private Texture bodyTexture9;
    private TextureRegion bodyTexture8;


    public boolean leftblockexists = true;
    public boolean rightblockexists = true;
    public boolean glassblockexists = true;


    float currentdragx = 0;
    float currentdragy = 0;

    boolean placed = false;
    boolean pigexists = true;
    boolean pigexists2 = true;


    RedBird r1;
    YellowBird y1;
    PinkBird p1;
    GlassBlock g1;
    PigType1 bottompig;
    PigType2 upperpig;
    WoodenBlock leftblock;
    WoodenBlock rightblock;


    private boolean lastBirdLaunched = false;


    private boolean switchScreen;
    private World world;
    private Body RightBoundary;
    private Body body;
    private Body body2;
    private Body body3;
    private Body body4;
    private Body body5;
    private Body body6;
    private Body body7;
    private Body body8;
    private Body body9;
    private Body Groundbody;
    private Texture PauseButtonActive;
    private Texture PauseButtonInactive;
    private Box2DDebugRenderer debugRenderer;

    private MyContactListener2 contactListener2;
    Game2State game2State = new Game2State();

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


    private boolean isButtonHovered(int buttonWidth, int buttonHeight, int x, int y) {
        return Gdx.input.getX() > x && Gdx.input.getX() < x + buttonWidth &&
            Gdx.graphics.getHeight() - Gdx.input.getY() > y &&
            Gdx.graphics.getHeight() - Gdx.input.getY() < y + buttonHeight;
    }

    public GamePlayScreen2(AngryBirdsGame game) {//constructor for gameplay screen
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
        bodyTexture2 = new Texture(Gdx.files.internal("yellowbird.png"));
        bodyTexture3 = new Texture(Gdx.files.internal("pinkbird.png"));
        bodyTexture4 = new Texture(Gdx.files.internal("slingshot.png"));
        bodyTexture5 = new Texture(Gdx.files.internal("woodblock.png"));
        bodyTexture6 = new Texture(Gdx.files.internal("woodblock.png"));
        bodyTexture7 = new Texture(Gdx.files.internal("pig1.png"));
        Texture bodyTextureBase8 = new Texture(Gdx.files.internal("glassBlock.png"));
        bodyTexture8 = new TextureRegion(bodyTextureBase8);
        bodyTexture9 = new Texture(Gdx.files.internal("CrownPigResized.png"));


        world = new World(new Vector2(0, -9.8f), true);

        // declaring bird class objects for the access of inital position,friction and density
        // characteristics
        r1 = new RedBird();
        y1 = new YellowBird();
        p1 = new PinkBird();
        g1 = new GlassBlock();
        leftblock = new WoodenBlock();
        rightblock = new WoodenBlock();
        bottompig = new PigType1();
        upperpig = new PigType2();

        r1.setHealth(1);
        y1.setHealth(1);
        p1.setHealth(1);
        bottompig.setHealth(1); // is a bottom pig
        g1.setHealth(1);
        leftblock.health = 2;
        rightblock.health = 2;
        upperpig.setHealth(2);

        // need to define an upper pig

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

        //for yellow bird
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.DynamicBody;
        bodyDef2.position.set(y1.getInitialX(), y1.getInitialY());
        body2 = world.createBody(bodyDef2);
        PolygonShape boxShape2 = new PolygonShape();
        boxShape2.setAsBox(0.2f, 0.5f);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = boxShape2;
        fixtureDef2.density = y1.getDensity();
        fixtureDef2.friction = y1.getFriction();
        fixtureDef2.restitution = 0.5f;
        body2.createFixture(fixtureDef2);
        boxShape2.dispose();

        //for pink bird
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


        GameData2.BodytoObjectMap.put(body, r1);
        GameData2.BodytoObjectMap.put(body2, y1);
        GameData2.BodytoObjectMap.put(body3, p1);
        // stored all the bodies in a body to object map


        //a wooden block
        // left block
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

        GameData2.BodytoObjectMap.put(body5, leftblock);


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

        GameData2.BodytoObjectMap.put(body6, rightblock);


        BodyDef bodyDef7 = new BodyDef();
        bodyDef7.type = BodyDef.BodyType.DynamicBody;
        bodyDef7.position.set(16.35f, 2f);
        body7 = world.createBody(bodyDef7);
        PolygonShape boxShape7 = new PolygonShape();
        boxShape7.setAsBox(1f, 0.5f);
        FixtureDef fixtureDef7 = new FixtureDef();
        fixtureDef7.shape = boxShape7;
        fixtureDef7.density = bottompig.getDensity();
        fixtureDef7.friction = bottompig.getFriction();
        fixtureDef7.restitution = 0.5f;
        fixtureDef7.friction = 0.05f;
        body7.createFixture(fixtureDef7);
        boxShape7.dispose();

        GameData2.BodytoObjectMap.put(body7, bottompig); // pig body

        BodyDef bodyDef8 = new BodyDef();
        bodyDef8.type = BodyDef.BodyType.DynamicBody;
        bodyDef8.position.set(16.2f, 6.5f);
        body8 = world.createBody(bodyDef8);
        PolygonShape boxShape8 = new PolygonShape();
        boxShape8.setAsBox(2.5f, 0.1f);
        FixtureDef fixtureDef8 = new FixtureDef();
        fixtureDef8.shape = boxShape8;
        fixtureDef8.density = 1f;
        fixtureDef8.friction = 0.5f;
        fixtureDef8.restitution = 0.5f;
        body8.createFixture(fixtureDef8);
        boxShape8.dispose();

        GameData2.BodytoObjectMap.put(body8, g1);


        BodyDef bodyDef9 = new BodyDef();
        bodyDef9.type = BodyDef.BodyType.DynamicBody;
        bodyDef9.position.set(16.3f, 8f);
        body9 = world.createBody(bodyDef9);
        PolygonShape boxShape9 = new PolygonShape();
        boxShape9.setAsBox(0.5f, 0.5f);
        FixtureDef fixtureDef9 = new FixtureDef();
        fixtureDef9.shape = boxShape9;
        fixtureDef9.density = upperpig.getDensity();
        fixtureDef7.friction = upperpig.getFriction();
        fixtureDef9.restitution = 0.5f;
        fixtureDef9.friction = 0.05f;
        body9.createFixture(fixtureDef9);
        boxShape9.dispose();

        GameData2.BodytoObjectMap.put(body9, upperpig);


        // to handle launching we will set method

        // we will now provide all the information to the contact listener

        contactListener2 = new MyContactListener2();
        pigBodies = new ArrayList<>();
        pigBodies.add(body7);
        pigBodies.add(body9);// added new pig body
        blockBodies = new ArrayList<>();
        GameData2.BodytoObjectMap.put(body5, leftblock);
        GameData2.BodytoObjectMap.put(body6, rightblock);
        GameData2.BodytoObjectMap.put(body8, g1);

        blockBodies.add(body5);// leftblock
        blockBodies.add(body6);// right block
        blockBodies.add(body8);// glass block

        contactListener2.setInformation(BirdList, pigBodies, blockBodies);


        // this will allow the contact listners to make changes in the body health

        debugRenderer = new Box2DDebugRenderer();
    }

    public GamePlayScreen2(AngryBirdsGame game, Game2State game2Statein) // constructor for loading saved games , the game state is
    // is provided using the game1Statein
    {

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
        bodyTexture2 = new Texture(Gdx.files.internal("yellowbird.png"));
        bodyTexture3 = new Texture(Gdx.files.internal("pinkbird.png"));
        bodyTexture4 = new Texture(Gdx.files.internal("slingshot.png"));
        bodyTexture5 = new Texture(Gdx.files.internal("woodblock.png"));
        bodyTexture6 = new Texture(Gdx.files.internal("woodblock.png"));
        bodyTexture7 = new Texture(Gdx.files.internal("pig1.png"));
        Texture bodyTextureBase8 = new Texture(Gdx.files.internal("glassBlock.png"));
        bodyTexture8 = new TextureRegion(bodyTextureBase8);
        bodyTexture9 = new Texture(Gdx.files.internal("CrownPigResized.png"));


        world = new World(new Vector2(0, -9.8f), true);

        // declaring bird class objects for the access of inital position,friction and density
        // characteristics
        r1 = new RedBird();
        y1 = new YellowBird();
        p1 = new PinkBird();
        g1 = new GlassBlock();
        leftblock = new WoodenBlock();
        rightblock = new WoodenBlock();
        bottompig = new PigType1();
        upperpig = new PigType2();

        r1.setHealth(game2Statein.BirdHealths.get(2));
        y1.setHealth(game2Statein.BirdHealths.get(1));
        p1.setHealth(game2Statein.BirdHealths.get(0));
        bottompig.setHealth(game2Statein.PigHealth.get(1)); // is a bottom pig
        g1.setHealth(game2Statein.BlockHealths.get(2));
        leftblock.health = game2Statein.BlockHealths.get(0);
        rightblock.health = game2Statein.BlockHealths.get(1);
        upperpig.setHealth(game2Statein.PigHealth.get(0));

        // need to define an upper pig

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
        bodyDef.position.set(game2Statein.Positions.get(0).x, game2Statein.Positions.get(0).y);
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

        //for yellow bird
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.DynamicBody;
        bodyDef2.position.set(game2Statein.Positions.get(1).x, game2Statein.Positions.get(1).y);
        body2 = world.createBody(bodyDef2);
        PolygonShape boxShape2 = new PolygonShape();
        boxShape2.setAsBox(0.2f, 0.5f);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = boxShape2;
        fixtureDef2.density = y1.getDensity();
        fixtureDef2.friction = y1.getFriction();
        fixtureDef2.restitution = 0.5f;
        body2.createFixture(fixtureDef2);
        boxShape2.dispose();


        BodyDef bodyDef3 = new BodyDef();
        bodyDef3.type = BodyDef.BodyType.DynamicBody;
        bodyDef3.position.set(game2Statein.Positions.get(2).x, game2Statein.Positions.get(2).y);
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


        GameData2.BodytoObjectMap.put(body, r1);
        GameData2.BodytoObjectMap.put(body2, y1);
        GameData2.BodytoObjectMap.put(body3, p1);
        // stored all the bodies in a body to object map


        //a wooden block
        // left block
        BodyDef bodyDef5 = new BodyDef();
        bodyDef5.type = BodyDef.BodyType.DynamicBody;
        bodyDef5.position.set(game2Statein.Positions.get(5).x, game2Statein.Positions.get(5).y);
        body5 = world.createBody(bodyDef5);
        PolygonShape boxShape5 = new PolygonShape();
        boxShape5.setAsBox(0.2f, 1.6f);
        FixtureDef fixtureDef5 = new FixtureDef();
        fixtureDef5.shape = boxShape5;
        fixtureDef5.restitution = 0f;

        body5.createFixture(fixtureDef5);
        boxShape5.dispose();


        GameData2.BodytoObjectMap.put(body5, leftblock);


        BodyDef bodyDef6 = new BodyDef();
        bodyDef6.type = BodyDef.BodyType.DynamicBody;
        bodyDef6.position.set(game2Statein.Positions.get(6).x, game2Statein.Positions.get(6).y);
        body6 = world.createBody(bodyDef6);
        PolygonShape boxShape6 = new PolygonShape();
        boxShape6.setAsBox(0.2f, 1.6f);
        FixtureDef fixtureDef6 = new FixtureDef();
        fixtureDef6.shape = boxShape6;
        fixtureDef6.restitution = 0f;

        body6.createFixture(fixtureDef6);
        boxShape6.dispose();

        GameData2.BodytoObjectMap.put(body6, rightblock);


        BodyDef bodyDef7 = new BodyDef();
        bodyDef7.type = BodyDef.BodyType.DynamicBody;
        bodyDef7.position.set(game2Statein.Positions.get(4).x, game2Statein.Positions.get(4).y);
        body7 = world.createBody(bodyDef7);
        PolygonShape boxShape7 = new PolygonShape();
        boxShape7.setAsBox(1f, 0.5f);
        FixtureDef fixtureDef7 = new FixtureDef();
        fixtureDef7.shape = boxShape7;
        fixtureDef7.density = bottompig.getDensity();
        fixtureDef7.friction = bottompig.getFriction();
        fixtureDef7.restitution = 0.5f;
        fixtureDef7.friction = 0.05f;
        body7.createFixture(fixtureDef7);
        boxShape7.dispose();

        GameData2.BodytoObjectMap.put(body7, bottompig); // pig body

        BodyDef bodyDef8 = new BodyDef();
        bodyDef8.type = BodyDef.BodyType.DynamicBody;
        bodyDef8.position.set(game2Statein.Positions.get(7).x, game2Statein.Positions.get(7).y);
        body8 = world.createBody(bodyDef8);
        PolygonShape boxShape8 = new PolygonShape();
        boxShape8.setAsBox(2.5f, 0.1f);
        FixtureDef fixtureDef8 = new FixtureDef();
        fixtureDef8.shape = boxShape8;
        fixtureDef8.density = 1f;
        fixtureDef8.friction = 0.5f;
        fixtureDef8.restitution = 0.5f;
        body8.createFixture(fixtureDef8);
        boxShape8.dispose();

        GameData2.BodytoObjectMap.put(body8, g1);


        BodyDef bodyDef9 = new BodyDef();
        bodyDef9.type = BodyDef.BodyType.DynamicBody;
        bodyDef9.position.set(game2Statein.Positions.get(3).x, game2Statein.Positions.get(3).y);
        body9 = world.createBody(bodyDef9);
        PolygonShape boxShape9 = new PolygonShape();
        boxShape9.setAsBox(1f, 0.5f);
        FixtureDef fixtureDef9 = new FixtureDef();
        fixtureDef9.shape = boxShape9;
        fixtureDef9.density = upperpig.getDensity();
        fixtureDef7.friction = upperpig.getFriction();
        fixtureDef9.restitution = 0.5f;
        fixtureDef9.friction = 0.05f;
        body9.createFixture(fixtureDef9);
        boxShape9.dispose();

        GameData2.BodytoObjectMap.put(body9, upperpig);

        currentindex = game2Statein.currentindex;
        // to handle launching we will set method

        // we will now provide all the information to the contact listener

        contactListener2 = new MyContactListener2();
        pigBodies = new ArrayList<>();
        pigBodies.add(body7);
        pigBodies.add(body9);// added new pig body
        blockBodies = new ArrayList<>();
        GameData2.BodytoObjectMap.put(body5, leftblock);
        GameData2.BodytoObjectMap.put(body6, rightblock);
        GameData2.BodytoObjectMap.put(body8, g1);

        blockBodies.add(body5);// leftblock
        blockBodies.add(body6);// right block
        blockBodies.add(body8);// glass block

        contactListener2.setInformation(BirdList, pigBodies, blockBodies);


        leftblockexists = game2Statein.leftblockexists;
        rightblockexists = game2Statein.rightblockexists;
        glassblockexists = game2Statein.glassblockexists;

        pigexists = game2State.lowerpigexists;
        pigexists2 = game2State.upperpigexists;


        // this will allow the contact listners to make changes in the body health

        debugRenderer = new Box2DDebugRenderer();
    }
    // this will be changed after the main block is complete

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
        world.setContactListener(contactListener2);
        // setting up the valid bird and current bird
        currentbird = BirdList.get(currentindex);
        validbird = true; // here we can write any code we want to execute when we are setting the bird
        if (!placed) {


            elapsedTime += delta;
            if (elapsedTime >= 4.5) { // Check if 4.5 seconds have passed
                currentbird.setTransform(4.66f, 4.89f, 0);
                placed = true; // Ensure this logic only runs once
            }

        }
        if (placed) {
            elapsedTime = 0;
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

        if (leftblockexists) {
            Vector2 bodyPosition5 = body5.getPosition();
            batch.draw(bodyTexture5, bodyPosition5.x * 100f - (bodyTexture5.getWidth() / 0.5f), bodyPosition5.y * 100f - (bodyTexture5.getHeight() / 0.6f), bodyTexture5.getWidth() / 0.5f, bodyTexture5.getHeight() / 0.5f);
        }

        if (rightblockexists) {
            Vector2 bodyPosition6 = body6.getPosition();
            batch.draw(bodyTexture6, bodyPosition6.x * 100f - (bodyTexture6.getWidth() / 0.5f), bodyPosition6.y * 100f - (bodyTexture6.getHeight() / 0.6f), bodyTexture6.getWidth() / 0.5f, bodyTexture6.getHeight() / 0.5f);


        }

        if (glassblockexists) {
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

        if (pigexists2) {
            Vector2 bodyPosition9 = body9.getPosition();
            batch.draw(bodyTexture9, bodyPosition9.x * 100f - (bodyTexture9.getWidth() / 2), bodyPosition9.y * 100f - (bodyTexture9.getHeight() / 2), bodyTexture9.getWidth() / 2.5f, bodyTexture9.getHeight() / 2.5f);

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
                            lastBirdLaunched = true;
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
        BirdHealths.add(y1.health);
        BirdHealths.add(r1.health);


        ArrayList<Integer> PigHealths = new ArrayList<>();
        PigHealths.add(upperpig.health);
        PigHealths.add(bottompig.health);

        ArrayList<Pair> Positions = new ArrayList<>();
        Positions.add(new Pair(body.getPosition().x, body.getPosition().y));
        Positions.add(new Pair(body2.getPosition().x, body2.getPosition().y));
        Positions.add(new Pair(body3.getPosition().x, body3.getPosition().y));
        Positions.add(new Pair(body7.getPosition().x, body7.getPosition().y));
        Positions.add(new Pair(body9.getPosition().x, body9.getPosition().y));
        Positions.add(new Pair(body5.getPosition().x, body5.getPosition().y));
        Positions.add(new Pair(body6.getPosition().x, body6.getPosition().y));
        Positions.add(new Pair(body8.getPosition().x, body8.getPosition().y));


        WoodenBlock leftblock = (WoodenBlock) GameData2.BodytoObjectMap.get(body5);
        WoodenBlock rightblock = (WoodenBlock) GameData2.BodytoObjectMap.get(body6);
        GlassBlock glassblock = (GlassBlock) GameData2.BodytoObjectMap.get(body8);

        ArrayList<Integer> BlockHealths = new ArrayList<>();
        BlockHealths.add(leftblock.health);
        BlockHealths.add(rightblock.health);
        BlockHealths.add(glassblock.health);


        game2State.updateGameState(BirdHealths, PigHealths, Positions, BlockHealths, currentindex);
        game2State.leftblockexists = leftblockexists;
        game2State.rightblockexists = rightblockexists;
        game2State.glassblockexists = glassblockexists;

        game2State.upperpigexists = pigexists2;
        game2State.lowerpigexists = pigexists;


        PigType1 pigtocheck;
        pigtocheck = (PigType1) GameData2.BodytoObjectMap.get(pigBodies.get(0));
        if (pigtocheck.health <= 0 && pigexists) {
            System.out.println("Deactivating lower pig");
            body7.setActive(false);
            pigexists = false;
        }

        PigType2 pigtocheck2 = (PigType2) GameData2.BodytoObjectMap.get(pigBodies.get(1));
        if (pigtocheck2.health <= 0 && pigexists2) {
            System.out.println("Collision detected");
            body9.setActive(false);
            pigexists2 = false;

        }

        if (pigexists2 == false && pigexists == false) {
            gamewon = true;

        }

        WoodenBlock leftbloc = (WoodenBlock) GameData2.BodytoObjectMap.get(body5);
        if (leftbloc.health <= 0) {
            leftblockexists = false;
            glassblockexists = false;
            world.step(1 / 60f, 6, 2);  // Simulate 1/60th of a second with a velocity and position iteration count
            body9.setActive(false);
            pigexists2 = false;
            body9.setActive(false);
            body5.setActive(false);
        }


        WoodenBlock rightbloc = (WoodenBlock) GameData2.BodytoObjectMap.get(body6);
        if (rightbloc.health <= 0) {
            rightblockexists = false;
            glassblockexists = false;
            world.step(1 / 60f, 6, 2);  // Simulate 1/60th of a second with a velocity and position iteration count
            pigexists2 = false;
            body9.setActive(false);
            body9.setActive(false);
            body6.setActive(false);

        }


        GlassBlock glassBlock = (GlassBlock) GameData2.BodytoObjectMap.get(body8);
        if (glassBlock.health <= 0) {
            glassblockexists = false;
            pigexists2=false;
            body9.setActive(false);
            body8.setActive(false);
        }

        if (switchScreen) {
            game.setScreen(new PauseScreen2(game, game2State));  // Perform screen switch here

        }

        if (gamewon) {
            game.setScreen(new LevelUpScreen3(game));
        }


        if (lastBirdLaunched == true && body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0 && currentindex == 0 && gamewon == false) {
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
        world.destroyBody(body8);
        if (pigexists2) {
            world.destroyBody(body9);
        }

        world.destroyBody(RightBoundary);

        if (pigexists) {
            world.destroyBody(body7);
        }
        world.dispose();
    }
}
