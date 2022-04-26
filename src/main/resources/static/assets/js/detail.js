$(document).ready(function () {
    $(window).scroll(function () {
        var e = $(window).scrollTop();
        let h=$('#flag').offset().top;
        let h1=$('.options-detail').height();
        // if(e<100){
        //     $('.margin-top-prod').css("margin-top",0)
        // }else if(e-100>h-h1){
        //     $('.margin-top-prod').css("margin-top",h-h1)
        // }else{
        //     $('.margin-top-prod').css("margin-top",e-100)
        // }

        if(e>100 && e-100<h-h1){
            $('.options-parent').addClass("top-fixed");
            $('.margin-top-prod').css("margin-top",0)
        }else if(e-100>h-h1){
            $('.options-parent').removeClass("top-fixed");
            $('.margin-top-prod').css("margin-top",h-h1)
        }else{
            $('.options-parent').removeClass("top-fixed");
        }
        
    });
    
    star=$('.star-prod').attr('data-star');
    $('.star-prod').rateYo({
        rating: star,
        ratedFill: "#101012",
        readOnly:true,
        starWidth: "12px"
    });
    $('.rateYo-cus').rateYo({
        rating: star,
        ratedFill: "#101012",
        readOnly:true,
        starWidth: "16px"
    });
    $('.star-item').each(function(i,e){
        star=$(e).attr('data-star');
        $(e).rateYo({
            rating: star,
            ratedFill: "#101012",
            readOnly:true,
            starWidth: "12px"
        });
    })
    $('.rateYo').each(function(index,element){
        star=$(element).attr('data-star');
        $(element).rateYo({
            rating: star,
            ratedFill: "#101012",
            readOnly:true,
            starWidth: "12px"
        });
    });
    $('.filter-size label').click(function(){
        $('.filter-size label').removeClass("size-active");
        $(this).addClass("size-active");
    });



    $('#formSize label').click(function (){
        let count=$(this).attr("count");
        $('#errorSize').html('Còn '+count+' sản phẩm');
    })
})

$(()=>{
    $('#formSize label').each(function (index,element){
        let id=$(element).attr("data-id");
        let count=$(element).attr("count");
        let list_cart=[];
        list_cart=JSON.parse(window.localStorage.getItem('list_cart'));
        if(list_cart!=null){
            for (let item of list_cart){
                if(item.productDetailId==id){
                    count-=item.quantity;
                }
            }
        }
        count=count<0?0:count;
        $(element).attr("count",count);
    })
})

$(window).on("load",function (){
    $('.prod-popular').slick({
        slidesToShow: 4,
        slidesToScroll: 4,
        infinite: false,
        draggable: true,
        arrows: true,
        Swipe: true,
        responsive: [{
            breakpoint: 1300,
            settings: {
                slidesToShow: 3,
                slidesToScroll: 3
            }
        },
            {
                breakpoint: 992,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 2
                }
            }
        ],
        prevArrow: '<a class="btn-slick-cus btn-slick-pre"><i class="fal fa-long-arrow-left"></i></a>',
        nextArrow: '<a class="btn-slick-cus btn-slick-next"><i class="fal fa-long-arrow-right"></i></a>'
    });
    $('.images-prod').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        infinite: true,
        draggable: true,
        arrows: true,
        Swipe: true,
        dots: true,
        customPaging: function(slider, i) {
            // this example would render "tabs" with titles
            return '<span class="dot"><img src="'+$(slider.$slides[i]).attr("data-img")+'"></span>';
        },
        appendDots: '.dotClass',
        prevArrow: '<a class="btn-slick-cus btn-slick-pre"><i class="fal fa-long-arrow-left"></i></a>',
        nextArrow: '<a class="btn-slick-cus btn-slick-next"><i class="fal fa-long-arrow-right"></i></a>'
    });
})
// menu-prod
$(document).ready(function(){
    $(window).scroll(function(){
        $('.menu-prod ul li').removeClass("menu-prod-ac");
        var scrolls=$(window).scrollTop();
        let flag=$('#flag').offset().top;
        for (let index = 1; index <=3; index++) {
            var url='#section'+index;
            var scroll_sec=$(url).offset().top-100;
            if(index!=3){
                var i=index+1;
                var urln='#section'+i;
                var scroll_sec_n=$(urln).offset().top-100;
                if(scroll_sec>scrolls && index==1){
                    $('.menu-prod ul li:eq(0)').addClass("menu-prod-ac");
                }else if(scroll_sec<=scrolls && scrolls<scroll_sec_n){
                    $('.menu-prod ul li:eq('+(index)+')').addClass("menu-prod-ac");
                }
            }else{
                if(scroll_sec<=scrolls){
                    $('.menu-prod ul li:eq('+(index)+')').addClass("menu-prod-ac");
                }
            }
        }
        if(scrolls>flag-100){
            $('.menu-prod').removeClass('sticky-top');
        }else{
            $('.menu-prod').addClass('sticky-top');
        }
    })
    $('.menu-prod ul li').click(function(){
        var href=$(this).children().attr("href-link");
        if(href=='#section0'){
            $('body,html').animate({
                scrollTop:0
            });
        }
        $('body,html').animate({
            scrollTop:$(href).offset().top-60
        });
    })
    $('.star span').click(function(){
        $('body,html').animate({
            scrollTop:$('#section3').offset().top-60
        });
    })
})

function addCart(event){
    let data=$('#formSize').serializeArray();
    if(data.length===0){
        $('#errorSize').html('Bạn chưa chọn size');
    }else {
        let id=data[0].value;
        let count=$('#formSize label#size-'+id).attr("count");
        if(count>0){
            $(event).children().attr("class","spinner-border spinner-border-sm");
            setTimeout(function (){
                let flagUser=$('.options-user').attr("flag");
                let price=$('p#price').attr("price");
                if(flagUser==="true"){
                    let dataCart={};
                    dataCart["productDetailId"]=id;
                    dataCart["price"]=price;
                    $.ajax({
                        url: "/api/cart",
                        type: "POST",
                        contentType: 'application/json',
                        data:JSON.stringify(dataCart),
                        success:function (result){

                        },error:function (result){
                            if(result.responseJSON.httpStatus==="BAD_REQUEST"){
                                window.location.href="/login";
                            }
                        }
                    })
                }
                let list_cart=[];
                let flag=false;
                list_cart=JSON.parse(window.localStorage.getItem('list_cart'));
                if(list_cart!=null){
                    for (let item of list_cart){
                        if(item.productDetailId==id){
                            item.quantity-=-1;
                            flag=true;
                            item.price=price;
                        }
                    }
                    if(!flag){
                        let data={};
                        data['productDetailId']=id;
                        data['quantity']=1;
                        data['price']=price;
                        list_cart.push(data);
                    }
                }else{
                    list_cart=[];
                    let data={};
                    data['productDetailId']=id;
                    data['quantity']=1;
                    data['price']=price;
                    list_cart.push(data);
                }
                localStorage.setItem('list_cart', JSON.stringify(list_cart));
                $(event).children().attr("class","fas fa-check");
                $('#formSize label#size-'+id).attr("count",count*1-1);
                $('#errorSize').html('Còn '+(count*1-1)+' sản phẩm');
            },2000)

        }else {
            $('#errorSize').html('Sản phẩm đã hết hàng');
        }
    }

}

function avgStar(cmt){
    let sum=0;
    cmt.forEach((e)=>{
        sum-=-e.star;
    })
    return sum/cmt.size();
}