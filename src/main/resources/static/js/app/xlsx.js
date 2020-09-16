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
        $(document).on('click', '#btn-deduction', function () {
            _this.deduction();
        });
        $(document).on('click', '#btn-print', function () {
            _this.print();
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
                url: "/api/v1/xlsx/analyze",
                data: formData,
                processData: false,
                contentType: false,
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
            }).done(function (e) {
                window.location.href = "/xlsx/result";
            }).fail(function () {
                alert("파일 형식 에러: \n엑셀 내 상품명, 옵션 정보, 수량 분류가 존재하는지 확인해주세요.");
            });
        }
        else {
            alert("xlsx 또는 xls 파일이 아닙니다.");
        }
    },

    deduction: function (tr) {
        var tr = $('tr[name=resultTable]');
        var data = {};

        for (var index = 0, size = tr.length; index < size; ++index) {
            var td = tr.eq(index).children();

            if (td.eq(1) === "COLOR") continue;

            data[td.eq(5).text()] = [td.eq(3).text()]
        }

        $.ajax({
            type: 'PUT',
            url: '/api/v1/stock/deduction',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('재고 수량 수정이 완료되었습니다.');
            window.location.href = "/xlsx/result/deduction";
        }).fail(function () {
            alert(JSON.stringify(error));
        });
    },

    print: function() {
        var printBody = $('#printArea').html();

        win = window.open();
        
        
    

    
    
        

        win.document.write("<!DOCTYPE HTML>");
        win.document.write("<html>");
        win.document.write("<head>");
        win.document.write("<title>Honey_Is Assistant</title>");
        win.document.write("<meta http-equiv='Content-Type' content='text/html' charset='UTF-8'/>");
        win.document.write("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'>");
        win.document.write("<link rel='stylesheet' href='/css/main.css'></link>");
        win.document.write("</head>");
        win.document.write(printBody);

        win.print();
        win.close();
    }
};

xlsx.init();
