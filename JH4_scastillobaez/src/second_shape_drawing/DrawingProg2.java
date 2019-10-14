package second_shape_drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class DrawingProg2 extends JFrame implements ActionListener
{
    private final Dimension DIMEN = new Dimension(800, 400);
    private final String defaultColor = "Red";
    private final String defaultShape = "Rectangle";

    DrawingPanel drawingPanel = new DrawingPanel();
    JPanel shapePanel = null;
    JPanel colorPanel = null;
    ArrayList<JRadioButton> shapeButtons = null;
    ArrayList<JRadioButton> colorButtons = null;
    JCheckBox filled = new JCheckBox("filled");

    DrawingProg2()
    {
        super("My Drawing Program");

        String[] colors = { "Red", "Green", "Blue" };
        String[] shapes = { "Rectangle", "Oval", "Line", "Scribble", "Polygon" };

        setSize(DIMEN);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        layout(shapes, colors);

        setVisible(true);
    }

    private void layout(String[] shapes, String[] colors)
    {
        setLayout(new BorderLayout());

        shapePanel = new JPanel(new FlowLayout());
        shapeButtons = new ArrayList<>(shapes.length);
        filled.addActionListener(this);
        shapePanel.add(filled);
        buildPanel(shapeButtons, shapePanel, shapes);

        colorPanel = new JPanel(new GridLayout(0, 1));
        colorButtons = new ArrayList<>(colors.length);
        buildPanel(colorButtons, colorPanel, colors);

        this.add(drawingPanel, BorderLayout.CENTER);
        this.add(shapePanel, BorderLayout.NORTH);
        this.add(colorPanel, BorderLayout.WEST);

        try
        {
            shapeButtons.get(getIndexOf(shapes, defaultShape)).doClick();
            colorButtons.get(getIndexOf(colors, defaultColor)).doClick();
        }
        catch (IndexOutOfBoundsException e)
        {
        }
    }

    private void buildPanel(ArrayList<JRadioButton> buttonList, JPanel jp, String[] buttonLabels)
    {
        ButtonGroup buttonGroup = new ButtonGroup();
        for (String elem : buttonLabels)
        {
            JRadioButton tempRadio = new JRadioButton(elem);
            tempRadio.addActionListener(this);
            buttonGroup.add(tempRadio);
            jp.add(tempRadio);
            buttonList.add(tempRadio);
        }
    }

    private <T> int getIndexOf(T[] arr, T value)
    {
        for (int i = 0; i < arr.length; i++)
            if (value == arr[i])
                return i;
        return -1;
    }

    public static void main(String[] args)
    {
        DrawingProg2 dp = new DrawingProg2();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        String action = actionEvent.getActionCommand().toLowerCase();

        switch(action)
        {
            case "red":
                drawingPanel.drawing.setColor(Color.red);
                break;
            case "green":
                drawingPanel.drawing.setColor(Color.green);
                break;
            case "blue":
                drawingPanel.drawing.setColor(Color.blue);
                break;
            case "filled":
                drawingPanel.drawing.setFilled(filled.isSelected());
                break;
            case "rectangle":
                drawingPanel.drawing.setDrawType(DrawType.rectangle);
                filled.setVisible(true);
                break;
            case "oval":
                drawingPanel.drawing.setDrawType(DrawType.oval);
                filled.setVisible(true);
                break;
            case "line":
                drawingPanel.drawing.setDrawType(DrawType.line);
                filled.setVisible(false);
                break;
            case "scribble":
                drawingPanel.drawing.setDrawType(DrawType.scribble);
                filled.setVisible(false);
                break;
            case "polygon":
                drawingPanel.drawing.setDrawType(DrawType.polygon);
                filled.setVisible(true);
                break;
        }
    }
}
