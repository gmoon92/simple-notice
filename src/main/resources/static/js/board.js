$(document).ready(function () {
	getBoardList();
});

function factoryBoards(){
	return {
		 setBoards : function(_boardList){
			boardList = _boardList;
		 }
	
		,getBoards : function(){
			return boardList;
		}
	}
}

function getBoardList(pageNum){
	var pageNum = pageNum || 1;
	var keyword = $("#keyword").val() || "";
	var options = $("#options option:selected").val();
	
	$.ajax({
		 type: "GET"
		,url: "/api/board/"+pageNum+"?type=1&keyword="+keyword+"&option="+options
		,success: function (resObj) {
			new factoryBoards().setBoards(resObj);
			makeHtmlCode(resObj.data);
		}
	});
}

function deleteBoard(id){
	if(!confirm("삭제하시겠습니까?")){
		return;
	}
	$.ajax({
		 type: "DELETE"
		,contentType: "application/json"
		,url: "/api/board/" + id
		,success: function (resObj) {
			if(resObj == "SUCCESS"){
				getBoardList();
			}
			$("#myModal").modal("hide");
		}
		,error: function (resObj) {}
	});
}

function createBoard() {
    var params = {
    	 title 	  : $("#title").val()
    	,contents : $("#contents").val()
    	,type	  : 1
    	,u_id 	  : uId
    }
    
    $.ajax({
         type: "POST"
        ,contentType: "application/json"
        ,url: "/api/board/"
        ,dataType: 'json'
        ,data: JSON.stringify(params)
        ,success: function (resObj) {
        	getBoardList();
        	$("#myModal").modal("hide");
        }
        ,error: function (resObj) {}
    });
}

function updateBoard(id) {
	var params = {
			 title 	  : $("#title").val()
			,contents : $("#contents").val()
			,type	  : 1
			,u_id 	  : uId
	}
	$.ajax({
		//type: "PUT"
		type: "PATCH"
	   ,contentType: "application/json"
	   ,url: "/api/board/"+id
	   ,dataType: 'json'
	   ,data: JSON.stringify(params)
	   ,success: function (resObj) {
			getBoardList();
			$("#myModal").modal("hide");
		}
	,error: function (resObj) { }
	});
}