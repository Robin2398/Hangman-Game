import java.io.*;
import java.util.*;

// This handles a game session (with just one word).
public class HangmanGame {
    private ArrayList<Character> wrongLetters = new ArrayList<Character>(0);
    private int wrongAttemptsLeft = 6;
    private String secretWord;
    private boolean [] letters;

    public HangmanGame(String secretWord) {
        this.secretWord = secretWord;
        this.letters = new boolean[secretWord.length()];
        Arrays.fill(letters, false);

    }

    public boolean guessLetter(char letter) throws AlreadyTriedException {
        boolean letterIsInWord = secretWord.indexOf((int) letter) != -1;

        if(letterIsInWord) {
            flipLetters(letter);
            return true;
        } else {
            if(wrongLetters.contains(letter))  {
		throw new AlreadyTriedException();
            } else {
                wrongLetters.add(letter);
                wrongAttemptsLeft--;
		return false;
            }
        }
    }

    // flips * to letters
    private void flipLetters(char letter) {
        for(int i = 0; i < secretWord.length(); i++) {
            if(letter == secretWord.charAt(i))
                letters[i] = true;
        }
    }
    
    //Make a word with * and if player gets right, it reveals the letters. 
    public String getBoard() {
        String revealedLetters = "";

        for(int i = 0; i < secretWord.length(); i++) {
            if(letters[i] == false)
                revealedLetters += "*";
            else
                revealedLetters += secretWord.substring(i, i+1);
        }
        return revealedLetters;
    }

    //Check if all the values are True or not 
    public boolean hasWon() {
        for(int i = 0; i < letters.length; i++) {
            if(!letters[i])
                return false;
        }
        return true;
    }

    //Check if you lost the game
    public boolean hasLost() {
        return wrongAttemptsLeft == 0;
    }

    public int getWrongAttemptsLeft() {
        return wrongAttemptsLeft;
    }

    public String getSecretWord() {
	return secretWord;
    }
}
