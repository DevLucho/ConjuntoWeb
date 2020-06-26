function Mensaje(titulo, texto, icono) {
    Swal.fire({title: titulo, text: texto, icon: icono, showConfirmButton: false, timer: 2000});
}

function ConfirmacionResidente(linkURL) {
    Swal.fire({
        icon: 'success',
        title: "Registro exitoso",
        confirmButtonText: 'Iniciar sesi&oacute;n',
    }).then(function (result) {
        console.log(result);
        if (result.value) {
            window.location.href = linkURL;
        }
    });
}
function RegistrarVisitante(titulo, html) {
    Swal.fire({
        icon: 'success',
        title:titulo,
        html:html,
        confirmButtonColor: '#2A6378',
        confirmButtonText:'Volver',
        padding:'2% 2% 3% 2%',
        customClass:{
            title:'title-class',
            icon:'icon-class',
            popup:'popup-class'
        },
        allowOutsideClick:false
    });
}
function EdicionVisitante(linkURL,titulo,html) {
    Swal.fire({
        icon: 'success',
        title:titulo,
        html:html,
        confirmButtonColor: '#2A6378',
        confirmButtonText:'Ver',
        padding:'0% 0% 2% 0%',
        customClass:{
            title:'title-class',
            icon:'icon-class',
            popup:'popup-class'
        },
        allowOutsideClick:false
    }).then(function (result) {
        console.log(result);
        if (result.value) {
            window.location.href = linkURL;
        }
    });
}
function confirmar(){
    Swal.fire({
        icon:'info',
        title: 'Entrega Exitosa',
        text:'El paquete se ha entregado al residente...',
        showClass: {
            popup: 'animate__animated animate__fadeInDown'
        },
        hideClass: {
            popup: 'animate__animated animate__fadeOutUp'
        },
        customClass:{
            title:'title-class',
            icon:'icon-class',
            popup:'popup-class'
        },
        timer:900000,
        allowOutsideClick:false
    });
}