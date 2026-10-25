/* ==================================
        Mode ajout / modification
================================== */

const params = new URLSearchParams(window.location.search);

const personnelId = params.get("id");

const form = document.getElementById("personnelForm");

const message = document.getElementById("message");

const genre = document.getElementById("genre");
const nomJeuneFilleField = document.getElementById("nomJeuneFilleField");
const nomJeuneFille = document.getElementById("nomJeuneFille");

const dropZone = document.getElementById("dropZone");

const photoInput = document.getElementById("photoInput");

const photoPreview = document.getElementById("photoPreview");

let photoFileName = null;


async function uploadPhoto(file){

    const formData = new FormData();

    formData.append("photo", file);   // ✅ correspond à @RequestParam("photo")

    const response = await fetch(
        "http://localhost:8081/upload/image",   // ✅ URL correcte
        {
            method: "POST",
            body: formData
        }
    );

    if(!response.ok){
        throw new Error("Échec de l'upload de l'image");
    }

    photoFileName = await response.text();

}

function afficherImage(file){

    const reader = new FileReader();

    reader.onload = function(e){

        photoPreview.src = e.target.result;

        photoPreview.style.display = "block";

        document.querySelector(".drop-text").style.display="none";

    }

    reader.readAsDataURL(file);

}
dropZone.addEventListener("click",()=>{

    photoInput.click();

});
photoInput.addEventListener("change", async () => {

    const file = photoInput.files[0];
    if(!file) return;

    afficherImage(file);

    try {
        await uploadPhoto(file);
    } catch(error) {
        message.style.color = "#e53935";
        message.innerHTML = "⚠ Erreur lors de l'upload de la photo.";
    }

});

dropZone.addEventListener("dragover",(e)=>{

    e.preventDefault();

    dropZone.classList.add("dragover");

});

dropZone.addEventListener("dragleave",()=>{

    dropZone.classList.remove("dragover");

});

dropZone.addEventListener("drop", async (e) => {

    e.preventDefault();
    dropZone.classList.remove("dragover");

    const file = e.dataTransfer.files[0];
    if(!file) return;

    afficherImage(file);

    try {
        await uploadPhoto(file);
    } catch(error) {
        message.style.color = "#e53935";
        message.innerHTML = "⚠ Erreur lors de l'upload de la photo.";
    }

});

function verifierGenre() {

    if (genre.value === "MALE") {

        nomJeuneFilleField.style.display = "none";
        nomJeuneFille.value = "";

    } else {

        nomJeuneFilleField.style.display = "flex";

    }

}

genre.addEventListener("change", verifierGenre);


/* ==================================
      Récupération des valeurs
================================== */

function getValue(id){

    const element = document.getElementById(id);

    if(!element || element.value.trim() === "")
        return null;

    return element.value.trim();

}


/* ==================================
          Checkbox Boolean
================================== */

function getCheck(id){

    const element = document.getElementById(id);

    return element ? element.checked : false;

}


/* ==================================
        Remplissage des champs
================================== */

function setValue(id, value){

    const input = document.getElementById(id);

    if(input && value != null){

        input.value = value;

    }

}


/* ==================================
   🔹 NOUVEAU : Gestion Corps / Catégorie / Grade
================================== */

const API_URL = "http://localhost:8081";

const corpsSelect = document.getElementById("corps");
const categorieSelect = document.getElementById("categorie");
const gradeInput = document.getElementById("grade");

let categoriesDuCorps = []; // liste en mémoire pour retrouver le grade sélectionné

// Chargement de tous les corps au démarrage
async function chargerCorps(){

    if(!corpsSelect) return;

    try{

        const response = await fetch(`${API_URL}/corps/getAllCorps`);
        const corpsList = await response.json();

        corpsSelect.innerHTML = '<option value="">-- Sélectionner un corps --</option>';

        corpsList.forEach(corps => {

            const option = document.createElement("option");
            option.value = corps.id;
            option.textContent = corps.name;
            corpsSelect.appendChild(option);

        });

    }
    catch(error){

        console.error("Erreur lors du chargement des corps :", error);

    }

}

