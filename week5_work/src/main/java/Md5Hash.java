import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.security.MessageDigest;

public class Md5Hash extends UDF {
    public String evaluate(String input) {
        if (StringUtils.isBlank(input)) {
            return "input is null";
        }
        String md5_value = null;
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes("UTF8"));
            /* 方式 1 */
            for (byte item : array) {
                buffer.append(String.format("%02x", item)); // %02x 格式控制: 以十六进制输出,2为指定的输出字段的宽度.如果位数小于2,则左端补0
            }
            /* 方式 2 */
//            for (int i = 0; i < array.length; i++) {
//                buffer.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
//            }

            md5_value = buffer.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5_value;
    }

    public static void main(String[] args) {
        Md5Hash md5 = new Md5Hash();
        String md5_value = md5.evaluate("input is null");
        System.out.println(md5_value);
    }
}
