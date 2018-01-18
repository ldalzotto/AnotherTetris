package com.ldz.itf;

public interface ITetrisGrid {

    boolean addTetrisElement(TetrisElement tetrisElement);

    void addBottomLine(BottomLine bottomLine);

    void renderAndUpdate(float delta);

    BottomLine getBottomLine();

    boolean isInField(TetrisElement tetrisElement);

}
