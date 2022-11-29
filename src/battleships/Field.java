package battleships;

public class Field {

	private String[][]squareArr;
	private String[] mLettersArr = new String[26]; // Alphabet list for field coordinates
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
		squareArr = new String[squareSize][squareSize];
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<squareArr.length;i++)
		{
			for(int j=0;j<squareArr.length;j++)
			{
				sb.append(mLettersArr[i]+""+(j+1));
				squareArr[i][j]=sb.toString();
				sb.setLength(0);
			}
		}
	}

	// Print the square (ocean) to the screen
	public void printSquare()
	{
		for (int i = 0; i < squareArr.length; i++) {

			printFieldLineDivider();
			System.out.println("");
			for (int j = 0; j < mSquareSize; j++) {
				if(j==0)				{
					System.out.print(" | ");
				}
				System.out.print(squareArr[i][j]+" | ");
			}
			System.out.println("");
		}
		this.printFieldLineDivider();
		System.out.println(" ");
	}

	// Print line for formatting
	private void printFieldLineDivider()
	{
		System.out.print(" ");
		for (int i=0;i<mSquareSize;i++)
		{
			if(i<9) {
				System.out.print("-----");
			}
			else {
				System.out.print("------");
			}
		}

	}









}
