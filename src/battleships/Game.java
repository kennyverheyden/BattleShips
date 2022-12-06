package battleships;
import java.util.Random;
import java.util.Scanner;
import battleships.Field;

public class Game {

	int mMaxSquareSize;
	int mMinSquareSize;
	int mPointsPlayer;
	int mPointsComputer;
	int mMaxShips;

	public Game()
	{
		mMaxSquareSize=15; // MAX SET = 25 due alphabet array coordinates
		mMinSquareSize=8;
		mPointsPlayer=5;
		mPointsComputer=5;
		mMaxShips=4;
	}

	public void initGame()
	{
		Field square = new Field(scanPlayerInputSquareSize(mMaxSquareSize, mMinSquareSize)); // Initialization dynamic boardSquare
		Player human = new Player(mMaxShips,mPointsPlayer);
		Player computer = new Player(mMaxShips,mPointsPlayer);
		square.printSquare();
		setPlayerStartCoordinates(square,human);
	}

	public String computerChoseCoordinate(Field square, Player player)
	{
		boolean duplicateField = false;
		StringBuilder sb = new StringBuilder();
		Random rn = new Random();
		String computerChoice;
		int randomX;
		int randomY;
		do
		{
			randomX=rn.nextInt(square.getmSquareArr().length);
			randomY=(rn.nextInt(square.getmSquareArr().length))+1;
			sb.append(square.getmLettersArr()[randomX]);
			sb.append(randomY);
			computerChoice=sb.toString();
			sb.setLength(0);


			int i=0;
			while(i<player.getmCoordinates().length)
			{
				if(player.getmCoordinates()[i].equals(computerChoice))
				{

					System.out.println("Computer chosed duplicate");
					duplicateField=true;
					break;
				}
				else
				{
					duplicateField=false;
				}
				i++;
			}


		}
		while(duplicateField);
		return computerChoice;
	}

	public void setPlayerStartCoordinates(Field square, Player player)
	{
		for(int i=0;i<player.getmCoordinates().length;i++)
		{
			String shipCoordinate;
			shipCoordinate=scanPlayerShipCoordinate(square, player);

			i=setCoordinate(square,player,shipCoordinate,i);

		}

		for(int i=0;i<player.getmCoordinates().length;i++)
		{
			System.out.println(player.getmCoordinates(i));
		}
	}


	public int setCoordinate(Field square,Player player,String shipCoordinate, int i)
	{
		String direction="null";
		int coordinateNumber=0;
		int number=Integer.parseInt(shipCoordinate.replaceAll("[^0-9]", "")); 
		if(number<10) {
			direction =String.valueOf(shipCoordinate.charAt(2)); // get direction
			coordinateNumber=Integer.parseInt(String.valueOf(shipCoordinate.charAt(1)));
		}else {
			direction =String.valueOf(shipCoordinate.charAt(3));
			coordinateNumber=Integer.parseInt(String.valueOf(shipCoordinate.charAt(1))+String.valueOf(shipCoordinate.charAt(2)));
		}
		String letter =String.valueOf(shipCoordinate.charAt(0)); 

		if(direction.equals("h"))
		{	
			for(int j=0;j<3;j++)
			{
				player.setmCoordinates(letter+coordinateNumber+direction, i);
				coordinateNumber++;
				i++;
			}
			i--;
		}
		else
		{
			int letterPosition=0;
			while(letterPosition<square.getmLettersArr().length)
			{
				if(square.getmLettersArr()[letterPosition].equals(letter))
				{		
					break;	
				}
				letterPosition++;
			}
			for(int j=0;j<3;j++)
			{
				player.setmCoordinates(square.getmLettersArr()[letterPosition]+coordinateNumber+direction, i);
				i++;
				letterPosition++;
			}
			i--;
		}
		return i;
	}

	public boolean checkDuplicate(Player player, String shipcoordinate)
	{
		boolean duplicate=false;
		int i=0;
		while(i<player.getmCoordinates().length)
		{
			if(shipcoordinate.equals(player.getmCoordinates(i)))
			{
				duplicate=true;
				break;
			}
			else
			{
				duplicate=false;
			}
			i++;
		}
		return duplicate;
	}

	public void updateSquare(Field square, String shipCoordinate)
	{
		String letter = String.valueOf(shipCoordinate.charAt(0)); // get letter coordinate
		String direction;
		int directionVertical=0; // dynamic, preserve place (fields) for ship more than one field
		int directionHorizontal=0; // dynamic
		int number;
		number=(Integer.parseInt((shipCoordinate.replaceAll("[^0-9]", "")))); //get coordinate number
		if(number<10) {
			direction =String.valueOf(shipCoordinate.charAt(2)); // get direction
		}else {
			direction =String.valueOf(shipCoordinate.charAt(3));
		}

		for (int i = 0; i < square.getmSquareArr().length; i++) {
			for (int j = 0; j < square.getmSquareArr().length; j++)
			{

			}
		}
	}

	//Ask player ship coordinates
	public String scanPlayerShipCoordinate(Field square, Player player)
	{
		Scanner input = new Scanner(System.in);
		String shipCoordinate=null;;
		String shipDirection = null;
		String[] letterArr = square.getmLettersArr();
		boolean validInput=false;

		do {
			System.out.println("Enter coordinate between A1 and "+(letterArr[square.getmSquareArr().length-1]).toUpperCase()+""+square.getmSquareArr().length +" H=horizontal/V=vertical e.g. [A1V]:");
			try
			{
				shipCoordinate=input.nextLine().toLowerCase().toString();
				validInput=true;
				if(validateCoordinate(square, shipCoordinate))
				{
					if(checkDuplicate(player,shipCoordinate))
					{
						System.out.println("Coordinate already used");
						validInput=false;
					}
					else
					{
						return shipCoordinate;
					}

				}
				else
				{
					System.out.println("Coordinate out of range");
					validInput=false;
				}
			}
			catch(Exception e){
				System.out.println("Enter valid coordinate\n");
				validInput=false;
			}
		}while(!validInput);
		return shipCoordinate;
	}


	public boolean validateCoordinate(Field square, String input)
	{	
		String[] lettersArr=square.getmLettersArr();
		String letter = String.valueOf(input.charAt(0)); // get letter coordinate
		String direction;
		int directionVertical=0; // dynamic, preserve place (fields) for ship more than one field
		int directionHorizontal=0; // dynamic
		int number;
		int i=0;
		boolean check=false;
		number=(Integer.parseInt((input.replaceAll("[^0-9]", "")))); //get coordinate number
		if(number<10) {
			direction =String.valueOf(input.charAt(2)); // get direction
		}else {
			direction =String.valueOf(input.charAt(3));
		}

		if(direction.equals("v")) // preserve place (fields) for ship more than one field
		{
			directionVertical=2;  // for substraction of fields from max square vertical
		}
		if(direction.equals("h"))
		{
			directionHorizontal=2; // for substraction of fields from max square horizontal
		}

		if(direction.equals("h") || direction.equals("v")) // Valid input of direction of ship
		{
			while(i<square.getmSquareArr().length-directionVertical)
			{
				if(lettersArr[i].equals(letter))
				{
					if(number <= (square.getmSquareArr().length-directionHorizontal))
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
			check=false;
			System.out.println("Enter valid direction after coordinate");
		}
		return check;
	}

	public int scanPlayerInputSquareSize(int maxSquareSize, int minSquareSize)
	{
		// Ask player size for dynamic boardSquare
		int squareSize=0;
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
