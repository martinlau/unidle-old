/*
 * Normalized hide address bar for iOS & Android
 * (c) Scott Jehl, scottjehl.com
 * MIT License
 */
(function (win) {
    "use strict";
    var doc = win.document,
        scrollTop = 1,
        getScrollTop = function () {
            return win.pageYOffset || ((doc.compatMode === "CSS1Compat") && doc.documentElement.scrollTop) || doc.body.scrollTop || 0;
        },
        bodyCheck = win.setInterval(function () {
            if (doc.body) {
                win.clearInterval(bodyCheck);
                scrollTop = getScrollTop();
                win.scrollTo(0, scrollTop === 1 ? 0 : 1);
            }
        }, 15);

    if (!win.location.hash && win.addEventListener) {
        win.scrollTo(0, 1);
        win.addEventListener("load", function () {
            win.setTimeout(function () {
                if (getScrollTop() < 20) {
                    win.scrollTo(0, scrollTop === 1 ? 0 : 1);
                }
            }, 0);
        });
    }
}(this));
