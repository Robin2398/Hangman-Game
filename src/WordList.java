
import java.io.*;
import java.util.*;

public class WordList {
    private File wordListFile;

    public WordList(File wordListFile) {
	this.wordListFile = wordListFile;
    }
    
    //Pick a random word from counted lines
    public String getRandomLine() throws IOException {
        int lineCount = getNumberOfLines();
        int lineNumber = new Random().nextInt(lineCount);

        BufferedReader reader = new BufferedReader(new InputStreamReader(getWordListInputStream()));

        try {
            for(int i=0; i < lineNumber; i++) {
                reader.readLine();
            }
            return reader.readLine();
        } finally {
            reader.close();
        }
    }

    //Count the number of lines                                                                                                                     
    private int getNumberOfLines() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getWordListInputStream()));

        try {
            int count = 0;
            while(reader.readLine() != null) {
                count++;
            }
            return count;
        } finally {
            reader.close();
        }
    }

    private InputStream getWordListInputStream() throws FileNotFoundException {
        if(wordListFile == null) {
            // gets the WordsList.txt file from the class path                                                                                       
            return Main.class.getClassLoader().getResourceAsStream("resources/WordList.txt");
        } else {
            return new FileInputStream(wordListFile);
        }
    }
}
