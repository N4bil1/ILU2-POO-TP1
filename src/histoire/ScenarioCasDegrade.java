package histoire;

import villagegaulois.Etal;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) {
		Etal etal = new Etal();
		try {
			etal.acheterProduit(9, null);

		} catch (IllegalArgumentException | IllegalStateException e) {
			e.printStackTrace();
		}
		System.out.println("Fin du test");
	}
}
