import java.io.*;
import java.util.*;
import java.awt.*;

// This is a wrapper around HangmanGame that can choose random words from a file.
public class Main {
    private static File wordListFile; // null if using default

    //main method that would run the game
    public static void main(String [] args) throws IOException, InterruptedException {

        if(args.length == 1) {
            wordListFile = new File(args[0]);
        } else {
            wordListFile = null;
        }

	WordList wordList = new WordList(wordListFile);
	    String secretWord = wordList.getRandomLine();
	    HangmanGame hg = new HangmanGame(secretWord);
	    new HangmanGUI(wordList).play();
    }
}
