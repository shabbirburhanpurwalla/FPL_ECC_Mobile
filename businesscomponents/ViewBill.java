package businesscomponents;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pageobjects.ViewBillPageObjects;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import componentgroups.CommonFunctions;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

 /**************************************************
 * View Bill
 * 
 * This class contains all the resuable components 
 * used for View Bill flow
 * 
 * @author 387478
 * @modified_by 387478 on 17 October
 * *************************************************
 */
public class ViewBill extends ReusableLibrary{
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	Actions action = new Actions(driver);
	public String accountType;
	
	public ViewBill(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	 /***********************************************************
	 * CRAFT View Bill Page
	 * 
	 * This function navigates to View Bill Page after user is 
	 * logged into the application and verifies various elements
	 * present on the page
	 * 
	 * @author 324096
	 * @throws IOException 
	 * @throws AWTException 
	 * @modified by 387478 on Oct 13 - Incorporated Page Object Model
	 * ***********************************************************
	 */
	public void viewBillPage() throws InterruptedException, IOException, AWTException{
		
		//Click View Bill Link
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lnk_ViewBill), ViewBillPageObjects.lnk_ViewBill.getObjectName());
		
		//Retrieve Data from Accounts sheet
		String accountNumber = commonFunction.getData("Accounts", "AccountNumber","Account Number",true);		
		
		//Pad accountnumber with zeros
		if(accountNumber.length()> 0 && accountNumber.length()<10)
			accountNumber = String.format("%010d", Integer.parseInt(accountNumber));

