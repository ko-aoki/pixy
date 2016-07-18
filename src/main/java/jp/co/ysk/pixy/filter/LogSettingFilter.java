package jp.co.ysk.pixy.filter;

import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

/**
 * ログ出力用の診断コンテキストを設定するFilterクラス.
 * Created by ko-aoki on 2016/07/17.
 */
public class LogSettingFilter implements Filter {

    String CLIENT = "client";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 診断コンテキストを設定します.
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            MDC.put(CLIENT, servletRequest.getRemoteAddr());
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(CLIENT);
        }
    }

    @Override
    public void destroy() {

    }
}
