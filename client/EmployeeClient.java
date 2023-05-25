package client;

import static client.ClientUtil.*;

import annotation.Common;
import annotation.Compensation;
import annotation.Contract;
import annotation.Marketing;
import employee.Employee;
import compensation.Claim;
import compensation.Status;
import exception.InvalidCompensationException;
import exception.PermissionException;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

public class EmployeeClient {

    private static final String HOST = "localhost";
    private static final int PORT = 40021;
    private static final String NAME = "SERVER";
    private static Server server = null;
    private static Employee employee = null;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws IOException {
        if ((server = connect(HOST, PORT, NAME)) == null) return;
        if ((employee = employeeLogin(server, reader)) == null) return;
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
            case "1" -> uploadReport(reader);
            case "2" -> reviewClaim(reader);
            case "3" -> payCompensation(reader);
            default -> System.out.println("잘못된 입력입니다.");
        }
    }

    ///////////////////////////////////////////////////////////
    //// print menu
    ///////////////////////////////////////////////////////////

    @Common
    private static void printMainMenu() {
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
        System.out.println("1. 손해조사보고서를 업로드한다.");
        System.out.println("2. 보험금 지급 여부를 심사한다.");
        System.out.println("3. 보험금을 지급한다.");
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
    private static void uploadReport(BufferedReader reader) throws IOException {
        List<Claim> claims = new ArrayList<>();
        for (Claim claim : server.getClaims()) {
            if (claim.getStatus().equals(Status.REPORTING.toString()))
                claims.add(claim);
        }
        Claim claim = null;
        while (claim == null) {
            for (Claim element : claims) System.out.println(element);
            String claimId = ClientUtil.getInput("Input claim ID", reader);
            for (Claim element : claims) {
                if (element.getClaimId().equals(claimId))
                    claim = element;
            }
            if (claim == null)
                System.out.println("Not found!");
        }
        String report = ClientUtil.getInput("Input report", reader);
        claim.setReport(report);
        if (server.updateClaim(claim)) {
            System.out.println("You have been uploaded report successfully!");
            decideCompensation(reader, claim);
        } else
            System.out.println("You have been failed to upload report!");
    }

    @Compensation
    private static void decideCompensation(BufferedReader reader, Claim claim) throws IOException {
        int compensation = 0;
        while (compensation == 0) {
            compensation = Integer.parseInt(ClientUtil.getInput("Input compensation", reader));
            try {
                if (compensation <= 0) throw new InvalidCompensationException();
            } catch (InvalidCompensationException e) {
                System.out.println(e.getMessage());
                compensation = 0;
            }
        }
        claim.setCompensation(compensation);
        claim.setStatus(Status.REVIEWING.toString());
        if (server.updateClaim(claim)) System.out.println("You have been decided compensation successfully!");
        else System.out.println("You have been failed to decide compensation!");
    }

    @Compensation
    private static void reviewClaim(BufferedReader reader) throws IOException {
        List<Claim> claims = server.getClaims();
        Claim claim = null;
        while (claim == null) {
            for (Claim element : claims) System.out.println(element);
            String claimId = ClientUtil.getInput("Input claim ID", reader);
            for (Claim element : claims) {
                if (element.getClaimId().equals(claimId))
                    claim = element;
            }
            if (claim == null)
                System.out.println("Not found!");
        }
        String review = ClientUtil.getReviewResult(reader);
        claim.setStatus(review);
        claim.setReviewer(employee.getEmployeeId());
        if (server.updateClaim(claim)) {
            System.out.println("You have been reviewed claim successfully!");
        } else
            System.out.println("You have been failed to review claim!");
    }

    private static void payCompensation(BufferedReader reader) throws IOException {
        List<Claim> claims = server.getClaims();
        Claim claim = null;
        while (claim == null) {
            for (Claim element : claims) System.out.println(element);
            String claimId = ClientUtil.getInput("Input claim ID", reader);
            for (Claim element : claims) {
                if (element.getClaimId().equals(claimId))
                    claim = element;
            }
            if (claim == null)
                System.out.println("Not found!");
        }
        while (true) {
            String value = ClientUtil.getInput("Would you like to pay the compensation? (OK/NO)", reader);
            if (value.equals("OK")) {
                claim.setStatus(Status.PAID.toString());
                server.updateClaim(claim);
                System.out.println("You have been paid compensation successfully!");
                return;
            } else if (value.equals("NO")) {
                System.out.println("You have been canceled");
                return;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

}
