package com.skcc.stockv1.core.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.egovframe.rte.fdl.cmmn.trace.LeaveaTrace;
import org.egovframe.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import org.egovframe.rte.fdl.cmmn.trace.manager.TraceHandlerService;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.AntPathMatcher;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@Slf4j
public class MapperConfig {



    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
    /**
     * @return [LeaveaTrace 설정] traceHandlerService 등록. TraceHandler 설정
     */
    @Bean
    public DefaultTraceHandleManager traceHandlerService() {
        DefaultTraceHandleManager defaultTraceHandleManager = new DefaultTraceHandleManager();
        defaultTraceHandleManager.setReqExpMatcher(antPathMatcher());
        defaultTraceHandleManager.setPatterns(new String[] {"*"});
        return defaultTraceHandleManager;
    }


    @Bean
    public LeaveaTrace leaveaTrace() {
        LeaveaTrace leaveaTrace = new LeaveaTrace();
        leaveaTrace.setTraceHandlerServices(new TraceHandlerService[] {traceHandlerService()});
        return leaveaTrace;
    }


    @Bean(name = {"sqlSession", "egov.sqlSession"})
    public SqlSessionFactoryBean sqlSession(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setConfigLocation(
                pathMatchingResourcePatternResolver.getResource("classpath:/mapper/config/mapper-config.xml"));

        try {
            sqlSessionFactoryBean.setMapperLocations(
                    pathMatchingResourcePatternResolver
                            .getResources("classpath:/mapper/domain/**/*.xml"));
        } catch (IOException e) {
            // TODO Exception 처리 필요
        }

        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate egovSqlSessionTemplate(@Qualifier("sqlSession") SqlSessionFactory sqlSession) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSession);
//		mapUnderscoreToCamelCase
        return sqlSessionTemplate;
    }
}
