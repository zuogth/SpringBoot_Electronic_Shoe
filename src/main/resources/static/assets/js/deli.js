$(()=>{
    $(window).bind('beforeunload', function(){
        $.ajax({
            url:'/api/bill/update',
            type:'PUT',
            contentType:'application/json',
            success:function (){

            },error:function (){
                alert('Có lỗi xảy ra!')
            }
        })
    });

    $('.form-group input').each(function (index,element){
        let valInit=$(this).val();
        if(valInit!==''){
            $(this).parent().children('span').addClass("span-ac");
            console.log(valInit);
        }
    })
    $('.form-group input').focus(function () {
        $(this).parent().children('span').addClass("span-ac");
    })
    $('.form-group input').focusout(function () {
        let val=$(this).val();
        if(val===''){
            $(this).parent().children('span').removeClass("span-ac");
        }
    })
    $('.form-group span').click(function(){
        $(this).parent().children('input').focus();
    })
    let totalPrice=0;
    let count=0;
    $('.item-order-r p#price').each(function (index, element){
        let quantity=$(element).parent().children('#count').html();
        let price=$(element).attr('data-price');
        totalPrice+=price*quantity;
        count+=quantity*1;
    })

    $('.total-prod').children().eq(0).html(count+' sản phẩm');
    $('.total-prod').children().eq(1).html(toMoney(totalPrice));
    let sale=$('.ship-prod').children().eq(1).attr("pay-sale");
    $('.total-price-child').children('span').html(toMoney(totalPrice+sale*1));

    $('.btn-pay button').click(function (){
        localStorage.removeItem('list_cart');
    })
})


$(()=>{
    $("#payment-offline").collapse('show');
    $('#payment-online').collapse('hide');
    $('.pay-off-selected').children("label").html(`<i class="fas fa-dot-circle"></i>`)
    $('.pay-onl-selected').children("label").html(`<i class="far fa-circle"></i>`);
    $(".pay-onl-selected").click(function(){
        $(this).children("label").html(`<i class="fas fa-dot-circle"></i>`)
        $('.pay-off-selected').children("label").html(`<i class="far fa-circle"></i>`)
        $("#payment-offline").collapse('hide');
        $('#payment-online').collapse('show');
    })

    $(".pay-off-selected").click(function(){
        $(this).children("label").html(`<i class="fas fa-dot-circle"></i>`)
        $('.pay-onl-selected').children("label").html(`<i class="far fa-circle"></i>`);
        $("#payment-offline").collapse('show');
        $('#payment-online').collapse('hide');
    })
})

$(()=>{
    $('#formPay').validate({
        relus:{
            email:{
                required:true,
                email:true
            },
            phone:{
                required:true,
                minlength:10
            }
        },
        messages:{
            email:{
                required:"Vui lòng nhập email của bạn",
                email:"Email bạn nhập không hợp lệ"
            },
            phone:{
                required:"Vui lòng nhập số điện thoại của bạn",
                minlength:"Số điện thoại không hợp lệ"
            },
            firstName:"Vui lòng nhập tên của bạn",
            lastName:"Vui lòng nhập họ của bạn",
            province:"Vui lòng chọn thành phố",
            district:"Vui lòng chọn huyện/quận",
            ward:"Vui lòng chọn xã",
            village:"Vui lòng ghi chi tiết địa chỉ giao hàng"
        },
        onfocusout: function(element) {
            this.element(element);
        },
        errorClass:"errorClass"
    });
})