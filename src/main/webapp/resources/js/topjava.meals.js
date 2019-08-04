$(function () {
    makeEditable({
        ajaxUrl: "ajax/meals/",
        filterUrl: "",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    });
});

function setFilter() {
    var filterUrl = "filter?startDate=" + $("#startDate").val()
        + "&endDate=" + $("#endDate").val() + "&startTime="
        + $("#startTime").val() + "&endTime=" + $("#endTime").val();
    setFilterUrl(filterUrl);
    updateTable();
}

function clearFilter() {
    var filterUrl = "";
    setFilterUrl(filterUrl);
    updateTable(filterUrl);
}