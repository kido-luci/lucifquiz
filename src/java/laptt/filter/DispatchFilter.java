package laptt.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatchFilter implements Filter {

    private FilterConfig filterConfig = null;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");

        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        String uri = httpServletRequest.getRequestURI();

        ServletContext servletContext = filterConfig.getServletContext();
        String url = servletContext.getInitParameter("start-up");

        try {
            String resource = uri.replaceFirst(httpServletRequest.getContextPath() + "/", "");

            if (resource.length() > 0) {
                if (resource.lastIndexOf(".jsp") > 0
                        || resource.lastIndexOf(".html") > 0
                        || resource.lastIndexOf(".png") > 0
                        || resource.lastIndexOf(".css") > 0
                        || resource.lastIndexOf(".min.css") > 0
                        || resource.lastIndexOf(".js") > 0
                        || resource.lastIndexOf(".jpg") > 0
                        || resource.lastIndexOf(".jpeg") > 0) {
                    url = null;
                } else if (resource.equals("start-up")) {
                    url = httpServletRequest.getContextPath();
                } else {
                    url = servletContext.getInitParameter(resource);
                    if (url == null || url.length() == 0) {
                        url = httpServletRequest.getContextPath();
                    }
                }
            }

            if (url != null && url.equals(httpServletRequest.getContextPath())) {
                httpServletResponse.sendRedirect(url);
            } else if (url != null) {
                RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(url);
                requestDispatcher.forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Throwable t) {
            log("DispatchFilter_Throwable: " + t.getMessage());
        }
    }


    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
