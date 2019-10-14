package stack_indentation_checker;

public class Main {
    public static void main(String[] args) {
        for (String str : args)
            new IndentChecker(str).readFile();
    }
}
