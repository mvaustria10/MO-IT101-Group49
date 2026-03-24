/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ms2project;
/*
*
* COMPUTER PROGRAMING 1
*    GROUP 49
*       PARTICIPATING MEMBER: MARK VEMHER AUSTRIA// NOTHING FOLLOWS
*          PLEASE CHECK README FILE FOR MORE INFO
*
*    GITHUB: mvaustria10
*
*  CODE TESTED AND RUNNING--
*/ 

//import statements
import java.io.BufferedReader;// 
//reads text files
import java.io.FileReader;//

import java.io.IOException;// haldles file reading error

import java.text.ParseException;//
// for parsing and date format
import java.text.SimpleDateFormat;//

import java.util.ArrayList;//
// store list of data
import java.util.List;//

import java.util.HashMap;//
//store key value pairs
import java.util.Map;//

import java.util.Scanner;//read inputs 
class PayrollData {
    public double cutoff1Hours, cutoff2Hours;
    public double grossCutoff1, grossCutoff2;
    public double sss, philHealth, pagIbig, tax;
    public double totalDeductions;
    public double netCutoff1, netCutoff2;
    public double monthlyGross;
}

public class MS2Project {
    
// Employee CSV column indices
    static final int EMP_ID_INDEX = 0;
    static final int EMP_LAST_NAME_INDEX = 1;
    static final int EMP_FIRST_NAME_INDEX = 2;
    static final int EMP_BIRTHDAY_INDEX = 3;
    static final int EMP_BASIC_SALARY_INDEX = 13;
    static final int EMP_HOURLY_RATE_INDEX = 18;
    
    // Attendance CSV column indices
    static final int ATT_EMP_ID_INDEX = 0;
    static final int ATT_DATE_INDEX = 3;
    static final int ATT_LOGIN_TIME_INDEX = 4;
    static final int ATT_LOGOUT_TIME_INDEX = 5;
    // ========================================================
    
    //variables
    static Scanner scanner = new Scanner(System.in);//reads user input
    static List<String[]> employeeData = new ArrayList<>();//stores employee rows
    static List<String[]> attendanceData = new ArrayList<>();//stores attendance rows
    static Map<String, String[]> employeeMap = new HashMap<>();//look up employee data
    static Map<String, List<String[]>> attendanceMap = new HashMap<>();//look up attendance data
    static SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy"); // formats the date this way ("M/d/yyyy")
    
    static final String VALID_USER_1 = "employee"; // employee user name
    static final String VALID_USER_2 = "Payroll_staff"; // staff user name
    static final String VALID_PASSWORD = "12345"; //pw for both
    static final double WORK_START_HOUR = 8.0; //start shift
    static final double WORK_END_HOUR = 17.0; // end shift
    
    // Months to process: June (6) to December (12)
    static final int[] MONTHS_TO_PROCESS = {6, 7, 8, 9, 10, 11, 12};
    
