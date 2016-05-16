package filter;



import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class Filter
 */
@WebFilter("/Filter")
public class Filter implements javax.servlet.Filter {

    /**
     * Default constructor. 
     */
    public Filter() {
        // TODO Auto-generated constructor stub
    }

   /**
    * @see Filter#destroy()
    */
   public void destroy() {
      // TODO Auto-generated method stub
   }

   /**
    * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
    */
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      // TODO Auto-generated method stub
      // place your code here
      // pass the request along the filter chain
	   System.out.println("1");
      request.getRequestDispatcher("/Controller").forward(request, response);
      //request.getServletContext().getRequestDispatcher("/web_project/Controller").forward(request, response);
   }

   /**
    * @see Filter#init(FilterConfig)
    */
   public void init(FilterConfig fConfig) throws ServletException {
      // TODO Auto-generated method stub
   }

}