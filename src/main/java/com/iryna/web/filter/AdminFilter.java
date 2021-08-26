//package com.iryna.web.filter;
//
//import com.iryna.entity.Role;
//import com.iryna.security.SecurityService;
//import com.iryna.service.ServiceLocator;
//import com.iryna.web.parser.CookieParser;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class AdminFilter implements Filter {
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    private SecurityService securityService = ServiceLocator.getService(SecurityService.class);
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//
//        String token = CookieParser.getTokenFromCookies(httpServletRequest.getCookies());
//        log.info("Check if user is authorized");
//        if (token != null) {
//            if (securityService.isAccessAllowForRole(Role.ADMIN, token)) {
//                log.info("Authorized access");
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }
//        }
//        log.info("Unauthorized access {}", httpServletRequest.getRequestURI());
//        httpServletResponse.sendRedirect("/login");
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
