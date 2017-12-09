package jeu;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Properties;

public class Mastermind extends Jeu implements Serializable{	

	private static final long serialVersionUID = 1L;
	
	/**
	 * @see #lecturePropertis() dans la classe Jeu
	 */
	public Properties lecturePropertis() {
		return super.lecturePropertis();
	}
	
	/**
	 * @see #getNbCase() dans la classe Jeu
	 */
	public int getNbCase() {
		Properties prop = lecturePropertis();
		int nbCase = super.getNbCase();
		String resu = prop.getProperty("nbCasenbCaseMastermind");
		nbCase = Integer.parseInt(resu.replaceAll("\\ ", ""));  

		return nbCase;
	}
	
	/**
	 * @see #getNbEssai() dans la classe Jeu
	 */
	public int getNbEssai() {
		int nbEssai = super.getNbCase();

		Properties prop = lecturePropertis();
		String resu = prop.getProperty("nbEssaiMastermind");
		nbEssai = Integer.parseInt(resu.replaceAll("\\ ", ""));  

		return nbEssai;
	}
	
	/**
	 * Cette méthode retourne un résultat de type String d'une comparaison 
	 * entre une combinaison secrète et une proposition donnée.   
	 * @param combiSecrete 
	 * @param combiEssai
	 * @return str
	 */
	public String resultatComparer(int combiEssai[], int combiSecrete[]) {
		
		String str = " ";
		String str1 = " ";
		
		int nb = 0;
		int nb1 = 0;
		
		boolean comparerRes = true;      
		
		for(int i = 0 ; i < getNbCase() ;i++) {
			
			for(int j = 0; j < getNbCase(); j++) {
				
				if(combiEssai[i]==combiSecrete[j]) {
					if(i == j) {
						
					    nb ++;
					    str = nb + " bien placé(s), ";
						}
					
					else {
						
						nb1 ++;
						str1 = nb1 + " présnt(s) ";	
						comparerRes=false;	
					}					comparerRes=false;	

				}
				else {
					//str = " Aucun chiffre n'existe ";	
					comparerRes=false;	
				}
			
		}
 }
		str = str + str1;		
		return str;             
	}


	@Override
	public int[][] affinerMaxMin(int[] proposition1, String str) {
		// TODO Auto-generated method stub
		return null;
	} 
		
}


