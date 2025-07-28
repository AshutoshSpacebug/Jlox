package Scanner;
/*
        This Token class will be responsible for assigning
        an example implementation

        new Token(TokenType.EQUAL_EQUAL, "==", null, line)
        1] TokenType.EQUAL_EQUAL -- our enumeration from TokenType
        2] "==" -- the lexeme, the actual characters from the source
        3] null -- the literal value / the reference towards
        4] line -- line no. where the error occurred

 */
public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    public Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}
