package com.fractals.filters;

import com.fractals.backingbeans.LoginBacking;
import java.io.IOException;
import java.util.Enumeration;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebFilter;


import javax.servlet.ServletContext;

/**
 * Filter all requests for management pages. Checks if the users is first logged
 * in, then if the user is a manager.
 *
 * This class was based on a tutorial by Ken Fogel.
 * Original version can be found at: https://gitlab.com/omniprof/JSFSample30Filter
 *
 * @author Danieil Skrinikov
 * @version  1.0.01
 * @since 2017-03-06
 *
 */
@WebFilter(filterName = "ManagementFilter", urlPatterns = {"/management/*", "/faces/management/*" })
public class ManagementFilter implements Filter {

    private ServletContext context;
    
    @Inject
    private LoginBacking login;
    
    /**
     * Is called whenever someone tries to access the management side of the site.
     * Checks if the person sending the request is logged in and if it is an 
     * administrator.
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @param chain chain of filters.
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {               
               
        
        // Check if user is logged in and if is administrator.
        if(!login.isLoggedIn() || !login.getCurrentUser().getIsAdmin()){
            // Not logged in
            
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect( contextPath + "/client/index.xhtml");
        }
        else{
            chain.doFilter(request, response);
        }
    }
    
    /**
     * Gets the context if the current session.
     * 
     * @param config
     * @throws ServletException 
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        context = config.getServletContext();
    }

    
    @Override
    public void destroy() {
        // Nothing to do here!
    }
}