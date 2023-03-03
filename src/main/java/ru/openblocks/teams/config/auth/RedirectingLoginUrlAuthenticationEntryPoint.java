package ru.openblocks.teams.config.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;

/**
 * Этот компонент используется для того, чтобы переадресовывать запросы, которые окончились статусом 401
 * на страницу входа в приложение.
 */
public class RedirectingLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public RedirectingLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(
                request,
                response,
                getLoginFormUrl() + "?redirectUrl=" + request.getServletPath()
        );
    }
}
