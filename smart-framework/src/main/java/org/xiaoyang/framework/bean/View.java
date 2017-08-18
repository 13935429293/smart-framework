package org.xiaoyang.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象, 返回页面数据
 */
public class View {

    /**
     * 返回路径
     */
    private String path;

    private Map<String, Object> modal;

    public View(String path) {
        this.path = path;
        this.modal = new HashMap<String, Object>();
    }

    public View addModal(String key, String value) {
        modal.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModal() {
        return modal;
    }
}
