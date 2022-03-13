$(() => {
    $('.close-alert').click(function(){
        $(this).parent().children('span').html('');
        $(this).parent().hide();
    })
    let htmlAlert=$('.alert-noti').children('span').html();
    if(htmlAlert!=""){
        $('.alert-noti').show();
    }
})