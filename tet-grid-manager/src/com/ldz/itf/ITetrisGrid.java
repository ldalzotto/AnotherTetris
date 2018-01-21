package com.ldz.itf;

public interface ITetrisGrid {

    boolean addTetrisElement(TetrisElement tetrisElement);

    void renderAndUpdate(float delta);

    void setOutboundDestroyer(OutboundDestroyer outboundDestroyer);

    void deleteElementFromGrid(TetrisElement tetrisElement);

    TetrisElement getNewTetrisElementFromPool();

}
