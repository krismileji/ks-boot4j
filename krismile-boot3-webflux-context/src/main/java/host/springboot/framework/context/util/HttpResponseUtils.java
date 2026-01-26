package host.springboot.framework.context.util;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import host.springboot.framework3.core.util.inner.FilenameUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * 响应工具类
 *
 * <ul>
 *     <li><b>writeOfJson</b> - 向客户端输出JSON响应信息（返回Mono）</li>
 *     <li><b>writeOfFile</b> - 向客户端输出文件响应信息（返回Mono）</li>
 * </ul>
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
public class HttpResponseUtils {

    /**
     * 私有构造器，防止实例化
     *
     * @since 0.2.0
     */
    private HttpResponseUtils() {
    }

    /**
     * 向客户端输出 JSON 响应信息
     *
     * @param response ServerHttpResponse
     * @param object   响应对象
     * @return Mono
     * @since 0.2.0
     */
    public static @NonNull Mono<Void> writeOfJson(
            @NonNull ServerHttpResponse response,
            @NonNull Object object) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = JSON.toJSONBytes(object);
        DataBuffer dataBuffer = new DefaultDataBufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(dataBuffer));
    }

    /**
     * 向客户端输出 JSON 响应信息
     *
     * @param response     ServerHttpResponse
     * @param objectMapper 序列化类
     * @param object       响应对象
     * @return Mono
     * @since 0.2.0
     */
    public static @NonNull Mono<Void> writeOfJson(
            @NonNull ServerHttpResponse response,
            @NonNull ObjectMapper objectMapper,
            @NonNull Object object) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return Mono.fromCallable(() -> objectMapper.writeValueAsBytes(object))
                .map(bytes -> new DefaultDataBufferFactory().wrap(bytes))
                .flatMap(dataBuffer -> response.writeWith(Mono.just(dataBuffer)));
    }

    /**
     * 向客户端输出文件响应信息
     *
     * @param response ServerHttpResponse
     * @param file     文件
     * @return Mono
     * @since 0.2.0
     */
    public static @NonNull Mono<Void> writeOfFile(
            @NonNull ServerHttpResponse response,
            @NonNull File file) {
        String fileBaseName = FilenameUtils.getName(file.getAbsolutePath());
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        response.getHeaders().setContentType(mediaType);
        response.getHeaders().setContentDispositionFormData("attachment", 
                URLEncoder.encode(fileBaseName, StandardCharsets.UTF_8));
        response.getHeaders().setContentLength(file.length());

        Path filePath = file.toPath();
        Flux<DataBuffer> dataBufferFlux = DataBufferUtils.read(
                filePath,
                new DefaultDataBufferFactory(),
                4096
        );
        return response.writeWith(dataBufferFlux);
    }
}
