package com.ldz.itf;

import java.util.List;

public interface ITetRewardLineVolumeDetector {

    float getLineFillRate(RewardLine rewardLine, List<TetrisBlock> tetrisBlocks);

}
