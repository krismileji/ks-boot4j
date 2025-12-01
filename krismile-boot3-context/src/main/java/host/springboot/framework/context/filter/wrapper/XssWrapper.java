package host.springboot.framework.context.filter.wrapper;

import host.springboot.framework.context.util.XssUtils;
import host.springboot.framework3.core.util.inner.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * Xss攻击防御包装器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class XssWrapper extends HttpServletRequestWrapper {

    /**
     * 排除的参数名后缀
     */
    private final List<String> excludeParamEndWith;

    /**
     * 安全标签白名单
     */
    private final Safelist safeList;

    /**
     * 输出配置
     */
    private final Document.OutputSettings outputSettings;


    /**
     * 构造器
     *
     * @param request             HttpServletRequest
     * @param excludeParamEndWith 排除的参数名后缀
     * @param safeList            安全标签白名单
     * @param outputSettings      输出配置
     * @since 0.1.0
     */
    public XssWrapper(
            @NonNull HttpServletRequest request,
            @NonNull List<String> excludeParamEndWith,
            @NonNull Safelist safeList,
            Document.@NonNull OutputSettings outputSettings) {
        super(request);
        this.excludeParamEndWith = excludeParamEndWith;
        this.safeList = safeList;
        this.outputSettings = outputSettings;
    }

    @Override
    public String getParameter(String name) {
        if (Objects.nonNull(excludeParamEndWith)) {
            for (String endWith : excludeParamEndWith) {
                if (name.endsWith(endWith)) {
                    return super.getParameter(name);
                }
            }
        }
        name = XssUtils.clean(name, safeList, outputSettings);
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = XssUtils.clean(value, safeList, outputSettings);
        }
        return value;
    }

    @Override
    public String getHeader(String name) {
        name = XssUtils.clean(name, safeList, outputSettings);
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = XssUtils.clean(value, safeList, outputSettings);
        }
        return value;
    }
}