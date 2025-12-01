package host.springboot.framework3.core.util.common;

import org.jspecify.annotations.NonNull;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * 项目工具类
 *
 * <ul>
 *     <li><b>getResourcePath</b> - 获取项目 [resources] 文件夹下的文件</li>
 * </ul>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class ProjectUtils {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private ProjectUtils() {
    }

    /**
     * 运行时获取项目中Resource目录
     *
     * @return 项目中Resource目录
     * @throws FileNotFoundException 文件未找到
     * @since 0.1.0
     */
    public static String getResourcePath() throws FileNotFoundException {
        return getResourcePath("");
    }

    /**
     * 运行时获取项目中Resource目录
     *
     * @param filePath 相对于 {@code resource} 目录下的路径
     * @return 项目中Resource目录
     * @throws FileNotFoundException 文件未找到
     * @since 0.1.0
     */
    public static String getResourcePath(@NonNull String filePath) throws FileNotFoundException {
        return ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX + filePath).getPath();
    }
}
