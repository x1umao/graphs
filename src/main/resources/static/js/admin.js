let step = document.querySelector('#step');

function logout() {
    $.get("/logout", data => {
        window.location.href = "/login";
    })
}

function download() {
    const download = document.querySelector('#download');
    download.addEventListener('click', function () {
        window.location.href = '/download';
        download.style.display = 'none';
        document.querySelector('#upload-file-form').style.display = 'block';
        step.children[0].removeAttribute('style');
        step.children[1].setAttribute('style', 'font-weight:bold');
    })
}

function upload() {
    const file = document.getElementById('file');
    const fileChosen = document.getElementById('file-chosen');
    file.addEventListener('change', function () {
        document.querySelector('#upload-file-form').style.display = 'none';
        document.querySelector('#spinner').setAttribute('style', 'display:block');
        $.ajax({
            url: "/upload",
            type: "POST",
            data: new FormData($("#upload-file-form")[0]),
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            success: function (data) {
                // Handle upload success
                if (data !== 'success') {
                    $("#upload-file-message").text(data).show();
                } else {
                    document.querySelector('#spinner').setAttribute('style', 'display:none');
                    document.querySelector('#final').style.display = 'block';
                    fileChosen.textContent = file.files[0].name + ' has been uploaded';
                    step.children[1].removeAttribute('style');
                    step.children[2].setAttribute('style', 'font-weight:bold');
                    $("#upload-file-message").hide();
                }
            },
            error: function () {
                // Handle upload error
                $("#upload-file-message").text(
                    "File not uploaded (perhaps it's too much big)").show();
            }
        });
    })
}

function update() {
    $.get("/update", d => {
        window.location.href = "/admin-dash";
    });
}

function reUpload() {
    window.location.href = "/admin-dash";
}

function login() {
    let password = $("#password").val();
    let username = $("#username").val();
    $.get(`/key?username=${username}`, key => {
        const hash = CryptoJS.HmacSHA256(password, username + key);
        password = CryptoJS.enc.Base64.stringify(hash);
        $.ajax({
            url: "/doLogin",
            type: "POST",
            data: {
                username: username,
                password: password
            },
            success: function (data) {
                if (data.msg) {
                    window.location.href = "/admin-dash";
                } else {
                    $("#errorMsg").show();
                }
            },
            error: function () {
                alert("network errors!");
            }
        });
    })
}

download();
upload();