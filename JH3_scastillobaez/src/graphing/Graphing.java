package graphing;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

class fileData {

    String firstLine;

    ArrayList<String> players = new ArrayList<>();
    ArrayList<String> rankings = new ArrayList<>();

    void read(String fileName) {

        Scanner fileReader = null;

        try {

            fileReader = new Scanner(new FileInputStream(fileName));
            firstLine = fileReader.nextLine();

            while (fileReader.hasNextLine()) {
                String newline = fileReader.nextLine();
                String[] splitData = newline.split(";");

                players.add(splitData[0]);
                rankings.add(splitData[1].replaceAll("\\s", ""));
            }

        } catch (Exception e) {

            System.out.println("Unable to read data from file with exception " + e);

        } finally {
            fileReader.close();
        }

    }

}

public class Graphing extends JFrame {

    ArrayList<String> players = new ArrayList<>();
    ArrayList<String> rankings = new ArrayList<>();

    Graphing(String firstLine, ArrayList<String> players, ArrayList<String> rankings) {
        setTitle(firstLine);
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.players = players;
        this.rankings = rankings;

    }

    int getMaxTextWidth(ArrayList<String> arr, FontMetrics fm) {
        int maxValue = 0;
        for (int i = 0; i < arr.size(); i++) {
            int width = fm.stringWidth(arr.get(i));
            if (width > maxValue)
                maxValue = width;
        }
        return maxValue;
    }

    ArrayList<Integer> intFromString(ArrayList<String> arrayList) {
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            int x = Integer.parseInt(arrayList.get(i));
            ints.add(x);
        }
        return ints;
    }

    public void paint(Graphics g) {
        super.paint(g);
        int myBorder = 10;
        Insets insets = getInsets();
        int top = insets.top;
        int bottom = insets.bottom;
        int left = insets.left;
        int right = insets.right;
        int verticalSpacing = 20;
        int yCoord;
        int drawingWidth = getWidth() - left - right - 1;
        int drawingHeight = getHeight() - top - bottom - 1;

        g.setColor(Color.red);
        //draw red border
        for (int i = 0; i < myBorder; i++) {
            g.drawRect(left + i, top + i, drawingWidth - 2 * i, drawingHeight - 2 * i);
        }

        Font arial = new Font("SansSerif", Font.BOLD, 12);
        g.setFont(arial);
        FontMetrics fm = getFontMetrics(arial);
        int fontHeight = fm.getHeight();
        int maxAscent = fm.getMaxAscent();
        int strMaxWidth = left + getMaxTextWidth(players, fm);

        ArrayList<Integer> barWidths = intFromString(rankings);
        int maxBarWidth = barWidths.get(0);
        int barHeight = fontHeight;
        int x_barStart = left+strMaxWidth+myBorder+1;

        double scale = (drawingWidth-x_barStart-right) / (double) maxBarWidth;

        g.setColor(Color.black);
        int y = top;

        //draw player names
        for (int i = 0; i < players.size(); i++) {
            String text = players.get(i);
            int strWidth = fm.stringWidth(text);
            g.drawString(text, strMaxWidth-strWidth+myBorder+left, y + maxAscent+myBorder);
            y += fontHeight + 10;
        }
        g.setColor(Color.green);
        g.drawLine(x_barStart-1,top+myBorder,left+strMaxWidth+myBorder,drawingHeight+myBorder);
        //draw bars

        y = top;
        for (int i = 0; i < barWidths.size(); i++) {
            int scaledWidth = ((int)scale*barWidths.get(i));

            g.fillRect(x_barStart,y+myBorder,scaledWidth,barHeight);
            y+=fontHeight+10;

        }

    }

    public static void main(String[] args) {

        fileData fd = new fileData();
        fd.read("graphing.txt");

        Graphing gui = new Graphing(fd.firstLine, fd.players, fd.rankings);


    }


}