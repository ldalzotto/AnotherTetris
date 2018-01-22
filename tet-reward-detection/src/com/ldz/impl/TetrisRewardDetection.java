package com.ldz.impl;

import com.ldz.itf.IElementSpawn;
import com.ldz.itf.ITetRewardLineDetector;
import com.ldz.itf.ITetRewardLineVolumeDetector;
import com.ldz.itf.ITetrisRewardDetection;

public class TetrisRewardDetection implements ITetrisRewardDetection {

    private static ITetrisRewardDetection instance;
    private Boolean generated = false;

    private TetrisRewardDetection() {
        this.iTetRewardLineVolumeDetector = TetRewardLineVolumeDetector.getInstance();
        this.iTetRewardLineDetector = TetRewardLineDetector.getInstance();
    }

    public static ITetrisRewardDetection getInstance() {
        if (instance == null) {
            instance = new TetrisRewardDetection();
        }
        return instance;
    }

    private ITetRewardLineVolumeDetector iTetRewardLineVolumeDetector;
    private ITetRewardLineDetector iTetRewardLineDetector;

    @Override
    public void checkRewardAndSpawn() {
        IElementSpawn iElementSpawn = ElementSpawn.getInstance();
        iElementSpawn.spawnElement();
        generated = true;
    }
}
