package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		
		marche = new Marche(nbEtals);
		
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

	public String afficherVillageois() throws VillageSansChefException {
		
		if (chef == null) {
			throw new VillageSansChefException("Le village n'a pas de chef!");
		}
		
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
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int indiceEtalLibre = marche.trouverEtalLibre();
		if (indiceEtalLibre < 0) {
			chaine.append("Mais tous les étals du marche sont deja occupes...\n");
		} else {
			marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'etal n°" + (indiceEtalLibre+1) + ".\n");
		}
		
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		
		Etal[] etalsProduit = marche.trouverEtals(produit);
		
		if (etalsProduit.length == 0) {
			chaine.append("Aucun vendeur ne vend des " + produit + ".\n");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < etalsProduit.length; ++i) {
				chaine.append("- " + etalsProduit[i].getVendeur().getNom() + "\n");
			}
		}
		
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = rechercherEtal(vendeur);
		return etalVendeur.libererEtal();
	}

	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder("La marche du village \"");
		chaine.append(getNom());
		chaine.append("\" possède plusieurs étals :\n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
	
	// Classe interne Marche
	
	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; ++i) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
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
			int nbEtalsProduit = 0;
			Etal[] etalsProduit;
			
			for (int i = 0; i < etals.length; ++i) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					++nbEtalsProduit;
				}
			}
			
			etalsProduit = new Etal[nbEtalsProduit];
			int j = 0;
			
			for (int i = 0; i < etals.length; ++i) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsProduit[j] = etals[i];
					++j;
				}
			}
			
			return etalsProduit;
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
}