package com.ldz.impl;

import com.badlogic.gdx.math.Rectangle;
import com.ldz.itf.*;

public class GridInitializer implements IGridInitializer {

    private static IGridInitializer instance;

    private ITetrisElementGenerator iTetrisElementGenerator;
    private IOutboundDestroyerGenerator iOutboundDestroyerGenerator;

    private ITetrisGrid iTetrisGrid;

    private GridInitializer() {
        iTetrisElementGenerator = TetrisElementGenerator.getInstance();
        iTetrisGrid = TetrisGrid.getInstance();
        iOutboundDestroyerGenerator = OutboundDestroyerGenerator.getInstance();
    }

    public static IGridInitializer getInstance() {
        if (instance == null) {
            instance = new GridInitializer();
        }
        return instance;
    }

    @Override
    public void initializeGrid() {
        Rectangle bottomLimit = new Rectangle();
        bottomLimit.x = TetrisGridContants.GRID_BOTTOM_LEFT_POSITION.x + TetrisGridContants.GRID_BLOCK_SIZE * TetrisGridContants.NB_BLOCK_WIDTH / 2;
        bottomLimit.y = TetrisGridContants.GRID_BOTTOM_LEFT_POSITION.y + TetrisGridContants.GRID_BLOCK_SIZE / 2;
        bottomLimit.height = TetrisGridContants.GRID_BLOCK_SIZE;
        bottomLimit.width = TetrisGridContants.GRID_BLOCK_SIZE * TetrisGridContants.NB_BLOCK_WIDTH;

        Rectangle leftRectangle = new Rectangle();
        leftRectangle.x = TetrisGridContants.GRID_BOTTOM_LEFT_POSITION.x - TetrisGridContants.GRID_BLOCK_SIZE / 2;
        leftRectangle.y = TetrisGridContants.GRID_BOTTOM_LEFT_POSITION.y + TetrisGridContants.GRID_BLOCK_SIZE * TetrisGridContants.NB_BLOCK_HEIGHT / 2;
        leftRectangle.height = TetrisGridContants.GRID_BLOCK_SIZE * TetrisGridContants.NB_BLOCK_HEIGHT;
        leftRectangle.width = TetrisGridContants.GRID_BLOCK_SIZE;

        Rectangle rightRectangle = new Rectangle();
        rightRectangle.x = TetrisGridContants.GRID_BOTTOM_LEFT_POSITION.x + TetrisGridContants.GRID_BLOCK_SIZE * TetrisGridContants.NB_BLOCK_WIDTH + TetrisGridContants.GRID_BLOCK_SIZE / 2;
        rightRectangle.y = TetrisGridContants.GRID_BOTTOM_LEFT_POSITION.y + TetrisGridContants.GRID_BLOCK_SIZE * TetrisGridContants.NB_BLOCK_HEIGHT / 2;
        rightRectangle.height = TetrisGridContants.GRID_BLOCK_SIZE * TetrisGridContants.NB_BLOCK_HEIGHT;
        rightRectangle.width = TetrisGridContants.GRID_BLOCK_SIZE;

        iTetrisElementGenerator.createTetrisGrid(bottomLimit, leftRectangle, rightRectangle);
        //BottomLine bottomLine =  iTetrisElementGenerator.createTetrisBottomLine(TetrisGridContants.GRID_BOTTOM_LEFT_POSITION);
        //iTetrisGrid.addBottomLine(bottomLine);

        //initialize destroyer outbound
        OutboundDestroyer outboundDestroyer = this.iOutboundDestroyerGenerator.createOutboundDestroyer();
        this.iTetrisGrid.setOutboundDestroyer(outboundDestroyer);
    }
}
