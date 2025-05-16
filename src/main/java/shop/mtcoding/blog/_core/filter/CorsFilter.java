package shop.mtcoding.blog._core.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // System.out.println("CORS 필터 작동");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");
        log.debug("Origin : " + origin);

        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500"); //열어주고싶은 origin만 열어줘야한다. origin만 적으면 다 허용됨
        // response.setHeader("Access-Control-Expose-Headers", "Authorization"); // Expose => 이 헤더 응답을 자바스크립트로 접근하게 허용할게
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS"); //OPTIONS은 preflight 요청 때문에 열어둠
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Key, Content-Type, Accept, Authorization");// Allow => 이거 허용해줄게 / X-Key 프로토콜이 아닌 내가 임의로 만든 헤더는 대문자X에 하이픈 붙여서 준다.
        response.setHeader("Access-Control-Allow-Credentials", "true"); // true 하면 쿠키의 세션값 허용

        // Preflight 요청을 허용하고 바로 응답하는 코드
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK); // SC_OK = 200 성공이라는 뜻
        } else {
            chain.doFilter(req, res);
        }
    }
}