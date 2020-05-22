package com.data;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * 关于学习文件的测试类，比如改名、移动文件等,
 */
public class studyFileTests {

    private static String path = "F:\\dataSynchronizationCluster\\filesHugeLocal\\Java\\学习视频汇总\\消息队列之RabbitMQ\\RabbitMq经典课程";


    /**
     * 业务要求举例：“[高清 1080P] 一小时彻底搞懂RabbitMQ_P1_01.课程简介.flv”>>>>“01.课程简介.flv”
     * 可以看出需要处理的是“01之前的前缀”
     */
    @Test
    public void fileNameTests() {
        boolean isUpdate = true;
        String targetSplit = "_";//目标分割符号
        int sortOfTargetSplit = 2;//目标分割符号在整个字符串中排在第几个
        File mabyDirectory = new File(path);
        if (mabyDirectory.isDirectory()) {
            for (File file : mabyDirectory.listFiles()) {
                String name = file.getName();
                String tempName = name;
                if (name.indexOf(targetSplit) != -1) {
                    for (int i = 0; i < sortOfTargetSplit; i++) {
                        int indexTemp = tempName.indexOf(targetSplit);
                        tempName = tempName.substring(indexTemp + targetSplit.length());
                    }
                    if (isUpdate) {
                        file.renameTo(new File(file.getParent() + "\\" + tempName));
                    } else {
                        System.out.println(tempName);//得到目标名字
                    }
                }
            }
        }
    }

    /**
     * 将文件名打印出来且按照顺序排列
     */
    @Test
    public void getFileNameList() {
        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName().replace(".flv", ""));
        }
/*        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            int tempindex = 0;
            for (int j = 0; j < name.length(); j++) {
                Pattern p = Pattern.compile("[0-9]*");
                Matcher matcher = p.matcher(name.charAt(j) + "");
                if (!matcher.matches()) {
                    tempindex = j;
                    break;
                }
            }
            String substring = name.substring(0, tempindex);
            System.out.println(substring);
        }*/
    }
}
