package com.ldz.impl;

import com.badlogic.gdx.math.Vector2;
import com.ldz.itf.*;

import java.util.ArrayList;
import java.util.List;

public class ElementSpawn implements IElementSpawn {

    private static IElementSpawn instance;

    private ElementSpawn() {
    }

    public static IElementSpawn getInstance() {
        if (instance == null) {
            instance = new ElementSpawn();
        }
        return instance;
    }

    @Override
    public void spawnElement() {
        ITetrisElementGenerator iTetrisElementGenerator = TetrisElementGenerator.getInstance();
        ITetrisGrid iTetrisGrid = TetrisGrid.getInstance();
        IElementPlayerManager iElementPlayerManager = ElementPlayerManager.getInstance();


        TetrisElement tetrisElement = iTetrisElementGenerator.createTetrisElement(createPositions(), TetrisGridContants.GRID_BLOCK_SIZE,
                iTetrisGrid.getNewTetrisElementFromPool());
        iTetrisGrid.addTetrisElement(tetrisElement);
        iElementPlayerManager.setPlayerElement(tetrisElement);
    }

    private List<Vector2> createPositions() {
        List<Vector2> positions = new ArrayList<>();
        ElementSchemas.ElementSchemasType elementSchemasType = ElementSchemas.getRandomType();
        int[][] presence = elementSchemasType.getPresence();

        float initialHeight = TetrisGridContants.NB_BLOCK_HEIGHT * TetrisGridContants.GRID_BLOCK_SIZE;

        for (int x = 0; x < ElementSchemas.X_NUMBER; x++) {
            for (int y = 0; y < ElementSchemas.Y_NUMBER; y++) {
                if (presence[y][x] == 1) {
                    Vector2 positionToAdd = new Vector2(x * TetrisGridContants.GRID_BLOCK_SIZE, initialHeight + (y * TetrisGridContants.GRID_BLOCK_SIZE));
                    positions.add(positionToAdd);
                }
            }
        }

        return positions;
    }
}
