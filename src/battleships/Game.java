package battleships;

import java.util.Random;
import java.util.Scanner;

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
			shoot(square,human,computerShoot(square, computer, human));
			System.out.println("Your ships left: "+ human.getmPoints());
			System.out.println("Computer ships left: "+computer.getmPoints());
			square.printSquare();
		}

		if(human.getmPoints()>0)
		{
			System.out.println("You win, you destroyed all the ships in the ocean");
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
				player.setmCoordinates(letter+coordinateNumber, i, j);
				player.setmCoordinatesDestroyed(letter+coordinateNumber, i, j); // copy array for destroy markings
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
				player.setmCoordinates(square.getmLettersArr()[letterPosition]+coordinateNumber, i, j);
				player.setmCoordinatesDestroyed(square.getmLettersArr()[letterPosition]+coordinateNumber, i, j);
				letterPosition++;
			}
		}
		return i;
	}

	public boolean checkDuplicate(Player player, String shipCoordinate)
	{
		String letterCoordinate=null;
		String fieldCoordinate = null;
		String direction = null;
		String secondFieldCoordinate = null;
		String thirdFieldCoordinate=null;
		int number=0;

		if(Character.isLetter(shipCoordinate.charAt(shipCoordinate.length()-1)))
		{
			if(shipCoordinate.charAt(2)=='h' || shipCoordinate.charAt(2)=='v')
			{
				letterCoordinate=String.valueOf(shipCoordinate.charAt(0)); // get letter coordinate
				number=(Integer.parseInt((shipCoordinate.replaceAll("[^0-9]", "")))); //get coordinate number
				direction =String.valueOf(shipCoordinate.charAt(2));
				fieldCoordinate=shipCoordinate.substring(0, shipCoordinate.length()-1); 
			}
			else if(shipCoordinate.charAt(3)=='h' || shipCoordinate.charAt(3)=='v')
			{
				letterCoordinate=String.valueOf(shipCoordinate.charAt(0)); // get letter coordinate
				number=(Integer.parseInt((shipCoordinate.replaceAll("[^0-9]", "")))); //get coordinate number
				direction =String.valueOf(shipCoordinate.charAt(3));
				fieldCoordinate=shipCoordinate.substring(0, shipCoordinate.length()-1); 
			}

			if(direction.equals("h"))
			{
				secondFieldCoordinate= letterCoordinate+(number+1);
				thirdFieldCoordinate= letterCoordinate+(number+2);
			}
			if(direction.equals("v"))
			{
				int i=0; // find letter index
				while(i<Field.getmLettersArr().length)
				{
					if(Field.getmLettersArr()[i].equals(letterCoordinate))
					{
						break;
					}
					i++;
				}
				secondFieldCoordinate= Field.getmLettersArr()[i+1]+number;
				thirdFieldCoordinate= Field.getmLettersArr()[i+2]+number;
			}

			if(direction.equals("h") || direction.equals("v"))
			{
				for(int i=0;i<player.getmCoordinates().length;i++)
				{
					for(int j=0;j<3;j++)
					{
						if(player.getmCoordinates()[i][j]!=null) // avoid error on first input while coordinate list is empty
						{
							if(player.getmCoordinates()[i][j].equals(fieldCoordinate))
							{
								return true;
							}
							if(player.getmCoordinates()[i][j].equals(secondFieldCoordinate))
							{
								return true;
							}
							if(player.getmCoordinates()[i][j].equals(thirdFieldCoordinate))
							{
								return true;
							}
						}
					}
				}
			}
		}
		else
		{
			fieldCoordinate=shipCoordinate;
			for(int i=0;i<player.getmCoordinates().length;i++)
			{
				for(int j=0;j<3;j++)
				{
					if(player.getmCoordinates()[i][j]!=null) // avoid error on first input while coordinate list is empty
					{
						if(player.getmCoordinates()[i][j].equals(fieldCoordinate))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void updateSquareStart(Field square, Player human, Player computer)
	{
		String coordinateHuman;
		String coordinateComputer;


		// mark ships on field
		for (int humanI = 0; humanI < human.getmCoordinates().length; humanI++) {
			for (int humanJ = 0; humanJ < 3; humanJ++)
			{
				coordinateHuman=human.getmCoordinates()[humanI][humanJ];
				for (int iSquare = 0; iSquare < square.getmSquareMarkedArr().length; iSquare++) {
					for (int  jSquare = 0; jSquare < square.getmSquareMarkedArr().length; jSquare++)
					{
						// compaire with fields on the square
						if(square.getmSquareArr()[iSquare][jSquare].equals(coordinateHuman))
						{
							if(jSquare<9) // preserved space for formatting
							{
								square.getmSquareMarkedArr()[iSquare][jSquare]="<>"; // formatting
							}
							else
							{
								square.getmSquareMarkedArr()[iSquare][jSquare]=" <>"; // formatting
							}
						}
					}
				}
			}
		}

		// mark collisions on field
		for (int humanI = 0; humanI < human.getmCoordinates().length; humanI++) {
			for (int humanJ = 0; humanJ < 3; humanJ++)
			{
				for(int computerI=0;computerI<computer.getmCoordinates().length;computerI++) {
					for(int computerJ=0;computerJ<3;computerJ++)
					{
						coordinateHuman=human.getmCoordinates()[humanI][humanJ];
						coordinateComputer=computer.getmCoordinates()[computerI][computerJ];

						// ships on same field (collision)
						if(coordinateHuman.equals(coordinateComputer))
						{
							// get coordinate for comparing in field square
							coordinateHuman=human.getmCoordinates()[humanI][humanJ];
							coordinateComputer=computer.getmCoordinates()[computerI][computerJ];

							human.getmCoordinatesDestroyed()[humanI][humanJ]="@";
							computer.getmCoordinatesDestroyed()[computerI][computerJ]="@";

							// mark on field, the listed coordinates from this ship
							for (int iSquare = 0; iSquare < square.getmSquareArr().length; iSquare++) {
								for (int  jSquare = 0; jSquare < square.getmSquareArr().length; jSquare++)
								{
									// compaire with fields on the square
									if(square.getmSquareArr()[iSquare][jSquare].equals(coordinateHuman) || square.getmSquareArr()[iSquare][jSquare].equals(coordinateComputer))
									{
										if(jSquare<9) // preserved space for formatting
										{  // mark field as hit
											square.getmSquareMarkedArr()[iSquare][jSquare]=" @"; // formatting
										}
										else
										{	// mark field as hit
											square.getmSquareMarkedArr()[iSquare][jSquare]="  @"; // formatting
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
		checkFullShipHit(human);
		// count hitted fields computer
		checkFullShipHit(computer);
	}

	public void shoot(Field square, Player player, String shipCoordinate)
	{
		boolean hit=false;

		for(int i=0;i<player.getmCoordinates().length;i++)
		{
			for (int j = 0; j < 3; j++) {
				if(player.getmCoordinates()[i][j].equals(shipCoordinate)) {
					hit=true;
					player.getmCoordinatesDestroyed()[i][j]="@"; // mark hit on playerCoordinateList
					for (int iSquare = 0; iSquare < square.getmSquareArr().length; iSquare++) {
						for (int  jSquare = 0; jSquare < square.getmSquareArr().length; jSquare++)
						{
							if(square.getmSquareArr()[iSquare][jSquare].equals(player.getmCoordinates()[i][j]))
							{
								if(jSquare<9)
								{	// mark field as hit
									square.getmSquareMarkedArr()[iSquare][jSquare]=" @"; // formatting
								}
								else
								{	// mark field as hit
									square.getmSquareMarkedArr()[iSquare][jSquare]="  @"; // formatting
								}
							}
						}
					}
				}
			}
		}	

		// Ship amount processing
		boolean fullHit=false;
		if(hit)
		{
			if(checkFullShipHit(player, shipCoordinate))
			{
				player.setmPoints();
				fullHit=true;
				if(!player.getmIdName().equals("computer"))
				{
					System.out.println("One of your ships sank");
				}
				else
				{
					System.out.println("A ship of the computer sank");
				}
			}
			if(!player.getmIdName().equals("computer"))
			{
				if(!fullHit)
					System.out.println("The computer hit your ship on: "+ shipCoordinate);
			}
			else
			{
				if(!fullHit)
					System.out.println("You HIT a ship on field: "+ shipCoordinate);
			}
		}
		else
		{
			if(!player.getmIdName().equals("computer"))
			{
				System.out.println("The computer missed on field: "+shipCoordinate);
			}
			else
			{
				System.out.println("You missed on field: "+shipCoordinate);
			}

			if(player.getmIdName().equals("computer"))
			{
				for (int iSquare = 0; iSquare < square.getmSquareMarkedArr().length; iSquare++) {
					for (int  jSquare = 0; jSquare < square.getmSquareMarkedArr().length; jSquare++)
					{
						if(square.getmSquareArr()[iSquare][jSquare].equals(shipCoordinate))
						{
							if(jSquare<9)
							{	// mark hit
								if(!square.getmSquareMarkedArr()[iSquare][jSquare].equals(" @"))
									square.getmSquareMarkedArr()[iSquare][jSquare]=" +"; // formatting
							}
							else
							{	// mark hit
								if(!square.getmSquareMarkedArr()[iSquare][jSquare].equals("  @"))
									square.getmSquareMarkedArr()[iSquare][jSquare]="  +"; // formatting
							}
						}
					}
				}
			}
		}
	}

	private boolean checkFullShipHit(Player player, String shipCoordinate) {
		int shipFields=0;

		for(int i=0;i<player.getmCoordinates().length;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(player.getmCoordinates()[i][j].equals(shipCoordinate)) // field exist, then check from the first field
				{
					for(int x=0;x<3;x++)
					{
						if(player.getmCoordinatesDestroyed()[i][x].equals("@"))
						{
							shipFields++;
						}
					}
				}
			}
		}
		if(shipFields==3)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private void checkFullShipHit(Player player) {

		for(int i=0;i<player.getmCoordinates().length;i++)
		{
			int shipFields=0;
			for(int j=0;j<3;j++)
			{
				if(player.getmCoordinatesDestroyed()[i][j].equals("@"))
				{
					shipFields++;
				}
				if(shipFields==3)
				{
					player.setmPoints();
				}
			}
		}
	}

	public String computerShoot(Field field, Player computer, Player human)
	{
		StringBuilder sb = new StringBuilder();
		String computerChoice=null;
		boolean takenField;
		do
		{
			int randomX;
			Random rn = new Random();
			do {randomX=rn.nextInt(field.getmSquareArr().length);}while(randomX==0);
			sb.append(field.getmLettersArr()[randomX]);
			do {randomX=rn.nextInt(field.getmSquareArr().length);}while(randomX==0);
			takenField=false;	
			sb.append(randomX);
			computerChoice=sb.toString();
			sb.setLength(0);

			//as human do, if the computer see a hit @, he will take the field next to it
			//do not in start position or initialization, so check the coordinate list for empty values
			boolean initStatus=false;
			for (int i = 0; i < computer.getmCoordinates().length; i++) {
				for (int j = 0; j < 3; j++) {
					if(computer.getmCoordinates()[i][j]==null)
					{
						initStatus=true;
					}
					else
					{
						initStatus=false;
					}
				}
			}
			if(!initStatus)
			{
				for (int i = 0; i < human.getmCoordinates().length; i++) {
					for(int j=0; j<3;j++)
					{
						if(human.getmCoordinatesDestroyed()[i][j].equals("@"))
						{
							for(int x=0;x<3;x++)
							{
								if(!human.getmCoordinatesDestroyed()[i][x].equals("@"))
								{
									computerChoice=human.getmCoordinatesDestroyed()[i][x];
								}
							}
						}
					}
				}
			}

			// check if field is already taken by computer or not
			if(checkDuplicate(computer,computerChoice)) // check for own placed ships
			{
				takenField=true;
			}

			// check if the field is already destroyed or not
			for(int i=0;i<field.getmSquareMarkedArr().length;i++)
			{
				for(int j=0;j<field.getmSquareMarkedArr().length;j++)
				{
					if(field.getmSquareArr()[i][j].equals(computerChoice))
					{
						if(field.getmSquareMarkedArr()[i][j].equals(" @") || field.getmSquareMarkedArr()[i][j].equals("  @")) // check content of field
						{
							takenField=true;
						}
					}
				}
			}
		}
		while(takenField);
		return computerChoice;
	}

	// ask player ship coordinates
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
						System.out.println("Coordinate already used or in range with another ship");
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

	// ask player ship coordinates
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
							if(square.getmSquareArr().length>9)
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
							else
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
		if(number <=0)
		{
			System.out.println("Enter a valid number");
			return check;
		}
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
