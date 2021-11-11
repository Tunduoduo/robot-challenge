package com.robot;

/**
 * @author: Fiona Tian
 * @date: 06/11/2021
 */
public enum Orientation {

    NORTH("WEST","EAST",1),
    EAST("NORTH","SOUTH",1),
    SOUTH("EAST","WEST",-1),
    WEST("SOUTH","NORTH",-1);

    String leftOrient;
    String rightOrient;
    int value;

    Orientation(String leftOrient, String rightOrient, int value) {
        this.leftOrient = leftOrient;
        this.rightOrient = rightOrient;
        this.value = value;
    }

    public String getLeftOrient() {
        return leftOrient;
    }

    public void setLeftOrient(String leftOrient) {
        this.leftOrient = leftOrient;
    }

    public String getRightOrient() {
        return rightOrient;
    }

    public void setRightOrient(String rightOrient) {
        this.rightOrient = rightOrient;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
