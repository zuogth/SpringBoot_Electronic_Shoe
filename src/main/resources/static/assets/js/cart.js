
function closeItem(element,id){
    $(element).children().children().attr("class","spinner-border spinner-border-sm");
    setTimeout(function (){
        let list_cart=[];
        list_cart=JSON.parse(window.localStorage.getItem('list_cart'));
        for (let item of list_cart) {
            if(item.productDetailId===id.toString()){
                list_cart=list_cart.filter(i=>i!=item);
            }
        }
        localStorage.setItem('list_cart', JSON.stringify(list_cart));
        let data={};
        data["productDetailId"]=id;
        let flagUser=$('.options-user').children().eq(0).attr("flag");
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
        totalPrice();
    },1000)
}
function totalPrice(){
    let totalprice=0;
    let totalcount=0;
    $('.info-prod').each(function(index,element){
        let price=$(element).children('.name-prod').children('#price').attr("price");
        let count=$(element).children('.quantity').children('.quantity-l').children('#quantity').val();
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
    if(count==1){
        return;
    }
    $(element).parents().eq(1).children('.quantity-l').children().val(count*1-1);
    updateCartLocal(0,id)
    totalPrice();
}

function plus(element,id,countMax){
    let count=$(element).parents().eq(1).children('.quantity-l').children().val();
    if(count*1+1>countMax){
        return;
    }
    $(element).parents().eq(1).children('.quantity-l').children().val(count*1+1);
    updateCartLocal(1,id)
    totalPrice();
}


function updateCartLocal(op,id){
    let quantity;
    let list_cart=[];
    list_cart=JSON.parse(window.localStorage.getItem('list_cart'));
    for (let item of list_cart){
        if(item.productDetailId===id.toString()){
            if(op===1){
                item.quantity+=1;
            }else {
                item.quantity-=1;
            }
            quantity=item.quantity;
            break;
        }
    }
    localStorage.setItem('list_cart', JSON.stringify(list_cart));
    let data={};
    data["productDetailId"]=id;
    data["quantity"]=quantity;
    let flagUser=$('.options-user').children().eq(0).attr("flag");
    if(flagUser==="true"){
        $.ajax({
            url:'/api/cart/update',
            type: 'PUT',
            contentType: 'application/json',
            data:JSON.stringify(data),
            success:function (){

            },error:function (){
                alert("Error");
            }

        })
    }
}

//Load cart from local storage
$(()=>{
    let list_cart;
    list_cart=window.localStorage.getItem('list_cart');
    if(list_cart){
        $.ajax({
            url:'/api/cart/load',
            type:'POST',
            dataType:'JSON',
            contentType:'application/json',
            data:list_cart,
            success:function (result){
                let html='<div class="prods-cart">';
                for(let cart of result) {
                    html += `<div class="prod-cart">
                      <div class="prod-img">
                        <a href="detail.html"><img src="/assets/img/product/${cart.detail.image}" alt=""></a>
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
                          <div class="quantity-l">
                            <input type="number" name="quantity" id="quantity" value="${cart.quantity}">
                          </div>
                          <div class="change-quantity">
                            <a id="plus" onclick="plus(this,${cart.detail.id},3)"><i class="fas fa-plus"></i></a>
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
                            <a href="">Thanh toán <i class="far fa-long-arrow-right"></i></a>
                          </div>
                          <div class="pay-cart-b">
                            <ul>
                              <li><i class="fal fa-shipping-timed"></i><p>Chậm chễ trong việc giao hàng</p></li>
                              <li><i class="fas fa-undo-alt"></i><p>Trả hàng dễ dàng</p></li>
                            </ul>
                          </div>
                        </div>
                      </div>`;
                $('.content-cart').html(html).ready(function (){
                    totalPrice();
                });
            },error:function (){
                alert("Error")
            }
        })
    }
})

function toMoney(totalprice){
    return totalprice.toLocaleString('it-IT', {
        style: 'currency',
        currency: 'VND'
    });
}