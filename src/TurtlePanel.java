import javax.swing.*;
import java.awt.*;

public class TurtlePanel extends JPanel
{
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
        instructions = "FfFRFLFLFffLFLFLFLFffFRFRFRF";
        turtleIsVisible = true;
        lineColor = Color.PINK;
        turtleColor = Color.GREEN;
        turtleSize = 20;
    }


    @Override
    public void paintComponent(Graphics g)
    {
        setBackground(Color.BLACK);
        super.paintComponent(g);
        int xLoc = getWidth()/2;
        int yLoc = getHeight()/2;
        int heading = NORTH;

        for(int i=0; i<instructions.length(); i++)
        {
            char c = instructions.charAt(i);
            switch(c)
            {
                case 'l':
                case 'L':
                    heading = (heading+3)%4;
                    break;
                case 'r':
                case 'R':
                    heading = (heading +1)%4;
                    break;
                case 'f':
                    xLoc += distance * deltas[heading][0];
                    yLoc += distance * deltas[heading][1];
                    break;
                case 'F':
                    int newX = xLoc + distance * deltas[heading][0];
                    int newY = yLoc + distance * deltas[heading][1];
                    g.setColor(lineColor);
                    g.drawLine(xLoc, yLoc, newX, newY);
                    xLoc = newX;
                    yLoc = newY;
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

    public void setTurtleIsVisible(boolean b) {turtleIsVisible = b;}

    public void setDistance(int d)
    {
        distance = d;
        turtleSize = 2*distance/3;
    }

}
