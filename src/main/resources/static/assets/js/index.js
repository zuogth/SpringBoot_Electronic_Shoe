$(document).ready(function () {
    $('.prod-popular').slick({
        slidesToShow: 5,
        slidesToScroll: 5,
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
});

$(document).ready(function() {
            
    $('.rateYo').each(function(index,element){
        star=$(element).attr('data-star');
        $(element).rateYo({
            rating: star,
            ratedFill: "#101012",
            readOnly:true,
            starWidth: "12px"
        });
    });
    
    rateYo=$("#rateYo2").rateYo({
        fullStar: true
        
    });
    
    $('#rateYo2').click(function(){
        var rating = rateYo.rateYo("rating");
        $('#rateYo2').attr('data-star',rating);
    })
});