package com.ldz.itf;

public interface IElementPlayerManager {

    void render(float delta);

    void pushElementToGrid();

    void setPlayerElement(TetrisElement playerElement);

    TetrisElement getPlauerElement();

}
