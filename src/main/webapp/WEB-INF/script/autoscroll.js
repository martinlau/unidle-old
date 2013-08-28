/*
 * #%L
 * unidle
 * %%
 * Copyright (C) 2013 Martin Lau
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
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
}(window));
