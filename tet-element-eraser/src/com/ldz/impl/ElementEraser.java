package com.ldz.impl;

import com.ldz.itf.IElementEraser;
import com.ldz.itf.ITetrisGrid;
import com.ldz.itf.ITetrisPhysicsWorld;
import com.ldz.itf.TetrisElement;

import java.util.ArrayList;
import java.util.List;

public class ElementEraser implements IElementEraser {

    private List<TetrisElement> elementsToErase = new ArrayList<>();

    private static IElementEraser instance;
    public static IElementEraser getInstance(){
        if(instance==null){
            instance = new ElementEraser();
        }
        return instance;
    }
    private ElementEraser(){
        this.iTetrisGrid = TetrisGrid.getInstance();
        this.iTetrisPhysicsWorld = TetrisWorld.getInstance();
    }

    private ITetrisGrid iTetrisGrid;
    private ITetrisPhysicsWorld iTetrisPhysicsWorld;

    @Override
    public void addElementToErase(TetrisElement tetrisElement) {
        this.elementsToErase.add(tetrisElement);
    }

    @Override
    public void eraseAllElements() {
        for (TetrisElement tetrisElement : this.elementsToErase){
            this.eraseElement(tetrisElement);
        }
        this.elementsToErase.clear();
    }

    private void eraseElement(TetrisElement tetrisElement) {
        this.iTetrisPhysicsWorld.removeBody(tetrisElement.getBody());
        this.iTetrisGrid.deleteElementFromGrid(tetrisElement);
    }
}
