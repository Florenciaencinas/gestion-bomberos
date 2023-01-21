/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aguillen.gestion.bomberos.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.aguillen.gestion.bomberos.model.CompradorVenta;

/**
 *
 * @author a635019
 */
public class DAOVentas {
    private final JdbcTemplate jdbcTemplate;
	
    @Autowired
    public DAOVentas(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public void deleteAllComprador() {
    	jdbcTemplate.update("DELETE FROM comprador;");
    }
    
    public void insertVenta(CompradorVenta c) {
        //Inserto Rifa
        this.jdbcTemplate.update(
                "INSERT INTO comprador (nombre, direccion, apellido, localidad, "
                + "telefono, rifa, estado, fechaSuscripcion, nroBono, cobrador, "
                + "vendedor, zona) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);", c.getNombre(),
                c.getDireccion(), c.getApellido(), c.getLocalidad(),
                c.getTelefono(), c.getRifa(), c.getEstado(), c.getFechaSuscripcion(),
                c.getNroBono(),c.getCobrador(),c.getVendedor(), c.getZona());
    }
    
    public ArrayList<CompradorVenta> getAllVentas() {
        SqlRowSet compradores;
        compradores = jdbcTemplate.queryForRowSet("SELECT * FROM comprador ORDER BY rifa;");

        ArrayList<CompradorVenta> listaDeCompradorVentas = new ArrayList<CompradorVenta>();
        while (compradores.next()) {
            Integer id = compradores.getInt("id");
            String nombre = compradores.getString("nombre");
            String direccion = compradores.getString("direccion");
            String apellido = compradores.getString("apellido");
            String localidad = compradores.getString("localidad");
            String telefono = compradores.getString("telefono");
            String rifa = compradores.getString("rifa");
            String estado = compradores.getString("estado");
            Date fechaSuscripcion = compradores.getDate("fechaSuscripcion");
            String nroBono = compradores.getString("nroBono");
            String cobrador = compradores.getString("cobrador");
            String vendedor = compradores.getString("vendedor");
            String zona = compradores.getString("zona");
            String cuotasPagas = compradores.getString("cuotasPagas");
            Integer cuotasPorMes = compradores.getInt("cuotasPorMes");
            
            CompradorVenta comprador = new CompradorVenta();
            comprador.setId(id);
            comprador.setNombre(nombre);
            comprador.setDireccion(direccion);
            comprador.setApellido(apellido);
            comprador.setLocalidad(localidad);
            comprador.setTelefono(telefono);
            comprador.setRifa(rifa);
            comprador.setEstado(estado);
            comprador.setFechaSuscripcion(fechaSuscripcion);
            comprador.setNroBono(nroBono);
            comprador.setCobrador(cobrador);
            comprador.setVendedor(vendedor);
            comprador.setZona(zona);
            comprador.setCantCuotas(cuotasPagas);
            comprador.setCuotasPorMes(cuotasPorMes);
            
            listaDeCompradorVentas.add(comprador);
        }
        return listaDeCompradorVentas;
    }
    
    public void eliminarVenta(String venta) {
        jdbcTemplate.update(
                "DELETE FROM comprador WHERE id = ?;", venta);
    }
    
    public CompradorVenta getVentaID(Integer id) {
        SqlRowSet ventas;
        ventas = jdbcTemplate.queryForRowSet(
                "SELECT c.id, c.nombre, c.direccion, c.apellido, c.localidad, " +
                "c.telefono, c.rifa, c.estado, c.fechaSuscripcion, c.nroBono, " +
                "c.cobrador, c.vendedor, r.valor, max(r.numero) hasta, c.zona, c.cuotasPorMes " +
                "FROM comprador c, rifas r " +
                "WHERE c.rifa = r.primer_numero AND c.id = ?;", id);

        ventas.next();
        Integer idVenta = ventas.getInt("id");
        String nombre = ventas.getString("nombre");
        String direccion = ventas.getString("direccion");
        String apellido = ventas.getString("apellido");
        String localidad = ventas.getString("localidad");
        String telefono = ventas.getString("telefono");
        String rifa = ventas.getString("rifa");
        String estado = ventas.getString("estado");
        Date fechaSuscripcion = ventas.getDate("fechaSuscripcion");
        String nroBono = ventas.getString("nroBono");
        String cobrador = ventas.getString("cobrador");
        String vendedor = ventas.getString("vendedor");
        Integer valor = ventas.getInt("valor");
        String hasta = ventas.getString("hasta");
        String zona = ventas.getString("zona");
        Integer cuotasPorMes = ventas.getInt("cuotasPorMes");

        CompradorVenta comprador = new CompradorVenta();
        comprador.setId(idVenta);
        comprador.setNombre(nombre);
        comprador.setDireccion(direccion);
        comprador.setApellido(apellido);
        comprador.setLocalidad(localidad);
        comprador.setTelefono(telefono);
        comprador.setRifa(rifa);
        comprador.setEstado(estado);
        comprador.setFechaSuscripcion(fechaSuscripcion);
        comprador.setNroBono(nroBono);
        comprador.setCobrador(cobrador);
        comprador.setVendedor(vendedor);
        comprador.setValor(valor);
        comprador.setHasta(hasta);
        comprador.setZona(zona);
        comprador.setCuotasPorMes(cuotasPorMes);
        
        return comprador;
    }
    
    public void modificarVenta(CompradorVenta v) {
        this.jdbcTemplate.update(
                "UPDATE comprador SET nombre = ?, direccion = ?, apellido = ?,"
                + "localidad = ?, telefono = ?, rifa = ?, estado = ?, "
                + "nroBono = ?, cobrador = ?, vendedor = ?, zona = ?, cuotasPorMes = ? "
                + "where id = ? ;",v.getNombre(), v.getDireccion(), 
                v.getApellido(), v.getLocalidad(), v.getTelefono(), v.getRifa(), 
                v.getEstado(), v.getNroBono(), v.getCobrador(), v.getVendedor(),
                v.getZona(), v.getCuotasPorMes(), v.getId());
    }
}