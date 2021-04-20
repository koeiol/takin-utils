package com.koeiol.takin.framework.utils.chinesenumber;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 1兆=10^12这个用法在古代中国文献中已有记载，由小到大依次为一、十、百、千、万、亿、兆、京、垓、秭、穰、沟、涧、正、载、极、恒河沙、阿僧祇、那由他、不可思议、无量大数，万以下是十进制，万以后则为万进制，即万万为亿，万亿为兆、万京为垓；小数点以下为“十退位”，名称依次为分、厘、毫、丝、忽、微、纤、沙、尘、埃、渺、莫、模糊、逡巡、须臾、瞬息、弹指、刹那、...
 * <p>
 * Created by koeiol@github.com on 2017/5/24.
 */
public class TakinChineseNumber {

    private static final BigDecimal MAX_VALUE = new BigDecimal("9999999999999999.99");
    private static final BigDecimal MIN_VALUE = new BigDecimal("0.0000001");
    private static final String FORMAT_PATTERN = "0000000000000000.0000000";

    private static final String[] CHINESE_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[][] CHINESE_UNIT = {{"", "拾", "佰", "仟"}, {"兆", "亿", "万", ""}, {"角", "分", "厘", "毫", "丝", "忽", "微"}};

    private static final String[] BASE_UNIT = {"圆", "整"};

    private final BigDecimal convertValue;

    private TakinChineseNumber(BigDecimal convertValue) {
        this.convertValue = convertValue;
    }

    public static TakinChineseNumber from(BigDecimal convertValue) {
        return new TakinChineseNumber(convertValue);
    }

    public static TakinChineseNumber from(String convertValue) {
        return from(new BigDecimal(convertValue));
    }

    public String toChineseNumber() {
        return null;
    }

    public String toChineseMoney() {
        if (convertValue == null || convertValue.compareTo(BigDecimal.ZERO) == 0)
            return CHINESE_NUMBER[0] + BASE_UNIT[0] + BASE_UNIT[1];
        if (convertValue.compareTo(MAX_VALUE) > 0 || convertValue.compareTo(MIN_VALUE) < 0)
            return convertValue.toString();
        String[] parts = new DecimalFormat(FORMAT_PATTERN).format(convertValue).split("\\.");
        StringBuilder result = new StringBuilder();
        boolean start = false, zero = false, zeroed = false;
        for (int i = 0; i < 4; i++) {
            int groupValue = Integer.parseInt(parts[0].substring(i * 4, (i + 1) * 4));
            if (start)
                if ((zero || groupValue < 1000) && !zeroed) {
                    result.append(CHINESE_NUMBER[0]);
                    zeroed = true;
                }
            if (groupValue > 0) {
                result.append(groupConvert(groupValue)).append(CHINESE_UNIT[1][i]);
                start = true;
                zeroed = false;
            }
            zero = groupValue > 0 && groupValue % 10 == 0;
        }
        if (convertValue.compareTo(BigDecimal.ONE) > -1)
            result.append(BASE_UNIT[0]);
        if (!parts[1].equals("00")) {
            if (result.length() > 0 && (parts[0].charAt(parts[0].length() - 1) == '0' || parts[1].charAt(0) == '0'))
                result.append(CHINESE_NUMBER[0]);
            for (int i = 0; i < parts[1].length(); i++) {
                int single = parts[1].charAt(i) - '0';
                if (single > 0)
                    result.append(CHINESE_NUMBER[single]).append(CHINESE_UNIT[2][i]);
            }
        } else
            result.append(BASE_UNIT[1]);
        return result.toString();

    }

    private static String groupConvert(int groupValue) {
        if (groupValue > 9 && groupValue < 20)
            return CHINESE_UNIT[0][1] + (groupValue % 10 > 0 ? CHINESE_NUMBER[groupValue % 10] : "");
        String stringValue = String.valueOf(groupValue);
        StringBuilder result = new StringBuilder();
        boolean valid = false, zero = false, zerod = false;
        for (int i = 0; i < stringValue.length(); i++) {
            int intValue = stringValue.charAt(stringValue.length() - i - 1) - '0';
            if (intValue > 0) {
                valid = true;
                zero = false;
                zerod = false;
            } else if (valid)
                zero = true;
            if (valid) {
                if (!zerod)
                    result.insert(0, CHINESE_NUMBER[intValue] + (zero ? "" : CHINESE_UNIT[0][i]));
                if (zero)
                    zerod = true;
            }
        }
        return result.toString();
    }

}
