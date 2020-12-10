async function genSearchUser() {
    let search = document.querySelector('.search');
    let searchButton = button(await genSearchButton, 'Search');
    let searchPatientCard = input('text','searchSoft','soft name');
    searchButton.id = 'searchButton';
    search.appendChild(searchPatientCard);
    search.appendChild(searchButton);
}
async function genSearchButton() {
    let token = localStorage.getItem('token');
    let someList = document.querySelector('.someList');
    someList.innerHTML = '';
    let listProject = await getAllProjectsForUser(token);

    if (document.getElementById('searchSoft').value.length === 0) {
        await genListOfProjectsForUser()
    } else {
        let found = false;
        for (let i = 0; i < listProject.length; i++) {
            if (listProject[i]['name'] === document.getElementById('searchSoft').value) {
                found= true;
                let genDiv = div();
                let genP1 = p(listProject[i]['name'] + ' ' + listProject[i]['description']);
                let genBut = buttonWithParams('subscribe');

                genBut.onclick = async () => {
                    await subUser(listProject[i]);
                };
                genDiv.appendChild(genP1);
                genDiv.appendChild(genBut);
                someList.appendChild(genDiv);
            }
        }
        if(!found){
            let errMes = document.getElementById('errMes');
            errMes.innerHTML='nobody found (to return full list of projects leave this field empty)'
        }
    }
}

async function load() {

    let result = document.querySelector('.results');
    await generateListOfUsers(result);
    genPrev();
    await genNext();


    if (await isAuth()) {
        genLogout();
        if (await isAdmin()) {
            await genProject();
            await genAdminCreate();
            await genAdminUpdate();
            await genAdminDelete();
            await genListOfProjectsForAdmin();
            await genAdminInfo();


        } else {
            await genListOfProjectsForUser();
            await genUserInfo();
            await genSearchUser();
        }
    } else {
        genLogReg(result);

    }

}
