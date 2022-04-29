//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int openBracket = markdown.indexOf("[", currentIndex);
            if(openBracket == -1){
                if(toReturn.size() == 0){
                    throw new StringIndexOutOfBoundsException();
                }
                return toReturn;
            }
            int closeBracket = markdown.indexOf("]", openBracket);
            int openParen = markdown.indexOf("(", closeBracket);
            int closeParen = markdown.indexOf(")", openParen);
            if(markdown.substring(openParen + 1, closeParen).length() == 0){
                currentIndex = closeParen + 1;
            }
            else{
                toReturn.add(markdown.substring(openParen + 1, closeParen));
                currentIndex = closeParen + 1;  
            }
            for(int i = openBracket; i < closeBracket; i++) {
                int wrong = markdown.indexOf("(", closeBracket);
                int wrong2 = markdown.indexOf("[", currentIndex);
                if (wrong > -1 || wrong2 > -1){
                    System.out.println("Invalid link");
                    return toReturn;
                }
            }
            toReturn.add(markdown.substring(openParen + 1, closeParen));
            currentIndex = closeParen + 1;
        }

        return toReturn;
    }


    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
	    System.out.println(links);
    }
}
