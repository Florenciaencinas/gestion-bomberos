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
import com.aguillen.gestion.bomberos.model.CompradoresRifas;
import com.aguillen.gestion.bomberos.model.Rifa;

/**
 *
 * @author Abel Guillen
 */
public class DAOReportes {
    private final JdbcTemplate jdbcTemplate;
	
    @Autowired
    public DAOReportes(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public CompradoresRifas getCobranzaPorCobrador(String cobrador) {
        SqlRowSet cobranza = jdbcTemplate.queryForRowSet(
            "SELECT DISTINCT " +
            "   c.rifa, r.valor, r.cabezal, c.cuotasPorMes, " +
            "		CASE c.estado " +
            "			WHEN 'BAJA' THEN 'B' " +
            "			WHEN 'TERMINADA' THEN 'T' " +
            "			ELSE c.cuotasPagas " +
            "		END AS cuotasPagas " +
            "FROM " +
            "   rifas r, comprador c " +
            "WHERE " +
            "   r.primer_numero = c.rifa " +
            "   AND c.estado != 'CONTADO' " +
            "	AND c.cobrador = ?" +
            "ORDER BY " +
            "   primer_numero;", cobrador
        );

        ArrayList<CompradorVenta> listaDeCobranza = new ArrayList<CompradorVenta>();
        ArrayList<Rifa> listaDeRifas = new ArrayList<Rifa>();
        while (cobranza.next()) {
            String numRifa = cobranza.getString("rifa");
            Integer valor = cobranza.getInt("valor");
            String cuotasPagas = cobranza.getString("cuotasPagas");
            Integer cuotasPorMes = cobranza.getInt("cuotasPorMes");
            Integer cabezal = cobranza.getInt("cabezal");
            
            CompradorVenta comprador = new CompradorVenta();
            comprador.setRifa(numRifa);
            comprador.setValor(valor);
            comprador.setCuotasPagas(cuotasPagas);
            comprador.setCuotasPorMes(cuotasPorMes);
            
            Rifa rifa = new Rifa();
            rifa.setCabezal(cabezal);
            
            listaDeCobranza.add(comprador);
            listaDeRifas.add(rifa);
        }
        
        CompradoresRifas compradoresRifas = new CompradoresRifas();
        compradoresRifas.setCompradorVentaList(listaDeCobranza);
        compradoresRifas.setRifasList(listaDeRifas);
        return compradoresRifas;
    }
    
    public CompradoresRifas getCobranzaPorZona(String zona) {
        SqlRowSet cobranza = jdbcTemplate.queryForRowSet(
            "SELECT DISTINCT " +
            "   c.rifa, r.valor, r.cabezal, c.cuotasPorMes, " +
            "		CASE c.estado " +
            "			WHEN 'BAJA' THEN 'B' " +
            "			WHEN 'TERMINADA' THEN 'T' " +
            "			ELSE c.cuotasPagas " +
            "		END AS cuotasPagas " +
            "FROM " +
            "   rifas r, comprador c " +
            "WHERE " +
            "   r.primer_numero = c.rifa " +
            "   AND c.estado != 'CONTADO' " +
            "	AND c.zona = ?" +
            "ORDER BY " +
            "   primer_numero;", zona
        );

        ArrayList<CompradorVenta> listaDeCobranza = new ArrayList<CompradorVenta>();
        ArrayList<Rifa> listaDeRifas = new ArrayList<Rifa>();
        while (cobranza.next()) {
            String numRifa = cobranza.getString("rifa");
            Integer valor = cobranza.getInt("valor");
            String cuotasPagas = cobranza.getString("cuotasPagas");
            Integer cuotasPorMes = cobranza.getInt("cuotasPorMes");
            Integer cabezal = cobranza.getInt("cabezal");
            
            CompradorVenta comprador = new CompradorVenta();
            comprador.setRifa(numRifa);
            comprador.setValor(valor);
            comprador.setCuotasPagas(cuotasPagas);
            comprador.setCuotasPorMes(cuotasPorMes);
            
            Rifa rifa = new Rifa();
            rifa.setCabezal(cabezal);
            
            listaDeCobranza.add(comprador);
            listaDeRifas.add(rifa);
        }
        
        CompradoresRifas compradoresRifas = new CompradoresRifas();
        compradoresRifas.setCompradorVentaList(listaDeCobranza);
        compradoresRifas.setRifasList(listaDeRifas);
        return compradoresRifas;
    }
    
    public ArrayList<CompradorVenta> getReportePorVendedor(String vendedor) {
        SqlRowSet reportePorVendedor;
        reportePorVendedor = jdbcTemplate.queryForRowSet(
                "SELECT DISTINCT" +
                "   r.primer_numero, r.valor, c.apellido, c.estado, " +
                "   c.nombre, c.direccion, c.telefono, r.cuotas,   " +
                "   c.cuotasPagas, c.fechaSuscripcion " +
                "FROM " +
                "   rifas r, comprador c " +
                "WHERE " +
                "   r.primer_numero = c.rifa " +
                "   AND c.vendedor = ? " +
                "ORDER BY " + 
                "   r.primer_numero, c.apellido, c.nombre;", vendedor);

        ArrayList<CompradorVenta> listaDeVentas = new ArrayList<CompradorVenta>();
        while (reportePorVendedor.next()) {
            String rifa = reportePorVendedor.getString("primer_numero");
            Integer valor = reportePorVendedor.getInt("valor");
            String apellido = reportePorVendedor.getString("apellido");
            String estado = reportePorVendedor.getString("estado");
            String nombre = reportePorVendedor.getString("nombre");
            String direccion = reportePorVendedor.getString("direccion");
            String telefono = reportePorVendedor.getString("telefono");
            String cantCuotas = reportePorVendedor.getString("cuotas");
            String cuotasPagas = reportePorVendedor.getString("cuotasPagas");
            Date fechaSuscripcion = reportePorVendedor.getDate("fechaSuscripcion");
            
            CompradorVenta comprador = new CompradorVenta();
            comprador.setRifa(rifa);
            comprador.setValor(valor);
            comprador.setApellido(apellido);
            comprador.setEstado(estado);
            comprador.setNombre(nombre);
            comprador.setDireccion(direccion);
            comprador.setTelefono(telefono);
            comprador.setCantCuotas(cantCuotas);
            comprador.setCuotasPagas(cuotasPagas);
            comprador.setFechaSuscripcion(fechaSuscripcion);
            
            listaDeVentas.add(comprador);
        }
        return listaDeVentas;
    }
    
    public ArrayList<CompradorVenta> getReporteTodos() {
        SqlRowSet reportePorVendedor;
        reportePorVendedor = jdbcTemplate.queryForRowSet(
                "SELECT DISTINCT" +
                "   r.primer_numero, r.valor, c.apellido, c.estado, " +
                "   c.nombre, c.direccion, c.telefono, r.cuotas,   " +
                "   c.cuotasPagas, c.fechaSuscripcion " +
                "FROM " +
                "   rifas r, comprador c " +
                "WHERE " +
                "   r.primer_numero = c.rifa " + 
                "ORDER BY " + 
                "   r.primer_numero, c.apellido, c.nombre; ");
                
        ArrayList<CompradorVenta> listaDeVentas = new ArrayList<CompradorVenta>();
        while (reportePorVendedor.next()) {
            String rifa = reportePorVendedor.getString("primer_numero");
            Integer valor = reportePorVendedor.getInt("valor");
            String apellido = reportePorVendedor.getString("apellido");
            String estado = reportePorVendedor.getString("estado");
            String nombre = reportePorVendedor.getString("nombre");
            String direccion = reportePorVendedor.getString("direccion");
            String telefono = reportePorVendedor.getString("telefono");
            String cantCuotas = reportePorVendedor.getString("cuotas");
            String cuotasPagas = reportePorVendedor.getString("cuotasPagas");
            Date fechaSuscripcion = reportePorVendedor.getDate("fechaSuscripcion");
            
            CompradorVenta comprador = new CompradorVenta();
            comprador.setRifa(rifa);
            comprador.setValor(valor);
            comprador.setApellido(apellido);
            comprador.setEstado(estado);
            comprador.setNombre(nombre);
            comprador.setDireccion(direccion);
            comprador.setTelefono(telefono);
            comprador.setCantCuotas(cantCuotas);
            comprador.setCuotasPagas(cuotasPagas);
            comprador.setFechaSuscripcion(fechaSuscripcion);
            
            listaDeVentas.add(comprador);
        }
        return listaDeVentas;
    }
}
