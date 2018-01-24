package com.ldz.impl;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.ldz.itf.*;

import java.util.ArrayList;
import java.util.List;

public class TetRewardLineVolumeDetector implements ITetRewardLineVolumeDetector {

    private static ITetRewardLineVolumeDetector instance;
    public static ITetRewardLineVolumeDetector getInstance(){
        if(instance==null){
            instance=new TetRewardLineVolumeDetector();
        }
        return instance;
    }
    private TetRewardLineVolumeDetector(){
        this.iTetElementVolumeCalculation = TetElementVolumeCalculation.getInstance();
    }

    private ITetElementVolumeCalculation iTetElementVolumeCalculation;

    @Override
    public float getLineFillRate(RewardLine rewardLine, List<TetrisBlock> tetrisBlocks) {

        // ((PolygonShape)tetrisBlocks.get(0).getFixture().getShape()).
        return 0;
    }

    @Override
    public List<CuttedRewardBlock> getCuttedBlockDetails(RewardLine rewardLine, List<TetrisBlock> tetrisBlocks) {

        List<CuttedRewardBlock> result = new ArrayList<>();
        for (TetrisBlock tetrisBlock :
                tetrisBlocks) {
            CuttedRewardBlock cuttedRewardBlock = new CuttedRewardBlock();
            cuttedRewardBlock.setUpperCuttedBlock(this.splitBlockAndCalculateValueAndOrder(rewardLine.getUpperLineAsPolyLine(), tetrisBlock));
            cuttedRewardBlock.setLowerCuttedBlock(this.splitBlockAndCalculateValueAndOrder(rewardLine.getLowerLineAsPolyLine(), tetrisBlock));
            cuttedRewardBlock.setContainedArea(this.computeAreaRatio(cuttedRewardBlock));
            result.add(cuttedRewardBlock);
        }
        
        return result;
    }

    private CuttedBlock splitBlockAndCalculateValueAndOrder(Polyline polyline, TetrisBlock tetrisBlock){
        Polygon polygon = FixtureToPolygonUtil.fixtureToPolygon(tetrisBlock.getFixture());
        List<Polygon> cuttedPolygons = this.iTetElementVolumeCalculation.splitPolygonFromLine(polygon, polyline);

        CuttedBlock cuttedBlock = new CuttedBlock();
        Vector2 centerLinePoint = new Vector2(polyline.getTransformedVertices()[0], polyline.getTransformedVertices()[1]);
        //get upper and lower polygon
        for (Polygon polygon1 :
                cuttedPolygons ) {
            int positionResult = this.isUpperOrLowerFromCenter(polygon1, centerLinePoint);
            if(positionResult == 1){
                cuttedBlock.setLowerArea(polygon1.area());
                cuttedBlock.setLowerPolygon(polygon1);
            } else if(positionResult==0){
                cuttedBlock.setUpperArea(polygon1.area());
                cuttedBlock.setUpperPolygon(polygon1);
            }
        }

        return cuttedBlock;
    }

    /**
     * return 0 if upper, 1 if lower, -1 if unknown
     * @param polygon
     * @param centerPoint
     * @return
     */
    private int isUpperOrLowerFromCenter(Polygon polygon, Vector2 centerPoint){

        float[] vertices = polygon.getTransformedVertices();
        for (int i = 1; i<vertices.length; i+=2){
            if(vertices[i] > centerPoint.y){
                return 0;
            } else if(vertices[i] < centerPoint.y){
                return 1;
            }
        }

        return -1;

    }

    private float computeAreaRatio(CuttedRewardBlock cuttedRewardBlock){
        float containedArea = cuttedRewardBlock.getLowerCuttedBlock().getUpperArea() -
                cuttedRewardBlock.getUpperCuttedBlock().getUpperArea();

        return containedArea;
    }
}