    // main method, login page
    public static void main(String[] args) {
        loadEmployeeData(); //read data
        loadAttendanceData(); //read data
        
        System.out.println("============================================");
        System.out.println("     WELCOME TO MOTORPH PAYROLL SYSTEM      ");
        System.out.println("============================================");
        
        boolean loggedIn = login(); //login, will ask for credentials
        
        if (!loggedIn) {
            System.out.println("\nIncorrect username and/or password."); //will show if login fails
            System.out.println("---Bye---");
        }
    }
    //login method
    static boolean login() {
        System.out.print("\nEnter Username: ");//prompt
        String username = scanner.nextLine().trim(); //reads user input
        System.out.print("Enter Password: ");//prompt
        String password = scanner.nextLine().trim(); //reads user input
        
        if ((username.equals(VALID_USER_1) || username.equals(VALID_USER_2))  //check if username entered is valid
            && password.equals(VALID_PASSWORD)) {  //check if pw is correct
            
            if (username.equals(VALID_USER_1)) {
                employeeMenu();      //
            } else {                 // menu for users
                payrollStaffMenu();  //
            }
            return true; //
        }                //  returns if login succeeded or not
        return false;    //
    }
    // employee menu
    static void employeeMenu() {
        while (true) { // loops the menu until exit
            System.out.println("\n============================================");     //
            System.out.println("           MOTORPH EMPLOYEE MENU            ");       //
            System.out.println("============================================");       // menu and selection
            System.out.println("1. Enter your employee number");                      //
            System.out.println("2. Exit the program");                                //
            System.out.println("============================================");       //
            System.out.print("Choose an option: ");                                   //
            
            String choice = scanner.nextLine().trim(); //gets user choice
            
            switch (choice) { //this will execute based on choice
                case "1" -> {
                    //
                    System.out.print("\nEnter Employee Number: ");     // shows employee details
                    String empNum = scanner.nextLine().trim();         //
                    displayEmployeeDetails(empNum);                    //
                }
                case "2" -> {
                    System.out.println("\nThank you for using MotorPH Payroll System. Goodbye!");
                    System.exit(0);
                }
                default -> // handles wrong input
                    System.out.println("\nInvalid option. Please try again.");
            }
            //this will execute based on choice
                    }
    }
     static void displayEmployeeDetails(String empNum) {
        // Input validation
        if (empNum == null || empNum.trim().isEmpty()) {
            System.out.println("\nError: Employee number cannot be empty.");
            return;
        }
        
        if (employeeMap.containsKey(empNum)) {  //check employee
            String[] emp = employeeMap.get(empNum); //get emp data
            System.out.println("\n============================================");
            System.out.println("         MOTORPH EMPLOYEE DETAILS           ");
            System.out.println("============================================");
            System.out.println("Employee Number: " + emp[EMP_ID_INDEX]);                     // CHANGED
            System.out.println("Employee Name: " + emp[EMP_FIRST_NAME_INDEX] + " " + emp[EMP_LAST_NAME_INDEX]);        // CHANGED
            System.out.println("Birthday: " + emp[EMP_BIRTHDAY_INDEX]);  // CHANGED
            System.out.println("============================================");
        } else {
            System.out.println("\nError: Employee number '" + empNum + "' does not exist.");  // IMPROVED
        }
    }
 
