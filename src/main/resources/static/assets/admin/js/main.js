// $(() => {
//     $('.close-alert').click(function(){
//         $(this).parent().children('span').html('');
//         $(this).parent().hide();
//     })
//     let htmlAlert=$('.alert-noti').children('span').html();
//     if(htmlAlert!=""){
//         $('.alert-noti').show();
//     }
// })

// loader
$(window).on("load",function(){
    setTimeout(function (){
        $(".preloader-cus").addClass("non-active-loader");
    },1000);

})