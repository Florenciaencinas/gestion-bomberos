package controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.DAORifas;
import dao.DAOVentas;

@Controller
public class IndexController {

    private final JdbcTemplate jdbcTemplate;
    private final DAORifas daoRifas;
    private final DAOVentas daoVentas;

    @Autowired
    public IndexController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        daoRifas = new DAORifas(jdbcTemplate);
        daoVentas = new DAOVentas(jdbcTemplate);
    }
    
    @RequestMapping(value = "")
    public String login(Model template, HttpServletRequest request) {
        String usuario = (String) request.getSession().getAttribute("usuario");
        if(usuario == null) return "login";
        else return "home";
    }
    
    @RequestMapping(value = "/configuracion")
    public String configuracion(Model template, HttpServletRequest request) {
        return "configuracion";
    }
    
    @RequestMapping(value = "/logout")
    public String logout(Model template, HttpServletRequest request) {
        request.getSession().removeAttribute("usuario");
        return "login";
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String loginPost(Model template, 
            @RequestParam(value = "usuario") String usuario,
            @RequestParam(value = "contraseña") String contraseña,
            HttpServletRequest request) {
        
        if(verificarLogin(usuario, contraseña)) {
            request.getSession().setAttribute("usuario", usuario);
            return "home";
        }
        else return "login";
    }
    
    @RequestMapping(value = "/campania/eliminar", method = RequestMethod.POST)
    @ResponseBody
    public String eliminarCampaña() {
    	daoRifas.deleteAllRifas();
    	daoVentas.deleteAllComprador();
    	return "/";
    }
    
    public boolean verificarLogin(String usuario, String contraseña) {
        if(usuario.equals("admin") && contraseña.equals("bomberos")) return true;
        else return false;
    }
    
    @RequestMapping(value = "/home/error")
    public String error(Model template) {
        return "error";
    }
}