var undefinedStock = {
    init: function () {
        var _this = this;
        $('#undefinedTable tr').on('click', function () {
            _this.addSaveForm($(this));
        });
        $(document).on('click', '#btn-undefinedStockSearchClient', function () {
            _this.undefinedStockSearchClient();
        });
        $('#btn-clientSubmit').on('click', function () {
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

        str += "<td id=productName>" + productName + "</td>";
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

    getClientName: function () {
        var name = $('input[name="clientName"]:checked').val();
        opener.document.getElementById("clientName").value = name;
        self.close();
    }
};

undefinedStock.init();
