(function ($) {
    "use strict";

    var submitForm = function (e) {
        var action = $(this).data('form-action'),
            $form  = $(this).parent("form");

        e.preventDefault();

        $form.attr('action', action).submit();
    };

    $('body').on('click', '.form-submit', submitForm);

}(jQuery));
