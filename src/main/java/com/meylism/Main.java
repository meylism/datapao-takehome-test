package com.meylism;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static void main(String[] args) {
        Facade facade = new IMDBFacade("https://www.imdb.com/chart/top/");
        facade.scrape();
    }
}