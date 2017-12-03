package jeu;

import java.io.Serializable;
import java.util.Arrays;


public class PlusMoins extends Jeu implements Serializable{

	private static final long serialVersionUID = 1L;
	private int max;
	private int min;
	private String str;
	int tabMin[] = new int[getNbCase()];
	int tabMax[] = new int[getNbCase()];

	/**
	 * Cette méthode est abstract.Elle retourne un résultat de type String de la comparaison 
	 * entre une combinaison secrète et une proposition donnée.   
	 * @param combiSecrete 
	 * @param combiEssai
	 * @return str
	 */	
	public String resultatComparer(int combiEssai[], int combiSecrete[]) {

		str = "";

		for(int i = 0; i < getNbCase(); i++) {

			if(combiEssai[i] == combiSecrete[i]) {
				str += "=";
			}
			else if(combiEssai[i] < combiSecrete[i]) {
				str +="+";

			}
			else if(combiEssai[i] > combiSecrete[i]) {
				str += "-";
			}
		} 	
		return str;
	}


	public int[] genPropOrdinateur(int[] proposition1, String str,int[] tabMin,int[] tabMax) {

		int tabCombiOrdinateur[] = new int[getNbCase()];


		if(str != "" ) {

			for(int i=0; i<getNbCase();i++) {
				if(str.charAt(i) == '+') {
					tabMin[i] = proposition1[i];
				}
				else if(str.charAt(i) == '-') {
					tabMax[i] = proposition1[i];
				}
			}
		}

		for(int i = 0; i < getNbCase(); i++) {
			tabCombiOrdinateur[i] = (int) (Math.random() * (tabMax[i] - tabMin[i] +1)) + tabMin[i];	
		}

		if(str != "" ) {
			for(int i=0; i<getNbCase();i++) {

				if(str.charAt(i) == '=') {
					tabCombiOrdinateur[i] = proposition1[i];
				}
			}
		}
		return tabCombiOrdinateur;
	}
}