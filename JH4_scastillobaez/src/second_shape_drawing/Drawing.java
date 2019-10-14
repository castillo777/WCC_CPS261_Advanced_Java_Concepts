package second_shape_drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

enum DrawType {scribble, oval, rectangle, polygon, line};

class DrawingProperties
{
    DrawType drawType;
    boolean filled;
    Color color;

    DrawingProperties(DrawType drawType, Color color, boolean filled)
    {
        this.drawType = drawType;
        this.color = color;
        this.filled = filled;
    }

    public String toString()
    {
        return drawType + "      color = " + color +"      filled = "+ filled;
    }
}

public class Drawing
{
    DrawingProperties drawingProperties = new DrawingProperties(DrawType.rectangle, Color.blue, false);
    ArrayList<Shape> shapeArr = new ArrayList<Shape>();
    Shape inProgress = null;

    public String toString()
    {
        return drawingProperties.toString();
    }

    public void draw(Graphics g)
    {
        for (int i=0; i < shapeArr.size(); i++)
        {
            Shape s = shapeArr.get(i);
            s.draw(g);
        }
        if (inProgress != null)
            inProgress.draw(g);
    }

    public void setColor(Color color)
    {
        drawingProperties.color = color;
    }

    public void setFilled(boolean filled)
    {
        drawingProperties.filled = filled;
    }

    public void setDrawType(DrawType drawType)
    {
        if (drawingProperties.drawType == DrawType.polygon && inProgress != null)
        {
            shapeArr.add(inProgress);
            inProgress = null;
        }
        drawingProperties.drawType = drawType;
    }

    public void mousePressed(Point p)
    {
        switch(drawingProperties.drawType)
        {
            case polygon: // polygon doesn't work with mousePressed
                return;
            case rectangle:
                inProgress = new Rectangle(drawingProperties.color, drawingProperties.filled);
                break;
            case oval:
                inProgress = new Oval(drawingProperties.color, drawingProperties.filled);
                break;
            case line:
                inProgress = new Line(drawingProperties.color);
                break;
            case scribble:
                inProgress = new Scribble(drawingProperties.color);
                break;
        }
        inProgress.firstPoint(p);
    }

    public void mouseDragged(Point p)
    {
        switch(drawingProperties.drawType)
        {
            case polygon: // polygon doesn't work with mouseDragged
                return;
            case rectangle:
            case oval:
            case scribble:
            case line:
                inProgress.subsequentPoint(p);
                break;
        }
    }

    public void mouseReleased(Point p)
    {
        if (drawingProperties.drawType == DrawType.polygon)
            return;
        inProgress.subsequentPoint(p);
        shapeArr.add(inProgress);
        inProgress = null;
    }

    public void mouseClicked(Point p)
    {
        if (drawingProperties.drawType == DrawType.polygon)
        {
            if (inProgress == null)
            {
                inProgress = new Polygon(drawingProperties.color, drawingProperties.filled);
                inProgress.firstPoint(p);
            }
            else
                inProgress.subsequentPoint(p);
        }
    }

}