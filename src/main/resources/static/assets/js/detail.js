$(document).ready(function () {
    $(window).scroll(function () {
        var e = $(window).scrollTop();
        let h=$('#flag').offset().top;
        let h1=$('.options-detail').height();
        if(e<100){
            $('.margin-top-prod').css("margin-top",0)
        }else if(e-100>h-h1){
            $('.margin-top-prod').css("margin-top",h-h1)
        }else{
            $('.margin-top-prod').css("margin-top",e-100)
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
    star=$('.star-item').attr('data-star');
    $('.star-item').rateYo({
        rating: star,
        ratedFill: "#101012",
        readOnly:true,
        starWidth: "12px"
    });
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

    $('#formSize label').click(function (){
        let count=$(this).attr("count");
        $('#errorSize').html('Còn '+count+' sản phẩm');
    })
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

//chua dung
function loadProductDetail(id){
    $.ajax({
        url:'/api/product/'+id,
        type:'GET',
        contentType:'application/json',
        success:function (result){
            let html=`<div class="content-detail">
                          <div class="images-prod-parent">
                            <div class="images-prod">`;
            for(let image of result.images){
                html+=`<div class="image-item"
                             data-img="/assets/img/product/prod-detail/pureboots_21/${image}">
                          <img src="/assets/img/product/prod-detail/pureboots_21/${image}" alt="">
                        </div>`;
            }
        html+=`</div>
        <div class="dotClass"></div>
        <div class="url-page-cus">
          <ul>
            <li><a href="/">Trang chủ</a></li> /
            <li><a href="/brand/${result.prod.product.brand.id}">Giày ${result.prod.product.brand.name}</a></li> /
            <li>GIÀY ${result.prod.product.name}</li>
          </ul>
        </div>
      </div>
      <div class="same-prod-detail">
        <div class="same-prod-detail-l">
          <h6>Các màu có sẵn khác</h6>
          <p>Có ${result.sameProd.length} màu</p>
        </div>
        <div class="same-prod-detail-r">`;
            for(let same of result.sameProd){
                html+=`<div class="${same.color.id==result.prod.color.id?'same-prod-item same-prod-item-ac':'same-prod-item'}">
                          <a onclick="loadProductDetail(${same.id})"><img src="/assets/img/product/${same.thumb}" alt=""></a>
                        </div>`;
            }
        html+=`</div>
      </div>
      <div class="menu-prod sticky-top">
        <ul>
          <li class="menu-prod-ac">
            <a href-link="#section0">Bộ sưu tập</a>
          </li>
          <li>
            <a href-link="#section1">Mô tả</a>
          </li>
          <li>
            <a href-link="#section2">Thông tin chi tiết</a>
          </li>
          <li>
            <a href-link="#section3">Nhận xét</a>
          </li>
        </ul>
      </div>
      <div class="content-prod-detail">
        <div id="section1">
          <div class="description-prod">
            <h4>GIÀY PUREBOOST 21</h4>
            <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Laborum alias ipsa ipsum ad
              magni
              dolore nulla enim ullam esse numquam, veritatis nobis corrupti excepturi itaque commodi
              non
              voluptatum sapiente fugit!</p>
          </div>
          <div class="image-prod">
            <img src="/assets/img/product/${result.prod.thumb}" alt="">
          </div>
        </div>
        <div id="section2">
          <h4>Thông số</h4>
          <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Amet pariatur reprehenderit, error
            eveniet non repellat rem accusamus est maxime ut. Nulla earum vel sed veniam, cumque esse
            nihil.
            Minus, eveniet?</p>
        </div>
        <div id="section3">
          <h4>Nhận xét</h4>
          <div class="star-cus">
            <div class="star-cus-l">
              <h5>5/<span>0</span></h5>
            </div>
            <div class="star-cus-r">
              <h6>Đánh giá</h6>
              <div class="rateYo-cus" data-star="4"></div>
            </div>
          </div>
          <hr>
          <div class="comments">
            <div class="comments-item">
              <h6>Sản phẩm tốt</h6>
              <div class="star-item" data-star="4"></div>
              <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugit optio magnam officia rerum
                eaque modi quidem. Tempora animi recusandae, nemo laborum mollitia exercitationem
                nostrum facere, eos id asperiores labore est?</p>
              <span>2021-11-20</span>
              <span> / </span>
              <span>Dương Tuấn Hiếu</span>

            </div>
            <div class="comments-item">
              <h6>Sản phẩm tốt</h6>
              <div class="star-item" data-star="5"></div>
              <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugit optio magnam officia rerum
                eaque modi quidem. Tempora animi recusandae, nemo laborum mollitia exercitationem
                nostrum facere, eos id asperiores labore est?</p>
              <span>2021-11-20</span>

            </div>
            <div class="comments-item">
              <h6>Sản phẩm tốt</h6>
              <div class="star-item" data-star="4.5"></div>
              <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugit optio magnam officia rerum
                eaque modi quidem. Tempora animi recusandae, nemo laborum mollitia exercitationem
                nostrum facere, eos id asperiores labore est?</p>
              <span>2021-11-20</span>
            </div>
          </div>
        </div>
      </div>
      <div id="flag"></div>
      <div class="prod-popular-pr">
        <h3>Sản phẩm mua nhiều</h3>
        <div class="prod-popular">
          <div class="item item-prod">
            <a href=""><img src="/assets/img/sst-aec-superearth-sw.jpg" alt=""></a>
            <span class="price">2.300.000 đ</span>
            <h6>Giày Superstar</h6>
            <div class="rateYo" data-star="4.8"></div>
          </div>
          <div class="item item-prod">
            <a href=""><img src="/assets/img/sst-aec-superearth-sw.jpg" alt=""></a>
            <span class="price">2.300.000 đ</span>
            <h6>Giày Superstar</h6>
            <div class="rateYo" data-star="4"></div>
          </div>
          <div class="item item-prod">
            <a href=""><img src="/assets/img/sst-aec-superearth-sw.jpg" alt=""></a>
            <span class="price">2.300.000 đ</span>
            <h6>Giày Superstar</h6>
            <div class="rateYo" data-star="3"></div>
          </div>
          <div class="item item-prod">
            <a href=""><img src="/assets/img/sst-aec-superearth-sw.jpg" alt=""></a>
            <span class="price">2.300.000 đ</span>
            <h6>Giày Superstar</h6>
            <div class="rateYo" data-star="5"></div>
          </div>
          <div class="item item-prod">
            <a href=""><img src="/assets/img/sst-aec-superearth-sw.jpg" alt=""></a>
            <span class="price">2.300.000 đ</span>
            <h6>Giày Superstar</h6>
            <div class="rateYo" data-star="4.5"></div>
          </div>
          <div class="item item-prod">
            <a href=""><img src="/assets/img/sst-aec-superearth-sw.jpg" alt=""></a>
            <span class="price">2.300.000 đ</span>
            <h6>Giày Superstar</h6>
            <div class="rateYo" data-star="4"></div>
          </div>
        </div>
      </div>
    </div>
    <div class="options-parent">
      <div class="margin-top-prod"></div>
      <div class="options-detail">
        <div class="star">
          <div class="star-prod" data-star="4.8"></div>
          <span>(10)</span>
        </div>
        <h2>Giày ${result.prod.product.name}</h2>
        <p>${toMoney(result.prod.product.price)}</p>
        <p>CHỌN SIZE</p>
        <div class="filter-size">`;
            for(let size of result.sizes){
                html+=`<label for="size-${size}">${size}</label>
                        <input type="radio" name="size" id="size-${size}" hidden>`;
            }
        html+=`</div>
        <div class="explain-size">
          <a href=""><i class="fas fa-ruler-horizontal"></i>Bảng kích cỡ</a>
        </div>
        <div class="buy-prod">
          <a th:href="@{/cart}">Thêm vào giỏ hàng <i class="far fa-long-arrow-right"></i></a>
        </div>
        <ul>
          <li><i class="fal fa-shipping-fast"></i>
            <p>Giao hàng miễn phí</p>
          </li>
          <li><i class="fas fa-exchange-alt"></i>
            <p>Không đúng kích cỡ hoặc màu sắc? Vui lòng truy cập trang Trả lại hàng & Hoàn tiền của
              chúng
              tôi để biết chi tiết</p>
          </li>
          <li><i class="fal fa-exclamation-triangle"></i>
            <p>Do ảnh hưởng COVID-19, thời gian giao hàng sẽ dài hơn dự kiến</p>
          </li>
        </ul>
      </div>
    </div>`;
            $('.detail-product').html(html);
            $(document).ready(function(){
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
                })
        })

        },error:function (){
            console.log('error');
        }
    })
}

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
                let flagUser=$('.options-user').children().eq(0).attr("flag");
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
                            $('#formSize label#size-'+id).attr("count",count*1-1);
                        },error:function (){
                            alert("Error!");
                        }
                    })
                }
                let list_cart=[];
                let flag=false;
                list_cart=JSON.parse(window.localStorage.getItem('list_cart'));
                if(list_cart!=null){
                    for (let item of list_cart){
                        if(item.productDetailId==id){
                            item.quantity+=1;
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
            },2000)
        }else {
            $('#errorSize').html('Sản phẩm đã hết hàng');
        }
    }

}