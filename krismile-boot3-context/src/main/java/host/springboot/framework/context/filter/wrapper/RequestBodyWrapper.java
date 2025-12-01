package host.springboot.framework.context.filter.wrapper;

import host.springboot.framework.context.util.HttpRequestUtils;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.jspecify.annotations.NonNull;

import java.io.*;
import java.util.Objects;

/**
 * 重复获取请求流包装器
 *
 * <p>该类主要为解决 {@link HttpServletRequest} 中 {@code inputStream} 流只能被读取一次问题
 * <p>在处理表单请求时需要注意, 如果带有文件将会调用 {@link HttpServletRequest#getParts()} 拿到文件域,F
 * 最终会调用 {@code Request#getParts()} 方法, 核心为 {@code getParts()} 方法中的 {@code parseParts(boolean)} 方法,
 * 在 {@code parseParts(boolean)} 方法中将会使用 {@code ServletFileUpload#parseRequest(RequestContext)} 方法进行解析文件,
 * 在 {@code parseRequest(RequestContext)} 方法中取出输入流, 所以如果为非 {@code json} 请求时不进行任何操作
 * <p><b>Warning:</b> 在提前使用 {@link #getInputStream()} 方法时依然需要谨慎使用
 * (建议提前使用 {@link HttpRequestUtils#isJsonRequest(HttpServletRequest)}) 判断该请求是否为 {@code json} 请求
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class RequestBodyWrapper extends HttpServletRequestWrapper {

    /**
     * 请求流数据
     */
    private final byte[] copyBody;

    /**
     * 读取流缓存大小
     */
    private static final int READ_BUFFER_SIZE = 1024;

    /**
     * 输出流缓存大小
     */
    private static final int OUT_BUFFER_SIZE = 8192;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IOException If the request is null
     * @since 0.1.0
     */
    public RequestBodyWrapper(@NonNull HttpServletRequest request) throws IOException {
        super(request);
        InputStream inputStream = request.getInputStream();
        copyBody = copyInputStreamByte(inputStream);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(copyBody)));
    }

    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStream() {
            private int lastIndexRetrieved = -1;
            private ReadListener readListener = null;

            @Override
            public boolean isFinished() {
                return lastIndexRetrieved == (copyBody.length - 1);
            }

            @Override
            public boolean isReady() {
                return isFinished();
            }

            @Override
            public void setReadListener(ReadListener listener) {
                this.readListener = listener;
                if (!isFinished()) {
                    try {
                        readListener.onDataAvailable();
                    } catch (IOException e) {
                        readListener.onError(e);
                    }
                } else {
                    try {
                        readListener.onAllDataRead();
                    } catch (IOException e) {
                        readListener.onError(e);
                    }
                }
            }

            @Override
            public int read() throws IOException {
                int index = -1;
                if (!isFinished()) {
                    index = copyBody[lastIndexRetrieved + 1];
                    lastIndexRetrieved++;
                    if (isFinished() && (Objects.nonNull(readListener))) {
                        try {
                            readListener.onAllDataRead();
                        } catch (IOException e) {
                            readListener.onError(e);
                            throw e;
                        }
                    }
                }
                return index;
            }
        };
    }

    /**
     * 复制请求流中的 {@link Byte[]} 数据
     *
     * @param inputStream {@link InputStream}
     * @return 复制后的 {@link Byte[]} 数据
     * @throws IOException 读取流异常|复制字节异常
     * @since 0.1.0
     */
    private byte[] copyInputStreamByte(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(OUT_BUFFER_SIZE);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {

            int buffer;
            byte[] body = new byte[READ_BUFFER_SIZE];
            while (-1 != (buffer = inputStream.read(body))) {
                bufferedOutputStream.write(body, 0, buffer);
            }
            bufferedOutputStream.flush();

            return outputStream.toByteArray();
        }
    }
}