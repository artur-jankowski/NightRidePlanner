package pl.jankowski.NightRidePlanner.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends GenericFilterBean {

    private static final String BEARER = "Bearer";

    private UserDetailsServiceImpl userDetailsService;

    public JwtTokenFilter(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String headerValue = ((HttpServletRequest) request).getHeader("Authorization");
        getBearerToken(headerValue).ifPresent(token ->
                userDetailsService.loadUserByJwtToken(token).ifPresent(userDetails ->
                        SecurityContextHolder.getContext().setAuthentication(
                                new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities())
                        )
                )
        );
        filterChain.doFilter(request, response);
    }

    private Optional<String> getBearerToken(String headerValue) {
        if (headerValue != null && headerValue.startsWith(BEARER)) {
            return Optional.of(headerValue.replace(BEARER, "").trim());
        }
        return Optional.empty();
    }
}
