package jeu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Mastermind extends Jeu implements Serializable{	

	private static final long serialVersionUID = 1L;
	private int[] tabIndice;
	private int listePos[][] = new int[getNbCase()][10];

	protected Mastermind() {

		if(!properties.isEmpty()) {
			this.nbCase = Integer.parseInt(properties.getProperty("nbCaseMastermind"));
			this.nbEssai = Integer.parseInt(properties.getProperty("nbEssaiMastermind"));  
		}
	}

	/**
	 * 
	 */
	public int[] genCombiOrdinateur() {

		int tabCombiOrdinateur[] = new int[getNbCase()];

		for(int i = 0; i < tabCombiOrdinateur.length; i++) {
			tabCombiOrdinateur[i] = (int) (Math.random() * 10);	
		}
		return tabCombiOrdinateur;
	}

	/**
	 * Cette méthode permet de créer toute les combinaison possibles en fonction
	 * du nombre de cases et de chiffres (entre 0 et 9).
	 * @return litePos[][]
	 */
	public int[][] creerlistePos() {

		for(int i = 0; i < getNbCase(); i++) {
			for(int j = 0; j < 10; j++) {

				listePos[i][j] = j; 	
			}
		}
		return listePos;
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

		tabIndice = new int[getNbCase()];
	    ArrayList<Integer> indiceExist = new ArrayList<Integer>(); //ArrayList pour sauvegarder les indices du tableau des élements trouvés (mplacé, présent)
		String resultat ="";

		for(int i = 0; i < combiEssai.length ; i++) {

			if(combiEssai[i] == combiSecrete[i]) {   // tester si il y'a des chiffres bien placés
				tabIndice[i] = 2;	
				indiceExist.add(i);
			}
		}

		for(int i = 0; i < combiSecrete.length ; i++) {
			for(int j = 0; j < combiEssai.length ; j++) {
		
			if( (combiEssai[j] == combiSecrete[i]) && (!indiceExist.contains(j)) && (tabIndice[i] != 2) && (tabIndice[i] != 1) ){
				
				tabIndice[i] = 1;
				indiceExist.add(j);
			}
		}
	}
		resultat = TabindiceString();
		return resultat;
}


	/**
	 * Cette méthode retourne le résultat de la comparaison entre 
	 * une combinaison secrète et une proposition donnée ( bien placé, présent)
	 * à travers le contenu du tableau indice.
	 * @see resultatComparer() 
	 * @return str
	 */
	public String TabindiceString() {
		int tabVide[] = new int[getNbCase()];
		String str="";
		String str1 ="";
		String str2 ="";
		int nb=0;
		int nb1=0;

		if(Arrays.equals(tabIndice, tabVide)) {
			str ="Il n'existe aucun chiffre";	
		}
		else {
			for(int i = 0; i < tabIndice.length ; i++) {

				if(tabIndice[i]== 2) {
					nb++;
					str1 = nb + " bien placé(s)";
				}
				else if(tabIndice[i]== 1) {
					nb1++;
					str2 = nb1 + " présent(s) ";	
				}
			}
			str = str1 + " "  + str2;
		}

		return str;
	}

	@Override
	public void setStrRes(String strResuOrdi) {
		// TODO Auto-generated method stub

	}

} 




