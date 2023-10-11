package com.chenpp.spring.samples.i18n.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author April.Chen
 * @date 2023/9/11 2:23 下午
 **/
@Activate
public class DubboHeaderFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String value = RpcContext.getContext().getAttachment("lang");
        //根据业务存储数据
        if (StringUtils.isNotEmpty(value)) {
            RpcContext.getContext().setAttachment("lang", value);
        }
        return invoker.invoke(invocation);
    }
}
