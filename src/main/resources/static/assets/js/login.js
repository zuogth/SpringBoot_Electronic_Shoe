$(() => {
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
    $('.input-gender>label>label').click(function(){
        $('.input-gender>label>label').html('<i class="far fa-circle"></i>');
        $(this).html('<i class="fas fa-dot-circle"></i>');
    })
    $('.form-group span').click(function(){
        $(this).parent().children('input').focus();
    })
})


$(()=>{
    $('#formLogin').validate({
        relus:{
            email:{
                required:true,
                email:true
            },
            password:{
                required:true,
                minlength:6
            }
        },
        messages:{
            email:{
                required:"Hãy nhập email của bạn",
                email:"Email bạn nhập không hợp lệ"
            },
            password:{
                required:"Hãy nhập mật khẩu của bạn",
                minlength:"Mật khẩu tối thiểu 6 ký tự"
            }
        },
        errorClass:"errorClass"
    });
    $('#formRegister').validate({
        relus:{
            firstname:"required",
            lastname:"required",
            email:{
                required:true,
                email:true
            },
            password:{
                required:true,
                minlength:6
            },
            re_password:{
                required:true,
                minlength:6,
                equalTo:"#password"
            },
            gender:"required"
        },
        messages:{
            firstname:"Hãy nhập tên của bạn",
            lastname:"Hãy nhập họ của bạn",
            email:{
                required:"Hãy nhập email của bạn",
                email:"Email bạn nhập không hợp lệ"
            },
            password:{
                required:"Hãy nhập mật khẩu của bạn",
                minlength:"Mật khẩu tối thiểu 6 ký tự"
            },
            re_password:{
                required:"Hãy nhập mật khẩu của bạn",
                minlength:"Mật khẩu tối thiểu 6 ký tự",
                equalTo:"Mật khẩu nhập lại không đúng"
            },
            gender:"Bạn chưa chọn giới tính"
        },
        onfocusout: function(element) {
            this.element(element);
        },
        errorClass:"errorClass"
    })
})