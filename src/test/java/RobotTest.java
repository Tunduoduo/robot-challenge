import com.robot.Orientation;
import com.robot.Tabletop;
import com.robot.ToyRobot;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * 1. Test the three examples providing by this task.
 *
 * 2. Test command PLACE X,Y,F MOVE LEFT RIGHT REPORT
 *
 * @author: Fiona Tian
 * @date: 07/11/2021
 */
@RunWith(JUnit4.class)
public class RobotTest extends TestCase {

    private Tabletop tabletop;
    private ToyRobot robot;

    @Before
    public void init(){

        tabletop = new Tabletop(5,5);
        robot = new ToyRobot();

    }

    /**
     * Example Input and Output:
     *
     * plain PLACE 0,0,NORTH MOVE REPORT Output: 0,1,NORTH
     *
     * plain PLACE 0,0,NORTH LEFT REPORT Output: 0,0,WEST
     *
     * plain PLACE 1,2,EAST MOVE MOVE LEFT MOVE REPORT Output: 3,3,NORTH
     * */
    @Test
    public void testExample() throws Exception{

        tabletop.execute("PLACE 0,0,NORTH MOVE REPORT ", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(1, robot.getPositionY());
        assertEquals(Orientation.NORTH.name(), robot.getOrientation());

    }

    @Test
    public void testExample2() throws Exception{

        tabletop.execute("PLACE 0,0,NORTH LEFT REPORT", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertEquals(Orientation.WEST.name(), robot.getOrientation());

    }

    @Test
    public void testExample3() throws Exception{

        tabletop.execute("PLACE 1,2,EAST MOVE MOVE LEFT MOVE REPORT ", robot);

        assertEquals(3, robot.getPositionX());
        assertEquals(3, robot.getPositionY());
        assertEquals(Orientation.NORTH.name(), robot.getOrientation());

    }
    /**
     * Test empty command
     * */
    @Test
    public void testEmptyCommand() throws Exception{


        tabletop.execute(" ", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertNull(robot.getOrientation());

    }
    /**
     * Test invalid command
     * */
    @Test
    public void testInvalidCommand(){

        String[] params = new String[4];
        params[0] = "1,2,North";
        params[1] = "skjdf ksjf";
        params[2] = "Places";
        params[3] = "111111";

        for(String param : params){

            Exception e = Assert.assertThrows(Exception.class, () -> tabletop.execute(param, robot));

            System.err.println(e.getMessage());

            assertEquals(0, robot.getPositionX());
            assertEquals(0, robot.getPositionY());
            assertNull(robot.getOrientation());
            assertEquals("Invalid command: the command only " +
                    "contains 'PLACE X,Y,F MOVE LEFT RIGHT REPORT'.", e.getMessage());
        }

    }

    /**
     * Test the first command
     */
    @Test
    public void testFirstCommand(){

        String[] params = new String[4];
        params[0] = "MOVE";
        params[1] = " LEFT";
        params[2] = " RIGHT ";
        params[3] = "REPORT ";

        for(String param : params){

            Exception e = Assert.assertThrows(Exception.class, () -> tabletop.execute(param, robot));

            System.err.println(e.getMessage());

            assertEquals(0, robot.getPositionX());
            assertEquals(0, robot.getPositionY());
            assertNull(robot.getOrientation());
            assertEquals("Invalid command: the first command should be 'PLACE'.", e.getMessage());

        }
    }


    /**
     * Test command PLACE X,Y,F
     */
    @Test
    public void testPlaceOrientation() throws Exception{

        String[] params = new String[4];
        params[0] = "PLACE 1,2,NORTH";
        params[1] = "PLACE 1,2,EAST  ";
        params[2] = "PLACE  1,2,SOUTH";
        params[3] = "PLACE   1,2,WEST ";

        for(String param : params){

            tabletop.execute(param, robot);

            assertEquals(1, robot.getPositionX());
            assertEquals(2, robot.getPositionY());
            assertEquals(param.replace(" ","").substring(9), robot.getOrientation());
        }

    }

    @Test
    public void testTwoPlaceCommands() throws Exception{

        String[] params = new String[5];
        params[0] = "PLACE 1,2,NORTH PLACE 2,3,EAST";
        params[1] = "PLACE 1, 2,NORTH REPORT PLACE 2,3,EAST ";
        params[2] = "PLACE 1, 2,NORTH MOVE PLACE 2,3,EAST ";
        params[3] = "PLACE 1, 2,NORTH RIGHT PLACE 2,3,EAST ";
        params[4] = "PLACE 1, 2,NORTH LEFT PLACE 2,3,EAST ";

        for(String param : params){

            tabletop.execute(param, robot);
            assertEquals(2, robot.getPositionX());
            assertEquals(3, robot.getPositionY());
            assertEquals(Orientation.EAST.name(),robot.getOrientation());

        }

    }

    @Test
    public void testInvalidPosition(){

        String[] params = new String[3];
        params[0] = "PLACE s,0,NORTH REPORT";
        params[1] = "PLACE 0,s,NORTH REPORT";
        params[2] = "PLACE s,s,NORTH REPORT";
        for(String param : params) {

            Exception e = Assert.assertThrows(Exception.class, () -> tabletop.execute(param, robot));

            System.err.println(e.getMessage());

            assertEquals(0, robot.getPositionX());
            assertEquals(0, robot.getPositionY());
            assertNull(robot.getOrientation());
            assertEquals("The position X and Y should be numbers.", e.getMessage());
        }

    }

    @Test
    public void testPositionWithoutXYF(){

        String[] params = new String[2];
        params[0] = "PLACE 2,NORTH";
        params[1] = "PLACE 1,2";
        for(String param : params) {

            Exception e = Assert.assertThrows(Exception.class, () -> tabletop.execute(param, robot));

            System.err.println(e.getMessage());

            assertEquals(0, robot.getPositionX());
            assertEquals(0, robot.getPositionY());
            assertNull(robot.getOrientation());
            assertEquals("The command PLACE is invalid! It should be like 'PLACE X,Y,F'.", e.getMessage());

        }

    }

    @Test
    public void testPositionWithSpace() throws Exception{

        String[] params = new String[4];
        params[0] = "PLACE 1 ,2,NORTH";
        params[1] = "PLACE 1, 2,NORTH";
        params[2] = "PLACE 1,2 ,NORTH";
        params[3] = "PLACE 1,2, NORTH";

        for(String param : params){

            tabletop.execute(param, robot);
            assertEquals(1, robot.getPositionX());
            assertEquals(2, robot.getPositionY());
            assertEquals(Orientation.NORTH.name(),robot.getOrientation());

        }

    }


    @Test
    public void testInvalidPositionXY(){

        String[] params = new String[6];
        params[0] = "PLACE -1,2,NORTH";
        params[1] = "PLACE 5,2,NORTH";
        params[2] = "PLACE 7,2,NORTH";
        params[3] = "PLACE 1,-1,NORTH";
        params[4] = "PLACE 1,5,NORTH";
        params[5] = "PLACE 1,7,NORTH";

        for(String param : params){

            Exception e = Assert.assertThrows(Exception.class, () -> tabletop.execute(param, robot));

            System.err.println(e.getMessage());

            assertEquals(0, robot.getPositionX());
            assertEquals(0, robot.getPositionY());
            assertNull(robot.getOrientation());
            assertEquals("The position is invalid.", e.getMessage());
        }

    }

    @Test
    public void testInvalidOrientation(){

        Exception e = Assert.assertThrows(Exception.class, () -> tabletop.execute("PLACE 1,2,E", robot));

        System.err.println(e.getMessage());

        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertNull(robot.getOrientation());
        assertEquals("The orientation is invalid.", e.getMessage());
    }


    /**
     * Test command MOVE
     */
    @Test
    public void testMove() throws Exception{

        tabletop.execute("PLACE 0,0,NORTH MOVE", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(1, robot.getPositionY());
        assertEquals(Orientation.NORTH.name(), robot.getOrientation());

    }

    @Test
    public void testMoveOutOfRange(){

        String[] params = new String[8];
        params[0] = "PLACE 0,0,SOUTH MOVE";
        params[1] = "PLACE 0,0,WEST MOVE";
        params[2] = "PLACE 4,0,SOUTH MOVE";
        params[3] = "PLACE 4,0,EAST MOVE";
        params[4] = "PLACE 4,4,NORTH MOVE";
        params[5] = "PLACE 4,4,EAST MOVE";
        params[6] = "PLACE 0,4,NORTH MOVE";
        params[7] = "PLACE 0,4,WEST MOVE";

        for(String param : params){

            Exception e = Assert.assertThrows(Exception.class, () -> tabletop.execute(param, robot));

            System.err.println(e.getMessage());

            assertEquals("The position is invalid.", e.getMessage());
            assertEquals(Integer.parseInt(param.substring(6,9).split(",")[0]), robot.getPositionX());
            assertEquals(Integer.parseInt(param.substring(6,9).split(",")[1]), robot.getPositionY());
            assertEquals(param.replace(" MOVE","").substring(10), robot.getOrientation());

        }

    }

    /**
     * Test command LEFT
     */
    @Test
    public void testNorthLeft() throws Exception{

        tabletop.execute("PLACE 0,0,NORTH LEFT", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertEquals(Orientation.WEST.name(), robot.getOrientation());

    }
    @Test
    public void testEastLeft() throws Exception{

        tabletop.execute("PLACE 0,0,EAST LEFT", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertEquals(Orientation.NORTH.name(), robot.getOrientation());

    }
    @Test
    public void testSouthLeft() throws Exception{

        tabletop.execute("PLACE 0,0,SOUTH LEFT", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertEquals(Orientation.EAST.name(), robot.getOrientation());

    }

    @Test
    public void testWestLeft() throws Exception{

        tabletop.execute("PLACE 0,0,WEST LEFT", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertEquals(Orientation.SOUTH.name(), robot.getOrientation());

    }


    /**
     * Test command RIGHT
     */
    @Test
    public void testNorthRight() throws Exception{

        tabletop.execute("PLACE 0,0,NORTH RIGHT", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertEquals(Orientation.EAST.name(), robot.getOrientation());

    }

    @Test
    public void testEastRight() throws Exception{

        tabletop.execute("PLACE 0,0,EAST RIGHT", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertEquals(Orientation.SOUTH.name(), robot.getOrientation());

    }

    @Test
    public void testSouthRight() throws Exception{

        tabletop.execute("PLACE 0,0,SOUTH RIGHT", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertEquals(Orientation.WEST.name(), robot.getOrientation());

    }

    @Test
    public void testWestRight() throws Exception{

        tabletop.execute("PLACE 0,0,WEST RIGHT", robot);

        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertEquals(Orientation.NORTH.name(), robot.getOrientation());

    }

    /**
     * Test report
     */
    @Test
    public void testReport() throws Exception{

        String[] params = new String[4];
        params[0] = "PLACE 1,2,NORTH REPORT MOVE MOVE";
        params[1] = "PLACE 1,2,NORTH REPORT MOVE REPORT MOVE";
        params[2] = "PLACE 1,2,NORTH REPORT MOVE REPORT MOVE REPORT";
        params[3] = "PLACE 1,2,NORTH REPORT MOVE REPORT MOVE REPORT REPORT";

        for(String param : params){

            tabletop.execute(param, robot);
            assertEquals(1, robot.getPositionX());
            assertEquals(4, robot.getPositionY());
            assertEquals(Orientation.NORTH.name(),robot.getOrientation());

        }

    }


}
