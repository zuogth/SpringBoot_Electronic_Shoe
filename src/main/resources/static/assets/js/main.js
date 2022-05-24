// loader
$(window).on("load",function(){
    setTimeout(function (){
        $(".preloader-cus").addClass("non-active-loader");
    },1000);

})
//Load cart from local storage
$(()=>{
    loadCartLocalMain();
})
//load cart from local storage
function loadCartLocalMain(){
    let list_cart=window.localStorage.getItem('list_cart');
    let req;
    if(list_cart){
        req={
            url:'/api/carts/fixed',
            type:'POST',
            dataType:'JSON',
            contentType:'application/json',
            data:list_cart
        }
    }else {
        req={
            url:'/api/carts',
            type:'GET',
            dataType:'JSON',
            contentType:'application/json',
        }
    }
    $.ajax(req).done(function (result){
        if(result.details==null){
            return;
        }
        let html = "";
        let total=0;
        let count = 0;
        for(let cart of result.details){
            html +=`<div class="card-fixed-prod" id="c-f-p-${cart.detail.id}">
                        <img src="${cart.detail.image}" alt="">
                        <div class="card-fixed-prod-info">
                            <p>${cart.detail.name}</p>
                            <p class="unit-price">${toMoney(cart.detail.price)}</p>
                            <div class="card-fixed-prod-info-size">
                                <span>Cỡ</span>
                                <span>${cart.detail.size}</span>
                            </div>
                            <div class="card-fixed-prod-info-quantity">
                                <span>Số lượng</span>
                                <span id="c-f-p-i-quantity-${cart.detail.id}">${cart.quantity}</span>
                            </div>
                        </div>
                    </div>`;
            total+=cart.detail.price*(100-cart.detail.discount)/100*cart.quantity;
            count+=cart.quantity*1;
        }
        $('div.card-fixed div p').html(count);
        $('div.card-fixed-info-header span').html('('+count+')');
        $('div.card-fixed-prods').html(html);
        $('div.card-fixed-total span').html(toMoney(total));
        $('div.card-fixed-total span').attr("data",total);
    }).fail(function(){
        alert("Error")
    })
}
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
        $(".result-search").html('');
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
        search(this,'result-search');
    })

    $('.form-search-error input').keyup(function (){
        //let key=ChangeToSlug($(this).val());
        search(this,'result-search-err');
    })
})

function search(elm,genClass){
    let data={};
    data['slug']=$(elm).val();
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
                                <img src="${prod.image}" alt="img">
                                <div class="info-result">
                                    <h5>${prod.product.name}</h5>
                                    <p>${toMoney(prod.product.price)}</p>
                                </div>
                                <a href="/products/${prod.product.slug}/${prod.color.slug}">Xem chi tiết</a>
                            </div>`;
            }
            $('.'+genClass).html(html);
        },error:function (){
            alert("Error");
        }
    })
}

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
    $('.card-fixed-info').hide();
})


function toMoney(totalprice){
    return totalprice.toLocaleString('it-IT', {
        style: 'currency',
        currency: 'VND'
    });
}

function showCard(){
    $('.card-fixed-info').toggle(300);
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