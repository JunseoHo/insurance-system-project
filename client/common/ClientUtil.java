package client.common;

import annotation.Common;
import common.Customer;
import common.Employee;
import exception.NotOneOrTwoException;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

@Common
public class ClientUtil {

    public static final String NA = "NA";

    public static Server connect(String host, int port, String name) {
        try {
            return (Server) LocateRegistry
                    .getRegistry(host, port)
                    .lookup(name);
        } catch (RemoteException | NotBoundException e) {
            System.out.println("서버 연결에 실패했습니다.");
            return null;
        }
    }

    public static Customer customerLogin(Server server, BufferedReader reader) throws IOException {
        Customer customer = null;
        while (customer == null) {
            String inputId = getInput("아이디를 입력하세요. (x 입력 시 종료)", reader);
            if (inputId.equals("x")) return null;
            if ((customer = server.getCustomer(inputId)) == null) System.out.println("로그인에 실패했습니다.");
        }
        return customer;
    }

    public static Employee employeeLogin(Server server, BufferedReader reader) throws IOException {
        Employee employee = null;
        while (employee == null) {
            String inputId = getInput("아이디를 입력하세요. (x 입력 시 종료)", reader);
            if (inputId.equals("x")) return null;
            if ((employee = server.getEmployee(inputId)) == null) System.out.println("로그인에 실패했습니다.");
        }
        return employee;
    }


    public static String getInput(String message, BufferedReader reader) throws IOException {
        System.out.print(message + "\n" + ">> ");
        return reader.readLine().trim();
    }
    
}
