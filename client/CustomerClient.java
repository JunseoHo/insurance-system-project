package client;

import annotation.Common;
import annotation.Compensation;
import annotation.Contract;
import annotation.Marketing;
import customer.Customer;
import compensation.Claim;
import compensation.Status;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import static client.ClientUtil.*;

public class CustomerClient {

    private static final String HOST = "localhost";
    private static final int PORT = 40021;
    private static final String NAME = "SERVER";
    private static Server server = null;
    private static Customer customer = null;
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Common
    public static void main(String[] args) throws IOException {
        if ((server = connect(HOST, PORT, NAME)) == null) return;
        if ((customer = customerLogin(server, reader)) == null) return;
        System.out.println("로그인에 성공했습니다.");
        selectMainMenu();
        reader.close();
    }

    ///////////////////////////////////////////////////////////
    //// select menu
    ///////////////////////////////////////////////////////////

    @Common
    private static void selectMainMenu() throws IOException {
        while (true) {
            printMainMenu();
            switch (getInput("번호를 선택해주세요.", reader)) {
                case "1" -> selectContractMenu();
                case "2" -> selectMarketingMenu();
                case "3" -> selectCompensationMenu();
                case "x" -> {
                    System.out.println("클라이언트를 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    @Contract
    private static void selectContractMenu() throws IOException {
        printContractMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {

        }
    }

    @Marketing
    private static void selectMarketingMenu() throws IOException {
        printMarketingMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {

        }
    }

    @Compensation
    private static void selectCompensationMenu() throws IOException {
        printCompensationMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {
            case "1" -> createClaim();
            default -> System.out.println("잘못된 입력입니다.");
        }
    }

    ///////////////////////////////////////////////////////////
    //// print menu
    ///////////////////////////////////////////////////////////

    @Common
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

    @Contract
    private static void printContractMenu() {
        System.out.println("\n***** 계약 메뉴 *****");
    }

    @Marketing
    private static void printMarketingMenu() {
        System.out.println("\n***** 마케팅 메뉴 *****");
    }

    @Compensation
    private static void printCompensationMenu() {
        System.out.println("\n***** 보상 메뉴 *****");
        System.out.println("1. 보험금을 청구한다.");
    }


    ///////////////////////////////////////////////////////////////////
    ///// contract service
    ///////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////
    ///// marketing service
    ///////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////
    //// compensation service
    ///////////////////////////////////////////////////////////

    @Compensation
    private static void createClaim() throws IOException {
        String[] values = new String[12];
        values[0] = UUID.randomUUID().toString();
        values[1] = customer.getCustomerId();
        values[2] = NA;
        values[3] = ClientUtil.getInputDate(reader);
        values[4] = ClientUtil.getInputText("청구유형", reader);
        values[6] = ClientUtil.getInputText("사고장소", reader);
        values[5] = ClientUtil.getInputText("사고내용", reader);
        values[7] = NA;
        values[8] = "0";
        values[9] = NA;
        values[10] = Status.REPORTING.toString();
        if (server.createClaim(new Claim(values))) {
            Claim claim = server.getClaim(values[0]);
            claim.print();
            server.getEmployee(claim.getEmployeeId()).print();
            getInput("보험금 청구가 완료되었습니다. (아무 키나 입력하여 메인화면으로)", reader);
        } else System.out.println("보험금 청구에 실패했습니다.");
    }

}
