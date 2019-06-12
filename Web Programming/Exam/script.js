$(document).ready( function () {
    $('#1stLevelFriends').DataTable({
    });
    $('#2ndLevelFriends').DataTable({
    });

    load1stLevelFriends();
    load2ndLevelFriends();

    $('#2ndLevelFriends tbody').on('click', 'tr', function () {
        let table = $('#2ndLevelFriends').DataTable();
        let data = table.row(this).data();
        $('#textBox').val(data[0]);
    } );

    $('#1stLevelFriends tbody').on('click', 'tr', function () {
        let table = $('#1stLevelFriends').DataTable();
        let data = table.row(this).data();
        $('#textBox').val(data[0]);
    } );

    $('#makeButton').click(function () {
        if ($('#textBox').val() === '')
            alert("Text box empty");
        else
            makeFriends($('#textBox').val());
    });

    $('#removeButton').click(function () {
        if ($('#textBox').val() === '')
            alert("Text box empty");
        else
            removeFriends($('#textBox').val());
    });
} );

function load1stLevelFriends() {
    let table = $('#1stLevelFriends').DataTable();
    $.getJSON('/Template/Friends/getFriends.php?level=1', function( data ) {
        console.log(data);
        table.clear();
        table.rows.add(data);
        table.draw();
    });
}
function load2ndLevelFriends() {
    let table = $('#2ndLevelFriends').DataTable();
    $.getJSON('/Template/Friends/getFriends.php?level=2', function( data ) {
        console.log(data);
        table.clear();
        table.rows.add(data);
        table.draw();
    });
}

function makeFriends(friend) {
    console.log(friend);
    $.post('/Template/Friends/makeFriends.php?friend=' + friend, function () {
        load1stLevelFriends();
        load2ndLevelFriends();
    })
}

function removeFriends(friend) {
    console.log(friend);
    $.post('/Template/Friends/removeFriends.php?friend=' + friend, function () {
        load1stLevelFriends();
        load2ndLevelFriends();
    })
}