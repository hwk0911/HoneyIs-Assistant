var undefinedStock = {
    init: function () {
        var _this = this;
        $('#undefinedTable tr').on('click', function () {
            _this.addSaveForm($(this));
        });
        $(document).on('click', '#btn-undefinedStockSearchClient', function () {
            _this.undefinedStockSearchClient();
        });
        $(document).on('click', '#btn-undefinedStockUpdate', function () {
            _this.undefinedStockUpdate();
        });
        $(document).on('click', '#btn-clientSubmit', function () {
            _this.getClientName();
        });
    },

    addSaveForm: function (tr) {
        var td = tr.children();

        var productName = td.eq(0).text();

        alert("[" + productName + "]" + " 제품의 발주처를 추가합니다.");

        var str = "<table class='table table-hover table-dark'>";

        str += "<caption>재고 수정 데이터 입력</caption>"

        str += "<thead>";

        str += "<tr>";
        str +=
            "<th width='*'>CLIENT NAME</th>" +
            "<th width='55%'>PRODUCT NAME</th>" +
            "<th width='5%'>SAVE</th>" +
            "<th width='5%'>DELETE</th>";

        str += "</tr>";
        str += "</thead>";

        str += "<tbody>";
        str += "<tr>";
        str += "<td>" +
            "<form class='form-inline md-form mr-auto mb-4'>" +
            "<input class='form-control mr-sm-2' type='text' placeholder='검색을 통해 입력' aria-label='Search' id='clientName' readonly>" +
            "<button class='btn aqua-gradient btn-rounded btn-sm my-0' type='button' id='btn-undefinedStockSearchClient'>" +
            "<img src='/images/search.png'/>" +
            "</button>" +
            "</form>" +
            "</td>";

        str += "<td>" + "<input type='text' class='form-control mr-sm-2' id='productName' value='" + productName + "' readonly>" + "</td>";
        str += "<td>";
        str += "<button type='submit' id='btn-undefinedStockUpdate' class='btn btn-primary'>SAVE</button>";
        str += "</td>";
        str += "<td>";
        str += "<button type='submit' id='btn-undefinedStockDelete' class='btn btn-warning'>DELETE</button>";
        str += "</td>";
        str += "</tr>";
        str += "</tbody>";
        str += "</table>";

        $('#stockClientUpdate').html(str);
    },

    undefinedStockSearchClient: function () {
        const left = (window.screenLeft / 2) - 800;
        const top = (window.screenTop / 2) - 600;

        window.open(
            "client/radio", "발주처 검색",
            "width=800, height=600," + "top=" + top + "," + "left=" + left +
            ", scrollbars=yes, resizeable=no"
        );
    },

    getClientName: function () {
        var name = $('input[name="clientName"]:checked').val();
        opener.document.getElementById("clientName").value = name;
        self.close();
    },

    undefinedStockUpdate: function () {
        var data = {
            productName: $('#productName').val(),
            clientName: $('#clientName').val()
        }

        if (!data.clientName) {
            alert("CLIENT_NAME 항목이 누락되었습니다. 다시 입력해주세요.");
        }
        else {
            $.ajax({
                type: 'PUT',
                url: '/api/v1/stock/client/update',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('발주처 등록이 완료되었습니다.');
                window.location.reload();
            }).fail(function () {
                alert(JSON.stringify(error));
            });
        }
    }
};

undefinedStock.init();
