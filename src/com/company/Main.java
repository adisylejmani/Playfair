package com.company;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        HashMap hm = new HashMap();
        hm.put(7, "shtate");
        hm.put(13, "un");
        hm.put(102, "un jam ti je ne jemi ajdas;ldas");
        hm.put(1, 2020);
        hm.put("adi", "10.01.2000");
        if (!hm.containsKey(13))
            hm.put(13, "ti");
        else
            System.out.println("Qelesi 13 ekziston");
        System.out.println(hm.get("adi"));

    }
}