// Chargement des catégories liées à un corps donné
async function chargerCategoriesParCorps(corpsId, categorieIdSelectionnee = null){

    if(!categorieSelect) return;

    categorieSelect.innerHTML = '<option value="">-- Sélectionner --</option>';

    if(!corpsId){

        categorieSelect.disabled = true;
        gradeInput.value = "";
        categoriesDuCorps = [];
        return;

    }

    try{

        const response = await fetch(`${API_URL}/categories/corps/${corpsId}`);
        categoriesDuCorps = await response.json();

        categorieSelect.disabled = false;

        categoriesDuCorps.forEach(cat => {

            const option = document.createElement("option");
            option.value = cat.id;
            option.textContent = cat.num;
            categorieSelect.appendChild(option);

        });

        // Si on est en mode édition, on resélectionne la bonne catégorie
        if(categorieIdSelectionnee){

            categorieSelect.value = categorieIdSelectionnee;

            const categorieChoisie = categoriesDuCorps.find(
                cat => String(cat.id) === String(categorieIdSelectionnee)
            );

            gradeInput.value = categorieChoisie ? categorieChoisie.grade : "";

        }

    }
    catch(error){

        console.error("Erreur lors du chargement des catégories :", error);

    }

}

if(corpsSelect){

    corpsSelect.addEventListener("change", () => {

        chargerCategoriesParCorps(corpsSelect.value);

    });

}

if(categorieSelect){

    categorieSelect.addEventListener("change", () => {

        const categorieChoisie = categoriesDuCorps.find(
            cat => String(cat.id) === categorieSelect.value
        );

        gradeInput.value = categorieChoisie ? categorieChoisie.grade : "";

    });

}


/* ==================================
     Chargement (mode modification)
================================== */

async function chargerPersonnel(){

    if(!personnelId){

        return;

    }

    try{

        const response = await fetch(
            "http://localhost:8081/personnel/getPersonnelById/" + personnelId
        );

        if(!response.ok){

            throw new Error("Personnel introuvable");

        }

        const personnel = await response.json();

        await remplirFormulaire(personnel);

    }

    catch(error){

        message.style.color = "#e53935";

        message.innerHTML = "Impossible de charger le personnel.";

    }

}


