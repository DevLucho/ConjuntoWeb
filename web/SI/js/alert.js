/* global Swal */

// Swal.fire({
//     title: "Hola pepe",
//     text:"lucho es feo :v",
//     html:'<div class="rojo">perra</div> <div class="azul">agonia</div><br>',
//     icon:'info',//chulito
//     //icon:'error'icon:'warning'icon:'info' icon:'question'
//     confirmButtonText:'<i><a href="../modulo_reportes/reportes.html"> hola</a></i>',
//     footer:"<br>esto es de vida o muerte <br>",
//     // background:'black' fondo
//     //width y padding
//     //grow:"fullscreen" tamaño
//     //backdrop:true tener o no la capa negra
//     //timer: 6000, tiempo de duracion
//     //timerProgressBar: true barrita del tiempo
//     //toast:true, hacer el poput peque
//     //position:"bottom-left" ubicar el poput 
//     allowOutsideClick:false,// oprimir afuera
//    // allowEnterKey:false, oprimir enter
//     //allowOutsideClick:false
//     //input:'select' tipo de input
//     //inputPlaceholder:'conten' 
//     //inputOptions:{Mexico:'Mexico', España:'Española'}
//     customClass:{
//         // container:'',
//          popup:'popup-class'
//         // header:'',
        // title:'',
//         // closeButton:'',
//         // icon:'',
//         // image:'',
//         // content:'',
//         // input:'',
//         // actions:'',
//         // confirButton:'',
//         // cancelButton:'',
//         // footer:'',
//     },
//     showConfirmButton:true, //agregar o no el boton
//     // confirmButtonColor:'#FFFFFF',// color del boton
//     // confirmButtonText:'epep',

//     // showCancelButton:true,// agregar o no el boton
//     // cancelButtonColor:'#000000',// color del boton
//     // cancelButtonText:'hpña'

//     buttonsStyling:true,
//     showCloseButton:false,

//     imageUrl:'../../img/bbq.jpg',
//     imageWidth:'200px',
//     imageAlt:'nnocs'
// });

 
// $('#botones').click(function(){
//     Swal.fire({
//         title:'Ficha de visitantes Creada <br>',
//         html:'<div class="circulo"><img src="../../img/caja.png" class="isos"></div> Para buscar datos, <br> modificar datos o agregar <br> datos ingresar a visitantes <br><br>   <img class="image-class" src="../../img/flechap.png">',
//         width:'42%',
//         padding:'40px 0px 50px 100px',
//         customClass:{
//             title:'title-class',
//             image:'image-class',
//             popup:'popup-class'
//         },
//         confirmButtonText:'Volver',
//         allowOutsideClick:false

//     })
//     return false;
// })

$('#botonEdit').click(function(){
    Swal.fire({
        title:'Ficha de visitantes Modificada <br>',
        html:'<div class="circulo"><img src="../../../img/caja.png" class="isos"></div> Para buscar datos, <br> modificar datos o agregar <br> datos ingresar a visitantes <br><br>   <img class="image-class" src="../../../img/flechap.png">',
        width:'42%',
        padding:'40px 0px 20px 60px',
        customClass:{
            title:'title-class',
            image:'image-class',
            popup:'popup-class'
        },
        confirmButtonText:'Volver',
        allowOutsideClick:false

    });
    return false;
});
// https://www.youtube.com/watch?v=wZtEwIr7_gg
function confirmameestaxd(){
    Swal.fire({
        title:'Entrega Exitosa <br>',
        html:'<div class="circulo"><i class="fas fa-paper-plane isos"></i></i></i></div>El paquete ha llegado al<br>residente...<br>   <img class="image-class" src="../../../img/flechap.png">',
        padding:'40px 0px 20px 60px',
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
function validar_form(){
    
    var formulario = document.form;

    // Nombres
    if(formulario.nombre.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger">Porfavor ingrese su nombre</div>';
        formulario.nombre.focus();
        return false;
    } 
    else if(validar_texto(formulario.nombre.value) === false){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger">No se permiten valores numericos en el nombre</div>';
        formulario.nombre.value = "";
        formulario.nombre.focus();
        return false;      
    } 
    else {
        document.getElementById("alerta").innerHTML = "";
    }

      // Apellidos
      if(formulario.apellido.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger">Porfavor ingrese su apellido</div>';
        formulario.apellido.focus();
        return false;
    }
    else if(validar_texto(formulario.apellido.value) === false){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger">No se permiten valores numericos en el apellido</div>';
        formulario.apellido.value = "";
        formulario.apellido.focus();
        return false;      
    }  
    else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Numero de identificación
    if(formulario.identificacion.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger">Porfavor ingrese su numero de documento</div>';
        formulario.identificacion.focus();
        return false;
    } 
    else if(formulario.identificacion.value.length !==10){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger">Número de documento incorrecto</div>';
        formulario.identificacion.value = "";
        formulario.identificacion.focus();
        return false;  
    } 
    else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Torre
    if(formulario.torre.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger">Porfavor seleccione su torre de residencia</div>';
        formulario.torre.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // Apartamento
    if(formulario.apto.value === ""){
        document.getElementById("alerta").innerHTML = '<div class="alert alert-danger">Porfavor seleccione su apartamento de residencia</div>';
        formulario.apto.focus();
        return false;
    } else {
        document.getElementById("alerta").innerHTML = "";
    }
    // poput
    Swal.fire({
        title:'Ficha de visitantes Creada <br>',
        html:'<div class="circulo"><img src="../../../img/caja.png" class="isos"></div> Para buscar datos, <br> modificar datos o agregar <br> datos ingresar a visitantes <br><br>   <img class="image-class" src="../../../img/flechap.png">',
        width:'42%',
        padding:'40px 0px 50px 100px',
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
