package org.un_idle.geo;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.GeoIp2Provider;
import com.maxmind.geoip2.model.Omni;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.ServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class LocationArgumentResolver implements WebArgumentResolver {

    private final GeoIp2Provider geoIp2Provider;

    public LocationArgumentResolver(final Resource resource) throws IOException {
        this(resource.getFile());
    }

    public LocationArgumentResolver(final File file) throws IOException {
        this.geoIp2Provider = new DatabaseReader(file);
    }

    @Override
    public Object resolveArgument(final MethodParameter methodParameter, final NativeWebRequest nativeWebRequest) throws Exception {

        if (methodParameter.getParameterType() != Location.class) {
            return UNRESOLVED;
        }

        final ServletRequest servletRequest = nativeWebRequest.getNativeRequest(ServletRequest.class);
        final InetAddress inetAddress = InetAddress.getByName(servletRequest.getRemoteAddr());
        final Omni omni = geoIp2Provider.omni(inetAddress);

        return new Location(omni.getCity().getName(),
                            omni.getMostSpecificSubdivision().getName(),
                            omni.getCountry().getName(),
                            omni.getContinent().getName());
    }

}
