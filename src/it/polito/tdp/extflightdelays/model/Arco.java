package it.polito.tdp.extflightdelays.model;

public class Arco {

	int airport1;
	int airport2;
	double peso;
	
	public int getAirport1() {
		return airport1;
	}
	public void setAirport1(int airport1) {
		this.airport1 = airport1;
	}
	public int getAirport2() {
		return airport2;
	}
	public void setAirport2(int airport2) {
		this.airport2 = airport2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public Arco(int airport1, int airport2, double peso) {
		super();
		this.airport1 = airport1;
		this.airport2 = airport2;
		this.peso = peso;
	}
		
}
