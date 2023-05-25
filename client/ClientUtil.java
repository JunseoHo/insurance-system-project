package client;

import annotation.Common;
import customer.Customer;
import compensation.Status;
import employee.Employee;
import exception.DateFormatException;
import exception.EmptyInputException;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ClientUtil {

    public static final String NA = "NA";

    ///////////////////////////////////////////////////////////
    //// common
    ///////////////////////////////////////////////////////////

    @Common
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

    @Common
    public static Customer customerLogin(Server server, BufferedReader reader) throws IOException {
        Customer customer = null;
        while (customer == null) {
            String inputId = getInput("아이디를 입력하세요. (x 입력 시 종료)", reader);
            if (inputId.equals("x")) return null;
            if ((customer = server.getCustomer(inputId)) == null) System.out.println("로그인에 실패했습니다.");
        }
        return customer;
    }

    @Common
    public static Employee employeeLogin(Server server, BufferedReader reader) throws IOException {
        Employee employee = null;
        while (employee == null) {
            String inputId = getInput("아이디를 입력하세요. (x 입력 시 종료)", reader);
            if (inputId.equals("x")) return null;
            if ((employee = server.getEmployee(inputId)) == null) System.out.println("로그인에 실패했습니다.");
        }
        return employee;
    }

    ///////////////////////////////////////////////////////////
    //// contract
    ///////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////
    //// marketing
    ///////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////
    //// compensation
    ///////////////////////////////////////////////////////////

    public static String getReviewResult(BufferedReader reader) {
        String value = null;
        while (value == null) {
            try {
                value = ClientUtil.getInput("Review Result (ACCEPTED / REJECTED)", reader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!value.equals(Status.ACCEPTED.toString()) && !value.equals(Status.REJECTED.toString()))
                value = null;
        }
        return value;
    }

    public static String getInputDate(BufferedReader reader) {
        String value = null;
        while (value == null) {
            try {
                if (!ClientUtil.checkDateFormat(value = ClientUtil.getInput("사고날짜", reader)))
                    throw new DateFormatException();
            } catch (DateFormatException | IOException e) {
                value = null;
                System.out.println(e.getMessage());
            }
        }
        return value;
    }

    public static String getInputText(String label, BufferedReader reader) {
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

    public static boolean checkDateFormat(String date) {
        try {
            SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy/MM/dd");
            dateFormatParser.setLenient(false);
            dateFormatParser.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String getInput(String message, BufferedReader reader) throws IOException {
        System.out.print(message + "\n" + ">> ");
        return reader.readLine().trim();
    }
}
