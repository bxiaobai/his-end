package   com.his.master.utils.security;

import com.alibaba.fastjson.JSON;
import   com.his.master.common.ResultUtils;
import   com.his.master.exception.BusinessException;
import   com.his.master.exception.ThrowUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse
            response, AuthenticationException authException) throws IOException,
            ServletException {
        throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录");
    }
}