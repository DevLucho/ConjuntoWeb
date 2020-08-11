function Mensaje(titulo, texto, icono) {
    Swal.fire({title: titulo, text: texto, icon: icono, showConfirmButton: false, timer: 3000});
};

function Mensajes(titulo, texto, icono) {
    Swal.fire({title: titulo, text: texto, icon: icono});
};

/*
function Alertify(mensaje, icon){   
     alertify.notify(mensaje, icon, 5);
};
*/

function MensajeAlertify(mensaje, icon) {
    const Toast = Swal.mixin({
        toast: true,
        position: 'bottom-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        onOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
    });
    Toast.fire({
        icon: icon,
        title: mensaje
    });
};

// Mensaje para redirigir con texto
function MensajeRedirect(link, titulo, texto, icono) {
    Swal.fire({
        title: titulo,
        text: texto,
        icon: icono,
        confirmButtonText: 'Ok',
        allowOutsideClick: false
    }).then(function (result) {
        console.log(result);
        if (result.value) {
            window.location.href = link;
        }
    });
}

// Mensaje para redirigir sin texto
function ConfirmacionResidente(linkURL, icono, titulo, txtbtn) {
    Swal.fire({
        icon: icono,
        title: titulo,
        confirmButtonText: txtbtn,
        allowOutsideClick: false
    }).then(function (result) {
        console.log(result);
        if (result.value) {
            window.location.href = linkURL;
        }
    });
}

// ejecutar despues de confirmar
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
    });
};

function ConfirmacionResidenteR(linkURL) {
    Swal.fire({
        icon: 'warning',
        title: "Esta pqrs ya esta resuelta",
        confirmButtonText: 'Volver',
        allowOutsideClick: false
    }).then(function (result) {
        console.log(result);
        if (result.value) {
            window.location.href = linkURL;
        }
    });
}

function RegistrarVisitante(icono, titulo, html) {
    Swal.fire({
        icon: icono,
        title: titulo,
        html: html,
        confirmButtonColor: '#2A6378',
        confirmButtonText: 'Volver',
        padding: '2% 2% 3% 2%',
        customClass: {
            title: 'title-class',
            icon: 'icon-class',
            popup: 'popup-class'
        },
        allowOutsideClick: false
    });
}

function EdicionVisitante(linkURL, titulo, html) {
    Swal.fire({
        icon: 'success',
        title: titulo,
        html: html,
        confirmButtonColor: '#2A6378',
        confirmButtonText: 'Ver',
        padding: '0% 0% 2% 0%',
        customClass: {
            title: 'title-class',
            icon: 'icon-class',
            popup: 'popup-class'
        },
        allowOutsideClick: false
    }).then(function (result) {
        console.log(result);
        if (result.value) {
            window.location.href = linkURL;
        }
    });
}

function ConfirmarSalida(icono, titulo, html) {
    Swal.fire({
        icon: icono,
        title: titulo,
        html: html,
        showClass: {
            popup: 'animate__animated animate__fadeInDown'
        },
        hideClass: {
            popup: 'animate__animated animate__fadeOutUp'
        },
        customClass: {
            title: 'title-class',
            icon: 'icon-class2',
            popup: 'popup-class'
        },
        allowOutsideClick: false
    });
}