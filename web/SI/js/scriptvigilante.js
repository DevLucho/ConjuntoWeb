/* global Swal */

// Validar texto, acentos y ñ
function validar_texto(parametro){
    var patron = /^[a-zA-ZáéíóúñÑ\s]*$/;
    if(parametro.search(patron)){
        return false;
    }
    else{
        return true;
    }
}
// validar formulario
function validar_formulario (){    
    var formulario = document.form;
    // Nombres
    if(formulario.nombre.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingrese su nombre</div>';
        formulario.nombre.focus();
        return false;
    } 
    else if(validar_texto(formulario.nombre.value) === false){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>No se permiten valores númericos en el nombre</div>';
        formulario.nombre.value = "";   
        formulario.nombre.focus();
        return false;      
    } 
    else {
        document.getElementById("alerta").innerHTML = "";
    }

      // Apellidos
      if(formulario.apellido.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingrese su apellido</div>';
        formulario.apellido.focus();
        return false;
    }
    else if(validar_texto(formulario.apellido.value) === false){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>No se permiten valores númericos en el apellido</div>';
        formulario.apellido.value = "";
        formulario.apellido.focus();
        return false;      
    }  
    else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Torre
    if(formulario.torre.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor seleccione su torre de residencia</div>';
        formulario.torre.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Apartamento
    if(formulario.apto.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor seleccione su apartamento de residencia</div>';
        formulario.apto.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // paquete
    if(formulario.paquete.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingresar paquete</div>';
        formulario.paquete.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // empresa
    if(formulario.empresa.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingresar empresa</div>';
        formulario.empresa.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }

    Swal.fire({
        title:'Datos Guardados <br>',
        // html:'<div class="circulo"><i class="fas fa-save isos"></i></div> Tiempo disponible para entregar el<br>domicilio de max. 10 min desde<br> "variable donde se guarda la hora" <br><br>   <img class="image-class" src="../img/flechap.png">',
        html:'<div class="circulo"><i class="fas fa-save isos"></i></div> Tiempo disponible para entregar el<br/>domicilio de max. 10 min desde<br/> "variable donde se guarda la hora" <br/><br/><img class="image-class" src="../img/flechap.png">',
        padding:'40px 0px 20px 120px',
        customClass:{
            title:'title-class',
            image:'image-class',
            popup:'popup-class',
            imageUrl:'flechap.png',
    imageWidth:'200px',
     imageAlt:'nnocs'
        },
        confirmButtonText:'Volver',
        allowOutsideClick:false    
        });
        return false;
        formulario.submit();
}

// validar formulario
function validar_formEdit (){    
    var formulario = document.form;
    // Nombres
    if(formulario.nombre.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingrese su nombre</div>';
        formulario.nombre.focus();
        return false;
    } 
    else if(validar_texto(formulario.nombre.value) === false){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>No se permiten valores númericos en el nombre</div>';
        formulario.nombre.value = "";
        formulario.nombre.focus();
        return false;      
    } 
    else {
        document.getElementById("alerta").innerHTML = "";
    }

      // Apellidos
      if(formulario.apellido.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingrese su apellido</div>';
        formulario.apellido.focus();
        return false;
    }
    else if(validar_texto(formulario.apellido.value) === false){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>No se permiten valores númericos en el apellido</div>';
        formulario.apellido.value = "";
        formulario.apellido.focus();
        return false;      
    }  
    else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Torre
    if(formulario.torre.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor seleccione su torre de residencia</div>';
        formulario.torre.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Apartamento
    if(formulario.apto.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor seleccione su apartamento de residencia</div>';
        formulario.apto.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // paquete
    if(formulario.paquete.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingresar paquete</div>';
        formulario.paquete.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // empresa
    if(formulario.empresa.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingresar empresa</div>';
        formulario.empresa.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }

    Swal.fire({
        title:'Modificacion Guardada <br>',
        html:'<div class="circulo"><img src="../../img/carpeta.png" class="isos"></div>  Tiempo disponible para entregar el<br>domicilio de max. 10 min desde<br> "variable donde se guarda la hora" <br>   <img class="image-class" src="../../img/flechap.png">',
        width:'42%',
        padding:'30px 0px 10px 100px',
        customClass:{
            title:'title-class',
            image:'image-class',
            popup:'popup-class'
        },
        confirmButtonText:'Volver',
        allowOutsideClick:false

    });
        return false;
        formulario.submit();
}

// validar formulario
function validar_formMM (){    
    var formulario = document.form;
    // Torre
    if(formulario.torre.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor seleccione su torre de residencia</div>';
        formulario.torre.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Apartamento
    if(formulario.apto.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor seleccione su apartamento de residencia</div>';
        formulario.apto.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // paquete
    if(formulario.paquete.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingresar paquete</div>';
        formulario.paquete.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // empresa
    if(formulario.empresa.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingresar empresa</div>';
        formulario.empresa.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }

    Swal.fire({
        title:'Datos Guardados <br>',
        html:'<div class="circulo"><i class="fas fa-envelope-open isos"></i></div> Para buscar datos<br>modificar datos o agregar<br>datos ingresar en el menu<br>   <img class="image-class" src="../../img/flechap.png">',
        padding:'40px 0px 20px 120px',
        customClass:{
            title:'title-class',
            image:'image-class',
            popup:'popup-class'
        },
        confirmButtonText:'Volver',
        allowOutsideClick:false    
        });
        return false;
        formulario.submit();
}
                        
function activar(){
    var campo= $('#empresa').val();
    if((campo !== null) && (campo !=='')){
        $("#btoE").attr('disabled', false);
    }
    else{
        $("#btoE").attr('disabled', true);
    }
}

function activiti(){
    

    Swal.fire({
        title:'Notificacion Exitosa <br>',
        html:'<div class="circulo fortuna"><i class="far fa-bell isos isos2"></i></i></div>El mensaje ha llegado al<br>usuario.Para confirmar la<br>entrega ingresar a busqueda<br>de datos <br>   <img class="image-class" src="../img/flechap.png">',
        padding:'40px 0px 20px 120px',
        customClass:{
            title:'title-class',
            image:'image-class',
            popup:'popup-class'
        },
        confirmButtonText:'Volver',
        allowOutsideClick:false    
        });
        return false;
}

// validar formulario
function validar_formME (){    
    var formulario = document.form;
    // Torre
    if(formulario.torre.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor seleccione su torre de residencia</div>';
        formulario.torre.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Apartamento
    if(formulario.apto.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor seleccione su apartamento de residencia</div>';
        formulario.apto.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // paquete
    if(formulario.paquete.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingresar paquete</div>';
        formulario.paquete.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // empresa
    if(formulario.empresa.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>Porfavor ingresar empresa</div>';
        formulario.empresa.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }

    Swal.fire({
        title:'Edicion Exitosa <br>',
        html:'<div class="circulo"><i class="fas fa-edit isos"></i></i></div> Para buscar datos<br>modificar datos o agregar<br>datos ingresar en el menu<br>   <img class="image-class" src="../../img/flechap.png">',
        padding:'40px 0px 20px 120px',
        customClass:{
            title:'title-class',
            image:'image-class',
            popup:'popup-class'
        },
        confirmButtonText:'Volver',
        allowOutsideClick:false    
    });
    return false;
    formulario.submit();
}




var elementTop = $('.nav').offset().top;

$(window).scroll(function(){
    if( $(window).scrollTop() >= elementTop){
        $('body').addClass('nav_fixed');
    }else{
        $('body').removeClass('nav_fixed');
    }
});

$('.btn-menu').on('click', function(){
    $('.nav').toggleClass('nav-toggle');
});
