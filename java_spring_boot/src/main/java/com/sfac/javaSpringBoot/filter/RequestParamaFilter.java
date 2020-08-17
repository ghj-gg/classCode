package com.sfac.javaSpringBoot.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

public class RequestParamaFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestParamaFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("------ Init Request Param Filter ------");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.debug("------ Do Request Param Filter ------");
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest){
          @Override
          public String getParameter(String name){
              String value = httpRequest.getParameter(name);
              if (StringUtils.isNotBlank(value)){
                  return value.replaceAll("fuck","*****");
              }
              return super.getParameter(name);
          }

          @Override
          public String[] getParameterValues(String name){
              String[] values = httpRequest.getParameterValues(name);
              if (values != null && values.length > 0){
                  for(int i = 0;i < values.length;i++){
                      values[i] = values[i].replaceAll("fuck","*****");
                  }
                  return values;
              }
              return super.getParameterValues(name);
          }
        };
        chain.doFilter(wrapper,response);
    }

    @Override
    public void destroy() {
        LOGGER.debug("------ Destroy Request Param Filter ------");
    }
}
