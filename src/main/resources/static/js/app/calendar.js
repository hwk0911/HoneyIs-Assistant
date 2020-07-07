var calendar = {
    init: function() {
        var _this = this;

        var date = new Date();

        $('#btn-income').on('click', function () {
            window.location.href = '/income/' + date.getFullYear() + '/' + (date.getMonth() + 1);
        });

        $('#btn-expenditure').on('click', function () {
            window.location.href = '/expenditure/' + date.getFullYear() + '/' + (date.getMonth() + 1);
        });
    }
};

calendar.init();