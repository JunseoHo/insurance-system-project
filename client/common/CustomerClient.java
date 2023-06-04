package client.common;

import annotation.Common;
import client.contract.CustomerContractMethod;
import client.marketing.CustomerMarketingMethod;
import client.compensation.CustomerCompensationMethod;
import common.Customer;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static client.common.ClientUtil.*;

@Common
public class CustomerClient {

    private static final String HOST = "localhost";
    private static final int PORT = 40021;
    private static final String NAME = "SERVER";
    private static Server server = null;
    private static Customer customer = null;
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        System.setProperty("java.rmi.server.hostname", "localhost");
        if ((server = connect(HOST, PORT, NAME)) == null) return;
        if ((customer = customerLogin(server, reader)) == null) return;
        System.out.println("로그인에 성공했습니다.");
        selectMainMenu();
        reader.close();
    }

    private static void selectMainMenu() throws IOException {
        while (true) {
            printMainMenu();
            switch (getInput("번호를 선택해주세요.", reader)) {
                case "1" -> CustomerContractMethod.selectContractMenu(server, reader, customer);
                case "2" -> CustomerMarketingMethod.selectMarketingMenu(server, reader, customer);
                case "3" -> CustomerCompensationMethod.selectCompensationMenu(server, reader, customer);
                case "x" -> {
                    System.out.println("클라이언트를 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n*****   광고   *****");
        System.out.println("신뢰와 친절로 보답하는 보험사, oo 보험!");
        System.out.println("\n***** 고객 안내 *****");
        System.out.println("아래 메뉴에서 원하시는 항목을 선택해주세요.");
        System.out.println("\n***** 메인 메뉴 *****");
        System.out.println("1. 계약 메뉴.");
        System.out.println("2. 마케팅 메뉴");
        System.out.println("3. 보상 메뉴");
        System.out.println("x. 종료한다.");
    }

}
