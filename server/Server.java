package server;

import annotation.Common;
import annotation.Compensation;
import customer.Customer;
import employee.Employee;
import compensation.Claim;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.List;

public interface Server extends Remote {

    ///////////////////////////////////////////////////////////
    //// common
    ///////////////////////////////////////////////////////////
    @Common
    Customer getCustomer(String customerId) throws RemoteException;

    @Common
    Employee getEmployee(String employeeId) throws RemoteException;

    ///////////////////////////////////////////////////////////////////
    ///// contract service
    ///////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////
    ///// marketing service
    ///////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////
    ///// compensation service
    ///////////////////////////////////////////////////////////////////
    @Compensation
    Claim getClaim(String claimId) throws RemoteException;

    @Compensation
    List<Claim> getClaims() throws RemoteException;

    @Compensation
    boolean updateClaim(Claim claim) throws RemoteException;

    @Compensation
    boolean createClaim(Claim claim) throws RemoteException;


}
