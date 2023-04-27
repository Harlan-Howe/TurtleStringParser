import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is a custom class that creates a button that contains a color... clicking the button presents a dialog with a
 * color chooser. If the user clicks ok in that dialog, updates the color and fires a PropertyChange event.
 */
public class ColorChooserButton extends JButton implements ActionListener
{
    private Color myColor;

    public ColorChooserButton(String title, Color startColor)
    {
        super();
        myColor = startColor;
        setMinimumSize(new Dimension(100,100));
        addActionListener(this);
    }

    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        g.setColor(myColor);
        g.fillRect(10, 10, getWidth()-20, getHeight()-20);
    }

    @Override
    /**
     * required as we are implementing the ActionListener interface (see line 10). This will be called if the user
     * clicks this button. It is activated on line 19 with the "addActionListener(this)" command.
     */
    public void actionPerformed(ActionEvent e)
    {
        JColorChooser chooser = new JColorChooser(myColor);
        int result = JOptionPane.showConfirmDialog(null, chooser); //open a dialog window with a color chooser in it,
                                                                    // and wait for the user to click ok or cancel.
        if (JOptionPane.OK_OPTION == result) // if they clicked "ok"
        {
            Color oldColor = myColor;
            myColor = chooser.getColor();
            repaint();
            this.firePropertyChange("myColor",oldColor, myColor);// "fire" an event that will be picked up by the frame.
        }
    }


}
