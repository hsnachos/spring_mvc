<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../includes/header.jsp"></jsp:include>
      

	<!-- Begin Page Content -->
	<div class="container-fluid">
	
	    <!-- Page Heading -->
	    <h1 class="h3 mb-2 text-gray-800"></h1>
	    <p class="mb-4">DataTables is a third party plugin that is used to generate the demo table below.
	        For more information about DataTables, please visit the <a target="_blank"
	            href="https://datatables.net">official DataTables documentation</a>.</p>
	            
	    <!-- DataTales Example -->
	    <div class="card shadow mb-4">
	        <div class="card-header py-3">
	            <h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
	        </div>
	        <div class="card-body">
	           <form method="post">
				  <div class="form-group">
				    <label for="bno">bno</label>
				    <input type="text" class="form-control" placeholder="Enter bno" id="bno" name="bno" readonly value="${board.bno}">
				  </div>
				  <div class="form-group">
				    <label for="title">title</label>
				    <input type="text" class="form-control" placeholder="Enter title" id="title" name="title" readonly value="${board.title}">
				  </div>
				  <div class="form-group">
				    <label for="comment">content</label>
				    <textarea rows="10" class="form-control" id="comment" name="content" readonly>${board.content}</textarea>
				  </div>
				  <div class="form-group">
				    <label for="writer">writer</label>
				    <input type="text" class="form-control" placeholder="Enter writer" id="writer" name="writer" readonly value="${board.writer}">
				  </div>
<%--				   <sec:authentication property="principal.username"/>--%>
				   <c:if test="${not empty board.attachs[0].uuid}">
				   <div class="form-group">
					   <label for="file">file<br> <span class="btn btn-primary">파일첨부</span></label>
					   <input type="file" class="form-control d-none" id="file" name="file" multiple>
					   <div class="uploadResult my-3">
<%--						   <ul class="list-group filenames my-3"></ul>--%>
							   <ul class="list-group filenames my-3">
							   <c:forEach items="${board.attachs}" var="attach">

									   <li class="list-group-item" data-uuid="${attach.uuid}" data-name="${attach.name}" data-path="${attach.path}" data-image="${attach.image}">
										   <a href="/download${attach.url}&thumb=off">
											   <i class="far fa-file"></i>
												   ${attach.name}
										   </a>
										   <%--<i class="fa fa-times-circle btn-remove float-right" data-file="${attach.url}&thumb=on"></i>--%>
										   </li>

							   </c:forEach>
							   </ul>
						   <ul class="nav thumbs">
								<c:forEach items="${board.attachs}" var="attach">
									<c:if test="${attach.image}">
									   <li class="nav-item" data-uuid="${attach.uuid}">
										   <a class="img-thumb" href="">
											   <img class="img-thumbnail" src="/display${attach.url}&thumb=on" data-src="${attach.url}&thumb=off">
										   </a>
									   </li>
									</c:if>
								</c:forEach>
						   </ul>
					   </div>
				   </div>
				   </c:if>
				   <sec:authorize access="isAuthenticated() and principal.username eq #board.writer">

				  			<a href="modify?bno=${board.bno}" class="btn btn-outline-warning">Modify</a>
				   </sec:authorize>

				  <a href="list${cri.fullQueryString}" class="btn btn-secondary">List</a>
				</form>   
	        </div>
	    </div>

		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h6 class="m-0 font-weight-bold text-primary float-left">Reply</h6>
				<sec:authorize access="isAuthenticated()">
					<button class="btn btn-primary float-right btn-sm" id="btnReg">New Reply</button>
				</sec:authorize>
			</div>
			<div class="card-body">
				<ul class="list-group chat">

				</ul>
				<button class="btn btn-primary btn-block my-2 col-8 mx-auto btn-more">더보기</button>
			</div>
		</div>
	
	</div>
	<!-- /.container-fluid -->

	<div class="modal fade" id="replyModal" tabindex="-1" role="dialog" aria-labelledby="replyModalLabel"
		 aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="replyModalLabel">Modal title</h5>
					<button class="close" type="button" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="reply">Reply</label>
						<input type="text" class="form-control" id="reply" placeholder="Enter reply" >
					</div>
					<div class="form-group">
						<label for="replyer">Replyer</label>
						<input type="text" class="form-control" id="replyer" placeholder="Enter replyer" readonly>
					</div>
					<div class="form-group">
						<label for="replydate">Reply Date</label>
						<input type="text" class="form-control" id="replydate" >
					</div>

				</div>
				<div class="modal-footer" id="modalFooter">
					<button class="btn btn-warning" type="button" data-dismiss="modal">Modify</button>
					<button class="btn btn-danger" type="button" data-dismiss="modal">Remove</button>
					<button class="btn btn-primary" type="button" data-dismiss="modal">Register</button>
					<button class="btn btn-secondary" type="button" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>


<script>
	ClassicEditor.create($("#comment").get(0), {
		toolbar : []
	}).then(function (editor) {
		editor.enableReadOnlyMode('lock');
		editor.isReadOnly = true;
	});
</script>
<script>
	var cp = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/resources/js/reply.js" ></script>
