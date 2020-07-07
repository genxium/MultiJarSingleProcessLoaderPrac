package com.mycompany.app;

public class Lib implements LibMBean {
    public String reverse(final String str) {
        StringBuilder sb=new StringBuilder(str);
        sb.reverse();
        return sb.toString();
    }
}
