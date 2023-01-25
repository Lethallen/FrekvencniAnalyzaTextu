package analyza;

import java.io.Serializable;

// jeden soubor
public class Slovo implements Serializable {
	
	private String slovo;
	private int pocet_vyskytu;
	private double procento_vyskytu;
	
	
	public Slovo(String slovo, int pocet_vyskytu, double p) {
		super();
		this.slovo = slovo;
		this.pocet_vyskytu = pocet_vyskytu;
		this.procento_vyskytu = p;
	}
	
	

	
	public String getSlovo() {
		return slovo;
	}
	
	public void setSlovo(String slovo) {
		this.slovo = slovo;
	}
	
	public int getPocet_vyskytu() {
		return pocet_vyskytu;
	}
	
	public void setPocet_vyskytu(int pocet_vyskytu) {
		this.pocet_vyskytu = pocet_vyskytu;
	}




	public double getProcento_vyskytu() {
		return procento_vyskytu;
	}




	public void setProcento_vyskytu(double procento_vyskytu) {
		this.procento_vyskytu = procento_vyskytu;
	}




	public String getKolik(int kolik) {
		// TODO Auto-generated method stub
		return null;
	}
	

	}
	

