/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aguillen.gestion.bomberos.controller.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aguillen.gestion.bomberos.dao.DAORifas;
import com.aguillen.gestion.bomberos.model.Rifa;

/**
 *
 * @author a635019
 */
@RestController
public class RifasWebService {
    private final JdbcTemplate jdbcTemplate;
    private final DAORifas daoRifas;
	
    @Autowired
    public RifasWebService(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            daoRifas = new DAORifas(jdbcTemplate);
    }
    
    @RequestMapping(value = "/rifas/buscar/{id}", method = RequestMethod.POST)
    public Rifa getRifaID(@PathVariable(value = "id") Integer id) {
        return daoRifas.getRifaID(id);
    }

}