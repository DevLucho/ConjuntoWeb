// Validar texto, acentos y n
function validar_texto(parametro) {
    var patron = /^[a-zA-Z&aacute;&eacute;&iacute;&oacute;&uacute;&ntilde;&Ntilde;\s]*$/;
    if (parametro.search(patron)) {
        return false;
    } else {
        return true;
    }
}
// validar correo
function validar_correo(parametro) {
    var patron = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
    if (!patron.test(parametro)) {
        return false;
    } else {
        return true;
    }
}
/*
 validar telefono
 function validar_telefono(parametro){
 var patron = /^\d[10]$/;
 if(!patron.test(parametro)){
 return false;
 }
 else{
 return true;
 }
 }
 */

function validar_formulario() {

    var formulario = document.form;

    // Nombres
    if (formulario.nombre.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingrese su nombre</div>';
        formulario.nombre.focus();
        return false;
    } else if (validar_texto(formulario.nombre.value) === false) {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>No se permiten valores n&uacute;mericos en el nombre</div>';
        formulario.nombre.value = "";
        formulario.nombre.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Apellidos
    if (formulario.apellido.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingrese su apellido</div>';
        formulario.apellido.focus();
        return false;
    } else if (validar_texto(formulario.apellido.value) === false) {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>No se permiten valores n&uacute;mericos en el apellido</div>';
        formulario.apellido.value = "";
        formulario.apellido.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Tipo de documento
    if (formulario.tipoDocumento.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor seleccione tipo de documento</div>';
        formulario.tipoDocumento.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Numero de identificacion
    if (formulario.identificacion.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingrese su numero de documento</div>';
        formulario.identificacion.focus();
        return false;
    } else if (formulario.identificacion.value.length !== 10) {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>N&uacute;mero de documento incorrecto</div>';
        formulario.identificacion.value = "";
        formulario.identificacion.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Correo electronico
    if (formulario.correo.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingrese su correo electronico</div>';
        formulario.correo.focus();
        return false;
    } else if (validar_correo(formulario.correo.value) === false) {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Correo electronico inv&aacute;lido</div>';
        formulario.correo.value = "";
        formulario.correo.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    //Telefono
    if (formulario.phone.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingrese su numero de celular</div>';
        formulario.phone.focus();
        return false;
    } else if (formulario.phone.value.length !== 10) {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>N&uacute;mero de tel&eacute;fono incorrecto</div>';
        formulario.phone.value = "";
        formulario.phone.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Direccion
    if (formulario.direccion.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingrese su direcci&oacute;n de residencia</div>';
        formulario.direccion.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Torre
    if (formulario.torre.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor seleccione su torre de residencia</div>';
        formulario.torre.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Apartamento
    if (formulario.apto.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor seleccione su apartamento de residencia</div>';
        formulario.apto.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Contrasenia
    if (formulario.contrasenia.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingrese su contrase&ntilde;a</div>';
        formulario.contrasenia.focus();
        return false;
    } else if (formulario.contrasenia.value.length < 7 || formulario.contrasenia.value.length > 20) {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Contrase&ntilde;a inv&aacute;lida</div>';
        formulario.contrasenia.value = "";
        formulario.contrasenia.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Confirmar contrasenia
    if (formulario.confirmar.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor confirme su contrase&ntilde;a</div>';
        formulario.confirmar.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }

    if (formulario.contrasenia.value !== formulario.confirmar.value) {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Contrase&ntilde;as no coinciden</div>';
        formulario.confirmar.value = "";
        formulario.confirmar.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Rol
    if (formulario.rol.value === "") {
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor asigne un rol</div>';
        formulario.rol.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }

    formulario.submit();
}