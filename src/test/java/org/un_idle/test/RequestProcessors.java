package org.un_idle.test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class RequestProcessors {

    public static RequestPostProcessor remoteAddr(final String remoteAddr) {
        return new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(final MockHttpServletRequest request) {
                request.setRemoteAddr(remoteAddr);

                return request;
            }
        };
    }

}
