$(document).ready(function(){
	
	$("#right-toggle").change(function() {
		  $('#context1').toggle();
		  $('#context2').toggle();
	});
		
	$('#upload-submit-all').click(function(){
		alert('clicked on upload-submit-all');
	});
	
	$('#hash-submit-all').click(function(){
		alert('clicked on hash-submit-all');
	});
	
	/*Template for sending for an AJAX request on click trigger
	 * 
	 * 
	 $( "#<buttonID>" ).click(function() {
        $.ajax({
          type: "POST",
          url: "<Target URL here>",
          data: <some Json data>,
          success: function(){
        	  alert('Request success - 200 OK');
          },
          error: function(){
        	  alert('Request failure');
          }
        })
    });
    
	 * 
	 * */
	
});