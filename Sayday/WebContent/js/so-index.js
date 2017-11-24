$(document).ready(function(){
	$("#question-holder").keyup(function(event) {
	    if (event.keyCode === 13) {
	        $("#submit-button").click();
	    }
	});
	$('#submit-button').on('click', function(e){
		var question = $('#question-holder').val();
		if (question == undefined || question == ""){
			alert("Please specify your question first...");
			return;
		}
		var data = {}
		data["Action"] = "ask";
		data["Question"] = question;
		$('#main-header').css('z-index', 14);
		$('#loader-div').fadeIn();
		$.ajax({
          type: "POST",
          url: "SOServlet",
          data: JSON.stringify(data),
          contentType: 'application/json; charset=utf-8',
          success: function(response){
        	  $('#question-holder').val('');
        	  var panelObject = $("<div class='panel panel-success'></div>");
        	  var panelHeading = $("<div class='panel-heading'></div>");
        	  var titleHeading = $("<h1 class='panel-title'>" + response.title + "</h1>");
        	  var questionHeading = $("<h3>" + response.question+ "</h3>")
        	  var XGlyph = $("<span class='glyphicon glyphicon-remove' style='float:right; cursor:pointer;'></span>");
        	  XGlyph.on('click', function(event){
        		 var initiator = event.target;
        		 var holder = $(this).parent().parent().parent();
        		 holder.fadeOut();
        	  });
        	  titleHeading.append(XGlyph);
        	  var panelBody = $("<div class='panel-body'>" + response.answer + "</div>")
        	  panelHeading.append(titleHeading);
        	  panelHeading.append(questionHeading);
        	  panelObject.append(panelHeading);
        	  panelObject.append(panelBody);
        	  $("#results-container").prepend(panelObject);
        	  var body = $("html, body");
        	  body.stop().animate({scrollTop:0}, 500, 'swing');
        	  restoreVisibility();
          },
          error: function(){
        	  alert('Request failure');
        	  restoreVisibility();
          }
        });
	})
});

function restoreVisibility(){
	$('#loader-div').fadeOut();
	$("#question-holder").blur();
	$('#main-header').css('z-index', 18);
}