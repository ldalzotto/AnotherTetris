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

    private final float HEIGHT_AREA_REWARD = 0.6f;

    private static ITetrisRewardDetection instance;
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
                        shapeRenderer.line(rewardLine.getBeginCenterPoint(), rewardLine.getEndCenterPoint());
                        shapeRenderer.end();

                        return null;
                    }
                }, Gdx.graphics.getDeltaTime());
            }

            if(tetrisBlocksOnCurrentLine.size() >= TetrisGridContants.NB_BLOCK_WIDTH-2){
                List<CuttedRewardBlock> cuttedRewardBlocks =
                        this.iTetRewardLineVolumeDetector.getCuttedBlockDetails(rewardLine, tetrisBlocksOnCurrentLine);

                float theoricalArea =
                        Math.abs(rewardLine.getBeginUpperPoint().y - rewardLine.getBeginLowerPoint().y)
                          * Math.abs(rewardLine.getEndUpperPoint().x - rewardLine.getBeginUpperPoint().x);

                float averationAreaRatio = 0;
                for (CuttedRewardBlock cuttedRewardBlock :
                        cuttedRewardBlocks) {
                    averationAreaRatio += cuttedRewardBlock.getContainedArea();
                }
                averationAreaRatio = averationAreaRatio / theoricalArea;
                if(averationAreaRatio >= HEIGHT_AREA_REWARD){
                    for (TetrisBlock tetrisBlockToErase :
                            tetrisBlocksOnCurrentLine) {
                       this.iElementEraser.addBlockToErase(tetrisBlockToErase);
                    }
                }
            }

        }
    }

    @Override
    public void addRewardLine(Vector2 beginPosition, Vector2 endPosition) {
        RewardLine rewardLine = new RewardLine();
        rewardLine.setBeginCenterPoint(beginPosition);
        rewardLine.setEndCenterPoint(endPosition);

        //set upper and bottom
        rewardLine.setBeginUpperPoint(new Vector2(beginPosition).add(0, TetrisGridContants.GRID_BLOCK_SIZE * HEIGHT_AREA_REWARD/2));
        rewardLine.setEndUpperPoint(new Vector2(endPosition).add(0, TetrisGridContants.GRID_BLOCK_SIZE * HEIGHT_AREA_REWARD/2));

        rewardLine.setBeginLowerPoint(new Vector2(beginPosition).add(0, TetrisGridContants.GRID_BLOCK_SIZE * -HEIGHT_AREA_REWARD/2));
        rewardLine.setEndLowerPoint(new Vector2(endPosition).add(0, TetrisGridContants.GRID_BLOCK_SIZE * -HEIGHT_AREA_REWARD/2));


        this.rewardLines.add(rewardLine);
    }
}
