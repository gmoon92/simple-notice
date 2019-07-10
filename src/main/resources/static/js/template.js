$(document).ready(function () {
	eventManagement();
});

function eventManagement(){
	$("#myBtn")
	.click(function(){
		openModal();
    });
	
	$("#keyword")
	.keydown(function(e){
		if(e.keyCode == 13){
			getBoardList();
		}
	});
	$("#keywordBtn")
	.click(function(e){
		getBoardList();
	});
}

function makeHtmlCode(params){
	makeTableHtmlCode(params.content || []);
	makePageNationHtmlCode(params);
	
	function makeTableHtmlCode(resRowDatas){
		var $selector = $("#noticeContents");
		var htmlCode = "";
		if(resRowDatas.length > 0){
			for(var rowIdx in resRowDatas){
				var id 		 	 = resRowDatas[rowIdx].id;
				var title 		 = resRowDatas[rowIdx].title || "";
				var name 	 	 = resRowDatas[rowIdx].member.name || "";
				var createdDate  = resRowDatas[rowIdx].createdDate.substr(0,10) || "";
				var modifiedDate = resRowDatas[rowIdx].modifiedDate.substr(0,10) || "";
				
				htmlCode +="<tr onclick='triggerContents("+id+")'>"
						  +"<td>"+ title +"</td>" 
						  +"<td>"+ name +"</td>" 
						  +"<td>"+ createdDate +"</td>" 
						  +"<td>"+ modifiedDate +"</td>"
						  +"</tr>" 
						  
						  +"<tr style='display:none;' id='contents"+id+"' class='contents'>"
						  +"<td colspan='4' style='padding:3%;' id='contents"+rowIdx+"'></td>"
						  
						  +"<tr style='display:none;' id='btnArea"+id+"' class='contents'>"
						  +"<td colspan='4' style='text-align:right;'>";
				
				if(uId==resRowDatas[rowIdx].member.uid){
				htmlCode  +="<button class=\"btn btn-info\" onclick=\"openModal("+id+");\">수정</button>" 
						  +"<button class=\"btn btn-danger\" onclick=\"deleteBoard("+id+");\">삭제</button>";
				}
				htmlCode  +="</td>"
						  +"</tr>" ;
				$selector.html(htmlCode);
			}
			
			for(var rowIdx in resRowDatas){
				var contents 	 = resRowDatas[rowIdx].contents.replace(/(?:\r\n|\r|\n)/g, '<br />') || "";
				$("#contents"+rowIdx).text(contents);
			}
		}else{
			htmlCode +="<tr>"
					  +"<td colspan='4' align='center'>'"+ $("#keyword").val() + "'에 대한 검색결과가 없습니다.</td>"
				 	  +"</tr>";
			
			$selector.html(htmlCode);
		}
	}
	
	function makePageNationHtmlCode(params){
		var $selector = $(".baordPaging>.pagination");
		var htmlCode = "";
		
		if(!params.empty){
			var pageable = params.pageable;
			
			// 페이지 제한 블록
			var limitBlock = 5;
			// 전체 페이지 수
			var totalPages = params.totalPages;
			// 전체 페이지 블록 수
			var totaBlcok = totalPages%limitBlock == 0 ? totalPages/limitBlock : totalPages/limitBlock + 1;
			// 현재 페이지 수
			var curPageNum = pageable.pageNumber+1; // jpa 0부터 시작
			// 현재 블록
			var curBlock = Math.ceil(curPageNum/limitBlock); // 올림
			// 블록 시작을 알리는 숫자
			var firstBlockNum = (curBlock-1)*limitBlock + 1;
			// 블록 마지막을 알리는 숫자
			var lastBlockNum = (firstBlockNum + limitBlock) > totalPages ? totalPages + 1: (firstBlockNum + limitBlock);
			
			
			var isDisableLeftArrow = (curBlock == 1 || params.first) ? ' class="disabled" ' : ' onClick=\"getBoardList('+ (firstBlockNum-1) +')\"';
			htmlCode += "<li" + isDisableLeftArrow + ">"
					 + '<a href=\"javascript:void(0)\"> <i class=\"fa fa-chevron-left\"></i></a>'
					 + "</li>";
			
			for(var i = firstBlockNum; i < lastBlockNum; i++){
				var active = curPageNum == i ? " class=' active'" : ' onClick=\"getBoardList('+ i +')\"';
				htmlCode +="<li"+active+">"
						 +'<a href=\"javascript:void(0)\">' + (i) + '</a>'
						 +"</li>";
			}
			
			var isDisableRightArrow = (totaBlcok == curBlock || params.last) ? ' class="disabled" ' : ' onClick=\"getBoardList('+ (lastBlockNum) +')\"';
			htmlCode += "<li" + isDisableRightArrow + ">"
					 + '<a href=\"javascript:void(0)\"> <i class=\"fa fa-chevron-right\"></i> </a>'
					 + "</li>";
		}
		$selector.html(htmlCode);
	}
}

function triggerContents(id){
	var $selector = $("#contents"+id+", #btnArea"+id);
	
	if($selector.css("display")=="none"){
		$selector.css("display","table-row");
	}else{
		$selector.css("display","none");
		
	}
}

function openModal(id){
	var id = id || "";
	var $modalTitle = $("#modalTitle");
	var $btnModal 	= $("#btnModal");

	if(id == ""){
		$modalTitle.text("글 작성");
		$btnModal.text("작성");
		$(".modal-footer").html('<button class="btn btn-primary" id="btnModal" onclick="createBoard();" data-dismiss="modal">저장</button>');
		$("#title").val("");
		$("#contents").val("");
		
	}else{
		$modalTitle.text("글 수정");
		$btnModal.text("수정");
		$(".modal-footer").html('<button class="btn btn-primary" id="btnModal" onclick="updateBoard('+id+');" data-dismiss="modal">수정</button>');
		
		var boards = new factoryBoards().getBoards().data.content;
		for(idx in boards){
			var board = boards[idx];
			if(id==board.id){
				$("#title").val(board.title);
				$("#contents").val(board.contents);
				break;
			}
			continue;
		}
	}
	$("#myModal").modal();
}
