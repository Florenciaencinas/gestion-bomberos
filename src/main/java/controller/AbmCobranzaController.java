/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOCobranza;
import javax.servlet.http.HttpServletRequest;
import model.CompradorVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Abel Guillen
 */

@Controller
@RequestMapping("/cobranza")
public class AbmCobranzaController {
    private final JdbcTemplate jdbcTemplate;
    private final DAOCobranza daoCobranza;
    
    @Autowired
    public AbmCobranzaController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        daoCobranza = new DAOCobranza(jdbcTemplate);
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
    
}