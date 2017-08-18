package org.xiaoyang.framework.bean;

import org.xiaoyang.framework.util.CastUtil;

import java.util.Map;

/**
 * 封装请求参数
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String, Object> getMap() {
        return paramMap;
    }
}
