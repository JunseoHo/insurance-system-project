package client.contract;

import annotation.Contracts;
import common.Customer;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;

import static client.common.ClientUtil.getInput;

@Contracts
public class CustomerContractMethod {


    public static void selectContractMenu(Server server, BufferedReader reader, Customer customer) throws IOException {
        printContractMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {

        }
    }

    private static void printContractMenu() {
        System.out.println("\n***** 계약 메뉴 *****");
    }


}
