package jeu;

import java.io.Serializable;
import java.util.Arrays;


public class PlusMoins extends Jeu implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Cette méthode est abstract.Elle retourne un résultat de type String de la comparaison 
     * entre une combinaison secrète et une proposition donnée.   
	 * @param combiSecrete 
	 * @param combiEssai
	 * @return str
	 */	
	public String resultatComparer(int combiEssai[], int combiSecrete[]) {
		
		String str = " ";
		
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
		
		String resultat = "Proposition : " + Arrays.toString(combiEssai).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n";
		return resultat;
	}
	
}