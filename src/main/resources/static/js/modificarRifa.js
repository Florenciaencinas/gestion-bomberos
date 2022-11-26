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
    
    $('button#modificarRifa').click(function() {
        var idRifa = $('tr.visto p#idRifa').text();
//        alert(idRifa);
        
        $.ajax({
            url: '/rifas/buscar/' + idRifa,
            method: "POST",
            success: function(respuesta) {
                
                var id = respuesta.id;
                var valor = respuesta.valor;
                var cabezal = respuesta.cabezal;
                var cuotas = respuesta.cuotas;
                var numero = respuesta.numero;
                var primerNumero = respuesta.primerNumero;

                $("#valor").val(valor);
                $('#formRifa').attr('action', '/rifas/modificar/' + primerNumero);
            }
        });
        
    });
});