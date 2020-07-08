var monthButton = {
    init: function () {
        var _this = this;
        $('#btn-term').on('click', function () {
            _this.term();
        });
    },

    term: function() {
        var date = {
            start: $('#startDate').val(),
            end: $('#endDate').val()
        }

        if(!date.start || !date.end) {
            alert('Term Error');
        }
        else if(date.start <= date.end) {
            $.ajax({
                type: 'POST',
                url: '/api/v1/termincome',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(date)
            }).done(function () {
                alert(121212);
                window.location.href = '/';
            }).fail(function () {
                alert('error');
            });
        }
        else {
            alert('Term Error');
        }        
    }
}

monthButton.init();