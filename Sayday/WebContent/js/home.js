$('document').ready(function(){
    $.ajax({
        url:"http://localhost:8080/Logger/DataSender",
        type:"GET",
        data:
        {
            id:"12",
            name:"karim"
        },
        success: function(data){
            console.log(data);
        }
    })
})