package jeu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Mastermind extends Jeu implements Serializable{	

	private static final long serialVersionUID = 1L;

	private int[] tabIndice;
	private String strComparer = "";

	private ArrayList<int[]> listePos;

	private int n = 10; 
	private int index = 0; 

	private int[] combinaison;

	public Mastermind() {

		if(!properties.isEmpty()) {
			
			this.nbEssai = Integer.parseInt(properties.getProperty("nbEssaiMastermind"));
			this.nbCase = Integer.parseInt(properties.getProperty("nbCaseMastermind"));
			logger.info("Récupération des nombres de cases et d'essai");
		}
		else {
			this.nbEssai = 10;
			this.nbCase = 4;
			
			logger.error("erreur lors de la lecture du fichier properties");
			logger.info("Récupération des nombres de cases et d'essai par défaut");
		}		
	}

	/**
	 * 
	 */
	public int[] genCombiOrdinateur() {
		
		if(listePos == null) {
			
			listePos = new ArrayList<int[]>((int)(Math.pow(nbCase, 10)));
		}
		
		int tabCombiOrdinateur[] = new int[nbCase];

		if(listePos.size() == 0) { 

			creerlistePos(index);
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
	 * Construction recursive des listes possibles.
	 * @return litePos[][]
	 */
	public void creerlistePos(int index) {

		if(combinaison == null) {
			
			combinaison = new int[nbCase];
		}
		
		if (index >= nbCase) {

			// la liste est construite 
			int[] combinaison2 = new int[nbCase];

			System.arraycopy(combinaison, 0, combinaison2, 0, nbCase); 

			listePos.add(combinaison2);

			return;
		}

		for(int i=0 ; i < n; i++) {

			combinaison[index]=i;
			creerlistePos(index+1);
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

		tabIndice = new int[nbCase]; // tableau d'indice avec "2" si le chiffre est bien placé ou "1" si le chiffre est présent 
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

		int tabVide[] = new int[nbCase];
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

	public void setStrComparer(String strResuOrdi) {

		strComparer = strResuOrdi;
	}

} 




