package filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jwt.JwtManager;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "JwtFilter",
        urlPatterns = {"/*"})
public class JwtFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String tokenHeader = ((HttpServletRequest)req).getHeader("Autohrization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring( new String("Bearer").length());
            Jws<Claims> claims = JwtManager.getInstance().parseToken(token);
            if ( claims != null ) {
                String subject = claims.getBody().getSubject();
                String role = (String) claims.getBody().get("loggedInAs");
                req.setAttribute("loggedInAs", role);
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
