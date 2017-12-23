package jeu;

import java.io.Serializable;

public class PlusMoins extends Jeu implements Serializable{

	private static final long serialVersionUID = 1L;
	private String strComparer="";
	private int minMax[][] = new int[getNbCase()][2];

	
	protected PlusMoins() {

		if(!properties.isEmpty()) {
			this.nbCase = Integer.parseInt(properties.getProperty("nbCasePlusMoins"));
			this.nbEssai = Integer.parseInt(properties.getProperty("nbEssaiPlusMoins"));  
		}
	}
	
	/**
	 * Cette méthode permet de remplir un tableau qui reprèsente une combinaison  donné par l'ordinateur. 
	 */
	public int[] genCombiOrdinateur() {
		minMax = affinerMaxMin();
		
		int tabCombiOrdinateur[] = new int[getNbCase()];

		for(int i = 0; i < tabCombiOrdinateur.length; i++) {
			tabCombiOrdinateur[i] = (int) (Math.random() * (minMax[i][1] - minMax[i][0] +1)) + minMax[i][0];	
		}
		return tabCombiOrdinateur;
	}

	/**
	 * Cette méthode retourne un résultat de type String d'une comparaison 
	 * entre une combinaison secrète et une proposition donnée.   
	 * @param combiSecrete 
	 * @param combiEssai
	 * @return str
	 */	
	public String resultatComparer(int combiEssai[], int combiSecrete[]) {
		
		strComparer = "";

		for(int i = 0; i < combiSecrete.length ; i++) {

			if(combiEssai[i] == combiSecrete[i]) {
				strComparer += "=";
			}
			else if(combiEssai[i] < combiSecrete[i]) {
				strComparer +="+";

			}
			else if(combiEssai[i] > combiSecrete[i]) {
				strComparer += "-";
			}
		} 	
		return strComparer;
	}
	
	public void setStrRes(String strResuOrdi)
	{
		strComparer = strResuOrdi;
	}

	/**
	 *Cette méthode permet d'affiner l'intervalle [min max] de la méthode genCombiOrdinateur()
	 *@return minMax[][]
	 *@see genCombiOrdinateur()
	 */
	public int[][] affinerMaxMin() {
		
		if(strComparer != "") {
				//String str = resultatComparer(combiEssaiOrdi,combiSecreteHumain);
			for(int i=0; i<strComparer.length();i++) {
				if(strComparer.charAt(i) == '+') {
					minMax[i][0] = combiEssaiOrdi[i] + 1;
				}
				else if(strComparer.charAt(i) == '-') {
					minMax[i][1] = combiEssaiOrdi[i] - 1;
				}
				else if(strComparer.charAt(i) == '=') {
					minMax[i][0] = combiEssaiOrdi[i];
					minMax[i][1] = combiEssaiOrdi[i];
				}
			}
		}
		else {
			for(int i = 0; i < minMax.length; i++) {

				minMax[i][0] = 0;		
				minMax[i][1] = 9;		
			}
		}
		return minMax;
	}
}