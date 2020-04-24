package com.learn;

import org.junit.Test;


/**
 * @author xu.rb
 * @since 2020-04-23 23:14
 */
public class MyTest {


    /**
     * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。
     * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     */
    @Test
    public void test01(){
        String str = "We Are Happy";
        String str1 = "We Are Happy";
        String str3 = "We Are Happy";
        //方法一：直接用JDK String类提供的方法
        System.out.println("use String.replace() : "+str1.replace(" ","%20"));

        //方法二：使用char[]数组
        char[] chars = str.toCharArray();
        String result = "";

        for (int i = 0; i < chars.length; i++) {
            char c = ' ';
            if(c == chars[i]){
                result = result + "%20";
            }else{
                result = result + chars[i];
            }
        }
        System.out.println("use char[] : " +result);

        //方法三：使用byte数组 需要把字符型变量再次转为String
        byte[] bytes = str3.getBytes();
        String result1 = "";
        for (int i = 0; i < bytes.length; i++) {
            if(' ' == bytes[i]){
                result1 = result1 + "%20";
            }else{
                byte[] convert = new byte[1];
                convert[0] = bytes[i];
                result1 = result1 + new String(convert);
            }
        }
        System.out.println("use byte[] : " +result1);
    }
}
