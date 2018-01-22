package com.ldz.impl;

import com.badlogic.gdx.utils.Pool;
import com.ldz.itf.ITetElementPoolable;
import com.ldz.itf.TetrisElement;

public class TetElementPoolable implements ITetElementPoolable {

    private static ITetElementPoolable instance;
    public static ITetElementPoolable getInstance(){
        if(instance==null){
            instance=new TetElementPoolable();
        }
        return instance;
    }
    private TetElementPoolable(){};

    private final Pool<TetrisElement> tetrisElementPool = new Pool<TetrisElement>() {
        @Override
        protected TetrisElement newObject() {
            return new TetrisElement();
        }
    };

    @Override
    public TetrisElement getElementInstance() {
        return tetrisElementPool.obtain();
    }

    @Override
    public void deleteElementFromGrid(TetrisElement tetrisElement) {
        this.tetrisElementPool.free(tetrisElement);
    }
}
