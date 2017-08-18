package org.xiaoyang.framework;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.xiaoyang.framework.bean.Data;
import org.xiaoyang.framework.bean.Handler;
import org.xiaoyang.framework.bean.Param;
import org.xiaoyang.framework.bean.View;
import org.xiaoyang.framework.helper.BeanHelper;
import org.xiaoyang.framework.helper.ConfigHelper;
import org.xiaoyang.framework.helper.ControllerHelper;
import org.xiaoyang.framework.helper.LoaderHelper;
import org.xiaoyang.framework.util.CodecUtil;
import org.xiaoyang.framework.util.JsonUtil;
import org.xiaoyang.framework.util.ReflectionUtil;
import org.xiaoyang.framework.util.StreamUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 转发
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化相关的helper类
        LoaderHelper.init();
        // 获取serverContext对象，用于注册servlet
        ServletContext servletContext = config.getServletContext();
        // 处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getJspPath() + "*");
        // 处理静态资源的配置
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        Handler handler = ControllerHelper.getHandler(requestPath, requestMethod);
        if (null != handler) {
            // 获取controller类及其实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            // 创建请求参数
            Map<String, Object> params = new HashMap<String, Object>();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramsName = parameterNames.nextElement();
                String paramValue = req.getParameter(paramsName);
                params.put(paramsName, paramValue);
            }

            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotBlank(body)) {
                String[] bodyParams = StringUtils.split(body, "&");
                if (ArrayUtils.isNotEmpty(bodyParams)) {
                    for (String param : bodyParams) {
                        String[] nameValue = StringUtils.split(param, "=");
                        if (ArrayUtils.isNotEmpty(nameValue) && nameValue.length == 2) {
                            params.put(nameValue[0], nameValue[1]);
                        }
                    }
                }
            }

            Param param = new Param(params);
            // 调用action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            // 处理返回值
            if (result instanceof View) { // page请求
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotBlank(path)) {
                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        Map<String, Object> modal = view.getModal();
                        for (Map.Entry<String, Object> entry : modal.entrySet()) {
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getJspPath() + path).forward(req, resp);
                    }
                }
            } else if (result instanceof Data) {
                Data data = (Data) result;
                Object modal = data.getModal();
                if (null != modal) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJson(modal);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }

        }
    }
}
