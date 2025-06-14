package com.angrybird;

import com.angrybird.PigType1;
import com.angrybird.RedBird;
import com.angrybird.screens.GameData;
import com.angrybird.screens.GamePlayScreen;
import com.angrybird.screens.HelperFunctions;
import com.angrybird.screens.MyContactListener1;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.List;

class AngryBirdsGameTest extends HelperFunctions {

    private World world;
    private Body birdBody, pigBody;
    private MyContactListener1 contactListener;
    private PigType1 pig;
    private RedBird bird;

    @BeforeEach
    void setup() {
//        System.loadLibrary("gdx-box2d64");
        System.setProperty("java.library.path", "path/to/native/libraries");

        world = new World(new Vector2(0, -9.8f), true);
        contactListener = new MyContactListener1();
        world.setContactListener(contactListener);

        bird = new RedBird();
        pig = new PigType1();
        bird.setHealth(1);
        pig.setHealth(1);

        BodyDef birdDef = new BodyDef();
        birdDef.type = BodyDef.BodyType.DynamicBody;
        birdDef.position.set(2, 2);
        birdBody = world.createBody(birdDef);
        GameData.BodytoObjectMap.put(birdBody, bird);

        BodyDef pigDef = new BodyDef();
        pigDef.type = BodyDef.BodyType.StaticBody;
        pigDef.position.set(2, 0);
        pigBody = world.createBody(pigDef);
        GameData.BodytoObjectMap.put(pigBody, pig);

        contactListener.setInformation(
            new ArrayList<>(List.of(birdBody)),
            new ArrayList<>(List.of(pigBody)),
            new ArrayList<>()
        );
    }


    @Test
    void health_reduces_when_collides() {
        // Simulate collision
        world.step(1 / 60f, 6, 2);
        contactListener.beginContact(createMockContact(birdBody, pigBody));

        assertEquals(0, bird.getHealth(), "Bird's health should reduce after collision.");
        assertEquals(0, pig.getHealth(), "Pig's health should reduce after collision.");
    }

    @Test
    void testBirdLaunch() {
        birdBody.setLinearVelocity(5, 10);
        Vector2 velocity = birdBody.getLinearVelocity();

        assertEquals(5, velocity.x);
        assertEquals(10, velocity.y);
    }

    @Test
    void trajectory_calc_bird() {
        int initialX = 0;
        int initialY = 0;
        float velocityX = 10;
        float velocityY = 15;
        float t = 1; //1 second

        float calculatedX = HelperFunctions.CalcXcoord(initialX, t, (int) velocityX, true);
        float calculatedY = HelperFunctions.CalcYcoord(initialY, t, (int) velocityY, true);

        assertEquals(10, calculatedX, "Calculated X-coordinate should match expected value.");
        assertTrue(calculatedY <15, "Calculated Y-coordinate should decrease due to gravity.");
    }

    @Test
    void pig_health_after_multiple_collision() {
        contactListener.beginContact(createMockContact(birdBody, pigBody));
        contactListener.beginContact(createMockContact(birdBody, pigBody));

        assertEquals(0, pig.getHealth(), "Pig's health will be 0 after multiple hits.");
    }

    private Contact createMockContact(Body bodyA, Body bodyB) {
        Contact contact = mock(Contact.class);
        Fixture fixtureA = mock(Fixture.class);
        Fixture fixtureB = mock(Fixture.class);
        when(contact.getFixtureA()).thenReturn(fixtureA);
        when(contact.getFixtureB()).thenReturn(fixtureB);
        when(fixtureA.getBody()).thenReturn(bodyA);
        when(fixtureB.getBody()).thenReturn(bodyB);

        return contact;
    }
}
