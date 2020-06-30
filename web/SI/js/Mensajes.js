function Mensaje(titulo, texto, icono) {
    Swal.fire({title: titulo, text: texto, icon: icono, showConfirmButton: false, timer: 4000});
};

function Mensajes(titulo, texto, icono) {
    Swal.fire({title: titulo, text: texto, icon: icono});
};

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
