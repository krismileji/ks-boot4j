package host.springboot.framework.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import host.springboot.framework.mybatisplus.domain.BaseDO;
import host.springboot.framework3.core.model.RequestInfo;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * MybatisPlus自动填充配置
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@AutoConfiguration
@ConditionalOnClass({
        SqlSessionFactory.class,
        SqlSessionFactoryBean.class
})
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnMissingBean(MetaObjectHandler.class)
public class KsMybatisPlusMetaObjectHandler implements MetaObjectHandler {

    /**
     * 构造器
     *
     * @param objectMapper objectMapper
     * @since 0.1.0
     */
    public KsMybatisPlusMetaObjectHandler(ObjectProvider<ObjectMapper> objectMapper) {
        JacksonTypeHandler.setObjectMapper(objectMapper.getIfAvailable(ObjectMapper::new));
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        RequestInfo requestInfo = RequestInfo.REQUEST_CONTEXT.get();
        this.strictInsertFill(metaObject, BaseDO.FIELD_CREATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, BaseDO.FIELD_UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        if (Objects.nonNull(requestInfo)) {
            this.strictInsertFill(metaObject, BaseDO.FIELD_CREATE_USER, requestInfo::getUserId, String.class);
            this.strictInsertFill(metaObject, BaseDO.FIELD_UPDATE_USER, requestInfo::getUserName, String.class);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        RequestInfo requestInfo = RequestInfo.REQUEST_CONTEXT.get();
        this.strictUpdateFill(metaObject, BaseDO.FIELD_UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        if (Objects.nonNull(requestInfo)) {
            this.strictUpdateFill(metaObject, BaseDO.FIELD_UPDATE_USER, requestInfo::getUserId, String.class);
        }
    }
}
