/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAORifas;
import dao.DAOVentas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import model.CompradorVenta;
import model.Rifa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Abel Guillen
 */
@Controller
@RequestMapping("/ventas")
public class AbmVentasController {
    private final JdbcTemplate jdbcTemplate;
    private final DAORifas daoRifas;
    private final DAOVentas daoVentas;

    @Autowired
    public AbmVentasController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        daoRifas = new DAORifas(jdbcTemplate);
        daoVentas = new DAOVentas(jdbcTemplate);
    }
    
    @RequestMapping(value = "")
    public String ventas(Model template, HttpServletRequest request) {
        String usuario = (String) request.getSession().getAttribute("usuario");
        if(usuario == null) return "redirect:" + "/";
        else return "ventas";
    }
    
    @RequestMapping(value = "/nueva")
    public String nuevaVenta(Model template) {
        return "nuevaVenta";
    }
    
    @RequestMapping(value = "/nueva", method = RequestMethod.POST)
    public String nuevaVentaPost(Model template,
            @RequestParam(value = "fechaSuscripcion") String fechaSuscripcion,
            @RequestParam(value = "nroRifa") String nroRifa,
            @RequestParam(value = "estado") String estado,
            @RequestParam(value = "nroBono") String nroBono,
            @RequestParam(value = "apellido") String apellido,
            @RequestParam(value = "nombre") String nombre,
            @RequestParam(value = "direccion") String direccion,
            @RequestParam(value = "localidad") String localidad,
            @RequestParam(value = "telefono") String telefono,
            @RequestParam(value = "cobrador") String cobrador,
            @RequestParam(value = "vendedor") String vendedor,
            @RequestParam(value = "zona") String zona){
    
        ArrayList<Rifa> listaDeRifas = daoRifas.getAllRifas();
        //Valida que el nro de Rifa sea correcto
        if(validaRifa(listaDeRifas, nroRifa)) {
            CompradorVenta compradorVenta = new CompradorVenta();
            compradorVenta.setNombre(nombre);
            compradorVenta.setDireccion(direccion);
            compradorVenta.setApellido(apellido);
            compradorVenta.setLocalidad(localidad);
            compradorVenta.setTelefono(telefono);
            compradorVenta.setRifa(nroRifa);
            compradorVenta.setCobrador(cobrador.toUpperCase());
            compradorVenta.setVendedor(vendedor);
            compradorVenta.setZona(zona);
            try {
                compradorVenta.setFechaSuscripcion(
                        new SimpleDateFormat("yyyy-MM-dd").parse(fechaSuscripcion));
            } catch (ParseException ex) {
                template.addAttribute("error", true);
                template.addAttribute("mensaje", "No se pudo parsear la fecha correctamente.");
                template.addAttribute("href", "ventas/nueva");
                return "error";
            }
            compradorVenta.setEstado(estado);
            compradorVenta.setNroBono(nroBono);
            
            daoVentas.insertVenta(compradorVenta);
        } else {
            template.addAttribute("error", true);
            template.addAttribute("mensaje", "Numero de Rifa no valido.");
            template.addAttribute("href", "ventas/nueva");
            return "error";
        }
        
        return "redirect:/ventas";
    }
    
    public boolean validaRifa(ArrayList<Rifa> listaDeRifas, String nroRifa) {
        for(Rifa r : listaDeRifas) {
            String rifa = r.getPrimerNumero();
            if(rifa.equals(nroRifa)) {
                return true;
            }
        }
        return false;
    }
    
    @RequestMapping(value = "/lista")
    public String listaRifas(Model template) {
        ArrayList<CompradorVenta> compradores = daoVentas.getAllVentas();
        template.addAttribute("ventas", compradores);
        return "listaVentas";
    }
    
    //Redirige mediante AJAX
    @RequestMapping(value = "/eliminar/{venta}", method = RequestMethod.POST)
    @ResponseBody
    public String eliminarVenta(Model template,
            @PathVariable(value = "venta") String venta) {
        daoVentas.eliminarVenta(venta);
        return "/ventas/lista";
    }
    
    @RequestMapping(value = "/modificar/{idVenta}")
    public String modificarVenta(Model template,
            @PathVariable(value = "idVenta") Integer id,
            @RequestParam(value = "nombre") String nombre,
            @RequestParam(value = "direccion") String direccion,
            @RequestParam(value = "apellido") String apellido,
            @RequestParam(value = "localidad") String localidad,
            @RequestParam(value = "telefono") String telefono,
            @RequestParam(value = "nroRifa") String rifa,
            @RequestParam(value = "estado") String estado,
            @RequestParam(value = "nroBono") String nroBono,
            @RequestParam(value = "cobrador") String cobrador,
            @RequestParam(value = "vendedor") String vendedor,
            @RequestParam(value = "zona") String zona,
            @RequestParam(value = "cuotasPorMes") Integer cuotasPorMes) {
        
        CompradorVenta compradorVenta = new CompradorVenta();
        compradorVenta.setId(id);
        compradorVenta.setEstado(estado);
        compradorVenta.setNroBono(nroBono);
        compradorVenta.setNombre(nombre);
        compradorVenta.setDireccion(direccion);
        compradorVenta.setApellido(apellido);
        compradorVenta.setLocalidad(localidad);
        compradorVenta.setTelefono(telefono);
        compradorVenta.setRifa(rifa);
        compradorVenta.setCobrador(cobrador);
        compradorVenta.setVendedor(vendedor);
        compradorVenta.setZona(zona);
        compradorVenta.setCuotasPorMes(cuotasPorMes);

        daoVentas.modificarVenta(compradorVenta);
        
        return "redirect:" + "/ventas/lista";
    }
    
    @RequestMapping(value = "/imprimir/sobre/{idVenta}")
    public String imprimirSobre(Model template, 
            @PathVariable(value = "idVenta") Integer id) {
        CompradorVenta venta = daoVentas.getVentaID(id);
        template.addAttribute("venta", venta);
        return "imprimirSobre";
    }
}