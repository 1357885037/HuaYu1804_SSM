package htmlfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class CacheFilter implements Filter, ApplicationContextAware {

    private static final Logger log= LoggerFactory.getLogger(CacheFilter.class);
    private static ApplicationContext ctx;

  /*  @Autowired
    private RedisTemplate redisTemplate;
*/

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.ctx=applicationContext;
    }

    private  String getHtmlFromCache(){
        StringRedisTemplate redis=(StringRedisTemplate)ctx.getBean("redisTemplate");
        return (String) redis.opsForValue().get("home");
    }

    private void putIntoCache(String html){
        StringRedisTemplate redis=(StringRedisTemplate)ctx.getBean("redisTemplate");
        redis.opsForValue().set("home",html, TimeUnit.MINUTES.toSeconds(1));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse resp=(HttpServletResponse)servletResponse;
        HttpServletRequest req=(HttpServletRequest)servletRequest;

        //如果不是访问主页，放行
        if(!req.getRequestURI().equals("/SSMHUAYU/Mq.do")){
            filterChain.doFilter(servletRequest,resp);
        }
        // 从缓存中得到主页html

        String html =getHtmlFromCache();
        if (null == html) {
            //截取生成的html并放入缓存
            ResponsWrapper Wrapper = new ResponsWrapper(resp);
            filterChain.doFilter(servletRequest,Wrapper);
            html=Wrapper.getResult();
            putIntoCache(html);
        }
        resp.setContentType("text/html; charset=utf-8");
        System.out.println(html);
        resp.getWriter().print(html);


    }

    @Override
    public void destroy() {

    }

}
