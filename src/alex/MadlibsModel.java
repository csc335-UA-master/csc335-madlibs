package alex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MadlibsModel {
	private static final String POS_FILENAME = "C:\\Users\\alexa\\git\\csc335-madlibs\\parts_of_speech.txt";
	private static final String TEMPLATE_FILENAME = "C:\\Users\\alexa\\git\\csc335-madlibs\\templates.txt";

	private int maxPosition;
	private int guesses;
	private HashMap<String, String> posMap;
	private HashMap<Integer, String> guessMap;
	private String templateString;
	public MadlibsModel()
	{
	
		this.maxPosition = 0;
		this.posMap = this.makePosMap();
		this.generateTemplate();
		this.guessMap = this.makeGuessMap();
		this.guesses = 0;
	}
	
	private HashMap<String, String> makePosMap()
	{
		HashMap<String, String> posMap = new HashMap<String, String>();
		File f = new File(this.POS_FILENAME);
		Scanner fscanner = null;
		try {
			fscanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(fscanner.hasNextLine())
		{
			String line = fscanner.nextLine();
			String[] mapping = line.split("\t");
			posMap.put(mapping[0], mapping[1]);
		}
		
		return posMap;
	}
	private void generateTemplate() {
		// Picks random quote from the text file
		ArrayList<String> quotes = new ArrayList<String>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(this.TEMPLATE_FILENAME));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Adds lines in file to ArrayList
		quotes.add(scanner.nextLine());
		while (scanner.hasNextLine()) {
			quotes.add(scanner.nextLine());
		}
		Collections.shuffle(quotes);
		templateString = quotes.get(0).toUpperCase();
	
	}
	
	
	private HashMap<Integer, String> makeGuessMap()
	{
		HashMap<Integer, String> guessMap = new HashMap<Integer, String>();
		File f = new File(this.TEMPLATE_FILENAME);
		Scanner fscanner = null;
		try {
			fscanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> targetKeyValues = new ArrayList<String>();
		Pattern p = Pattern.compile("\\((.*?)\\)");
		String line = this.templateString;
		Matcher match= p.matcher(line);
		while (match.find() == true) {
			targetKeyValues.add(match.group(1));
			this.maxPosition += 1;
		}
		
		for (String line2: targetKeyValues) {
			String[] toBeMapped = line2.split(":");
			toBeMapped[1] = toBeMapped[1].trim(); // To get rid of leading whitespace
			guessMap.put(Integer.parseInt(toBeMapped[0]), toBeMapped[1]);
		}

		fscanner.close();
		return guessMap;
	}
	
	
	public String getTemplateString()
	{
		return this.templateString;
	}
	
	public String getPOS(int position)
	{
		return this.guessMap.get(position);
	}
	
	public void replace(int position, String replacement)
	{
		//System.out.println("(" + String.valueOf(position) + ": " + this.guessMap.get(position) + ")");
		this.templateString = this.templateString.replace("(" + String.valueOf(position) + ": " + this.guessMap.get(position) + ")", "(" + String.valueOf(position) + ": " + replacement.toUpperCase() + ")");
		this.guessMap.replace(position, replacement);
		this.guesses += 1;
	}

	public int getMaxPosition() {
		// TODO Auto-generated method stub
		return this.maxPosition;
	}
	
	public int getGuesses()
	{
		return this.guesses;
	}
	
	public HashMap<String, String> getPOSMap()
	{
		return this.posMap;
	}
		
		
}
