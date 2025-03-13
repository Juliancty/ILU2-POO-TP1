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
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] tabEtal = this.marche.trouverEtals(produit);
		if(tabEtal.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché\n");
		} else if(tabEtal.length == 1){
			chaine.append("Seul le vendeur " + tabEtal[0].getVendeur().getNom() + " propose des " + produit + " au marché\n");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for(int i = 0; i < tabEtal.length; i++) {
				chaine.append("- " + tabEtal[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return this.marche.trouverVendeur(vendeur);
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int quantite) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + quantite + " " + produit + "\n");
		int etalLibre = this.marche.trouverEtalLibre();
		if( etalLibre != -1) {
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (etalLibre+1) + "\n");
			this.marche.utiliserEtal(etalLibre, vendeur, produit, quantite);
		} else {
			chaine.append("Mais aucun étal n'est disponible...\n");
		}
		return chaine.toString();
	}
	
	public String afficherMarche() {
		return this.marche.afficherMarche();
	}
	
	public String partirVendeur(Gaulois vendeur) {
		return this.marche.trouverVendeur(vendeur).libererEtal();
	}
	
	
	private class Marche{
		public Etal[] etals;
		
		public Marche(int nbEtal) {
			this.etals = new Etal[nbEtal];
			for(int i = 0; i < nbEtal; i++) {
				this.etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit){
			this.etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
		}
		
		public int trouverEtalLibre() {
			for(int i = 0; i < this.etals.length; i++) {
				if(!(this.etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtal = 0;
			for(int i = 0; i < this.etals.length; i++) {
				if(this.etals[i].contientProduit(produit)) {
					nbEtal++;
				}
			}
			Etal[] tabEtal = new Etal[nbEtal];
			int j = 0;
			for(int i = 0; i < this.etals.length; i++) {
				if(this.etals[i].contientProduit(produit)) {
					tabEtal[j] = this.etals[i];
					j++;
				}
			}
			return tabEtal;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i < this.etals.length; i++) {
				if(this.etals[i].getVendeur() == gaulois) {
					return this.etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			int nbEtalVide = 0;
			StringBuilder chaine = new StringBuilder();
			for(int i = 0; i < this.etals.length; i++) {
				if(this.etals[i].isEtalOccupe()) {
					chaine.append(this.etals[i].afficherEtal() + "\n");
				} else {
					nbEtalVide++;
				}
			}
			if(nbEtalVide > 0) {
				chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
	}
}