package jeu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Mastermind extends Jeu implements Serializable{	

	private static final long serialVersionUID = 1L;

	private int[] tabIndice;
	private String strComparer = "";

	private ArrayList<int[]> listePos = new ArrayList<int[]>((int)(Math.pow(getNbCase(), 10)));

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

		if(listePos.size() == 0) {
			creerlistePos();
		}
		else {
			affinerListePos();	
		}


		int indiceAlea = (int)(Math.random()*listePos.size()); 	// Choix de l'indice dans l'ensemble des combinaisons (aléatoire)

		tabCombiOrdinateur = listePos.get(indiceAlea);

		return tabCombiOrdinateur;
	}

	/**
	 * Cette méthode permet de créer toutes les combinaisons possibles en fonction
	 * du nombre de cases et de chiffres (entre 0 et 9).
	 * @return litePos[][]
	 */
	public void creerlistePos() {

		int[] tab;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				for(int l = 0; l < 10; l++) {
					for(int k = 0; k < 10; k++) {

						tab = new int[getNbCase()];
						tab[0] = i;
						tab[1] = j;
						tab[2] = l;
						tab[3] = k;
						listePos.add(tab);

					}
				}
			}
		}

	}

	/**
	 * @return 
	 * 
	 */
	public void affinerListePos() {

		 // cette variable contient le résultat de la comparaison 
		// de la combinaison secrète et la proposition de l'ordinateur
		String strComparerCombiProp = strComparer; 

		if(!strComparerCombiProp.equals("")) {
			for(int i = 0; i < listePos.size(); i++) {

				//Comparer la proposition de l'ordinateur avec toutes les combinaisons qui existent dans l'ArrayList.
				String strComparerPropPossi = resultatComparer(listePos.get(i),combiEssaiOrdi);  

				if(!strComparerPropPossi.equals(strComparerCombiProp)) {

					// On supprime toutes les combinaisons qui n'ont pas le même résultat de comparaison
					//que la proposition avec la combinaison secrète.
					listePos.remove(i);
					i--;
				}
			}
		}
	}

	/**
	 * Comparaison de deux combinaisons indiquant le nombre de chiffres bien placés et le nombre de chiffres mal placés
	 * @param combiSecrete 
	 * @param combiEssai
	 * @return resultat
	 */
	public String resultatComparer(int combiEssai[], int combiSecrete[]) {

		tabIndice = new int[getNbCase()]; // tableau d'indice avec "2" si le chiffre est bien placé ou "1" si le chiffre est présent 
		ArrayList<Integer> indiceExist = new ArrayList<Integer>(); //ArrayList pour sauvegarder les indices du tableau des élements trouvés (mplacé, présent)
		strComparer = "";

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
		strComparer = TabindiceString();
		return strComparer;
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

		strComparer = strResuOrdi;
	}

} 




