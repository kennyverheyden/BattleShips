package battleships;

import java.util.Scanner;

public class Ship {

	private String mShipCoordinatesArr[];
	private int mMaxShipsPlayer;
	
	public Ship()
	{
		mMaxShipsPlayer=3; // Amount of ships for the player
	}
	
	public void scanPlayerCoordinates()
	{
		String inputCoordinate;
		Scanner input = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		String[] shipCoordinatesArr = new String[mMaxShipsPlayer];
		char checkLetterExist;
		int checkNumberExist;
		boolean duplicateField=false;
		boolean valid; // exit loop
		System.out.println("");
		for(int i=0;i<mMaxShipsPlayer;i++)
		{
			valid=false;
			while(!valid) //check userinput
			{
				try
				{
					System.out.print("\nEnter coordinate for ship " +(i+1)+" of "+mMaxShipsPlayer+": "); //user input
					inputCoordinate=input.nextLine().toLowerCase().toString();
					sb.append(inputCoordinate); // StringBuilder to splits up letter coordinate for input validation
					checkLetterExist=sb.charAt(0); //get coordinate letter
					checkNumberExist=Integer.parseInt(inputCoordinate.replaceAll("[^0-9]", "")); //get coordinate number
					sb.setLength(0); //clear StringBuilder
					int j=0; //index for validation loop
					while(j<Field.getmLettersArr().length && checkNumberExist <=Field.getmSquareArr().length && checkNumberExist>0) //validate user input
					{
						if(checkLetterExist == Field.getmLettersArr()[j].charAt(0)) //first input letter must be the same as in the letter coordinates list
						{
							int x=0; //index for duplicate check loop
							while(x<i)//check for duplicate fields already entered
							{
								if(inputCoordinate.equals(shipCoordinatesArr[x]))
								{
									System.out.println("Field already assigned, enter another field");
									duplicateField=true;
									break;
								}
								x++;
							}
							if(!duplicateField)
							{
								shipCoordinatesArr[i]=inputCoordinate;
								valid=true;
								duplicateField=false;
								System.out.println("Ship "+(i+1)+" deployed on field "+shipCoordinatesArr[i]);
								break;
							}	
						}
						j++;
					}
					if(valid)
					{
						break;
					}
					else
					{
						if(!duplicateField)
						{
							System.out.println("Coordinate not in range of field");
						}
					}
					duplicateField=false;
				} catch (NumberFormatException nfe) {
					System.out.println("Enter valid coordinat");
					valid=false;
				}
			}
		}
		System.out.println("");
	}
}
