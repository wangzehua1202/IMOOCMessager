package net.xingsu.web.italker.push.provider;

import com.google.common.base.Strings;
import com.mysql.cj.x.protobuf.Mysqlx;
import net.xingsu.web.italker.push.bean.api.base.ResponseModel;
import net.xingsu.web.italker.push.bean.db.User;
import net.xingsu.web.italker.push.factory.UserFactory;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

/**
 * 用于所有的请求的接口过滤和拦截
 * Created by Administrator on 2017/9/28 0028.
 */
@Provider
public class AuthRequestFilter implements ContainerRequestFilter{

    //实现接口的过滤方法
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //检测是否是注册登录接口
        String relationPath = ((ContainerRequest)requestContext).getPath(false);
        if(relationPath.startsWith("account/login")
                || relationPath.startsWith("account/register")){
            //直接走正常逻辑，不做拦截
            return;
        }

        //从请求头中去找到第一个token
        String token = requestContext.getHeaders().getFirst("token");
        if(!Strings.isNullOrEmpty(token)){
            //查询自己的信息
            final User self = UserFactory.findByToken(token);
            if(self != null){
                //给当前请求添加一个SecurityContext（上下文）
                requestContext.setSecurityContext(new SecurityContext() {
                    //主体部分
                    @Override
                    public Principal getUserPrincipal() {
                        //user实现接口 Principal
                        return self;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        //可以在这里写入用户的权限，role是权限名
                        //可以管理管理员权限等等
                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        //检查https，默认即可false
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return null;
                    }
                });
                //写入上下文后就返回
                return;
            }
        }

        //token为空，则直接返回一个账户需要登录的Model
        ResponseModel model = ResponseModel.buildAccountError();
        //停止一个请求的继续下发，调用该方法后直接返回请求
        //不会走到Service
        Response response = Response.status(Response.Status.OK).entity(model).build();
        requestContext.abortWith(response);
    }
}
