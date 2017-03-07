package com.fractals.filters;

import com.fractals.controllers.LoginController;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The filter that restricts the access for not authenticated users
 * on client's site.
 * 
 * Based on the project by Ken Fogel https://gitlab.com/omniprof/JSFSample30Filter
 *
 * @author Aline Shulzhenko
 * @version 07/03/2017
 * @since 1.8
 */
@WebFilter(filterName = "ClientAuthenticationFilter", urlPatterns = {"/client/invoice.xhtml", "/faces/client/invoice.xhtml",
                                                                     "/client/checkout.xhtml", "/faces/client/checkout.xhtml",
                                                                     "/downloads/library.xhtml", "/faces/downloads/library.xhtml"})
public class ClientAuthenticationFilter implements Filter {
    
    private ServletContext context;
    
    @Inject
    private LoginController login;

    /**
     * Gets the context if the current session.
     * @param filterConfig The FilterConfig object.
     * @throws ServletException 
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
    }

    /**
     * Verifies that the user is authenticated in order to access invoice, checkout and downloads pages.
     * If not, the user is redirected to the login page.
     * @param request Http request object.
     * @param response Http response object.
     * @param chain The chain of filters.
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(!login.isLoggedIn()) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect( contextPath + "/client/login.xhtml");
        }
        else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
    
}
