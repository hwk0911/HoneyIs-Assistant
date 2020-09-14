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

        if (!data.name) {
            alert("CLIENT_NAME 항목이 누락되었습니다. 다시 입력해주세요.");
        }
        else {
            $.ajax({
                type: 'POST',
                url: '/api/v1/client/save',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('발주처가 등록되었습니다.');
                window.location.reload();
            }).fail(function () {
                alert(JSON.stringify(error));
            });
        }
    },

    addUpdateForm: function (tr) {

        var td = tr.children();

        var id = td.eq(0).text();
        var name = td.eq(1).text();
        var location = td.eq(2).text();
        var number = td.eq(3).text();

        if (id !== "ID") {
            alert("다음 데이터의 수정을 진행합니다." + tr.text());

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
        }
    },

    clientUpdate: function () {
        var data = {
            id: $('#updateId').val(),
            name: $('#updateName').val(),
            location: $('#updateLocation').val(),
            number: $('#updateNumber').val()
        };

        if (!data.name) {
            alert("CLIENT_NAME 항목이 누락되었습니다. 다시 입력해주세요.");
        }
        else {
            $.ajax({
                type: 'PUT',
                url: '/api/v1/client/update',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                beforeSend: function () {
                    var width = 0;
                    var height = 0;
                    var left = 0;
                    var top = 0;

                    width = 50;
                    height = 50;

                    top = ($(window).height() - height) / 2 + $(window).scrollTop();
                    left = ($(window).width() - width) / 2 + $(window).scrollLeft();

                    if ($("#loading").length != 0) {
                        $("#loading").css({
                            "top": top + "px",
                            "left": left + "px"
                        });
                        $("#loading").show();
                    }
                    else {
                        $('body').append('<div id="loading" style="position:absolute; top:' +
                            top + 'px; left:' +
                            left + 'px; width:' +
                            width + 'px; height:' +
                            height + 'px; z-index:9999; background:#f0f0f0; filter:alpha(opacity=50); opacity:alpha*0.5; margin:auto; padding:0; "><img src="/images/loading.gif" style="width:50px; height:50px;"></div>');
                    }

                }
            }).done(function () {
                alert('발주처 수정이 완료되었습니다.');
                window.location.reload();
            }).fail(function () {
                alert(JSON.stringify(error));
            });
        }
    },

    clientDelete: function () {
        var data = {
            id: $('#updateId').val(),
            name: $('#updateDate').val(),
            location: $('#updateLocation').val(),
            number: $('#updateNumber').val()
        };

        if (confirm("선택된 데이터: \n" + data.name + "\n" + data.location + "\n" + data.number + "\n" + "경고: 해당 발주처에 등록된 모든 상품은 발주처 미등록 상품으로 전환됩니다." + "\n" + "발주처 삭제 후 복구가 불가능합니다. \n삭제하시겠습니까?")) {
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
