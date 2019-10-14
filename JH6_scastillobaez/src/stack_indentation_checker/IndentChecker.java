package stack_indentation_checker;

import java.io.*;
import java.util.Stack;

public class IndentChecker {
    private BufferedReader reader;
    private Stack<Integer> indentStack = new Stack<Integer>();
    private String fileName;

    public IndentChecker(String fileName) {
        this.fileName = fileName;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            this.reader = reader;
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }


    public void readFile() {
        int count = 1;
        try {
            String line;
            while ((line = this.reader.readLine()) != null) {
                System.out.println(count + ": " + line);
                processLine(line,count);
                count++;
            }
            System.out.println("***************"+this.fileName + " is indented properly.");
        } catch (Exception e) {
            System.out.println("Error - " + e);
        }
    }

    private void processLine(String line, int lineNumber){
        int index = findFirstNonBlankLine(line);

        if (index == -1)
            return;
        if (this.indentStack.size() == 0) {
            this.indentStack.push(index);
            return;
        }
        if (index > this.indentStack.peek()) {
            this.indentStack.push(index);
            return;
        }
        while(this.indentStack.peek() > index)
            this.indentStack.pop();
        if(this.indentStack.peek() != index){
            throw new BadIndentationException("Bad indentation at line: " + String.valueOf(lineNumber));
        }

    }

    private int findFirstNonBlankLine(String line) {
        int index = 0;
        for (char a : line.toCharArray()) {
            if (a != ' ')
                return index;
            index++;
        }
        return -1;
    }
}