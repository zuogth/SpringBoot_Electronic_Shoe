
function loadImage(){
    $('.product').each(function(index,element){
        let urlP=$(element).children().children('.same-prods').children().eq(0).children().children().attr("src");
        let href=$(element).children().children('.same-prods').children().eq(0).children().attr("href");
        $(element).children().children('.same-prods').children().eq(0).addClass('same-prods-item-ac');
        $(element).children().children().eq(0).attr("href",href);
        $(element).children().children().eq(0).children().attr("src",urlP);
        $(element).children().children().eq(0).children().attr("data-img",urlP);
    })
}

function loadSameProd(element){
    $(element).children().addClass("product-hover-ac");
    $(element).children().children().eq(1).addClass("same-prods-ac");
    let test=$(element).children().children().eq(1).children().length;
    if(test>=5){
        $(element).children().children().eq(1).slick({
            slidesToShow: 5,
            slidesToScroll: 5,
            infinite: false,
            draggable: true,
            arrows: false,
            Swipe: true
        });
        $(element).children().children().eq(1).attr("flag",true);
    }
}

function unLoadSameProd(element){
    if($(element).children().children().eq(1).attr("flag")){
        $(element).children().eq(0).children().eq(1).slick("unslick");
    }
    $('.product-hover').removeClass("product-hover-ac");
    $(element).children().eq(0).children().eq(1).removeClass("same-prods-ac");
    let url=$(element).children().children().eq(0).children().attr("data-img");
    $(element).children().children().eq(0).children().attr("src",url);
}


function loadImgSame(element){
    let url=$(element).attr("src");
    let id=$(element).attr("class");
    $('.product img#'+id).attr("src",url);
}

$(()=>{
    let flag=true;
    $('.filter-child').mouseenter(function(){
        flag=true;
    })
    $('.filter-child').mouseleave(function(){
        flag=false;
    })
    $('.filter ul li').mouseleave(function(){
        flag=false;
    })
    $(window).click(function(){
        if(!flag){
            $('.filter-child').hide();
            flag=true;
        }
    })
    loadImage();
    $('.filter ul li').click(function(){
        let check=$(this).attr("flag");
        let id=$(this).attr("data-id");
        if(check=="true"){
            $('div.filter-'+id).hide();
            $(this).attr("flag",false);
        }else{
            $('div.filter-child').hide();
            $('div.filter-'+id).show();
            $('.filter ul li').attr("flag",false);
            $(this).attr("flag",true);
            flag=true;
        }
    });
    $('.filter-sizeId label').click(function(){
        $('.filter-sizeId label').removeClass("sizeId-active");
        $(this).addClass("sizeId-active");
    })
    $('.filter-colorId label').click(function(){
        $('.filter-colorId label').removeClass("colorId-active");
        $(this).addClass("colorId-active");
    })
    $('.filter-child>label').click(function(){
        $(this).parent().children('label').children('label').html('<i class="far fa-circle"></i>');
        $(this).children('label').html('<i class="fas fa-dot-circle"></i>');
    })
    $('.filter-child input').change(function(){
        searchProduct(this);
    })

    $('.filter-item-child i').click(function(){
        let input_name=$(this).parent().attr("id");
        let id=input_name+'-default';
        $('.filter-'+input_name+' input').prop("checked",false);
        $('input#'+id).prop("checked",true);
        $(this).parent().hide();
        $('.filter-'+input_name+' label').removeClass(input_name+"-active");
        $('.filter ul li').attr("flag",false);
        if(input_name=="sort" || input_name=="price"){
            $('.filter-'+input_name+'>label>label').html('<i class="far fa-circle"></i>');
        }
        $('.filter-child').hide();
        searchProduct(this);
    })

    $(window).scroll(function () {
        let e = $(window).scrollTop();
        let scrollForm=$('#flagFilter').offset().top;
        if (e > scrollForm-50) {
            $('.filter').addClass("active");
        } else {
            $('.filter').removeClass("active");
        }
    });

})

function searchProduct(element){
    let data={};
    let dataForm=$('#formFilter').serializeArray();

    $.each(dataForm,function(i,v){
        data[""+v.name+""]=v.value;
    });
    let name=$(element).attr("name");
    $('.filter-items div#'+name).show();
    $.ajax({
        url:'/api/filter',
        type:'POST',
        contentType:'application/json',
        dataType:'JSON',
        data:JSON.stringify(data),
        success:function (result){
            let html='';
            let flag=false;
            if(result.products!=null){
                if(result.products.length===0){
                    html+=`<h5 id="noti-zero">Không có sản phẩm nào mà bạn muốn tìm ! </h5>`;
                    flag=true;
                }else {
                    for (let prod of result.products){
                        html+=`<div class="product"  onmouseenter="loadSameProd(this)" onmouseleave="unLoadSameProd(this)">
                        <div class="product-hover">
                            <a href="">
                                <img id="product-${prod.id}" src="" data-img="" alt="">
                            </a>
                            <div class="same-prods">`;
                        for(let detail of result.details){
                            if(detail.product.id===prod.id){
                                html+=`<div class="same-prods-item">
                                        <a href="/product/${detail.id}"><img onmousemove="loadImgSame(this)" class="product-${prod.id}" src="${detail.image}" alt=""></a>
                                    </div>`;
                            }
                        }

                        html+=`</div>
                            <div class="info-prod">
                                <h6>${prod.name}</h6>
                                <span>${toMoney(prod.price)}</span>
                                <p>Mới</p>
                            </div>
                        </div>
                    </div>`;
                    }
                    if(result.products.length%4===1){
                        html+=`<div style="height: 500px"></div>`;
                    }
                }
            }else{
                if(result.details.length===0){
                    html+=`<h5 id="noti-zero">Không có sản phẩm nào mà bạn muốn tìm ! </h5>`;
                    flag=true;
                }else {
                    for (let detail of result.details){
                        html+=`<div class="product"  onmouseenter="loadSameProd(this)" onmouseleave="unLoadSameProd(this)">
                        <div class="product-hover">
                            <a href="">
                                <img id="product-${detail.product.id}" src="${detail.image}"  data-img="${detail.image}" alt="">
                            </a>
                            <div class="same-prods"></div>
                            <div class="info-prod">
                                <h6>${detail.product.name}</h6>
                                <span>${toMoney(detail.product.price)}</span>
                                <p>Mới</p>
                            </div>
                        </div>
                    </div>`;
                    }
                    if(result.details.length%4===1){
                        html+=`<div style="height: 500px"></div>`;
                    }
                }
            }
            if(flag){
                $('h5#noti-zero').remove();
                $('.list-product').hide();
                $('.cate-main').append(html);
            }else {
                $('h5#noti-zero').remove();
                $('.list-product').show().html(html);
            }
            loadImage();
        },
        error:function (){
            console.log('Error!');
        }
    })
}