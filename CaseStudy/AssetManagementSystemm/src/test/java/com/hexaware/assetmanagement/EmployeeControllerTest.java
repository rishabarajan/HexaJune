package com.hexaware.assetmanagement;

import com.hexaware.assetmanagement.controller.EmployeeController;
import com.hexaware.assetmanagement.model.Employee;
import com.hexaware.assetmanagement.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllerTest {

    private EmployeeController employeeController;
    private FakeEmployeeService fakeEmployeeService;
    private Employee sampleEmployee;

    @BeforeEach
    void setUp() throws Exception {
        employeeController = new EmployeeController();
        fakeEmployeeService = new FakeEmployeeService();

        // 🔧 Inject fake service via reflection
        Field field = EmployeeController.class.getDeclaredField("employeeService");
        field.setAccessible(true);
        field.set(employeeController, fakeEmployeeService);

        sampleEmployee = new Employee(1, "John Doe", "johnd", "pass123", "john@example.com",
                "9876543210", "NYC", "EMPLOYEE", "Male");

        fakeEmployeeService.addEmployee(sampleEmployee);
    }

    @Test
    void testAddEmployee() {
        Employee newEmp = new Employee(2, "Jane Doe", "janed", "pass456", "jane@example.com",
                "1234567890", "LA", "EMPLOYEE", "Female");

        ResponseEntity<Employee> response = employeeController.addEmployee(newEmp);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("janed", response.getBody().getUserName());
    }

    @Test
    void testUpdateEmployee() {
        sampleEmployee.setContactNumber("0000000000");

        ResponseEntity<Employee> response = employeeController.updateEmployee(sampleEmployee);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("0000000000", response.getBody().getContactNumber());
    }

    @Test
    void testDeleteEmployee() {
        ResponseEntity<String> response = employeeController.deleteEmployee(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Employee Record Deleted...", response.getBody());
    }

    @Test
    void testGetAllEmployees() {
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testLogin_Success() {
        Employee login = new Employee();
        login.setUserName("johnd");
        login.setPassword("pass123");

        ResponseEntity<String> response = employeeController.employeeLogin(login);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Login successful", response.getBody());
    }

    @Test
    void testLogin_Failure() {
        Employee login = new Employee();
        login.setUserName("johnd");
        login.setPassword("wrong");

        ResponseEntity<String> response = employeeController.employeeLogin(login);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void testSearchById_Found() {
        ResponseEntity<Employee> response = employeeController.searchEmployeeById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("johnd", response.getBody().getUserName());
    }

    @Test
    void testSearchById_NotFound() {
        ResponseEntity<Employee> response = employeeController.searchEmployeeById(999);

        assertEquals(500, response.getStatusCodeValue()); // Exception thrown
        assertNull(response.getBody());
    }

    @Test
    void testSearchByUsername_Found() {
        ResponseEntity<Employee> response = employeeController.searchEmployeeByUserName("johnd");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getEmployeeName());
    }

    @Test
    void testSearchByUsername_NotFound() {
        ResponseEntity<Employee> response = employeeController.searchEmployeeByUserName("unknown");

        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    // --- Fake EmployeeService Implementation ---
    static class FakeEmployeeService extends EmployeeService {
        private final Map<Integer, Employee> empMap = new HashMap<>();
        private final Map<String, Employee> usernameMap = new HashMap<>();

        @Override
        public String addEmployee(Employee employee) {
            empMap.put(employee.getEmployeeId(), employee);
            usernameMap.put(employee.getUserName(), employee);
            return "Employee Record Inserted...";
        }

        @Override
        public String updateEmployee(Employee employee) {
            empMap.put(employee.getEmployeeId(), employee);
            usernameMap.put(employee.getUserName(), employee);
            return "Employee Record Updated...";
        }

        @Override
        public String deleteEmployee(int employeeId) {
            Employee removed = empMap.remove(employeeId);
            if (removed != null) {
                usernameMap.remove(removed.getUserName());
            }
            return "Employee Record Deleted...";
        }

        @Override
        public List<Employee> getAllEmployees() {
            return new ArrayList<>(empMap.values());
        }

        @Override
        public Employee searchEmployeeById(int employeeId) {
            if (!empMap.containsKey(employeeId)) {
                throw new RuntimeException("Employee not found");
            }
            return empMap.get(employeeId);
        }

        @Override
        public boolean validateLogin(String username, String password) {
            Employee emp = usernameMap.get(username);
            return emp != null && emp.getPassword().equals(password);
        }

        @Override
        public Employee searchEmployeeByUserName(String userName) {
            if (!usernameMap.containsKey(userName)) {
                throw new RuntimeException("Not found");
            }
            return usernameMap.get(userName);
        }
    }
}
