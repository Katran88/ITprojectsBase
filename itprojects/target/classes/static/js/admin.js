async function genListOfProjectsForAdmin() {
    let token = localStorage.getItem('token');
    let someList = document.querySelector('.someList');
    someList.innerHTML = '';
    let listProject = await getAllProjectsForAdmin(token);

    for (let i = 0; i < listProject.length; i++) {
        let genDiv = div();
        let genP1 = p(listProject[i]['name'] + ' ' + listProject[i]['description']);
        let genBut = buttonWithParams('get');

        genBut.onclick = async () => {
            await getCertainProject(listProject[i]);
        };
        genDiv.appendChild(genP1);
        genDiv.appendChild(genBut);
        someList.appendChild(genDiv);
    }
}

async function getCertainProject(softObj) {
    console.log(softObj);
    let token = localStorage.getItem('token');
    let soft = await getProjectByName(softObj['name'], token);

    document.getElementById('name').value = soft['name'];
    document.getElementById('description').value = soft['description'];
}

async function genProject() {
    let info = document.querySelector(".personalInfo");
    let name = input('text', 'name', 'Name', '');
    let description = input('text', 'description', 'Description', '');

    info.appendChild(name);
    info.appendChild(description);
}

async function genAdminCreate() {

    let create = document.querySelector('.create');
    let createButton = button(await genAdminCreateButton, 'Create');
    createButton.id = 'docCreateButton';
    create.appendChild(createButton);

}

async function genAdminDelete() {

    let create = document.querySelector('.create');
    let deleteButton = button(genDeleteProject, 'Delete');
    create.appendChild(deleteButton);
}

async function genAdminCreateButton() {
    let token = localStorage.getItem('token');
    let errMes = document.getElementById('errMes');

    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;

    let isNotExist = await isProjectExist({name: name}, token);

    if (validateDocument() && await isAuth() && isNotExist.ok) {
        let data = {
            name: name,
            description: description,

        };
        await createProject(data, token);
        console.log(data);
        errMes.innerHTML = 'Created';
        await genListOfProjectsForAdmin();
    } else {
        errMes.innerHTML = 'Not all fields are correct or project exist';
    }
}

async function genDeleteProject() {
    let errMes = document.getElementById('errMes');
    let token = localStorage.getItem('token');
    let name = document.getElementById('name').value;
    let isNotExist = await isProjectExist({name: name}, token);
    if (await isAuth() && !isNotExist.ok) {

        let project = await getProjectByName( name, token);
        let isNotExist =await isUserProjectExistByProjectId({id: project['id']}, token);
        if (isNotExist.ok) {
            let result = await deleteProjectByName({name: name}, token);
            if (result.ok) {
                errMes.innerHTML = 'deleted';
                await genListOfProjectsForAdmin();
            }else{
                errMes.innerHTML = 'smt wrong';
            }

        } else {
            errMes.innerHTML = 'u cant delete this product while product had subed users';
        }

    } else {
        errMes.innerHTML = 'nothing to delete';
    }
}

function validateDocument() {
    let nameL = document.getElementById('name').value.length;
    let descriptionL = document.getElementById('description').value.length;

    if (!(nameL >= 2 && nameL <= 16)) {
        return false;
    }
    if (!(descriptionL >= 4)) {
        return false;
    }
    return true;

}

async function isAdmin() {
    let user = await getUser();
    return user['role'] === 'ROLE_ADMIN';
}

async function genUpdateProject() {
    let errMes = document.getElementById('errMes');
    let token = localStorage.getItem('token');
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    let isNotExist = await isProjectExist({name: name}, token);
    if (await isAuth() && !isNotExist.ok) {

        let res = await getProjectByName(name, token);

        if (validateDocument()) {
            await updateProject({
                id: res['id'],
                name: name,
                description: description,

            }, token);
            errMes.innerHTML = 'updated';
            await genListOfProjectsForAdmin();
        } else {
            errMes.innerHTML = 'not validate data';
        }

    } else {
        errMes.innerHTML = 'nothing to update';
    }
}

async function genAdminUpdate() {
    let create = document.querySelector('.update');
    let deleteButton = button(genUpdateProject, 'update');
    create.appendChild(deleteButton);
}

