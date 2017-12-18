package jeu;

import java.io.Serializable;

public class PlusMoins extends Jeu implements Serializable{

	private static final long serialVersionUID = 1L;
	private String str="";

	protected PlusMoins() {

		if(!properties.isEmpty()) {
			this.nbCase = Integer.parseInt(properties.getProperty("nbCasePlusMoins"));
			this.nbEssai = Integer.parseInt(properties.getProperty("nbEssaiPlusMoins"));  
		}
	}

	/**
	 * Cette méthode retourne un résultat de type String d'une comparaison 
	 * entre une combinaison secrète et une proposition donnée.   
	 * @param combiSecrete 
	 * @param combiEssai
	 * @return str
	 */	
	public String resultatComparer(int combiEssai[], int combiSecrete[]) {

		str = "";

		for(int i = 0; i < combiEssai.length ; i++) {

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
	public int[][] affinerMaxMin(int[] combiEssaiOrdi) {

		int minMax[][] = new int[getNbCase()][2];

		for(int i = 0; i < minMax.length; i++) {

			minMax[i][0] = 0;		
			minMax[i][1] = 9;		
		}
		if(str != "" ) {

			for(int i=0; i<str.length();i++) {
				if(str.charAt(i) == '+') {
					minMax[i][0] = combiEssaiOrdi[i] + 1;
				}
				else if(str.charAt(i) == '-') {
					minMax[i][1] = combiEssaiOrdi[i] - 1;
				}
				else if(str.charAt(i) == '=') {
					minMax[i][0] = combiEssaiOrdi[i];
					minMax[i][1] = combiEssaiOrdi[i];
				}
			}
		}
		return minMax;
	}
}