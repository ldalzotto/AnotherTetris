package com.ldz.impl;

import com.ldz.itf.ITetElementVolumeCalculation;
import com.ldz.itf.ITetRewardLineVolumeDetector;
import com.ldz.itf.RewardLine;
import com.ldz.itf.TetrisBlock;

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
        return 0;
    }
}
