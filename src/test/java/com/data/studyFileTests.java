package com.data;

import org.junit.jupiter.api.Test;
import org.thymeleaf.spring5.context.SpringContextUtils;

import java.io.File;

/**
 * 关于学习文件的测试类，比如改名、移动文件等,
 */
public class studyFileTests {

    private static String path = "E:\\sync\\huge\\java\\学习视频汇总\\Spring5\\狂神说Spring5";


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
            System.out.println("Done");
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

    /**
     * 在10以内的数字编号前补零，比如“1、Spring简介.flv”改为“01、Spring简介.flv”
     */
    @Test
    public void addZero() {
        Boolean confirmExecute = true;
        String splitChar = "、";//记录数字和后面的标题之间的间隔符号
        //遍历文件名，通过截取字符串获得编号，如果是10以内且没有零的的则加零；
        File mabyDirectory = new File(path);
        if (mabyDirectory.isDirectory()) {
            for (File studyFile : mabyDirectory.listFiles()) {
                String studyFileName = studyFile.getName();
                //如果存在分隔符的话才分割
                if (studyFileName.indexOf(splitChar) != -1) {
                    int splitCharIndex = studyFileName.indexOf(splitChar);
                    String mabyNumber = studyFileName.substring(0, splitCharIndex);//序号部分
                    String titlePath = studyFileName.substring(splitCharIndex);//标题内容部分
                    try {
                        int sortNum = Integer.parseInt(mabyNumber);
                        if (sortNum > 0 && sortNum < 10) {
                            System.out.println("旧标题分割：" + mabyNumber + "==" + titlePath);//打印看一下什么情况
                            StringBuffer sb = new StringBuffer();
                            sb.append("0" + mabyNumber + titlePath);
                            if (confirmExecute) {
                                studyFile.renameTo(new File(studyFile.getParent() + "\\" + sb));
                            }
                            System.out.println("新标题：" + sb.toString());
                            System.out.println("");
                        }
                    } catch (Exception e) {
                        System.out.println("前缀不合法（非数字）：" + studyFileName);
                    }


                }
            }
        }
    }
}
