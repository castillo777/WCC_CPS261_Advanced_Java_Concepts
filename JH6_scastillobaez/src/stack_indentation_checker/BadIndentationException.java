package stack_indentation_checker;

public class BadIndentationException extends RuntimeException {
    BadIndentationException(String error){
        super(error);
    }
}
