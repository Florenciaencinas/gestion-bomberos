/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Date;
import model.CompradorVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Abel Guillen
 */
public class DAOReportes {
    private final JdbcTemplate jdbcTemplate;
	
    @Autowired
    public DAOReportes(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public ArrayList<CompradorVenta> getCobranzaPorCobrador(String cobrador) {
        SqlRowSet cobranza = jdbcTemplate.queryForRowSet(
            "SELECT DISTINCT " +
            "   c.rifa, r.valor, c.cuotasPorMes, " +
            "		CASE c.estado " +
            "			WHEN 'BAJA' THEN 'B' " +
            "			WHEN 'TERMINADA' THEN 'T' " +
            "			ELSE c.cuotasPagas " +
            "		END AS cuotasPagas " +
            "FROM " +
            "   rifas r, comprador c " +
            "WHERE " +
            "   r.primer_numero = c.rifa " +
            "   AND c.estado != 'CONTADO' " +
            "	AND c.cobrador = ?" +
            "ORDER BY " +
            "   primer_numero;", cobrador
        );

        ArrayList<CompradorVenta> listaDeCobranza = new ArrayList<CompradorVenta>();
        while (cobranza.next()) {
            String rifa = cobranza.getString("rifa");
            Integer valor = cobranza.getInt("valor");
            String cuotasPagas = cobranza.getString("cuotasPagas");
            Integer cuotasPorMes = cobranza.getInt("cuotasPorMes");
            
            CompradorVenta comprador = new CompradorVenta();
            comprador.setRifa(rifa);
            comprador.setValor(valor);
            comprador.setCuotasPagas(cuotasPagas);
            comprador.setCuotasPorMes(cuotasPorMes);
            
            listaDeCobranza.add(comprador);
        }
        return listaDeCobranza;
    }
    
    public ArrayList<CompradorVenta> getCobranzaPorZona(String zona) {
        SqlRowSet cobranza = jdbcTemplate.queryForRowSet(
            "SELECT DISTINCT " +
            "   c.rifa, r.valor, c.cuotasPorMes, " +
            "		CASE c.estado " +
            "			WHEN 'BAJA' THEN 'B' " +
            "			WHEN 'TERMINADA' THEN 'T' " +
            "			ELSE c.cuotasPagas " +
            "		END AS cuotasPagas " +
            "FROM " +
            "   rifas r, comprador c " +
            "WHERE " +
            "   r.primer_numero = c.rifa " +
            "   AND c.estado != 'CONTADO' " +
            "	AND c.zona = ?" +
            "ORDER BY " +
            "   primer_numero;", zona
        );

        ArrayList<CompradorVenta> listaDeCobranza = new ArrayList<CompradorVenta>();
        while (cobranza.next()) {
            String rifa = cobranza.getString("rifa");
            Integer valor = cobranza.getInt("valor");
            String cuotasPagas = cobranza.getString("cuotasPagas");
            Integer cuotasPorMes = cobranza.getInt("cuotasPorMes");
            
            CompradorVenta comprador = new CompradorVenta();
            comprador.setRifa(rifa);
            comprador.setValor(valor);
            comprador.setCuotasPagas(cuotasPagas);
            comprador.setCuotasPorMes(cuotasPorMes);
            
            listaDeCobranza.add(comprador);
        }
        return listaDeCobranza;
    }
    
    public ArrayList<CompradorVenta> getReportePorVendedor(String vendedor) {
        SqlRowSet reportePorVendedor;
        reportePorVendedor = jdbcTemplate.queryForRowSet(
                "SELECT DISTINCT" +
                "   r.primer_numero, r.valor, c.apellido, c.estado, " +
                "   c.nombre, c.direccion, c.telefono, r.cuotas,   " +
                "   c.cuotasPagas, c.fechaSuscripcion " +
                "FROM " +
                "   rifas r, comprador c " +
                "WHERE " +
                "   r.primer_numero = c.rifa " +
                "   AND c.vendedor = ? " +
                "ORDER BY " + 
                "   r.primer_numero, c.apellido, c.nombre;", vendedor);

        ArrayList<CompradorVenta> listaDeVentas = new ArrayList<CompradorVenta>();
        while (reportePorVendedor.next()) {
            String rifa = reportePorVendedor.getString("primer_numero");
            Integer valor = reportePorVendedor.getInt("valor");
            String apellido = reportePorVendedor.getString("apellido");
            String estado = reportePorVendedor.getString("estado");
            String nombre = reportePorVendedor.getString("nombre");
            String direccion = reportePorVendedor.getString("direccion");
            String telefono = reportePorVendedor.getString("telefono");
            String cantCuotas = reportePorVendedor.getString("cuotas");
            String cuotasPagas = reportePorVendedor.getString("cuotasPagas");
            Date fechaSuscripcion = reportePorVendedor.getDate("fechaSuscripcion");
            
            CompradorVenta comprador = new CompradorVenta();
            comprador.setRifa(rifa);
            comprador.setValor(valor);
            comprador.setApellido(apellido);
            comprador.setEstado(estado);
            comprador.setNombre(nombre);
            comprador.setDireccion(direccion);
            comprador.setTelefono(telefono);
            comprador.setCantCuotas(cantCuotas);
            comprador.setCuotasPagas(cuotasPagas);
            comprador.setFechaSuscripcion(fechaSuscripcion);
            
            listaDeVentas.add(comprador);
        }
        return listaDeVentas;
    }
    
    public ArrayList<CompradorVenta> getReporteTodos() {
        SqlRowSet reportePorVendedor;
        reportePorVendedor = jdbcTemplate.queryForRowSet(
                "SELECT DISTINCT" +
                "   r.primer_numero, r.valor, c.apellido, c.estado, " +
                "   c.nombre, c.direccion, c.telefono, r.cuotas,   " +
                "   c.cuotasPagas, c.fechaSuscripcion " +
                "FROM " +
                "   rifas r, comprador c " +
                "WHERE " +
                "   r.primer_numero = c.rifa " + 
                "ORDER BY " + 
                "   r.primer_numero, c.apellido, c.nombre; ");
                
        ArrayList<CompradorVenta> listaDeVentas = new ArrayList<CompradorVenta>();
        while (reportePorVendedor.next()) {
            String rifa = reportePorVendedor.getString("primer_numero");
            Integer valor = reportePorVendedor.getInt("valor");
            String apellido = reportePorVendedor.getString("apellido");
            String estado = reportePorVendedor.getString("estado");
            String nombre = reportePorVendedor.getString("nombre");
            String direccion = reportePorVendedor.getString("direccion");
            String telefono = reportePorVendedor.getString("telefono");
            String cantCuotas = reportePorVendedor.getString("cuotas");
            String cuotasPagas = reportePorVendedor.getString("cuotasPagas");
            Date fechaSuscripcion = reportePorVendedor.getDate("fechaSuscripcion");
            
            CompradorVenta comprador = new CompradorVenta();
            comprador.setRifa(rifa);
            comprador.setValor(valor);
            comprador.setApellido(apellido);
            comprador.setEstado(estado);
            comprador.setNombre(nombre);
            comprador.setDireccion(direccion);
            comprador.setTelefono(telefono);
            comprador.setCantCuotas(cantCuotas);
            comprador.setCuotasPagas(cuotasPagas);
            comprador.setFechaSuscripcion(fechaSuscripcion);
            
            listaDeVentas.add(comprador);
        }
        return listaDeVentas;
    }
}
