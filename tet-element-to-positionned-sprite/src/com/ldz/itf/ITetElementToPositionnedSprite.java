package com.ldz.itf;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

import java.util.List;

public interface ITetElementToPositionnedSprite {


    List<Sprite> getSpritesFromTetrisElement(TetrisElement tetrisElement);


}
