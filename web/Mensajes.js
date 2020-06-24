/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function mensajes (titulo, texto, icono){
    Swal.fire({
        title:titulo,
        text:texto,
        icon:icono,
        buttom:'close'
    })
}