<script>
	moment.locale('ko');
	console.log('${pageContext.request.contextPath}');

	var bno = '${board.bno}';

	var csrfHeader= '${_csrf.headerName}';
	var csrfToken = '${_csrf.token}';
	//
	// console.log("bno", bno);
	// console.log(replyService);
	// console.log(replyService.add);

	var replyer = '';
	<sec:authorize access="isAuthenticated()">
		replyer = '<sec:authentication property="principal.username"/>'
	</sec:authorize>
	
	$(document).ajaxSend(function (e, xhr) {
		xhr.setRequestHeader(csrfHeader, csrfToken)
	})

	replyService.getList({bno:bno}, function (result) {
		var str = "";
		for(var i in result){
			str += getReplyLiStr(result[i]);
		}
		$(".chat").html(str);
	});

	// $("#replyModal").modal("show");
	$("#btnReg").click(function (){
		$("#replyModal").find("input").val("").prop("readonly", false);
		$("#replydate").closest("div").hide();
		$('#replyer').val(replyer);
		$('#replyer').attr("readonly", true);

		// $("#modalFooter button").hide();
		//$("#modalFooter button").eq(1).hide();
		//$("#modalFooter button").eq(2).hide();

		$("#replyModal").modal("show");
	})

	$(".chat").on("click", "li", function () {
		console.log("rno", $(this).data("rno"));
		replyService.get($(this).data("rno"), function (result) {
			// $("#replyModal").find("input").val("")
			$("#reply").val(result.reply);
			$("#replyer").val(result.replyer);
			$("#replydate").val(moment(result.replydate).format()).prop("readonly", true).closest("div").show();

			$("#modalFooter button").show();
			$("#modalFooter button").eq(2).hide();

			$("#modalFooter button").eq(3).hide();

			$("#replyModal").modal("show").data("rno", result.rno);
			console.log(result);
		});
	})

	// 글 작성 버튼 이벤트
	$("#modalFooter button").eq(2).click(function () {
		var obj = {bno:bno, reply:$("#reply").val(), replyer:$("#replyer").val()}
		console.log(obj);
		replyService.add(obj, function (result) {
			$("#replyModal").find("input").val("");
			$("#replyModal").modal("hide");
			console.log(result);


			replyService.get(result, function (data) {
				console.log(data);
				$(".chat").prepend(getReplyLiStr(data));
			})

			// 추가 이벤트
		})
	})

	// 삭제버튼
	$("#modalFooter button").eq(1).click(function () {
		var obj = {rno : $("#replyModal").data("rno"), replyer: $("#replyer").val()}
		console.log('reply del obj', obj);

		replyService.remove(obj, function (result) {
			$("#replyModal").modal("hide");
			console.log(result);
			$(".chat li").each(function () {
				console.log($(this).data("rno"))
				if($(this).data("rno") == obj.rno){
					$(this).remove();
				}
			})
		})
	})

	$("#modalFooter button").eq(0).click(function () {
		var obj = {rno:$("#replyModal").data("rno"), reply:$("#reply").val()}
		console.log(obj);
		replyService.modify(obj, function (result) {
			$("#replyModal").modal("hide");
			console.log(result);

			$(".chat li").each(function () {
				if($(this).data("rno") == obj.rno){
					var $this = $(this);
					replyService.get($this, function (r) {
						$this.replaceWith(getReplyLiStr(r));
					});
				}
			})

		})
	})

	$(".btn-more").click(function () {
		// var rno = $(".chat li").last()
		var rno = $(".chat li:last").data("rno") + 1;

		console.log("rno", rno);

		replyService.getList({bno:bno, rno:rno}, function (result) {
			console.log("result", result);

			if(!result.length){
				$(".btn-more").prop("disabled", true);
				return;
			}

			var str = "";
			for(var i in result){
				str += getReplyLiStr(result[i]);
			}
			 $(".chat").append(str);
		});
	})

	function getReplyLiStr(obj) {
		return `
					<li class="list-group-item" data-rno="\${obj.rno}">
						<div class="header">
							<strong class="primary-font">\${obj.replyer}</strong>
							<small class="float-right text-muted">\${moment(obj.replydate).fromNow()}</small>
						</div>
						<p>\${obj.reply}</p>
					</li>
			`;
	}
/*
	var inputJson = {bno:bno, replyer:'작성자', reply:'댓글 내용'};
	replyService.add(inputJson, function (result) {
		console.log(result);
	});
*/
/*

	replyService.get(26, function (result) {
		console.log(result);
	});
*/


/*

	replyService.remove(26, function (result) {
		console.log(result);
	});
*/

/*

	replyService.modify({rno:40, reply:'수정된 댓글 내용'}, function (result) {
		console.log(result);
	});
*/

	$("form button").click(function () {
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
		$("form").append(str).submit();
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

	$(":file").change(function () {
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

		console.log('formData', formData);

		$.ajax({
			url: '/uploadAjax',
			processData : false,
			contentType : false,
			data : formData,
			dataType: "json",
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

			str += `<li class="list-group-item" data-uuid="\${obj.uuid}" data-name="\${obj.name}" data-path="\${obj.path}" data-image="\${obj.image}"><a href="/download?\${params}"><i class="fas fa-file"></i>`;
			str += obj.name + '</a><i class="fa fa-times-circle btn-remove float-right" data-file="' + params + '" ></i></li>'
			if(obj.image){
				obj.thumb = "off";
				var params2 = new URLSearchParams(obj).toString();
				imgStr += '<li class="nav-item"><a class="img-thumb" href="" ><img src="/display?' + params + '" data-src="' + params2 + '"></li>';
			}

		}
		console.log("showUploadedFile", str);
		console.log("imgStr", imgStr)

		// 내부적으로 스트림 사용
		$(".uploadResult .filenames").append(str);
		$(".uploadResult .thumbs").append(imgStr);
		$(":file").get(0).reset();
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
</script>

<jsp:include page="../includes/footer.jsp"></jsp:include>