    //payroll staff menu
    static void payrollStaffMenu() {
        while (true) {
            System.out.println("\n============================================");
            System.out.println("        MOTORPH PAYROLL STAFF MENU                 ");
            System.out.println("============================================");
            System.out.println("1. Process Payroll");
            System.out.println("2. Exit the program");
            System.out.println("============================================");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1" -> processPayrollMenu();
                case "2" -> {
                    System.out.println("\nThank you for using MotorPH Payroll System. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("\nInvalid option. Please try again.");
            }
        }
    }
    static void processPayrollMenu() {
        while (true) {
            System.out.println("\n============================================");
            System.out.println("       PROCESS PAYROLL MENU               ");
            System.out.println("============================================");
            System.out.println("1. One employee");
            System.out.println("2. All employees");
            System.out.println("3. Exit the program");
            System.out.println("============================================");
            System.out.print("Choose an option: ");
            
            String choice;
            choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1" -> {
                    System.out.print("\nEnter Employee Number: ");
                    String empNum = scanner.nextLine().trim();
                    processPayrollForEmployee(empNum);
                }
                case "2" -> processPayrollForAllEmployees();
                case "3" -> {
                    return;
                }
                default -> System.out.println("\nInvalid option. Please try again.");
            }
        }
    }
    //load emp data
    static void loadEmployeeData() {
        String fileName;
        fileName = "src/Employee.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) { // Read and open the CSV file
            String line;
            boolean isFirstLine = true; // Skip header row
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] data = line.split(","); // Split CSV line into columns
                
                // Validate that we have all required columns
                if (data.length <= EMP_HOURLY_RATE_INDEX) {
                    System.err.println("Warning: Incomplete employee record - skipping: " + line);
                    continue;
                }
                
                employeeData.add(data); // Store row in list
                employeeMap.put(data[EMP_ID_INDEX], data); // Creates lookup by emp# using constant
            }
            
            System.out.println("\nLoaded " + employeeData.size() + " employees from CSV."); // Returns number of loaded employees
            
        } catch (IOException e) {
            System.err.println("Error loading Employee.csv: " + e.getMessage());
            System.err.println("Please ensure Employee.csv exists in the current directory.");
            System.exit(1); // Exit program if critical file is missing
        }
    }
 
    
    static void loadAttendanceData() {
        String fileName;
        fileName = "src/Attendance.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] data = line.split(",");
                
                // Validate that we have all required columns
                if (data.length <= ATT_LOGOUT_TIME_INDEX) {
                    System.err.println("Warning: Incomplete attendance record - skipping: " + line);
                    continue;
                }
                
                attendanceData.add(data);
                
                String empNum = data[ATT_EMP_ID_INDEX]; // CHANGED: Use constant
                if (!attendanceMap.containsKey(empNum)) {  // Group attendance by employee
                    attendanceMap.put(empNum, new ArrayList<>()); // Create new list for employee
                }
                attendanceMap.get(empNum).add(data);
            }
            
            System.out.println("Loaded " + attendanceData.size() + " attendance records from CSV."); // Returns number of records loaded
            
        } catch (IOException e) {
            System.err.println("Error loading Attendance.csv: " + e.getMessage());
            System.err.println("Please ensure Attendance.csv exists in the current directory.");
            System.exit(1); // Exit program if critical file is missing
        }
    }
 
   /**
     * Calculates total work hours and days worked within a specific cutoff period (1-15 or 16-end).
     * @param attendance List of attendance records for an employee
     * @param month Month number (6-12)
     * @param startDay Start day of cutoff period (1 or 16)
     * @param endDay End day of cutoff period (15 or 28-31)
     * @return Array containing [totalHours, daysWorked]
     */
    
    static double[] calculateCutoffHours(List<String[]> attendance, int month, int startDay, int endDay) {
        double totalHours = 0.0; // Hours worked in the cutoff period
        int daysWorked = 0; // Number of days worked in the period
        
        if (attendance == null || attendance.isEmpty()) {  
            return new double[]{0.0, 0};  // Returns zero if no attendance data
        }
        
        for (String[] record : attendance) {
            try {  // Parse the date
                java.util.Date date = dateFormat.parse(record[ATT_DATE_INDEX]); // CHANGED: Use constant
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(date);
                // Check if the record is in the cutoff period (spelling fixed)
                int recordMonth = cal.get(java.util.Calendar.MONTH) + 1;
                int recordDay = cal.get(java.util.Calendar.DAY_OF_MONTH);
                
                if (recordMonth == month && recordDay >= startDay && recordDay <= endDay) {
                    // Calculate daily hours and update totals
                    double hours = calculateDailyHours(record[ATT_LOGIN_TIME_INDEX], record[ATT_LOGOUT_TIME_INDEX]); // CHANGED: Use constants
                    totalHours += hours;    
                    daysWorked++;
                }
                
            } catch (ParseException e) {
                // Log warning instead of silently skipping
                System.err.println("Warning: Failed to parse date in attendance record: " + record[ATT_DATE_INDEX]);
            }
        }
        
        return new double[]{totalHours, daysWorked}; // Return the result
    }
 
    
    //calculate daily hrs. computes the number of wokr hrs for a single day based on login and logout time capped at 8 hrs
    static double calculateDailyHours(String logIn, String logOut) {
        // Validate inputs
        if (logIn == null || logOut == null || logIn.trim().isEmpty() || logOut.trim().isEmpty()) {
            System.err.println("Warning: Invalid login/logout time - returning 0 hours");
            return 0.0;
        }
        
        try {
            String[] loginParts = logIn.split(":");
            double loginHour = Double.parseDouble(loginParts[0]);
            double loginMinute = Double.parseDouble(loginParts[1]);
            double loginTime = loginHour + (loginMinute / 60.0); // Converts time format into decimal hours
            
            String[] logoutParts = logOut.split(":");
            double logoutHour = Double.parseDouble(logoutParts[0]);
            double logoutMinute = Double.parseDouble(logoutParts[1]);
            double logoutTime = logoutHour + (logoutMinute / 60.0); // Converts time format into decimal hours
            
            double effectiveStart = Math.max(loginTime, WORK_START_HOUR); // Start time capped at 8:00 AM
            double effectiveEnd = Math.min(logoutTime, WORK_END_HOUR); // End time capped at 5:00 PM
            double hoursWorked = effectiveEnd - effectiveStart; // Compute hours worked
            
            if (loginTime <= 8.05 && effectiveEnd >= WORK_END_HOUR) {
                return 8.0; // Cap full workday at 8 hours
            }
            
            return Math.max(0, hoursWorked); // Returns positive hours only
            
        } catch (NumberFormatException e) { // Handles errors - any invalid input will count as zero
            System.err.println("Warning: Could not parse time values '" + logIn + "' and '" + logOut + "': " + e.getMessage());
            return 0.0;
        }
    }
 
    // sss calculation
    static double calculateSSS(double monthlySalary) {
        if (monthlySalary < 3250) return 135.0;
        else if (monthlySalary < 3750) return 157.5;
        else if (monthlySalary < 4250) return 180.0;
        else if (monthlySalary < 4750) return 202.5;
        else if (monthlySalary < 5250) return 225.0;
        else if (monthlySalary < 5750) return 247.5;
        else if (monthlySalary < 6250) return 270.0;
        else if (monthlySalary < 6750) return 292.5;
        else if (monthlySalary < 7250) return 315.0;
        else if (monthlySalary < 7750) return 337.5;
        else if (monthlySalary < 8250) return 360.0;
        else if (monthlySalary < 8750) return 382.5;
        else if (monthlySalary < 9250) return 405.0;
        else if (monthlySalary < 9750) return 427.5;
        else if (monthlySalary < 10250) return 450.0;
        else if (monthlySalary < 10750) return 472.5;
        else if (monthlySalary < 11250) return 495.0;
        else if (monthlySalary < 11750) return 517.5;
        else if (monthlySalary < 12250) return 540.0;
        else if (monthlySalary < 12750) return 562.5;
        else if (monthlySalary < 13250) return 585.0;
        else if (monthlySalary < 13750) return 607.5;
        else if (monthlySalary < 14250) return 630.0;
        else if (monthlySalary < 14750) return 652.5;
        else if (monthlySalary < 15250) return 675.0;
        else if (monthlySalary < 15750) return 697.5;
        else if (monthlySalary < 16250) return 720.0;
        else if (monthlySalary < 16750) return 742.5;
        else if (monthlySalary < 17250) return 765.0;
        else if (monthlySalary < 17750) return 787.5;
        else if (monthlySalary < 18250) return 810.0;
        else if (monthlySalary < 18750) return 832.5;
        else if (monthlySalary < 19250) return 855.0;
        else if (monthlySalary < 19750) return 877.5;
        else if (monthlySalary < 20250) return 900.0;
        else if (monthlySalary < 20750) return 922.5;
        else if (monthlySalary < 21250) return 945.0;
        else if (monthlySalary < 21750) return 967.5;
        else if (monthlySalary < 22250) return 990.0;
        else if (monthlySalary < 22750) return 1012.50;
        else if (monthlySalary < 23250) return 1035.00;
        else if (monthlySalary < 23750) return 1057.50;
        else if (monthlySalary < 24250) return 1080.00;
        else if (monthlySalary < 24750) return 1102.50;
        else return 1125.00;
    }
    //Phlhealth calculations
    static double calculatePhilHealth(double monthlyBasicSalary) {
        double contribution = monthlyBasicSalary * 0.03;
        return Math.min(contribution, 1800.0);
    }
    //Pagibig calculations
    static double calculatePagIbig(double monthlyBasicSalary) {
        if (monthlyBasicSalary >= 1000 && monthlyBasicSalary <= 1500) {
            return Math.min(monthlyBasicSalary * 0.01, 100.0);
        } else if (monthlyBasicSalary > 1500) {
            return Math.min(monthlyBasicSalary * 0.02, 100.0);
        } else {
            return 0.0;
        }
    }
    //tax calculation
    static double calculateTax(double monthlyTaxableIncome) {
        if (monthlyTaxableIncome <= 20832) {
            return 0.0;
        } else if (monthlyTaxableIncome < 33333) {
            return (monthlyTaxableIncome - 20833) * 0.20;
        } else if (monthlyTaxableIncome < 66667) {
            return 2500 + (monthlyTaxableIncome - 33333) * 0.25;
        } else if (monthlyTaxableIncome < 166667) {
            return 10833 + (monthlyTaxableIncome - 66667) * 0.30;
        } else if (monthlyTaxableIncome < 666667) {
            return 40833.33 + (monthlyTaxableIncome - 166667) * 0.32;
        } else {
            return 200833.33 + (monthlyTaxableIncome - 666667) * 0.35;
        }
    }
