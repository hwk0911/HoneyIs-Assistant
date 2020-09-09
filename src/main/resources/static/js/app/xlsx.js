var xlsx = {
    init: function () {
        var _this = this;
        $(document).on('dragover', '#area-order', function (e) {
            _this.dragOver(e);
        });
        $(document).on('dragover', '#area-send', function (e) {
            _this.dragOver(e);
        });
        $(document).on('dragleave', '#area-order', function (e) {
            _this.dragOver(e);
        });
        $(document).on('dragleave', '#area-send', function (e) {
            _this.dragOver(e);
        });
        $(document).on('drop', '#area-order', function (e) {
            _this.uploadFiles(e, "order");
        });
        $(document).on('drop', '#area-send', function (e) {
            _this.uploadFiles(e, "send");
        });
        $(document).on('click', '#orderTable tr', function () {
            _this.updateClient($(this));
        });
    },

    dragOver: function (e) {
        e.stopPropagation();
        e.preventDefault();
        if (e.type == "dragover") {
            $(e.target).css({
                "background-color": "black",
                "outline-offset": "-20px"
            });
        } else {
            $(e.target).css({
                "background-color": "gray",
                "outline-offset": "-10px"
            });
        }
    },

    uploadFiles: function (e, method) {
        e.stopPropagation();
        e.preventDefault();
        this.dragOver(e);

        e.dataTransfer = e.originalEvent.dataTransfer;
        var files = e.target.files || e.dataTransfer.files;

        var formData = new FormData();

        for (var index = 0, size = files.length; index < size; ++index) {
            formData.append("files", files[index]);
        }

        if (files[0].name.includes(".xlsx") || files[0].name.includes(".xls")) {
            $.ajax({
                type: "POST",
                url: "/api/v1/xlsx/analyze/" + method,
                data: formData,
                processData: false,
                contentType: false
            }).done(function (e) {
                window.location.href = "/xlsx/result/" + method;
            }).fail(function () {
                alert("파일 형식 에러: \n엑셀 내 상품명, 옵션 정보, 수량 분류가 존재하는지 확인해주세요.");
            });
        }
        else {
            alert("xlsx 또는 xls 파일이 아닙니다.");
        }
    },

    updateClient: function (tr) {
        alert("다음 상품의 발주처를 수정합니다." + tr.text());

        var td = tr.children();

        var productName = td.eq(0).text();
        var productId = td.eq(4).text();

        this.clientPopup(productId, productName);
    },

    clientPopup: function(productId, productName) {
        var id = productId;
        var name = productName;

        var str = "<form class='form-inline md-form mr-auto mb-4' style='float:right;' method='get' action='/client/search'>";
        str += "<input class='form-control mr-sm-2' type='text' placeholder='Search' aria-label='Search' name='searchword'>";
        str += "<button class='btn aqua-gradient btn-rounded btn-sm my-0' type='submit' id='btn-clientSearch'>";
        str += "<img src='https://img.icons8.com/ios-glyphs/30/000000/search.png'/>";
        str += "</button>";
        str += "</form>";

        var popup = window.open("", "클라이언트 검색", "width=600, height=380");
        popup.document.write(
            str
        );

        
    }
};

xlsx.init();
