package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	
//	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		
//		marche = new Marche(nbEtals);
		
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	/*** TP1 ***/
	
	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			assert (nbEtals > 0);
			etals = new Etal[nbEtals];
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			//TODO exception : indice out of range
			if (etals[indiceEtal].isEtalOccupe()) {
				System.out.println("L'étal " + indiceEtal + " est deja occupé !");
			} else {
				System.out.println("Le villageois " + vendeur.getNom() + " s'installe sur l'étal " + indiceEtal + ".");
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
		}
		
		private int trouverEtalLibre() {
			int indiceEtalLibre = -1;
			for (int i = 0; i < etals.length; ++i) {
				if (!etals[i].isEtalOccupe()) {
					indiceEtalLibre = i;
					break;
				}
			}
			return indiceEtalLibre;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalsOccupes = 0;
			Etal[] etalsOccupes;
			
			for (int i = 0; i < etals.length; ++i) {
				if (etals[i].isEtalOccupe()) {
					++nbEtalsOccupes;
				}
			}
			
			etalsOccupes = new Etal[nbEtalsOccupes];
			int j = 0;
			
			for (int i = 0; i < etals.length; ++i) {
				if (etals[i].isEtalOccupe()) {
					etalsOccupes[j] = etals[i];
					++j;
				}
			}
			
			return etalsOccupes;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			Etal etalVendeur = null;
			
			for (int i = 0; i < etals.length; ++i) {
				if (etals[i].getVendeur().getNom().equals(gaulois.getNom())) {
					etalVendeur = etals[i];
					break;
				}
			}
			
			return etalVendeur;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalsLibres = etals.length;
			
			for (int i = 0; i < etals.length; ++i) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
					--nbEtalsLibres;
				}
			}
			if (nbEtalsLibres != 0) {
				chaine.append("Il reste " + nbEtalsLibres + " étals non utilisés dans le marché.\n");
			}
			
			return chaine.toString();
		}
		
	}
	
	public static void main(String[] args) {
		Village village = new Village("le village des irréductibles", 10);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		Gaulois obelix = new Gaulois("Obélix", 25);
		Gaulois asterix = new Gaulois("Astérix", 8);
		
		Etal etal1 = new Etal();
		Etal etal2 =  new Etal();
	
		
		Marche marche = new Marche(2);
		
		marche.etals[0] = etal1;
		marche.etals[1] = etal2;
		
		marche.utiliserEtal(0, asterix, "Epee", 4);
		marche.utiliserEtal(1, obelix, "Bouclier", 2);
		marche.trouverEtalLibre();
		marche.trouverEtals("Epee");
		marche.trouverVendeur(asterix);
		System.out.println(marche.afficherMarche());

	}
}