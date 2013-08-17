package org.unidle.social;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

@Component
public class SignInAdapterImpl implements SignInAdapter {

    @Override
    public String signIn(final String userId,
                         final Connection<?> connection,
                         final NativeWebRequest request) {

        SecurityContextHolder.getContext()
                             .setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));

        return null;
    }

}