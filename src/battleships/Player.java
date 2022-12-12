package battleships;

public class Player {

	private String[][] mCoordinates;
	private String mIdName;
	private int mPoints;

	public Player(int maxShips, String idName)
	{
		mCoordinates=new String[maxShips][3]; // 3 each ship takes 3 fields
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

	public void setmCoordinates(String mCoordinates, int i, int j) {
		this.mCoordinates[i][j] = mCoordinates;
	}

	public int getmPoints() {
		return mPoints;
	}

	public void setmPoints() {
		this.mPoints--;
	}

}
