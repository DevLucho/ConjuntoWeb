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


