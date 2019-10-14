package calculator_applet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.applet.Applet;

public class CalculatorApplet extends Applet implements ActionListener {

    protected JFrame calWin;
    protected JPanel keyPad;
    protected JPanel panel;
    protected JTextField show;
    protected JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
    protected JButton add, sub, mul, div, com, clr;
    protected double o1, o2;
    protected char op;

    public CalculatorApplet() {
        clean();
    }

    public void clean() {
        o1 = 0;
        o2 = 0;
        op = ' ';
    }

    public void createUserInterface() {
        calWin = new JFrame("Calculator");

        keyPad = new JPanel();
        keyPad.setLayout(new GridLayout(4, 4, 4, 4));

        show = new JTextField(15);
        show.setEditable(false);
        show.setBackground(Color.YELLOW);
        show.setFont(new Font("SERIF", Font.PLAIN, 32));

        b0 = new JButton("0");
        b0.setBackground(Color.CYAN);
        b0.setFont(new Font("SERIF", Font.PLAIN, 32));

        b1 = new JButton("1");
        b1.setBackground(Color.CYAN);
        b1.setFont(new Font("SERIF", Font.PLAIN, 32));

        b2 = new JButton("2");
        b2.setBackground(Color.CYAN);
        b2.setFont(new Font("SERIF", Font.PLAIN, 32));

        b3 = new JButton("3");
        b3.setBackground(Color.CYAN);
        b3.setFont(new Font("SERIF", Font.PLAIN, 32));

        b4 = new JButton("4");
        b4.setBackground(Color.CYAN);
        b4.setFont(new Font("SERIF", Font.PLAIN, 32));

        b5 = new JButton("5");
        b5.setBackground(Color.CYAN);
        b5.setFont(new Font("SERIF", Font.PLAIN, 32));

        b6 = new JButton("6");
        b6.setBackground(Color.CYAN);
        b6.setFont(new Font("SERIF", Font.PLAIN, 32));

        b7 = new JButton("7");
        b7.setBackground(Color.CYAN);
        b7.setFont(new Font("SERIF", Font.PLAIN, 32));

        b8 = new JButton("8");
        b8.setBackground(Color.CYAN);
        b8.setFont(new Font("SERIF", Font.PLAIN, 32));

        b9 = new JButton("9");
        b9.setBackground(Color.CYAN);
        b9.setFont(new Font("SERIF", Font.PLAIN, 32));

        add = new JButton("+");
        add.setBackground(Color.RED);
        add.setFont(new Font("SERIF", Font.PLAIN, 32));

        sub = new JButton("-");
        sub.setBackground(Color.RED);
        sub.setFont(new Font("SERIF", Font.PLAIN, 32));

        mul = new JButton("*");
        mul.setBackground(Color.RED);
        mul.setFont(new Font("SERIF", Font.PLAIN, 32));

        div = new JButton("/");
        div.setBackground(Color.RED);
        div.setFont(new Font("SERIF", Font.PLAIN, 32));

        com = new JButton("=");
        com.setBackground(Color.RED);
        com.setFont(new Font("SERIF", Font.PLAIN, 32));

        clr = new JButton("Clear");
        clr.setBackground(Color.RED);
        clr.setFont(new Font("SERIF", Font.PLAIN, 20));

        calWin.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        panel = new JPanel();
        panel.setSize(300, 300);
        BorderLayout layout = new BorderLayout();
        layout.setHgap(10);
        layout.setVgap(4);
        panel.setLayout(layout);

        keyPad.add(b0);
        keyPad.add(b1);
        keyPad.add(b2);
        keyPad.add(b3);
        keyPad.add(b4);
        keyPad.add(b5);
        keyPad.add(b6);
        keyPad.add(b7);
        keyPad.add(b8);
        keyPad.add(b9);
        keyPad.add(add);
        keyPad.add(sub);
        keyPad.add(mul);
        keyPad.add(div);
        keyPad.add(com);
        keyPad.add(clr);

        panel.add(show, BorderLayout.NORTH);
        panel.add(keyPad, BorderLayout.CENTER);

        calWin.getContentPane().add(panel);
        calWin.setBackground(Color.lightGray);
        calWin.setSize(400, 400);
        calWin.setVisible(true);

        b0.addActionListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        add.addActionListener(this);
        sub.addActionListener(this);
        mul.addActionListener(this);
        div.addActionListener(this);
        com.addActionListener(this);
        clr.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b0) {
            show.setText(show.getText() + "0");
        }

        if (e.getSource() == b1) {
            show.setText(show.getText() + "1");
        }

        if (e.getSource() == b2) {
            show.setText(show.getText() + "2");
        }

        if (e.getSource() == b3) {
            show.setText(show.getText() + "3");
        }

        if (e.getSource() == b4) {
            show.setText(show.getText() + "4");
        }

        if (e.getSource() == b5) {
            show.setText(show.getText() + "5");
        }

        if (e.getSource() == b6) {
            show.setText(show.getText() + "6");
        }

        if (e.getSource() == b7) {
            show.setText(show.getText() + "7");
        }

        if (e.getSource() == b8) {
            show.setText(show.getText() + "8");
        }

        if (e.getSource() == b9) {
            show.setText(show.getText() + "9");
        }

        if (e.getSource() == add) {
            o1 = Double.parseDouble(show.getText());
            op = '+';
            show.setText("");
        }

        if (e.getSource() == sub) {
            if (show.getText().trim().equals("")) {
                show.setText("-");
            } else {
                o1 = Double.parseDouble(show.getText());
                op = '-';
                show.setText("");
            }
        }
        if (e.getSource() == mul) {
            o1 = Double.parseDouble(show.getText());
            op = '*';
            show.setText("");
        }

        if (e.getSource() == div) {
            o1 = Double.parseDouble(show.getText());
            op = '/';
            show.setText("");
        }

        if (e.getSource() == com) {
            o2 = Double.parseDouble(show.getText());
            calculate();
        }

        if (e.getSource() == clr) {
            show.setText("");
            clean();
        }
    }

    public void calculate() {
        switch (op) {

            case '+':
                show.setText(new Double(o1 + o2).toString());
                break;
            case '-':
                show.setText(new Double(o1 - o2).toString());
                break;
            case '*':
                show.setText(new Double(o1 * o2).toString());
                break;
            case '/':
                show.setText(new Double(o1 / o2).toString());
                break;
        }
    }
    public void paint(Graphics g) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        CalculatorApplet c;
        c = new CalculatorApplet();
        c.createUserInterface();
    }
}