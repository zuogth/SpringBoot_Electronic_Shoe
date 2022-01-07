$(()=>{
    let active=$(location).attr('pathname').split('/')[2];
    $('.options-info ul li#info-'+active).addClass("active")
    $('#phone_user').html($('#phone_user').html().slice(0,7)+'***');
    showContent();
    $('.options-info ul li').click(function(){
        $('.options-info ul li').removeClass('active');
        $(this).addClass('active');
        showContent();
    })
})

function showContent(){
    $('.main-info-item').removeClass('active-content');
    $('.options-info ul li').each(function(index,element){
        let flag=$(element).attr('class');
        if(flag==='active'){
            let id=$(element).attr('id');
            $('.'+id).addClass('active-content');
        }
    })
}
//custom span input
$(()=>{
    $('.form-group input').each(function (index,element){
        let valInit=$(this).val();
        if(valInit!==''){
            $(this).parent().children('span').addClass("span-ac");
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
})

$(()=>{
    //gender
    $('.input-gender>label').click(function(){
        $('.input-gender>label>label').html('<i class="far fa-circle"></i>');
        $(this).children('label').html('<i class="fas fa-dot-circle"></i>');
    })
    //clear form
    $('.btn-login button.cancel').click(function(){
        let id=$(this).parents().eq(1).attr("id");
        $('#'+id).validate().resetForm();
    })
    $('#change-pw-btn').click(function(){
        clearForm('form-change-pw');
    })
})
//đánh giá sao
$(()=>{
    rateYo=$("#rateYo2").rateYo({
        fullStar: true,
        starWidth: "25px",
        ratedFill:'#8f0037'
    });
    $('#rateYo2').click(function(){
        var rating = rateYo.rateYo("rating");
        $('input#star').val(rating);
    })

    $('a.ratting').click(function(){
        clearForm('form-ratting');
        rateYo.rateYo("rating", 0);
    })
})
function clearError(){
    $('.star label').html('');
}

function clearForm(id){
    $('#'+id ).validate().resetForm();
    $('#'+id ).trigger("reset");
    $('#'+id+' span').removeClass("span-ac");
}
//validate
$(()=>{
        $('#form-detail').validate({
            relus:{
                firstName:"required",
                lastName:"required",
                gender:"required",
                phone:{
                    required:true,
                    minlength:10
                }
            },
            messages:{
                firstName:"Vui lòng nhập tên của bạn",
                lastName:"Vui lòng nhập họ của bạn",
                gender:"Bạn chưa chọn giới tính của mình",
                phone:{
                    required:"Vui lòng nhập số điện thoại của bạn",
                    minlength:"Số điện thoại ít nhất 10 ký tự"
                }
            },
            onfocusout: function(element) {
                this.element(element);
            },
            errorClass:"errorClass"
        });

    $('#form-change-pw').validate({
        relus:{
            password:{
                required:true,
                minlength:6
            },
            password_new:{
                required:true,
                minlength:6
            },
            re_password_new:{
                required:true,
                minlength:6,
                equalTo:"#password_new"
            }
        },
        messages:{
            password:{
                required:"Vui lòng nhập mật khẩu của bạn",
                minlength:"Mật khẩu tối thiểu 6 ký tự"
            },
            password_new:{
                required:"Vui lòng nhập mật khẩu mới của bạn",
                minlength:"Mật khẩu dài tối thiểu 6 ký tự",
            },
            re_password_new:{
                required:"Vui lòng nhập lại mật khẩu mới của bạn",
                minlength:"Mật khẩu dài tối thiểu 6 ký tự",
                equalTo:"Mật khẩu nhập lại không đúng"
            },
        },
        onfocusout: function(element) {
            this.element(element);
        },
        errorClass:"errorClass"
    });


    $('#form-ratting').validate({
        relus:{
            star:"required",
            title:"required",
            content:{
                required:true,
                minlength:30
            }
        },
        messages:{
            star:"Bạn chưa chọn số sao đánh giá sản phẩm",
            title:"Vui lòng nhập tiêu đề bài đánh giá của bạn",
            content:{
                required:"Vui lòng nhập nội dung đánh giá của bạn",
                minlength:"Nội dung đánh giá dài ít nhất 30 ký tự"
            }
        },
        onfocusout: function(element) {
            this.element(element);
        },
        errorClass:"errorClass"
    });
})

//update detail info
$(()=>{
    $('a#profile-detail').click(function (){
        $('#form-detail').validate().resetForm();
        $('#form-detail small').html('');
        $.ajax({
            url:'/api/info/detail',
            type:'GET',
            contentType:'application/json',
            success:function (result){
                $('input#firstName').val(result.firstName);
                $('input#lastName').val(result.lastName);
                $('input#phone').val(result.phone);
                $('.input-gender label').each(function (index,element){
                    let val=$(element).children('input').val()
                    if(val*1===result.gender){
                        $(element).children('label').html('<i class="fas fa-dot-circle"></i>');
                        $(element).children('input').prop( "checked", true );
                    }
                })
                checkInput();
            },
            error:function (err){
                if(err.responseJSON.httpStatus!=null){
                    window.location.href="/login";
                }
            }
        });
    })

    $('button#btn-update').click(function (){
        let flag=$('#form-detail').valid();
        if(flag){
            let data={};
            let formData=$('#form-detail').serializeArray();
            $.each(formData,function (i,v){
                data[""+v.name+""]=v.value;
            })
            $.ajax({
                url:'/api/info/detail',
                type: 'PUT',
                dataType:'JSON',
                contentType: 'application/json',
                data:JSON.stringify(data),
                success:function (result){
                    $('div#info-detail').modal("hide");
                    $('div.alert-noti span').html(result.success);
                    $('div.alert-noti').show();
                    setTimeout(function (){
                        location.reload();
                    },2000);
                },error:function (err){
                    for (let item of err.responseJSON){
                        $('small#'+item.key).html(item.message);
                    }
                }
            })
        }
    })
})

//change password
$(()=>{
    $('button#btn-change-pw').click(function (){
        let flag=$('#form-change-pw').valid();
        if(flag){
            let data={};
            let formData=$('#form-change-pw').serializeArray();
            $.each(formData,function (i,v){
                data[""+v.name+""]=v.value;
            })
            $.ajax({
                url:'/api/info/password',
                type:'PUT',
                dataType: 'JSON',
                contentType:'application/json',
                data:JSON.stringify(data),
                success:function (result){
                    if(result.success!=null){
                        $('div#change-pw').modal("hide");
                        $('div.alert-noti span').html(result.success);
                        $('div.alert-noti').show();
                        setTimeout(function (){
                            window.location.href="/login";
                        },2000);
                    }else {
                        $('small#password').html(result.error);
                    }
                },
                error:function (err){
                    for (let item of err.responseJSON){
                        $('small#'+item.key).html(item.message);
                    }
                }
            })
        }
    })
})

//add comment
$(()=>{
    $('a.ratting').click(function (){
        let productId=$(this).attr("data-id");
        $('input#productId').val(productId);
    })

    $('button#btn-form-ratting').click(function (){
        let flag=$('#form-ratting').valid();
        if(flag){
            let data={};
            let formData=$('#form-ratting').serializeArray();
            $.each(formData,function (i,v){
                data[""+v.name+""]=v.value;
            })
            $.ajax({
                url:'/api/info/comment',
                type:'POST',
                dataType:'JSON',
                contentType:'application/json',
                data:JSON.stringify(data),
                success:function (result){
                    $('div#ratting-prod').modal("hide");
                    $('div.alert-noti span').html(result.success);
                    $('div.alert-noti').show();
                    let id=$('input#productId').val();
                    $('a#rate-'+id).remove();
                    $('.item-order-r').append('<a class="ratting">Đã đánh giá</a>');
                },
                error:function (err){
                    for (let item of err.responseJSON){
                        $('small#'+item.key).html(item.message);
                    }
                }
            })
        }
    })
})
function checkInput(){
    $('#form-detail .form-group input').each(function (index,element){
        let valInit=$(this).val();
        if(valInit!==''){
            $(this).parent().children('span').addClass("span-ac");
        }
    })
}