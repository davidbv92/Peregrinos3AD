package com.luisdbb.tarea3AD2024base.modelo;

/**
 * @author David Ballesteros
 * @since 27-02-2025
 */
public class SelladoData {

	private static SelladoData instancia;
    private boolean vip;
    private Peregrino peregrino;
    private Parada parada;
    
    private SelladoData() {
    }
    
    public static SelladoData getInstancia() {
        if (instancia == null) {
            instancia = new SelladoData();
        }
        return instancia;
    }

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	public Parada getParada() {
		return parada;
	}

	public void setParada(Parada parada) {
		this.parada = parada;
	}
}
