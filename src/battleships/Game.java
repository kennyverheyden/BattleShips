package battleships;
import java.util.Arrays;
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
		mMinSquareSize=4;
		mMaxShips=4;
		mPointsPlayer=mMaxShips;
		mPointsComputer=mMaxShips;
	}

	public void initGame()
	{
		Field square = new Field(scanPlayerInputSquareSize(mMaxSquareSize, mMinSquareSize)); // Initialization dynamic boardSquare
		Player human = new Player(mMaxShips,mPointsPlayer,"human");
		Player computer = new Player(mMaxShips,mPointsPlayer,"computer");
		square.printSquare();
		setPlayerStartCoordinates(square,human);
		setPlayerStartCoordinates(square,computer);
		updateSquareStart(square, human, computer);
		square.printSquare();
		System.out.println("Ships left player: "+mPointsPlayer);
		System.out.println("Ships left computer: "+mPointsComputer);
		System.out.println("");
	}

	public String computerChoseCoordinate(Field square, Player player)
	{
		StringBuilder sb = new StringBuilder();
		Random rn = new Random();
		String computerChoice=null;;
		String[] directions= {"h","v"};
		boolean validChose=false;
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

				int i=0;
				if(i!=0)
				{
					while(i<player.getmCoordinates().length)
					{
						if(player.getmCoordinates()[i].equals(computerChoice))
						{
							System.out.println("Computer chosed duplicate");
							break;
						}
						i++;
					}
				}
			}
			while(checkDuplicate(player, computerChoice));
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

		for(int i=0;i<player.getmCoordinates().length;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(shipCoordinate.equals(player.getmCoordinates()[i][j]))
						{
					duplicate=true;
						}
			}
		}

		return duplicate;
	}

	public void updateSquareStart(Field square, Player human, Player computer)
	{

		String coordinateHuman;
		String coordinateComputer;

		for (int humanI = 0; humanI < human.getmCoordinates().length; humanI++) {
			for (int humanJ = 0; humanJ < 3; humanJ++)
			{
				for(int computerI=0;computerI<computer.getmCoordinates().length;computerI++)
				{
					for(int computerJ=0;computerJ<3;computerJ++)
					{
						if(computer.getmCoordinates()[computerI][computerJ].equals(human.getmCoordinates()[humanI][humanJ]))
						{
							for (int x = 0; x < 3; x++)
							{
								coordinateHuman=human.getmCoordinates()[humanI][x].substring(0, human.getmCoordinates()[humanI][x].length()-1);
								coordinateComputer=computer.getmCoordinates()[computerJ][x].substring(0, computer.getmCoordinates()[computerJ][x].length()-1);

								for (int iSquare = 0; iSquare < square.getmSquareArr().length; iSquare++) {
									for (int  jSquare = 0; jSquare < square.getmSquareArr().length; jSquare++)
									{
										if(square.getmSquareArr()[iSquare][jSquare].equals(coordinateHuman) || square.getmSquareArr()[iSquare][jSquare].equals(coordinateComputer))
										{
											if(jSquare<10)
											{
												square.getmSquareArr()[iSquare][jSquare]="@@"; // formatting
											}
											else
											{
												square.getmSquareArr()[iSquare][jSquare]="@@@"; // formatting
											}
											if(x==2)
											{
												this.setmPointsComputerMin();
												this.setmPointsPlayerMin();
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
	}




	public boolean updateSquare(Field square, Player player, String shipCoordinate)
	{
		String fieldCoordinate;
		boolean hit=false;
		String mark;
		if(player.getmIdName().equals("human"))
		{
			mark="y";
		}
		else
		{
			mark="c";
		}
		for (int i = 0; i < player.getmCoordinates().length; i++) {
			for (int j = 0; j < 3; j++)
			{
				if(shipCoordinate.equals(player.getmCoordinates()[i][j]))
				{
					for (int x = 0; x < 3; x++)
					{
						fieldCoordinate=player.getmCoordinates()[i][x].substring(0, shipCoordinate.length()-1);
						for (int iSquare = 0; iSquare < square.getmSquareArr().length; iSquare++) {
							for (int  jSquare = 0; jSquare < square.getmSquareArr().length; jSquare++)
							{
								if(fieldCoordinate.equals(square.getmSquareArr()[iSquare][jSquare]))
								{
									if(jSquare<10)
									{
										square.getmSquareArr()[iSquare][jSquare]=mark+"@";
									}
									else
									{
										square.getmSquareArr()[iSquare][jSquare]=mark+"@@";
									}
									hit=true;
								}
							}
						}
					}
				}

			}
		}
		return hit;


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
			System.out.println("Enter coordinate between A1 and "+(letterArr[square.getmSquareArr().length-1]).toUpperCase()+""+square.getmSquareArr().length +" h=horizontal/v=vertical e.g. [a1v]:");
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





	public void setmPointsComputerMin() {
		this.mPointsComputer--;
	}


	public void setmPointsPlayerMin() {
		this.mPointsPlayer--;
	}

}
