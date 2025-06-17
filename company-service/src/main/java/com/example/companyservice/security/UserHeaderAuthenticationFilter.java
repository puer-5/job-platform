//package com.example.companyservice.security;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.stream.Collectors;
//
//public class UserHeaderAuthenticationFilter extends OncePerRequestFilter {
//
//    // Define header names (should match Zuul filter)
//    private static final String USER_ID_HEADER = "X-User-Id";
//    private static final String USER_NAME_HEADER = "X-User-Name";
//    private static final String USER_ROLES_HEADER = "X-User-Roles"; // Comma-separated roles
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // Get user information from headers added by Zuul
//        String userId = request.getHeader(USER_ID_HEADER);
//        String username = request.getHeader(USER_NAME_HEADER);
//        String userRolesHeader = request.getHeader(USER_ROLES_HEADER);
//
//        // If user information is present in headers (meaning Zuul authenticated the request)
//        if (userId != null && username != null && userRolesHeader != null) {
//
//            // Parse roles from the comma-separated string
//            Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
//            if (!userRolesHeader.trim().isEmpty()) {
//                authorities = Arrays.stream(userRolesHeader.split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//            }
//
//
//            // Create an authenticated Authentication object
//            // We use username as the principal, credentials are null as authentication is done
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    username, // Principal (can also be a custom UserDetails object if needed)
//                    null, // Credentials (already validated by Zuul)
//                    authorities // Granted Authorities (roles)
//            );
//
//            // Note: If you need the userId in your controllers, you can create a custom Principal object
//            // or store it as a "detail" in the Authentication object.
//            // authentication.setDetails(userId); // Example: Storing userId as detail
//
//            // Set the authentication object in the Security Context
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            System.out.println("Jobseeker Filter: Authenticated user from headers: " + username + ", Roles: " + userRolesHeader);
//
//        } else {
//            // No user headers found. Request might be unauthenticated or public.
//            // SecurityContextHolder will remain empty, and subsequent Spring Security
//            // configurations (like @PreAuthorize) will treat the user as anonymous.
//            System.out.println("Jobseeker Filter: No user headers found. Proceeding as unauthenticated.");
//        }
//
//        // Continue the filter chain
//        filterChain.doFilter(request, response);
//    }
//}
