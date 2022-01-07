// Lên đầu trang

$(document).ready(function () {
    $(window).scroll(function () {
        var e = $(window).scrollTop();
        if (e > 1000) {
            $('#myBtnTop').show();
        } else {
            $('#myBtnTop').hide();
        }
        // $('nav.nav-options div').hide();
    });
    $('#myBtnTop').click(function () {
        $('body,html').animate({
            scrollTop: 0
        });
    });
})

// search vs nav-resp

$(document).ready(function () {
    $("#h-btn-search").click(function () {
        $("#h-search").addClass("search-nav-active");
        $("body").addClass("scroll-hand");
    });
    $(".h-btn-close-search").click(function () {
        $("#h-search").removeClass("search-nav-active");
        $("body").removeClass("scroll-hand");
    });
    $(".btn-toggler-nav").click(function () {
        $("#nav-resp").addClass("search-nav-active");
        $("body").addClass("scroll-hand");
    });
    $(".h-btn-close-nav").click(function () {
        $("#nav-resp").removeClass("search-nav-active");
        $("body").removeClass("scroll-hand");
    });
})

$(()=>{
    $('.h-search-input input').keyup(function (){
        //let key=ChangeToSlug($(this).val());
        let data={};
        data['slug']=$(this).val();
        $.ajax({
            url:'/api/search',
            type:'POST',
            dataType:'JSON',
            contentType:'application/json',
            data:JSON.stringify(data),
            success:function (result){
                let html='';
                for (let prod of result){
                    html+=`<div class="result-item">
                                <img src="/assets/img/product/${prod.image}" alt="img">
                                <div class="info-result">
                                    <h5>${prod.product.name}</h5>
                                    <p>${toMoney(prod.product.price)}</p>
                                </div>
                                <a href="/product/${prod.id}">Xem chi tiết</a>
                            </div>`;
                }
                $('.result-search').html(html);
            },error:function (){
                alert("Error");
            }
        })
    })
})

$(()=>{
    $('li#icon-user').mouseenter(function(){
        $('.options-user').show();
    })
    $('.options-header').mouseleave(function(){
        $('.options-user').hide();
    })
    $('.close-alert').click(function(){
        $(this).parent().children('span').html('');
        $(this).parent().hide();
    })
    let htmlAlert=$('.alert-noti').children('span').html();
    if(htmlAlert!=""){
        $('.alert-noti').show();
    }
})

function toMoney(totalprice){
    return totalprice.toLocaleString('it-IT', {
        style: 'currency',
        currency: 'VND'
    });
}

function ChangeToSlug(text) {
    var slug;

    //Đổi chữ hoa thành chữ thường
    slug = text.toLowerCase();

    //Đổi ký tự có dấu thành không dấu
    slug = slug.replace(/á|à|ả|ạ|ã|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ/gi, 'a');
    slug = slug.replace(/é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ/gi, 'e');
    slug = slug.replace(/i|í|ì|ỉ|ĩ|ị/gi, 'i');
    slug = slug.replace(/ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ/gi, 'o');
    slug = slug.replace(/ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự/gi, 'u');
    slug = slug.replace(/ý|ỳ|ỷ|ỹ|ỵ/gi, 'y');
    slug = slug.replace(/đ/gi, 'd');
    //Xóa các ký tự đặt biệt
    slug = slug.replace(/\`|\~|\!|\@|\#|\||\$|\%|\^|\&|\*|\(|\)|\+|\=|\,|\.|\/|\?|\>|\<|\'|\"|\:|\;|_/gi, '');
    //Đổi khoảng trắng thành ký tự gạch ngang
    slug = slug.replace(/ /gi, "-");
    //Đổi nhiều ký tự gạch ngang liên tiếp thành 1 ký tự gạch ngang
    //Phòng trường hợp người nhập vào quá nhiều ký tự trắng
    slug = slug.replace(/\-\-\-\-\-/gi, '-');
    slug = slug.replace(/\-\-\-\-/gi, '-');
    slug = slug.replace(/\-\-\-/gi, '-');
    slug = slug.replace(/\-\-/gi, '-');
    //Xóa các ký tự gạch ngang ở đầu và cuối
    slug = '@' + slug + '@';
    slug = slug.replace(/\@\-|\-\@|\@/gi, '');
    //In slug ra textbox có id “slug”
    return slug;
}