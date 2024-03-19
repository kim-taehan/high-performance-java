package com.skcc.orderv1.core.paging.aspect;

import com.skcc.orderv1.core.paging.annotation.PageQuery;
import com.skcc.orderv1.core.paging.dto.PageDto;
import com.skcc.orderv1.core.paging.dto.Paging;
import com.skcc.orderv1.core.paging.mapper.PagingMapper;
import com.skcc.orderv1.core.response.ResEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Slf4j
@RequiredArgsConstructor
public class PagingAspect {

    public static final String SUFFIX_ID = "Count";
    private final PagingMapper pagingMapper;

    @Around("@annotation(postMapping) && (args(paging,..) || args(..,paging))")
    public Object doPagingByController(ProceedingJoinPoint joinPoint, PostMapping postMapping, Paging paging) throws Throwable {
        HttpServletRequest request = extractRequest();
        request.setAttribute("page", paging.getPage());
        ResEntity ret = (ResEntity) joinPoint.proceed();
        ret.updatePaging(paging.getPage());
        return ret;
    }

    private static HttpServletRequest extractRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    @Around("@annotation(pageQuery)")
    public Object doPageQuery(ProceedingJoinPoint joinPoint, PageQuery pageQuery) throws Throwable {

        HttpServletRequest request = extractRequest();
        PageDto page = (PageDto) request.getAttribute("page");

        updatePageArgs(joinPoint, page);

        // paging query execute
        Object result =  joinPoint.proceed();

        // find count sql query
        String queryId = pageQuery.value() + SUFFIX_ID;

        // count query execute
        int totalCount = findTotalCount(queryId, joinPoint.getArgs());

        page.updateTotal(totalCount);
        return result;
    }

    private void updatePageArgs(ProceedingJoinPoint joinPoint, PageDto page) {
        Map params = findParams(joinPoint);
        params.put("pageSize", page.getPageSize());
        params.put("currentPage", page.getCurrentPage());
        params.put("totalPage", page.getTotalPage());
        params.put("totalCount", page.getTotalCount());
    }


    private Map findParams(ProceedingJoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof HashMap) {
                return (Map) arg;
            }
        }
        throw new IllegalStateException("Map 객체가 없는 경우 페이징 쿼리를 사용할 수 없습니다.");
    }

    private int findTotalCount(String queryId, Object[] args) {
        for (Object arg : args) {
            if (arg instanceof HashMap) {
                return pagingMapper.pagingCall(queryId, (Map)arg);
            }
        }
        return 0;
    }


}
