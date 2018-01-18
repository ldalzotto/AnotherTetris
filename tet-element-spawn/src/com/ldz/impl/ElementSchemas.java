package com.ldz.impl;

import com.badlogic.gdx.math.MathUtils;

public class ElementSchemas {

    public static int X_NUMBER = 4;
    public static int Y_NUMBER = 3;

    public static ElementSchemasType getRandomType() {
        int randomIndex = MathUtils.random(0, ElementSchemasType.values().length - 1);
        return ElementSchemasType.values()[randomIndex];
    }

    enum ElementSchemasType {
        LINE(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0}
        }),
        L(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 1, 1},
                {0, 0, 0, 0}
        }),
        Z(new int[][]{
                {1, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        }),
        BLOCK(new int[][]{
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        }),
        T(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 0, 0}
        });

        private int[][] presence = new int[ElementSchemas.Y_NUMBER][ElementSchemas.X_NUMBER];

        ElementSchemasType(int[][] presence) {
            this.presence = presence;
        }

        public int[][] getPresence() {
            return presence;
        }
    }

}
