package com.ldz.impl;

import com.ldz.itf.IElementSpawn;
import com.ldz.itf.ITetrisRewardDetection;

public class TetrisRewardDetection implements ITetrisRewardDetection {

    private static ITetrisRewardDetection instance;
    private Boolean generated = false;

    private TetrisRewardDetection() {
    }

    public static ITetrisRewardDetection getInstance() {
        if (instance == null) {
            instance = new TetrisRewardDetection();
        }
        return instance;
    }

    @Override
    public void checkRewardAndSpawn() {
        IElementSpawn iElementSpawn = ElementSpawn.getInstance();
        iElementSpawn.spawnElement();
        generated = true;
    }
}
