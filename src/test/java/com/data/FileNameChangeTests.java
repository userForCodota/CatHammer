package com.data;

import com.data.common.StrUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * @ClassName FileNameChangeTests
 * @Description TODO 针对文件的名字进行加密并且去掉文件类型，解密的时候加上文件类型
 * @Author dororo
 * @Date 2021/3/4 0004 17:38
 * @Version 1.0
 */
@SpringBootTest
public class FileNameChangeTests {
    /**
     * 加密解密。辅助：有预览功能。比如“星际穿越.mp4”>>>“星际穿越mp4”>>>加密后“阿萨达的路径”
     */
    @Test
    void encryptionAndDecryption() {
        boolean isDo = false;
        String dirPath = "I:\\only_mobile_hard_disk_drive\\videos\\学术研究";
        File mabyDir = new File(dirPath);//可能是目录而不是文件
        if (mabyDir.isDirectory()) {
            File[] videoFiles = mabyDir.listFiles();
            for (File videoFile : videoFiles) {
                String newName = videoFile.getName().replaceAll(" ", "");//去空格去点
//                String newName = videoFile.getName().replace(".", "").replaceAll(" ", "");//去空格去点
                String encryption = StrUtils.encryption(newName);
                String encryption_2 = StrUtils.encryption(encryption);
                System.out.println(encryption+"=="+encryption_2);

            }
        }
    }

}
