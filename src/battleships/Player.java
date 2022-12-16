package battleships;

public class Player {

	private String[][] mCoordinates; // stores the player ships coordinate, multi array [4][3] 4 ships of 3 fields

	// mark destroyed ships @, we need this apart for counting points and uses the original array as index for searching with original shipCoordinate
	private String[][] mCoordinatesDestroyed; 

	private String mIdName;
	private int mPoints;

	public Player(int maxShips, String idName)
	{
		mCoordinates=new String[maxShips][3]; // 3 each ship takes 3 fields
		mCoordinatesDestroyed=new String[maxShips][3]; // copy for destroy marks
		mPoints=maxShips;
		mIdName=idName;
	}

	public String getmIdName()
	{
		return mIdName;
	}

	public String getmCoordinates(int i, int j) {
		return mCoordinates[i][j];
	}

	public String[][] getmCoordinates() {
		return mCoordinates;
	}

	public void setmCoordinates(String Coordinate, int i, int j) {
		this.mCoordinates[i][j] = Coordinate;
	}

	public void setmCoordinatesDestroyed(String CoordinateDestroyed, int i, int j) {
		this.mCoordinatesDestroyed[i][j] = CoordinateDestroyed;
	}

	public int getmPoints() {
		return mPoints;
	}

	public void setmPoints() {
		this.mPoints--;
	}

	public String[][] getmCoordinatesDestroyed() {
		return mCoordinatesDestroyed;
	}

	public void setmCoordinatesDestroyed(String[][] mCoordinatesDestroyed) {
		this.mCoordinatesDestroyed = mCoordinatesDestroyed;
	}



}
