
package Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
        static boolean hadError = false;
        public static void main(String[] args) throws IOException {
            /*
                    Reasons To trigger IOException

                    File Not Found
                    Permission Denied
                    Path Is a Directory
                    File Is Too Large
                    Filesystem Errors ...

            */

            // agrs refer to commandLine inputs
            if (args.length > 1) {

                System.out.println("Usage: jlox [script]");

                // Terminates JVM with EXIT code 64 aka --> Command line usage error
                if (hadError) System.exit(64);
            }
            else if (args.length == 1) runFile(args[0]); // A single command line input
            else runPrompt();

        }
        private static void runFile(String path) throws IOException{
            // path is a CLI input from terminal converted to String

            // This function converts raw byte data to UTF-8 encoded String
            byte[] bytes = Files.readAllBytes(Paths.get(path)); // readAllBytes returns  a byte[];

            // Charset.defaultCharset() is responsible for UTF-8 encoding
            String source = new String(bytes, Charset.defaultCharset());
            run(source);
        }
        private static void runPrompt() throws IOException{
            // InputStreamReader uses Charset to encode Keyboard input (System.in) to UTF-8
            InputStreamReader input = new InputStreamReader(System.in);
            /*

                Reading from System.in character by character is slow.
                Each read might require a system call to the OS — expensive!

                BufferedReader: Reads a large block of characters once and stores them in memory.
                Future reads just pull from that in-memory buffer, which is fast.

            */

            BufferedReader reader = new BufferedReader(input);

            while (true){
                System.out.println("> ");
                String line = reader.readLine();
                if (line == null) break;
                run(line);
                hadError = false;
            }

        }

        private static void run(String source) {
            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.scanTokens();
                // For now, just print the tokens.
            for (Token token : tokens) {
                System.out.println(token);
            }
        }
        // Error Handling
        static void error(int line, String message) {
            report(line, "", message);
        }
        static void report(int line, String where, String message){
            System.out.println("[Line" + line + "] Error" + where + ": " + message);
        }
}
