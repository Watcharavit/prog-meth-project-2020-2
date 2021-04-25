package logic;


public class Cell {
	private entity.Character myCharacter;
	private boolean isEmpty;

	public Cell() {
		//isEmpty = true;
	}

	public boolean IsEmpty() {
		return isEmpty;
	}

	public entity.Character getMyCharacter() {
		return myCharacter;
	}

	public void setMyCharacter(entity.Character myCharacter) {
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
