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
$(function () {

    "use strict";

    $(document).on("click", ".load-more", function (ev) {

        ev.preventDefault();

        var $link = $(this),
            href = $link.attr("href"),
            target = $link.data("load-more-target");

        $.ajax(href, {dataType: "html"}).done(function (data) {
            var $newContent = $(target, $("<div></div>").html(data));
            // TODO use proper pagination and selectively append to questions and replace load more
            $(target).html($newContent.length ? $newContent.html() : data);
        });

    });
});
