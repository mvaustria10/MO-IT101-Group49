/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ms2project;
///
///
/// COMPUTER PROGRAMING 1
///    GROUP 49
///       PARTICIPATING MEMBER: MARK VEMHER AUSTRIA// NOTHING FOLLOWS
///           PLEASE CHECK README FILE FOR MORE INFO
///
///

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

public class MS2Project {
    
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
                case "1":                                              //
                    System.out.print("\nEnter Employee Number: ");     // shows employee details
                    String empNum = scanner.nextLine().trim();         //
                    displayEmployeeDetails(empNum);                    //
                    break;                
                case "2":
                    System.out.println("\nThank you for using MotorPH Payroll System. Goodbye!");
                    System.exit(0);
                    break;
                default:                                                          // handles wrong input
                    System.out.println("\nInvalid option. Please try again.");
            }
        }
    }
    //display employee details
    static void displayEmployeeDetails(String empNum) {
        if (employeeMap.containsKey(empNum)) {  //check employee
            String[] emp = employeeMap.get(empNum); //get emp data
            System.out.println("\n============================================");
            System.out.println("         MOTORPH EMPLOYEE DETAILS           ");
            System.out.println("============================================");
            System.out.println("Employee Number: " + emp[0]);                     //
            System.out.println("Employee Name: " + emp[2] + " " + emp[1]);        //employee data
            System.out.println("Birthday: " + emp[3]);
            System.out.println("============================================");
        } else {
            System.out.println("\nEmployee number does not exist");  //employee not foud
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
                case "1":
                    processPayrollMenu();
                    break;
                case "2":
                    System.out.println("\nThank you for using MotorPH Payroll System. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        }
    }
    //process payroll menu
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
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    System.out.print("\nEnter Employee Number: ");
                    String empNum = scanner.nextLine().trim();
                    processPayrollForEmployee(empNum);
                    break;
                case "2":
                    processPayrollForAllEmployees();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        }
    }
    //load emp data
    static void loadEmployeeData() {
        String fileName = "Employee.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) { //read file andopenthe csv file
            String line;
            boolean isFirstLine = true;//skips header
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; }
                
                String[] data = line.split(","); // splits csv lines to column
                employeeData.add(data); //stores row in list
                employeeMap.put(data[0], data); //creates lookup by emp#
            }
            
            System.out.println("\nLoaded " + employeeData.size() + " employees from CSV."); //returms number of loaded emp
            
        } catch (IOException e) {
            System.out.println("Error loading Employee.csv: "); //handles file errors
        }
    }
    
    static void loadAttendanceData() {
        String fileName = "Attendance.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] data = line.split(",");
                attendanceData.add(data);
                
                String empNum = data[0];
                if (!attendanceMap.containsKey(empNum)) {  //grp attendance by emp
                    attendanceMap.put(empNum, new ArrayList<>()); //creates new list for emp
                }
                attendanceMap.get(empNum).add(data);
            }
            
            System.out.println("Loaded " + attendanceData.size() + " attendance records from CSV."); //returns num of records loaded
            
        } catch (IOException e) { System.out.println("Error loading Attendance.csv: ");
        }
    }
    //calculate cutoff hours. this calculates total work hrs and days worked for an emp within specific date range in a month
    static double[] calculateCutoffHours(List<String[]> attendance, int month, int startDay, int endDay) {
        double totalHours = 0.0; //hrs worked in the cutoff period
        int daysWorked = 0; // num of days worked in the period
        
        if (attendance == null) {
            return new double[]{0.0, 0};  //returns zero if no attendance data
        }
        
        for (String[] record : attendance) {
            try {  //parse the date
                java.util.Date date = dateFormat.parse(record[3]);
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(date);
                //check if the record is in the citoff period
                int recordMonth = cal.get(java.util.Calendar.MONTH) + 1;
                int recordDay = cal.get(java.util.Calendar.DAY_OF_MONTH);
                
                if (recordMonth == month && recordDay >= startDay && recordDay <= endDay) {
                    double hours = calculateDailyHours(record[4], record[5]); //calculate daily hrs and update totals
                    totalHours += hours;
                    daysWorked++;
                }
                
            } catch (ParseException e) {
                // Skip invalid dates
            }
        }
        
        return new double[]{totalHours, daysWorked}; //return the result
    }
    
    //calculate daily hrs. computes the number of wokr hrs for a single day based on login and logout time capped at 8 hrs
    static double calculateDailyHours(String logIn, String logOut) {
        try {
            String[] loginParts = logIn.split(":");                 // 
            double loginHour = Double.parseDouble(loginParts[0]);   //
            double loginMinute = Double.parseDouble(loginParts[1]);//converts time format into decimal hrs
            double loginTime = loginHour + (loginMinute / 60.0);   //
            
            String[] logoutParts = logOut.split(":");
            double logoutHour = Double.parseDouble(logoutParts[0]);    //
            double logoutMinute = Double.parseDouble(logoutParts[1]);   //converts time format into decimal hrs
            double logoutTime = logoutHour + (logoutMinute / 60.0);    //
            
            double effectiveStart = Math.max(loginTime, WORK_START_HOUR); //
            double effectiveEnd = Math.min(logoutTime, WORK_END_HOUR);    // ensures time counted is within official work hrs
            double hoursWorked = effectiveEnd - effectiveStart;       // computehrs worked
            
            if (loginTime <= 8.05 && effectiveEnd >= WORK_END_HOUR) {
                return 8.0; // cap full workday at 8 hrs
            }
            
            return Math.max(0, hoursWorked); //returns positive hrs
            
        } catch (Exception e) { //handles errors, any invalid input will count as zero
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
    static void processPayrollForEmployee(String empNum) {
        if (!employeeMap.containsKey(empNum)) { //check if emp exist
            System.out.println("\nThe employee number does not exist.");
            return;
        }
        
        String[] emp = employeeMap.get(empNum); //check fields in the emp array      
        double hourlyRate = Double.parseDouble(emp[18]);  //gets hr rate data
        double basicSalary = Double.parseDouble(emp[13]);// gets basic salary data
        List<String[]> empAttendance = attendanceMap.get(empNum);
        
        // print header for payroll slip
        System.out.println("\n");
        System.out.println("================================================================================");
        System.out.println("                        PAYROLL SLIP                                          ");
        System.out.println("================================================================================");
        
        System.out.println("Employee #: " + emp[0]);
        System.out.println("Employee Name: " + emp[2] + " " + emp[1]);
        System.out.println("Birthday: " + emp[3]);
        
        for (int month : MONTHS_TO_PROCESS) { //calculate payroll per cutoff
            String monthName = getMonthName(month);
            
            //calculate hours
            double[] cutoff1Hours = calculateCutoffHours(empAttendance, month, 1, 15); 
            double[] cutoff2Hours = calculateCutoffHours(empAttendance, month, 16, getDaysInMonth(month));
            
            //calculate gross
            double grossCutoff1 = cutoff1Hours[0] * hourlyRate; 
            double grossCutoff2 = cutoff2Hours[0] * hourlyRate;
            double monthlyGross = grossCutoff1 + grossCutoff2;
            
            //calculate deductions
            double sss = calculateSSS(monthlyGross); 
            double philHealth = calculatePhilHealth(basicSalary);
            double pagIbig = calculatePagIbig(basicSalary);
            double tax = calculateTax(monthlyGross - sss - philHealth - pagIbig);
            
            //compute total deductions and net pay
            double totalDeductions = sss + philHealth + pagIbig + tax;
            double netCutoff1 = grossCutoff1;
            double netCutoff2 = grossCutoff2 - totalDeductions;
            
            //print payroll for each month
               System.out.println("\n--------------------------------------------------------------------------------");
               System.out.println("Month: " + monthName);
               System.out.println("--------------------------------------------------------------------------------");
               System.out.println("Cutoff 1 (" + monthName + " 1-15): Hours=" + String.format("%.2f", cutoff1Hours[0]) + 
                                  " | Gross=" + String.format("%.2f", grossCutoff1) + 
                                  " | Net=" + String.format("%.2f", netCutoff1));
               System.out.println("Cutoff 2 (" + monthName + " 16-end): Hours=" + String.format("%.2f", cutoff2Hours[0]) + 
                                  " | Gross=" + String.format("%.2f", grossCutoff2) + 
                                  " | Deductions=" + String.format("%.2f", totalDeductions) + 
                                  " | Net=" + String.format("%.2f", netCutoff2));
        }
        
        System.out.println("================================================================================");
    } 
    //generates and prints payroll report for all employees in the system
    static void processPayrollForAllEmployees() {
        System.out.println("\n");
        System.out.println("================================================================================");
        System.out.println("                    ALL EMPLOYEES PAYROLL (JUNE-DECEMBER)                      ");
        System.out.println("================================================================================");
        
        List<String> empNums = new ArrayList<>(employeeMap.keySet()); //get all employee numbers
        
        for (String empNum : empNums) {
            String[] emp = employeeMap.get(empNum);
            
            //extract data and attendance
            double hourlyRate = Double.parseDouble(emp[18]);
            double basicSalary = Double.parseDouble(emp[13]);
            List<String[]> empAttendance = attendanceMap.get(empNum);
            
            //shows employee details
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Employee #: " + emp[0] + " | Name: " + emp[2] + " " + emp[1]);
            System.out.println("--------------------------------------------------------------------------------");
            
            //loops every month to process payroll
            for (int month : MONTHS_TO_PROCESS) {
                String monthName = getMonthName(month);
                
                //calculate hrs per cutoff
                double[] cutoff1Hours = calculateCutoffHours(empAttendance, month, 1, 15);
                double[] cutoff2Hours = calculateCutoffHours(empAttendance, month, 16, getDaysInMonth(month));
                
                //calculate gross
                double grossCutoff1 = cutoff1Hours[0] * hourlyRate;
                double grossCutoff2 = cutoff2Hours[0] * hourlyRate;
                double monthlyGross = grossCutoff1 + grossCutoff2;
                
                //calculate deductions
                double sss = calculateSSS(monthlyGross);
                double philHealth = calculatePhilHealth(basicSalary);
                double pagIbig = calculatePagIbig(basicSalary);
                double tax = calculateTax(monthlyGross - sss - philHealth - pagIbig);
                
                // net pay
                double totalDeductions = sss + philHealth + pagIbig + tax;
                double netCutoff1 = grossCutoff1;
                double netCutoff2 = grossCutoff2 - totalDeductions;
                
                System.out.println("  " + monthName + " Cutoff 1: Hours=" + String.format("%.2f", cutoff1Hours[0]) + 
                                   " | Gross=" + String.format("%.2f", grossCutoff1) + 
                                   " | Net=" + String.format("%.2f", netCutoff1));
                System.out.println("  " + monthName + " Cutoff 2: Hours=" + String.format("%.2f", cutoff2Hours[0]) + 
                                   " | Gross=" + String.format("%.2f", grossCutoff2) + 
                                   " | Deductions=" + String.format("%.2f", totalDeductions) + 
                                   " | Net=" + String.format("%.2f", netCutoff2));
            }
            System.out.println();
        }
        
        System.out.println("================================================================================");
    }
    //Converts a month number into its name (as a string).
    static String getMonthName(int month) {
        switch (month) {
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "Unknown";
        }
    }
    //Returns the number of days in a given month (June–December).
    static int getDaysInMonth(int month) {
        switch (month) {
            case 6: return 30;  // June
            case 7: return 31;  // July
            case 8: return 31;  // August
            case 9: return 30;  // September
            case 10: return 31; // October
            case 11: return 30; // November
            case 12: return 31; // December
            default: return 30;
        }
    }
}