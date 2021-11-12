package com.robot;

/**
 * @author: Fiona Tian
 * @date: 04/11/2021
 */
public class Tabletop {

    private int sizeX;

    private int sizeY;


    public Tabletop(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }


    /**
     * Validate the command
     * 1. The command only contains 'PLACE X,Y,F MOVE LEFT RIGHT REPORT'.
     * 2. The first command is PLACE.
     */

    private Command validCommand(String command, ToyRobot robot) throws Exception{

        try{
            Command c = Command.valueOf(command.trim());

            if(robot.getOrientation()==null
                    && !c.name().equalsIgnoreCase(Command.PLACE.name())){
                throw new Exception("Invalid command: the first command should be 'PLACE'.");

            }
            return c;
        }catch (IllegalArgumentException e){

            throw new Exception("Invalid command: " +
                    "the command only contains 'PLACE X,Y,F MOVE LEFT RIGHT REPORT'.");
        }

    }

    /**
     * Get the PLACE parameters, then convert string into int
     *
     * and invalidate the parameters
     */
    private String validPlace(String commandStr, ToyRobot robot) throws Exception {

        int posX;
        int posY;

        //Get the params one by one
        if(commandStr.split(",").length<3){
            throw new Exception("The command PLACE is invalid! It should be like 'PLACE X,Y,F'.");
        }

        String paramX = commandStr.substring(0, commandStr.indexOf(","))
                .replace(Command.PLACE.name(),"").trim();

        commandStr = commandStr.substring(commandStr.indexOf(",")+1);
        String paramY = commandStr.substring(0, commandStr.indexOf(","));

        commandStr = commandStr.substring(commandStr.indexOf(",")+1);
        String paramOrient;
        if(commandStr.trim().contains(" ")){
            paramOrient = commandStr.trim().substring(0,commandStr.indexOf(" "));
        }else {
            paramOrient = commandStr.trim();
        }

        //Get posX and posY
        try{
            posX = Integer.parseInt(paramX.trim());
            posY = Integer.parseInt(paramY.trim());
        }catch (Exception e){
            throw new Exception("The position X and Y should be numbers.");
        }


        this.validPosition(posX, posY, paramOrient, robot);

        return commandStr;

    }

    /**
     * Validate the position to move.
     *
     */
    private void validPosition(int posX, int posY, ToyRobot robot) throws Exception{

        if(posX>=sizeX || posX<0 || posY>=sizeY || posY<0){
            throw new Exception("The position is invalid.");
        }
        robot.setPositionX(posX);
        robot.setPositionY(posY);

    }
    /**
     * Validate the position to place.
     *
     */
    private void validPosition(int posX, int posY, String orientation, ToyRobot robot) throws Exception{

        if(posX>=sizeX || posX<0 || posY>=sizeY || posY<0){
            throw new Exception("The position is invalid.");
        }

        try{
            Orientation o = Orientation.valueOf(orientation.toUpperCase());
            robot.setOrientation(o.name());
        }catch (IllegalArgumentException e){
            throw new Exception("The orientation is invalid.");
        }

        robot.setPositionX(posX);
        robot.setPositionY(posY);

    }

    /**
     * Identify, validate and execute the command.
     *
     */
    public void execute(String commandStr, ToyRobot robot) throws Exception{

        String command;

        //'PLACE X,Y,NORTH' contains " ".
        // " " may be also between two commands.
        if(commandStr==null || commandStr.trim().isEmpty()){
            return;
        }else if(commandStr.trim().contains(" ")){
            commandStr = commandStr.trim().toUpperCase();
            command = commandStr.substring(0,commandStr.indexOf(" "));
        }else {
            commandStr = commandStr.trim().toUpperCase();
            command = commandStr.toUpperCase();
        }

        Orientation orient;

        Command c = this.validCommand(command, robot);

        switch(c){
            //PLACE
            case PLACE:
                commandStr = this.validPlace(commandStr, robot);
                break;
            //MOVE
            case MOVE:
                orient = Orientation.valueOf(robot.getOrientation());
                if(orient.name().equalsIgnoreCase(Orientation.NORTH.name())
                        || orient.name().equalsIgnoreCase(Orientation.SOUTH.name())){

                    validPosition(robot.getPositionX(), robot.getPositionY()+orient.getValue(), robot);

                }else{

                    validPosition(robot.getPositionX()+orient.getValue(), robot.getPositionY(), robot);
                }
                break;
            //LEFT
            case LEFT:
                orient = Orientation.valueOf(robot.getOrientation());
                robot.setOrientation(orient.getLeftOrient());
                break;
            //RIGHT
            case RIGHT:
                orient = Orientation.valueOf(robot.getOrientation());
                robot.setOrientation(orient.getRightOrient());
                break;
            //REPORT
            case REPORT:
                robot.report();
                break;

        }

        if(commandStr.trim().contains(" ")){
            commandStr = commandStr.substring(commandStr.indexOf(" ")+1);
            if(!commandStr.isEmpty()){
                execute(commandStr, robot);
            }
        }

    }
}
