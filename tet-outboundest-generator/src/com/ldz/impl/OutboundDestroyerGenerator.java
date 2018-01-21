package com.ldz.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ldz.itf.*;

import java.util.Arrays;

public class OutboundDestroyerGenerator implements IOutboundDestroyerGenerator {

    private final Integer BOUND_WIDTH_SCREEN_PERCENT = 15;
    private ITetrisPhysicsContactGeneration iTetrisPhysicsContactGeneration;

    private static IOutboundDestroyerGenerator instance;
    public static IOutboundDestroyerGenerator getInstance(){
        if(instance == null){
            instance = new OutboundDestroyerGenerator();
        }
        return instance;
    }
    private OutboundDestroyerGenerator(){
        this.iTetrisPhysicsContactGeneration = TetrisPhysicsContactGeneration.getInstance();
        this.iPhysicsGeneration = PhysicsGeneration.getInstance();
    }

    private IPhysicsGeneration iPhysicsGeneration;

    @Override
    public OutboundDestroyer createOutboundDestroyer() {

        OutboundDestroyer outboundDestroyer = new OutboundDestroyer();

        SingleDestroyer leftDestroyer = new SingleDestroyer();
        SingleDestroyer rightDestroyer = new SingleDestroyer();
        SingleDestroyer downDestroyer = new SingleDestroyer();

        //retrieve screen pos
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        //left
        Body leftBody = this.iPhysicsGeneration.createRectangleBody(new Vector2(-screenWidth/2, 0), screenWidth*BOUND_WIDTH_SCREEN_PERCENT/100, screenHeight);
        leftBody.setUserData(outboundDestroyer);
        leftDestroyer.setBody(leftBody);

        //right
        Body rightBody = this.iPhysicsGeneration.createRectangleBody(new Vector2(screenWidth/2, 0), screenWidth*BOUND_WIDTH_SCREEN_PERCENT/100, screenHeight);
        rightBody.setUserData(outboundDestroyer);
        rightDestroyer.setBody(rightBody);

        //down
        Body downBody = this.iPhysicsGeneration.createRectangleBody(new Vector2(0, -screenHeight/2), screenHeight , screenHeight*BOUND_WIDTH_SCREEN_PERCENT/100);
        downBody.setUserData(outboundDestroyer);
        downDestroyer.setBody(downBody);

        outboundDestroyer.setSingleDestroyers(Arrays.asList(leftDestroyer, rightDestroyer, downDestroyer));

        this.iTetrisPhysicsContactGeneration.addContactListener(OutboundContactListener.getInstance());

        return outboundDestroyer;
    }
}
