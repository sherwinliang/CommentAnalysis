package com.ca.utils;

/* @description:  cookies Cookies cookie饼干
                 dog dogs狗粮
                coconut椰子油
                flour flours pancakes 面粉
                coffee Coffee coffees Cappuccino cappuccino咖啡
                chips chip薯条
                Popcorn popcorn爆米花
                drink 饮料
*/
public enum ProductTypeEnum {
    Cookies("饼干","cookies,Cookies,cookie"),
    Dog("狗粮","dog,dogs"),
    Coconut("椰子油","coconut"),
    Flour("面粉","flour,flours,pancakes"),
    Coffee("咖啡","coffee,Coffee,coffees,Cappuccino,cappuccino"),
    Chips("薯条","chips,chip"),
    Popcorn("爆米花","Popcorn,popcorn"),
    Drink("饮料","drink");
    private String name;
    private String keyWords;

    ProductTypeEnum(String name, String keyWords) {
        this.name = name;
        this.keyWords = keyWords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}
