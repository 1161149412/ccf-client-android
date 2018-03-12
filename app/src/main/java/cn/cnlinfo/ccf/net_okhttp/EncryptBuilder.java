package cn.cnlinfo.ccf.net_okhttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;

/*加密生成器*/
public class EncryptBuilder {

    private static final MediaType CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded");

    private final StringBuilder content = new StringBuilder();
    private final StringBuilder signString = new StringBuilder();

    private String mSignKey;

    public EncryptBuilder(String signKey) {
        this.mSignKey = signKey;
    }

    /**
     * Add new key-value pair.
     * 添加键值对
     */
    public EncryptBuilder add(String name, String value) {
        if (content.length() > 0) {
            content.append('&');
        }
        try {
            content.append(URLEncoder.encode(name, "UTF-8"))
                    .append('=')
                    .append(URLEncoder.encode(value, "UTF-8"));
            if (name.matches("^\\w+\\[\\d+\\]$")) {

            } else {
                if (signString.length() > 0) {
                    signString.append('&');
                }
                signString.append(URLEncoder.encode(name, "UTF-8"))
                        .append('=')
                        .append(URLEncoder.encode(value, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
        return this;
    }

    public RequestBody build() {
        if (content.length() == 0) {
            throw new IllegalStateException("Form encoded body must have at least one part.");
        }
        addSign();

        // Convert to bytes so RequestBody.create() doesn't add a charset to the content-type.
        byte[] contentBytes = content.toString().getBytes(Util.UTF_8);
        return RequestBody.create(CONTENT_TYPE, contentBytes);
    }

    //  添加签名
    private void addSign() {
        String key = mSignKey + signString.toString();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(key.getBytes());
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
            add("sign", URLEncoder.encode(buf.toString(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
