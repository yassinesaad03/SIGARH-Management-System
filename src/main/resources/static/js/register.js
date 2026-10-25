const form = document.getElementById("registerForm");


const nom = document.getElementById("nom");
const prenom = document.getElementById("prenom");
const matriculeAdmin = document.getElementById("matriculeAdmin");
const email = document.getElementById("email");

const password = document.getElementById("password");
const confirmPassword = document.getElementById("confirmPassword");


const message = document.getElementById("message");


const togglePassword =
    document.getElementById("togglePassword");

const toggleConfirmPassword =
    document.getElementById("toggleConfirmPassword");


const strengthBar =
    document.getElementById("strengthBar");

const strengthText =
    document.getElementById("strengthText");


const passwordMatch =
    document.getElementById("passwordMatch");





/*==================================
        Afficher mot de passe
==================================*/


togglePassword.addEventListener("click",()=>{


    if(password.type === "password"){


        password.type="text";


        togglePassword.innerHTML =
        '<i class="fa-solid fa-eye-slash"></i>';


    }

    else{


        password.type="password";


        togglePassword.innerHTML =
        '<i class="fa-solid fa-eye"></i>';


    }


});







/*==================================
    Afficher confirmation password
==================================*/


toggleConfirmPassword.addEventListener("click",()=>{


    if(confirmPassword.type === "password"){


        confirmPassword.type="text";


        toggleConfirmPassword.innerHTML =
        '<i class="fa-solid fa-eye-slash"></i>';


    }

    else{


        confirmPassword.type="password";


        toggleConfirmPassword.innerHTML =
        '<i class="fa-solid fa-eye"></i>';


    }


});








/*==================================
      Force mot de passe
==================================*/


password.addEventListener("input",()=>{


    let score=0;



    if(password.value.length >= 8)
        score++;



    if(/[A-Z]/.test(password.value))
        score++;



    if(/[a-z]/.test(password.value))
        score++;



    if(/[0-9]/.test(password.value))
        score++;




    switch(score){


        case 0:
        case 1:


            strengthBar.style.width="25%";

            strengthBar.style.background="#e53935";

            strengthText.innerHTML="Faible";


        break;




        case 2:


            strengthBar.style.width="50%";

            strengthBar.style.background="#fb8c00";

            strengthText.innerHTML="Moyen";


        break;




        case 3:


            strengthBar.style.width="75%";

            strengthBar.style.background="#fdd835";

            strengthText.innerHTML="Bon";


        break;




        case 4:


            strengthBar.style.width="100%";

            strengthBar.style.background="#43a047";

            strengthText.innerHTML="Fort";


        break;


    }



});









/*==================================
    Vérification live confirmation
==================================*/


function checkPasswordMatch(){


    if(confirmPassword.value === ""){


        passwordMatch.innerHTML="";


        confirmPassword.style.borderColor="";


        return;


    }





    if(password.value === confirmPassword.value){



        passwordMatch.innerHTML =

        '<i class="fa-solid fa-circle-check"></i> Les mots de passe correspondent';



        passwordMatch.style.color="#43a047";


        confirmPassword.style.borderColor="#43a047";


    }



    else{


        passwordMatch.innerHTML =

        '<i class="fa-solid fa-circle-xmark"></i> Les mots de passe ne correspondent pas';



        passwordMatch.style.color="#e53935";


        confirmPassword.style.borderColor="#e53935";


    }



}




confirmPassword.addEventListener(
    "input",
    checkPasswordMatch
);



password.addEventListener(
    "input",
    ()=>{

        if(confirmPassword.value !== "")
            checkPasswordMatch();

    }
);








/*==================================
          Gestion erreurs
==================================*/


function setError(id,text){


    document.getElementById(id).innerText=text;


}




function clearErrors(){


    setError("nomError","");

    setError("prenomError","");

    setError("matriculeAdminError","");

    setError("emailError","");

    setError("passwordError","");

    setError("confirmPasswordError","");


}








/*==================================
          Validation
==================================*/


function validate(){


    clearErrors();


    let valid=true;




    if(nom.value.trim()===""){


        setError(
            "nomError",
            "Le nom est obligatoire"
        );


        valid=false;


    }





    if(prenom.value.trim()===""){


        setError(
            "prenomError",
            "Le prénom est obligatoire"
        );


        valid=false;


    }






    if(matriculeAdmin.value.trim() === ""){

        setError(
            "matriculeAdminError",
            "Le matricule  est obligatoire"
        );

        valid = false;

    }







    const emailRegex =
    /^[^\s@]+@[^\s@]+\.[^\s@]+$/;




    if(!emailRegex.test(email.value)){


        setError(
            "emailError",
            "Email invalide"
        );


        valid=false;


    }







    const passwordRegex =
    /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}$/;





    if(!passwordRegex.test(password.value)){


        setError(
            "passwordError",
            "Minimum 8 caractères, une majuscule, une minuscule et un chiffre"
        );


        valid=false;


    }







    if(confirmPassword.value===""){


        setError(
            "confirmPasswordError",
            "Veuillez confirmer votre mot de passe"
        );


        valid=false;


    }



    else if(password.value !== confirmPassword.value){


        setError(
            "confirmPasswordError",
            "Les mots de passe ne correspondent pas"
        );


        valid=false;


    }





    return valid;


}








/*==================================
        Création compte
==================================*/


form.addEventListener("submit",async(e)=>{


    e.preventDefault();



    message.innerHTML="";





    if(!validate())
        return;






    const admin={


        nom:nom.value.trim(),

        prenom:prenom.value.trim(),

        matriculeAdmin: Number(matriculeAdmin.value),

        email:email.value.trim(),

        password:password.value


    };






    try{



        const response =
        await fetch(
            "http://localhost:8081/admin/register",
            {


                method:"POST",


                headers:{


                    "Content-Type":"application/json"


                },


                body:JSON.stringify(admin)


            }
        );






        const result =
        await response.text();







        if(response.ok){



            message.style.color="green";


            message.innerHTML=result;



            form.reset();



            strengthBar.style.width="0%";


            strengthText.innerHTML="";


            passwordMatch.innerHTML="";






            setTimeout(()=>{


                window.location.href="login.html";


            },2000);



        }



        else{



            message.style.color="red";


            message.innerHTML=result;


        }






    }



    catch(error){



        message.style.color="red";


        message.innerHTML =
        "Impossible de contacter le serveur.";


    }



});