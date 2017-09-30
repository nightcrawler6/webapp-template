$(document).ready(function(){
	$('.selectpicker').selectpicker();
	
    $( "#mybtn" ).click(function() {
    	var firstName = $('#fname').val();
    	var lastName = $('#lname').val();
    	if(notValid(firstName, lastName)){
    		alert('Please fill in first name and last name!');
    		return;
    	}
        $.ajax({
          type: "POST",
          url: "http://localhost:8080/Sayday/FormServlet",
          data: JSON.stringify({
            firstName: $('#fname').val(),
            lastName: $('#lname').val()
          }),
          success: function(){
        	  alert('saved successfully: ' + firstName + ' ' + lastName)
          },
          error: function(){
        	  alert("couldn't save entry!")
          }
        })
    });
    
    $.ajax({
    	type: "GET",
    	url: "http://localhost:8080/SayDay/FormServlet?type=getAllPeople",
    	dataType: "json",
    	success: function(data){
    		fillSelectPicker(data);
    	},
    	error: function(){
    		alert('error fetching data from server')
    	}
    })
    
    function notValid(fname, lname){
    	if(fname==undefined ||
    			fname=="" ||
    			lname==undefined ||
    			lname==""){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    function fillSelectPicker(data){
    	var selectObj = $('.selectpicker');
    	for(var i in data){
    		console.log(data[i]);
    	}
    }
});