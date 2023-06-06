package client.contract;

import annotation.Contracts;

import common.Product;
import compensation.Claim;
import contract.Contract;
import common.Employee;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static client.common.ClientUtil.getInput;

@Contracts
public class EmployeeContractMethod {

    private static int rate;

    public static void selectContractMenu(Server server, BufferedReader reader, Employee employee) throws IOException {
        printContractMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {
            case "1" -> developProduct(server, reader);
            case "2" -> computeRate(reader);
            case "3" -> showProductList(server, reader);
            case "4" -> updateProductInput(server, reader);
            case "5" -> underwritingContract(server, reader);
            case "6" -> manageClaimPayoutInitial(server, reader);
            default -> System.out.println("잘못된 입력입니다.");
        }
    }

    private static void manageClaimPayoutInitial(Server server, BufferedReader reader) throws IOException, RemoteException {
        showClaimPayoutMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {
            case "1" -> ListClaimPayout(server, reader);
            case "2" -> analysisClaimPayout(server, reader);
            default -> System.out.println("***** 잘못된 입력입니다. *****");
        }

    }

    private static void analysisClaimPayout(Server server, BufferedReader reader) throws RemoteException {
        List<Claim> temp = server.getClaims();
        List<Claim> claims = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getStatus().equals("accepted")) {
                claims.add(temp.get(i));
            }
        }
        if (claims.size() == 0) {
            System.out.println("제지급금 목록이 비어있습니다.");
        } else {
            int sum = 0;
            int num = 0;
            for (int i = 0; i < claims.size(); i++) {
                sum += claims.get(i).getCompensation();
                num++;
            }
            int avg = sum / num;
            System.out.println("대기 인원 : " + num + "명");
            System.out.println("총 제지급금 금액 : " + sum + "원");
            System.out.println("제지급금 평균 : " + avg + "원");
        }
    }

    private static void ListClaimPayout(Server server, BufferedReader reader) throws RemoteException {
        List<Contract> contracts = server.getContract();
        System.out.print(toTableSetClaimPayout());
        for (int i = 0; i < contracts.size(); i++) {
            System.out.print(contracts.get(i).getClaimPayout());
        }
    }

    private static void showClaimPayoutMenu() {
        System.out.println("***** 제지급금 관리 *****");
        System.out.println("1. 제지급금 목록");
        System.out.println("2. 제지급금 분석");
    }

    private static void underwritingContract(Server server, BufferedReader reader) throws IOException {
        List<Contract> temp = server.getContract();
        List<Contract> contracts = new ArrayList<>();
        System.out.println("***** 인수심사 목록 *****");
        for (int i = 0; i < temp.size(); i++) {
            if (!temp.get(i).getUnderwriting()) {
                Contract contract = temp.get(i);
                System.out.println("[" + contract.getContractId() + "] " + contract.getProductId() + "상품에 대한 " + contract.getCustomerId() + "님의 계약입니다.");
                contracts.add(temp.get(i));
            }
        }
        String answer = getInput("인수심사할 계약의 아이디를 입력하여 주십시오", reader);
        Contract forUnderWritedContract = null;
        for (int i = 0; i < contracts.size(); i++) {
            if (answer.equals(contracts.get(i).getContractId())) {
                forUnderWritedContract = contracts.get(i);
            }
        }
        int answerForFee = Integer.parseInt(getInput("보험료를 입력하여 주십시오.", reader));
        forUnderWritedContract.setPremiums(answerForFee);
        server.setUnderwriting(forUnderWritedContract);
        System.out.println("저장 완료");
    }

    private static void updateProductInput(Server server, BufferedReader reader) throws IOException {
        String ans = getInput("수정할 상품의 ID를 입력하여 주십시오", reader);
        List<Product> products = server.getProduct();
        for (int i = 0; i < products.size(); i++) {
            if (ans.equals(products.get(i).getId())) {
                updateProduct(reader, ans, server);
//				server.updateProduct(product)
            }
        }
    }

    private static void updateProduct(BufferedReader reader, String productId, Server server) {
        Product product = null;
        String[] values = new String[7];
        try {
            System.out.println("************상품 개발*************");
            System.out.println("----상품의 정보를 입력하여 주십시오.----");
            values[1] = getInput("상품 명 ", reader);
            values[2] = getInput("대상 고객 ", reader);
            values[3] = getInput("보상 세부 내용 ", reader);
            System.out.println("수집된 요율(저장한 요율이 있다면 저장이라고 써 주십시오) :  ");
            int value = 0;
            String answer = reader.readLine().trim();
            if (answer.equals("저장")) values[4] = "" + rate;
            else {
                try {
                    value = Integer.parseInt(answer);
                } catch (NumberFormatException e) {
                    value = getInputRate("숫자를 입력하여 주세요.", reader);
                }
                values[4] = "" + value;
            }
            values[5] = getInput("손익 분석 ", reader);

            System.out.println("보험료 : ");
            int val = 0;
            try {
                val = Integer.parseInt(reader.readLine().trim());
            } catch (NumberFormatException e) {
                val = getInputRate("숫자를 입력하여 주세요.", reader);
            }
            values[6] = "" + val;

            String answerFinal = getInputYesOrNo("****상품을 수정하시겠습니까? (Y/N)****", reader);
            if (answerFinal.equalsIgnoreCase("y")) {
                server.updateProduct(new Product(productId, values[1], values[2], values[3], Integer.parseInt(values[4]), values[5], Integer.parseInt(values[6])));

            } else if (answerFinal.equalsIgnoreCase("n")) {
                System.out.println("***** 상품 개발이 취소되었습니다. *****");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showProductList(Server server, BufferedReader reader) throws RemoteException {
        List<Product> products = new ArrayList<>();
        products = server.getProduct();
        System.out.print(toTableSet());
        for (int i = 0; i < products.size(); i++)
            System.out.print(products.get(i).toString());
    }

    private static void computeRate(BufferedReader reader) throws IOException {
        String input[] = new String[4];
        System.out.println("**** 대상 고객의 정보를 입력하여 주세요. ****");
        getInputNum("성별 : 1. 남자 2.여자", reader);
        input[0] = getInputNum("대상 고객의 연령대가 60대 이상입니까?\n1. 예 2.아니오", reader);
        input[1] = getInputNum("가족력이 있는 고객 전용입니까?\n1. 예 2.아니오", reader);
        input[2] = getInputNum("흡연하는 고객이 대상입니까?\n1. 예 2.아니오", reader);
        input[3] = getInputNum("상대적으로 위험한 직업을 가진 고객이 대상입니까?\n1. 예 2.아니오", reader);
        double temp = 0;
        for (int i = 0; i < input.length; i++) {
            if (Integer.parseInt(input[i]) == 1) temp += 1;
        }
        double resultRate = (100 + (temp * 25)) / 100;
        System.out.println("해당 상품의 요율은 " + resultRate + "배 입니다.");
        String answer = getInputYesOrNo("출력된 요율을 저장하시겠습니까?\n Y/N", reader);
        if (answer.equalsIgnoreCase("y")) {
            rate = (int) (resultRate * 100);
            System.out.println("***** 요율 저장 완료  *****");
        } else {
            System.out.println("***** 요율 계산 완료 *****");
        }

    }

    private static void developProduct(Server server, BufferedReader reader) throws IOException {
        String[] values = new String[7];
        try {
            System.out.println("************상품 개발*************");
            System.out.println("----상품의 정보를 입력하여 주십시오.----");
            values[0] = getInputID("상품 ID", reader, server);
            values[1] = getInput("상품 명 ", reader);
            values[2] = getInput("대상 고객 ", reader);
            values[3] = getInput("보상 세부 내용 ", reader);
            System.out.println("수집된 요율(저장한 요율이 있다면 저장이라고 써 주십시오) :  ");
            int value = 0;
            String answer = reader.readLine().trim();
            if (answer.equals("저장")) values[4] = "" + rate;
            else {
                try {
                    value = Integer.parseInt(answer);
                } catch (NumberFormatException e) {
                    value = getInputRate("숫자를 입력하여 주세요.", reader);
                }
                values[4] = "" + value;
            }
            values[5] = getInput("손익 분석 ", reader);

            System.out.println("보험료 : ");
            int val = 0;
            try {
                val = Integer.parseInt(reader.readLine().trim());
            } catch (NumberFormatException e) {
                val = getInputRate("숫자를 입력하여 주세요.", reader);
            }
            values[6] = "" + val;

            String answerFinal = getInputYesOrNo("****상품을 업로드 하시겠습니까? (Y/N)****", reader);
            if (answerFinal.equalsIgnoreCase("y")) {
//				System.out.println(values[4]);
                if (server.createProduct(new Product(values[0], values[1], values[2], values[3], Integer.parseInt(values[4]), values[5], Integer.parseInt(values[6]))))
                    System.out.println("***** 업로드 완료 *****");
            } else if (answerFinal.equalsIgnoreCase("n")) {
                System.out.println("***** 상품 개발이 취소되었습니다. *****");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void printContractMenu() {
        System.out.println("\n***** 계약 메뉴 *****");
        System.out.println("1. 상품 개발");
        System.out.println("2. 요율 계산");
        System.out.println("3. 상품 리스트 출력");
        System.out.println("4. 상품 수정");
        System.out.println("5. 인수 심사");
        System.out.println("6. 제지급금 관리");
    }

    public static String toTableSet() {
        StringBuilder table = new StringBuilder();
        table.append("--------------------------------------------------------------------------------------------------------------------\n");
        table.append(String.format("| %-10s | %-15s | %-10s | %-20s | %-5s | %-25s | %-10s| \n", "Product ID", "Product Name", "Target", "Compensation Detail", "rate", "profit n loss analysis", "premiums"));
        table.append("--------------------------------------------------------------------------------------------------------------------\n");
        return table.toString();
    }

    public static String toTableSetClaimPayout() {
        StringBuilder table = new StringBuilder();
        table.append("--------------------------------\n");
        table.append(String.format("| %-9s | %-13s |\n", "계약ID", "제지급금"));
        table.append("--------------------------------\n");
        return table.toString();
    }

    public static String getInputNum(String message, BufferedReader reader) throws IOException {
        System.out.print(message + "\n" + ">> ");
        String a = reader.readLine().trim();
        if (a.equals("1") || a.equals("2")) return a;
        else {
            System.out.println("*** 잘못된 입력입니다. ***");
            return getInputNum(message, reader);
        }
    }

    public static int getInputRate(String message, BufferedReader reader) throws IOException {
        System.out.print(message + "\n" + ">> ");
        int a = 0;
        try {
            a = Integer.parseInt(reader.readLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("*** 잘못된 입력입니다. ***");
            return getInputRate(message, reader);
        }
        return a;
    }

    public static String getInputYesOrNo(String message, BufferedReader reader) throws IOException {
        System.out.print(message + "\n" + ">> ");
        String a = reader.readLine().trim();
        if (a.equalsIgnoreCase("y") || a.equalsIgnoreCase("n")) return a;
        else {
            System.out.println("*** 잘못된 입력입니다. ***");
            return getInputYesOrNo(message, reader);
        }
    }

    public static String getInputID(String message, BufferedReader reader, Server server) throws IOException {
        System.out.print(message + "\n" + ">> ");
        List<Product> products = server.getProduct();
        String a = reader.readLine().trim();
        for (int i = 0; i < products.size(); i++) {
            if (a.equals(products.get(i).getId())) {
                System.out.println("*** 중복된 아이디입니다. ***");
                return getInputID(message, reader, server);
            }
        }
        return a;
    }
}
