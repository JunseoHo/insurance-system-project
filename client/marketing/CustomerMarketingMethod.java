package client.marketing;

import annotation.Marketing;
import common.Customer;
import common.Product;
import contract.Contract;
import marketing.Board;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static client.common.ClientUtil.getInput;

@Marketing
public class CustomerMarketingMethod {
    public static void selectMarketingMenu(Server server, BufferedReader reader, Customer customer) throws IOException {
        printMarketingMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {
            case "1" -> joinClaim(server, reader, customer);//계약을 신청한다.
            case "2" -> createStatement(server, reader, customer);//문의를 작성한다.
            default -> System.out.println("잘못된 입력입니다.");
        }
    }

    private static void printMarketingMenu() {
        System.out.println("\n***** 마케팅 메뉴 *****");
        System.out.println("1. [계약신청]");
        System.out.println("2. [문의게시판]");
    }

    private static void joinClaim(Server server, BufferedReader reader, Customer customer) throws IOException {
        List<Product> productList = server.getProducts();
        int index = -1;
        while (index == -1) {
            for (Product product : productList)
                System.out.println(product.getId() + " : " + product.getProductName());
            String productId = getStandardInput("상품 아이디를 선택하십시오.", reader);
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getId().equals(productId)) {
                    index = i;
                    break;
                }
            }
            if (index == -1)
                System.out.println("상품을 찾지 못했습니다.");
        }
        String userInput = getStandardInput("고객님 가입 신청 하시겠습니까?(Y/N)", reader);
        if (userInput.equals("Y")) {
            System.out.println(">>가입 신청이 완료되었습니다. 초기화면으로 돌아갑니다.");
            Contract contract = new Contract("1YEAR", 0, 0, productList.get(index).getCompensationDetail(), customer.getCustomerId(), UUID.randomUUID().toString(), 0, productList.get(index).getId(), false);
            server.createContract(contract);
        } else if (userInput.equals("N")) {
            System.out.println(">>가입 신청이 취소되었습니다. 초기화면으로 돌아갑니다.");
        } else {
            System.out.println("잘못된 입력입니다.");
        }
//        if (server.createClaim(new Claim(values))) {
//             Claim claim = server.getClaim(values[0]);
//             claim.print();
//             server.getCustomer(claim.getCustomerId()).print();
//             getInput("보험금 청구가 완료되었습니다. (아무 키나 입력하여 메인화면으로)", reader);
//         } else System.out.println("보험금 청구에 실패했습니다.");
//     }

    }

    private static void createStatement(Server server, BufferedReader reader, Customer customer) throws IOException {
        String input = getStandardInput("1. 문의작성하기, 2. 취소하기", reader);
        String[] statement = new String[2];
        switch (input) {
            case "1":
                System.out.println("문의내용을 입력하세요");
                statement[0] = getStandardInput("제목입력:", reader);
                statement[1] = getStandardInput("문의내용입력:", reader);
                break;
            default:
                System.out.println("Invaild Input, Try Again");
                break;
        }
        String userInput = getStandardInput("제출하시겠습니까?(Y/N)", reader);
        if (userInput.equals("Y")) {
            server.createBoard(new Board(UUID.randomUUID().toString(), statement[0], statement[1], customer.getCustomerId(), "", 0));
            System.out.println(">>문의 작성이 완료되었습니다. 초기화면으로 돌아갑니다.");
        } else if (userInput.equals("N")) {
            System.out.println(">>문의 작성이 취소되었습니다. 초기화면으로 돌아갑니다.");
        }
    }

    private static String getStandardInput(String message, BufferedReader reader) throws IOException {
        System.out.print(message + "\n" + ">> ");
        return reader.readLine().trim();
    }
}
