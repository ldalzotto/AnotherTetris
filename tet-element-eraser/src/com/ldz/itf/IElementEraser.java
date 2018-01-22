package com.ldz.itf;

public interface IElementEraser {

    void addElementToErase(TetrisElement tetrisElement);
    void addBlockToErase(TetrisBlock tetrisBlock);
    void eraseAllElements();
}
