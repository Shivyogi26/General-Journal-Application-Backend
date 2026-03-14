package net.engineeringdigest.journalApp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("🔐 JWT FILTER EXECUTING for: " + request.getRequestURI());

        // =========================
        // 1. GET AUTH HEADER
        // =========================
        final String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization header = " + authHeader);

        // =========================
        // 2. CHECK HEADER FORMAT
        // =========================
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ No Bearer token found");
            filterChain.doFilter(request, response);
            return;
        }

        // =========================
        // 3. EXTRACT TOKEN
        // =========================
        String token = authHeader.substring(7);
        System.out.println("Token extracted");

        try {

            // =========================
            // 4. EXTRACT USERNAME
            // =========================
            String username = jwtService.extractUsername(token);
            System.out.println("Username from token = " + username);

            // =========================
            // 5. AUTHENTICATE IF NOT ALREADY
            // =========================
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);

                System.out.println("User loaded from DB");

                // =========================
                // 6. VALIDATE TOKEN
                // =========================
                if (jwtService.isTokenValid(token, userDetails.getUsername())) {

                    System.out.println("✅ Token is valid");

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // =========================
                    // 7. SET AUTHENTICATION
                    // =========================
                    SecurityContextHolder.getContext()
                            .setAuthentication(authenticationToken);

                    System.out.println("✅ Authentication set in SecurityContext");
                } else {
                    System.out.println("❌ Token invalid");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ JWT processing failed: " + e.getMessage());
        }

        // =========================
        // 8. CONTINUE FILTER CHAIN
        // =========================
        filterChain.doFilter(request, response);
    }
}