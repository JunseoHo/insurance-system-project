package client.marketing;

import annotation.Marketing;
import common.Customer;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;

import static client.common.ClientUtil.getInput;

@Marketing
public class CustomerMarketingMethod {


    public static void selectMarketingMenu(Server server, BufferedReader reader, Customer customer) throws IOException {
        printMarketingMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {

        }
    }

    private static void printMarketingMenu() {
        System.out.println("\n***** 마케팅 메뉴 *****");
    }

}
