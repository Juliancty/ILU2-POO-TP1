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
		this.marche = new Marche(nbEtals);
	}
	
	private static class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for(int i = 0; i < etals.length; i++) {
				if(!etals[i].isEtalOccupe()) return i;
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtal = 0;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].contientProduit(produit)) nbEtal++;
			}
			Etal[] etalsProduit = new Etal[nbEtal];
			int i = 0;
			for(int j = 0; j < etals.length; j++) {
				if(etals[j].contientProduit(produit)) {
					etalsProduit[i] = etals[j];
					i++;
				}
			}
			return etalsProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].getVendeur() == gaulois) return etals[i];
			}
			return null;
		}
		
		private String afficherMarche() {
			int nbEtalVide = 0;
			String chaine = "";
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					chaine += etals[i].afficherEtal();
				} else {
					nbEtalVide++;
				}
			}
			chaine += "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
			return chaine;
		}
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
		if(this.chef == null) throw new VillageSansChefException();
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
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int indiceEtalLibre = marche.trouverEtalLibre();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		if(indiceEtalLibre != -1) {
			marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°"+ (indiceEtalLibre+1) + "\n");
		} else {
			chaine.append("Malheureuseument aucun étal n'est disponible.\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsProduit = marche.trouverEtals(produit);
		if(etalsProduit.length == 0) {
			return "Il n'y a pas de vendeur qui propose des " + produit + " au marché\n";
		} 
		if (etalsProduit.length == 1) {
			return "Seul le vendeur " + etalsProduit[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n";
		}
		chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
		for(int i = 0; i < etalsProduit.length; i++) {
			chaine.append("- " + etalsProduit[i].getVendeur().getNom() + "\n");
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		if(etal != null) {
			return etal.libererEtal();
		}
		return vendeur.getNom() + " n'occupe aucun étal\n";
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + this.nom + "\" possède plusieurs étals : \n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
}

