async function remplirFormulaire(personnel){

    setValue("matricule", personnel.matricule);
    setValue("horaire", personnel.horaire);
    setValue("nom", personnel.nom);
    setValue("prenom", personnel.prenom);
    setValue("nomJeuneFille", personnel.nomJeuneFille);
    setValue("prenomPere", personnel.prenomPere);
    setValue("prenomGpere", personnel.prenomGpere);
    setValue("prenomMere", personnel.prenomMere);
    setValue("dateNaissance", personnel.dateNaissance);
    setValue("genre", personnel.genre);
    setValue("groupSanguin", personnel.groupSanguin);
    setValue("lieuNaissance", personnel.lieuNaissance);
    setValue("etatCivil", personnel.etatCivil);
    setValue("pays", personnel.pays);
    setValue("mail", personnel.mail);
    setValue("numTelephone1", personnel.numTelephone1);
    setValue("numTelephone2", personnel.numTelephone2);
    setValue("identite", personnel.identite);
    setValue("dateidentite", personnel.dateidentite);
    setValue("numidentite", personnel.numidentite);
    setValue("delivrance", personnel.delivrance);

    if(personnel.photo){

        photoFileName = personnel.photo;

        photoPreview.src =
            "http://localhost:8081/uploads/" + personnel.photo;

        photoPreview.style.display = "block";

        document.querySelector(".drop-text").style.display = "none";

    }

    // 🔹 NOUVEAU : Corps / Catégorie / Grade
    if(personnel.corps){

        setValue("corps", personnel.corps.id);

        await chargerCategoriesParCorps(
            personnel.corps.id,
            personnel.categorie ? personnel.categorie.id : null
        );

    }

    setValue("dateCategorie", personnel.dateCategorie);
    setValue("echelle", personnel.echelle);
    setValue("dateechelle", personnel.dateechelle);
    setValue("dateGrade", personnel.dateGrade);
    setValue("rang", personnel.rang);
    setValue("daterang", personnel.daterang);
    setValue("poste", personnel.poste);
    setValue("dateEmbauche", personnel.dateEmbauche);
    setValue("dateEntree", personnel.dateEntree);
    setValue("serviceMilitaire", personnel.serviceMilitaire);
    setValue("statut", personnel.statut);
    setValue("caisse", personnel.caisse);
    setValue("numcompte", personnel.numcompte);
    setValue("comptecourant", personnel.comptecourant);
    setValue("numaffiliation", personnel.numaffiliation);
    setValue("numaffcomp", personnel.numaffcomp);
    setValue("numaffconv", personnel.numaffconv);
    setValue("lieutravail", personnel.lieutravail);
    setValue("permis", personnel.permis);
    setValue("repartitionGeo", personnel.repartitionGeo);
    setValue("nationalite", personnel.nationalite);
    setValue("numfixe", personnel.numfixe);
    setValue("adresse", personnel.adresse);
    setValue("gouvernorat", personnel.gouvernorat);
    setValue("codePostal", personnel.codePostal);
    setValue("numAnneProlongement", personnel.numAnneProlongement);
    setValue("dateRetraite", personnel.dateRetraite);

    // Checkbox / radio
    const assuranceGroup = document.getElementById("assuranceGroup");
    if(assuranceGroup) assuranceGroup.checked = !!personnel.assuranceGroup;

    const assuranceMaladie = document.getElementById("assuranceMaladie");
    if(assuranceMaladie) assuranceMaladie.checked = !!personnel.assuranceMaladie;

    const affiliationMutuelle = document.getElementById("affiliationMutuelle");
    if(affiliationMutuelle) affiliationMutuelle.checked = !!personnel.affiliationMutuelle;

    const assuranceConjoint = document.getElementById("assuranceConjoint");
    if(assuranceConjoint) assuranceConjoint.checked = !!personnel.assuranceConjoint;

    const prevoyanceSociale = document.getElementById("prevoyanceSociale");
    if(prevoyanceSociale) prevoyanceSociale.checked = !!personnel.prevoyanceSociale;

    const radioMaitre = document.querySelector(
        `input[name="MaitreFamille"][value="${!!personnel.MaitreFamille}"]`
    );
    if(radioMaitre) radioMaitre.checked = true;

    const handicapeInput = document.getElementById("handicape");
    if(handicapeInput){

        handicapeInput.checked = !!personnel.handicape;

        if(handicapeInput.checked && natureHandicapField){

            natureHandicapField.style.display = "flex";

            setValue("naturehandicape", personnel.naturehandicape);

        }

    }

    // Titre / bouton dynamiques (si présents dans le HTML)
    const titre = document.getElementById("formTitle");
    if(titre) titre.innerHTML = "Modifier un personnel";

    const submitBtn = document.getElementById("submitBtn");
    if(submitBtn) submitBtn.innerHTML = "Enregistrer les modifications";

}


/* ==================================
     Gestion handicap dynamique
================================== */

const handicape =
    document.getElementById("handicape");

const natureHandicapField =
    document.getElementById("natureHandicapField");

const natureHandicap =
    document.getElementById("naturehandicape");


if(handicape){

    handicape.addEventListener("change", () => {

        if(handicape.checked){

            natureHandicapField.style.display = "flex";

        }

        else{

            natureHandicapField.style.display = "none";

            natureHandicap.value = "";

        }

    });

}


/* ==================================
   Vérification date embauche / entrée
================================== */

