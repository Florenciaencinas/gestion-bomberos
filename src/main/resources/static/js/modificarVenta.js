/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  a635019
 * Created: 28/10/2018
 */
$(document).ready(function() {
	$(document).ready(function() {
	    $('#dtRifas').DataTable();
	} );
	
	
    $("tbody tr").mouseover(function() {
        $(this).css("cursor", "pointer");
        if ($(this).hasClass("visto")) {
                $(this).removeClass("visto");
        } else {
                $(this).addClass("visto");
        }
    });
    $("tbody tr").mouseout(function() {
        $(this).css("cursor", "pointer");
        if ($(this).hasClass("visto")) {
                $(this).removeClass("visto");
        }
    });
    
    $('button#modificarVenta').click(function() {
        var idVenta = $('tr.visto p#idVenta').text();
//        alert(idVenta);
        
        $.ajax({
            url: '/ventas/buscar/' + idVenta,
            method: "POST",
            success: function(respuesta) {
                
                var id = respuesta.id;
                var nombre = respuesta.nombre;
                var direccion = respuesta.direccion;
                var apellido = respuesta.apellido;
                var localidad = respuesta.localidad;
                var telefono = respuesta.telefono;
                var rifa = respuesta.rifa;
                var estado = respuesta.estado;
                var fechaSuscripcion = respuesta.fechaSuscripcion;
                var nroBono = respuesta.nroBono;
                var cobrador = respuesta.cobrador;
                var vendedor = respuesta.vendedor;
                var zona = respuesta.zona;
                var cuotasPorMes = respuesta.cuotasPorMes;
                
                $("#rifa").val(rifa);
                $("#estado option[value="+estado+"]").attr("selected", true);
                $("#nroBono").val(nroBono);
                $("#apellido").val(apellido);
                $("#nombre").val(nombre);
                $("#direccion").val(direccion);
                $("#localidad").val(localidad);
                $("#telefono").val(telefono);
                $("#cobrador").val(cobrador);
//                $("#cobrador option[value="+cobrador+"]").attr("selected", true);
                $("#vendedor option[value="+vendedor+"]").attr("selected", true);
                $("#zona option[value="+zona+"]").attr("selected", true);
                $("#cuotasPorMes").val(cuotasPorMes);
                
                $('#formVenta').attr('action', '/ventas/modificar/' + idVenta);
            }
        });
        
    });
});