package battleships;

public class Player {

	private String[] mCoordinates;
	private int mPlacedShipCount;
	private int mPoints;

	public Player(int max, int points)
	{
		mCoordinates=new String[max*3]; // *3 each ship takes 3 fields
		mPoints=points;
		mPlacedShipCount=0;
	}

	public String getmCoordinates(int i) {
		return mCoordinates[i];
	}
	
	public String[] getmCoordinates() {
		return mCoordinates;
	}
	
	public void setmCoordinates(String mCoordinates, int i) {
		this.mCoordinates[i] = mCoordinates;
	}

	public int getmPoints() {
		return mPoints;
	}

	public void setmPoints(int mPoints) {
		this.mPoints = mPoints;
	}

	public int getmPlacedShipCount() {
		return mPlacedShipCount;
	}

}
