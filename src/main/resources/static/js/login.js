const form = document.getElementById("loginForm");

const message = document.getElementById("message");


form.addEventListener("submit", async function (e) {

    e.preventDefault();


    const matriculeAdmin =
        Number(document.getElementById("matriculeAdmin").value);


    const password =
        document.getElementById("password").value;



    // Nettoyer ancien message

    message.innerHTML = "";



    try {


        const response = await fetch(
            "http://localhost:8081/admin/login",
            {

                method:"POST",

                headers:{

                    "Content-Type":"application/json"

                },


                body:JSON.stringify({

                    matriculeAdmin:matriculeAdmin,

                    password:password

                })

            }
        );



        const result = await response.text();



        // ==========================
        //      Connexion réussie
        // ==========================


        if(response.ok){


            message.style.color = "#43a047";


            message.innerHTML =
            '<i class="fa-solid fa-circle-check"></i> ' 
            + result;



            setTimeout(()=>{


                window.location.href="dashboard.html";


            },1500);



        }



        // ==========================
        //      Erreur login
        // ==========================


        else{


            message.style.color = "#e53935";


            message.innerHTML =
            '<i class="fa-solid fa-circle-xmark"></i> '
            + result;


        }




    }



    catch(error){



        message.style.color="#e53935";


        message.innerHTML =
        '<i class="fa-solid fa-triangle-exclamation"></i> Impossible de contacter le serveur.';



    }



});