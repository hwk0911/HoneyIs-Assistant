var expenditure = {
    init: function () {
        var _this = this;
        $('#btn-expenditureSave').on('click', function () {
            _this.expenditureSave();
        });
        $('#expenditureTable tr').on('click', function () {
            _this.addUpdateForm($(this));
        });
        $(document).on('click', '#btn-expenditureUpdate', function () {
            _this.expenditureUpdate();
        });
        $(document).on('click', '#btn-expenditureDelete', function () {
            _this.expenditureDelete();
        });
    },

    expenditureSave: function () {
        var data = {
            email: $('#email').val(),
            date: $('#date').val(),
            price: $('#price').val(),
            location: $('#location').val(),
            history: $('#history').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/expenditure/save',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('신규 지출이 등록되었습니다.');
            window.location.href = '/expenditure';
        }).fail(function () {
            alert(JSON.stringify(error));
        });
    },

    addUpdateForm: function (tr) {
        alert("다음 데이터의 수정을 진행합니다." + tr.text());

        var td = tr.children();

        var id = td.eq(0).text();
        var date = td.eq(1).text();
        var price = td.eq(2).text();
        var location = td.eq(3).text();
        var history = td.eq(4).text();

        var str = "<table class='table table-hover table-dark'>";

        str += "<caption>지출 수정 데이터 입력</caption>"

        str += "<thead>";

        str += "<tr>";
        str += "<th width='15%'>DATE</th>" + 
        "<th width='15%'>PRICE</th>" + 
        "<th width='25%%'>LOCATION</th>" + 
        "<th width='35%'>HISTORY</th>" + 
        "<th width='5%'>수정</th>" + 
        "<th width='5%'>삭제</th>";
        
        str += "</tr>";
        str += "</thead>";

        str += "<tbody>";
        str += "<tr>";
        str += "<td style='display:none'>" + id + "<input type='text' style='display:none' id='updateId' value='" + id + "'>" + "</td>";
        str += "<td><input class='form-control mr-sm-2' type='date' id='updateDate' value='" + date + "'></td>";
        str += "<td><input class='form-control mr-sm-2' type='number' id='updatePrice' value='" + price + "'></td>";
        str += "<td><input class='form-control mr-sm-2' type='text' id='updateLocation' value='" + location + "'></td>";
        str += "<td><input class='form-control mr-sm-2' type='text' id='updateHistory' value='" + history + "'></td>";
        str += "<td>";
        str += "<button type='submit' id='btn-expenditureUpdate' class='btn btn-primary'>UPDATE</button>";
        str += "</td>";
        str += "<td>";
        str += "<button type='submit' id='btn-expenditureDelete' class='btn btn-warning'>DELETE</button>";
        str += "</td>";
        str += "</tr>";
        str += "</tbody>";
        str += "</table>";

        $('#updateForm').html(str);
    },

    expenditureUpdate: function () {
        var data = {
            id: $('#updateId').val(),
            date: $('#updateDate').val(),
            price: $('#updatePrice').val(),
            location: $('#updateLocation').val(),
            history: $('#updateHistory').val()
        };

        $.ajax({
            type: 'PUT',
            url: '/api/v1/expenditure/update',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('지출 수정이 완료되었습니다.');
            window.location.reload();
        }).fail(function () {
            alert(JSON.stringify(error));
        });
    },

    expenditureDelete: function () {
        var data = {
            id: $('#updateId').val(),
            date: $('#updateDate').val(),
            price: $('#updatePrice').val(),
            location: $('#updateLocation').val(),
            history: $('#updateHistory').val()
        };

        if (confirm("선택된 데이터: \n" + data.date + "\n" + data.price + "\n" + data.location + "\n" + data.history + "\n데이터 삭제 후 복구가 불가능합니다. 삭제하시겠습니까?")) {
            $.ajax({
                type: 'DELETE',
                url: '/api/v1/expenditure/delete/' + data.id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'
            }).done(function () {
                alert('삭제 완료되었습니다.');
                window.location.reload();
            }).fail(function () {
                alert(JSON.stringify(error));
            });
        }
    }
};

expenditure.init();