async function accept(userProductsListElement, token) {
    await updateUserProduct({
        id: userProductsListElement['id'],
        denide: false,
        set: true
    }, token);
    await genAdminInfo();
}

async function denide(userProductsListElement, token) {
    await updateUserProduct({
        id: userProductsListElement['id'],
        denide: true,
        set: true
    }, token);
    await genAdminInfo();
}

async function genAdminInfo() {
    let token = localStorage.getItem('token');
    let userProductsList = await getAllUserProduct(token);

    console.log(userProductsList);
    let info = document.querySelector('.neededInfo');
    let table = document.createElement('table');
    info.innerHTML = '';
    table.setAttribute('class', 'table');

    for (let i = 0; i < userProductsList.length; i++) {

        if (i === 0) {
            let tr = document.createElement('tr');
            let th1 = document.createElement('th');
            th1.innerHTML = 'Project name';
            let th2 = document.createElement('th');
            th2.innerHTML = 'Project description';
            let th3 = document.createElement('th');
            th3.innerHTML = 'User name';
            let th4 = document.createElement('th');
            th4.innerHTML = 'Accept';
            let th5 = document.createElement('th');
            th5.innerHTML = 'Denide';

            tr.appendChild(th1);
            tr.appendChild(th2);
            tr.appendChild(th3);
            tr.appendChild(th4);
            tr.appendChild(th5);
            table.appendChild(tr);
        }
        let isSet = userProductsList[i]['set'];
        if (isSet === false) {
            let tr = document.createElement('tr');
            for (let y = 0; y < 7; y++) {
                let th = document.createElement('th');
                let user = userProductsList[i]['user'];
                let project = userProductsList[i]['project'];

                switch (y) {
                    case 0: {
                        th.innerHTML = project['name'];
                        break;
                    }
                    case 1: {
                        th.innerHTML = project['description'];
                        break;
                    }
                    case 2: {
                        th.innerHTML = user['login'];
                        break;
                    }
                    case 3: {
                        let subButton = buttonWithParams('Accept');
                        subButton.onclick = async () => {
                            await accept(userProductsList[i], token);
                        };
                        th.appendChild(subButton);
                        break;
                    }
                    case 4: {
                        let subButton = buttonWithParams('Denide');
                        subButton.onclick = async () => {
                            await denide(userProductsList[i], token);
                        };
                        th.appendChild(subButton);
                        break;
                    }
                }
                tr.appendChild(th);

            }
            table.appendChild(tr);
        }

    }
    info.appendChild(table);
}


async function genListOfSubscribedUsers() {
    let token = localStorage.getItem('token');
    let userData = await getUserByToken(token);
    let text = await userData.text();
    let textUserData = JSON.parse(text);
    let subPatients = await getAllSubPatients(textUserData['id'], token);


    let info = document.querySelector('.neededInfo');
    let table = document.createElement('table');
    table.setAttribute('class', 'table');

    for (let i = 0; i < subPatients.length; i++) {

        if (i === 0) {
            let tr = document.createElement('tr');
            let th1 = document.createElement('th');
            th1.innerHTML = 'Name';
            let th2 = document.createElement('th');
            th2.innerHTML = 'Surname';
            let th3 = document.createElement('th');
            th3.innerHTML = 'FatherName';
            let th4 = document.createElement('th');
            th4.innerHTML = 'Patient Report';
            let th5 = document.createElement('th');
            th5.innerHTML = 'Sick';
            let th6 = document.createElement('th');
            th6.innerHTML = 'Recommendation';
            let th7 = document.createElement('th');
            th7.innerHTML = 'Publish Recommendation';
            tr.appendChild(th1);
            tr.appendChild(th2);
            tr.appendChild(th3);
            tr.appendChild(th4);
            tr.appendChild(th5);
            tr.appendChild(th6);
            tr.appendChild(th7);
            table.appendChild(tr);
        }
        let tr = document.createElement('tr');
        for (let y = 0; y < 7; y++) {
            let th = document.createElement('th');


            switch (y) {
                case 0: {
                    th.innerHTML = subPatients[i]['name'];
                    break;
                }
                case 1: {
                    th.innerHTML = subPatients[i]['surname'];
                    break;
                }
                case 2: {
                    th.innerHTML = subPatients[i]['fathername'];
                    break;
                }
                case 3: {
                    th.innerHTML = subPatients[i]['patientReport'];
                    break;
                }
                case 4: {
                    th.innerHTML = subPatients[i]['sicks'][0];
                    break;
                }
                case 5: {
                    let inputInfo = input('text', subPatients[i]['name'], 'for patient');
                    th.appendChild(inputInfo);
                    break;
                }
                case 6: {
                    let subButton = buttonWithParams('publish');
                    subButton.onclick = async () => {
                        await publishRecommendation(subPatients[i],
                            document.getElementById(subPatients[i]['name']).value,
                            token
                        );
                    };
                    th.appendChild(subButton);
                    break;
                }
            }
            tr.appendChild(th);

        }
        table.appendChild(tr);
    }
    info.appendChild(table);
}

