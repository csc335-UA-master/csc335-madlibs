package alex;

import java.util.Scanner;

public class Madlibs {
	
	
	public static void main(String[] args)
	{
		MadlibsController controller = new MadlibsController();
		Scanner playscanner = new Scanner(System.in);
		
		System.out.println("Welcome to Madlibs, would you like to play? (Yes/No)");
		//while user is playing
		while(!playscanner.nextLine().equalsIgnoreCase("No"))
		{
			Scanner intscanner = new Scanner(System.in);
			Scanner scanner = new Scanner(System.in);
			while(!controller.gameOver())
			{
				int position = -1;
				//String replacement = "";
				printMessage(controller);
				System.out.println("Enter a position to replace: ");
				try {
					
					position = intscanner.nextInt();
					System.out.println("Enter a replacement \"" + controller.getPOS(position) + "\": ");
					String replacement = scanner.nextLine();
					controller.makeGuess(position, replacement);
				}
				catch(MadlibsIllegalPositionException e)
				{
					System.err.println("Invalid Position, please enter another position");
					continue; 
				}
				catch(MadlibsIllegalPOSException e)
				{
					System.err.println("Invalid Position, please enter another position");
					continue;
				}
				
			}
		}
		
		

	}
	
	private static void printMessage(MadlibsController c)
	{
		System.out.println(c.getTemplate());
	}
}
