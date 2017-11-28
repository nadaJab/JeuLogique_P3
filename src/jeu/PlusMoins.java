package jeu;

import java.io.Serializable;
import java.util.Arrays;


public class PlusMoins extends Jeu implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * la méthode qui compare deux combinaisons une secrete et l'autre saisie par le joueur
	 * @param combiSecrete 
	 * @param combiEssai
	 * @return comparerRes
	 */	
	public boolean comparer(int combiEssai[], int combiSecrete[]) {
		String str = "";
		boolean comparerRes =true;      
		
		for(int i = 0; i < getNbCase(); i++) {
			
			if(combiEssai[i] == combiSecrete[i]) {
				str += "=";
			}
			else if(combiEssai[i] < combiSecrete[i]) {
				str +="+";
				comparerRes=false;		//Si combiEssai[i] et combiSecrete[i] sont différents alors comparerRes=false
			}
			else if(combiEssai[i] > combiSecrete[i]) {
				str += "-";
				comparerRes=false;
			}
		} 
		System.out.println("Proposition : " + Arrays.toString(combiEssai).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");
		/* Au final, si la proposition donnée et la combinaison secrète ne sont pas identiques
		 * alors la méthode comparer(int [], int []) retourne <false> */
		return comparerRes;             
	}		
	
}