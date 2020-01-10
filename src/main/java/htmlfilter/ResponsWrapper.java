package htmlfilter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class ResponsWrapper extends HttpServletResponseWrapper {
    private PrintWriter cachedWriter;
    private CharArrayWriter bufferedWriter;
    public ResponsWrapper(HttpServletResponse response) {
        super(response);
        //这个是我么嗯保存返回结果的地方
        bufferedWriter = new CharArrayWriter();
        // 这个是包装PrintWriter的,让所有结果通过这个PrintWriter写入到bufferedWriter中
        cachedWriter =new PrintWriter(bufferedWriter);
    }

    public PrintWriter getWriter() {
        return cachedWriter;
    }

    public String getResult() {
        return bufferedWriter.toString();
    }
}
