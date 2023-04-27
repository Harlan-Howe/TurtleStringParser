import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TurtleStringParserFrame extends JFrame implements ActionListener, ChangeListener, PropertyChangeListener
{
    private TurtlePanel turtle;
    private JSlider distanceSlider;
    private JLabel distanceLabel;
    private JCheckBox visibleTurtleToggle;
    private ColorChooserButton lineColorChooserButton, turtleColorChooserButton;

    private JTextArea outputTextArea;

    private JTextField sourceTF;
    private JTextField find1TextField, replace1TextField; // TODO (optional): make more.
    private JSpinner iterationSpinner;
    private JButton goButton;

    private StringConvolver myConvolver;

    public TurtleStringParserFrame()
    {
        super("Turtles all the way down.");
        setSize(1000,800);
        this.getContentPane().setLayout(new BorderLayout());
        turtle = new TurtlePanel();
        myConvolver = new StringConvolver();

        JPanel controlPanel = buildControlGUI();
        JPanel outputPanel = buildOutputGUI();
        JPanel inputPanel = buildInputGUI();
        this.getContentPane().add(turtle, BorderLayout.CENTER);
        this.getContentPane().add(controlPanel, BorderLayout.NORTH);
        this.getContentPane().add(outputPanel, BorderLayout.SOUTH);
        this.getContentPane().add(inputPanel, BorderLayout.EAST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }

    /**
     * Creates the controls for the North panel of the window, altering the general appearance of the turtle and path.
     * @return The panel that is ready to add to the North.
     */
    public JPanel buildControlGUI()
    {
        JPanel controlPanel = new JPanel();
        distanceSlider = new JSlider(JSlider.HORIZONTAL,1,100,30);
        distanceSlider.addChangeListener(this);
        distanceLabel = new JLabel("30 ");
        visibleTurtleToggle = new JCheckBox("Visible Turtle");
        visibleTurtleToggle.setSelected(true);
        visibleTurtleToggle.addActionListener(this);
        lineColorChooserButton = new ColorChooserButton("temp",Color.PINK);
        lineColorChooserButton.addPropertyChangeListener(this);
        turtleColorChooserButton = new ColorChooserButton("temp",Color.GREEN);
        turtleColorChooserButton.addPropertyChangeListener(this);

        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("Distance"));
        controlPanel.add(distanceSlider);
        controlPanel.add(distanceLabel);
        JPanel colorPanel = new JPanel(new GridLayout(2,2));
        colorPanel.add(new JLabel("Line:",JLabel.RIGHT));
        colorPanel.add(lineColorChooserButton);
        colorPanel.add(new JLabel("Turtle:", JLabel.RIGHT));
        colorPanel.add(turtleColorChooserButton);
        controlPanel.add(colorPanel);
        controlPanel.add(visibleTurtleToggle);
        return controlPanel;
    }

    /**
     * Creates the panel for the south side of the window, this is going to show the string that the turtle is being
     * given to follow, which may vary from the source string from the replacements. Consists of a text area in a scroll
     * pane, in case there is extra text that doesn't fit onscreen.
     * @return - a panel ready to add to the South.
     */
    public JPanel buildOutputGUI()
    {
        JPanel outputPanel = new JPanel();
        outputTextArea = new JTextArea("Nothing yet",4,60);
        outputTextArea.setLineWrap(true);
        outputTextArea.setEnabled(true);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputPanel.add(outputScrollPane);
        outputPanel.setPreferredSize(new Dimension(-1, 75));
        return outputPanel;
    }

    /**
     * creates the panel for the east side of the window. This has the controls for the shape of the path, along with
     * the "Go" button.
     * @return A panel that is ready to add to the East.
     */
    public JPanel buildInputGUI()
    {
        JPanel inputPanel = new JPanel();
        sourceTF = new JTextField(30);
        sourceTF.setText("Lffff RFFRFRFRFLLfRFLf LFFRFRFRFLfLff fRRFRFFRFf fLLFLFLFRFRF");
        sourceTF.setToolTipText("Enter the text that will be used as the basis of the turtle's instructions.");
        sourceTF.setBorder(new TitledBorder("Source string"));

        Box verticalStack = Box.createVerticalBox();
        verticalStack.add(sourceTF);
        verticalStack.add(Box.createVerticalStrut(30));

        Box findReplace1Row = Box.createHorizontalBox();
        find1TextField = new JTextField(10);
        find1TextField.setBorder(new TitledBorder("Find"));
        replace1TextField = new JTextField(10);
        replace1TextField.setBorder(new TitledBorder("Replace"));
        findReplace1Row.add(find1TextField);
        findReplace1Row.add(replace1TextField);
        verticalStack.add(findReplace1Row);
        verticalStack.add(Box.createVerticalStrut(30));

        Box iterationRow = Box.createHorizontalBox();
        iterationSpinner = new JSpinner(new SpinnerNumberModel(0,0,8,1)); // start, min, max, stepsize
        iterationRow.add(new JLabel("Number of replacement cycles:"));
        iterationRow.add(iterationSpinner);
        iterationRow.add(Box.createHorizontalGlue());
        verticalStack.add(iterationRow);
        verticalStack.add(Box.createVerticalStrut(30));

        goButton = new JButton("Go");
        Box goRow = Box.createHorizontalBox();
        goRow.add(goButton);
        goButton.addActionListener(this);
        verticalStack.add(goRow);

        verticalStack.add(Box.createVerticalGlue());
        inputPanel.add(verticalStack);
        return inputPanel;
    }

    @Override
    /**
     * required if we are implementing the ActionListener interface (see line 11). This method will get called if the
     * Go button or the turtle toggle button are pressed. (They are told to do this with the "addActionListener(this)"
     * command... look up about 12 lines for an example.)
     */
    public void actionPerformed(ActionEvent actEvt)
    {
        if (actEvt.getSource() == goButton)
        {
            respondToGoButton();
        }
        if (actEvt.getSource() == visibleTurtleToggle)
        {
            turtle.setTurtleIsVisible(visibleTurtleToggle.isSelected());
        }
    }

    /**
     * The user pressed the Go button, and we've been routed to this method to respond. Takes the source string and
     * performs a replacement cycle N times, before sending the resulting string to the output text area and the turtle.
     */
    private void respondToGoButton()
    {
        String source = sourceTF.getText();
        String stringToFind = find1TextField.getText();
        String stringToReplaceWith = replace1TextField.getText();
        int N = (int) iterationSpinner.getValue();

        myConvolver.setSourceString(source);
        myConvolver.setFind1(stringToFind);
        myConvolver.setReplace1(stringToReplaceWith);

        String instructions = myConvolver.convolveStringNTimes(N);

        turtle.setInstructions(instructions);
        outputTextArea.setText(instructions);
    }

    @Override
    /**
     * required if we are implementing the ChangeListener interface (see line 11). This method will be called if the
     * user makes a change to the distance slider at the top of the window.
     */
    public void stateChanged(ChangeEvent chgEvt)
    {
        if (chgEvt.getSource() == distanceSlider)
        {
            turtle.setDistance(distanceSlider.getValue());
            distanceLabel.setText(""+distanceSlider.getValue());

        }
        else
            System.out.println(chgEvt);
    }


    @Override
    /**
     * required if we are implementing the PropertyChangeListener interface (see line 11). This method will be called
     * if the user selects a new color in one of the color buttons.
     */
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getSource()==lineColorChooserButton && (((String)evt.getPropertyName()).equals("myColor")))
        {
            turtle.setLineColor((Color)evt.getNewValue());
        }
        if (evt.getSource()==turtleColorChooserButton && (((String)evt.getPropertyName()).equals("myColor")))
        {
            turtle.setTurtleColor((Color)evt.getNewValue());
        }
    }
}
