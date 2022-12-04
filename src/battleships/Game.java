package battleships;
import java.util.Scanner;
import battleships.Field;

public class Game {

	public void initGame()
	{
		Field square = new Field(scanPlayerInputSquareSize()); // Initialization dynamic boardSquare
		square.printSquare();
		String shipCoordinate=scanPlayerShipCoordinate();
		System.out.println(shipCoordinate);

	}

	//Ask player ship coordinates
	public String scanPlayerShipCoordinate()
	{
		Scanner input = new Scanner(System.in);
		String shipCoordinate;
		String shipDirection = null;
		String[] letterArr = Field.getmLettersArr();
		boolean validInput=false;

		do {
			System.out.println("Enter coordinate between A1 and "+(letterArr[Field.getmSquareArr().length-1]).toUpperCase()+""+Field.getmSquareArr().length +" H=horizontal/V=vertical e.g. [A1V]:");
			try
			{
				shipCoordinate=input.nextLine().toLowerCase().toString();
				validInput=true;

				if(validateCoordinate(shipCoordinate))
				{
					return shipCoordinate;

				}
				else
				{
					System.out.println("Coordinate not in range");
					validInput=false;
				}
			}
			catch(Exception e){
				System.out.println("Enter valid coordinate\n");
				validInput=false;
			}
		}while(!validInput);
		return shipDirection;

	}


	public boolean validateCoordinate(String input)
	{	
		String[] lettersArr=Field.getmLettersArr();
		String letter = String.valueOf(input.charAt(0)); // get letter coordinate
		String direction =String.valueOf(input.charAt(2)); // get direction
		int directionVertical=0; // dynamic, preserve place (fields) for ship more than one field
		int directionHorizontal=0; // dynamic
		int number = (Integer.parseInt(String.valueOf(input.charAt(1))))-1; // get number coordinate
		int i=0;
		boolean check=false;
		boolean validDirection=false;
		if(direction.equals("v")) // preserve place (fields) for ship more than one field
		{
			directionVertical=2;  // for substraction of fields from max square vertical
		}
		if(direction.equals("h"))
		{
			directionHorizontal=2; // for substraction of fields from max square horizontal
		}

		while(!validDirection)
		{
			if(direction.equals("h") || direction.equals("v")) // Valid input of direction of ship
			{
				validDirection=true;
				while(i<Field.getmSquareArr().length-directionVertical)
				{
					if(lettersArr[i].equals(letter))
					{
						if(number < (Field.getmSquareArr().length-directionHorizontal))
						{
							check=true;
							break;
						}
					}
					else
					{
						check=false;
					}
					i++;
				}
			}
			else
			{
				validDirection=false;
			}
		}
		return check;
	}

	public int scanPlayerInputSquareSize()
	{
		// Ask player size for dynamic boardSquare

		int squareSize=0;
		int maxSquareSize=10; // MAX SET = 25 due alphabet array coordinates
		int minSquareSize=5;
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
