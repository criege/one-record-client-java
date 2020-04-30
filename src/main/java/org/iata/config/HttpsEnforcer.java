package org.iata.config;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpsEnforcer implements Filter {

  private FilterConfig filterConfig;

  public static final String X_FORWARDED_PROTO = "x-forwarded-proto";

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    System.out.println("TEST");

    if (request.getHeader(X_FORWARDED_PROTO) != null) {
      System.out.println("HERE");
      if (request.getHeader(X_FORWARDED_PROTO).indexOf("https") != 0) {
        String pathInfo = (request.getPathInfo() != null) ? request.getPathInfo() : "";
        response.sendRedirect("https://" + request.getServerName() + pathInfo);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // nothing
  }
}