
function closeItem(element,id){
    $(element).children().children().attr("class","spinner-border spinner-border-sm");
    setTimeout(function (){
        let data={};
        data["productDetailId"]=id;
        let flagUser=$('.options-user').attr("flag");
        if(flagUser==="true"){
            $.ajax({
                url:'/api/cart/delete',
                type: 'DELETE',
                contentType: 'application/json',
                data:JSON.stringify(data),
                success:function (){

                },error:function (){
                    alert("Error");
                }

            })
        }
        $(element).parents().eq(1).remove();
        updateLocal();
        let list_cart;
        list_cart=JSON.parse(window.localStorage.getItem('list_cart'));
        if(list_cart==null){
            $('.content-cart').html('<h5>Giỏ hàng của bạn trống</h5>')
            localStorage.removeItem('list_cart');
        }else {
            localStorage.setItem('list_cart', JSON.stringify(list_cart));
        }
        totalPrice();
    },1000)
}
function totalPrice(){
    let totalprice=0;
    let totalcount=0;
    $('.info-prod').each(function(index,element){
        let price=$(element).children('.name-prod').children('#price').attr("price");
        let count=$(element).children('.quantity').children('.quantity-l').children().val();
        totalcount+=count*1;
        totalprice+=price*count;
    })
    $('.total-prod').children().eq(0).html(totalcount+' Sản phẩm');
    $('.total-prod').children().eq(1).attr("totalprice",totalprice);
    $('.total-prod').children().eq(1).html(toMoney(totalprice));
    let ship=$('.ship-prod').children().eq(1).attr("pay-ship")*1;
    $('.total-price-child').children().eq(1).html(toMoney(totalprice+ship));
}

function minus(element,id){
    let count=$(element).parents().eq(1).children('.quantity-l').children().val();
    if(count<=1){
        return;
    }
    count-=1;
    $(element).parents().eq(1).children('.quantity-l').children().val(count);
    updateCartLocal(count,id);
    totalPrice();
}

function plus(element,id,countMax){
    let count=$(element).parents().eq(1).children('.quantity-l').children().val();
    if(count>=countMax){
        return;
    }
    count-=-1;
    $(element).parents().eq(1).children('.quantity-l').children().val(count);
    updateCartLocal(count,id);
    totalPrice();
}


