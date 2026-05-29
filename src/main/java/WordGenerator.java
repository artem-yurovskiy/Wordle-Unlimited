import java.io.BufferedReader; 
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class WordGenerator {
	ArrayList<String> wordleWords;
	
	public WordGenerator() {
		wordleWords = new ArrayList<>();
	}
	
	public String chooseRandomWord() {
		Random randomWordGenerator = new Random();
		
		return wordleWords.get(randomWordGenerator.nextInt(wordleWords.size()));
		
	}
	
	public void createWordsList() {
		
		InputStream input = getClass().getResourceAsStream("/WordleWords.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		
		String currentLine;
		try { 
			while ((currentLine = br.readLine()) != null) {
				String[] cl = currentLine.split(" ");
				wordleWords.add(cl[5]);
			}
		} catch (Exception e) {
			
		}
		
	}

}
