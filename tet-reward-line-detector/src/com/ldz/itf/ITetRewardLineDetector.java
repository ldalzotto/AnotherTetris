package com.ldz.itf;

import java.util.List;

public interface ITetRewardLineDetector {

    List<TetrisBlock> getAllTetrisBlocksIntersecting(RewardLine rewardLine);

}
