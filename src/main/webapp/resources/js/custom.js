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
        var id = $(this).attr("id");

        if ($(this).find("span").hasClass("toggle-reviews-active"))
            $(this).find("span").removeClass("toggle-reviews-active");
        else
            $(this).find("span").addClass("toggle-reviews-active");
        $('#r' + id).toggle("slow");
    });

    $('.album-track-list').find(".stars").each(function () {
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

});

