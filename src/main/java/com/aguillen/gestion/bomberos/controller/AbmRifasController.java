/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aguillen.gestion.bomberos.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aguillen.gestion.bomberos.dao.DAORifas;
import com.aguillen.gestion.bomberos.model.Rifa;

/**
 *
 * @author Abel Guillen
 */
@Controller
@RequestMapping("/rifas")
public class AbmRifasController {
    private final JdbcTemplate jdbcTemplate;
    private final DAORifas daoRifas;

    @Autowired
    public AbmRifasController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        daoRifas = new DAORifas(jdbcTemplate);
    }
    
    @RequestMapping(value = "")
    public String rifas(Model template, HttpServletRequest request) {
        String usuario = (String) request.getSession().getAttribute("usuario");
        if(usuario == null) return "redirect:" + "/";
        else return "rifas";
    }
    
    @RequestMapping(value = "/nuevas")
    public String nuevasRifas(Model template) {
        return "nuevasRifas";
    }
    
    @RequestMapping(value = "/nuevas", method = RequestMethod.POST)
    public String nuevasRifasPost(Model template,
            @RequestParam(value = "valor") Integer valor,
            @RequestParam(value = "cabezal") Integer cabezal,
            @RequestParam(value = "desde") Integer desde,
            @RequestParam(value = "hasta") Integer hasta,
            @RequestParam(value = "serie") Integer serie,
            @RequestParam(value = "cuotas") Integer cuotas){
        
        ArrayList<Rifa> listaDeRifas = daoRifas.getAllRifas();
        
        Rifa rifa = new Rifa();
        rifa.setValor(valor);
        rifa.setCabezal(cabezal);
        rifa.setDesde(desde);
        rifa.setHasta(hasta);
        rifa.setSerie(serie);
        rifa.setCuotas(cuotas);
        
        String msjValidacion = validarRifas(rifa);
        if(msjValidacion.equals("OK")) {
            if(cabezal == 1) {
                for(Integer i=desde;i<=hasta;i++) {
                    String numero = formatearNumero(i.toString());
                    rifa.setPrimerNumero(numero);
                    rifa.setNumero(numero);
                    String existe = existeNumero(listaDeRifas, rifa);
                    if(existe.equals("OK")) {
                        daoRifas.insertRifa(rifa);
                    } else {
                        template.addAttribute("error", true);
                        template.addAttribute("mensaje", existe);
                        template.addAttribute("href", "rifas/nuevas");
                        return "error";
                    }
                }
            } else {
                int cont = 0;
                Integer primerNumeroTemp = 0;
                for(Integer i=desde;i<=hasta;i++) {
                    //Formateo y seteo el numero
                    String numero = formatearNumero(i.toString());
                    rifa.setNumero(numero);
                    //Seteo el primer numero si es la primer rifa
                    if(cont == 0) {
                        rifa.setPrimerNumero(numero);
                        primerNumeroTemp = i;
                    } else {
                        if(i==primerNumeroTemp+cabezal) {
                            rifa.setPrimerNumero(numero);
                            primerNumeroTemp = i;
                        } else {
                            String primerNumero = formatearNumero(primerNumeroTemp.toString());
                            rifa.setPrimerNumero(primerNumero);
                        }
                    }
                    cont++;
                    String existe = existeNumero(listaDeRifas, rifa);
                    if(existe.equals("OK")) {
                        daoRifas.insertRifa(rifa);
                    } else {
                        template.addAttribute("error", true);
                        template.addAttribute("mensaje", existe);
                        template.addAttribute("href", "rifas/nuevas");
                        return "error";
                    }
                    
                }
            }
            return "redirect:/rifas/lista";
        } else {
            template.addAttribute("error", true);
            template.addAttribute("mensaje", msjValidacion);
            template.addAttribute("href", "rifas/nuevas");
            return "error";
        }
    }
    
    public String existeNumero(ArrayList<Rifa> listaDeRifas, Rifa r) {
        String numeroAComparar = r.getNumero();
        for(Rifa rifa : listaDeRifas) {
            String numero = rifa.getNumero();
            if(numero.equals(numeroAComparar)) {
                return "No se pudo insertar la rifa: " + numero + 
                        " porque ya existe en la base de datos.";
            }
        }
        return "OK";
    }
    
    public String formatearNumero(String numero) {
        if(numero.length() == 1) numero = "00" + numero;
        if(numero.length() == 2) numero = "0" + numero;
        return numero;
    }
    
    public boolean esPar(Integer numero) {
        if(numero%2 == 0) return true;
        return false;
    }
    
    public String validarRifas(Rifa r) {
        Integer desde = r.getDesde();
        Integer hasta = r.getHasta();
        if(hasta < desde) 
            return "El valor del campo 'Desde' no puede ser mayor al valor del campo 'Hasta'.";
        else if((hasta - desde + 1)%r.getCabezal()!=0) 
            return "La cantidad de números a cargar no se corresponde con los cabezales ingresados.";
        return "OK";
    }
    
    @RequestMapping(value = "/lista")
    public String listaRifas(Model template) {
        ArrayList<Rifa> rifas = daoRifas.getAllRifasGroup();
        template.addAttribute("rifas", rifas);
        return "listaRifas";
    }
    
    //Redirige mediante AJAX
    @RequestMapping(value = "/eliminar/{rifa}", method = RequestMethod.POST)
    @ResponseBody
    public String eliminarRifa(Model template,
            @PathVariable(value = "rifa") String rifa) {
        daoRifas.eliminarRifa(rifa);
        return "/rifas/lista";
    }

    @RequestMapping(value = "/modificar/{primerNumero}")
    public String modificarRifa(Model template,
            @PathVariable(value = "primerNumero") String primerNumero,
            @RequestParam(value = "valor") Integer valor) {
        
        Rifa r = new Rifa();
        r.setPrimerNumero(primerNumero);
        r.setValor(valor);
        
        daoRifas.modificarRifa(r);
        
        return "redirect:" + "/rifas/lista";
    }
}