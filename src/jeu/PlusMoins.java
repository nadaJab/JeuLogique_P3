package jeu;


public class PlusMoins extends Jeu {
	
	private int[][] minMax;
	
	public PlusMoins() {

		if(!properties.isEmpty()) {
		
			this.nbEssai = Integer.parseInt(properties.getProperty("nbEssaiPlusMoins"));  
			this.nbCase = Integer.parseInt(properties.getProperty("nbCasePlusMoins"));
			
			logger.info("Récupération des nombres de cases et d'essai");
		}
		else {

			this.nbEssai = 7;
			this.nbCase = 4;
			
			logger.error("erreur lors de la lecture du fichier properties");
			logger.info("Récupération des nombres de cases et d'essai par défaut");

		}
	}

	/**
	 * Cette méthode permet de remplir un tableau qui reprèsente une combinaison  donné par l'ordinateur. 
	 */
	public int[] genCombiOrdinateur() {

		minMax = affinerMaxMin();

		int tabCombiOrdinateur[] = new int[ nbCase];

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
	 * @return strResComparer
	 */	
	public String resultatComparer(int combiEssai[], int combiSecrete[]) {

		String strResComparer = "";

		for(int i = 0; i < combiSecrete.length ; i++) {

			if(combiEssai[i] == combiSecrete[i]) {
				strResComparer += "=";
			}
			else if(combiEssai[i] < combiSecrete[i]) {
				strResComparer +="+";

			}
			else if(combiEssai[i] > combiSecrete[i]) {
				strResComparer += "-";
			}
		} 	
		return strResComparer;
	}


	/**
	 *Cette méthode permet d'affiner l'intervalle [min max] de la méthode genCombiOrdinateur()
	 *@return minMax[][]
	 *@see genCombiOrdinateur()
	 */
	public int[][] affinerMaxMin() {

		if(minMax == null) {

			minMax = new int[nbCase][2];
		}
	
			if(strComparer != "") {

				for(int i=0; i<strComparer.length();i++) {
					if(strComparer.charAt(i) == '+') {
						minMax[i][0] = propositionOrdi[i] + 1;
					}
					else if(strComparer.charAt(i) == '-') {
						minMax[i][1] = propositionOrdi[i] - 1;
					}
					else if(strComparer.charAt(i) == '=') {
						minMax[i][0] = propositionOrdi[i];
						minMax[i][1] = propositionOrdi[i];
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
