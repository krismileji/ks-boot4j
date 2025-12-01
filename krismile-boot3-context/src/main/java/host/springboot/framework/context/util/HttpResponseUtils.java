package host.springboot.framework.context.util;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import host.springboot.framework3.core.util.inner.FilenameUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 响应工具类
 *
 * <ul>
 *     <li><b>writeOfJson</b> - 向客户端输出JSON响应信息</li>
 *     <li><b>writeOfFile</b> - 向客户端输出文件响应信息</li>
 * </ul>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class HttpResponseUtils {

    /**
     * 私有构造器
     */
    private HttpResponseUtils() {
    }

    /**
     * 向客户端输出JSON响应信息
     *
     * @param response HttpServletResponse
     * @param object   响应对象
     * @throws IOException 获取响应流失败|写入响应流失败|刷新响应流失败|关闭响应流失败
     * @since 0.1.0
     */
    public static void writeOfJson(
            @NonNull HttpServletResponse response,
            @NonNull Object object) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json; charset=" + response.getCharacterEncoding());
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSON.toJSONBytes(object));
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 向客户端输出JSON响应信息
     *
     * @param response     HttpServletResponse
     * @param objectMapper 序列化类
     * @param object       响应对象
     * @throws IOException 获取响应流失败|写入响应流失败|刷新响应流失败|关闭响应流失败
     * @since 0.1.0
     */
    public static void writeOfJson(
            @NonNull HttpServletResponse response,
            @NonNull ObjectMapper objectMapper,
            @NonNull Object object) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json; charset=" + response.getCharacterEncoding());
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(objectMapper.writeValueAsBytes(object));
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 向客户端输出文件响应信息
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param file     文件
     * @throws IOException 获取输出流失败
     * @since v4.5.0
     */
    public static void writeOfFile(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull File file) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        MediaType mediaType;
        String fileBaseName = FilenameUtils.getName(file.getAbsolutePath());

        ServletContext servletContext = request.getServletContext();
        String mineType = servletContext.getMimeType(fileBaseName);
        try {
            mediaType = MediaType.parseMediaType(mineType);
        } catch (Exception e) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        response.setContentType(mediaType.getType());

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=".concat(URLEncoder.encode(fileBaseName, StandardCharsets.UTF_8)));
        response.setContentLengthLong(file.length());

        try (FileInputStream fis = new FileInputStream(file)) {
            int n;
            byte[] buffer = new byte[4096];
            while (-1 != (n = fis.read(buffer))) {
                response.getOutputStream().write(buffer, 0, n);
            }
        }
    }
}
