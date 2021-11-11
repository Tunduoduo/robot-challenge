package com.robot;

/**
 * @author: Fiona Tian
 * @date: 04/11/2021
 */
public class ToyRobot {

    int positionX;
    int positionY;
    String orientation;

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void report(){
        System.err.println(getPositionX()+","+getPositionY()+","+getOrientation());
    }
}