const dateEmbauche =
    document.getElementById("dateEmbauche");

const dateEntree =
    document.getElementById("dateEntree");

const dateError =
    document.getElementById("dateEmbaucheError");


function checkDateEmbauche(){

    if(
        dateEmbauche.value !== "" &&
        dateEntree.value !== ""
    ){

        const embauche =
            new Date(dateEmbauche.value);

        const entree =
            new Date(dateEntree.value);

        // date limite = embauche + 1 mois
        const limite = new Date(embauche);

        limite.setMonth(limite.getMonth() + 1);


        if(entree < embauche){

            dateError.style.color = "#e53935";

            dateError.innerHTML =
                "⚠ La date d'entrée doit être après la date d'embauche.";

            dateEntree.style.borderColor = "#e53935";

        }

        else if(entree > limite){

            dateError.style.color = "#e53935";

            dateError.innerHTML =
                "⚠ La différence entre la date d'embauche et la date d'entrée ne doit pas dépasser un mois.";

            dateEntree.style.borderColor = "#e53935";

        }

        else{

            dateError.innerHTML = "";

            dateEntree.style.borderColor = "#d9d9d9";

        }

    }

    else{

        dateError.innerHTML = "";

        dateEntree.style.borderColor = "#d9d9d9";

    }

}

if(dateEmbauche && dateEntree){

    dateEmbauche.addEventListener("input", checkDateEmbauche);
    dateEntree.addEventListener("input", checkDateEmbauche);

    dateEmbauche.addEventListener("change", checkDateEmbauche);
    dateEntree.addEventListener("change", checkDateEmbauche);

}


/* ==================================
   Vérification dates professionnelles
================================== */

const datesProfessionnelles = [

    { id:"dateCategorie", error:"dateCategorieError", nom:"La date catégorie" },
    { id:"dateechelle",   error:"dateEchelleError",   nom:"La date échelle" },
    { id:"dateGrade",     error:"dateGradeError",     nom:"La date grade" },
    { id:"daterang",      error:"dateRangError",      nom:"La date rang" }

];


function verifierDateProfessionnelle(champ){

    const input =
        document.getElementById(champ.id);

    const error =
        document.getElementById(champ.error);

    if(!input || !error || !dateEntree)
        return;


    if(
        dateEntree.value !== "" &&
        input.value !== ""
    ){

        const entree =
            new Date(dateEntree.value);

        const date =
            new Date(input.value);


        if(date < entree){

            error.style.color = "#e53935";

            error.innerHTML =
                `⚠ ${champ.nom} doit être supérieure ou égale à la date d'entrée.`;

            input.style.borderColor = "#e53935";

        }

        else{

            error.innerHTML = "";

            input.style.borderColor = "#d9d9d9";

        }

    }

    else{

        error.innerHTML = "";

        input.style.borderColor = "#d9d9d9";

    }

}


datesProfessionnelles.forEach(champ => {

    const input =
        document.getElementById(champ.id);

    if(input){

        input.addEventListener(
            "input",
            () => verifierDateProfessionnelle(champ)
        );

    }

});


if(dateEntree){

    dateEntree.addEventListener("input", () => {

        datesProfessionnelles.forEach(champ => {

            verifierDateProfessionnelle(champ);

        });

    });

}


/* ==================================
        Vérification Email live
================================== */

const emailInput =
    document.getElementById("mail");

const emailError =
    document.getElementById("mailError");


function checkEmail(){

    const emailRegex =
        /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if(
        emailInput.value !== "" &&
        !emailRegex.test(emailInput.value)
    ){

        emailError.style.color = "#e53935";

        emailError.innerHTML =
            "⚠ Adresse email invalide.";

        emailInput.style.borderColor = "#e53935";

    }

    else{

        emailError.innerHTML = "";

        emailInput.style.borderColor = "#d9d9d9";

    }

}

