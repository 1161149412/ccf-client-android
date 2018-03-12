package cn.cnlinfo.ccf.utils;

import java.util.Random;

/**
 * Created by Administrator on 2017/10/16 0016.
 */

public class ObtainVerificationCode {

    public static String createVerificationCode() {
        Random random = new Random();
        char[]buffer = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L',
                'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y','Z',
                '0','1','2','3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd',
                'e','f', 'g', 'h', 'j', 'k', 'l','m', 'n', 'p', 'q', 'r', 's', 't',
                'u','v', 'w', 'x', 'y','z'};
       StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++){
            int num = random.nextInt(buffer.length-i);
            char c = buffer[num];
            builder.append(c);
        }
        return builder.toString();
    }


    public static String createNumVerificationCode() {
        Random random = new Random();
        char[]buffer = {'0','1','2','3', '4', '5', '6', '7', '8', '9'};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++){
            int num = random.nextInt(buffer.length-i);
            char c = buffer[num];
            builder.append(c);
        }
        return builder.toString();
    }
}
