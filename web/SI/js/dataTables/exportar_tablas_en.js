$(document).ready(function () {
    $('.tabla').DataTable({
        // Para cambiar idioma   
        "language": {
            "url": "https://cdn.datatables.net/plug-ins/1.10.20/i18n/Spanish.json"
        },
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
                titleAttr: 'Export to Excel',
                className: 'btn btn-outline-success'
            },
            {
                extend: 'pdfHtml5',
                text: '<i class="fas fa-file-pdf"></i> ',
                titleAttr: 'Export to PDF',
                className: 'btn btn-outline-danger'
            },
            {
                extend: 'print',
                text: '<i class="fa fa-print"></i> ',
                titleAttr: 'Print',
                className: 'btn btn-outline-info'
            }
        ]
    });
});