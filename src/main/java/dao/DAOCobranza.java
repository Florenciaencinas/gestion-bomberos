/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.CompradorVenta;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author a635019
 */
public class DAOCobranza {
    private final JdbcTemplate jdbcTemplate;
	
    @Autowired
    public DAOCobranza(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public CompradorVenta getCobranzaPorRifa(String nroRifa) {
        SqlRowSet cobranza;
        cobranza = jdbcTemplate.queryForRowSet(
                "SELECT rifa, nombre, apellido " +
                "FROM comprador " +
                "WHERE rifa = ?;", nroRifa);

        cobranza.next();
        String rifa = cobranza.getString("rifa");
        String nombre = cobranza.getString("nombre");
        String apellido = cobranza.getString("apellido");

        CompradorVenta cv = new CompradorVenta();
        cv.setRifa(rifa);
        cv.setApellido(apellido);
        cv.setNombre(nombre);
        
        return cv;
    }
    
    public void modificarCobranza(String nroRifa, String cantCuotas) {
        if(cantCuotas.equals("-1")) {
        	this.jdbcTemplate.update(
                    "UPDATE comprador SET cuotasPagas = cuotasPagas + ? "
                    + "where rifa = ? ;",cantCuotas, nroRifa);
        } else {
	        this.jdbcTemplate.update(
	                "UPDATE comprador SET cuotasPagas = cuotasPagas + ?, cuotasPorMes = ? "
	                + "where rifa = ? ;",cantCuotas, cantCuotas, nroRifa);
        }
    }
    
    public void modificarCobranza(List<String> rifas, String cantCuotas) {
    	
    	String inClause = buildINClause(rifas);
    	if(cantCuotas.equals("-1")) {
        	this.jdbcTemplate.update(
                    "UPDATE comprador SET cuotasPagas = cuotasPagas + ? "
                    + "where rifa " + inClause, cantCuotas);
        } else {
	        this.jdbcTemplate.update(
	                "UPDATE comprador SET cuotasPagas = cuotasPagas + ?, cuotasPorMes = ? "
	                + "where rifa " + inClause, cantCuotas, cantCuotas);
        }
    }
    
    private String buildINClause(List<String> rifas) {
    	String inClause = "IN (";
    	for(int i=0;i<rifas.size();i++) {
    		if(i==rifas.size()-1) {
    			inClause+="'" + rifas.get(i) + "'";
    		} else {
    			inClause+="'" + rifas.get(i) + "',";
    		}
    	}
    	inClause+=") ;";
    	return inClause;
    }
     
}