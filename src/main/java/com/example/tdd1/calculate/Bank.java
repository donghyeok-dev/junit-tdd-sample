package com.example.tdd1.calculate;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Bank {
    private  HashMap<Pair, Integer> rates = new LinkedHashMap<>();

    public void addRate(String from, String to, Integer rate) {
        rates.put(new Pair(from, to), rate);
    }
    public Money reduce(Expression source, String to) {
        return source.reduce(this, to);
    }

    public int rate(String from, String to) {
        if(from.equals(to)) return 1;

        return rates.get(new Pair(from, to));
    }


}
