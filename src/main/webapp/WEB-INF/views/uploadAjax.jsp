<%--
  Created by IntelliJ IDEA.
  User: banghansol
  Date: 2023/04/07
  Time: 10:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>2023/04/07 10:34 AM</title>
    <link href="${pageContext.request.contextPath }/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath }/resources/vendor/jquery/jquery.min.js"></script>
    <style>
        .body{
            margin: 0;
        }
        .bigPictureWrapper {
            position: absolute;
            display: none;
            justify-content: center;
            align-items: center;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: gray;
            z-index: 100;
            background: rgba(0, 0, 0, 0.5);
        }

        .bigPicture {
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .bigPicture img {
            max-width: 600px;
        }
    </style>
</head>
<body>
    <form method="post" enctype="multipart/form-data">
        <input name="test">
        <label for="files"><i class="fas fa-folder-plus"></i></label>
<%--        <div class="uploadDiv">--%>
        <input type="file" name="files" id="files" multiple accept="image/jpeg, image/png" style="display: none">
<%--        </div>--%>
        <input type="reset" value="reset">
        <button>submit</button>
        <div></div>
    </form>
    <div class="uploadResult">
        <ul>

        </ul>
    </div>
    <div class="bigPictureWrapper">
        <div class="bigPicture">

        </div>
    </div>
    <script>

        function checkExtension(files) {
            const MAX_SIZE = 5 * 1024 * 1024;
            const EXCLUDE_EXT = new RegExp("(.*?)\.(js|jsp|asp|php)")

            for(let i in files){
                if(files[i].size >= MAX_SIZE || EXCLUDE_EXT.test(files[i].name)) {
                    return false;
                }
            }

            return true;
        }
            /*
            // JS의 파일 타입을 사용하는 내용
        $("form :file").change(function () {
            let str = "";
            for(let i in this.files){
                str += "<p>" + "============================" + "</p>"
                str += "<p>" + this.files[i].name + "</p>"
                str += "<p>" + this.files[i].type + "</p>"
                str += "<p>" + this.files[i].size + "</p>"
            }

            $("form div").html(str);
        });
            */
/*

        function showImage(param) {
            //alert(param);
            $(".bigPictureWrapper").css("display", "flex").show();
            $(".bigPicture")
                .html("<img src='/display?" + param + "'>")
                .animate({width:'100%', height:'100%'}, 500);
        }
*/

        $(".uploadResult ul").on("click", ".img-thumb", function () {
            event.preventDefault();

            $(".bigPictureWrapper").css("display", "flex").show();
            var param = $(this).find("img").data("src")

            console.log('imgcheck', $(this));
            console.log('param', param);

             $(".bigPicture")
                 .html("<img src='/display?" + param + "'>")
                 .animate({width:'100%', height:'100%'}, 500);
        })

        $(".uploadResult ul").on("click", ".btn-remove", function () {
            event.preventDefault();
            var file = $(this).data("file");
            console.log(file);
            /*$.post("/deleteFile", {
                data : {file: file},
                dataType : "json",
                success : function (data) {
                    console.log(data);
                }
            })*/
            $.getJSON("/deleteFile?" + file).done(function () {
                console.log(data);
            });
        });

        function showUploadedFile(uploadResultArr) {
            var str = "";

            // $.each
            // $(selector).each
            for(var i in uploadResultArr){
                let obj = uploadResultArr[i];
                obj.thumb = "on";
                var params = new URLSearchParams(obj).toString()

                if(!obj.image){

                    str += '<li><a href="/download?' + params + '"><i class="fas fa-file"></i>';
                } else {
                    obj.thumb = "off";
                    var params2 = new URLSearchParams(obj).toString();
                    str += '<li><a class="img-thumb" href="" ><img src="/display?' + params + '" data-src="' + params2 + '" >';
                }
                str += obj.name + '</a><i class="fa fa-times-circle btn-remove" data-file="' + params + '" ></i></li>'

            }

            // console.log("showUploadedFile", str);

            // 내부적으로 스트림 사용
            $(".uploadResult ul").append(str);
        }

        $("form button").click(function () {
            event.preventDefault();

            let files = $(":file").get(0).files;
            console.log("button click", files);
            if(!checkExtension(files)){
                alert("조건 불일치");
                return false;
            }


            let formData = new FormData();
            for(let i in files){
                formData.append("files", files[i]);
            }

            $.ajax({
                url: 'uploadAjax',
                processData : false,
                contentType : false,
                data : formData,
                method : "post",
                success : function (data) {
                    console.log("ajax", data);
                    $("form").get(0).reset();
                    showUploadedFile(data);
                }

            })


        })
    </script>
</body>
</html>