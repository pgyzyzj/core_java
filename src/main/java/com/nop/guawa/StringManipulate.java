package com.nop.guawa;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

/**
 * Created by yangzijun on 17-2-22.
 */
public class StringManipulate {

    public static void join() {
        String[] strArr = {"a,b,c", null};
        System.out.println(Joiner.on(",").skipNulls().join(strArr));
        ;
        System.out.println(Joiner.on(",").useForNull("[N/A]").join(strArr));
        ;
    }

    public static void split() {
        String str = "a b c";
        Iterator<String> it = Splitter.on(" ").split(str).iterator();
        List<String> list = Lists.newArrayList(it);
        System.out.println(list);
    }

    public static void match() {
        String str = "This invoice has an id of 192/10/10";
        CharMatcher charMatcher = CharMatcher.DIGIT.or(CharMatcher.is('/'));
        System.out.println(charMatcher.retainFrom(str));
    }

    public static void caseFormat(){
        String pearPhpName = "Really_Fucked_Up_PHP_PearConvention_That_Looks_UGLY_because_of_no_NAMESPACES";
        String javaAndCPPName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, pearPhpName);
        System.out.println(javaAndCPPName);
    }

    public static void main(String[] args) {
        join();
        split();
        match();
        caseFormat();
    }


}
