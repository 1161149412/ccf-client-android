package cn.cnlinfo.ccf.utils;
import java.security.MessageDigest;
import java.util.List;

public class SignUtil {

    public static String signWithMd5(String signKey, List<Pair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getFirst());
            sb.append('=');
            sb.append(params.get(i).getSecond());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(signKey);
        return MD5.getMessageDigest(sb.toString().getBytes());
    }

    public static String signWithSHA512(String signKey, List<Pair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("key=");
        sb.append(signKey);
        sb.append("&");
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getFirst());
            sb.append('=');
            sb.append(params.get(i).getSecond());
            if (i < params.size() - 1) {
                sb.append('&');
            }
        }

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(sb.toString().getBytes());
            StringBuffer buf = new StringBuffer();
            byte[] bits = md.digest();
            for (int i = 0; i < bits.length; i++) {
                int a = bits[i];
                if (a < 0)
                    a += 256;
                if (a < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(a));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
