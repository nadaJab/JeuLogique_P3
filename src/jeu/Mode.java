package jeu;

import java.io.Serializable;

public enum Mode implements Serializable {

CHALLENGER (1),
DEFENSEUR (2),
DUEL (3);
	
	private int mode;
	
	Mode(int mode){
		this.mode = mode;
	}
	
	public int getMode() {
		return this.mode;
	}
}