async function genDocSearchButton() {
    let token = localStorage.getItem('token');
    let userData = await getUserByToken(token);
    let text = await userData.text();
    let textUserData = JSON.parse(text);
    let subPatients = await getAllSubPatients(textUserData['id'], token);
    let info = document.querySelector('.neededInfo');

    let inputResult = document.getElementById('searchPatientCard').value;
    info.innerHTML = '';

    if (inputResult.length === 0) {
        await genListOfSubscribedUsers();
    } else {
        let table = document.createElement('table');
        table.setAttribute('class', 'table');

        for (let i = 0; i < subPatients.length; i++) {

            if (i === 0) {
                let tr = document.createElement('tr');
                let th1 = document.createElement('th');
                th1.innerHTML = 'Name';
                let th2 = document.createElement('th');
                th2.innerHTML = 'Surname';
                let th3 = document.createElement('th');
                th3.innerHTML = 'FatherName';
                let th4 = document.createElement('th');
                th4.innerHTML = 'Patient Report';
                let th5 = document.createElement('th');
                th5.innerHTML = 'Sick';
                let th6 = document.createElement('th');
                th6.innerHTML = 'Recommendation';
                let th7 = document.createElement('th');
                th7.innerHTML = 'Publish Recommendation';
                tr.appendChild(th1);
                tr.appendChild(th2);
                tr.appendChild(th3);
                tr.appendChild(th4);
                tr.appendChild(th5);
                tr.appendChild(th6);
                tr.appendChild(th7);
                table.appendChild(tr);
            }
            if (inputResult === subPatients[i]['name']) {
                let tr = document.createElement('tr');
                for (let y = 0; y < 7; y++) {
                    let th = document.createElement('th');

                    switch (y) {
                        case 0: {
                            th.innerHTML = subPatients[i]['name'];
                            break;
                        }
                        case 1: {
                            th.innerHTML = subPatients[i]['surname'];
                            break;
                        }
                        case 2: {
                            th.innerHTML = subPatients[i]['fathername'];
                            break;
                        }
                        case 3: {
                            th.innerHTML = subPatients[i]['patientReport'];
                            break;
                        }
                        case 4: {
                            th.innerHTML = subPatients[i]['sicks'][0];
                            break;
                        }
                        case 5: {
                            let inputInfo = input('text', subPatients[i]['name'], 'for patient');
                            th.appendChild(inputInfo);
                            break;
                        }
                        case 6: {
                            let subButton = buttonWithParams('publish');
                            subButton.onclick = async () => {
                                await publishRecommendation(subPatients[i],
                                    document.getElementById(subPatients[i]['name']).value,
                                    token
                                );
                            };
                            th.appendChild(subButton);
                            break;
                        }
                    }
                    tr.appendChild(th);

                }

                table.appendChild(tr);
            }
        }
        info.appendChild(table);
    }
}

async function genAdminSearch() {

    let search = document.querySelector('.search');
    let searchButton = button(await genDocSearchButton, 'Search');
    let searchPatientCard = input('text', 'searchPatientCard', 'patient name');
    searchButton.id = 'docCreateButton';
    search.appendChild(searchPatientCard);
    search.appendChild(searchButton);
}
