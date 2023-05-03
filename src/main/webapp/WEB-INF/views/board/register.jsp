<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../includes/header.jsp"></jsp:include>
      

	<!-- Begin Page Content -->
	<div class="container-fluid">
	
	    <!-- Page Heading -->
	    <h1 class="h3 mb-2 text-gray-800">Tables</h1>
	    <p class="mb-4">DataTables is a third party plugin that is used to generate the demo table below.
	        For more information about DataTables, please visit the <a target="_blank"
	            href="https://datatables.net">official DataTables documentation</a>.</p>
	
	    <!-- DataTales Example -->
	    <div class="card shadow mb-4">
	        <div class="card-header py-3">
	            <h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
	        </div>
	        <div class="card-body">
	           <form method="post" id="boardform">
				  <div class="form-group">
				    <label for="title">title</label>
				    <input type="text" class="form-control" placeholder="Enter title" id="title" name="title">
				  </div>
				  <div class="form-group">
				    <label for="comment">content</label>
				    <textarea rows="10" class="form-control" id="comment" name="content" ></textarea>
				  </div>
				  <div class="form-group">
					  <label for="writer">writer</label>
				    <input type="text" class="form-control" placeholder="Enter writer" id="writer" name="writer" value="<sec:authentication property="principal.member.userName" />" readonly>
					  <input type="hidden" class="form-control" name="writer" value="<sec:authentication property="principal.username" />">
				  </div>
				   <div class="form-group">
					   <label for="file">file<br> <span class="btn btn-primary">파일첨부</span></label>
					   <input type="file" class="form-control d-none" id="file" name="file" multiple>
					   <div class="uploadResult my-3">
						   <ul class="list-group filenames my-3 sortable"></ul>
						   <ul class="nav thumbs"></ul>
					   </div>
				   </div>

				  <button type="submit" class="btn btn-primary" id="#btnReg">Submit</button>
				   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				  <input type="hidden" name="pageNum" value="1">
				  <input type="hidden" name="amount" value="${cri.amount}">
				</form>   
	        </div>
	    </div>
	
	</div>
	<!-- /.container-fluid -->

<style>
	.ck-editor__editable[role="textbox"] {
		min-height: 300px;
	}
</style>
<script>
	ClassicEditor.create($("#comment").get(0), {
		ckfinder : {
			uploadUrl : '/ckImage.json'
		}
	});
</script>
<jsp:include page="../includes/footer.jsp"></jsp:include>

<script>
	var csrfHeader= '${_csrf.headerName}';
	var csrfToken = '${_csrf.token}';

	$(document).ajaxSend(function (e, xhr) {
		xhr.setRequestHeader(csrfHeader, csrfToken)
	})

	$(function () {
		$("#btnReg").click(function () {
			event.preventDefault();
			// title, content, writer, attachs[0].uuid
			var str = '';
			$(".filenames li").each(function (i, obj) {
				console.log(i, obj, this);
				str += `
					<input type="hidden" name="attachs[\${i}].uuid" value="\${$(this).data('uuid')}">
					<input type="hidden" name="attachs[\${i}].name" value="\${$(this).data('name')}">
					<input type="hidden" name="attachs[\${i}].path" value="\${$(this).data('path')}">
					<input type="hidden" name="attachs[\${i}].image" value="\${$(this).data('image')}">
				`

			})

			console.log(str);
			$("#boardform").append(str).submit();
		})

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

		$("#file").change(function () {
			event.preventDefault();

			let files = $("#file").get(0).files;
			console.log("button click", files);
			if(!checkExtension(files)){
				alert("조건 불일치");
				return false;
			}


			let formData = new FormData();
			for(let i in files){
				formData.append("files", files[i]);
			}

			console.log('formData', formData);

			$.ajax({
				url: '/uploadAjax',
				processData : false,
				contentType : false,
				data : formData,
				dataType: "json",
				beforeSend: function (xhr) {
					xhr.setRequestHeader(csrfHeader, csrfToken);
				},
				method : "post",
				success : function (data) {
					console.log("ajax", data);
					$("form").get(0).reset();
					showUploadedFile(data);
				}

			})


		})

		// 업로드관련
		/*
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
		*/
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

			var param = $(this).find("img").data("src")
			$("#showImageModal").find("img").attr("src", "/display" + param).end().modal("show");

		})

		$(".uploadResult ul").on("click", ".btn-remove", function () {
			console.log("delete click")

			var $this = $(this);
			var file = $(this).data("file");
			console.log(file);
			console.log(file.uuid);
			$.ajax({
				url : "/deleteFile?" + file,
				success : function (data) {
					console.log(data);
					// $this.parent().remove();
					$('[data-uuid="'+ data + '"]').remove();
				}
			})
		});

		function showUploadedFile(uploadResultArr) {
			var str = "";
			var imgStr = "";

			console.log(uploadResultArr)

			// $.each
			// $(selector).each
			for(var i in uploadResultArr){
				let obj = uploadResultArr[i];

				console.log('obj', i, obj);

				obj.thumb = 'on';
				var params = new URLSearchParams(obj).toString()

				str += `<li class="list-group-item " data-uuid="\${obj.uuid}" data-name="\${obj.name}" data-path="\${obj.path}" data-image="\${obj.image}"><a href="/download\${obj.url}"><i class="far fa-file"></i>`;
				str += obj.name + `</a><i class="far fa-times-circle btn-remove float-right" data-file="\${obj.url}" ></i></li>`
				if(obj.image){
					imgStr += `<li class="nav-item m-2 " data-uuid="\${obj.uuid}"><a class="img-thumb" href="" ><img class="img-thumbnail" src="/display\${obj.url}&thumb=on" data-src="\${obj.url}"></a></li>`;
				}

			}
			console.log("showUploadedFile", str);
			console.log("imgStr", imgStr)

			// 내부적으로 스트림 사용
			$(".uploadResult .filenames").append(str);
			$(".uploadResult .thumbs").append(imgStr);
			//$(":file").get(0).reset();
		}
/*
		$("form button").click(function () {
			//event.preventDefault();

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

			console.log('formData', formData);

			$.ajax({
				url: '/uploadAjax',
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

		*/

		$('.filenames').sortable({
			/*
			sort: function (event, ui){

			},
			*/
			change : function () {
				console.log(this);
			}
		}).css({cursor: "move"});
	})

</script>