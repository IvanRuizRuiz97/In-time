let createIncidencia = function(){

    let form = document.forms["formulario"];
const info = {
    type: 'crearIncidencia',
    userName: sessionStorage.userName,
    tipo: form["tipo"].value,
    body: form["body"].value,
    token: sessionStorage.token,
};

const data = {
    data: JSON.stringify(info),
    url: 'crearIncidencia',
    type: 'post',
    contentType: 'application/json',
    success: function(response) {
        form.reset();
        alert("Incidencia Creada");
    },
    error: function(response) {
        sessionStorage.clear();
        window.location.href = 'index.html';
    }
};
$.ajax(data);
} 

	