//Payroll process for employees
    
    static PayrollData calculateMonthlyPayroll(List<String[]> empAttendance, int month, 
                                               double hourlyRate, double basicSalary) {
        PayrollData payroll = new PayrollData();
        
        // Calculate hours per cutoff
        double[] cutoff1Hours = calculateCutoffHours(empAttendance, month, 1, 15);
        double[] cutoff2Hours = calculateCutoffHours(empAttendance, month, 16, getDaysInMonth(month));
        
        payroll.cutoff1Hours = cutoff1Hours[0];
        payroll.cutoff2Hours = cutoff2Hours[0];
        
        // Calculate gross pay
        payroll.grossCutoff1 = payroll.cutoff1Hours * hourlyRate;
        payroll.grossCutoff2 = payroll.cutoff2Hours * hourlyRate;
        payroll.monthlyGross = payroll.grossCutoff1 + payroll.grossCutoff2;
        
        // Calculate deductions
        payroll.sss = calculateSSS(payroll.monthlyGross);
        payroll.philHealth = calculatePhilHealth(basicSalary);
        payroll.pagIbig = calculatePagIbig(basicSalary);
        payroll.tax = calculateTax(payroll.monthlyGross - payroll.sss - payroll.philHealth - payroll.pagIbig);
        
        // Calculate net pay
        payroll.totalDeductions = payroll.sss + payroll.philHealth + payroll.pagIbig + payroll.tax;
        payroll.netCutoff1 = payroll.grossCutoff1;
        payroll.netCutoff2 = payroll.grossCutoff2 - payroll.totalDeductions;
        
        return payroll;
    }
    
    static void processPayrollForEmployee(String empNum) {
        // Input validation
        if (empNum == null || empNum.trim().isEmpty()) {
            System.out.println("\nError: Employee number cannot be empty.");
            return;
        }
        
        if (!employeeMap.containsKey(empNum)) { // Check if employee exists
            System.out.println("\nError: Employee number '" + empNum + "' does not exist.");
            return;
        }
        
        String[] emp = employeeMap.get(empNum);
        double hourlyRate = safeParseDouble(emp[EMP_HOURLY_RATE_INDEX], 0.0); // Gets hourly rate using constant
        double basicSalary = safeParseDouble(emp[EMP_BASIC_SALARY_INDEX], 0.0); // Gets basic salary using constant
        List<String[]> empAttendance = attendanceMap.getOrDefault(empNum, new ArrayList<>()); // Use getOrDefault
        
        // Print header for payroll slip
        System.out.println("\n");
        System.out.println("================================================================================");
        System.out.println("                        PAYROLL SLIP                                          ");
        System.out.println("================================================================================");
        
        System.out.println("Employee #: " + emp[EMP_ID_INDEX]); // Use constant
        System.out.println("Employee Name: " + emp[EMP_FIRST_NAME_INDEX] + " " + emp[EMP_LAST_NAME_INDEX]); // Use constants
        System.out.println("Birthday: " + emp[EMP_BIRTHDAY_INDEX]); // Use constant
        
        // Calculate and display payroll for each month
        for (int month : MONTHS_TO_PROCESS) {
            String monthName = getMonthName(month);
            PayrollData payroll = calculateMonthlyPayroll(empAttendance, month, hourlyRate, basicSalary); // REFACTORED
            
            // Print payroll for each month
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("Month: " + monthName);
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Cutoff 1 (" + monthName + " 1-15): Hours=" + String.format("%.2f", payroll.cutoff1Hours) + 
                               " | Gross=" + String.format("%.2f", payroll.grossCutoff1) + 
                               " | Net=" + String.format("%.2f", payroll.netCutoff1));
            System.out.println("Cutoff 2 (" + monthName + " 16-end): Hours=" + String.format("%.2f", payroll.cutoff2Hours) + 
                               " | Gross=" + String.format("%.2f", payroll.grossCutoff2) + 
                               " | Deductions=" + String.format("%.2f", payroll.totalDeductions) + 
                               " | Net=" + String.format("%.2f", payroll.netCutoff2));
        }
        
        System.out.println("================================================================================");
    }
 
    //generates and prints payroll report for all employees in the system
    static void processPayrollForAllEmployees() {
        System.out.println("\n");
        System.out.println("================================================================================");
        System.out.println("                    ALL EMPLOYEES PAYROLL (JUNE-DECEMBER)                      ");
        System.out.println("================================================================================");
        
        List<String> empNums = new ArrayList<>(employeeMap.keySet()); // Get all employee numbers
        
        if (empNums.isEmpty()) {
            System.out.println("No employees found in the system.");
            return;
        }
        
        for (String empNum : empNums) {
            String[] emp = employeeMap.get(empNum);
            
            // Extract data and attendance
            double hourlyRate = safeParseDouble(emp[EMP_HOURLY_RATE_INDEX], 0.0); // Use constant and safe parsing
            double basicSalary = safeParseDouble(emp[EMP_BASIC_SALARY_INDEX], 0.0); // Use constant and safe parsing
            List<String[]> empAttendance = attendanceMap.getOrDefault(empNum, new ArrayList<>()); // Use getOrDefault
            
            // Show employee details
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Employee #: " + emp[EMP_ID_INDEX] + " | Name: " + emp[EMP_FIRST_NAME_INDEX] + " " + emp[EMP_LAST_NAME_INDEX]); // Use constants
            System.out.println("--------------------------------------------------------------------------------");
            
            // Loop every month to process payroll
            for (int month : MONTHS_TO_PROCESS) {
                String monthName = getMonthName(month);
                PayrollData payroll = calculateMonthlyPayroll(empAttendance, month, hourlyRate, basicSalary); // REFACTORED
                
                System.out.println("  " + monthName + " Cutoff 1: Hours=" + String.format("%.2f", payroll.cutoff1Hours) + 
                                   " | Gross=" + String.format("%.2f", payroll.grossCutoff1) + 
                                   " | Net=" + String.format("%.2f", payroll.netCutoff1));
                System.out.println("  " + monthName + " Cutoff 2: Hours=" + String.format("%.2f", payroll.cutoff2Hours) + 
                                   " | Gross=" + String.format("%.2f", payroll.grossCutoff2) + 
                                   " | Deductions=" + String.format("%.2f", payroll.totalDeductions) + 
                                   " | Net=" + String.format("%.2f", payroll.netCutoff2));
            }
            System.out.println();
        }
        
        System.out.println("================================================================================");
    }
 
    //Converts a month number into its name (as a string).
    static String getMonthName(int month) {
        return switch (month) {
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "Unknown";
        };
    }
    //Returns the number of days in a given month (June–December).
    static int getDaysInMonth(int month) {
        return switch (month) {
            case 6 -> 30;
            case 7 -> 31;
            case 8 -> 31;
            case 9 -> 30;
            case 10 -> 31;
            case 11 -> 30;
            case 12 -> 31;
            default -> 30;
        }; 
    }

    private static double safeParseDouble(String string, double defaultValue) {
    if (string == null || string.trim().isEmpty()) {
        return defaultValue;
    }
    try {
        return Double.parseDouble(string.trim());
    } catch (NumberFormatException e) {
        System.err.println("Warning: Could not parse '" + string + "' as double, using default: " + defaultValue);
        return defaultValue;
    }
  }
}
