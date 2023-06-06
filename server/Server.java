package server;

import annotation.Common;
import annotation.Compensation;
import annotation.Contracts;
import annotation.Marketing;
import common.Customer;
import common.Employee;
import common.Product;
import compensation.Claim;
import contract.Contract;
import marketing.Board;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface Server extends Remote {

    @Common
    Customer getCustomer(String customerId) throws RemoteException;

    @Common
    List<Customer> getCustomers() throws RemoteException;

    @Common
    Employee getEmployee(String employeeId) throws RemoteException;
    
    @Contracts
    public List<Product> getProduct()throws RemoteException;
    
    @Contracts
    public boolean createProduct(Product product)throws RemoteException;
    
    @Contracts
    public List<Contract> getContract()throws RemoteException;
    
    @Contracts
    public void setUnderwriting(Contract forUnderWritedContract)throws RemoteException;
    
    @Contracts 
    public boolean updateProduct(Product product) throws RemoteException;

    @Contracts
    public List<Product> getProduct() throws RemoteException;

    @Contracts
    public boolean createProduct(Product product) throws RemoteException;

    @Contracts
    public List<Contract> getContract() throws RemoteException;

    @Contracts
    public void setUnderwriting(Contract forUnderWritedContract) throws RemoteException;


    @Compensation
    Claim getClaim(String claimId) throws RemoteException;

    @Compensation
    List<Claim> getClaims() throws RemoteException;

    @Compensation
    boolean updateClaim(Claim claim) throws RemoteException;

    @Compensation
    boolean createClaim(Claim claim) throws RemoteException;

    @Marketing
    List<Product> getProducts() throws RemoteException;

    @Marketing
    void createContract(Contract contract) throws RemoteException;

    @Marketing
    void createBoard(Board board) throws RemoteException;

    @Marketing
    List<Board> getBoards() throws RemoteException;

    @Marketing
    void updateBoard(Board board) throws RemoteException;

}
