fillMainTable()

function fillMainTable() {
    const tableRows = document.querySelector('.users-table')
    fetch("/admin/api/users/").then(response => response.json()).then(data => console.log(data))
}

