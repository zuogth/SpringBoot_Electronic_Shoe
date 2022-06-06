package com.dth.spring_boot_shoe.utils;

import com.ibm.icu.text.RuleBasedNumberFormat;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern WHITESPACE = Pattern.compile("[\\s\\p{Punct}&&[^-]]");
    private static final Pattern NORMAL=Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    /**
     * Hàm bỏ dấu tiếng Việt
     *
     * @param nội dung cần bỏ dấu
     * @return nội dung sau khi đã bỏ dấu
     */
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        temp = NORMAL.matcher(temp).replaceAll("");
        temp=WHITESPACE.matcher(temp).replaceAll("-");
        temp=temp.toLowerCase(Locale.ROOT)
                .replaceAll("đ", "d")
                .replaceAll("-{2,}","-")
                .replaceAll("^-|-$","");
        return temp;
    }

    public static String convertMoneyToString(Long money){
        String output="";
        try {
            RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(new Locale("vi"), RuleBasedNumberFormat.SPELLOUT);
            output = ruleBasedNumberFormat.format(money) + " Đồng";
            output=output.replace("lẻ", "linh");
        } catch (Exception e) {
            output = "không đồng";
        }
        return output.toUpperCase();
    }

    public static String toMoney(BigDecimal price){
        Locale locale = new Locale("it","IT");
        NumberFormat vn =  NumberFormat.getInstance(locale);
        long price2=price.longValue();
        return vn.format(price2);
    }
}
