package com.flp.ems.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocator {

        
    
    /**
     * Gets a <code>DataSource</code> using the given JNDI name.
     *
     * @param dataSourceJndiName The <code>DataSource</code>'s JNDI name.
     *
     * @return DataSource The <code>DataSource</code>.
     *
     * @throws ServiceLocatorException If there are JNDI lookup problems.
     *
     * @see javax.sql.DataSource
     */
    public static DataSource getDataSource(String dataSourceJndiName) throws ServiceLocatorException {
        //TODO 3 declare a local variable dataSource of type DataSource and initialize it to null
		DataSource dataSource = null;

        try {
            Context ctx = new InitialContext();
            Context envContext  = (Context)ctx.lookup("java:/comp/env");
            
            dataSource = (DataSource) envContext.lookup(dataSourceJndiName);
            
            
        } catch (ClassCastException cce) {
            //TODO 5 wrap ClassCastException in ServiceLocatorException and throw
        	throw new ServiceLocatorException(cce);
        	
        } catch (NamingException ne) {
            //TODO 6 wrap NamingException in ServiceLocatorException and throw
        	throw new ServiceLocatorException(ne);
        }
		
		//TODO 7 return dataSource
        return dataSource;
    }


   



}