function updateCartLocal(count,id){
    updateLocal();
    let data={};
    data["productDetailId"]=id;
    data["quantity"]=count;
    let flagUser=$('.options-user').attr("flag");
    if(flagUser==="true"){
        $.ajax({
            url:'/api/cart/update',
            type: 'PUT',
            contentType: 'application/json',
            data:JSON.stringify(data),
            success:function (){

            },error:function (result){
                if(result.responseJSON.httpStatus==="BAD_REQUEST"){
                    window.location.href="/login";
                }
            }

        })
    }
}
function updateLocal(){
    localStorage.removeItem("list_cart");
    let list_cart=[];
    $('.info-prod').each(function (i,e){
        let price=$(e).children('.name-prod').children().eq(1).attr("price");
        let quantity=$(e).children(".quantity").children(".quantity-l").children().val();
        let id=$(e).children(".quantity").children(".quantity-l").children().attr("data-id");
        let data={};
        data['productDetailId']=id;
        data['quantity']=quantity;
        if(price!=null){
            data['price']=price;
        }
        list_cart.push(data);
    })
    if(list_cart.length==0){
        return;
    }
    localStorage.setItem('list_cart', JSON.stringify(list_cart));
}
function checkCart(){
    let flag=$('div.options-user').attr("flag");
    if(flag==="false"){
        window.location.href="/delivery";
    }else {
        let flagC=false;
        let data=[];
        $('.quantity .quantity-l input').each(function (i,e){
            let dt={};
            dt["productDetailId"]=$(e).attr("data-id");
            dt["quantity"]=$(e).val();
            if(dt["quantity"]==0){
                $(e).parents().eq(3).addClass("errorCount");
                flagC=true;
            }
            data.push(dt);
        })
        if(flagC){
            return;
        }
        $.ajax({
            url:'/api/cart/check',
            type:'POST',
            dataType: 'JSON',
            contentType:'application/json',
            data:JSON.stringify(data),
            success:function (result){
                let flagC=false;
                for (let item of result.check){
                    if(item.outQuantity){
                        flagC=true;
                        $('div#quantity-'+item.sizeQuantity.productDetailId+' input').val(item.sizeQuantity.quantity);
                        $('#plus-'+item.sizeQuantity.productDetailId).html(`<a id="plus" onclick="plus(this,${item.sizeQuantity.productDetailId},${item.sizeQuantity.quantity})"><i class="fas fa-plus"></i></a>`);
                    }
                }
                updateLocal();
                if(!flagC){
                    window.location.href="/delivery";
                }
                totalPrice();
            },
            error:function (){
                alert('Error')
            }
        })
    }
}
//Load cart from local storage
$(()=>{
    let list_cart=window.localStorage.getItem('list_cart');
    let req;
    if(list_cart){
        req={
            url:'/api/cart/load',
            type:'POST',
            dataType:'JSON',
            contentType:'application/json',
            data:list_cart
        }
    }else {
        req={
            url:'/api/cart/load',
            type:'GET',
            dataType:'JSON',
            contentType:'application/json',
        }
    }
        $.ajax(req).done(function (result){
            if(result.details==null){
                return;
            }
            let html='<div class="prods-cart">';
            let index=0;
            for(let cart of result.details) {
                html += `<div class="prod-cart">
                      <div class="prod-img">
                        <a href="/products/${cart.detail.product.slug+'/'+cart.detail.color.slug}"><img src="${cart.detail.image}" alt=""></a>
                      </div>
                      <div class="info-prod">
                        <div class="name-prod">
                          <span>${cart.detail.product.name}</span>
                          <span id="price" price="${cart.detail.product.price}">${toMoney(cart.detail.product.price)}</span>
                        </div>
                        <p>${cart.detail.color.name}</p>
                        <span>Cỡ: </span><span>${cart.detail.size.name}</span>
                        <div class="quantity">
                          <div class="change-quantity">
                            <a id="minus" onclick="minus(this,${cart.detail.id})"><i class="fas fa-minus"></i></a>
                          </div>
                          <div class="quantity-l" id="quantity-${cart.detail.id}">
                            <input type="number" name="quantity" id="quantity" value="${cart.quantity}" data-id="${cart.detail.id}" readonly>
                          </div>
                          <div class="change-quantity" id="plus-${cart.detail.id}">
                            <a id="plus" onclick="plus(this,${cart.detail.id},${result.quantities[index++].quantity})"><i class="fas fa-plus"></i></a>
                          </div>
                        </div>
                        <div class="close-item" onclick="closeItem(this,${cart.detail.id})">
                          <a><i class="fas fa-times"></i></a>
                        </div>
                      </div>
                    </div>`;
            }
            html+=`</div>
                      <div class="total-price">
                        <div class="order-info">
                          <h3>Đơn hàng</h3>
                          <div class="total-prod">
                            <span></span>
                            <span totalprice="">7.000.000 đ</span>
                          </div>
                          <div class="ship-prod">
                            <span>Giao hàng</span>
                            <span pay-ship="0">Miễn phí</span>
                          </div>
                          <div class="total-price-child">
                            <h6>Tổng</h6>
                            <span>7.000.000 đ</span>
                          </div>
                        </div>
                        <div class="code-sale">
                          <input type="text" name="code" id="code" placeholder="Nhập mã khuyễn mãi">
                          <a href=""><i class="fal fa-plus"></i></a>
                        </div>
                        <div class="pay-cart">
                          <div class="pay-cart-btn">
                            <a onclick="checkCart()">Thanh toán <i class="far fa-long-arrow-right"></i></a>
                          </div>
                          <div class="pay-cart-b">
                            <ul>
                              <li><i class="fal fa-shipping-timed"></i><p>Chậm chễ trong việc giao hàng</p></li>
                              <li><i class="fas fa-undo-alt"></i><p>Trả hàng dễ dàng</p></li>
                            </ul>
                          </div>
                        </div>
                      </div>`;
            if(result.details.length==0){
                html=`<h5>Giỏ hàng của bạn trống</h5>`;
            }
            $('.content-cart').html(html).ready(function (){
                totalPrice();
            });
            updateLocal();
        }).fail(function(){
            alert("Error")
        })
})

function toMoney(totalprice){
    return totalprice.toLocaleString('it-IT', {
        style: 'currency',
        currency: 'VND'
    });
}