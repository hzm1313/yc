function record() {
    $.ajax({
        type:'post',
        url:'record',
        cache:false,
        dataType:'json',
        success:function(data){
        },
        error : function(data){
        }
    });
}
record();