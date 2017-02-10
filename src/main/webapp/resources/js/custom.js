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
});

