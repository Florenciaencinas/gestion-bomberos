/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOReportes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import model.CompradorVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Abel Guillen
 */
@Controller
@RequestMapping("/reportes")
public class ReportesController {
    
    @SuppressWarnings("unused")
	private final JdbcTemplate jdbcTemplate;
    private final DAOReportes daoReportes;

    @Autowired
    public ReportesController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.daoReportes = new DAOReportes(jdbcTemplate);
    }
    
    @RequestMapping(value = "")
    public String reportes(Model template, HttpServletRequest request) {
        String usuario = (String) request.getSession().getAttribute("usuario");
        if(usuario == null) return "redirect:" + "/";
        else return "reportes";
    }
    
    @RequestMapping(value = "/venta")
    public String reporteVenta(Model template) {
        return "reporteVenta";
    }
    
    @RequestMapping(value = "/venta", method = RequestMethod.POST)
    public String reporteVentaPost(Model template,  
            @RequestParam(value = "seleccionarReporte") String seleccionarReporte,
            @RequestParam(value = "vendedor") String vendedor) {
        
        ArrayList<CompradorVenta> listaDeVentas = new ArrayList<CompradorVenta>();
        
        if(seleccionarReporte.equals("porVendedor")) {
            listaDeVentas = this.daoReportes.getReportePorVendedor(vendedor);
            template.addAttribute("vendedor", vendedor);
        } else if(seleccionarReporte.equals("todos")) {
            listaDeVentas = this.daoReportes.getReporteTodos();
            template.addAttribute("vendedor", "TODOS");
        }
        
        // Se agrega el estado y se formatea la fecha
        listaDeVentas = formatearLista(listaDeVentas);
        
        template.addAttribute("lista", listaDeVentas);
        
        return "imprimirReporteVenta";
    }
    
    public ArrayList<CompradorVenta> formatearLista(
            ArrayList<CompradorVenta> listaDeVentas) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        //Se setea estado
        for(CompradorVenta cv : listaDeVentas) {
            cv.setFechaSuscripcionStr(sdf.format(cv.getFechaSuscripcion()));
            String estado = cv.getEstado();
            Integer cantCuotas = Integer.parseInt(cv.getCantCuotas());
            Integer cuotasPagas = Integer.parseInt(cv.getCuotasPagas());
            if(estado.equals("A-PLAZO") && cantCuotas - cuotasPagas == 0) {
                cv.setEstado("TERMINADA");
            }
        }
        return listaDeVentas;
    }
    
    @RequestMapping(value = "/cobranza/{tipoCobranza}")
    public String reporteCobranza(Model template, @PathVariable(value = "tipoCobranza") String tipoCobranza) {
    	template.addAttribute("tipoCobranza", tipoCobranza);
    	return "reporteCobranza";
    }
    
    @RequestMapping(value = "/cobranza/{tipoCobranza}", method = RequestMethod.POST)
    public String reporteCobranzaPost(Model template, 
    		@RequestParam(value = "seleccionarReporte") String seleccionarReporte,
            @RequestParam(value = "cobrador") String cobrador,
            @RequestParam(value = "zona") String zona,
            @PathVariable(value = "tipoCobranza") String tipoCobranza) {
        
    	// Se obtiene la lista de cobranzas completa
    	ArrayList<CompradorVenta> listaDeCobranza = null;
    	if(seleccionarReporte.equals("porCobrador")) {
    		listaDeCobranza = daoReportes.getCobranzaPorCobrador(cobrador);
    	} else if(seleccionarReporte.equals("porZona")) {
    		listaDeCobranza = daoReportes.getCobranzaPorZona(zona);
    	}
    	
        if(tipoCobranza.equals("mensual")) calcularCuotasPagas(listaDeCobranza);
        
        // Se calcula la cantidad de hojas del reporte
        Integer cantidadDeHojas = calcularCantidadDeHojas(listaDeCobranza);
        boolean hojaDos = false;
        boolean hojaTres = false;
        boolean hojaCuatro = false;
        if(cantidadDeHojas >= 2) hojaDos = true;
        if(cantidadDeHojas >= 3) hojaTres = true;
        if(cantidadDeHojas >= 4) hojaCuatro = true;
        
        // Se obtiene la fecha formateada
        String fechaFormateada = calcularFecha();
        
        // Se parsea la lista de cobranza segun "x" columna (existen 4 columnas)
        // Columna 1 = 57
        ArrayList<CompradorVenta> filaUno = parsearLista(listaDeCobranza, 0, 47);
        ArrayList<CompradorVenta> filaDos = parsearLista(listaDeCobranza, 47, 97);
        ArrayList<CompradorVenta> filaTres = parsearLista(listaDeCobranza, 97, 147);
        ArrayList<CompradorVenta> filaCuatro = parsearLista(listaDeCobranza, 147, 197);
        ArrayList<CompradorVenta> filaCinco = parsearLista(listaDeCobranza, 197, 244);
        ArrayList<CompradorVenta> filaSeis = parsearLista(listaDeCobranza, 244, 294);
        ArrayList<CompradorVenta> filaSiete = parsearLista(listaDeCobranza, 294, 344);
        ArrayList<CompradorVenta> filaOcho = parsearLista(listaDeCobranza, 344, 394);
        ArrayList<CompradorVenta> filaNueve = parsearLista(listaDeCobranza, 394, 441);
        ArrayList<CompradorVenta> filaDiez = parsearLista(listaDeCobranza, 441, 491);
        ArrayList<CompradorVenta> filaOnce = parsearLista(listaDeCobranza, 491, 541);
        ArrayList<CompradorVenta> filaDoce = parsearLista(listaDeCobranza, 541, 591);
        ArrayList<CompradorVenta> filaTrece = parsearLista(listaDeCobranza, 591, 638);
        ArrayList<CompradorVenta> filaCatorce = parsearLista(listaDeCobranza, 638, 688);
        ArrayList<CompradorVenta> filaQuince = parsearLista(listaDeCobranza, 688, 738);
        ArrayList<CompradorVenta> filaDiezYSeis = parsearLista(listaDeCobranza, 738, 788);
        
        template.addAttribute("cantHojas", cantidadDeHojas);
        
        template.addAttribute("fechaFormateada", fechaFormateada);
        
        template.addAttribute("filaUno", filaUno);
        template.addAttribute("filaDos", filaDos);
        template.addAttribute("filaTres", filaTres);
        template.addAttribute("filaCuatro", filaCuatro);
        template.addAttribute("filaCinco", filaCinco);
        template.addAttribute("filaSeis", filaSeis);
        template.addAttribute("filaSiete", filaSiete);
        template.addAttribute("filaOcho", filaOcho);
        template.addAttribute("filaNueve", filaNueve);
        template.addAttribute("filaDiez", filaDiez);
        template.addAttribute("filaOnce", filaOnce);
        template.addAttribute("filaDoce", filaDoce);
        template.addAttribute("filaTrece", filaTrece);
        template.addAttribute("filaCatorce", filaCatorce);
        template.addAttribute("filaQuince", filaQuince);
        template.addAttribute("filaDiezYSeis", filaDiezYSeis);
        
        template.addAttribute("hojaDos", hojaDos);
        template.addAttribute("hojaTres", hojaTres);
        template.addAttribute("hojaCuatro", hojaCuatro);
        
        template.addAttribute("cantidad", listaDeCobranza.size());
        template.addAttribute("total", calcularTotal(listaDeCobranza));
        
        template.addAttribute("cobrador", cobrador.toUpperCase());
        template.addAttribute("zona", zona.toUpperCase());
        
        if(seleccionarReporte.equals("porCobrador")) {
        	return "reporteCobranzaPorCobrador";
    	} else {
    		return "reporteCobranzaPorZona";
    	}
    }
    
    private void calcularCuotasPagas(ArrayList<CompradorVenta> listaDeCobranza) {
		for(int i=0;i<listaDeCobranza.size();i++) {
			String cuotasPagasStr = listaDeCobranza.get(i).getCuotasPagas();
			Integer cuotasPorMes = listaDeCobranza.get(i).getCuotasPorMes();
			if(!cuotasPagasStr.equals("T") && !cuotasPagasStr.equals("B")) {
				Integer cuotasPagas = Integer.parseInt(cuotasPagasStr);
				Integer cuotaInicio = cuotasPagas - cuotasPorMes + 1;
				if(cuotaInicio > 0) {
					cuotasPagasStr = (cuotasPagas - cuotasPorMes + 1) + "-" + cuotasPagas;
					listaDeCobranza.get(i).setCuotasPagas(cuotasPagasStr);
				}
			}
		}
		
	}

	public Integer calcularTotal(ArrayList<CompradorVenta> listaDeCobranza) {
        Integer total = 0;
        for(CompradorVenta cv : listaDeCobranza) {
            Integer valor = cv.getValor();
            total += valor;
        }
        return total;
    }
    
    public String calcularFecha() {
        String valorDia = "";
        String valorMes = "";
        Calendar c = Calendar.getInstance();
        Integer year = c.get(Calendar.YEAR);
        Integer month = c.get(Calendar.MONTH);
        Integer day = c.get(Calendar.DAY_OF_MONTH);
        c.set(year, month, day);

        int diaSemana = c.get(Calendar.DAY_OF_WEEK);
        if (diaSemana == 1) valorDia = "Domingo";
        else if (diaSemana == 2) valorDia = "Lunes";
        else if (diaSemana == 3) valorDia = "Martes";
        else if (diaSemana == 4) valorDia = "Miercoles";
        else if (diaSemana == 5) valorDia = "Jueves";
        else if (diaSemana == 6) valorDia = "Viernes";
        else if (diaSemana == 7) valorDia = "Sabado";
        
        if(month == 0) valorMes = "Enero";
        else if(month == 1) valorMes = "Febrero";
        else if(month == 2) valorMes = "Marzo";
        else if(month == 3) valorMes = "Abril";
        else if(month == 4) valorMes = "Mayo";
        else if(month == 5) valorMes = "Junio";
        else if(month == 6) valorMes = "Julio";
        else if(month == 7) valorMes = "Agosto";
        else if(month == 8) valorMes = "Septiembre";
        else if(month == 9) valorMes = "Octubre";
        else if(month == 10) valorMes = "Noviembre";
        else if(month == 11) valorMes = "Diciembre";
        
        return valorDia + ", " + day + " de " + valorMes + " de " + year;
    }
    
    public Integer calcularCantidadDeHojas(ArrayList<CompradorVenta> listaDeCobranza) {
        Integer cantHojas = 1;
        double operacion = Double.parseDouble(String.valueOf(listaDeCobranza.size())) / 197;
        if(operacion > 1) cantHojas = 2;
        if(operacion > 2) cantHojas = 3;
        if(operacion > 3) cantHojas = 4;
        return cantHojas;
    }
    
    public ArrayList<CompradorVenta> parsearLista(
            ArrayList<CompradorVenta> listaDeCobranza, int inicio, 
            int hasta) {
        ArrayList<CompradorVenta> listaDeCobranzaParseada = 
                new ArrayList<CompradorVenta>();
        
        for(int i = inicio; i<hasta; i++) {
            try {
                CompradorVenta cv =  listaDeCobranza.get(i);
                listaDeCobranzaParseada.add(cv);
            } catch (Exception e) {
                i = hasta;
            }
        }
        
        return listaDeCobranzaParseada;
    }
}