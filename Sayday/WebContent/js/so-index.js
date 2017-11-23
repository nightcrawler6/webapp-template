$(document).ready(function(){
	$('#submit-button').on('click', function(e){
		var question = $('#question-holder').val();
		if (question == undefined || question == ""){
			alert("Please specify your question first...");
			return;
		}
		var data = {}
		data["Action"] = "ask";
		data["Question"] = question;
		$.ajax({
          type: "POST",
          url: "SOServlet",
          data: JSON.stringify(data),
          contentType: 'application/json; charset=utf-8',
          success: function(response){
        	  $('#question-holder').val('');
        	  var panelObject = $("<div class='panel panel-success'></div>");
        	  var panelHeading = $("<div class='panel-heading'></div>");
        	  var textHeading = $("<string><h1 class='panel-title'>" + response.title + "</h1></strong><h3>" + response.question+ "</h3>");
        	  var panelBody = $("<div class='panel-body'>" + response.answer + "</div>")
        	  panelHeading.append(textHeading);
        	  panelObject.append(panelHeading);
        	  panelObject.append(panelBody);
        	  $("#results-container").prepend(panelObject);
          },
          error: function(){
        	  alert('Request failure');
          }
        });
	})
});