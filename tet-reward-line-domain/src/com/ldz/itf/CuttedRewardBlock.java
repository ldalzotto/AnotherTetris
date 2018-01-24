package com.ldz.itf;

public class CuttedRewardBlock {

    private CuttedBlock upperCuttedBlock;
    private CuttedBlock lowerCuttedBlock;

    private float containedArea;

    public CuttedBlock getUpperCuttedBlock() {
        return upperCuttedBlock;
    }

    public void setUpperCuttedBlock(CuttedBlock upperCuttedBlock) {
        this.upperCuttedBlock = upperCuttedBlock;
    }

    public CuttedBlock getLowerCuttedBlock() {
        return lowerCuttedBlock;
    }

    public void setLowerCuttedBlock(CuttedBlock lowerCuttedBlock) {
        this.lowerCuttedBlock = lowerCuttedBlock;
    }

    public float getContainedArea() {
        return containedArea;
    }

    public void setContainedArea(float containedArea) {
        this.containedArea = containedArea;
    }
}
