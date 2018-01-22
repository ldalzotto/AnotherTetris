package com.ldz.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.ldz.itf.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TetrisRewardDetection implements ITetrisRewardDetection {

    private static ITetrisRewardDetection instance;
    private Boolean generated = false;
    private List<RewardLine> rewardLines = new ArrayList<>();

    private TetrisRewardDetection() {
        this.iTetRewardLineVolumeDetector = TetRewardLineVolumeDetector.getInstance();
        this.iTetRewardLineDetector = TetRewardLineDetector.getInstance();
        this.iShapeDebugger = ShapeDebugger.getInstance();
        this.iElementEraser = ElementEraser.getInstance();
    }

    public static ITetrisRewardDetection getInstance() {
        if (instance == null) {
            instance = new TetrisRewardDetection();
        }
        return instance;
    }

    private ITetRewardLineVolumeDetector iTetRewardLineVolumeDetector;
    private ITetRewardLineDetector iTetRewardLineDetector;
    private IShapeDebugger iShapeDebugger;
    private IElementEraser iElementEraser;

    @Override
    public void checkRewardAndSpawn() {
        for (RewardLine rewardLine :
                this.rewardLines) {
            List<TetrisBlock> tetrisBlocksOnCurrentLine =
                    iTetRewardLineDetector.getAllTetrisBlocksIntersecting(rewardLine);

            if(this.iShapeDebugger.isEnabled()){
                this.iShapeDebugger.pushDrawEvent(new Function<ShapeRenderer, Void>() {
                    @Override
                    public Void apply(ShapeRenderer shapeRenderer) {
                        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                        shapeRenderer.setColor(Color.CHARTREUSE);
                        shapeRenderer.line(rewardLine.getBeginPoint(), rewardLine.getEndPoint());
                        shapeRenderer.end();
                        return null;
                    }
                }, Gdx.graphics.getDeltaTime());
            }

            if(tetrisBlocksOnCurrentLine.size() >= TetrisGridContants.NB_BLOCK_WIDTH-2){
                for (TetrisBlock tetrisBlockToErase :
                        tetrisBlocksOnCurrentLine) {
                    this.iElementEraser.addBlockToErase(tetrisBlockToErase);
                }
            }

        }
        generated = true;
    }

    @Override
    public void addRewardLine(Vector2 beginPosition, Vector2 endPosition) {
        RewardLine rewardLine = new RewardLine();
        rewardLine.setBeginPoint(beginPosition);
        rewardLine.setEndPoint(endPosition);
        this.rewardLines.add(rewardLine);
    }
}
