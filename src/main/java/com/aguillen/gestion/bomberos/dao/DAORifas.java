/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aguillen.gestion.bomberos.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.aguillen.gestion.bomberos.model.Rifa;

/**
 *
 * @author a635019
 */
public class DAORifas {
    private final JdbcTemplate jdbcTemplate;
	
    @Autowired
    public DAORifas(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public void deleteAllRifas() {
    	jdbcTemplate.update("DELETE FROM rifas;");
    }
    
    public void insertRifa(Rifa r) {
        //Inserto Rifa
        this.jdbcTemplate.update(
                "INSERT INTO rifas (valor, cabezal, cuotas, numero, primer_numero) " +
                "VALUES (?,?,?,?,?);", r.getValor(), r.getCabezal(), r.getCuotas(),
                r.getNumero(), r.getPrimerNumero());
    }
    
    public ArrayList<Rifa> getAllRifas() {
        SqlRowSet rifas;
        rifas = jdbcTemplate.queryForRowSet("SELECT * FROM rifas;");

        ArrayList<Rifa> listaRifas = new ArrayList<Rifa>();
        while (rifas.next()) {
            Integer id = rifas.getInt("id");
            Integer valor = rifas.getInt("valor");
            Integer cabezal = rifas.getInt("cabezal");
            Integer cuotas = rifas.getInt("cuotas");
            String numero = rifas.getString("numero");
            String primerNumero = rifas.getString("primer_numero");
            
            Rifa r = new Rifa();
            r.setId(id);
            r.setValor(valor);
            r.setCabezal(cabezal);
            r.setCuotas(valor);
            r.setNumero(numero);
            r.setPrimerNumero(primerNumero);
            
            listaRifas.add(r);
        }
        return listaRifas;
    }
    
    public ArrayList<Rifa> getAllRifasGroup() {
        SqlRowSet rifas;
        rifas = jdbcTemplate.queryForRowSet(
                "SELECT *," +
                "CASE " +
                "   WHEN LENGTH(numero + cabezal -1) = 1" +
                "       THEN CONCAT('00', numero + cabezal -1)" +
                "   WHEN LENGTH(numero + cabezal -1) = 2" +
                "	THEN CONCAT('0', numero + cabezal -1)" +
                "   ELSE (numero + cabezal -1)" +
                "END AS hasta " +
                "FROM rifas GROUP BY primer_numero;");

        ArrayList<Rifa> listaRifas = new ArrayList<Rifa>();
        while (rifas.next()) {
            Integer id = rifas.getInt("id");
            Integer valor = rifas.getInt("valor");
            Integer cabezal = rifas.getInt("cabezal");
            Integer cuotas = rifas.getInt("cuotas");
            String numero = rifas.getString("numero");
            String primerNumero = rifas.getString("primer_numero");
            String hasta = rifas.getString("hasta");
            
            Rifa r = new Rifa();
            r.setId(id);
            r.setValor(valor);
            r.setCabezal(cabezal);
            r.setCuotas(cuotas);
            r.setNumero(numero);
            r.setPrimerNumero(primerNumero);
            r.setHastaStr(hasta);
            
            listaRifas.add(r);
        }
        return listaRifas;
    }
    
    public void eliminarRifa(String rifa) {
        jdbcTemplate.update(
                "DELETE FROM rifas WHERE primer_numero = ?;", rifa);
    }
    
    public Rifa getRifaID(Integer id) {
        SqlRowSet rifas;
        rifas = jdbcTemplate.queryForRowSet(
                "SELECT * " +
                "FROM rifas " +
                "WHERE id = ?;", id);

        rifas.next();
        Integer idRifa = rifas.getInt("id");
        Integer valor = rifas.getInt("valor");
        Integer cabezal = rifas.getInt("cabezal");
        Integer cuotas = rifas.getInt("cuotas");
        String numero = rifas.getString("numero");
        String primerNumero = rifas.getString("primer_numero");

        Rifa r = new Rifa();
        r.setId(id);
        r.setValor(valor);
        r.setCabezal(cabezal);
        r.setCuotas(cuotas);
        r.setNumero(numero);
        r.setPrimerNumero(primerNumero);
        
        return r;
    }
    
    public void modificarRifa(Rifa r) {
        this.jdbcTemplate.update(
                "UPDATE rifas SET valor = ? where primer_numero = ? ;",r.getValor(), r.getPrimerNumero());
    }
}