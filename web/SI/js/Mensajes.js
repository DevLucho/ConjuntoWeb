function Mensaje(titulo, texto, icono) {
    Swal.fire({title: titulo, text: texto, icon: icono, showConfirmButton: false, timer: 2000});
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

// Mensaje para redirigir (index)
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
};


