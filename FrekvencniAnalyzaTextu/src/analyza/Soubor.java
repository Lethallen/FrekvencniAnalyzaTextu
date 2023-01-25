package analyza;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Soubor extends Vector{
	
	//hledá slova
	public Soubor hledejSlovo(String s, int kolik) {
		Soubor vysledek = new Soubor();
		String n;
		int i;
		s = s.toUpperCase();
		for (i = 0; i < size(); i++) {
			n = ((Slovo) get(i)).getKolik(kolik).toUpperCase();
			if (n.indexOf(s) != -1) {
				vysledek.add(get(i));
			}
		}
		return (vysledek.size() != 0) ? vysledek : null;
	}
	
	//seøazení slov
	public void seradit(int jak) {
		final int kolik = jak;
		Comparator c = new Comparator() {
			public int compare(Object o1, Object o2) {
				Slovo s1 = (Slovo) o1;
				Slovo s2 = (Slovo) o2;
				return s1.getKolik(kolik).compareToIgnoreCase(s2.getKolik(kolik));
			}
		};
		Collections.sort(this, c);
	}

	

}
