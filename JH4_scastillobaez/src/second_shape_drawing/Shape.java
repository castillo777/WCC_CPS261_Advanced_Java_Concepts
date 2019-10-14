package second_shape_drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.lang.model.element.Element;

abstract class Shape
{
    Color color;
    Shape ( Color c)
    {
        color =c;
    }
    abstract void firstPoint(Point p);
    abstract void draw(Graphics g);
    abstract void subsequentPoint(Point p);
}

class Rectangle extends Shape
{
    boolean filled = false;
    Point start;
    Point lastPoint;

    Rectangle(Color c, boolean filled)
    {
        super(c);
        lastPoint = start;
        this.filled = filled;
    }

    @Override
    void firstPoint(Point p)
    {
        start = p;
        lastPoint = p;
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        int x = Math.min(start.x, lastPoint.x);
        int y = Math.min(start.y, lastPoint.y);
        int w = Math.abs(start.x - lastPoint.x);
        int h = Math.abs(start.y - lastPoint.y);
        if (filled)
            g.fillRect(x, y, w, h);
        else
            g.drawRect(x, y, w, h);
    }

    @Override
    void subsequentPoint(Point p)
    {
        lastPoint = p;
    }
}

class Scribble extends Shape
{
    ArrayList<Point> points = new ArrayList<Point>();

    Scribble(Color c)
    {
        super(c);
    }

    @Override
    void firstPoint(Point p)
    {
        points.add(p);
    }

    @Override
    void draw(Graphics g)
    {
        g.setColor(color);
        for (int i = 1; i < points.size(); i++)
        {
            Point first = points.get(i - 1);
            Point next = points.get(i);
            g.drawLine(first.x, first.y, next.x, next.y);
        }
    }

    @Override
    void subsequentPoint(Point p)
    {
        points.add(p);
    }
}

class Oval extends Shape
{
    boolean filled = false;
    Point start;
    Point lastPoint;

    Oval(Color c, boolean filled)
    {
        super(c);
        lastPoint = start;
        this.filled = filled;
    }

    @Override
    void firstPoint(Point p)
    {
        start = p;
        lastPoint = p;
    }

    @Override
    void draw(Graphics g)
    {
        g.setColor(color);
        int x = Math.min(start.x, lastPoint.x);
        int y = Math.min(start.y, lastPoint.y);
        int w = Math.abs(start.x - lastPoint.x);
        int h = Math.abs(start.y - lastPoint.y);
        if (filled)
            g.fillOval(x, y, w, h);
        else
            g.drawOval(x, y, w, h);
    }

    @Override
    void subsequentPoint(Point p)
    {
        lastPoint = p;

    }
}

class Line extends Shape
{
    Point start;
    Point lastPoint;

    Line(Color c)
    {
        super(c);
        lastPoint = start;
    }

    @Override
    void firstPoint(Point p)
    {
        start = p;
        lastPoint = p;
    }

    @Override
    void draw(Graphics g)
    {
        g.setColor(color);
        g.drawLine(start.x, start.y, lastPoint.x, lastPoint.y);
    }

    @Override
    void subsequentPoint(Point p)
    {
        lastPoint = p;
    }
}

class Polygon extends Shape
{
    boolean filled = false;
    ArrayList<Point> points = new ArrayList<Point>();

    Polygon(Color c, boolean filled)
    {
        super(c);
        this.filled = filled;
    }

    @Override
    void firstPoint(Point p)
    {
        points.add(p);
    }

    @Override
    void draw(Graphics g)
    {
        int numPoints = points.size();
        int[] x = new int[numPoints];
        int[] y = new int[numPoints];

        for (int i = 0; i < points.size(); i++)
        {
            x[i] = points.get(i).x;
            y[i] = points.get(i).y;
        }

        g.setColor(color);
        if (filled)
            g.fillPolygon(x, y, numPoints);
        else
            g.drawPolygon(x, y, numPoints);
    }

    @Override
    void subsequentPoint(Point p)
    {
        points.add(p);
    }

}
