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
            case "1" -> {
                if (employee.getDepartment().equals("investigating")) uploadReport(server, reader, employee);
                else System.out.println("권한이 없습니다!");
            }
            case "2" -> {
                if (employee.getDepartment().equals("supporting")) reviewClaim(server, reader, employee);
                else System.out.println("권한이 없습니다!");
            }
            case "3" -> {
                if (employee.getDepartment().equals("supporting")) payCompensation(server, reader);
                else System.out.println("권한이 없습니다!");
            }
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
            String claimId = ClientUtil.getInput("청구 아이디 입력", reader);
            for (Claim element : claims) {
                if (element.getClaimId().equals(claimId))
                    claim = element;
            }
            if (claim == null)
                System.out.println("없는 아이디입니다!");
        }
        String report = ClientUtil.getInput("보고서 입력", reader);
        claim.setReport(report);
        if (server.updateClaim(claim)) {
            System.out.println("성공적으로 보고서가 업로드 되었습니다!");
            decideCompensation(server, reader, claim);
        } else
            System.out.println("보고서 업로드에 실패하였습니다.");
    }

    @Compensation
    private static void decideCompensation(Server server, BufferedReader reader, Claim claim) throws IOException {
        int compensation = 0;
        while (compensation == 0) {
            compensation = Integer.parseInt(ClientUtil.getInput("보상금을 입력해주세요.", reader));
            try {
                if (compensation <= 0) throw new InvalidCompensationException();
            } catch (InvalidCompensationException e) {
                System.out.println(e.getMessage());
                compensation = 0;
            }
        }
        claim.setCompensation(compensation);
        claim.setStatus(Status.REVIEWING.toString());
        if (server.updateClaim(claim)) System.out.println("보상금을 성공적으로 결정하였습니다!");
        else System.out.println("보상금 결정에 실패했습니다.");
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
            String claimId = ClientUtil.getInput("청구 아이디 입력", reader);
            for (Claim element : claims) {
                if (element.getClaimId().equals(claimId))
                    claim = element;
            }
            if (claim == null)
                System.out.println("없는 아이디입니다!");
        }
        String review = getReviewResult(reader);
        claim.setStatus(review);
        claim.setReviewer(employee.getEmployeeId());
        if (server.updateClaim(claim)) {
            System.out.println("성공적으로 심사 결과가 반영되었습니다!");
        } else
            System.out.println("심사 결과 반영에 실패했습니다.");
    }

    private static void payCompensation(Server server, BufferedReader reader) throws IOException {
        List<Claim> claims = server.getClaims();
        Claim claim = null;
        while (claim == null) {
            for (Claim element : claims)
                if (element.getStatus().equals(Status.ACCEPTED.toString()))
                    System.out.println(element);
            String claimId = ClientUtil.getInput("청구 아이디 입력", reader);
            for (Claim element : claims) {
                if (element.getClaimId().equals(claimId))
                    claim = element;
            }
            if (claim == null)
                System.out.println("없는 아이디입니다.");
        }
        while (true) {
            String value = ClientUtil.getInput("보상금을 지급하시겠습니까? (OK/NO)", reader);
            if (value.equals("OK")) {
                claim.setStatus(Status.PAID.toString());
                server.updateClaim(claim);
                System.out.println("성공적으로 보상금이 지급되었습니다!");
                return;
            } else if (value.equals("NO")) {
                System.out.println("보상금 지급이 취소되었습니다.");
                return;
            } else {
                System.out.println("유효하지 않은 입력입니다.");
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
