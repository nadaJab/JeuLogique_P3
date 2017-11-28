package jeu;

import java.io.Serializable;
import java.util.Arrays;

public class Mastermind extends Jeu implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean comparer(int combiEssai[], int combiSecrete[]) {
		
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

		System.out.println("Proposition : " + Arrays.toString(combiEssai).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");
		
		return comparerRes;             
	} 
		
}


