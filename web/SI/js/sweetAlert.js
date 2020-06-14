// Alerta reserva confirmada
$('#btn-confirmar').click(function(){
  Swal.fire({
    title: 'Motivo de reserva',
    input: 'textarea',
    inputPlaceholder: 'Describe el motivo de reserva...',
    inputAttributes: {
      'aria-label': 'Type your message here'
    },    
    showCancelButton: true,
    cancelButtonColor: '#d33',
    confirmButtonText: 'Continuar',
    cancelButtonText:'Cancelar'
  })
  // Swal.fire(
  //   'Reservado!',
  //   'Se ha reservado satisfactoriamente la zona comunal.',
  //   'success'
  // )
});
// Alerta eliminar/cancelar usuario
$('.btn-cancelar-usuario').click(function(){
  Swal.fire({
    title: 'Estas seguro que deseas cancelar este usuario?',
    text: "No podras revertilo!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Si, cancelar!',
    cancelButtonText:'No'
  }).then((result) => {
    if (result.value) {
      Swal.fire(
        'Cancelado!',
        'Se ha cancelado exitosamente el usuario.',
        'success'
      )
    }
  })
});
// Alerta eliminar/cancelar comunicado
$('.btn-eliminar-comunicado').click(function(){
  Swal.fire({
    title: 'Estas seguro que deseas eliminar este comunicado?',
    text: "No podras revertilo!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Si, eliminar!',
    cancelButtonText:'No'
  }).then((result) => {
    if (result.value) {
      Swal.fire(
        'Eliminado!',
        'Se ha eliminado exitosamente el comunicado.',
        'success'
      )
    }
  })
});
// Alerta eliminar/cancelar evento
$('.btn-cancelar-evento').click(function(){
  Swal.fire({
    title: 'Estas seguro que deseas cancelar este evento?',
    text: "No podras revertilo!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Si, cancelar!',
    cancelButtonText:'No'
  }).then((result) => {
    if (result.value) {
      Swal.fire(
        'Cancelado!',
        'Se ha cancelado exitosamente el evento.',
        'success'
      )
    }
  })
});
// Alerta eliminar/cancelar zona
$('.btn-cancelar-zona').click(function(){
  Swal.fire({
    title: 'Estas seguro que deseas eliminar esta zona común?',
    text: "No podras revertilo!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Si, eliminar!',
    cancelButtonText:'No'
  }).then((result) => {
    if (result.value) {
      Swal.fire(
        'Eliminado!',
        'Se ha eliminado exitosamente la zona común.',
        'success'
      )
    }
  })
});

// Popup confirmar publicacion comunicado
$('.btn-publicar-comunicado').click(function(){
  Swal.fire({
    icon: 'success',
    title: 'Se ha publicado el comunicado',
    showConfirmButton: false,
    timer: 1500
  })
});

// Alerta eliminar/cancelar comunicado
$('.btn-eliminar-comunicado').click(function(){
  Swal.fire({
    title: 'Estas seguro que deseas eliminar este comunicado?',
    text: "No podras revertilo!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Si, eliminar!',
    cancelButtonText:'No'
  }).then((result) => {
    if (result.value) {
      Swal.fire(
        'Eliminado!',
        'Se ha eliminado exitosamente el comunicado.',
        'success'
      )
    }
  })
});