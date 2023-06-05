package server;

import annotation.Common;
import annotation.Compensation;
import annotation.Contracts;
import common.Customer;
import common.Employee;
import common.Product;
import compensation.Claim;
import contract.Contract;
import dao.*;
import marketing.Board;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServerImpl extends UnicastRemoteObject implements Server {

    private static final int PORT = 40021;
    private static final String NAME = "SERVER";
    private static CustomerDAO customerDAO;
    private static EmployeeDAO employeeDAO;
    private static ClaimDAO claimDAO;
    private static ProductDAO productDAO;
    private static ContractDAO contractDAO;
    private static BoardDAO boardDAO;

    public ServerImpl() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "localhost");
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.bind(NAME, new ServerImpl());
            System.out.println("서버가 성공적으로 시작되었습니다.");
            customerDAO = new CustomerDAO();
            employeeDAO = new EmployeeDAO();
            claimDAO = new ClaimDAO();
            productDAO = new ProductDAO();
            contractDAO = new ContractDAO();
            boardDAO = new BoardDAO();
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
            System.out.println("레지스트리 등록에 실패했습니다.");
        }
    }

    ///////////////////////////////////////////////////////////
    //// common
    ///////////////////////////////////////////////////////////

    @Common
    public Customer getCustomer(String customerId) throws RemoteException {
        List<Customer> customers = customerDAO.findCustomers();
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) return customer;
        }
        return null;
    }

    @Common
    public List<Customer> getCustomers() {
        return customerDAO.findCustomers();
    }

    @Common
    public Employee getEmployee(String employeeId) throws RemoteException {
        return employeeDAO.findByEmployeeId(employeeId);
    }

    ///////////////////////////////////////////////////////////////////
    ///// contract service
    ///////////////////////////////////////////////////////////////////

    @Contracts
    public List<Product> getProduct() throws RemoteException {
        List<Product> products = productDAO.findProducts();
        return products;
    }

    @Contracts
    public boolean createProduct(Product product) throws RemoteException {
        return productDAO.addProduct(product);
    }

    @Contracts
    public List<Contract> getContract() throws RemoteException {
        return contractDAO.findContracts();
    }

    @Contracts
    public void setUnderwriting(Contract forUnderWritedContract) throws RemoteException {
        contractDAO.updateContract(forUnderWritedContract);
    }


    ///////////////////////////////////////////////////////////////////
    ///// marketing service
    ///////////////////////////////////////////////////////////////////

    @Override
    public List<Product> getProducts() {
        return productDAO.findProducts();
    }

    @Override
    public void createContract(Contract contract) {
        contractDAO.addContract(contract);
    }

    @Override
    public void createBoard(Board board) throws RemoteException {
        boardDAO.addBoard(board);
    }

    @Override
    public List<Board> getBoards() throws RemoteException {
        return boardDAO.findAll();
    }

    @Override
    public void updateBoard(Board board) throws RemoteException {
        boardDAO.updateBoard(board);
    }

    ///////////////////////////////////////////////////////////
    //// compensation service
    ///////////////////////////////////////////////////////////

    @Compensation
    public Claim getClaim(String claimId) throws RemoteException {
        return claimDAO.findByClaimId(claimId);
    }

    @Compensation
    public List<Claim> getClaims() throws RemoteException {
        return claimDAO.findAll();
    }

    @Compensation
    public boolean updateClaim(Claim claim) throws RemoteException {
        claimDAO.updateClaim(claim);
        return true;
    }

    @Compensation
    public boolean createClaim(Claim newClaim) throws RemoteException {
        List<Employee> employees = employeeDAO.findAll();
        Map<String, Integer> table = new HashMap<>();
        for (Employee employee : employees)
            if (employee.getDepartment().equals("investigating")) table.put(employee.getEmployeeId(), 0);
        List<Claim> claims = claimDAO.findAll();
        for (Claim claim : claims) {
            int count = table.get(claim.getEmployeeId()) + 1;
            table.put(claim.getEmployeeId(), count);
        }
        String investigator = null;
        for (String employeeId : table.keySet()) {
            if (investigator == null || (table.get(investigator) > table.get(employeeId))) investigator = employeeId;
        }

        employees = employeeDAO.findAll();
        table = new HashMap<>();
        for (Employee employee : employees)
            if (employee.getDepartment().equals("supporting")) table.put(employee.getEmployeeId(), 0);
        claims = claimDAO.findAll();
        for (Claim claim : claims) {
            if (!claim.getReviewer().equals("NA")) {
                System.out.println(claim.getReviewer());
                int count = table.get(claim.getReviewer()) + 1;
                table.put(claim.getEmployeeId(), count);
            }
        }
        String supporter = null;
        for (String employeeId : table.keySet()) {
            if (supporter == null || (table.get(supporter) > table.get(employeeId))) supporter = employeeId;
        }

        newClaim.setEmployeeId(investigator);
        newClaim.setReviewer(supporter);
        claimDAO.addClaim(newClaim);
        return true;
    }


}