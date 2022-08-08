const tableBody = document.querySelector('#users-table-body')
const editModal = document.querySelector('#userEditModal')
const deleteModal = document.querySelector('#userDeleteModal')


updateUsersTable()

function updateUsersTable() {
    tableBody.innerHTML = ""
    fetch("/admin/api/users/").then(response => response.json())
        .then(addUsersToTable)

    function addUsersToTable(users) {
        users.forEach((user) =>{
            let newRow = document.createElement('tr')
            let idCell = document.createElement('td')
            idCell.textContent=user.id
            newRow.appendChild(idCell)

            let usernameCell = document.createElement('td')
            usernameCell.textContent=user.username
            usernameCell.id = "usernameCell_" + user.id
            newRow.appendChild(usernameCell)

            let firstNameCell = document.createElement('td')
            firstNameCell.textContent=user.firstName
            firstNameCell.id = "firstNameCell_" + user.id
            newRow.appendChild(firstNameCell)

            let lastNameCell = document.createElement('td')
            lastNameCell.textContent=user.lastName
            lastNameCell.id = "lastNameCell_" + user.id
            newRow.appendChild(lastNameCell)

            let emailCell = document.createElement('td')
            emailCell.textContent=user.email
            emailCell.id = "emailCell_" + user.id
            newRow.appendChild(emailCell)

            let rolesCell = document.createElement('td')
            for (let roleId in user.roles) {
                let newSpan = document.createElement('span')
                newSpan.textContent = user.roles[roleId].name
                newSpan.dataset.roleId = user.roles[roleId].id
                if (roleId !== user.roles.length - 1) rolesCell.innerHTML += " "
                rolesCell.appendChild(newSpan)
            }
            rolesCell.id = "rolesCell_" + user.id
            newRow.appendChild(rolesCell)

            let editButtonCell = document.createElement('td')
            let editButtonLink = document.createElement('a')
            editButtonLink.innerHTML = `<a class=\"btn btn-info text-white btn-sm\" role=\"button\" data-toggle=\"modal\" data-target=\"#userEditModal\" data-userId=\"${user.id}\">Edit</a>`
            editButtonLink.onclick = (event) => openEditModal(event)
            editButtonCell.appendChild(editButtonLink)
            newRow.appendChild(editButtonCell)

            let deleteButtonCell = document.createElement('td')
            let deleteButtonLink = document.createElement('a')
            deleteButtonLink.innerHTML = `<a class=\"btn btn-danger text-white btn-sm\" role=\"button\" data-toggle=\"modal\" data-target=\"#userDeleteModal\" data-userId=\"${user.id}\">Delete</a>`
            deleteButtonLink.onclick = (event) => openDeleteModal(event)
            deleteButtonCell.appendChild(deleteButtonLink)
            newRow.appendChild(deleteButtonCell)

            tableBody.appendChild(newRow)
        })
    }
}
function openEditModal(event) {
    let userId = event.target.dataset.userid
    fetch("/admin/api/users/" + userId).then(response => response.json())
        .then((data) => {updateModal(data, editModal)})
}

function openDeleteModal(event) {
    let userId = event.target.dataset.userid
    fetch("/admin/api/users/" + userId).then(response => response.json())
        .then((data) => {updateModal(data, deleteModal)})
}

function updateModal(user, modal) {
    document.querySelector("#" + modal.id + " .idField").setAttribute("value", user.id)
    document.querySelector("#" + modal.id + " .usernameField").setAttribute("value", user.username)
    document.querySelector("#" + modal.id + " .firstNameField").setAttribute("value", user.firstName)
    document.querySelector("#" + modal.id + " .lastNameField").setAttribute("value", user.lastName)
    document.querySelector("#" + modal.id + " .emailField").setAttribute("value", user.email)
    let userSelectedRoles = []
    for (let roleIndex in user.roles) {
        userSelectedRoles.push(user.roles[roleIndex].id)
    }
    document.querySelectorAll("#" + modal.id + " .rolesField option").forEach((optionNode) => {
        if (userSelectedRoles.includes(Number(optionNode.getAttribute("value")))) {
            optionNode.setAttribute("selected", true)
        } else {
            optionNode.removeAttribute("selected")
        }
    })
}

