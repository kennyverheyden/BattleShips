package battleships;
import java.util.Scanner;
import battleships.Field;

public class Game {

	public void initGame()
	{
		Field square = new Field(scanPlayerInputSquareSize()); // Initialization dynamic boardSquare
		square.printSquare();

	}
	
	
	
	// Ask player size for dynamic boardSquare
	public int scanPlayerInputSquareSize()
	{
		int squareSize=0;
		int maxSquareSize=10; // MAX SET = 25 due alphabet array coordinates
		int minSquareSize=3;
		boolean invalid= true;
		Scanner input = new Scanner(System.in);
		System.out.println("\nEnter the size of the ocean ["+minSquareSize+"-"+maxSquareSize+"]: ");

		do
		{
			// Checks that the input can be parsed as an int
			if(input.hasNextInt())
			{	
				squareSize=input.nextInt();
				input.nextLine();  // Advances the scanner to prevent input errors
				if(squareSize <= maxSquareSize && squareSize >= minSquareSize)
				{
					invalid=false;  // Sets the condition to false to break the loop
				}
				else
				{
					System.out.println("Size between "+ minSquareSize +" and "+maxSquareSize);
				}
			}
			else
			{
				System.out.println("Invalid input");
				input.nextLine(); // Advances the scanner to prevent input errors
			}
		}
		while(invalid); // The loop ends when the input is valid
		return squareSize;
	}
	
}
