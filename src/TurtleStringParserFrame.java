import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TurtleStringParserFrame extends JFrame implements ActionListener, ChangeListener
{
    private TurtlePanel turtle;
    private JSlider distanceSlider;
    private JLabel distanceLabel;
    private JCheckBox visibleTurtleToggle;

    private JTextArea outputTextArea;

    private JTextField sourceTF, replace1TF, with1TF;
    private JSpinner iterationSpinner;
    private JButton goButton;

    public TurtleStringParserFrame()
    {
        super("Turtles all the way down.");
        setSize(1000,800);
        this.getContentPane().setLayout(new BorderLayout());
        turtle = new TurtlePanel();
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

    public JPanel buildControlGUI()
    {
        JPanel controlPanel = new JPanel();
        distanceSlider = new JSlider(JSlider.HORIZONTAL,0,150,30);
        distanceSlider.addChangeListener(this);
        distanceLabel = new JLabel("30 ");
        visibleTurtleToggle = new JCheckBox("Visible Turtle");
        visibleTurtleToggle.setSelected(true);
        visibleTurtleToggle.addActionListener(this);
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("Distance"));
        controlPanel.add(distanceSlider);
        controlPanel.add(distanceLabel);
        controlPanel.add(visibleTurtleToggle);
        return controlPanel;
    }

    public JPanel buildOutputGUI()
    {
        JPanel outputPanel = new JPanel();
        outputTextArea = new JTextArea("Nothing yet",4,60);
        outputTextArea.setEnabled(false);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputPanel.add(outputScrollPane);
        outputPanel.setPreferredSize(new Dimension(-1, 75));
        return outputPanel;
    }

    public JPanel buildInputGUI()
    {
        JPanel inputPanel = new JPanel();
        sourceTF = new JTextField(30);
        sourceTF.setToolTipText("Enter the text that will be used as the basis of the turtle's instructions.");
        sourceTF.setBorder(new TitledBorder("Source string"));

        Box verticalStack = Box.createVerticalBox();
        verticalStack.add(sourceTF);
        verticalStack.add(Box.createVerticalStrut(30));

        Box replace1Row = Box.createHorizontalBox();
        replace1TF = new JTextField(10);
        replace1TF.setBorder(new TitledBorder("Replace"));
        with1TF = new JTextField(10);
        with1TF.setBorder(new TitledBorder("With"));
        replace1Row.add(replace1TF);
        replace1Row.add(with1TF);
        verticalStack.add(replace1Row);
        verticalStack.add(Box.createVerticalStrut(30));

        Box iterationRow = Box.createHorizontalBox();
        iterationSpinner = new JSpinner(new SpinnerNumberModel(0,0,8,1)); // start, min, max, stepsize
        iterationRow.add(new JLabel("Number of cycles:"));
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
    public void actionPerformed(ActionEvent actEvt)
    {
        if (actEvt.getSource() == goButton)
        {
            System.out.println("Go button pressed.");
        }
        if (actEvt.getSource() == visibleTurtleToggle)
        {
            turtle.setTurtleIsVisible(visibleTurtleToggle.isSelected());
            turtle.repaint();
        }
    }

    @Override
    public void stateChanged(ChangeEvent chgEvt)
    {
        if (chgEvt.getSource() == distanceSlider)
        {
            turtle.setDistance(distanceSlider.getValue());
            distanceLabel.setText(""+distanceSlider.getValue());
            turtle.repaint();
        }
    }
}
