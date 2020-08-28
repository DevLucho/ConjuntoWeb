$(document).ready(function () {
    $('.tabla').DataTable({
        // paneles de busqueda
        searchPanes: {
            cascadePanes: true,
            dtOpts: {
                dom: 'tp',
                paging: 'true',
                pagingType: 'simple',
                searching: false
            }
        },
        dom: 'PBfrtilp',
        columnDefs: [{
                searchPanes: {
                    show: false
                },
                targets: [5]
            }
        ],
        //para los botones de exportar  
        responsive: "true",
        //dom: 'Bfrtilp',
        buttons: [
            {
                extend: 'excelHtml5',
                text: '<i class="fas fa-file-excel"></i> ',
                titleAttr: 'Exportar a Excel',
                className: 'btn btn-outline-success'
            },
            {
                extend: 'pdfHtml5',
                text: '<i class="fas fa-file-pdf"></i> ',
                titleAttr: 'Exportar a PDF',
                className: 'btn btn-outline-danger'
            },
            {
                extend: 'print',
                text: '<i class="fa fa-print"></i> ',
                titleAttr: 'Imprimir',
                className: 'btn btn-outline-info'
            }
        ]
    });
});