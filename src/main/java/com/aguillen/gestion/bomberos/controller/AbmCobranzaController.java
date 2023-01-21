/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aguillen.gestion.bomberos.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aguillen.gestion.bomberos.dao.DAOCobranza;
import com.aguillen.gestion.bomberos.dao.DAORifas;
import com.aguillen.gestion.bomberos.dao.DAOVentas;
import com.aguillen.gestion.bomberos.dto.CobrarRifasDTO;
import com.aguillen.gestion.bomberos.model.CompradorVenta;
import com.aguillen.gestion.bomberos.model.Rifa;

/**
 *
 * @author Abel Guillen
 */

@Controller
@RequestMapping("/cobranza")
public class AbmCobranzaController {
    private final JdbcTemplate jdbcTemplate;
    private final DAOCobranza daoCobranza;
    private final DAOVentas daoVentas;
    
    @Autowired
    public AbmCobranzaController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        daoCobranza = new DAOCobranza(jdbcTemplate);
        daoVentas = new DAOVentas(jdbcTemplate);
    }
    
    @RequestMapping(value = "")
    public String cobranza(Model template, HttpServletRequest request) {
        String usuario = (String) request.getSession().getAttribute("usuario");
        if(usuario == null) return "redirect:" + "/";
        else return "cobranza";
    }
    
    @RequestMapping(value = "/nuevo")
    public String nuevoCobro(Model template) {
        return "nuevoCobro";
    }
    
    @RequestMapping(value = "/nuevo/cobro-multiple")
    public String nuevoCobroMultiple(Model template) {
        ArrayList<CompradorVenta> compradores = daoVentas.getAllVentas();
        template.addAttribute("ventas", compradores);
        return "nuevoCobroMultiple";
    }
    
    @RequestMapping(value = "/modificar/{rifa}")
    public String modificarVenta(Model template,
            @PathVariable(value = "rifa") String rifa,
            @RequestParam(value = "cantCuotas") String cantCuotas) {
        
        CompradorVenta compradorVenta = new CompradorVenta();
        compradorVenta.setRifa(rifa);
        compradorVenta.setCantCuotas(cantCuotas);

        daoCobranza.modificarCobranza(rifa, cantCuotas);
        
        return "redirect:" + "/cobranza/nuevo";
    }
    
    @RequestMapping(
		value = "/modificar/rifas", 
		method = RequestMethod.POST,
		consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )
    public String modificarRifas(
    		@RequestParam(value = "cantCuotas") String cantCuotas,
    		@RequestParam(value = "rifasSeleccionadas") List<String> rifasSeleccionadas) {

        daoCobranza.modificarCobranza(rifasSeleccionadas, cantCuotas);
        
        return "redirect:" + "/cobranza/nuevo/cobro-multiple";
    }
    
}