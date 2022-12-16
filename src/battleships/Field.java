package battleships;

public class Field {

	private String[][]mSquareArr; // Field, stores A1, A2, .
	private String[][]mSquareMarkedArr; // Field marks (hits, shoots, ..)
	private static String[] mLettersArr = new String[26]; // Alphabet list for field coordinates
	private int mSquareSize; // Define field size; defined by user input

	public Field(int squareSize)
	{
		mSquareSize=squareSize;
		// Fill up alphabet list for field coordinates
		for(char ch ='a';ch<='z';ch++)
		{
			mLettersArr[ch-'a']=String.valueOf(ch);
		}

		// Create field
		mSquareArr = new String[squareSize][squareSize]; // Field // Field, stores A1, A2, .
		mSquareMarkedArr = new String[squareSize][squareSize]; // Field marks (hits, shoots, ..)
		StringBuilder sb = new StringBuilder(); // merge letters
		for(int i=0;i<mSquareArr.length;i++)
		{
			for(int j=0;j<mSquareArr.length;j++)
			{
				sb.append(mLettersArr[i]+""+(j+1)); // add letter coordinates to fields
				mSquareArr[i][j]=sb.toString();
				mSquareMarkedArr[i][j]=sb.toString();
				sb.setLength(0);
			}
		}
	}

	// Print line for formatting
	private void printFieldLineDivider()
	{
		System.out.print(" ");
		for (int i=0;i<mSquareSize;i++)
		{
			if(i==0)
			{
				System.out.print("    ");
			}

			if(i<9) {
				System.out.print("-----");
			}
			else {
				System.out.print("------");
			}
		}
	}

	// Print the square (ocean) to the screen
	public void printSquare()
	{
		for (int number = 0; number < mSquareSize; number++)
		{
			if(number==0)				{
				System.out.print("      ");
			}
			System.out.print(" ");
			System.out.print(" "+(number+1)+"  ");
		}

		System.out.println();
		for (int i = 0; i < mSquareMarkedArr.length; i++) {

			printFieldLineDivider();
			System.out.println("");
			for (int j = 0; j < mSquareSize; j++) {
				if(j==0)
				{
					System.out.print("   ");
					System.out.print(this.getmLettersArr()[i]+" | ");
				}
				System.out.print(mSquareMarkedArr[i][j]+" | ");
			}
			System.out.println("");
		}
		this.printFieldLineDivider();
		System.out.println(" ");
	}

	public static String[] getmLettersArr() {
		return mLettersArr;
	}

	public String[][] getmSquareArr() {
		return mSquareArr;
	}

	public String[][] getmSquareMarkedArr() {
		return mSquareMarkedArr;
	}
}
