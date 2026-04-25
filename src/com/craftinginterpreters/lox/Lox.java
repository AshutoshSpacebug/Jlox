package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    static boolean hadError = false; // indicates error status
    public static void main(String[] args) throws IOException {
        /*
                    Reasons To trigger IOException

                    File Not Found
                    Permission Denied
                    Path Is a Directory
                    File Is Too Large
                    Filesystem Errors ...

         */

        if (args.length > 1) {                          // command line arguments of the string array exceeds 1
            System.out.println("Usage: jlox [script]");
            System.exit(64);                      // terminates the JVM aka below script won't run
        } else if (args.length == 1) {                  // command line arguments of the string array equal to 1
            runFile(args[0]);                           // call runFile method for the 1st argument
        } else {
            runPrompt();
        }
    }
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        /*
        1] Converts a String file path → a Path object
        2] Reads the entire file content into memory as a byte array
        */
        run(new String(bytes, Charset.defaultCharset())); // Charset.defaultCharset() returns the default character encoding used by the JVM on the current system
        if (hadError) System.exit(65);
    }

    /* Standard Buffer reader loop aka it reads user input line-by-line from the keyboard till line == null aka till we reach end of line  */
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }
    private static void run(String source) {            // for runPrompt
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        // For now, just print the tokens.
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    static void error(int line, String message) {       // prints the line on which error has occured along with a message
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
