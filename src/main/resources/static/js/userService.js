const tableBody = document.querySelector('#users-table-body')



updateUsersTable()

function updateUsersTable() {
    tableBody.innerHTML = ""
    fetch("/admin/api/users/").then(response => response.json())
        .then(addUsersToTable)
}
function addUsersToTable(users) {
    users.forEach((user) =>{
        let newRow = document.createElement('tr')
        let idCell = document.createElement('td')
        idCell.textContent=user.id
        newRow.appendChild(idCell)

        let usernameCell = document.createElement('td')
        usernameCell.textContent=user.username
        newRow.appendChild(usernameCell)

        let firstNameCell = document.createElement('td')
        firstNameCell.textContent=user.firstName
        newRow.appendChild(firstNameCell)

        let lastNameCell = document.createElement('td')
        lastNameCell.textContent=user.lastName
        newRow.appendChild(lastNameCell)

        let emailCell = document.createElement('td')
        emailCell.textContent=user.email
        newRow.appendChild(emailCell)

        let rolesCell = document.createElement('td')
        for (let roleId in user.roles) {
            let newSpan = document.createElement('span')
            newSpan.textContent = user.roles[roleId].name
            if (roleId !== user.roles.length - 1) newSpan.textContent += " "
            rolesCell.appendChild(newSpan)
        }
        newRow.appendChild(rolesCell)

        let editButtonCell = document.createElement('td')
        let editButtonLink = document.createElement('a')
        editButtonLink.innerHTML = "<a class=\"btn btn-info text-white btn-sm\" role=\"button\" data-toggle=\"modal\" data-target=\"#userEditModal\">Edit</a>"
        editButtonCell.appendChild(editButtonLink)
        newRow.appendChild(editButtonCell)

        let deleteButtonCell = document.createElement('td')
        let deleteButtonLink = document.createElement('a')
        deleteButtonLink.innerHTML = "<a class=\"btn btn-danger text-white btn-sm\" role=\"button\" data-toggle=\"modal\" data-target=\"#userDeleteModal\">Delete</a>"
        deleteButtonCell.appendChild(deleteButtonLink)
        newRow.appendChild(deleteButtonCell)

        tableBody.appendChild(newRow)
    })
}

