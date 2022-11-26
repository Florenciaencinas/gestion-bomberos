/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import model.CompradorVenta;
import model.Rifa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        SqlRowSet cobranza;
        
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
}