if(emailInput && emailError){

    emailInput.addEventListener("input", checkEmail);

}


/* ==================================
    Vérification téléphones live
================================== */

const telephone1 = document.getElementById("numTelephone1");

const telephone2 = document.getElementById("numTelephone2");

const telephoneError = document.getElementById("telephoneDupliqueError");


function checkTelephone(){

    if(
        telephone1.value !== "" &&
        telephone2.value !== "" &&
        telephone1.value === telephone2.value
    ){

        telephoneError.style.color = "#e53935";

        telephoneError.innerHTML =
            "⚠ Les deux numéros de téléphone doivent être différents.";

        telephone2.style.borderColor = "#e53935";

    }

    else{

        telephoneError.innerHTML = "";

        telephone2.style.borderColor = "#d9d9d9";

    }

}

if(telephone1 && telephone2 && telephoneError){

    telephone1.addEventListener("input", checkTelephone);
    telephone2.addEventListener("input", checkTelephone);

}


/* ==================================
    Vérification nombres 8 chiffres
================================== */

const champs8Chiffres = [

    { id:"numTelephone1", error:"telephone1Error", nom:"Le téléphone 1" },
    { id:"numTelephone2", error:"telephone2Error", nom:"Le téléphone 2" },
    { id:"numfixe",       error:"fixeError",        nom:"Le téléphone fixe" },
    { id:"numidentite",   error:"identiteError",    nom:"Le numéro d'identité" }

];


champs8Chiffres.forEach(champ => {

    const input =
        document.getElementById(champ.id);

    if(!input)
        return;

    const error =
        document.getElementById(champ.error);


    input.addEventListener("input", () => {

        input.value =
            input.value.replace(/\D/g, '');


        if(
            input.value !== "" &&
            input.value.length !== 8
        ){

            error.style.color = "#e53935";

            error.innerHTML =
                `⚠ ${champ.nom} doit contenir exactement 8 chiffres.`;

            input.style.borderColor = "#e53935";

        }

        else{

            error.innerHTML = "";

            input.style.borderColor = "#d9d9d9";

        }

    });

});


/* ==================================
             SUBMIT
================================== */

