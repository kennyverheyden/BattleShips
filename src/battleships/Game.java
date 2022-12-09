package battleships;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import battleships.Field;

public class Game {

	int mMaxSquareSize;
	int mMinSquareSize;
	int mPointsPlayer;
	int mMaxShips;

	public Game()
	{
		mMaxSquareSize=15; // MAX SET = 25 due alphabet array coordinates
		mMinSquareSize=7;
		mMaxShips=4;
	}

	public void initGame()
	{
		Field square = new Field(scanPlayerInputSquareSize(mMaxSquareSize, mMinSquareSize)); // Initialization dynamic boardSquare
		Player human = new Player(mMaxShips,"human");
		Player computer = new Player(mMaxShips,"computer");
		square.printSquare();
		setPlayerStartCoordinates(square,human);
		setPlayerStartCoordinates(square,computer);
		System.out.println("Human: "+ Arrays.deepToString(human.getmCoordinates()));
		System.out.println("Computer: "+ Arrays.deepToString(computer.getmCoordinates()));
		updateSquareStart(square, human, computer);
		square.printSquare();
		System.out.println("Ships left player: "+human.getmPoints());
		System.out.println("Ships left computer: "+computer.getmPoints());
		play(square, human, computer);
		System.out.println("");
	}

	public void play(Field square, Player human, Player computer) {

		while(human.getmPoints()>0 && computer.getmPoints()>0)
		{
			shoot(square,computer,scanPlayerShootCoordinate(square, computer));
			shoot(square,human,computerShoot(square));
			square.printSquare();
			System.out.println("Your ships left: "+ human.getmPoints());
			System.out.println("Computer ships left: "+computer.getmPoints());
		}

		if(human.getmPoints()>0)
		{
			System.out.println("You destroyed all the ships in the ocean");
		}
		else
		{
			System.out.println("You losed all your ships");
		}
	}

	public String computerChoseCoordinate(Field square, Player player)
	{
		StringBuilder sb = new StringBuilder();
		Random rn = new Random();
		String computerChoice=null;;
		String[] directions= {"h","v"};
		boolean validChose=false;
		boolean duplicate=false;
		int randomX;
		int randomY;
		int randomD;

		while(!validChose)
		{
			do
			{
				randomX=rn.nextInt(square.getmSquareArr().length);
				randomY=(rn.nextInt(square.getmSquareArr().length))+1;
				randomD=(rn.nextInt(directions.length));
				sb.append(square.getmLettersArr()[randomX]);
				sb.append(randomY);
				sb.append(directions[randomD]);
				computerChoice=sb.toString();
				sb.setLength(0);
				duplicate=checkDuplicate(player, computerChoice);
			}
			while(duplicate);

			validChose=validateCoordinate(square,computerChoice);
		}
		return computerChoice;
	}

