package com.snda.youni.taskweb.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.snda.youni.taskweb.common.AppSettings;

public class UserCookieFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		String uri = request.getRequestURI();  
		// /login 
		if(!  ( uri.contains("/passport/") 
				|| uri.contains("favicon.ico") )){
			Cookie[] cookies = request.getCookies();
			if(cookies!=null){
				boolean validate = false;
				for(Cookie c:cookies){
					
					if(AppSettings.getInstance().getString("user.cookie.name", "taskTodo_USER").equals(c.getName())){
						String cuserstr = c.getValue();
						if(cuserstr.split(":").length==2){
							validate = true;
						}
					}
				}
				if(!validate){
					//redirect to uam
					String webapp_host = AppSettings.getInstance().getString("webapp.domain.host", "");
					String webapp_port = AppSettings.getInstance().getString("webapp.domain.port", "");
					String url = AppSettings.getInstance().getString("uam.url.login", "")+"http://"+webapp_host+":"+webapp_port+"/passport/login?uri="+uri;
					response.sendRedirect(url);
					return;
				}
			}
		}

		filterChain.doFilter(request, response);
		
	}

}
