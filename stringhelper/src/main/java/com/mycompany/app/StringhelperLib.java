package com.mycompany.app;

public class StringhelperLib {
    String reverse(final String str) {
        StringBuilder sb=new StringBuilder(str);
        sb.reverse();
        return sb.toString();
    }
}
