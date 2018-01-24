package com.ldz.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.ldz.itf.*;

import java.util.ArrayList;
import java.util.List;

public class TetRewardLineDetector implements ITetRewardLineDetector {

    private static ITetRewardLineDetector instance;
    public static ITetRewardLineDetector getInstance(){
        if(instance==null){
            instance=new TetRewardLineDetector();
        }
        return instance;
    }
    private TetRewardLineDetector(){
        this.iTetrisPhysicsWorld = TetrisWorld.getInstance();
    }

    private ITetrisPhysicsWorld iTetrisPhysicsWorld;

    @Override
    public List<TetrisBlock> getAllTetrisBlocksIntersecting(RewardLine rewardLine) {
        List<TetrisBlock> tetrisBlocks = new ArrayList<>();
        this.iTetrisPhysicsWorld.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 vector2, Vector2 vector21, float v) {
                if(fixture.getUserData() instanceof TetrisBlock){
                    tetrisBlocks.add((TetrisBlock) fixture.getUserData());
                }
                return -1;
            }
        }, rewardLine.getBeginCenterPoint(), rewardLine.getEndCenterPoint());
        return tetrisBlocks;
    }

}
