package com.ldz.impl;

import com.ldz.itf.*;

import java.util.ArrayList;
import java.util.List;

public class ElementEraser implements IElementEraser {

    private List<TetrisElement> elementsToErase = new ArrayList<>();
    private List<TetrisBlock> blocksToErase = new ArrayList<>();


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
        this.iTetBlockPoolable = TetBlockPoolable.getInstance();
    }

    private ITetrisGrid iTetrisGrid;
    private ITetrisPhysicsWorld iTetrisPhysicsWorld;
    private ITetBlockPoolable iTetBlockPoolable;

    @Override
    public void addElementToErase(TetrisElement tetrisElement) {
        this.elementsToErase.add(tetrisElement);
    }

    @Override
    public void eraseAllElements() {
        for (TetrisBlock tetrisBlock :
                this.blocksToErase) {
            this.eraseBlock(tetrisBlock);
        }
        
        for (TetrisElement tetrisElement : this.elementsToErase){
            this.eraseElement(tetrisElement);
        }
        this.elementsToErase.clear();
        this.blocksToErase.clear();
    }

    @Override
    public void addBlockToErase(TetrisBlock tetrisBlock) {
        this.blocksToErase.add(tetrisBlock);
    }

    private void eraseBlock(TetrisBlock tetrisBlock){
        TetrisElement tetrisElement = tetrisBlock.getTetrisElementReference();
        tetrisElement.getBody().destroyFixture(tetrisBlock.getFixture());

        if(tetrisElement.getBody().getFixtureList().size == 0){
            this.elementsToErase.add(tetrisElement);
        }
        tetrisElement.getTetrisBlocks().remove(tetrisBlock);
        this.iTetBlockPoolable.deleteBlock(tetrisBlock);
    }

    private void eraseElement(TetrisElement tetrisElement) {
        this.iTetrisPhysicsWorld.removeBody(tetrisElement.getBody());
        this.iTetrisGrid.deleteElementFromGrid(tetrisElement);
    }
}
