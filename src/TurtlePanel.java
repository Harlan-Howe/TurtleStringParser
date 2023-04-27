import javax.swing.*;
import java.awt.*;

/**
 * a custom JPanel that holds a turtle that will draw a shape based on the commands in "instructions."
 */
public class TurtlePanel extends JPanel
{
    private int xLoc, yLoc, heading;
    private int distance;
    private String instructions;
    private boolean turtleIsVisible;
    private Color lineColor, turtleColor;
    private int turtleSize;

    public final int NORTH = 0;
    public final int EAST = 1;
    public final int SOUTH = 2;
    public final int WEST = 3;

    public final int[][] deltas = {{0,-1}, {1, 0}, {0, 1}, {-1, 0}};


    public TurtlePanel()
    {
        super();
        distance = 30;
        instructions = "";
        turtleIsVisible = true;
        lineColor = Color.PINK;
        turtleColor = Color.GREEN;
        turtleSize = 20;
        setBackground(Color.BLACK);

    }


    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        xLoc = getWidth()/2;
        yLoc = getHeight()/2;
        heading = NORTH;

        //TODO #0: loop through all the characters in the "instructions" variable.
        //       Every time you encounter 'L' or 'l', turnLeft();
        //       Every time you encounter 'R' or 'r', turnRight();
        //       Every time you encounter 'f', skipForward();
        //       Every time you encounter 'F', lineForward();
        //       Every time you encounter some other character, do nothing -- go on to the next character.

        // Potential extension... when encountering a numerical digit, change colors.
        //                        (e.g., '1' = red, '2' = green, etc.)
        // Potential extension... allow comments, perhaps by letting backslash mean "ignore the next character"
        //                        or the hashtag mean "ignore all text until the next hashtag."
        //                        (Yes, comments already work, as long as you don't use L, R, or F in your comment.)

        for(int i=0; i<instructions.length(); i++)
        {
            char c = instructions.charAt(i);
            switch(c)
            {
                case 'l':
                case 'L':
                    turnLeft();
                    break;
                case 'r':
                case 'R':
                    turnRight();
                    break;
                case 'f':
                    skipForward();
                    break;
                case 'F':
                    lineForward(g);
                    break;
            }
        }
        if (turtleIsVisible)
        {
            g.setColor(turtleColor);
            g.drawOval(xLoc-turtleSize/2, yLoc-turtleSize/2, turtleSize, turtleSize); // body
            int headSize = turtleSize/2;
            g.drawOval(xLoc+turtleSize*deltas[heading][0]/2-headSize/2,
                       yLoc+turtleSize*deltas[heading][1]/2-headSize/2,
                       headSize,
                       headSize); //head
            // now feet.
            int footSize = turtleSize/4;
            int footCenterComponent = (int)(0.707*(turtleSize/2+1));
            for(int x=-1; x<= +1; x+=2)
                for (int y=-1; y<= +1; y +=2)
                {
                    g.drawRect(xLoc+x*footCenterComponent-footSize/2,
                               yLoc+y*footCenterComponent-footSize/2,
                                footSize,
                                footSize);
                }
        }
    }

    /**
     * moves the turtle forward by "distance" in its current heading, drawing a line from where it just was to its new
     * location.
     * @param g - the graphics context.
     */
    private void lineForward(Graphics g)
    {
        int newX = xLoc + distance * deltas[heading][0];
        int newY = yLoc + distance * deltas[heading][1];
        g.setColor(lineColor);
        g.drawLine(xLoc, yLoc, newX, newY);
        xLoc = newX;
        yLoc = newY;
    }

    /**
     * moves the turtle forward by "distance" in its current heading, without drawing a line.
     */
    private void skipForward()
    {
        xLoc += distance * deltas[heading][0];
        yLoc += distance * deltas[heading][1];
    }

    /**
     * turns the turtle 90° clockwise
     */
    private void turnRight()
    {
        heading = (heading +1)%4;
    }

    /**
     * turns the turtle 90° counterclockwise.
     */
    private void turnLeft()
    {
        heading = (heading+3)%4;
    }

    public void setTurtleIsVisible(boolean b)
    {
        turtleIsVisible = b;
        repaint();
    }

    public void setDistance(int d)
    {
        distance = d;
        turtleSize = 2*distance/3;
        repaint();
    }

    public void setInstructions(String inst)
    {
        instructions = inst;
        repaint();
    }

    public void setLineColor(Color lineColor)
    {
        this.lineColor = lineColor;
        repaint();
    }

    public void setTurtleColor(Color turtleColor)
    {
        this.turtleColor = turtleColor;
        repaint();
    }
}
