package first_shape_drawing;

import java.awt.*;
import java.util.ArrayList;

enum DrawType {scribble, oval, rectangle, polygon, line};

class DrawingProperties {
    DrawType drawType;
    boolean filled;
    Color color;

    DrawingProperties(DrawType drawType, Color color, boolean filled) {
        this.drawType = drawType;
        this.color = color;
        this.filled = filled;
    }

    public String toString() {
        return drawType + " color=" + color + " filled=" + filled;
    }
}

public class Drawing {
    DrawingProperties drawingProperties = new DrawingProperties(DrawType.polygon, Color.blue, false);
    ArrayList<Shape> shapeArr = new ArrayList<Shape>();
    Shape inProgress = null;
    int counter = 0;
    boolean polyOption = false;
    ArrayList<Point> pointsPoly = new ArrayList<Point>();

    public String toString() {
        return drawingProperties.toString();
    }

    public void draw(Graphics g) {
        for (int i = 0; i < shapeArr.size(); i++) {
            Shape s = shapeArr.get(i);
            s.draw(g);
        }
        if (inProgress != null)
            inProgress.draw(g);
    }

    public void setColor(Color color) {
        drawingProperties.color = color;
    }

    public void setFilled(boolean filled) {
        drawingProperties.filled = filled;
    }

    public void setDrawType(DrawType drawType) {
        drawingProperties.drawType = drawType;
    }

    public void mousePressed(Point p) {
        polyOption = false;
        switch (drawingProperties.drawType) {
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
            case polygon:
                polyOption = true;
                counter++;
                pointsPoly.add(p);
                inProgress = new MyPolygon(drawingProperties.color, drawingProperties.filled, pointsPoly);


        }
        inProgress.firstPoint(p);
    }

    public void mouseDragged(Point p) {
        switch (drawingProperties.drawType) {
            case rectangle:
            case oval:
            case scribble:
            case line:
                inProgress.subsequentPoint(p);
                break;
        }
    }

    public void mouseReleased(Point p) {
        inProgress.subsequentPoint(p);
        if (!polyOption) {
            shapeArr.add(inProgress);
        } else {
            if (counter <= 2) {
                System.out.println(shapeArr);
                System.out.println(shapeArr.size());
                shapeArr.add(inProgress);
            }

            else if (counter > 2) {
                if (counter == 3){
                    shapeArr.remove(shapeArr.size()-2);
                }
                shapeArr.remove(shapeArr.size() - 1);

                shapeArr.add(inProgress);
                System.out.println(shapeArr);
            }

        }
        inProgress = null;
    }

}

abstract class Shape {
    Color color;


    Shape(Color c) {
        color = c;
    }

    abstract void firstPoint(Point p);

    abstract void draw(Graphics g);

    abstract void subsequentPoint(Point p);
}

class Rectangle extends Shape {
    boolean filled = false;
    Point start;
    Point lastPoint;

    Rectangle(Color c, boolean filled) {
        super(c);
        lastPoint = start;
        this.filled = filled;
    }

    @Override
    void firstPoint(Point p) {
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
    void subsequentPoint(Point p) {
        lastPoint = p;

    }
}

class Oval extends Shape {
    boolean filled = false;
    Point start;
    Point lastPoint;


    Oval(Color c, boolean filled) {
        super(c);
        lastPoint = start;
        this.filled = filled;
    }

    @Override
    void firstPoint(Point p) {

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
        if (filled) {
            g.fillOval(x, y, w, h);
        } else {
            g.drawOval(x, y, w, h);
        }
    }

    @Override
    void subsequentPoint(Point p) {
        lastPoint = p;
    }
}

class Line extends Shape {
    Point start;
    Point lastPoint;

    Line(Color c) {
        super(c);
        lastPoint = start;
    }

    @Override
    void firstPoint(Point p) {
        start = p;
        lastPoint = p;
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        int x = start.x;
        int y = start.y;
        int lastx = Math.abs(lastPoint.x);
        int lasty = Math.abs(lastPoint.y);

        g.drawLine(x, y, lastx, lasty);
    }

    @Override
    void subsequentPoint(Point p) {
        lastPoint = p;

    }
}

class Scribble extends Shape {
    ArrayList<Point> points = new ArrayList<Point>();

    Scribble(Color c) {
        super(c);
    }

    @Override
    void firstPoint(Point p) {
        points.add(p);

    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        for (int i = 1; i < points.size(); i++) {
            Point first = points.get(i - 1);
            Point next = points.get(i);
            g.drawLine(first.x, first.y, next.x, next.y);
        }

    }

    @Override
    void subsequentPoint(Point p) {
        points.add(p);


    }
}

class MyPolygon extends Shape {
    boolean filled = false;
    ArrayList<Point> pointsPoly = new ArrayList<Point>();


    MyPolygon(Color c, boolean filled, ArrayList<Point> pointsPoly) {
        super(c);
        this.filled = filled;

        for (int i = 0; i < pointsPoly.size(); i++) {
            this.pointsPoly.add(pointsPoly.get(i));
        }

    }

    @Override
    void firstPoint(Point p) {

    }

    @Override
    void draw(Graphics g) {

        if (pointsPoly.size() > 1) {

            Polygon myPoly = new Polygon();
            for (int i = 0; i < pointsPoly.size(); i++) {
                myPoly.addPoint(pointsPoly.get(i).x, pointsPoly.get(i).y);
            }
            g.setColor(color);

            if (filled) {
                g.fillPolygon(myPoly);
            } else
                g.drawPolygon(myPoly);

        }

    }

    @Override
    void subsequentPoint(Point p) {


    }
}