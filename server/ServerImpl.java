package server;

import annotation.Common;
import annotation.Compensation;
import customer.Customer;
import employee.Employee;
import compensation.Claim;
import dao.ClaimDAO;
import dao.CustomerDAO;
import dao.EmployeeDAO;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ServerImpl extends UnicastRemoteObject implements Server {

    private static final int PORT = 40021;
    private static final String NAME = "SERVER";
    private static CustomerDAO customerDAO;
    private static EmployeeDAO employeeDAO;
    private static ClaimDAO claimDAO;

    public ServerImpl() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.bind(NAME, new ServerImpl());
            System.out.println("서버가 성공적으로 시작되었습니다.");
            customerDAO = new CustomerDAO();
            employeeDAO = new EmployeeDAO();
            claimDAO = new ClaimDAO();
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
        return (Customer) customerDAO.findByCustomerId(customerId);
    }

    @Common
    public Employee getEmployee(String employeeId) throws RemoteException {
        return (Employee) employeeDAO.findByEmployeeId(employeeId);
    }

    ///////////////////////////////////////////////////////////////////
    ///// contract service
    ///////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////
    ///// marketing service
    ///////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////
    //// compensation service
    ///////////////////////////////////////////////////////////

    @Compensation
    public Claim getClaim(String claimId) throws RemoteException {
        return (Claim) claimDAO.findByClaimId(claimId);
    }

    @Compensation
    public List<Claim> getClaims() throws RemoteException {
        return (List<Claim>) claimDAO.findByClaimId();
    }

    @Compensation
    public boolean updateClaim(Claim claim) throws RemoteException {
        claimDAO.updateClaim(claim);
        return true;
    }

    @Compensation
    public boolean createClaim(Claim claim) throws RemoteException {
        claim.setEmployeeId("E001"); // temporary code
        claimDAO.addClaim(claim);
        return true;
    }

}