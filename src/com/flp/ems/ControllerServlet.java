package com.flp.ems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flp.ems.domain.Employee;
import com.flp.ems.service.EmployeeServiceImpl;
import com.flp.ems.util.Constants;

public class ControllerServlet extends HttpServlet {

	private static final String ACTION_KEY = "action";
	private static final String HOME_PAGE = "homePage";
	private static final String SEARCH_EMPLOYEE = "searchEmployee";
    private static final String VIEW_EMPLOYEES = "showAllEmployees";
    private static final String ADD_EMPLOYEE = "addEmployee";
    private static final String SAVE_EMPLOYEE = "saveEmployee";
    private static final String MODIFY_EMPLOYEE = "modifyEmployee";
    private static final String DELETE_EMPLOYEE = "deleteEmployee";
    private static final String ERROR_KEY = "errorMessage";
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String actionName = request.getParameter(ACTION_KEY);
        String destinationPage = null; 
        EmployeeServiceImpl service = new EmployeeServiceImpl();
        
        /*WebApplicationContext appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        service = appContext.getBean("service", EmployeeServiceImpl.class);*/
        
        if(VIEW_EMPLOYEES.equals(actionName))
        {
        	ArrayList<Employee> emps = null;
        	String type = request.getParameter("type"),value;
        	
        	System.out.println("type"+type);
        	
        	if(type!=null){
        		//TO SHOW SEARCHED EMPLOYEE
        		if(type.equals("name")){
        			value = request.getParameter("name");
        			System.out.println("value "+value);
        		}
        		else if(type.equals("emailId")){
        			value = request.getParameter("emailId");
        			System.out.println("value "+value);
        		}
        		else{
        			value = request.getParameter("kinId");
        			System.out.println("value "+value);
        		}
        			
        		emps = new ArrayList<>();
        		Employee emp = service.searchEmployee(type, value);
        		if(emp!=null)
        			emps.add(emp);
        		
        	}
        	else{
        		//TO SHOW ALL EMPLOYEES IN DB
        		emps = service.getAllEmployees();
        	}
        	
        	request.setAttribute("empList", emps);        	
        	destinationPage = "employeeList.jsp";
        	
        }
        else if(ADD_EMPLOYEE.equals(actionName))
        {
        	
        	Employee emp = new Employee();
        	request.setAttribute("emp", emp);
        	destinationPage = "employeeForm.jsp";
        	
        }
        else if(SAVE_EMPLOYEE.equals(actionName))
        {
        	Employee emp = null;
        	HashMap<String, String> employeeData = new HashMap<>();
        	int empId = -1;
        	
        	if(!request.getParameter("empId").equals("0")){
        		empId = Integer.parseInt(request.getParameter("empId"));
        		emp = service.searchEmployeeById(empId);
        	}

        	
        	employeeData.put(Constants.name, request.getParameter("name"));
    		employeeData.put(Constants.phoneNo, request.getParameter("phoneNo"));
    		employeeData.put(Constants.dateOfBirth, request.getParameter("dateOfBirth"));
    		System.out.println(request.getParameter("dateOfBirth"));
    		employeeData.put(Constants.dateOfJoining, request.getParameter("dateOfJoining"));
    		System.out.println(request.getParameter("dateOfJoining"));
    		employeeData.put(Constants.address, request.getParameter("address"));
    		employeeData.put(Constants.departmentId, request.getParameter("deptId"));
    		System.out.println(request.getParameter("deptId"));
    		employeeData.put(Constants.projectId, request.getParameter("projectId"));
    		employeeData.put(Constants.roleId, request.getParameter("roleId"));
    		
        	
        	
        	if(emp==null){
        		service.addEmployee(employeeData);
        		//TODO Show message employee added successfully
        	}
        	else{
        		service.modifyEmployee(employeeData,emp);
        		//TODO Show message data modified successfully
        	}
        	
        	//Showing all Employee list after adding/updating an employee
        	ArrayList<Employee> emps = service.getAllEmployees();
        	request.setAttribute("empList", emps);
        	destinationPage = "employeeList.jsp";
        	
        }
        else if(MODIFY_EMPLOYEE.equals(actionName))
        {
        	int empId = -1;
        	if(!request.getParameter("empId").equals("0"))
        		empId = Integer.parseInt(request.getParameter("empId"));
        	Employee emp = service.searchEmployeeById(empId);
        	
        	request.setAttribute("emp", emp);
        	
        	destinationPage = "employeeForm.jsp";
        	
        }
        else if(DELETE_EMPLOYEE.equals(actionName))
        {
        	String empIds[] = request.getParameterValues("empId");
        	
        	if(empIds!=null)
        			service.removeEmployee(empIds);
        	
        	//Showing all Employee list after deleting employees
        	ArrayList<Employee> emps = service.getAllEmployees();
        	request.setAttribute("empList", emps);
        	destinationPage = "employeeList.jsp";
        }
        else if(SEARCH_EMPLOYEE.equals(actionName))
        {
        	destinationPage="searchEmployee.jsp";
        }
        else if(HOME_PAGE.equals(actionName))
        {
        	destinationPage="index.jsp";
        }
        
        else
        {
            String errorMessage = "[" + actionName + "] is not a valid action.";
            request.setAttribute(ERROR_KEY, errorMessage);
        }
        
        
        RequestDispatcher rd = request.getRequestDispatcher(destinationPage);
        System.out.println(destinationPage);
        rd.forward(request, response);
        
	}
	
}
