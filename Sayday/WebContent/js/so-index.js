$(document).ready(function(){
	attachScrollEvent();
	$('#question-holder').focus();
	
	if(!/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
		$(document).keyup(function(event){
			if (event.keyCode === 27) {
				if ($("#main-header").css('position') == 'relative'){
					$("#main-header").css('position', 'sticky');
					$("#main-header").hide();
					$("#main-header").fadeIn();
				}
				else{
					$("#main-header").fadeOut(function(){
						$("#main-header").css('position', 'relative');
						$("#main-header").fadeIn();	
					});
				}
			}
		});
		var info = $('<h4 id="toggle-info">(Press ESC to toggle view)</h4>');
		$('#QnA').append(info);
	}
	else{
		console.log('lol');
		$("#main-header").css('position', 'relative');
	}
	$("#question-holder").keyup(function(event) {
	    if (event.keyCode === 13) {
	        $("#submit-button").click();
	        $("#question-holder").blur();
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
		$('#main-header').css('z-index', 5);
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
        	  var titleHeading = $("<h1 class='panel-title' style='font-size: 30px;'>" + response.title + "</h1>");
        	  var questionHeading = $("<h3 style='font-size:20px;'>" + response.question+ "</h3>")
        	  var XGlyph = $("<span class='glyphicon glyphicon-remove' style='float:right; cursor:pointer;'></span>");
        	  XGlyph.on('click', function(event){
        		 var initiator = event.target;
        		 var holder = $(this).parent().parent().parent();
        		 holder.fadeOut();
        	  });
        	  titleHeading.append(XGlyph);
        	  var panelBody = $("<div class='panel-body'>" + response.answer + "</div>")
        	  panelHeading.append(titleHeading);
        	  var tags = response.tags;
        	  var tag_container = $('<div></div>');
        	  
        	  for(var i in tags){
        		  var tag = $('<span class="badge badge-default">' + tags[i] + '</span>');
        		  tag_container.append(tag);
        	  }
        	  panelHeading.append(tag_container);
        	  panelHeading.append("<hr>");        	  
        	  panelHeading.append(questionHeading);
        	  panelObject.append(panelHeading);
        	  var badgeVal = response['is_top_rated']? 'BEST ANSWER': 'TOP RATED';
        	  var badgeCol = response['is_top_rated']? '#076506': '#af6d0a';
        	  var template = $(format('<span style="float: right; margin-right: 10px; margin-top: 10px; background-color: {0}"class="badge badge-default">{1}</span>',badgeCol, badgeVal));
        	  panelObject.append(template);
        	  panelObject.append(panelBody);
        	  $("#results-container").prepend(panelObject);
        	  var body = $("html, body");
        	  $(document).off('scroll');
        	  body.stop().animate({scrollTop:$(panelObject).offset().top}, { complete: function(){ attachScrollEvent();}}, 500, 'swing');
        	  $("#main-header").css('position', 'relative');
        	  restoreVisibility();
        	  $('#question-holder').focus();
          },
          error: function(){
        	  alert('Request failure');
        	  restoreVisibility();
        	  $('#question-holder').focus();
          }
        });
	})
});

function restoreVisibility(){
	$('#loader-div').fadeOut();
	$('#main-header').css('z-index', 18);
}

function attachScrollEvent(){
	$(document).scroll(function() {
		var scroll_current = $(document).scrollTop();
		var document_height = $(document).height();
		if(scroll_current<=$('.panel-primary').offset().top) {
		   $("#main-header").css('position', 'sticky');
		}
		else{
			if($('#toggle-info').css('display')=='none'){
				$('#toggle-info').css('display','block')
			}
		}
	});
}

format = function() {
	  var s = arguments[0];
	  for (var i = 0; i < arguments.length - 1; i++) {       
	    var reg = new RegExp("\\{" + i + "\\}", "gm");             
	    s = s.replace(reg, arguments[i + 1]);
	  }
	  return s;
}