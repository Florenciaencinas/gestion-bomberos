/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  a635019
 * Created: 19/01/2018
 */
$(document).ready(function() {
    $('#nroRifa').on('keyup change blur', function() {
        var nroRifa = $('#nroRifa').val();
        if(nroRifa.length == 3) {
            $.ajax({
                url: '/cobranza/buscar/' + nroRifa,
                method: "POST",
                success: function(respuesta) {

                    var nombre = respuesta.nombre;
                    var apellido = respuesta.apellido;
                    
                    $("#nomYApe").text(nombre + ' ' + apellido);

                    $('#formCobranza').attr('action', '/cobranza/modificar/' + nroRifa);
                }
            });
        }
    });
});