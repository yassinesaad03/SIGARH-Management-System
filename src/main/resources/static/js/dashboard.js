const tableBody = document.getElementById("tableBody");

const message = document.getElementById("message");

const searchInput = document.getElementById("searchInput");

const btnAdd = document.getElementById("btnAdd");

let personnels = [];


/* ==================================
        Charger les personnels
================================== */

async function loadPersonnels(){

    try{

        const response = await fetch(
            "http://localhost:8081/personnel/getAllPersonnel",
            { cache: "no-store" }          
        );

        if(!response.ok){

            throw new Error();

        }

        personnels = await response.json();

        afficherPersonnels(personnels);

    }

    catch(error){

        message.style.color="#e53935";

        message.innerHTML =
        '<i class="fa-solid fa-triangle-exclamation"></i> Impossible de charger la liste des personnels.';

    }

}

function getStatusClass(statut){

    switch(statut){

        case "ACTIF":
            return "status-actif";

        case "RETRAITE":
            return "status-retraite";

        case "ACCIDENT_DE_TRAVAIL":
            return "status-accident";

        case "EXCLU":
            return "status-exclu";

        case "MISE_EN_DISPONIBILITE":
            return "status-disponibilite";

        case "DETACHE":
            return "status-detache";

        default:
            return "";
    }

}

function formatStatus(statut){

    switch(statut){

        case "ACTIF":
            return "Actif";

        case "RETRAITE":
            return "Retraité";

        case "ACCIDENT_DE_TRAVAIL":
            return "Accident de travail";

        case "EXCLU":
            return "Exclu";

        case "MISE_EN_DISPONIBILITE":
            return "Mise en disponibilité";

        case "DETACHE":
            return "Détaché";

        default:
            return statut;
    }

}

/* ==================================
        Affichage tableau
================================== */

function afficherPersonnels(liste){

    tableBody.innerHTML="";

    if(liste.length===0){

        tableBody.innerHTML=`

            <tr>

                <td colspan="9" style="text-align:center;padding:30px">

                    Aucun personnel trouvé.

                </td>

            </tr>

        `;

        return;

    }

    liste.forEach(personnel => {

        const photoUrl = personnel.photo
            ? "http://localhost:8081/uploads/" + personnel.photo
            : "images/default-avatar.png";

        tableBody.innerHTML += `
        <tr>

            <td>
                <img
                    src="${photoUrl}"
                    alt="Photo"
                    class="photo-thumb">
            </td>

            <td>${personnel.matricule}</td>

            <td>${personnel.nom}</td>

            <td>${personnel.prenom}</td>

            <td>${personnel.genre}</td>

            <td>${personnel.grade}</td>

            <td>${personnel.poste}</td>

            <td>${personnel.numTelephone1}</td>

            <td>
                <span class="status-badge ${getStatusClass(personnel.statut)}">
                    ${formatStatus(personnel.statut)}
                </span>
            </td>

            <td class="actions">

                <button
                    class="btn-edit"
                    onclick="modifierPersonnel(${personnel.id})">

                    <i class="fa-solid fa-pen"></i>

                    Modifier

                </button>

            </td>

        </tr>
    `;

    });

}



/* ==================================
            Recherche
================================== */

searchInput.addEventListener("input",function(){

    const texte = this.value.toLowerCase();

    const resultat = personnels.filter(p =>

        (p.nom || "").toLowerCase().includes(texte)

        ||

        (p.prenom || "").toLowerCase().includes(texte)

        ||

        String(p.matricule || "").includes(texte)

    );

    afficherPersonnels(resultat);

});




/* ==================================
        Ajouter personnel
================================== */

btnAdd.addEventListener("click",()=>{

    window.location.href="form.html";

});




/* ==================================
        Modifier
================================== */

function modifierPersonnel(id){

    window.location.href="form.html?id="+id;

}




/* ==================================
        Supprimer
================================== */

async function supprimerPersonnel(id){

    const confirmation = confirm(

        "Voulez-vous vraiment supprimer ce personnel ?"

    );

    if(!confirmation){

        return;

    }

    try{

        const response = await fetch(

            "http://localhost:8081/personnel/deletePersonnel/"+id,

            {

                method:"DELETE"

            }

        );

        if(response.ok){

            message.style.color="#43a047";

            message.innerHTML =

            '<i class="fa-solid fa-circle-check"></i> Personnel supprimé avec succès.';

            loadPersonnels();

        }

        else{

            throw new Error();

        }

    }

    catch(error){

        message.style.color="#e53935";

        message.innerHTML =

        '<i class="fa-solid fa-circle-xmark"></i> Erreur lors de la suppression.';

    }

}

/* ==================================
        DECONNEXION
================================== */


const btnLogout =
    document.getElementById("btnLogout");



btnLogout.addEventListener("click",()=>{


    const confirmation =
        confirm("Voulez-vous vraiment vous déconnecter ?");



    if(confirmation){


        // Retour vers la page login

        window.location.href="index.html";


    }


});

/* ==================================
        Initialisation
================================== */

loadPersonnels();
window.addEventListener("pageshow", (event) => {

    // event.persisted = true si la page vient du bfcache
    if(event.persisted){

        loadPersonnels();

    }

});