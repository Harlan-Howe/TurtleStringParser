import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    public void actionPerformed(ActionEvent e)
    {
        JColorChooser chooser = new JColorChooser(myColor);
        int result = JOptionPane.showConfirmDialog(null, chooser);
        if (JOptionPane.OK_OPTION == result)
        {
            Color oldColor = myColor;
            myColor = chooser.getColor();
            repaint();
            this.firePropertyChange("myColor",oldColor, myColor);
        }
    }


}
