var stock = {
    init: function () {
        var _this = this;
        $(document).on('click', '#btn-stockSave', function () {
            _this.stockSave();
        });
        $('#stockTable tr').on('click', function () {
            _this.addSaveForm($(this));
        });
        $(document).on('click', '#stockListTable tr', function () {
            _this.addStockUpdateForm($(this));
        });
        $(document).on('click', '#btn-stockUpdate', function () {
            _this.stockUpdate();
        });
        $(document).on('click', '#btn-stockDelete', function () {
            _this.stockDelete();
        });
    },

    stockSave: function () {
        var data = {
            clientId: $('#clientId').val(),
            name: $('#name').val(),
            color: $('#color').val(),
            size: $('#size').val(),
            amount: (0)
        };

        if (!data.size) {
            data.size = "FREE";
        }

        alert(data.clientId + " " + data.name + " " + data.color + " " + data.size);

        $.ajax({
            type: 'POST',
            url: '/api/v1/stock/newest',
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
            alert('신규 재고 등록이 완료되었습니다.');
            window.location.reload();
        }).fail(function () {
            alert(JSON.stringify(error));
        });
    },

    addSaveForm: function (tr) {
        var td = tr.children();

        var id = td.eq(0).text();
        var clientName = td.eq(1).text();

        alert(clientName + " 발주처 재고를 추가합니다.");

        var str = "<table class='table table-hover table-dark'>";

        str += "<caption>신상 데이터 입력</caption>"

        str += "<thead>";

        str += "<tr>";
        str +=
            "<th width='15%'>CLIENT NAME</th>" +
            "<th width='40%'>NAME</th>" +
            "<th width='*'>COLOR</th>" +
            "<th width='*'>SIZE</th>" +
            "<th width='5%'>SAVE</th>";

        str += "</tr>";
        str += "</thead>";

        str += "<tbody>";
        str += "<tr>";
        str += "<td style='display:none'>" + id + "<input type='text' style='display:none' id='clientId' value='" + id + "'>" + "</td>";
        str += "<td style='display:none'><input type='text' style='display:none' id='email' value='$('#email')'>" + "</td>";
        str += "<td><input class='form-control mr-sm-2' type='text' value='" + clientName + "' disabled></td>";
        str += "<td><input class='form-control mr-sm-2' type='text' id='name' placeholder='쇼핑몰에 등록한 이름으로 등록해주시기 바랍니다.'></td>";
        str += "<td><input class='form-control mr-sm-2' type='text' id='color' placeholder='ex) 빨강,주황,노랑,초록...'></td>";
        str += "<td><input class='form-control mr-sm-2' type='text' id='size' placeholder='ex) 95,100,105...'></td>";
        str += "<td>";
        str += "<button type='submit' id='btn-stockSave' class='btn btn-primary'>SAVE</button>";
        str += "</td>";
        str += "</tr>";
        str += "</tbody>";
        str += "</table>";

        $('#newStock').html(str);
    },

    stockUpdate: function () {
        var data = {
            id: $('#productId').val(),
            productName: $('#name').val(),
            color: $('#color').val(),
            size: $('#size').val(),
            amount: $('#amount').val()
        };

        if (!data.size) {
            data.size = "FREE";
        }

        $.ajax({
            type: 'PUT',
            url: '/api/v1/stock/update',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('재고 수정이 완료되었습니다.');
            window.location.reload();
        }).fail(function () {
            alert(JSON.stringify(error));
        });
    },

    stockDelete: function () {
        var data = {
            id: $('#productId').val(),
            name: $('#productName').val(),
            color: $('#color').val(),
            size: $('#size').val()
        };

        if (confirm("선택된 데이터: \n" + data.name + "\n" + data.color + "\n" + data.size + "\n데이터 삭제 후 복구가 불가능합니다. 삭제하시겠습니까?")) {
            $.ajax({
                type: 'DELETE',
                url: '/api/v1/stock/delete/' + data.id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'
            }).done(function () {
                alert('삭제 완료되었습니다.');
                window.location.reload();
            }).fail(function () {
                alert(JSON.stringify(error));
            });
        }
    },

    addStockUpdateForm: function (tr) {
        var td = tr.children();

        var clientName = td.eq(0).text();
        var productName = td.eq(1).text();
        var color = td.eq(2).text();
        var size = td.eq(3).text();
        var amount = td.eq(4).text();
        var productId = td.eq(5).text();

        if (clientName !== "CLIENT") {
            alert("[" + productName + "]" + " 제품의 데이터를 수정합니다.");

            var str = "<table class='table table-hover table-dark'>";

            str += "<caption>재고 수정 데이터 입력</caption>"

            str += "<thead>";

            str += "<tr>";
            str +=
                "<th width='20%'>CLIENT NAME</th>" +
                "<th width='*'>PRODUCT NAME</th>" +
                "<th width='10%'>COLOR</th>" +
                "<th width='10%'>SIZE</th>" +
                "<th width='10%'>AMOUNT</th>" +
                "<th width='5%'>SAVE</th>" +
                "<th width='5%'>DELETE</th>";

            str += "</tr>";
            str += "</thead>";

            str += "<tbody>";
            str += "<tr>";
            str += "<td style='display:none'><input type='text' style='display:none' id='productId' value='" + productId + "'></td>";
            str += "<td>" + clientName + "</td>";
            str += "<td><input class='form-control mr-sm-2' type='text' id='name' value='" + productName + "'></td>";
            str += "<td><input class='form-control mr-sm-2' type='text' id='color' value='" + color + "'></td>";
            str += "<td><input class='form-control mr-sm-2' type='text' id='size' value='" + size + "'></td>";
            str += "<td><input class='form-control mr-sm-2' type='text' id='amount' value='" + amount + "'></td>";
            str += "<td>";
            str += "<button type='submit' id='btn-stockUpdate' class='btn btn-primary'>UPDATE</button>";
            str += "</td>";
            str += "<td>";
            str += "<button type='submit' id='btn-stockDelete' class='btn btn-warning'>DELETE</button>";
            str += "</td>";
            str += "</tr>";
            str += "</tbody>";
            str += "</table>";

            $('#stockUpdate').html(str);
        }
    }
};

stock.init();
