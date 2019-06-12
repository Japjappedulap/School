$(document).ready( function () {
    $('#dataTable').DataTable({
    });
} );


function onPopular() {
    $.getJSON('/Exam/popular.php', function (data) {
        $('#popularIDGoesHere').text(data['IDObject']);
        $('#popularNameGoesHere').text(data['name']);
        $('#popularCountGoesHere').text(data['Count']);
    });
}

function request() {
    let textBox = $("#textBox");
    let info = $('#info');

    if (textBox.val() === '') {
        info.text('must enter a name');
        return;
    }

    $.get('/Exam/ask.php?name=' + textBox.val(), function (data) {
        info.text(data);

    })

}