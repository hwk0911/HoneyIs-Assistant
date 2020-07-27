var client = {
    init: function () {
        var _this = this;
        $('#btn-clientSave').on('click', function () {
            _this.clientSave();
        });
        $('#clientTable tr').on('click', function () {
            _this.addUpdateForm($(this));
        });
        $(document).on('click', '#btn-clientUpdate', function () {
            _this.clientUpdate();
        });
        $(document).on('click', '#btn-clientDelete', function () {
            _this.clientDelete();
        });
    },

    clientSave: function () {
        var data = {
            email: $('#email').val(),
            location: $('#location').val(),
            name: $('#name').val(),
            number: $('#number').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/client/save',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('신규 지출이 등록되었습니다.');
            window.location.reload();
        }).fail(function () {
            alert(JSON.stringify(error));
        });
    },

    addUpdateForm: function (tr) {
        alert("다음 데이터의 수정을 진행합니다." + tr.text());

        var td = tr.children();

        var id = td.eq(0).text();
        var name = td.eq(1).text();
        var location = td.eq(2).text();
        var number = td.eq(3).text();

        var str = "<table class='table table-hover table-dark'>";

        str += "<caption>발주처 수정 데이터 입력</caption>"

        str += "<thead>";

        str += "<tr>";
        str += "<th width='20%'>NAME</th>" + 
        "<th width='35%'>LOCATION</th>" + 
        "<th width='35%'>NUMBER</th>" + 
        "<th width='5%'>수정</th>" + 
        "<th width='5%'>삭제</th>";
        
        str += "</tr>";
        str += "</thead>";

        str += "<tbody>";
        str += "<tr>";
        str += "<td style='display:none'>" + id + "<input type='text' style='display:none' id='updateId' value='" + id + "'>" + "</td>";
        str += "<td><input class='form-control mr-sm-2' type='text' id='updateName' value='" + name + "'></td>";
        str += "<td><input class='form-control mr-sm-2' type='text' id='updateLocation' value='" + location + "'></td>";
        str += "<td><input class='form-control mr-sm-2' type='text' id='updateNumber' value='" + number + "'></td>";
        str += "<td>";
        str += "<button type='submit' id='btn-clientUpdate' class='btn btn-primary'>UPDATE</button>";
        str += "</td>";
        str += "<td>";
        str += "<button type='submit' id='btn-clientDelete' class='btn btn-warning'>DELETE</button>";
        str += "</td>";
        str += "</tr>";
        str += "</tbody>";
        str += "</table>";

        $('#updateForm').html(str);
    },

    clientUpdate: function () {
        var data = {
            id: $('#updateId').val(),
            name: $('#updateName').val(),
            location: $('#updateLocation').val(),
            number: $('#updateNumber').val()
        };

        $.ajax({
            type: 'PUT',
            url: '/api/v1/client/update',
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

    clientDelete: function () {
        var data = {
            id: $('#updateId').val(),
            name: $('#updateDate').val(),
            location: $('#updateLocation').val(),
            number: $('#updateNumber').val()
        };

        if (confirm("선택된 데이터: \n" + data.name + "\n" + data.location + "\n" + data.number + "\n데이터 삭제 후 복구가 불가능합니다. 삭제하시겠습니까?")) {
            $.ajax({
                type: 'DELETE',
                url: '/api/v1/client/delete/' + data.id,
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

client.init();
