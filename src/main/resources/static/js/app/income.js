var list = {
    init: function () {
        var _this = this;
        $('#btn-incomeSave').on('click', function () {
            _this.incomeSave();
        });
    },

    incomeSave: function () {
        var data = {
            email: 'hwk0911@gmail.com',
            date: $('#date').val(),
            price: $('#price').val(),
            memo: $('#memo').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/saveincome',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('신규 매출이 등록되었습니다.');
            window.location.href = '/income';
        }).fail(function () {
            alert(JSON.stringify(error));
        });
    }
};

list.init();
