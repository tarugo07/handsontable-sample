$(function () {
    var data = [
        {id: 1, name: {first: 'Ted', last: 'Right'}},
        {id: 2, name: {first: 'Frank', last: 'Honest'}},
        {id: 3, name: {first: 'Joan', last: 'Well'}},
        {id: 4, name: {first: 'Gail', last: 'Polite'}},
        {id: 5, name: {first: 'Michael', last: 'Fair'}}
    ];

    var container = document.getElementById('example');
    var hot = new Handsontable(container, {
        data: data,
        rowHeaders: true,
        colHeaders: ['ID', 'FIRST NAME', 'LAST NAME'],
        minSpareRows: 1,
        startRows: 5,
        startCols: 3,
        columns: [
            {data: 'id'},
            {data: 'name.first'},
            {data: 'name.last'}
        ]
    });

    $('#save').click(function () {
        var request = [];
        var users = hot.getData();
        for (var i in users) if (users.hasOwnProperty(i)) {
            if (users[i].indexOf(null) != -1) continue;
            request.push({"id": users[i][0], "name": {"first": users[i][1], "last": users[i][2]}})
        }

        $.ajax({
            url: 'users',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(request)
        }).done(function (response) {
            console.log(JSON.stringify(response));
        });
    });

});
