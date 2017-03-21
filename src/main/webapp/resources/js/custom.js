$(document).ready(function () {

    $('[data-toggle="offcanvas"]').click(function () {
        $('#wrapper').toggleClass('toggled');
    });

    $('[data-toggle="offcanvas"]').click(function () {
        $('#wrapper').toggleClass('toggled');
    });

    $('a[href="#navbar-more-show"], .navbar-more-overlay').on('click', function (event) {
        console.log("Test");
        event.preventDefault();
        $('body').toggleClass('navbar-more-show');
        if ($('body').hasClass('navbar-more-show')) {
            $('a[href="#navbar-more-show"]').closest('li').addClass('active');
        } else {
            $('a[href="#navbar-more-show"]').closest('li').removeClass('active');
        }
        return false;
    });

    $('.toggle-reviews').click(function () {
        $(this).parent().siblings().eq(0).toggle("slow");       
    });

    $('.review').find(".stars").each(function () {
        var content = $(this).html().trim();
        var stars = "";

        for (var i = 0; i < 5; i++) {
            if (content - i <= 0)
                stars += '<span class="fa fa-star-o"></span>';
            else
                stars += '<span class="fa fa-star"></span>';
        }

        $(this).html(stars);
    });

    /**
     * Removes the rating-stars-container-selected class from the element
     * when the mouse hovers over it.
     */
    $('.rating-stars-container').on("mouseenter", function(){
        $(this).removeClass('rating-stars-container-selected');
    });

    $('.rating-star').on("mouseenter", function (e) {
        var pos = $(this).index();

        //Iterating through siblings
        $(this).siblings().andSelf().each(function () {
            if ($(this).index() <= pos) {
                $(this).removeClass('fa-star-o');
                $(this).addClass('fa-star');
            } else {
                $(this).removeClass('fa-star');
                $(this).addClass('fa-star-o');
            }
        });
    });

    /**
     * When the user hover's over a star, it fills all the previous stars and displays
     * new ones.
     */
    $('.rating-star').on("mouseleave", function () {
        var pos = $(this).index();

        $(this).siblings().andSelf().each(function () {
            if ($(this).index() >= pos) {
                $(this).removeClass('fa-star');
                $(this).addClass('fa-star-o');
            }
        });
    });

    $('.rating-stars-container').on("mouseleave", function () {
        var val = $(this).siblings().eq(0).val();
        if (val !== "") {
            $(this).children().each(function () {
                if ($(this).index() <= val - 1) {
                    $(this).removeClass('fa-star-o');
                    $(this).addClass('fa-star');
                } else {
                    $(this).removeClass('fa-star');
                    $(this).addClass('fa-star-o');
                }
            });
            
            $(this).addClass("rating-stars-container-selected");
        }
    });

    $('.rating-star').click(function () {
        var pos = $(this).index() + 1;        

        // Setting the value to the hidden input.
        $(this).parent().siblings().each(function () {
            $(this).val(pos);
        });
        
        $(this).parent().addClass("rating-stars-container-selected");
    });
    
    $('.cover-image').on("mouseenter", function(){
       $(this).siblings().eq(0).fadeIn();
    });
    
    $('.image-overlay').on("mouseleave",function(){
        $(this).fadeOut();
    });
    
    $('#no-downloads-container').children().each(function() {
        $(this).fadeIn(3000);
    });
    
    $('#downloads-container').fadeIn(3000);

});

