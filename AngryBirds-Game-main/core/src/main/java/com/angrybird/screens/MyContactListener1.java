package com.angrybird.screens;

import com.angrybird.BlueBird;
import com.angrybird.PigType1;
import com.angrybird.PinkBird;
import com.angrybird.RedBird;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class MyContactListener1 implements ContactListener {
    // need to set the bird bodies
    // also need to set the pigs
    // also need to set the block bodies

    ArrayList<Body> BirdBodies;
    // objects : redbird,bluebird,pinkbird
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
        // This method is called when two bodies begin to collide
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        if ((BirdBodies.contains(bodyA) && PigBodies.contains(bodyB))) {
            PigType1 collisionpig = (PigType1) GameData.BodytoObjectMap.get(bodyB);
            collisionpig.setHealth(0);

            int indexfound = 0;

            for (int i = 0; i < BirdBodies.size(); i++) {
                if (BirdBodies.get(i).equals(bodyA)) {
                    indexfound = i;
                    break;
                }
            }

            if (indexfound == 0) {
                RedBird r1 = (RedBird) GameData.BodytoObjectMap.get(bodyA);
                r1.setHealth(0);
                return;
            } else if (indexfound == 1) {
                BlueBird b1 = (BlueBird) GameData.BodytoObjectMap.get(bodyA);
                b1.setHealth(0);
                return;
            } else if (indexfound == 2) {
                PinkBird p1 = (PinkBird) GameData.BodytoObjectMap.get(bodyA);
                p1.setHealth(0);
                return;
            }
        } else if (BirdBodies.contains(bodyB) && PigBodies.contains(bodyA)) {
            PigType1 collisionpig = (PigType1) GameData.BodytoObjectMap.get(bodyA);
            collisionpig.setHealth(0);
            int indexfound = 0;
            for (int i = 0; i < BirdBodies.size(); i++) {
                if (BirdBodies.get(i).equals(bodyB)) {
                    indexfound = i;
                    break;
                }
            }
            if (indexfound == 0) {
                RedBird r1 = (RedBird) GameData.BodytoObjectMap.get(bodyB);
                r1.setHealth(0);
                return;
            } else if (indexfound == 1) {
                BlueBird b1 = (BlueBird) GameData.BodytoObjectMap.get(bodyB);
                b1.setHealth(0);
                return;
            } else if (indexfound == 2) {
                PinkBird p1 = (PinkBird) GameData.BodytoObjectMap.get(bodyB);
                p1.setHealth(0);
                return;
            }

        }
    }

    @Override
    public void endContact(Contact contact) {
        // This method is called when two bodies stop colliding
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
        // You can manipulate the collision properties here
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // This method is called after the physics engine processes the collision
        // You can get impulse data or handle post-collision actions
    }
}
