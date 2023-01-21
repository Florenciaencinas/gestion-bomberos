/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aguillen.gestion.bomberos.model;

import java.util.Date;

/**
 *
 * @author Abel Guillen
 */
public class CompradorVenta {
    Integer id, valor, cuotasPorMes;
    String nombre, direccion, apellido, localidad, telefono, rifa, estado, 
            nroBono, cobrador, vendedor, hasta, zona, cantCuotas, cuotasPagas,
            fechaSuscripcionStr;
    Date fechaSuscripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRifa() {
        return rifa;
    }

    public void setRifa(String rifa) {
        this.rifa = rifa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNroBono() {
        return nroBono;
    }

    public void setNroBono(String nroBono) {
        this.nroBono = nroBono;
    }

    public Date getFechaSuscripcion() {
        return fechaSuscripcion;
    }

    public void setFechaSuscripcion(Date fechaSuscripcion) {
        this.fechaSuscripcion = fechaSuscripcion;
    }

    public String getCobrador() {
        return cobrador;
    }

    public void setCobrador(String cobrador) {
        this.cobrador = cobrador;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getCantCuotas() {
        return cantCuotas;
    }

    public void setCantCuotas(String cantCuotas) {
        this.cantCuotas = cantCuotas;
    }

    public String getCuotasPagas() {
        return cuotasPagas;
    }

    public void setCuotasPagas(String cuotasPagas) {
        this.cuotasPagas = cuotasPagas;
    }

    public String getFechaSuscripcionStr() {
        return fechaSuscripcionStr;
    }

    public void setFechaSuscripcionStr(String fechaSuscripcionStr) {
        this.fechaSuscripcionStr = fechaSuscripcionStr;
    }

	public Integer getCuotasPorMes() {
		return cuotasPorMes;
	}

	public void setCuotasPorMes(Integer cuotasPorMes) {
		this.cuotasPorMes = cuotasPorMes;
	}
}