/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.webservices;

import dao.DAOVentas;
import model.CompradorVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author a635019
 */
@RestController
public class VentasWebService {
    private final JdbcTemplate jdbcTemplate;
    private final DAOVentas daoVentas;
	
    @Autowired
    public VentasWebService(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            daoVentas = new DAOVentas(jdbcTemplate);
    }
    
    @RequestMapping(value = "/ventas/buscar/{id}", method = RequestMethod.POST)
    public CompradorVenta getVentaID(@PathVariable(value = "id") Integer id) {
        return daoVentas.getVentaID(id);
    }

}