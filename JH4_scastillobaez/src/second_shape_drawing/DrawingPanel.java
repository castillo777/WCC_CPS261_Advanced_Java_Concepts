package second_shape_drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel
{
    Drawing drawing = new Drawing();
    Image offScreenImage = null;
    Dimension screenDimension = null;

    DrawingPanel()
    {
        super();

        MyMouseHandler mmh = new MyMouseHandler();
        addMouseListener(mmh);
        addMouseMotionListener(mmh);
    }

    class MyMouseHandler extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            drawing.mousePressed(e.getPoint());
            repaint();
        }
        public void mouseReleased(MouseEvent e)
        {
            drawing.mouseReleased(e.getPoint());
            repaint();
        }
        public void mouseDragged(MouseEvent e)
        {
            drawing.mouseDragged(e.getPoint());
            repaint();
        }
        public void mouseClicked(MouseEvent e)
        {
            drawing.mouseClicked(e.getPoint());
            repaint();
        }
    }


    public void paint(Graphics screen)
    {
        Dimension dimen = getSize();

        // If the buffer image doesn't exist or the screen size changed
        if (offScreenImage == null || !dimen.equals(screenDimension))
        {
            // Create a new image of the needed size
            screenDimension = dimen;
            offScreenImage = createImage(dimen.width, dimen.height);
        }

        // Get a graphics object representing the buffer image
        Graphics g = offScreenImage.getGraphics();

        // Fill background
        g.setColor(Color.white);
        g.fillRect(0, 0, dimen.width, dimen.height);

        // Draw shapes
        drawing.draw(g);

        // Draw the buffer image to the screen
        screen.drawImage(offScreenImage, 0, 0, this);
    }
}