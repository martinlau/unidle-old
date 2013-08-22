package org.unidle.social;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConnectControllerImpl extends ConnectController {

    public ConnectControllerImpl(final ConnectionFactoryLocator connectionFactoryLocator,
                                 final ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }

    @Override
    protected String connectedView(final String providerId) {

        return "redirect:/account";
    }

}