	public void setPlayerStartCoordinates(Field square, Player player)
	{
		for(int i=0;i<player.getmCoordinates().length;i++)
		{
			String shipCoordinate;
			if(player.getmIdName().equals("human"))
			{
				shipCoordinate=scanPlayerShipCoordinate(square, player);
			}
			else
			{
				shipCoordinate=computerChoseCoordinate(square, player);
			}
			i=setCoordinate(square,player,shipCoordinate,i);
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
			// we need to split up the String, to mark the other fields horizontal with dynamic number coordinate
			for(int j=0;j<3;j++)
			{
				player.setmCoordinates(letter+coordinateNumber+direction, i, j);
				coordinateNumber++;
			}
		}
		else
		{
			// we need to split up the String, to mark the other fields vertical with dynamic letter coordinate
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
				player.setmCoordinates(square.getmLettersArr()[letterPosition]+coordinateNumber+direction, i, j);
				letterPosition++;
			}
		}
		return i;
	}

	public boolean checkDuplicate(Player player, String shipCoordinate)
	{
		boolean duplicate=false;
		String listedField;
		String fieldCoordinate;

		for(int i=0;i<player.getmCoordinates().length;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(player.getmCoordinates()[i][j]!=null) // avoid error on first input while coordinate list is empty
				{
					listedField=player.getmCoordinates()[i][j].substring(0, player.getmCoordinates()[i][j].length()-1);
					fieldCoordinate=shipCoordinate.substring(0, shipCoordinate.length()-1);
					if(listedField.equals(fieldCoordinate))
					{
						duplicate=true;
					}
				}
			}
		}
		return duplicate;
	}

	public void updateSquareStart(Field square, Player human, Player computer)
	{
		String coordinateHuman; // for storing coordinate without the h or v in the string for processing
		String coordinateComputer;

		for (int humanI = 0; humanI < human.getmCoordinates().length; humanI++) {
			for (int humanJ = 0; humanJ < 3; humanJ++)
			{
				for(int computerI=0;computerI<computer.getmCoordinates().length;computerI++) {
					for(int computerJ=0;computerJ<3;computerJ++)
					{
						//remove the h or the v from the coordinate for processing
						coordinateHuman=human.getmCoordinates()[humanI][humanJ].substring(0, human.getmCoordinates()[humanI][humanJ].length()-1);
						coordinateComputer=computer.getmCoordinates()[computerI][computerJ].substring(0, computer.getmCoordinates()[computerI][computerJ].length()-1);

						// ships on same field (collision)
						if(coordinateHuman.equals(coordinateComputer))
						{
							//now read the occupied fields of this ship from the player coordinate list
							for (int x = 0; x < 3; x++) // read from the first field x of the ship
							{
								// get coordinate without h or v in the string for compairing in field square
								coordinateHuman=human.getmCoordinates()[humanI][x].substring(0, human.getmCoordinates()[humanI][x].length()-1);
								coordinateComputer=computer.getmCoordinates()[computerI][x].substring(0, computer.getmCoordinates()[computerI][x].length()-1);

								human.getmCoordinates()[humanI][x]="@";
								computer.getmCoordinates()[computerI][x]="@";

								// mark on field, the listed coordinates from this ship
								for (int iSquare = 0; iSquare < square.getmSquareArr().length; iSquare++) {
									for (int  jSquare = 0; jSquare < square.getmSquareArr().length; jSquare++)
									{
										// compaire with fields on the square
										if(square.getmSquareArr()[iSquare][jSquare].equals(coordinateHuman) || square.getmSquareArr()[iSquare][jSquare].equals(coordinateComputer))
										{

											if(jSquare<10) // preserved space for formatting
											{
												square.getmSquareArr()[iSquare][jSquare]="@@"; // formatting
											}
											else
											{
												square.getmSquareArr()[iSquare][jSquare]="@@@"; // formatting
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		// count hitted fields human
		for(int i=0; i<human.getmCoordinates().length;i++)
		{
			int count=0;
			for (int j=0; j<3; j++) {
				if(human.getmCoordinates()[i][j].equals("@"))
				{
					count++;
					if(count==3)
					{
						human.setmPoints();
					}
				}
			}
		}
		// count hitted fields computer
		for(int i=0; i<computer.getmCoordinates().length;i++)
		{
			int count=0;
			for (int j=0; j<3; j++) {
				if(human.getmCoordinates()[i][j].equals("@"))
				{
					count++;
					if(count==3)
					{

						computer.setmPoints();
					}
				}
			}
		}
	}

	public void shoot(Field square, Player player, String shipCoordinate)
	{
		String coordinate;
		String listedField; // temp storage from player coordinates array
		boolean hit=false;

		for(int i=0;i<player.getmCoordinates().length;i++)
		{
			for (int j = 0; j < 3; j++) {
				coordinate=player.getmCoordinates()[i][j].substring(0, player.getmCoordinates()[i][j].length()-1);
				if(coordinate.equals(shipCoordinate)) {
					hit=true;

					for(int x=0;x<3;x++)
					{
						listedField=player.getmCoordinates()[i][x].substring(0, player.getmCoordinates()[i][x].length()-1);
						player.getmCoordinates()[i][x]="@";
						for (int iSquare = 0; iSquare < square.getmSquareArr().length; iSquare++) {
							for (int  jSquare = 0; jSquare < square.getmSquareArr().length; jSquare++)
							{
								if(square.getmSquareArr()[iSquare][jSquare].equals(listedField))
								{
									if(jSquare<10)
									{
										square.getmSquareArr()[iSquare][jSquare]="@@"; // formatting
									}
									else
									{
										square.getmSquareArr()[iSquare][jSquare]="@@@"; // formatting
									}
								}
							}
						}
					}
				}
			}
		}
		if(hit)
		{
			player.setmPoints();
			if(!player.getmIdName().equals("computer"))
			{
				System.out.println("The computer toke one of your ships to sink");
			}
			else
			{
				System.out.println("You HIT a ship");
			}
		}
		else
		{
			if(!player.getmIdName().equals("computer"))
			{
				System.out.println("The computer missed");
			}
			else
			{
				System.out.println("You missed");
			}
		}
	}
	public String computerShoot(Field field)
	{
		StringBuilder sb = new StringBuilder();
		Random rn = new Random();
		int randomX=rn.nextInt(field.getmSquareArr().length);
		sb.append(field.getmLettersArr()[randomX]);
		sb.append(randomX);
		String computerChoice=sb.toString();
		return computerChoice;
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
			System.out.println("Enter coordinate between A1 and "+(letterArr[square.getmSquareArr().length-1]).toUpperCase()+""+square.getmSquareArr().length +" AND direction h=horizontal/v=vertical e.g. [A1h]:");
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

	//Ask player ship coordinates
	public String scanPlayerShootCoordinate(Field square, Player targetPlayer)
	{
		Scanner input = new Scanner(System.in);
		String shipCoordinate=null;;
		String shipDirection = null;
		String[] letterArr = square.getmLettersArr();

		boolean validInput=false;

		do {
			System.out.println("Enter coordinate between A1 and "+(letterArr[square.getmSquareArr().length-1]).toUpperCase()+""+square.getmSquareArr().length+" without direction e.g. [A1]: ");
			try
			{
				shipCoordinate=input.nextLine().toLowerCase().toString();
				String letter = String.valueOf(shipCoordinate.charAt(0)); 
				int number=(Integer.parseInt((shipCoordinate.replaceAll("[^0-9]", "")))); //get coordinate number
				for(int i=0;i<square.getmSquareArr().length;i++)
				{
					if(letterArr[i].equals(letter))
					{
						if(number<=square.getmSquareArr().length)
						{
							if(square.getmLettersArr().length>9)
							{
								if(shipCoordinate.length()>2)
								{
									System.out.println("Invalid input, please enter only coordinate e.g A1");
								}
								else
								{
									validInput=true;
								}
							}
							else
							{
								if(shipCoordinate.length()>3)
								{
									System.out.println("Invalid input, please enter only coordinate e.g A1");
								}
								else
								{
									validInput=true;
								}
							}
						}
						else
						{
							System.out.println("Coordinate out of range");
							validInput=false;
						}
					}		
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
