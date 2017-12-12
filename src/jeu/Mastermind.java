package jeu;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Properties;

public class Mastermind extends Jeu implements Serializable{	

	private static final long serialVersionUID = 1L;

	public Mastermind() {


		Properties properties = new Properties();
		InputStream input = getClass().getClassLoader().getResourceAsStream("resources/config.properties"); 	

		try{
			properties.load(input);
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		this.nbCase = Integer.parseInt(properties.getProperty("nbCaseMastermind"));
		this.nbEssai = Integer.parseInt(properties.getProperty("nbEssaiMastermind"));  

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




