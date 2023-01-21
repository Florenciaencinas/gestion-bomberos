package com.aguillen.gestion.bomberos.model;

import java.util.List;

public class CompradoresRifas {
	
	private List<CompradorVenta> compradorVentaList;
	private List<Rifa> rifasList;
	
	public List<CompradorVenta> getCompradorVentaList() {
		return compradorVentaList;
	}
	public void setCompradorVentaList(List<CompradorVenta> compradorVentaList) {
		this.compradorVentaList = compradorVentaList;
	}
	public List<Rifa> getRifasList() {
		return rifasList;
	}
	public void setRifasList(List<Rifa> rifasList) {
		this.rifasList = rifasList;
	}

}
