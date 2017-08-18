package org.xiaoyang.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 类型转换工具类
 */
public final class CastUtil {

    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    public static String castString(Object obj, String defaultValue) {
        return obj == null ? defaultValue : String.valueOf(obj);
    }

    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    public static double castDouble(Object obj, double defaultValue) {
        double returnValue = defaultValue;
        if (null != obj) {
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    returnValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    returnValue = defaultValue;
                }
            }
        }
        return returnValue;
    }

    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    public static long castLong(Object obj, long defaultValue) {
        long returnValue = defaultValue;
        if (null != obj) {
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    returnValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    returnValue = defaultValue;
                }
            }
        }
        return returnValue;
    }

    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    public static int castInt(Object obj, int defaultValue) {
        int returnValue = defaultValue;
        if (null != obj) {
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    returnValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    returnValue = defaultValue;
                }
            }
        }
        return returnValue;
    }

    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean returnValue = defaultValue;
        if (null != obj) {
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    returnValue = Boolean.parseBoolean(strValue);
                } catch (NumberFormatException e) {
                    returnValue = defaultValue;
                }
            }
        }
        return returnValue;
    }

}
