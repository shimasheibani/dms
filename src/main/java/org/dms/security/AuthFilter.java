package org.dms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if(token != null){
            try {
                String email = jwtUtils.getUsernamFromToken(token);
                if(StringUtils.hasText(email)) {
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                    if(jwtUtils.isTokenValid(token, userDetails)){
                        log.info("Token is valid, for username of {}", email);
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (Exception e) {
                // Invalid, malformed, or expired token: leave the security context empty
                // so the request falls through as unauthenticated instead of never
                // reaching filterChain.doFilter() below.
                log.warn("Rejected invalid JWT: {}", e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }
        // This must run unconditionally: previously it only ran inside the
        // "token present and valid" branch, so any request with no token or
        // an invalid token never continued down the filter chain at all
        // (the request would just hang instead of reaching the controller
        // or a 401 response).
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("Exception occurred while processing request", ex);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String tokenWithBearer = request.getHeader("Authorization");
        if(tokenWithBearer != null && tokenWithBearer.startsWith("Bearer ")){
            return tokenWithBearer.substring(7);
        }
        return null;
    }
}
