package com.ldz.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.ldz.itf.ITetRewardLineDetector;
import com.ldz.itf.ITetrisPhysicsWorld;
import com.ldz.itf.RewardLine;
import com.ldz.itf.TetrisBlock;

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
        this.iTetrisPhysicsWorld.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 vector2, Vector2 vector21, float v) {
                return 0;
            }
        }, rewardLine.getBeginPoint(), rewardLine.getEndPoint());
        return null;
    }

}
