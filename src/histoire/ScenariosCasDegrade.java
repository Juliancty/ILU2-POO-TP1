package histoire;

import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenariosCasDegrade {
	
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);
		etal.occuperEtal(assurancetourix, "fraise", 0);
		etal.acheterProduit(1,assurancetourix);
		System.out.println("Fin du test");
	}
}
