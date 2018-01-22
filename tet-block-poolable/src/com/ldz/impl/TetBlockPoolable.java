package com.ldz.impl;

import com.badlogic.gdx.utils.Pool;
import com.ldz.itf.ITetBlockPoolable;
import com.ldz.itf.TetrisBlock;
import com.ldz.itf.TetrisElement;

public class TetBlockPoolable implements ITetBlockPoolable {


    private static ITetBlockPoolable instance;
    public static ITetBlockPoolable getInstance(){
        if(instance==null){
            instance=new TetBlockPoolable();
        }
        return instance;
    }
    private TetBlockPoolable(){};

    private final Pool<TetrisBlock> tetrisBlockPool = new Pool<TetrisBlock>() {
        @Override
        protected TetrisBlock newObject() {
            return new TetrisBlock();
        }
    };

    @Override
    public TetrisBlock getBlockInstance() {
        return this.tetrisBlockPool.obtain();
    }

    @Override
    public void deleteBlock(TetrisBlock tetrisElement) {
        this.tetrisBlockPool.free(tetrisElement);
    }
}
