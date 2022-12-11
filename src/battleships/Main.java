
// Kenny Verheyden
// https://kennyverheyden.be

package battleships;

import java.util.Scanner;

import battleships.Game;


public class Main {

	public static void main(String[] args) {

		welcome();
		content();
		endMSG();
	}

	public static void endMSG()
	{
		System.out.println("\n  Thank you for playing");
	}

	public static void content()
	{
		Game game = new Game();
		Scanner userInput = new Scanner(System.in);
		boolean exitProgram=false;;
		boolean validInput= false;
		int userChose=0;

		do
		{
			System.out.println("     1. Play\n");
			System.out.println("     2. Exit the game\n");
			do
			{
				try
				{
					System.out.print("  Choice: ");
					userChose=userInput.nextInt();
					validInput=true;
				}
				catch(Exception e){
					userInput.next();
					System.out.println("Enter valid number\n");
				}
			}
			while(!validInput);

			switch(userChose) {
			case 1:
				game.initGame();
				break;
			case 2:
				exitProgram = true;
				break;
			}
		}
		while(!exitProgram);
	}

	private static void welcome() {
		System.out.println("  *************************************************************************");
		System.out.println("  *****                         Battle Ships                          *****");
		System.out.println("  *****           Defeat the computer ships in the ocean             *****");
		System.out.println("  *************************************************************************\n");
	}

}
