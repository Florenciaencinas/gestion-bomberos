/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aguillen.gestion.bomberos.controller.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aguillen.gestion.bomberos.dao.DAOCobranza;
import com.aguillen.gestion.bomberos.dto.CobrarRifasDTO;
import com.aguillen.gestion.bomberos.model.CompradorVenta;

/**
 *
 * @author a635019
 */
@RestController
public class CobranzaWebService {
    private final JdbcTemplate jdbcTemplate;
    private final DAOCobranza daoCobranza;
	
    @Autowired
    public CobranzaWebService(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            daoCobranza = new DAOCobranza(jdbcTemplate);
    }
    
    @RequestMapping(value = "/cobranza/buscar/{nroRifa}", method = RequestMethod.POST)
    public CompradorVenta getCobranzaPorRifa(@PathVariable(value = "nroRifa") String nroRifa) {
        return daoCobranza.getCobranzaPorRifa(nroRifa);
    }

}