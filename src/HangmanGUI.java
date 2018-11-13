import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.applet.*;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;



//Hangman GUI version class
public class HangmanGUI extends JFrame implements HangmanInterface {
    private HangmanGame hg;
    private JPanel gallow;
    private JLabel letter, board, message;
    private JTextField lettertf;
    private JButton submit, exit, restart, instructions;
    private WordList wordList;
    private Panel upper, lower;
    private JFrame f;
    private Applet song;
    
    
    //Button handler
    private SubmitButtonHandler submitHandler;
    private ExitButtonHandler exitHandler;
    private RestartButtonHandler restartHandler;
    private instructButtonHandler instructHandler;
    
    public HangmanGUI(WordList wordList) throws IOException {
	hg = new HangmanGame(wordList.getRandomLine());
	this.wordList = wordList;
    }
    
    public void play() {
	f = new JFrame();
	f.setSize(450, 400);
	Panel upper = new Panel();



	upper.setLayout(new BoxLayout(upper,BoxLayout.Y_AXIS));
	
     	gallow = new hanger();
    	message = new JLabel();
	board = new JLabel(hg.getBoard());
	letter = new JLabel("Guess a letter: ");
	lettertf = new JTextField(3);
	

	submit = new JButton("Submit");
	submitHandler = new SubmitButtonHandler();
	submit.addActionListener(submitHandler);
	f.getRootPane().setDefaultButton(submit); //Use "Enter key" as default for submit button.
	f.getContentPane().add(upper,BorderLayout.NORTH);	
	upper.add(gallow);
	upper.add(message);
	upper.add(board);
	upper.add(letter);
	upper.add(lettertf);
	upper.add(submit);
	submit.setAlignmentX(Component.CENTER_ALIGNMENT);	
	



	Panel lower = new Panel();
	lower.setLayout(new FlowLayout());

	instructions = new JButton("Instructions");
	instructHandler = new instructButtonHandler();
	instructions.addActionListener(instructHandler);

	exit = new JButton("Exit");
	exitHandler = new ExitButtonHandler();
	exit.addActionListener(exitHandler);
	
	restart = new JButton("Restart");
	restartHandler = new RestartButtonHandler();
	restart.addActionListener(restartHandler);

	f.getContentPane().add(lower,BorderLayout.SOUTH);

	lower.add(instructions);
	lower.add(exit);
	lower.add(restart);
	
	//Specify handlers for each button and add (register) ActionListeners to each button.
	
	//Add things to the pane in the order you want them to appear (left to right, top to bottom)
	
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	f.setVisible(true);
	f.setTitle("Hangman Game: GUI Version");
    }
    
    //Submit button
    public class SubmitButtonHandler implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    String word = lettertf.getText();
	    


	    lettertf.requestFocusInWindow();
	    
	    if(word.length() < 1) {
		message.setText("Must type something!");

		return;
	    } else if(word.length() > 1) {
		message.setText("Do not type more than one letter at a time.");
		
		lettertf.setText("");
		return;
	    }
	    
	    char letter = word.charAt(0);
	    lettertf.setText("");
	    
	    try{
		if(hg.guessLetter(letter) == true) {
		    message.setText("Good Guess!");
		  
		}
		else
	        {
		    message.setText("wrongAttemptsLeft left: " + hg.getWrongAttemptsLeft());
                     
		}

	    } catch(AlreadyTriedException ex) {
		message.setText("You have already tried that letter, please try another one.");
				

	    }
	    
	    board.setText(hg.getBoard());
	    
	    if(hg.hasWon()) {
		submit.setEnabled(false);
		message.setText("Congratulations, You have won!");
		

	    }
	    if(hg.hasLost()) {
		submit.setEnabled(false);
		message.setText("Sorry, you have lost!" + "  " + "The secret word was " + hg.getSecretWord() + ".  " + "Try again!");
	
			       
	    }
	    repaint();
	}
    }

    //Display a screen of instructions
    public class instructButtonHandler implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    JOptionPane.showMessageDialog(f,"Each * represents a letter in a word. Your task\nis to guess each letter in the word until you\ncomplete the word. Each wrong guess adds a \nbody part to the hanger. Once an entire\nman is created from wrong guesses\n(6 wrong guesses) then you lose the game.\nIf you guess the word before then, you win!","Instructions", JOptionPane.INFORMATION_MESSAGE);
	}
    }
    
    //Exit from the game
    public class ExitButtonHandler implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    System.exit(0);
	}
    }
    
    //Start the new game
    public class RestartButtonHandler implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    lettertf.requestFocusInWindow();
	    submit.setEnabled(true);
	    
	    try {
		hg = new HangmanGame(wordList.getRandomLine());
		board.setText(hg.getBoard());
		message.setText("");
		repaint();
	    } catch(IOException ex) {
		throw new RuntimeException(ex);
	    }
	}
    }
     
    //Drawing a hangman with a gallow
    public class hanger extends JPanel {
	hanger() {
	    setPreferredSize(new Dimension(150,150));
        }
	public void paint (Graphics g) {
	    g.setColor(Color.BLACK);
	    g.drawRect(10,70, 50, 5);
	    g.drawLine(35, 70, 35, 5);
	    g.drawLine(35,5,70,5);
	    
	    if(hg.getWrongAttemptsLeft() < 6)
		g.drawOval(60, 10, 20, 20);
	    if(hg.getWrongAttemptsLeft() < 5)
		g.drawLine(70, 30, 70, 60);
	    if(hg.getWrongAttemptsLeft() < 4)
		g.drawLine(70,60,50,65);
	    if(hg.getWrongAttemptsLeft() <3)
		g.drawLine(70,60,90,65);
	    if(hg.getWrongAttemptsLeft() <2)
		g.drawLine(70, 30, 50, 25);
	    if(hg.getWrongAttemptsLeft() <1)
		g.drawLine(70, 30, 90, 25);
	}
    }
}