		//Click AccountNumber link
		try{
			driver.findElement(By.linkText(accountNumber)).click();
			report.updateTestLog("Account Number Link", "Account Number Link "+ accountNumber +" is present and clicked", Status.PASS);
		}catch(Exception e){
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Account Number Link", "Link "+accountNumber+" not displayed");
		}
					
		
		//Navigate back to ViewBill page
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lnk_ViewBill), ViewBillPageObjects.lnk_ViewBill.getObjectName());		

		
		//Verify Another Account link
		/*commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lnk_SelectAnotherAccount), 
											ViewBillPageObjects.lnk_SelectAnotherAccount.getObjectName());*/
				
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_SelectAnotherAccount.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_SelectAnotherAccount.getProperty(), ViewBillPageObjects.lnk_SelectAnotherAccount.getObjectName(),true))				
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_SelectAnotherAccount),
					commonFunction.getData(properties.getProperty("Environment"),"General_Data", "SelectAnotherAccount","Select Another Account URL",true),
					ViewBillPageObjects.lnk_SelectAnotherAccount.getObjectName()
					);		
		
		//Verify customer name is displayed
		getPageElement(ViewBillPageObjects.lbl_CustomerName);	
		
		//Keep in Mind Label
		commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_KeepInMind.getLocatorType().toString(),
				ViewBillPageObjects.lbl_KeepInMind.getProperty(), ViewBillPageObjects.lbl_KeepInMind.getObjectName());
		
		
		//Energy News Link		
		commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_EnergyNews),
				commonFunction.getData(properties.getProperty("Environment"),"General_Data", "EnergyNews","EnergyNews",true),
				ViewBillPageObjects.lnk_EnergyNews.getObjectName()
				);
		
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_EmailShort.getLocatorType().toString(),
				ViewBillPageObjects.lbl_EmailShort.getProperty(), ViewBillPageObjects.lbl_EmailShort.getObjectName())){
			//String email = commonFunction.getData("Accounts", "EmailAddress", "Email Address", true);
			String email = commonFunction.getData("General_Data", "Username", "Email Address", true);
			if(email.length()<=27)
				commonFunction.compareText(email, getPageElement(ViewBillPageObjects.lbl_EmailShort).getText(), "Email Address Verification");
			else{
				commonFunction.compareText(email.substring(0,24)+"...", getPageElement(ViewBillPageObjects.lbl_EmailShort).getText(), "Short Email Address Verification");
				
				try{
					WebElement tooltipIcon = getPageElement(ViewBillPageObjects.lbl_EmailShort);
					Robot robot = new Robot();
					robot.mouseMove(0, 0);
					Actions builder = new Actions(driver);
					builder.moveToElement(tooltipIcon).build().perform();
					
					if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_EmailLong.getLocatorType().toString(),
							ViewBillPageObjects.lbl_EmailLong.getProperty(), "Full Email Address")){
						commonFunction.compareText(email, getPageElement(ViewBillPageObjects.lbl_EmailLong).getText(), "Full Email Address");
						robot.mouseMove(0, 0);
					}else{
						report.updateTestLog("Full Email", "Full Email Not Displayed", Status.WARNING);
					}
				}catch(Exception e){
					report.updateTestLog("Full Email", "ERROR while performing hover on Short Email", Status.WARNING);
				}
			}
		}
		
		/*//Contact Us Link		
		commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_ContactUs),
				commonFunction.getData(properties.getProperty("Environment"),"General_Data", "Contact_Us_Link","Contact Us Link",true),
				ViewBillPageObjects.lnk_ContactUs.getObjectName()
				);*/
		
		/*//Email Update Link		
		commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_EmailUpdate),
				commonFunction.getData(properties.getProperty("Environment"),"General_Data", "EmailUpdate","Email Update URL",true),
				ViewBillPageObjects.lnk_EmailUpdate.getObjectName()
				);*/
	
		
	}
	
	/***********************************************************
	 * CRAFT - Verify Balance Banner section on View Bill Page
	 * 
	 * 
	 * 
	 * @author 387478
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @modified by 387478 on Oct 29 - Created new CRAFT Keyword
	 * ***********************************************************
	 */
	
	public void verifyBalanceBannerSection() throws IOException, InterruptedException{
		String environment = properties.getProperty("Environment");
		String customerName = commonFunction.getData("Accounts", "CustomerName", "Customer Name",true);
		String dueDate = commonFunction.getData("Accounts", "DueDate","", false);
		String pastDueAmount = commonFunction.getData("Accounts", "PastDueAmount","",false);		
		String accountBalance = commonFunction.getData("Accounts", "BalanceAccount", "Account Balance", true);
		
		//Verify validity of displayed customer name
		//String customerName = getPageElement(ViewBillPageObjects.lbl_CustomerName).getText();
		commonFunction.verifyElementPresentEqualsText(getPageElement(ViewBillPageObjects.lbl_CustomerName), "customerName", 
				"Hello " + customerName+",");
		
		
		float actualAccountBalance=0;
		String accountBalFormatted = commonFunction.formatAmount(accountBalance);
		
	      
		//Read account balance from common data
		try{
		 actualAccountBalance = Float.parseFloat(commonFunction.RemoveSpecialcharactersFromAmount(accountBalance));
		 accountBalFormatted = commonFunction.formatAmount(accountBalance);
		}catch(Exception e){
		report.updateTestLog("Account Balance", e.getMessage(), Status.WARNING);
		
		}
		
		//Verify Account Balance Label in BlueBand section
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_BalanceLine.getLocatorType().toString(),
				ViewBillPageObjects.lbl_BalanceLine.getProperty(),ViewBillPageObjects.lbl_BalanceLine.getObjectName())){	
			//String balanceLine = getPageElement(ViewBillPageObjects.lbl_BalanceLine).getText(); //get Balance Line from Page
			String accountBalanceMsg=""; // Store the message to be displayed for various Account Balances (zero,credit,positive)
			
			
			//Zero Balance
			if(actualAccountBalance == 0)
				accountBalanceMsg = "Your account has a zero balance.";
			//Credit Balance
			else if(actualAccountBalance < 0)
				accountBalanceMsg = "Your account has a credit balance of $" + accountBalFormatted +".";
	
			//Past Due - Verify greeting line is displayed
			else if(actualAccountBalance != 0 && (!pastDueAmount.isEmpty())){
				//Verify greeting line is displayed
				if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lnk_GreetingLine.getLocatorType().toString(),
						ViewBillPageObjects.lnk_GreetingLine.getProperty(), ViewBillPageObjects.lnk_GreetingLine.getObjectName())){
					String expectedgreetingLine = "Your account is past due - please pay $"+ commonFunction.formatAmount(pastDueAmount) +" immediately.";
					commonFunction.verifyElementPresentEqualsText(getPageElement(ViewBillPageObjects.lnk_GreetingLine), "Past Due Greeting Line",
							expectedgreetingLine);	
					
				}
				accountBalanceMsg = "Your total balance is $" + accountBalFormatted + ".";			
			}
			
			//No Previous Past Due
			else if(actualAccountBalance > 0 && (!dueDate.isEmpty())){
				if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lnk_GreetingLine.getLocatorType().toString(),
						ViewBillPageObjects.lnk_GreetingLine.getProperty(), ViewBillPageObjects.lnk_GreetingLine.getObjectName())){
					String expectedgreetingLine = "Ready to pay your FPL bill? It's a snap.";
					commonFunction.verifyElementPresentEqualsText(getPageElement(ViewBillPageObjects.lnk_GreetingLine), "Greeting Line",
							expectedgreetingLine);	
				
				accountBalanceMsg = "Your current balance is $" + accountBalFormatted +" due by " + dueDate + ".";
				}
			}
			
			//No previous past due date error
			else if(actualAccountBalance != 0)
				accountBalanceMsg = "Your current balance is $" + accountBalFormatted + ".";
			
			//Verify if Balance Line is equal to accountBalanceMsg
			commonFunction.verifyElementPresentEqualsText(getPageElement(ViewBillPageObjects.lbl_BalanceLine), "BalanceLine",accountBalanceMsg);
		
		}
		
		//Verify PayBill button is displayed in Blue Band section if AccountBalance is greater than zero
		if(actualAccountBalance>0){
			if(!commonFunction.isElementPresent(ViewBillPageObjects.lnk_PayBillblueBand.getLocatorType().toString(),
					ViewBillPageObjects.lnk_PayBillblueBand.getProperty(),"",false))
				report.updateTestLog("Pay Bill Button", "Pay Bill Button is not displayed in blueband section when account balance is greater than zero", Status.FAIL);	
			else{
					report.updateTestLog("Pay Bill Button", "Pay Bill Button is displayed in blueband section when account balance is greater than zero", Status.PASS);
					//Verify PayBill Link
					if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_PayBillblueBand.getLocatorType().toString(), 
							ViewBillPageObjects.lnk_PayBillblueBand.getProperty(), ViewBillPageObjects.lnk_PayBillblueBand.getObjectName(),false)){
						
						//Verify Image Source
						commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.lnk_PayBillblueBand),
								commonFunction.getData(properties.getProperty("Environment"),"General_Data", "PayBillImage","PayBill Image URL",true),
								ViewBillPageObjects.lnk_PayBillblueBand.getObjectName());
						
						//Click and Verify URL - Not Working - Instead checking href attribute
					   /*commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_PayBillblueBand1),
								commonFunction.getData(properties.getProperty("Environment"),"General_Data", "POL_Link","PayBill URL",true),
								"Pay Bill Link"
								);*/
						
						commonFunction.compareText(commonFunction.getData(environment,"General_Data", "POL_Link","PayBill URL",true), 
								getPageElement(ViewBillPageObjects.lnk_PayBillblueBand1).getAttribute("href"),
								ViewBillPageObjects.lnk_PayBillblueBand1.getObjectName()+" verification");
					}
			}
		}
		
		//Verify PayBill button is not displayed in BlueBand section if AccountBalance is less than or equal to zero
		if(actualAccountBalance<=0)
			commonFunction.verifyIsElementNotPresent(ViewBillPageObjects.lnk_PayBillblueBand.getProperty(),ViewBillPageObjects.lnk_PayBillblueBand.getLocatorType().toString(),
					ViewBillPageObjects.lnk_PayBillblueBand.getObjectName());
	}
	
	/***********************************************************
	 * CRAFT - Check My Bill section
	 * 
	 * This function verifies the AMI  Graph for AMI and NON-AMI users
	 * 
	 * @author 387478
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws bsh.ParseException 
	 * @modified by 387478 on Oct 29 - Created new CRAFT Keyword
	 * ***********************************************************
	 */
	public void checkMyBillSection() throws InterruptedException, IOException, ParseException, bsh.ParseException{
		
		//Verify Last Bill Date
		String lastBillDate = commonFunction.getData("Accounts", "LastBillDate", "Last Bill Date",true);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_LastBillDate.getLocatorType().toString(),
				ViewBillPageObjects.lbl_LastBillDate.getProperty(), ViewBillPageObjects.lbl_LastBillDate.getObjectName(), true)){
				commonFunction.compareText("as of "+lastBillDate, 
						getPageElement(ViewBillPageObjects.lbl_LastBillDate).getText(), ViewBillPageObjects.lbl_LastBillDate.getObjectName());
		}
		
		//Verify Select Another Account Link		
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_SelectAnotherAccountMyBill.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_SelectAnotherAccountMyBill.getProperty(), ViewBillPageObjects.lnk_SelectAnotherAccountMyBill.getObjectName(),true))				
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_SelectAnotherAccountMyBill),
					commonFunction.getData(properties.getProperty("Environment"),"General_Data", "SelectAnotherAccount","Select Another Account URL",true),
					ViewBillPageObjects.lnk_SelectAnotherAccountMyBill.getObjectName()
					);	
		
		//Verify Account Summary Link		
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnl_ViewAccountSummary.getLocatorType().toString(), 
				ViewBillPageObjects.lnl_ViewAccountSummary.getProperty(), ViewBillPageObjects.lnl_ViewAccountSummary.getObjectName(),true))				
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnl_ViewAccountSummary),
					commonFunction.getData(properties.getProperty("Environment"),"General_Data", "AccountSummary","AccountSummary URL",true),
					ViewBillPageObjects.lnl_ViewAccountSummary.getObjectName()
					);
		
		/*//Verify View Bill Insert Link		
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_ViewBillInsert.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_ViewBillInsert.getProperty(), ViewBillPageObjects.lnk_ViewBillInsert.getObjectName(),true))				
			commonFunction.verifyLinkInWebPage(getPageElement(ViewBillPageObjects.lnk_ViewBillInsert),ViewBillPageObjects.lnk_ViewBillInsert.getObjectName(),
					commonFunction.getData(properties.getProperty("Environment"),"General_Data", "ViewBillInsert","ViewBillInsert URL",true));					
		*/			
		
		//Verify Customer Name
		String customerName = commonFunction.getData("Accounts", "CustomerName", "Customer Name",true);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_customerName.getLocatorType().toString(),
				ViewBillPageObjects.lbl_customerName.getProperty(), ViewBillPageObjects.lbl_customerName.getObjectName(), true)){
				commonFunction.compareText(customerName.toUpperCase(), 
						getPageElement(ViewBillPageObjects.lbl_customerName).getText(), ViewBillPageObjects.lbl_customerName.getObjectName());
		}
		
		//Verify Service Addess 
		String serviceAddress = commonFunction.getData("Accounts", "ServiceAddress", "Service Address",true);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_serviceAddress.getLocatorType().toString(),
				ViewBillPageObjects.lbl_serviceAddress.getProperty(), ViewBillPageObjects.lbl_serviceAddress.getObjectName(), true)){
				commonFunction.compareText(serviceAddress, 
						getPageElement(ViewBillPageObjects.lbl_serviceAddress).getText(), ViewBillPageObjects.lbl_serviceAddress.getObjectName());
		}
		
		
		//Verify Service Date Dropdown
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.drpdwn_ServiceDate.getLocatorType().toString(),
				ViewBillPageObjects.drpdwn_ServiceDate.getProperty(), ViewBillPageObjects.drpdwn_ServiceDate.getObjectName())){
			Select dropdown = new Select(getPageElement(ViewBillPageObjects.drpdwn_ServiceDate));
			int billCount = dropdown.getOptions().size();
			
			//getPageElement(ViewBillPageObjects.drpdwn_ServiceDate).click();
			report.updateTestLog("'Service Date' dropdown", billCount +" previous bills displayed", Status.PASS);
			if(billCount>1){
				String billDate = commonFunction.getData("Accounts", "PreviousBillDate", "Previous Bill Date", false);
				if(!billDate.isEmpty()){
						billDate = commonFunction.convertDatetoSingleDigit(billDate);
					
					SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
					Date date = formatter.parse(billDate);
					String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
					try{
						dropdown.selectByValue(formattedDate);
						Thread.sleep(7000);
						//Select second bill
						report.updateTestLog("Select Bill", "Bill dated "+ billDate + " selected successfully.", Status.PASS);
						commonFunction.compareText("Statement balance as of " + billDate, getPageElement(ViewBillPageObjects.lbl_StatementBalance).getText(), "Statement Balance Updation");
						
						
						//dropdown.selectByValue("04/01/2014");					
						//Thread.sleep(7000);
						//report.updateTestLog("Select Bill", "Bill dated "+ billDate + " selected again.", Status.PASS);
						//commonFunction.compareText("Statement balance as of " + billDate, getPageElement(ViewBillPageObjects.lbl_StatementBalance).getText(), "Statement Balance Updation");
						}catch(Exception e){
							report.updateTestLog("Select Bill from Service Date Dropdown", "Error selecting bill dated "+ formattedDate +" from Service Date dropdown", Status.FAIL);
						}
			}
		  }
		}
		
		//Click New Charges Section
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lbl_NewChargesHeader), 
				ViewBillPageObjects.lbl_NewChargesHeader.getObjectName());
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lbl_NewChargesHeader), 
				ViewBillPageObjects.lbl_NewChargesHeader.getObjectName());
		
		
	/*	//Click Download Bill Link
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_DownloadBill.getLocatorType().toString(),
				ViewBillPageObjects.lnk_DownloadBill.getProperty(), ViewBillPageObjects.lnk_DownloadBill.getObjectName(), true)){
			
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.lnk_DownloadBill),
					commonFunction.getData(properties.getProperty("Environment"),"General_Data", "DownloadBillImageURL","DownloadBill Image URL",true),
					ViewBillPageObjects.lnk_DownloadBill.getObjectName());
			
				downloadBill(ViewBillPageObjects.lnk_DownloadBill, ViewBillPageObjects.lnk_DownloadBill.getObjectName());
		}*/
		
		//Retrieve Data from Accounts sheet
		String abpDate = commonFunction.getData("Accounts", "ABPDate","ABP Date",false);
		
		if(!abpDate.isEmpty()){
			String abpMessage = "DO NOT PAY. Thank you for using FPL Automatic Bill Pay®. The amount due on your account will be drafted automatically on or after "+ abpDate+".";
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_ABPMessage.getLocatorType().toString(),
					ViewBillPageObjects.lbl_ABPMessage.getProperty(),ViewBillPageObjects.lbl_ABPMessage.getObjectName())){
				commonFunction.compareText(abpMessage, getPageElement(ViewBillPageObjects.lbl_ABPMessage).getText(), 
						ViewBillPageObjects.lbl_ABPMessage.getObjectName());
			}
		}
		
		//Pay Bill Button
		String accountBalance = commonFunction.getData("Accounts", "BalanceAccount", "Account Balance", false);
		Float actualAccountBalance = (float) 0;
		try{
			actualAccountBalance = Float.parseFloat(commonFunction.RemoveSpecialcharactersFromAmount(accountBalance));
		}catch(Exception e){
			actualAccountBalance = (float) 0;
		}
		
		if(actualAccountBalance > 0){
			//Verify PayBill button is displayed
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lnk_PayBill.getLocatorType().toString(),
					ViewBillPageObjects.lnk_PayBill.getProperty(),ViewBillPageObjects.lnk_PayBill.getObjectName())){
				
				//Verify Image Source
				commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.lnk_PayBill),
						commonFunction.getData(properties.getProperty("Environment"),"General_Data", "PayBillImage","PayBill Image URL",true),
						ViewBillPageObjects.lnk_PayBill.getObjectName());
				
				commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_PayBill),
						commonFunction.getData(properties.getProperty("Environment"),"General_Data", "POL_Link","PayBill URL",true),
						ViewBillPageObjects.lnk_PayBill.getObjectName()
						);
			}
		}
		else
			commonFunction.verifyIsElementNotPresent(ViewBillPageObjects.lnk_PayBill.getProperty(),ViewBillPageObjects.lnk_PayBill.getLocatorType().toString(),
					ViewBillPageObjects.lnk_PayBill.getObjectName());
			
	}
	 
	/*************************************************************
	 * CRAFT View Bill Page - Comparisons
	 * This function validates the Comparisons table displayed
	 * on View Bill Page
	 * 
	 * @author 387478
	 * @throws IOException 
	 * @modified by 387478 on Oct 30
	 * 
	 * *************************************************************
	 */	
	public void checkComparisonsSection(){
		//Verify Meter Comparison label is displayed
		commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_MeterComparison.getLocatorType().toString(),
				ViewBillPageObjects.lbl_MeterComparison.getProperty(),ViewBillPageObjects.lbl_MeterComparison.getObjectName());
		
		//Verify Meter Number
		String meterNumber = commonFunction.getData("Accounts", "MeterNumber", "Meter Number", true);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_meterNumber.getLocatorType().toString(),
				ViewBillPageObjects.lbl_meterNumber.getProperty(), ViewBillPageObjects.lbl_meterNumber.getObjectName(), true)){
			commonFunction.compareText("Meter reading - Meter "+ meterNumber, getPageElement(ViewBillPageObjects.lbl_meterNumber).getText(), ViewBillPageObjects.lbl_meterNumber.getObjectName());
		}
		
		//Verify Next Read Date
		String nextReadDate = commonFunction.getData("Accounts", "NextReadDate", "Meter Number", true);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_NextReadDate.getLocatorType().toString(),
				ViewBillPageObjects.lbl_NextReadDate.getProperty(), ViewBillPageObjects.lbl_NextReadDate.getObjectName(), true)){
			commonFunction.compareText(nextReadDate, getPageElement(ViewBillPageObjects.lbl_NextReadDate).getText(), ViewBillPageObjects.lbl_NextReadDate.getObjectName());
		}
	}
	
	
	/*************************************************************
	 * CRAFT View Bill Page - Account History
	 * This function validates the Account History table displayed
	 * on View Bill Page
	 * 
	 * @author 387478
	 * @throws IOException 
	 * @modified by 387478 on Oct 30- 
	 * 
	 * *************************************************************
	 */	
	public void checkAccountHistory() throws InterruptedException
	{
		action.sendKeys(Keys.HOME).perform();
		//action.sendKeys(Keys.PAGE_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();
		
		//Verify if Your Bills section is selected by default		    
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lnk_Yourbills.getLocatorType().toString(),
				ViewBillPageObjects.lnk_Yourbills.getProperty(),ViewBillPageObjects.lnk_Yourbills.getObjectName())){
			//Get class attribute to verify if Your Bills is selected by default
			System.out.println(getPageElement(ViewBillPageObjects.lnk_Yourbills).getAttribute("class"));
			if(getPageElement(ViewBillPageObjects.lnk_Yourbillsproperty).getAttribute("class").equals("active"))			
				report.updateTestLog("Verify 'Your bills' section", "'Your Bills' section is selected by default", Status.PASS);
			else
				report.updateTestLog("Verify 'Your bills' section", "Your bills Link is not selected by default", Status.FAIL);
		} 
		
		//Find no. of records displayed in Your bills section
		int pastBillCount = getPageElements(ViewBillPageObjects.lnk_Yourbills_history).size();
		if(pastBillCount == 0)
			report.updateTestLog("'Your bills' section", "No previous bills found in 'Your bills' section", Status.WARNING);
		else
			report.updateTestLog("'Your bills' section", pastBillCount + " previous bills found in 'Your bills' section", Status.PASS);
		
		
		//Click Your Payments section
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lnk_YourPayments), ViewBillPageObjects.lnk_YourPayments.getObjectName());
		Thread.sleep(4000);
			
		//Get class attribute to verify if Your Payments is selected after clicking
		if(getPageElement(ViewBillPageObjects.lnk_YourPaymentsProperty).getAttribute("class").equals("active"))		
			report.updateTestLog("Verify 'Your payments' section", "'Your payments' section is displayed and selected after click", Status.PASS);
		else
			report.updateTestLog("Verify 'Your payments' section", "'Your payments' section is not selected after click", Status.FAIL);
		
		
		//Find no. of records displayed in Your payments section
		int pastPaymentCount = getPageElements(ViewBillPageObjects.lnk_Yourpayments_history).size();
		if(pastPaymentCount == 0)
			report.updateTestLog("'Your Payments' section", "No previous payments found in 'Your payments' section", Status.WARNING);
		else
			report.updateTestLog("'Your Payments' section", pastPaymentCount + " previous payments found in 'Your payments' section", Status.PASS);
		
	}	
	
	/***********************************************************
	 * CRAFT - Verify AMI Graph
	 * 
	 * This function verifies the AMI  Graph for AMI and NON-AMI users
	 * 
	 * @author 387478
	 * @throws IOException 
	 * @modified by 387478 on Oct 29 - Created new CRAFT Keyword
	 * ***********************************************************
	 */
	
	public void verifyAMIGraph() throws InterruptedException{
		String ami = commonFunction.getData("Accounts", "Ami", "Ami", true);
		String environment = properties.getProperty("Environement");
		
		//Energy Usage Section
		action.sendKeys(Keys.END).perform();
		//Verify if Energy Usage Section Header is displayed
		commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_EnergyUseSectionHeader.getLocatorType().toString(),
				ViewBillPageObjects.lbl_EnergyUseSectionHeader.getProperty(),ViewBillPageObjects.lbl_EnergyUseSectionHeader.getObjectName());
		
		//If account is AMI enrolled, verify AMI message
		if(ami.toLowerCase().equals("yes")){
			//Verify AMI Legend is displayed
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.img_AmiLegend.getLocatorType().toString(),
					ViewBillPageObjects.img_AmiLegend.getProperty(),ViewBillPageObjects.img_AmiLegend.getObjectName())){
				
			/*	//Verify correct legend is displayed
				commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_AmiLegend),
						commonFunction.getData(environment,"General_Data", "AMIImageUrl","AMI Legend Image URL",true),
						ViewBillPageObjects.img_AmiLegend.getObjectName());
				*/
			}
			//Verify AMI Message
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_AmiMessage.getLocatorType().toString(),
					ViewBillPageObjects.lbl_AmiMessage.getProperty(),ViewBillPageObjects.lbl_AmiMessage.getObjectName())){
				
				commonFunction.compareText(getPageElement(ViewBillPageObjects.lbl_AmiMessage).getText().substring(2),
						commonFunction.getData("General_Data", "AmiMessage", ViewBillPageObjects.lbl_AmiMessage.getObjectName(), true),
						ViewBillPageObjects.lbl_AmiMessage.getObjectName());
				
				commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.obj_AmiGraph.getLocatorType().toString(),
						ViewBillPageObjects.obj_AmiGraph.getProperty(),ViewBillPageObjects.obj_AmiGraph.getObjectName());
				
				/*//Click "accessing your online energy dashboard." link
				commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnl_onlineEnergyDashboard),
						commonFunction.getData(properties.getProperty("Environment"), "General_Data", "EnergyDashboard_Link", ViewBillPageObjects.lnl_onlineEnergyDashboard.getObjectName(),
								true),ViewBillPageObjects.lnl_onlineEnergyDashboard.getObjectName());	*/
			}
			
		}else{
			//Verify non AMI Legend
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.img_NonAmiLegend.getLocatorType().toString(),
					ViewBillPageObjects.img_NonAmiLegend.getProperty(),ViewBillPageObjects.img_NonAmiLegend.getObjectName())){
				
				/*//Verify correct legend is displayed
				commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_NonAmiLegend),
						commonFunction.getData(environment,"General_Data", "NonAMIImageUrl","Non AMI Legend Image URL",true),
						ViewBillPageObjects.img_NonAmiLegend.getObjectName());*/
			}
			//Verify Non AMI Message
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_NonAmiMessage.getLocatorType().toString(),
					ViewBillPageObjects.lbl_NonAmiMessage.getProperty(),ViewBillPageObjects.lbl_NonAmiMessage.getObjectName())){
				
				commonFunction.compareText(getPageElement(ViewBillPageObjects.lbl_NonAmiMessage).getText().substring(1).trim(),
						commonFunction.getData("General_Data", "NonAmiMessage", ViewBillPageObjects.lbl_NonAmiMessage.getObjectName(), true),
						ViewBillPageObjects.lbl_NonAmiMessage.getObjectName());

				commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.obj_NonAmiGraph.getLocatorType().toString(),
						ViewBillPageObjects.obj_NonAmiGraph.getProperty(),ViewBillPageObjects.obj_NonAmiGraph.getObjectName());				
			 
		   }
		}	
	}
	
	 
	
	/**
	 * CRAFT Tooltip - KeyWord to check all the tooltips displayed on the ViewBill page
	 * @author 324096
	 * @throws InterruptedException 
	 * @modified_date Oct 15, 2014
	 */
	
	public void verifyTooltipMessages() throws InterruptedException{
		action.sendKeys(Keys.HOME).perform();
		//Expand New Charges Section to check all the tooltips displayed in New Charges section		    
	    commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lbl_NewChargesHeader), 
				ViewBillPageObjects.lbl_NewChargesHeader.getObjectName());
	    
	    String toolTipMessage = "";
	    
	  //Verify Balance Line Tooltip Message
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_BalanceLineTooltip, 
	    		ViewBillPageObjects.lbl_BalanceLineTooltipBub,
	    		ViewBillPageObjects.lbl_BalanceLineTooltipClose,
	    		ViewBillPageObjects.lbl_BalanceLineTooltip.getObjectName());
	    if(!toolTipMessage.isEmpty())
	    commonFunction.compareText(commonFunction.getData("General_Data", "BalanceLineTooltip", "Balance Line Tooltip", true)
	    		, toolTipMessage, "Balance Line Tooltip Verification");
	    
	    
	    
	 /* //Verify Keep In Mind Tooltip Message
	    verifyTooltipMessage(ViewBillPageObjects.lbl_KeepInMindTooltip, 
	    		ViewBillPageObjects.lbl_KeepInMindTooltipBub, 
	    		ViewBillPageObjects.lbl_KeepInMindTooltip.getObjectName());*/
	    
	    //Verify New Charges Table Rate Help Message
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_RATEHelpMsg9, 
	    		ViewBillPageObjects.lbl_NnewChargesTable_LITR_RATEHelpMsgBub,
	    		ViewBillPageObjects.lbl_NnewChargesTable_LITR_RATEHelpMsgClose,
	    		ViewBillPageObjects.lbl_NewChargesTable_LITR_RATEHelpMsg9.getObjectName());

	  //Verify CustomerCharge Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_VBDR_CustomerChargeHelpMsg, 
	    		ViewBillPageObjects.lbl_NewChargesTable_VBDR_CustomerChargeHelpMsgBub, 
	    		ViewBillPageObjects.lbl_NewChargesTable_VBDR_CustomerChargeHelpMsgClose,
	    		ViewBillPageObjects.lbl_NewChargesTable_VBDR_CustomerChargeHelpMsg.getObjectName());
	    
	    if(!toolTipMessage.isEmpty())
	    commonFunction.compareText(commonFunction.getData("General_Data", "CustomerChargeTooltip", "CustomerCharge Tooltip", true)
	    		, toolTipMessage, "CustomerCharge Tooltip Verification");
	   
	  //Verify NonFuel Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_VBDR_NonFuelHelpMsg, 
	    		ViewBillPageObjects.lbl_NewChargesTable_VBDR_NonFuelHelpMsgBub, 
	    		ViewBillPageObjects.lbl_NewChargesTable_VBDR_NonFuelHelpMsgClose,
	    		ViewBillPageObjects.lbl_NewChargesTable_VBDR_NonFuelHelpMsg.getObjectName());
	    if(!toolTipMessage.isEmpty())
	    commonFunction.compareText(commonFunction.getData("General_Data", "NonFuelTooltip", "NonFuel Tooltip", true)
	    		, toolTipMessage, "NonFuel Tooltip Verification");
	    
	  //Verify Fuel Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_VBDR_FuelHelpMsg, 
	    		ViewBillPageObjects.lbl_NewChargesTable_VBDR_FuelHelpMsgBub,
	    		ViewBillPageObjects.lbl_NewChargesTable_VBDR_FuelHelpMsgClose,
	    		ViewBillPageObjects.lbl_NewChargesTable_VBDR_FuelHelpMsg.getObjectName());
	    
	    if(!toolTipMessage.isEmpty())
	    commonFunction.compareText(commonFunction.getData("General_Data", "FuelTooltip", "Fuel Tooltip", true)
	    		, toolTipMessage, "Fuel Tooltip Verification");
	    
	    //Verify Call Credit Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_ON_CALL_CREDITHelpMsg, 
	    		ViewBillPageObjects.lbl_NewChargesTable_LITR_ON_CALL_CREDITHelpMsgBub, 
	    		ViewBillPageObjects.lbl_NewChargesTable_LITR_ON_CALL_CREDITHelpMsg.getObjectName());
	    
	    if(!toolTipMessage.isEmpty())
	    commonFunction.compareText(commonFunction.getData("General_Data", "CallCreditTooltip", "Call Credit Tooltip", true)
	    		, toolTipMessage, "Call Credit Tooltip Verification");
	    
	    //Verify Storm Charge Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_STORM_CHARGE_OTHERHelpMsg, 
	    		ViewBillPageObjects.lbl_NewChargesTable_LITR_STORM_CHARGE_OTHERHelpMsgBub, 
	    		ViewBillPageObjects.lbl_NewChargesTable_LITR_STORM_CHARGE_OTHERHelpMsg.getObjectName());
	    
	    if(!toolTipMessage.isEmpty())
	    commonFunction.compareText(commonFunction.getData("General_Data", "StormChargeTooltip", "Storm Charge Tooltip", true)
	    		, toolTipMessage, "Storm Charge Tooltip Verification");
	    
	  //Verify Gross Recipt Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_GROSS_RECEIPTS_TAX_INCRHelpMsg, 
	    		ViewBillPageObjects.lbl_NewChargesTable_LITR_GROSS_RECEIPTS_TAX_INCRHelpMsgBub, 
	    		ViewBillPageObjects.lbl_NewChargesTable_LITR_GROSS_RECEIPTS_TAX_INCRHelpMsg.getObjectName());
	    
	    if(!toolTipMessage.isEmpty())
		    commonFunction.compareText(commonFunction.getData("General_Data", "GrossReciptTooltip", "Gross Recipt Tooltip", true)
		    		, toolTipMessage, "Gross Recipt Tooltip Verification");
	    
	  //Verify Franchise Charge Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_FRANCHISE_CHARGEHelpMsg, 
	    		ViewBillPageObjects.lbl_NewChargesTable_LITR_FRANCHISE_CHARGEHelpMsgBub, 
	    		ViewBillPageObjects.lbl_NewChargesTable_LITR_FRANCHISE_CHARGEHelpMsg.getObjectName());
	    
	    if(!toolTipMessage.isEmpty())
		    commonFunction.compareText(commonFunction.getData("General_Data", "FranchiseChargeTooltip", "Franchise Charge Tooltip", true)
		    		, toolTipMessage, "Franchise Charge Tooltip Verification");
	    
	    
	    
	  //Verify Florida Sales Tax Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_FLORIDA_SALES_TAXHelpMsg, 
	    		ViewBillPageObjects.lbl_NewChargesTable_LITR_FLORIDA_SALES_TAXHelpMsgBub, 
	    		ViewBillPageObjects.lbl_NewChargesTable_LITR_FLORIDA_SALES_TAXHelpMsg.getObjectName());
	    
	    if(!toolTipMessage.isEmpty())
		    commonFunction.compareText(commonFunction.getData("General_Data", "FloridaSalesChargeTooltip", "Florida sales tax Tooltip", true)
		    		, toolTipMessage, "Florida sales tax Tooltip Verification");
	    
	    
	    
	  //Verify Discretionary sales surtax Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects. lbl_NewChargesTable_LITR_DISCRETIONARY_SALE_SURTAXHelpMsg, 
	    		ViewBillPageObjects. lbl_NewChargesTable_LITR_DISCRETIONARY_SALE_SURTAXHelpMsgBub, 
	    		ViewBillPageObjects. lbl_NewChargesTable_LITR_DISCRETIONARY_SALE_SURTAXHelpMsg.getObjectName());
	    
	    if(!toolTipMessage.isEmpty())
		    commonFunction.compareText(commonFunction.getData("General_Data", "DiscretionarySalesSurchargeTooltip", "Discretionary Sales Surcharge Tooltip", true)
		    		, toolTipMessage, "Discretionary Sales Surcharge Tooltip Verification");
	    
		    
	    
	  //Verify Utility Tax Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects. lbl_NewChargesTable_LITR_UTILITY_TAXHelpMsg, 
	    		ViewBillPageObjects. lbl_NewChargesTable_LITR_UTILITY_TAXHelpMsgBub, 
	    		ViewBillPageObjects. lbl_NewChargesTable_LITR_UTILITY_TAXHelpMsg.getObjectName());
	    
	    if(!toolTipMessage.isEmpty())
		    commonFunction.compareText(commonFunction.getData("General_Data", "UtilityTaxTooltip", "Utility Tax Tooltip", true)
		    		, toolTipMessage, "Utility Tax Tooltip Verification");
	    
	    
	  //Verify Statement Balance Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_StatementBalanceTooltip, 
	    		ViewBillPageObjects.lbl_StatementBalanceTooltipBub, 
	    		ViewBillPageObjects.lbl_StatementBalanceTooltip.getObjectName());
	    
	    
	  //Close New Charges Section    
	    commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lbl_NewChargesHeader), 
				ViewBillPageObjects.lbl_NewChargesHeader.getObjectName()); 
	    
	    //Verify Meter Comparison Tooltip
	    //String meterComparisonData = getPageElement(ViewBillPageObjects.lbl_MeterComparisonMessage).getText();
	    //String datasheetColumn="null";	    
	    
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_MeterComparisonTooltip, 
	    		ViewBillPageObjects.lbl_MeterComparisonTooltipBub, 
	    		ViewBillPageObjects.lbl_MeterComparisonTooltip.getObjectName());
	    
	    //Which message should be displayed based on customer usage was more or less
	  /*  if(meterComparisonData.contains("more"))
	    	datasheetColumn = "MeterComparisonTooltipUp";
	    else
	    	datasheetColumn = "MeterComparisonTooltipDown";
	    */
	    	
	    /*if(!toolTipMessage.isEmpty())
		    commonFunction.compareText(commonFunction.RemoveSpecialcharacters
		    		(commonFunction.getData("General_Data", datasheetColumn, "Meter Comparison Tooltip", true))
		    		, commonFunction.RemoveSpecialcharacters(toolTipMessage), "Meter Comparison Tooltip Verification");*/
	    
	    //Verify Meter Comparison Day Use Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_MeterComparisonDayUseTooltip, 
	    		ViewBillPageObjects.lbl_MeterComparisonDayUseTooltipBub, 
	    		ViewBillPageObjects.lbl_MeterComparisonDayUseTooltipClose,
	    		ViewBillPageObjects.lbl_MeterComparisonDayUseTooltip.getObjectName());
	    
	    
	  //Verify Meter Comparison Units Comsumed Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_MeterComparisonUnitConsTooltip, 
	    		ViewBillPageObjects.lbl_MeterComparisonUnitConsTooltipBub,
	    		ViewBillPageObjects.lbl_MeterComparisonUnitConsTooltipClose,
	    		ViewBillPageObjects.lbl_MeterComparisonUnitConsTooltip.getObjectName());
	    
	  //Verify Meter Comparison Average Consumption Tooltip
	    toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_MeterComparisonAvgConsTooltip, 
	    		ViewBillPageObjects.lbl_MeterComparisonAvgConsTooltipBub,
	    		ViewBillPageObjects.lbl_MeterComparisonAvgConsTooltipClose,
	    		ViewBillPageObjects.lbl_MeterComparisonAvgConsTooltip.getObjectName());
		    
	}
	
	 /**************************************************************
	 * CRAFT Function to check various promotional messages displayed on
	 * View Bill page
	 * This function validates if View Bill PDF is displayed
	 * 
	 * @author 324096
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @modified by 387478 on Oct 13 - Incorporated Page Object Model
	 * *************************************************************
	 */
	public void verifyPromotionalMessages() throws IOException, InterruptedException{
		action.sendKeys(Keys.END).perform();
		Thread.sleep(3000);
		
		String customerName = commonFunction.getData("Accounts", "CustomerName", "Customer Name",true).trim();
		accountType="residential";
		if(customerName.equals(customerName.toUpperCase()))
			accountType="commercial";
		
		if(accountType.equals("residential")){
			
			//Verify Residential Left Message
			verifyResidentialLeftMessage();			
			action.sendKeys(Keys.END).perform();
			
			//Verify PowerTracker Message
			verifyResidentialCenterPromoMessage();
			action.sendKeys(Keys.END).perform();
			
			//Verify Residential Promotional Message
			verifyResidentialRightPromoMessage();			
		}
		else{
			System.out.println("Checking Commercial Promo Messages");
			verifyCommercialLeftPromoMessage();			
			action.sendKeys(Keys.END).perform();
			
			
			verifyCommercialCenterPromoMessage();
			action.sendKeys(Keys.END).perform();
			
			//Verify Residential Promotional Message
			verifyCommercialRightPromoMessage();
		}
		
		action.sendKeys(Keys.HOME).perform();
	}
	

	/************************************************************************
	 * @description Method to perform mouse hover on given tooltip
	 * @param element
	 *            - element on which mouse hover action needs to be performed
	 * @param tooltipElement
	 * 			  - element from which text is to be extracted
	 * @param objName
	 * 			  - ObjName of the element which is to be updated in TestLog
	 * @author 387478
	 * @modified_date Oct 15, 2013
	 */
	public String verifyTooltipMessage(ViewBillPageObjects element, ViewBillPageObjects tooltipElement, String objName){
			String tooltipMessage = ""; //initialize ToolTip Message to blank
			try{
				//Verify if TootlTip icon is displayed
				if(commonFunction.isElementPresent(element.getLocatorType().toString(),element.getProperty(),"",false)){
					//Hover the tooltip icon
					WebElement tooltipIcon = getPageElement(element);
					Robot robot = new Robot();
					robot.mouseMove(0, 0);
					Actions builder = new Actions(driver);
					builder.moveToElement(tooltipIcon).build().perform();
					
					//Verify if tootltip message box is displayed upon hover
					if(commonFunction.isElementPresent(tooltipElement.getLocatorType().toString(),tooltipElement.getProperty(),"",false)){
						//Get the data present in tooltip message
						tooltipMessage = getPageElement(tooltipElement).getText();
						
						//Trim the message and eliminate the first three characters since they are irrelavent
						tooltipMessage = tooltipMessage.trim().substring(2);	
						
						//Update Report with the extracted tooltipMessage
						report.updateTestLog(objName,
								"Tooltip Message Contents:"+tooltipMessage, Status.PASS);			
						
					}else{
						report.updateTestLog(objName,
								"Unable to extract text from tooltip", Status.WARNING);						
					}
				}else{
					report.updateTestLog(objName,
							"Tooltip is not displayed", Status.WARNING);
				}
				
			} catch(Exception e){
				report.updateTestLog(objName,
						"Tooltip is not present or unable to extract text from tooltip", Status.WARNING);
			}
			return tooltipMessage;
	}
	
	/**
	 * @description Method to perform mouse hover on given tooltip and close the tooltip
	 * @param element
	 *            - element on which mouse hover action needs to be performed
	 * @param tooltipElement
	 * 			  - element from which text is to be extracted
	 * 
	 * @param tooltipElement
	 * 			  - element to close the tooltip
	 * 
	 * @param objName
	 * 			  - ObjName of the element which is to be updated in TestLog
	 * 
	 * @return String tooltipMessage
	 * 		   Returns empty string if the tooltip message is not found. Returns tooltip message if the tooltip is found 
	 * 
	 * @author 387478
	 * @modified_date Oct 15, 2013
	 */
	public String verifyTooltipMessage(ViewBillPageObjects element, ViewBillPageObjects tooltipElement
			,ViewBillPageObjects tooltipElementClose, String objName){
			String tooltipMessage = ""; //initialize ToolTip Message to blank
			try{
				//Verify if TootlTip icon is displayed
				if(commonFunction.isElementPresent(element.getLocatorType().toString(),element.getProperty(),"",false)){
					//Hover the tooltip icon
					WebElement tooltipIcon = getPageElement(element);
					Robot robot = new Robot();
					robot.mouseMove(0, 0);
					Actions builder = new Actions(driver);
					builder.moveToElement(tooltipIcon).build().perform();
					
					//Verify if tootltip message box is displayed upon hover
					if(commonFunction.isElementPresent(tooltipElement.getLocatorType().toString(),tooltipElement.getProperty(),"",false)){
						//Get the data present in tooltip message
						tooltipMessage = getPageElement(tooltipElement).getText();
						
						//Trim the message and eliminate the first three characters since they are irrelavent
						tooltipMessage = tooltipMessage.trim().substring(2);	
						
						//Update Report with the extracted tooltipMessage
						report.updateTestLog(objName,
								"Tooltip Message Contents:"+tooltipMessage, Status.PASS);
						
						//Close tooltip message
						if(commonFunction.isElementPresent(tooltipElementClose.getLocatorType().toString(),tooltipElementClose.getProperty(),"",false)){
							try{
							getPageElement(tooltipElementClose).click();
							report.updateTestLog("Close Tooltip",
									"Tooltip is closed", Status.PASS);
							}catch(Exception e){
								report.updateTestLog("Close Tooltip",
										"Unable to close the tooltip", Status.WARNING);
							}
						}else{
							report.updateTestLog("Close Tooltip",
									"Closed tooltip icon is not displayed", Status.WARNING);
						}
						
					}else{
						report.updateTestLog(objName,
								"Unable to extract text from tooltip", Status.WARNING);						
					}
				}else{
					report.updateTestLog(objName,
							"Tooltip is not present", Status.WARNING);
				}
				
			} catch(Exception e){
				report.updateTestLog(objName,
						"Tooltip is not present or unable to extract text from tooltip", Status.WARNING);
			}
			return tooltipMessage;
	}
	
	/**********************************************************
	 * View Bill PDF
	 * This function validates if View Bill PDF is displayed
	 * on clicking 'Download Bill' button in 'Your Bills' section
	 * and takes screenshot of the PDF
	 * 
	 * @author 324096
	 * @throws IOException
	 * 
	 * **********************************************************
	 */
	public void viewBillPDFDownload() throws InterruptedException{		
		    //Loop for all 'Download Bill' links in "Your Bills" section 
		    try{
			    String winHandleBefore1 = driver.getWindowHandle();
			    driver.findElement(By.xpath("(//a[contains(text(),'Download Bill')])[2]")).click();
			    
			    for(String winHandle : driver.getWindowHandles()){
			        if(!winHandle.equals(winHandleBefore1)){
			            driver.switchTo().window(winHandle);
			            Thread.sleep(10000);
			            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			            report.updateTestLog("Account History: Your Bills", "User Clicks on Download Bill Link", Status.PASS);
			            driver.close();
			            break;	
			        }
			    }
		    driver.switchTo().window(winHandleBefore1);
		    }
		    catch(Exception ex){
		    	report.updateTestLog("Account History: Your Bills", "VB PDF not found", Status.FAIL);
		    }
		    
	}
	

	 /*********************************************************
	 * Function to return WebElement for ViewBillPageObjects
	 * 
	 * This function takes ViewBillPageObjects object as an
	 * input and returns the WebElement object for the input 
	 * object 
	 * 
	 * @param viewBillPageEnum 
	 * 		  The enum element for which WebElement object needs to be 
	 *        created
	 * @return WebElement
	 * 		   WebElement object corresponding to the input enum is 
	 * 		   returned 
	 * ********************************************************
	 */
	
	private  WebElement getPageElement(ViewBillPageObjects viewBillPageEnum)
	{
	    try{
	    	
	        return commonFunction.getElementByProperty(viewBillPageEnum.getProperty(), viewBillPageEnum
	        .getLocatorType().toString());
	    } catch(Exception e){
	        report.updateTestLog("Get page element", viewBillPageEnum.toString()
	        + " object is not defined or found.", Status.FAIL);
	        return null;
	    }
	}
	
	
	/*********************************************************
	 * Function to return WebElements for ViewBillPageObjects
	 * 
	 * This function takes ViewBillPageObjects object as an
	 * input and returns the WebElement object for the input 
	 * object 
	 * 
	 * @param viewBillPageEnum 
	 * 		  The enum element for which WebElement object needs to be 
	 *        created
	 * @return WebElement
	 * 		   WebElement object corresponding to the input enum is 
	 * 		   returned 
	 * ********************************************************
	 */
	
	private  List<WebElement> getPageElements(ViewBillPageObjects viewBillPageEnum)
	{
	    try{
	    	
	        return commonFunction.getElementsByProperty(viewBillPageEnum.getProperty(), viewBillPageEnum
	        .getLocatorType().toString());
	    } catch(Exception e){
	        report.updateTestLog("Get page element", viewBillPageEnum.toString()
	        + " object is not defined or found.", Status.FAIL);
	        return null;
	    }
	}
	
	
	 /***********************************************************
	 * downloadBill
	 * This method verifies a new window is opened when user 
	 * clicks 'Download Bill' link and takes the screenshot 
	 * of the bill
	 * 
	 * @param lnk_Downloadbill
	 *        ViewBillPageObject which is to be downladed
	 * @throws InterruptedException 
	 * 
	 * ***********************************************************
	 */
	private void downloadBill(ViewBillPageObjects downloadlink, String objName) throws InterruptedException {
		
		String winHandleBefore = driver.getWindowHandle();
		if(commonFunction.clickIfElementPresent(getPageElement(downloadlink), objName)){
		Actions action = new Actions(driver);
		
		 //Navigate to the BILL PDF and take screenshot
		 for(String winHandle : driver.getWindowHandles()){
		        if(!winHandle.equals(winHandleBefore)){
		            driver.switchTo().window(winHandle);
		            Thread.sleep(10000);
		            driver.manage().window().maximize();
		            report.updateTestLog(objName, "Take Screesnhot of displayed bill", Status.SCREENSHOT);
		            action.sendKeys(Keys.END).perform();
		            report.updateTestLog(objName, "Take Screesnhot of displayed bill", Status.SCREENSHOT);
		            /*driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "c"));
		            
		            System.out.println("");
		            System.out.println(getClipboardContents());*/
		            
		            driver.close(); 
		        }
		    }  
		    
		    //Navigate back to original window
		    driver.switchTo().window(winHandleBefore);
		}
	}
	
	/***********************************************************
	 * verifyOHESPromoMessage
	 * -- This method verifies OHES Header, OHES Message and 'Get Started' link 
	 * 
	 * 
	 * ***********************************************************
	 */
	private void verifyResidentialLeftMessage() throws InterruptedException{
		action.sendKeys(Keys.END).perform();
		String environment = properties.getProperty("Environment");
		//Verify OHES header
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_OHESPromoHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_OHESPromoHeader.getProperty(), ViewBillPageObjects.lbl_OHESPromoHeader.getObjectName())){
			String header = getPageElement(ViewBillPageObjects.lbl_OHESPromoHeader).getText();		
			commonFunction.compareText(commonFunction.getData("General_Data", "Res_Left_Header","Residential Left Header", true),header
					,ViewBillPageObjects.lbl_OHESPromoHeader.getObjectName());
		}
		
		//Verify Light Bulb Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_LightBulb.getLocatorType().toString(),
				ViewBillPageObjects.img_LightBulb.getProperty(), ViewBillPageObjects.img_LightBulb.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_LightBulb),
					commonFunction.getData(environment,"General_Data", "LightBulbImageURL","Light Bulb Image URL",true),
					ViewBillPageObjects.img_LightBulb.getObjectName());
		}
		//Verify OHES Message
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_OHESMessage.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_OHESMessage.getProperty(), ViewBillPageObjects.lbl_OHESMessage.getObjectName())){
			String ohesMessage = getPageElement(ViewBillPageObjects.lbl_OHESMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Res_Left_Message", "Residential Left Message",true),
					ohesMessage.substring(0,ohesMessage.length() - 12),
					ViewBillPageObjects.lbl_OHESMessage.getObjectName());
		}
		
		//Click Link and Verify URL for 'Get Started'
		//String currentURL = driver.getCurrentUrl();
		
		action.sendKeys(Keys.END).perform();
		//System.out.println(column);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_GetStarted.getLocatorType().toString(), 
			ViewBillPageObjects.lnk_GetStarted.getProperty(), ViewBillPageObjects.lnk_GetStarted.getObjectName(),true)){
			/*		commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_GetStarted),
					commonFunction.getData("General_Data", column,"OHES Get Started URL",true),
					ViewBillPageObjects.lnk_GetStarted.getObjectName()
					);
			driver.get(currentURL);*/
			commonFunction.compareText(commonFunction.getData(environment,"General_Data", "Res_Left_Url","OHES Get Started URL",true), getPageElement(ViewBillPageObjects.lnk_GetStarted).getAttribute("href"),
					ViewBillPageObjects.lnk_GetStarted.getObjectName()+" verification");
				
		}
		action.sendKeys(Keys.END).perform();
		Thread.sleep(1000);
	}
	
	/***********************************************************
	 * verifyPowerTrackerPromoMessage
	 * -- This method verifies PowerTracker Header, PowerTracker Message and 'Here's How' link 
	 * 
	 * ***********************************************************
	 */
	private void verifyResidentialCenterPromoMessage() throws InterruptedException{
		Thread.sleep(1000);
		action.sendKeys(Keys.END).perform();
		//Verify PowerTracker Header
		String environment = properties.getProperty("Environment");
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_PowertrackerHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_PowertrackerHeader.getProperty(), ViewBillPageObjects.lbl_PowertrackerHeader.getObjectName())){
		commonFunction.compareText(commonFunction.getData("General_Data", "Res_Center_Header", "Residential Center Header",true),
				getPageElement(ViewBillPageObjects.lbl_PowertrackerHeader).getText(),ViewBillPageObjects.lbl_PowertrackerHeader.getObjectName());
		}
		
		//Verify Check Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_CheckImage.getLocatorType().toString(),
				ViewBillPageObjects.img_CheckImage.getProperty(), ViewBillPageObjects.img_CheckImage.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_CheckImage),
					commonFunction.getData(environment,"General_Data", "CheckImageURL","Check Image URL",true),
					ViewBillPageObjects.img_CheckImage.getObjectName());
		}
		
		//Verify PowerTracker Message
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_PowertrackerMessage.getLocatorType().toString(), 
					ViewBillPageObjects.lbl_PowertrackerMessage.getProperty(), ViewBillPageObjects.lbl_PowertrackerMessage.getObjectName())){
			String powerTrackerMessage = getPageElement(ViewBillPageObjects.lbl_PowertrackerMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Res_Center_Message","Residential Center Message",true),
					powerTrackerMessage.substring(0, powerTrackerMessage.length() - 11),ViewBillPageObjects.lbl_PowertrackerMessage.getObjectName());
			
		}
		String currentURL = driver.getCurrentUrl();
		
		action.sendKeys(Keys.END).perform();
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_Hereshow.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_Hereshow.getProperty(), ViewBillPageObjects.lnk_Hereshow.getObjectName(),true)){
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_Hereshow),
					commonFunction.getData(environment,"General_Data", "Res_Center_Url","Residential Center URL",true),
					ViewBillPageObjects.lnk_Hereshow.getObjectName()
					);
			driver.navigate().to(currentURL);
			Thread.sleep(1000);
		}
	}
	
	private void verifyResidentialRightPromoMessage() throws InterruptedException{
		Thread.sleep(3000);
		action.sendKeys(Keys.END).perform();
		//Verify Right Promotional Message Header
		String environment = properties.getProperty("Environment");
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_ResidentialRightHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_ResidentialRightHeader.getProperty(), ViewBillPageObjects.lbl_ResidentialRightHeader.getObjectName())){
		commonFunction.compareText(commonFunction.getData("General_Data", "Res_Right_Header", "Residential Right Header",true),
				getPageElement(ViewBillPageObjects.lbl_ResidentialRightHeader).getText(),ViewBillPageObjects.lbl_ResidentialRightHeader.getObjectName());
		}
		
		//Verify Icon Pointer Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_RightImage.getLocatorType().toString(),
				ViewBillPageObjects.img_RightImage.getProperty(), ViewBillPageObjects.img_RightImage.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_RightImage),
					commonFunction.getData(environment,"General_Data", "PointerImageUrl","Check Image URL",true),
					ViewBillPageObjects.img_RightImage.getObjectName());
		}
		
		//Verify Right Promotional Message
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_ResidentialRightMessage.getLocatorType().toString(), 
					ViewBillPageObjects.lbl_ResidentialRightMessage.getProperty(), ViewBillPageObjects.lbl_ResidentialRightMessage.getObjectName())){
			String powerTrackerMessage = getPageElement(ViewBillPageObjects.lbl_ResidentialRightMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Res_Right_Message","Residential Right Message",true),
					powerTrackerMessage.substring(0, powerTrackerMessage.length() - 8),ViewBillPageObjects.lbl_ResidentialRightMessage.getObjectName());
			
		}
		String currentURL = driver.getCurrentUrl();
		
		//See How URL
		action.sendKeys(Keys.END).perform();
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_Seehow.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_Seehow.getProperty(), ViewBillPageObjects.lnk_Seehow.getObjectName(),true)){
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_Seehow),
					commonFunction.getData(environment,"General_Data", "Res_Right_Url","Residential Right URL",true),
					ViewBillPageObjects.lnk_Seehow.getObjectName()
					);
			driver.navigate().to(currentURL);
		}
	}
	
	
	
	/***********************************************************
	 * verifyCommercialLeftMessage
	 * -- This method verifies OBEE Header, OBEE Message and 'Let Us Help' link 
	 * 
	 * 
	 * ***********************************************************
	 */
	private void verifyCommercialLeftPromoMessage() throws InterruptedException{
		action.sendKeys(Keys.END).perform();
		String environment = properties.getProperty("Environment");
		//Verify Commercial Left header
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_LeftHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_LeftHeader.getProperty(), ViewBillPageObjects.lbl_LeftHeader.getObjectName())){
			String ohesHeader = getPageElement(ViewBillPageObjects.lbl_LeftHeader).getText();		
			commonFunction.compareText(commonFunction.getData("General_Data", "Com_Left_Header","Commercial Left Header", true),
					ohesHeader,ViewBillPageObjects.lbl_LeftHeader.getObjectName());
		}
		
		//Verify Icon Arrow Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_IconArrow.getLocatorType().toString(),
				ViewBillPageObjects.img_IconArrow.getProperty(), ViewBillPageObjects.img_IconArrow.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_IconArrow),
					commonFunction.getData(environment,"General_Data", "ArrowImageUrl","Icon Arrow Image URL",true),
					ViewBillPageObjects.img_IconArrow.getObjectName());
		}
		
		//Verify Commercial Left Message
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_LeftMessage.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_LeftMessage.getProperty(), ViewBillPageObjects.lbl_LeftMessage.getObjectName())){
			String ohesMessage = getPageElement(ViewBillPageObjects.lbl_LeftMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Com_Left_Message", "Commercial Left Message",true),
					ohesMessage.substring(0,ohesMessage.length() - 12),
					ViewBillPageObjects.lbl_LeftMessage.getObjectName());
		}
		
		//Click Link and Verify URL for 'Lower Bill'
		String currentURL = driver.getCurrentUrl();
		//System.out.println(properties.getProperty("Environment").toLowerCase());
		
		
		action.sendKeys(Keys.END).perform();
		//System.out.println(column);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_LowerBill.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_LowerBill.getProperty(), ViewBillPageObjects.lnk_LowerBill.getObjectName(),true)){
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_LowerBill),
					commonFunction.getData(environment,"General_Data", "Com_Left_Url","Commercial Left URL",true),
					ViewBillPageObjects.lnk_LowerBill.getObjectName()
					);
			driver.navigate().to(currentURL);
			Thread.sleep(5000);
		}
		
		action.sendKeys(Keys.END).perform();
	}

	private void verifyCommercialCenterPromoMessage() throws InterruptedException{
		action.sendKeys(Keys.END).perform();
		String environment = properties.getProperty("Environment");
		//Verify Commercial Center header
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_CenterHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_CenterHeader.getProperty(), ViewBillPageObjects.lbl_CenterHeader.getObjectName())){
			String ohesHeader = getPageElement(ViewBillPageObjects.lbl_CenterHeader).getText();		
			commonFunction.compareText(commonFunction.getData("General_Data", "Com_Center_Header","Commercial Center Header", true),
					ohesHeader, ViewBillPageObjects.lbl_CenterHeader.getObjectName());
		}
		
		//Verify Sun Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_SunImage.getLocatorType().toString(),
				ViewBillPageObjects.img_SunImage.getProperty(), ViewBillPageObjects.img_SunImage.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_SunImage),
					commonFunction.getData(environment,"General_Data", "SunImageUrl","Sun Image URL",true),
					ViewBillPageObjects.img_SunImage.getObjectName());
		}
		
		//Verify Commercial Center Message
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_CenterMessage.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_CenterMessage.getProperty(), ViewBillPageObjects.lbl_CenterMessage.getObjectName())){
			String ohesMessage = getPageElement(ViewBillPageObjects.lbl_CenterMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Com_Center_Message", "Commercial Center Message",true),
					ohesMessage.substring(0,ohesMessage.length() - 12),
					ViewBillPageObjects.lbl_CenterMessage.getObjectName());
		}
		
		//Click Link and Verify URL for 'Lower Bill'
		String currentURL = driver.getCurrentUrl();
		//System.out.println(properties.getProperty("Environment").toLowerCase());
		
		
		action.sendKeys(Keys.END).perform();
		//System.out.println(column);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_SolarAction.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_SolarAction.getProperty(), ViewBillPageObjects.lnk_SolarAction.getObjectName(),true)){
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_SolarAction),
					commonFunction.getData(environment,"General_Data", "Com_Center_Url","Commercial Center URL",true),
					ViewBillPageObjects.lnk_SolarAction.getObjectName()
					);
			driver.navigate().to(currentURL);
			Thread.sleep(5000);
		}
		
		action.sendKeys(Keys.END).perform();
	}
	
	public void verifyCommercialRightPromoMessage() throws IOException, InterruptedException{
		action.sendKeys(Keys.END).perform();
		String environment = properties.getProperty("Environment");
		//Verify Commercial Right Header
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_RightHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_RightHeader.getProperty(), ViewBillPageObjects.lbl_RightHeader.getObjectName()))
			commonFunction.verifyElementPresentEqualsText(getPageElement(ViewBillPageObjects.lbl_RightHeader), 
					ViewBillPageObjects.lbl_RightHeader.getObjectName(), commonFunction.getData("General_Data", "Com_Right_Header","Commercial Right Header",true));
			
		//Verify Icon Pointer Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_IconPointer.getLocatorType().toString(),
				ViewBillPageObjects.img_IconPointer.getProperty(), ViewBillPageObjects.img_IconPointer.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_IconPointer),
					commonFunction.getData(environment,"General_Data", "ArrowImageUrl","Icon Pointer Image URL",true),
					ViewBillPageObjects.img_IconPointer.getObjectName());
		}
		
		//Verify Commercial Right Message
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_RightMessage.getLocatorType().toString(), 
					ViewBillPageObjects.lbl_RightMessage.getProperty(), ViewBillPageObjects.lbl_RightMessage.getObjectName())){				
			String residentialMessage = getPageElement(ViewBillPageObjects.lbl_RightMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Com_Right_Message","Commercial Right Message",true),
					residentialMessage.substring(0,residentialMessage.length() - 10),
					ViewBillPageObjects.lbl_RightMessage.getObjectName());
			}
				
		String currentURL = driver.getCurrentUrl();
		
		
		action.sendKeys(Keys.END).perform();
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_BlogPost.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_BlogPost.getProperty(), ViewBillPageObjects.lnk_BlogPost.getObjectName(),true)){				
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_BlogPost),
					commonFunction.getData(environment,"General_Data", "Com_Right_Url","Commercial Learn How URL",true),
					ViewBillPageObjects.lnk_BlogPost.getObjectName()
					);
			driver.navigate().to(currentURL);
		}		
	}
}

