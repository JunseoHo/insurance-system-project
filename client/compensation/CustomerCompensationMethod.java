package client.compensation;

import annotation.Compensation;
import client.common.ClientUtil;
import common.Employee;
import compensation.Claim;
import compensation.Status;
import common.Customer;
import contract.Contract;
import exception.DateFormatException;
import exception.EmptyInputException;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static client.common.ClientUtil.NA;
import static client.common.ClientUtil.getInput;

@Compensation
public class CustomerCompensationMethod {

    public static void selectCompensationMenu(Server server, BufferedReader reader, Customer customer) throws IOException {
        printCompensationMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {
            case "1" -> createClaim(server, reader, customer);
            default -> System.out.println("잘못된 입력입니다.");
        }
    }

    private static void printCompensationMenu() {
        System.out.println("\n***** 보상 메뉴 *****");
        System.out.println("1. 보험금을 청구한다.");
    }

    private static void createClaim(Server server, BufferedReader reader, Customer customer) throws IOException {
        String[] values = new String[12];
        values[0] = UUID.randomUUID().toString();
        values[1] = customer.getCustomerId();
        values[2] = NA;
        values[3] = getInputDate(reader);
        values[4] = getInputType(server, customer, "계약상품아이디", reader);
        values[6] = getInputText("사고장소", reader);
        values[5] = getInputText("사고내용", reader);
        values[7] = NA;
        values[8] = "0";
        values[9] = NA;
        values[10] = Status.REPORTING.toString();
        if (server.createClaim(new Claim(values))) {
            Claim claim = server.getClaim(values[0]);
            claim.print();
            server.getEmployee(claim.getEmployeeId()).print();
            Employee reviewer = server.getEmployee(claim.getReviewer());
            System.out.println("*** 심사 직원 정보 ***");
            System.out.println("직원 아이디 : " + reviewer.getEmployeeId());
            System.out.println("직원 성명 : " + reviewer.getName());
            getInput("보험금 청구가 완료되었습니다. (아무 키나 입력하여 메인화면으로)", reader);
        } else System.out.println("보험금 청구에 실패했습니다.");
    }

    private static String getInputDate(BufferedReader reader) {
        String value = null;
        while (value == null) {
            try {
                if (!checkDateFormat(value = ClientUtil.getInput("사고날짜", reader)))
                    throw new DateFormatException();
            } catch (DateFormatException | IOException e) {
                value = null;
                System.out.println(e.getMessage());
            }
        }
        return value;
    }

    private static String getInputType(Server server, Customer customer, String label, BufferedReader reader) {
        String value = null;
        while (value == null) {
            try {
                List<Contract> contracts = server.getContract();
                for (Contract contract : contracts) {
                    if (contract.getCustomerId().equals(customer.getCustomerId()) && contract.getUnderwriting()) {
                        System.out.println(contract.getProductId() + " : " + contract.getTermsOfSubscription());
                    }
                }
                if ((value = ClientUtil.getInput(label, reader))
                        .replace(" ", "_")
                        .equals(""))
                    throw new EmptyInputException();
            } catch (EmptyInputException | IOException e) {
                value = null;
                System.out.println(e.getMessage());
            }
        }
        return value;
    }

    private static String getInputText(String label, BufferedReader reader) {
        String value = null;
        while (value == null) {
            try {
                if ((value = ClientUtil.getInput(label, reader))
                        .replace(" ", "_")
                        .equals(""))
                    throw new EmptyInputException();
            } catch (EmptyInputException | IOException e) {
                value = null;
                System.out.println(e.getMessage());
            }
        }
        return value;
    }

    private static boolean checkDateFormat(String date) {
        try {
            SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy/MM/dd");
            dateFormatParser.setLenient(false);
            dateFormatParser.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
