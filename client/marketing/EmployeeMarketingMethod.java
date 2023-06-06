package client.marketing;

import annotation.Marketing;
import common.Customer;
import common.Employee;
import marketing.Board;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import static client.common.ClientUtil.getInput;

@Marketing
public class EmployeeMarketingMethod {

    public static void selectMarketingMenu(Server server, BufferedReader reader, Employee employee) throws IOException {
        printMarketingMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {
            case "1" -> contractInformationUpdate(server, reader, employee);//계약상세정보업데이트
            case "2" -> clientInformationAnalysis(server, reader, employee);//고객정보분석
            default -> System.out.println("잘못된 입력입니다.");
        }
    }

    private static void printMarketingMenu() {
        System.out.println("\n***** 마케팅 메뉴 *****");
        System.out.println("1. [계약내용업데이트]");
        System.out.println("2. [고객정보분석]");
    }

    private static void contractInformationUpdate(Server server, BufferedReader reader, Employee employee) throws IOException {
        List<Board> boards = server.getBoards();
        for (Board board : boards)
            if (board.getAnswer().equals(""))
                System.out.println("[" + board.getStatementId() + "]" + board.getCustomerId() + "님의 문의 : " + board.getTitle() + " -> " + board.getContent());
        String input = getStandardInput("문의 아이디를 입력해주세요", reader);
        int index = -1;
        for (int i = 0; i < boards.size(); i++)
            if (boards.get(i).getStatementId().equals(input))
                index = i;
        String userInput2 = getStandardInput("답변을 입력해주세요", reader);
        Board board = boards.get(index);
        board.setAnswer(userInput2);
        board.setProcessed(1);
        server.updateBoard(board);
        System.out.println("답변이 완료되었습니다.");
    }

    private static void clientInformationAnalysis(Server server, BufferedReader reader, Employee employee) throws IOException {
        String input = getStandardInput("1. 고객평균연령대확인 , 2. 가입된고객성비확인", reader);
        switch (input) {
            case "1" -> System.out.println("가입된 고객에 평균 연령층은 : " + getAgeGroup(server));
            case "2" -> System.out.println("가입된 고객에 성비는 : " + getGenderRate(server));
            default -> System.out.println("Invaild Input, Try Again");
        }
    }

    private static String getAgeGroup(Server server) throws RemoteException {
        List<Customer> customers = server.getCustomers();
        int total = 0;
        for (Customer customer : customers) {
            total += 2022 - Integer.parseInt(customer.getBirth().split("-")[0]);
        }
        total /= customers.size();
        return total + "세입니다";
    }

    private static String getGenderRate(Server server) throws RemoteException {
        List<Customer> customers = server.getCustomers();
        float male = 0;
        for (Customer customer : customers) {
            if (customer.getGender()) ++male;
        }
        return "남자(" + (male / customers.size()) * 100 + "%), 여자(" + ((customers.size() - male) / customers.size()) * 100 + "%)입니다.";
    }

    private static String getStandardInput(String message, BufferedReader reader) throws IOException {
        System.out.print(message + "\n" + ">> ");
        return reader.readLine().trim();
    }
}