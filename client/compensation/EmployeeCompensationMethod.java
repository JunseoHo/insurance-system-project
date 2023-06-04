package client.compensation;

import annotation.Compensation;
import client.common.ClientUtil;
import compensation.Claim;
import compensation.Status;
import common.Employee;
import exception.InvalidCompensationException;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static client.common.ClientUtil.getInput;

@Compensation
public class EmployeeCompensationMethod {

    public static void selectCompensationMenu(Server server, BufferedReader reader, Employee employee) throws IOException {
        printCompensationMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {
            case "1" -> uploadReport(server, reader, employee);
            case "2" -> reviewClaim(server, reader, employee);
            case "3" -> payCompensation(server, reader);
            default -> System.out.println("잘못된 입력입니다.");
        }
    }

    private static void printCompensationMenu() {
        System.out.println("\n***** 보상 메뉴 *****");
        System.out.println("1. 손해조사보고서를 업로드한다.");
        System.out.println("2. 보험금 지급 여부를 심사한다.");
        System.out.println("3. 보험금을 지급한다.");
    }

    @Compensation
    private static void uploadReport(Server server, BufferedReader reader, Employee employee) throws IOException {
        List<Claim> claims = new ArrayList<>();
        for (Claim claim : server.getClaims()) {
            if (claim.getStatus().equals(Status.REPORTING.toString()) && claim.getEmployeeId().equals(employee.getEmployeeId()))
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
            decideCompensation(server, reader, claim);
        } else
            System.out.println("You have been failed to upload report!");
    }

    @Compensation
    private static void decideCompensation(Server server, BufferedReader reader, Claim claim) throws IOException {
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
    private static void reviewClaim(Server server, BufferedReader reader, Employee employee) throws IOException {
        List<Claim> claims = server.getClaims();
        Claim claim = null;
        while (claim == null) {
            for (Claim element : claims) {
                if (element.getStatus().equals(Status.REVIEWING.toString()))
                    System.out.println(element);
            }
            String claimId = ClientUtil.getInput("Input claim ID", reader);
            for (Claim element : claims) {
                if (element.getClaimId().equals(claimId))
                    claim = element;
            }
            if (claim == null)
                System.out.println("Not found!");
        }
        String review = getReviewResult(reader);
        claim.setStatus(review);
        claim.setReviewer(employee.getEmployeeId());
        if (server.updateClaim(claim)) {
            System.out.println("You have been reviewed claim successfully!");
        } else
            System.out.println("You have been failed to review claim!");
    }

    private static void payCompensation(Server server, BufferedReader reader) throws IOException {
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

    public static String getReviewResult(BufferedReader reader) {
        String value = null;
        while (value == null) {
            try {
                value = ClientUtil.getInput("Review Result (accepted / rejected)", reader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!value.equals(Status.ACCEPTED.toString()) && !value.equals(Status.REJECTED.toString())) {
                value = null;
                System.out.println("Invalid Input");
            }
        }
        return value;
    }
}
