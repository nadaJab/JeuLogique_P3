package jeu;
import java.io.Serializable;
import java.util.Arrays;

public class Mastermind extends Jeu implements Serializable{	

	private static final long serialVersionUID = 1L;
	int[] indice;

	protected Mastermind() {

		this.nbCase = Integer.parseInt(properties.getProperty("nbCaseMastermind"));
		this.nbEssai = Integer.parseInt(properties.getProperty("nbEssaiMastermind"));  

	}

	/**
	 * Cette méthode retourne un résultat de type String d'une comparaison 
	 * entre une combinaison secrète et une proposition donnée.  
	 * Dans un premier temps on a rempli le tableau indice[] par le nombre:
	 * (2 si le chiffre est bien placé) ou (1 si le chiffre est présent) 
	 * @param combiSecrete 
	 * @param combiEssai
	 * @return resultat
	 */
	public String resultatComparer(int combiEssai[], int combiSecrete[]) {
		indice = new int[getNbCase()];
		String resultat ="";
		

		for(int i = 0; i < combiSecrete.length ; i++) {

			for(int j = 0; j < combiEssai.length ; j++) {

				if(combiEssai[j]==combiSecrete[i]) {

					if(i == j) {
						indice[i] = 2;
					}
					else {
						if(indice[i]!= 2) {  
							indice[i] = 1;
							
						}}}}}
		
		resultat = resultatString();
		return resultat;
	} 

	/**
	 * Cette méthode retourne le résultat de la comparison entre 
	 * une combinaison secrète et une proposition donnée ( bien placé, présent)
	 * depuis le contenu du tableau indice.
	 * @see resultatComparer() 
	 * @return str
	 */
	public String resultatString() {
		int testVide[] = new int[getNbCase()];
		String str="";
		String str1 ="";
		String str2 ="";
		int nb=0;
		int nb1=0;
		
		if(Arrays.equals(indice, testVide)) {
		str ="Il n'existe aucun chiffre";	
		}
		else {
		for(int i = 0; i < indice.length ; i++) {

			if(indice[i]== 2) {
				nb++;
				str1 = nb + " bien placé(s)";
			}
			else if(indice[i]== 1) {
				nb1++;
				str2 = nb1 + " présnt(s) ";	
			}
		  }
		str = str1 + " "  + str2;
		}
		
		return str;
	}
	
	/**
	 * 
	 */
	public int[][] affinerMaxMin(int[] proposition1) {

		int minMax[][] = new int[getNbCase()][2];

		for(int i = 0; i < minMax.length ; i++) {

			minMax[i][0] = 0;		
			minMax[i][1] = 9;		
		}
		/*
		if(indice != null ) {

			for(int i=0; i < indice.length ; i++) {
				if(indice[i] == ' ') {
					minMax[i][0] = proposition1[i] + 1;
				}
				else if(indice[i] == 'P') {
					minMax[i][1] = proposition1[i] - 1;
				}
				else if(indice[i] == 'B') {
					minMax[i][0] = proposition1[i];
					minMax[i][1] = proposition1[i];
				}
			}
		}*/
		return minMax;
	}
} 




