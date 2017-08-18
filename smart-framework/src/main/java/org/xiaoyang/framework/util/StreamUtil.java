package org.xiaoyang.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流处理工具
 */
public final class StreamUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从流中读取字符串
     * @param in
     * @return
     */
    public static String getString(InputStream in){
        StringBuffer sb = new StringBuffer();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("get string failure from input stream.", e);
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
