package client.common;

import static client.common.ClientUtil.*;

import annotation.Common;
import client.compensation.EmployeeCompensationMethod;
import client.contract.EmployeeContractMethod;
import client.marketing.EmployeeMarketingMethod;
import common.Employee;
import compensation.Claim;
import compensation.Status;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Common
public class EmployeeClient {

    private static final String HOST = "localhost";
    private static final int PORT = 40021;
    private static final String NAME = "SERVER";
    private static Server server = null;
    private static Employee employee = null;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws IOException {
        System.setProperty("java.rmi.server.hostname", "localhost");
        if ((server = connect(HOST, PORT, NAME)) == null) return;
        if ((employee = employeeLogin(server, reader)) == null) return;
        System.out.println("로그인에 성공했습니다.");
        selectMainMenu();
        reader.close();
    }

    private static void selectMainMenu() throws IOException {
        while (true) {
            printMainMenu();
            switch (getInput("번호를 선택해주세요.", reader)) {
                case "1" -> EmployeeContractMethod.selectContractMenu(server, reader, employee);
                case "2" -> EmployeeMarketingMethod.selectMarketingMenu(server, reader, employee);
                case "3" -> EmployeeCompensationMethod.selectCompensationMenu(server, reader, employee);
                case "x" -> {
                    System.out.println("클라이언트를 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n***** 메인 메뉴 *****");
        System.out.println("1. 계약 메뉴.");
        System.out.println("2. 마케팅 메뉴");
        System.out.println("3. 보상 메뉴");
        System.out.println("x. 종료한다.");
    }

}