form.addEventListener("submit", async(e) => {

    e.preventDefault();

    message.innerHTML = "";


    const personnel = {

        // ==========================
        // Informations personnelles
        // ==========================
        photo: photoFileName,
        matricule: getValue("matricule"),
        horaire: getValue("horaire"),
        nom: getValue("nom"),
        prenom: getValue("prenom"),
        nomJeuneFille: getValue("nomJeuneFille"),
        prenomPere: getValue("prenomPere"),
        prenomGpere: getValue("prenomGpere"),
        prenomMere: getValue("prenomMere"),
        dateNaissance: getValue("dateNaissance"),
        genre: getValue("genre"),
        groupSanguin: getValue("groupSanguin"),
        lieuNaissance: getValue("lieuNaissance"),
        etatCivil: getValue("etatCivil"),

        MaitreFamille:
            document.querySelector('input[name="MaitreFamille"]:checked')
                ? document.querySelector('input[name="MaitreFamille"]:checked').value === "true"
                : false,


        // ==========================
        // Coordonnées
        // ==========================

        pays: getValue("pays"),
        mail: getValue("mail"),
        numTelephone1: getValue("numTelephone1"),
        numTelephone2: getValue("numTelephone2"),
        numfixe: getValue("numfixe"),
        adresse: getValue("adresse"),
        gouvernorat: getValue("gouvernorat"),
        codePostal: getValue("codePostal"),
        nationalite: getValue("nationalite"),
        repartitionGeo: getValue("repartitionGeo"),


        // ==========================
        // Identité
        // ==========================

        identite: getValue("identite"),
        dateidentite: getValue("dateidentite"),
        numidentite: getValue("numidentite"),
        delivrance: getValue("delivrance"),


        // ==========================
        // Assurances
        // ==========================

        assuranceGroup: getCheck("assuranceGroup"),
        assuranceMaladie: getCheck("assuranceMaladie"),
        affiliationMutuelle: getCheck("affiliationMutuelle"),
        assuranceConjoint: getCheck("assuranceConjoint"),
        prevoyanceSociale: getCheck("prevoyanceSociale"),


        // ==========================
        // Situation professionnelle
        // ==========================

        lieutravail: getValue("lieutravail"),

        // 🔹 NOUVEAU : envoi des objets corps / categorie (relations @ManyToOne)
        corps: corpsSelect && corpsSelect.value
            ? { id: corpsSelect.value }
            : null,

        categorie: categorieSelect && categorieSelect.value
            ? { id: categorieSelect.value }
            : null,

        dateCategorie: getValue("dateCategorie"),
        echelle: getValue("echelle"),
        dateechelle: getValue("dateechelle"),
        grade: getValue("grade"),
        dateGrade: getValue("dateGrade"),
        rang: getValue("rang"),
        daterang: getValue("daterang"),
        poste: getValue("poste"),
        dateEmbauche: getValue("dateEmbauche"),
        dateEntree: getValue("dateEntree"),
        serviceMilitaire: getValue("serviceMilitaire"),
        caisse: getValue("caisse"),
        permis: getValue("permis"),
        statut: getValue("statut"),


        // ==========================
        // Informations financières
        // ==========================

        numcompte: getValue("numcompte"),
        comptecourant: getValue("comptecourant"),
        numaffiliation: getValue("numaffiliation"),
        numaffcomp: getValue("numaffcomp"),
        numaffconv: getValue("numaffconv"),


        // ==========================
        // Handicap
        // ==========================

        handicape: getCheck("handicape"),

        naturehandicape:
            getCheck("handicape")
                ? getValue("naturehandicape")
                : null,


        // ==========================
        // Retraite
        // ==========================

        numAnneProlongement: getValue("numAnneProlongement"),
        dateRetraite: getValue("dateRetraite")

    };


    console.log(personnel);


    // URL et méthode selon le mode (ajout ou modification)
    const url = personnelId
        ? "http://localhost:8081/personnel/updatePersonnel/" + personnelId
        : "http://localhost:8081/personnel/addPersonnel";

    const method = personnelId ? "PATCH" : "POST";


    try{

        const response = await fetch(url, {

            method: method,

            headers: {

                "Content-Type": "application/json"

            },

            body: JSON.stringify(personnel)

        });


        if(response.ok){

            message.style.color = "#43a047";

            message.innerHTML =
                personnelId
                    ? '<i class="fa-solid fa-circle-check"></i> Personnel modifié avec succès.'
                    : '<i class="fa-solid fa-circle-check"></i> Personnel ajouté avec succès.';


            if(!personnelId){

                form.reset();

                if(natureHandicapField){

                    natureHandicapField.style.display = "none";

                }

                // 🔹 NOUVEAU : reset de la catégorie/grade après ajout
                if(categorieSelect){
                    categorieSelect.innerHTML = '<option value="">-- Sélectionner d\'abord un corps --</option>';
                    categorieSelect.disabled = true;
                }
                if(gradeInput){
                    gradeInput.value = "";
                }

            }

            setTimeout(() => {

                window.location.href = "dashboard.html";

            }, 300);

        }

        else{

            const error =
                await response.text();

            message.style.color = "#e53935";

            message.innerHTML =
                '<i class="fa-solid fa-circle-xmark"></i> '
                + error;

        }

    }

    catch(error){

        message.style.color = "#e53935";

        message.innerHTML =
            '<i class="fa-solid fa-triangle-exclamation"></i> Impossible de contacter le serveur.';

    }

});


/* ==================================
     Chargement initial (si édition)
================================== */

chargerCorps();       // 🔹 NOUVEAU : charge la liste des corps au démarrage
chargerPersonnel();
verifierGenre();