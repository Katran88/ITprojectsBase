async function subUser(listProjectElement) {
    let token = localStorage.getItem('token');
    let userData = await getUserByToken(token);
    let text = await userData.text();
    let textUserData = JSON.parse(text);
    let errMes = document.getElementById('errMes');
    let isNotSubed = await isSubscribed({user:textUserData , project:listProjectElement},token);
    if(isNotSubed.ok){
        await createUserProject({user:textUserData , project:listProjectElement},token);
        errMes.innerHTML = "subbed on product -" + listProjectElement['name'];
    }else{
        errMes.innerHTML = "u've already sub on this product -" + listProjectElement['name'];
    }
}
async function genUserInfo() {
    let token = localStorage.getItem('token');
    let user = await getUser();
    let userProductsList = await getUserProductListByUserName(user['login'],token);

    console.log(userProductsList);
    let info = document.querySelector('.neededInfo');
    let table = document.createElement('table');
    info.innerHTML='';
    table.setAttribute('class' , 'table');

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
            th4.innerHTML = 'Accept/Denide';


            tr.appendChild(th1);
            tr.appendChild(th2);
            tr.appendChild(th3);
            tr.appendChild(th4);
            table.appendChild(tr);
        }
        let isSet = userProductsList[i]['set'];
        if(isSet===true) {
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
                        th.innerHTML = userProductsList[i]['denide']===true ? "Denide" : "Accept";
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
async function genListOfProjectsForUser() {
    let token = localStorage.getItem('token');
    let someList = document.querySelector('.someList');
    someList.innerHTML = '';
    let listProject = await getAllProjectsForUser(token);

    for (let i = 0; i < listProject.length; i++) {
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
