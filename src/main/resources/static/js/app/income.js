var income = {
    init: function () {
        var _this = this;
        $('#btn-incomeSave').on('click', function () {
            _this.incomeSave();
        });
        $('#incomeTable tr').on('click', function () {
            _this.addUpdateForm($(this));
        });
        $(document).on('click','#btn-incomeUpdate', function () {
            _this.incomeUpdate();
        });
    },

    incomeSave: function () {
        var data = {
            email: $('#email').val(),
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
    },
    
    addUpdateForm: function (tr) {
        alert("다음 데이터의 수정을 진행합니다." + tr.text());

        var td = tr.children();

        var id = td.eq(0).text();
        var date = td.eq(1).text();
        var price = td.eq(2).text();
        var memo = td.eq(3).text();

        var str = "<table class='table table-hover table-dark'>";

        str += "<thead>";        
        str += "<tr>";
        str += "<th>매출 수정 데이터 입력</th>";
        str += "</tr>";    
        
        str += "<tr>";
        str += "<th width='25%'>DATE</th><th width='25%'>PRICE</th><th width='*'>MEMO</th>";
        str += "</tr>";             
        str += "</thead>";

        str += "<tbody>";
        str += "<tr>";
        str += "<td style='display:none'>" + id + "<input type='text' style='display:none' id='updateId' value='" + id + "'>" + "</td>";
        str += "<td><input type='date' id='updateDate' width='100%' value='" + date +"'></td>";
        str += "<td><input type='number' id='updatePrice' width='100%' value'" + price +"></td>";
        str += "<td><input type='text' id='updateMemo' width='200%' value'" + memo +"></td>";
        str += "<td>";
        str += "<button type='submit' id='btn-incomeUpdate' class='btn btn-primary'>UPDATE</button>";
        str += "</td>";
        str += "</tr>";
        str += "</tbody>";
        str += "</table>";

        $('#updateForm').html(str);
    },

    incomeUpdate: function() {
        var data = {
            id: $('#updateId').val(),
            date: $('#updateDate').val(),
            price: $('#updatePrice').val(),
            memo: $('#updateMemo').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/updateincome',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('매출 수정이 완료되었습니다.');
            window.location.reload();
        }).fail(function () {
            alert(JSON.stringify(error));
        });
    }
};

income.init();
