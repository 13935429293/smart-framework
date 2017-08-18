package org.xiaoyang.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码解码工具类
 */
public final class CodecUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);
    private static final String UTF8 = "UTF-8";

    /**
     * url 编码
     * @param source
     * @return
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, UTF8);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode failure.", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * url 解码
     * @param source
     * @return
     */
    public static String decodeURL(String source){
        String target;
        try {
            target = URLDecoder.decode(source, UTF8);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode failure.", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
