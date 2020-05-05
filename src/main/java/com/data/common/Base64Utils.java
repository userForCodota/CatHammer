package com.data.common;

import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

/**
 * 将图片转成base64一遍储存在数据库中
 */
public class Base64Utils {

    /**
     * 字符串利用base64加密(进行编码)
     *
     * @param originalString 原始文本
     * @return 编码后（加密后）
     */
    public static String encode(String originalString) {
        byte[] bytes = originalString.getBytes();
        //Base64 加密
        String encoded = Base64.getEncoder().encodeToString(bytes);
        return encoded;
    }

    /**
     * 将base64加密过的信息进行解码，还原原本的字符串信息
     *
     * @param codeString 编码
     * @return 原字符串信息
     */
    public static String decode(String codeString) {
        byte[] decoded = Base64.getDecoder().decode(codeString);
        String decodeStr = new String(decoded);
        return decodeStr;
    }

    /**
     * 通过图片地址将互联网图片转成base64格式（拼接前端代码）
     *
     * @return
     */
    public static String internetPicToBase64(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (url == null) {
            return "";
        }
        ByteArrayOutputStream data = new ByteArrayOutputStream();//存读出来的内容
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream inputStream = httpURLConnection.getInputStream();
            //拿到输入流之后，将内容读取到内存中
            //设置篮子大小
            byte[] basket = new byte[1024];
            int tempLength = -1;
            while ((tempLength = inputStream.read(basket)) != -1) {
                data.write(basket, 0, tempLength);
            }
            inputStream.close();
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("\t[目标图片无法加载]\t" + e.getMessage());
        }
        //对字节数组进行base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.toByteArray());
    }


    /**
     * 将已经转成base64的图片编码组装成前端标签的格式，比如<img src="data:image/jpg;base64,${value}" class="images" border="5px"/>，src的值是本方法的处理目标
     *
     * @param base64PicValue 已经转成base64的图片的编码
     * @param picType        图片的类型
     * @return
     */
    public static String Base64PicToSrc(String base64PicValue, String picType) {
        StringBuffer sb = new StringBuffer();
//        sb.append("data:image/").append(picType)
        return null;
    }
}
