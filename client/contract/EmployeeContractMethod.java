package client.contract;

import annotation.Contract;
import common.Employee;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;

import static client.common.ClientUtil.getInput;

@Contract
public class EmployeeContractMethod {

    public static void selectContractMenu(Server server, BufferedReader reader, Employee employee) throws IOException {
        printContractMenu();
        switch (getInput("번호를 선택해주세요.", reader)) {

        }
    }

    private static void printContractMenu() {
        System.out.println("\n***** 계약 메뉴 *****");
    }
}
