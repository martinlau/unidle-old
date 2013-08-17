(function ($) {
    "use strict";

    var submitForm = function (e) {
        e.preventDefault();

        var formId = $(this).data('form-id');
        if ('#' !== formId.charAt(0)) {
            formId = '#' + formId;
        }

        $(formId).submit();
    };

    $('body').on('click', '.form-submit', submitForm);

}(jQuery));
