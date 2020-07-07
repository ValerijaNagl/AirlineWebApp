function login(e) {
    if(e.preventDefault()) e.preventDefault();
    let formData = new FormData(e.target);

    let username = $('#username').val();
    let password = $('#password').val();

    $.ajax({
        url: "/rest/users/login",
        type:"GET",
        data: {
            username: username,
            password: password
        },
        contentType:"application/json",
        dataType:"json",
        success: function(data){
            window.localStorage.setItem('jwt', data.JWTToken);
            window.localStorage.setItem('id',data.id);
            window.localStorage.setItem('username',data.username);
            window.localStorage.setItem('type',data.typeOfUser);
            window.localStorage.setItem('password',data.password);
            // redirekcija
            window.location.replace("/index.jsp");
        },
        error: function(data){
            alert("Must be a valid user!");
        }
    });

    return false;
}
var form = document.getElementById('login-form');
form.addEventListener("submit", login);