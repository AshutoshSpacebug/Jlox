package Scanner;

import java.util.ArrayList;
import java.util.List;

import static Scanner.TokenType.*;

public class Scanner {
    private final String source; // source represent encoded (UTF - 8) Data in String format
    private final List<Token> tokens = new ArrayList<>();

    /*
            start --> Represents the starting index of the current token being scanned in the source string.
            current -->  current position (index) in the source string — it moves character by character as you scan.

     */
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public Scanner(String source){
        this.source = source;
    }

    public List<Token> scanTokens(){
        while (!isAtEnd()){
            start = current;
            scanToken();
        }
        // EOF --> end of line
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        // current moves character by character in the source
        return current >= source.length();
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            case '/': addToken(SLASH); break;
        }
    }

    // advance returns each character in the source String
    private char advance() {
        current++;
        return source.charAt(current - 1);
    }
    private void addToken(TokenType type) {
        addToken(type, null);
    }
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
