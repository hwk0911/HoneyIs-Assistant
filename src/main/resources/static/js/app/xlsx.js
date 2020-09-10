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
    }
};

xlsx.init();
