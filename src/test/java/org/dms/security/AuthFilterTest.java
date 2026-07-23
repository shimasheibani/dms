package org.dms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Regression tests for AuthFilter.
 *
 * Main bug: filterChain.doFilter(request, response) was only called INSIDE
 * the "token present and valid" branch. Any request with no token, or an
 * invalid/expired one (including the login/register endpoints themselves,
 * since they never carry a token), never reached filterChain.doFilter() at
 * all -- the request would simply hang instead of continuing down the
 * chain or reaching a 401 handler.
 */
@ExtendWith(MockitoExtension.class)
class AuthFilterTest {

    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private AuthFilter authFilter;

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void continuesFilterChain_whenNoAuthorizationHeaderPresent() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verifyNoInteractions(jwtUtils, customUserDetailsService);
    }

    @Test
    void continuesFilterChain_whenTokenIsInvalidOrExpired() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer bad.token.value");
        when(jwtUtils.getUsernamFromToken("bad.token.value")).thenThrow(new RuntimeException("expired"));

        assertDoesNotThrow(() -> authFilter.doFilterInternal(request, response, filterChain));

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
