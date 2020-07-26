function Mensaje(titulo, texto, icono) {
    Swal.fire({title: titulo, text: texto, icon: icono, showConfirmButton: false, timer: 3000});
};

function Mensajes(titulo, texto, icono) {
    Swal.fire({title: titulo, text: texto, icon: icono});
};

function MensajeAlertify(mensaje, icon){   
     alertify.notify(mensaje, icon, 5);
};

// Mensaje para redirigir
function MensajeRedirect(link) {
    Swal.fire({
        title: 'Advertencia!',
        text: 'Esta pqrs ya esta resuelta',
        icon: 'warning',
        confirmButtonText: 'Ok'
    }).then(function (result) {
        console.log(result);
        if (result.value) {
            window.location.href = link;
        }
    });
}

function Confirmar(titulo, texto, icono, textob, tituloc, textoc, iconoc) {
    Swal.fire({
        title: titulo,
        text: texto,
        icon: icono,
        confirmButtonColor: '#3085d6',
        confirmButtonText: textob,
        showCancelButton: true,
        cancelButtonText: 'No',
        cancelButtonColor: '#d33'
    }).then((result) => {
        if (result.value) {
            Swal.fire(tituloc, textoc, iconoc)
        }
    })
};

// Mensaje para redirigir (login) - registro residente
function ConfirmacionResidente(linkURL) {
    Swal.fire({
        icon: 'success',
        title: "Registro exitoso",
        confirmButtonText: 'Iniciar sesi&oacute;n'
    }).then(function (result) {
        console.log(result);
        if (result.value) {
            window.location.href = linkURL;
        }
    });
}

function ConfirmacionResidenteR(linkURL) {
    Swal.fire({
        icon: 'warning',
        title: "Esta pqrs ya esta resuelta",
        confirmButtonText: 'Volver',
        allowOutsideClick:false
    }).then(function (result) {
        console.log(result);
        if (result.value) {
            window.location.href = linkURL;
        }
    });
}

function RegistrarVisitante(icono,titulo, html) {
    Swal.fire({
        icon: icono,
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
function ConfirmarSalida(icono,titulo,html){
    Swal.fire({
        icon:icono,
        title: titulo,
        html: html,
        showClass: {
            popup: 'animate__animated animate__fadeInDown'
        },
        hideClass: {
            popup: 'animate__animated animate__fadeOutUp'
        },
        customClass:{
            title:'title-class',
            icon:'icon-class2',
            popup:'popup-class'
        },
        allowOutsideClick:false
    });
}