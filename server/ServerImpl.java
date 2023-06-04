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
            if (customer.getCustomerId().equals(customerId))
                return customer;
        }
        return null;
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
    public boolean createProduct(Product product)throws RemoteException{
        return productDAO.addProduct(product);
    }

    @Contracts
    public List<Contract> getContract() throws RemoteException {
        List<Contract> contracts = contractDAO.findContracts();
        for(int i=0; i<contracts.size(); i++) {
            if(contracts.get(i).getUnderwriting()) contracts.remove(i);
        }
        return contracts;
    }

    @Contracts
    public void setUnderwriting(Contract forUnderWritedContract) throws RemoteException {
        contractDAO.updateContract(forUnderWritedContract);
    }




    ///////////////////////////////////////////////////////////////////
    ///// marketing service
    ///////////////////////////////////////////////////////////////////

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
            if (employee.getDepartment().equals("investigating"))
                table.put(employee.getEmployeeId(), 0);
        List<Claim> claims = claimDAO.findAll();
        for (Claim claim : claims) {
            int count = table.get(claim.getEmployeeId()) + 1;
            table.put(claim.getEmployeeId(), count);
        }
        String investigator = null;
        for (String employeeId : table.keySet()) {
            if (investigator == null || (table.get(investigator) > table.get(employeeId)))
                investigator = employeeId;
        }
        newClaim.setEmployeeId(investigator);
        claimDAO.addClaim(newClaim);
        return true;
    }

}