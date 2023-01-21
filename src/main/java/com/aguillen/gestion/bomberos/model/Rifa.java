/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aguillen.gestion.bomberos.model;

/**
 *
 * @author Abel Guillen
 */
public class Rifa {
    Integer id, valor, cabezal, desde, hasta, serie, cuotas;
    String numero, primerNumero, hastaStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getCabezal() {
        return cabezal;
    }

    public void setCabezal(Integer cabezal) {
        this.cabezal = cabezal;
    }

    public Integer getDesde() {
        return desde;
    }

    public void setDesde(Integer desde) {
        this.desde = desde;
    }

    public Integer getHasta() {
        return hasta;
    }

    public void setHasta(Integer hasta) {
        this.hasta = hasta;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer plan) {
        this.cuotas = plan;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPrimerNumero() {
        return primerNumero;
    }

    public void setPrimerNumero(String primerNumero) {
        this.primerNumero = primerNumero;
    }

    public String getHastaStr() {
        return hastaStr;
    }

    public void setHastaStr(String hastaStr) {
        this.hastaStr = hastaStr;
    }
}