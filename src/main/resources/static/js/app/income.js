var income = {
    init: function () {
        var _this = this;
        $('#btn-incomeSave').on('click', function () {
            _this.incomeSave();
        });
        $('#incomeTable tr').on('click', function () {
            _this.addUpdateForm($(this));
        });
        $(document).on('click', '#btn-incomeUpdate', function () {
            _this.incomeUpdate();
        });
        $(document).on('click', '#btn-incomeDelete', function () {
            _this.incomeDelete();
        });
    },

    incomeSave: function () {
        var data = {
            email: $('#email').val(),
            date: $('#date').val(),
            price: $('#price').val(),
            memo: $('#memo').val()
        };
        if (!data.price) {
            alert("PRICE 항목이 누락되었습니다. 다시 입력해주세요.");
        }
        else {
            $.ajax({
                type: 'POST',
                url: '/api/v1/income/save',
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
    },

    addUpdateForm: function (tr) {
        var td = tr.children();

        var id = td.eq(0).text();
        var date = td.eq(1).text();
        var price = td.eq(2).text();
        var memo = td.eq(3).text();

        if (id !== "ID") {
            alert("다음 데이터의 수정을 진행합니다." + tr.text());

            var str = "<table class='table table-hover table-dark'>";

            str += "<caption>매출 수정 데이터 입력</caption>"

            str += "<thead>";

            str += "<tr>";
            str += "<th width='15%'>DATE</th>" +
                "<th width='15%'>PRICE</th>" +
                "<th width='*'>MEMO</th>" +
                "<th width='5%'>수정</th>" +
                "<th width='5%'>삭제</th>";

            str += "</tr>";
            str += "</thead>";

            str += "<tbody>";
            str += "<tr>";
            str += "<td style='display:none'>" + id + "<input type='text' style='display:none' id='updateId' value='" + id + "'>" + "</td>";
            str += "<td><input class='form-control mr-sm-2' type='date' id='updateDate' value='" + date + "'></td>";
            str += "<td><input class='form-control mr-sm-2' type='number' id='updatePrice' value='" + price + "'></td>";
            str += "<td><input class='form-control mr-sm-2' type='text' id='updateMemo' value='" + memo + "'></td>";
            str += "<td>";
            str += "<button type='submit' id='btn-incomeUpdate' class='btn btn-primary'>UPDATE</button>";
            str += "</td>";
            str += "<td>";
            str += "<button type='submit' id='btn-incomeDelete' class='btn btn-warning'>DELETE</button>";
            str += "</td>";
            str += "</tr>";
            str += "</tbody>";
            str += "</table>";

            $('#updateForm').html(str);
        }
    },

    incomeUpdate: function () {
        var data = {
            id: $('#updateId').val(),
            date: $('#updateDate').val(),
            price: $('#updatePrice').val(),
            memo: $('#updateMemo').val()
        };

        if (!data.price) {
            alert("PRICE 항목이 누락되었습니다. 다시 입력해주세요.");
        }
        else {
            $.ajax({
                type: 'PUT',
                url: '/api/v1/income/update',
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
    },

    incomeDelete: function () {
        var data = {
            id: $('#updateId').val(),
            date: $('#updateDate').val(),
            price: $('#updatePrice').val(),
            memo: $('#updateMemo').val()
        };

        if (confirm("선택된 데이터: \n" + data.date + "\n" + data.price + "\n" + data.memo + "\n데이터 삭제 후 복구가 불가능합니다. 삭제하시겠습니까?")) {
            $.ajax({
                type: 'DELETE',
                url: '/api/v1/income/delete/' + data.id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
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
                alert('삭제 완료되었습니다.');
                window.location.reload();
            }).fail(function () {
                alert(JSON.stringify(error));
            });
        }
    }
};

income.init();
