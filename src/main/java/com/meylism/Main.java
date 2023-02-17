package com.meylism;

public class Main {
    public static void main(String[] args) {
        Facade facade = new IMDBFacade("https://www.imdb.com/chart/top/");
        facade.scrape();
        facade.report(null);
    }
}