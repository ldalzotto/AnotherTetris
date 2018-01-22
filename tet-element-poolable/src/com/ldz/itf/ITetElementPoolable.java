package com.ldz.itf;

public interface ITetElementPoolable {

    public TetrisElement getElementInstance();

    public void deleteElementFromGrid(TetrisElement tetrisElement);
}
