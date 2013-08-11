package org.un_idle.geo;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.un_idle.service.LocationService;

import javax.servlet.ServletRequest;

public class LocationArgumentResolver implements WebArgumentResolver {

    private final LocationService locationService;

    public LocationArgumentResolver(final LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public Object resolveArgument(final MethodParameter methodParameter,
                                  final NativeWebRequest nativeWebRequest) throws Exception {

        if (methodParameter.getParameterType() != Location.class) {
            return UNRESOLVED;
        }

        final ServletRequest servletRequest = nativeWebRequest.getNativeRequest(ServletRequest.class);

        return locationService.forAddress(servletRequest.getRemoteAddr());
    }

}
