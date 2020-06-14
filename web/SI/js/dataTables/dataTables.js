$(document).ready(function(){
    // Ejecutar dataTable
    $(document).ready(function() {
        $('.tabla').DataTable();
    } );
    // Cambiar idioma a español
    $('.tabla').DataTable({
        "language":{
            "url": "https://cdn.datatables.net/plug-ins/1.10.20/i18n/Spanish.json"
        }
    });
});