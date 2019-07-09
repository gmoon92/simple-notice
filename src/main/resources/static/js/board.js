$(document).ready(function () {
	getBoardList();
	$("#end_ymd").val(new Date().toISOString().substring(0, 10));
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

function getBoardListValid(){
	var warningStr = "은(는) 필수 입력값입니다.";
	var oDate 	 = $("#option_date option:selected").val();
	var oKeyword = $("#option_keyword option:selected").val();
	var sta_ymd  = $("#sta_ymd").val();
	var end_ymd  = $("#end_ymd").val();
	
	if(oDate == ""){ return alert("날짜 옵션"+ warningStr); }
	if(sta_ymd == ""){ return alert("시작일"+ warningStr); }
	if(end_ymd == ""){ return alert("종료일"+ warningStr); }
	if(oKeyword == ""){ return alert("키워드 옵션"+ warningStr); }
	return true;
}

function getBoardList(pageNum){
	if(!getBoardListValid()){
		return false;
	}
	
	var pageNum = pageNum || 1;

	var params = {
			 type 			 : 1
			,keyword 		 : $("#keyword").val() || ""
			,option_keyword  : $("#option_keyword option:selected").val() || ""
			,option_date  	 : $("#option_date option:selected").val() || ""
			,sta_ymd  		 : $("#sta_ymd").val() || ""
			,end_ymd  		 : $("#end_ymd").val() || ""
	};
	$.ajax({
		 type: "GET"
		,url: "/api/board/"+pageNum
		,data : params
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