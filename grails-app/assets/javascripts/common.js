$(function () {
    $(".clickable-row").click(function () {
        window.document.location = $(this).data("href");
    });
});

$(function () {
    setTimeout(function () {
        $(".flashMessage").fadeOut(2000);
    }, 3000);
});