package com.dth.spring_boot_shoe.service.impl;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class CommonService {

    private static final Pattern WHITESPACE = Pattern.compile("[\\s\\p{Punct}&&[^-]]");
    private static final Pattern NORMAL=Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    /**
     * Hàm bỏ dấu tiếng Việt
     *
     * @param nội dung cần bỏ dấu
     * @return nội dung sau khi đã bỏ dấu
     */
    public String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        temp = NORMAL.matcher(temp).replaceAll("");
        temp=WHITESPACE.matcher(temp).replaceAll("-");
        temp=temp.toLowerCase(Locale.ROOT)
                .replaceAll("đ", "d")
                .replaceAll("-{2,}","-")
                .replaceAll("^-|-$","");
        return temp;
    }
}
