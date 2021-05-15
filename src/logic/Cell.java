package logic;

import entity.base.Being;

public class Cell {
	private Being myCharacter;
	private boolean isEmpty;

	public Cell() {
		//isEmpty = true;
	}

	public boolean IsEmpty() {
		return isEmpty;
	}

	public Being getMyCharacter() {
		return myCharacter;
	}

	public void setMyCharacter(Being myCharacter) {
		this.myCharacter = myCharacter;
	}

	public void removeCharacter() {
		myCharacter = null;
		//isEmpty = true;
	}

	/*public int getSymbol() {
		if (isEmpty) {
			return -1;
		}
		return myEntity.getSymbol();
	}*/

}
