package org.un_idle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.ServletRequest;

@Component
public class LocationArgumentResolver implements WebArgumentResolver {

    private final LocationService locationService;

    @Autowired
    public LocationArgumentResolver(final LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public Object resolveArgument(final MethodParameter methodParameter,
                                  final NativeWebRequest nativeWebRequest) throws Exception {

        if (methodParameter.getParameterType() != Location.class) {
            return UNRESOLVED;
        }

        final String address = StringUtils.hasText(nativeWebRequest.getParameter("address"))
                               ? nativeWebRequest.getParameter("address")
                               : nativeWebRequest.getNativeRequest(ServletRequest.class)
                                                 .getRemoteAddr();

        return locationService.locateAddress(address);
    }

}
