package jeu;

import java.io.Serializable;

public class PlusMoins extends Jeu implements Serializable{

	private static final long serialVersionUID = 1L;
	private String str;
	

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
	
	/**
	 *Cette méthode permet d'affiner l'intervalle [min max] de la méthode genCombiOrdinateur()
	 *@return minMax[][]
	 *@see genCombiOrdinateur()
	 */
	public int[][] affinerMaxMin(int[] proposition1, String str) {
		
		int minMax[][] = new int[getNbCase()][2];
		
		for(int i = 0; i < getNbCase(); i++) {
				
				minMax[i][0] = 0;		
				minMax[i][1] = 9;		
			}
		if(str != "" ) {

			for(int i=0; i<getNbCase();i++) {
				if(str.charAt(i) == '+') {
					minMax[i][0] = proposition1[i] + 1;
				}
				else if(str.charAt(i) == '-') {
					minMax[i][1] = proposition1[i] - 1;
				}
				else if(str.charAt(i) == '=') {
					minMax[i][0] = proposition1[i];
					minMax[i][1] = proposition1[i];
				}
			}
		}
		return minMax;
	}
}