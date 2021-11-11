import com.robot.Tabletop;
import com.robot.ToyRobot;

import java.util.Scanner;

/**
 * The entry to the Robot
 * @author: Fiona Tian
 * @date: 05/11/2021
 */
public class Application {


    public static void main(String[] args) throws Exception{

        boolean exit = false;

        String command;
        Tabletop tabletop = new Tabletop(5,5);
        ToyRobot robot = new ToyRobot();

        Scanner scanner = new Scanner(System.in);

        System.err.println("Please input the following commands: 'PLACE X,Y,NORTH', 'MOVE', 'LEFT', " +
                "'RIGHT', 'REPORT' OR 'EXIT' to end this Application.");

        while (!exit){

            command = scanner.nextLine();
            if(command.equalsIgnoreCase("exit")){
                break;
            }

            try{
                tabletop.execute(command,robot);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }


    }
}
