/**
 * 
 */
package componentgroups;


/*
 * 
 * The CommonFunctions java class is a compilation of reusable functions, which aid in rapid development of 
 * selenium scripts. It also promotes novice users to create highly stable selenium scripts. 
 * 
 * @Purpose       - Common Function Class
 * @author        - Cognizant
 * @Created       - 09 Nov 2011
 *  
 **********************************************
 */
import java.awt.Robot;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageobjects.ObjectLocator;
import pageobjects.ViewBillPageObjects;

//import supportlibraries.Asserts;
import supportlibraries.Browser;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import supportlibraries.WebDriverFactory;

import bsh.ParseException;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import com.thoughtworks.selenium.Wait;
//import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

@SuppressWarnings("unused")
public class CommonFunctions extends ReusableLibrary{
	public String ORName = "Common_OR";
	private final int SHORT_TO = 5;
	public static Wait wait;

	

	public CommonFunctions(ScriptHelper scriptHelper){
		super(scriptHelper);
	}

	private void assertTrue(boolean textPresent){
		
	}
	
	/*******************************************************
	 * Function to Check whether Frame is present ON web page or not
	 * @param frameId
	 * @return
	 * ******************************************************
	 */
	public boolean isFramePresent(String frameId)
	{
		try {
				WebElement frame = driver.findElement(By.id(frameId));
			    if(frame.isDisplayed())
			    {
			    	return true;
			    }
			    else
			    {
			    	return false;
			    }
		    } 
	    catch (NoSuchElementException e) 
	    	{
				return false;
	    	}
	}	
	
	/*******************************************************
	 * Function to check whether Element is present on page
	 * 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public boolean isElementPresent(By by)
	{
		try 
		{
			driver.findElement(by);
			return true;
		} 
		catch (NoSuchElementException e) 
		{
			return false;
		}
	}
	
	
	/**
	 * Verify if element is present on page
	 * 
	 * @param strObjectProperty
	 *            -
	 * @param strFindElementType
	 *            - Element type to search by.
	 * @return returns true if the element exist, otherwise, false.
	 */
	public boolean isElementPresent(String strObjectProperty, String strFindElementType){
		WebElement elemToFind = null;
		try{

			if(strFindElementType.equalsIgnoreCase("CSS"))
				elemToFind = driver.findElement(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				elemToFind = driver.findElement(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				elemToFind = driver.findElement(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				elemToFind = driver.findElement(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				elemToFind = driver.findElement(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				elemToFind = driver.findElement(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				elemToFind = driver.findElement(By.className(strObjectProperty));
			// report.updateTestLog("isElementPresent","Expected...",
			// Status.PASS);

		} catch(org.openqa.selenium.NoSuchElementException nsee){
			System.out.println("Exception in isElementPresent:" + nsee);
			return false;
		}
		// Extra protection...
		if(elemToFind == null){
			return false;
		} else{
			System.out.println("Found element:" + strObjectProperty);
			return true;
		}
	}
	
	/**
	 ************************************************************* 
	 * Function to verify if an element exists on the Webpage
	 * Does not print error to log if element is not found
	 * 
	 * @param locatorType
	 *            - Element type to search by. i.e CSS,ID,NAME etc.
	 *            
	 * @param property
	 *            - Property of the element to be located 
	 *            
	 * @param objName
	 *            - Name of the object to be updated in test log
	 *              Required when printError is true           
	 *            
	 * @param printError
	 * 			- If true, error will be printed to log when element
	 * 			  is not found.
	 *          - If false, no error will be printed to the log* 
	 *            
	 * @return true
	 *         If the element exists on page
	 *         
	 * @return false 
	 *   	   If the element does not exist on page
	 *   
	 * @author 387478  
	 ************************************************************* 
	 */
	public boolean isElementPresent(String locatorType, String property, String objName, Boolean printError){
		WebElement elemToFind;	
		try{	    	
				elemToFind = getElementByProperty(property,locatorType,false);
				if(!elemToFind.isDisplayed()){
					if(printError)
			    		report.updateTestLog(objName, objName+" element is not displayed", Status.FAIL);
					
					return false;
				}
		    } catch(Exception e){
		    	if(printError)
		    		report.updateTestLog(objName, objName+" element is not displayed", Status.FAIL);
		        return false;
		    }
		    if(elemToFind == null){
		    	if(printError)
		    		report.updateTestLog(objName, objName+" element is not displayed", Status.FAIL);
		    	return false;
		    }
			return true;
	}
	
	
	/**
	 ************************************************************* 
	 * Function to verify if an element is present in the application, not using
	 * OR.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean isElementPresentVerification(WebElement elemToVerify, String strObjName){
		
		try{
			if(elemToVerify.isDisplayed()){
				//report.updateTestLog((strObjName + " element is present"), strObjName+ " is verified successfully", Status.PASS);
				return true;
			} else{
				report.updateTestLog((strObjName + " element is present"), "'" + strObjName
						+ "' is NOT displayed", Status.FAIL);
				return false;
			}
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Error in identifying element (" + strObjName + ")", nsee.toString(), Status.FAIL);
			return false;
		} catch(Exception e){
			report.updateTestLog("IS ELEMENT PRESENT VERIFICATION", "Error in identifying object (" + strObjName
					+ ") -" + e.toString(), Status.FAIL);
			return false;
		}
	}
	
	
	/**
	 ************************************************************* 
	 * Function to verify and update test log if an element exists on the Webpage
	 * 
	 * @param  strFindElementType 
	 * 		   Type of element i.e ID, CSS, NAME, etc.
	 * 
	 * @param strObjectProperty
	 *        Propertu of the element which is to be searched
	 * @return true
	 *         If the element exists on page
	 *         
	 * @return false 
	 *   	   If the element does not exist on page
	 ************************************************************* 
	 */
	public boolean verifyIfElementIsPresent(String strFindElementType, String strObjectProperty, String objName){
		WebElement elemToFind;
		try{	    	
				
					if(strFindElementType.equalsIgnoreCase("CSS"))
						elemToFind = driver.findElement(By.cssSelector(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("XPATH"))
						elemToFind =  driver.findElement(By.xpath(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("ID"))
						elemToFind =  driver.findElement(By.id(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("NAME"))
						elemToFind = driver.findElement(By.name(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
						elemToFind = driver.findElement(By.linkText(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("TAG"))
						elemToFind =  driver.findElement(By.tagName(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("CLASS"))
						elemToFind =  driver.findElement(By.className(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("PARTIALLINKTEXT"))
						elemToFind = driver.findElement(By.partialLinkText(strObjectProperty));
					else
						elemToFind = null;
				
		    } catch(Exception e){
		    	report.updateTestLog(objName, objName+" element is not displayed", Status.FAIL);
		        return false;
		    }
		    if(elemToFind == null ||(!elemToFind.isDisplayed())){
		    	report.updateTestLog(objName, objName+" element is not displayed", Status.FAIL);
		    	return false;
		    }
		    if(elemToFind.isDisplayed()){
			    report.updateTestLog(objName, objName+" is displayed", Status.PASS);	
				return true;
		    }else{
		    	report.updateTestLog(objName, objName+" element is not displayed", Status.FAIL);
		    	return false;
		    }
	}
	
	/**
	 * Method to Verify Element is Not Present For Negative Scenario
	 * 
	 * @param elemToVerify
	 * @param strObjName
	 */

	public void verifyIsElementNotPresent(String strObjectProperty, String strFindElementType, String strObjName){
		WebElement element=null;
		try{

			if(strFindElementType.equalsIgnoreCase("CSS")){
				element = driver.findElement(By.cssSelector(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("XPATH")){
				element = driver.findElement(By.xpath(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("ID")){
				element = driver.findElement(By.id(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("NAME")){
				element = driver.findElement(By.name(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("LINKTEXT")){
				element = driver.findElement(By.linkText(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("TAG")){
				element = driver.findElement(By.tagName(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("CLASS")){
				element = driver.findElement(By.className(strObjectProperty));
			} else{
			}
			
			if(element.isDisplayed())
				report.updateTestLog("verifyIsElementNotPresent - (" + strObjName + ")", "("
						+ strObjName + ")" + " Is Present!!Not expected", Status.FAIL);
			else
				report.updateTestLog("verifyIsElementNotPresent - identifying element (" + strObjName
						+ ")", "(" + strObjName + ") is NOT PRESENT as expected", Status.PASS);
			

		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("verifyIsElementNotPresent - identifying element (" + strObjName
					+ ")", "(" + strObjName + ") is NOT PRESENT as expected", Status.PASS);
		}

	}
	
	
	/**
	 * Method to Verify Element is Not Present within a parent element -
	 * Negative Scenario
	 * 
	 * @param elemToVerify
	 * @param strObjName
	 */
	public boolean verifyIsElementNotPresent(WebElement elmt, String strParentElementName, String strObjectProperty,
			String strFindElementType, String strObjName){
	
		try{

			if(strFindElementType.equalsIgnoreCase("CSS")){
				elmt.findElement(By.cssSelector(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("XPATH")){
				elmt.findElement(By.xpath(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("ID")){
				elmt.findElement(By.id(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("NAME")){
				elmt.findElement(By.name(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("LINKTEXT")){
				elmt.findElement(By.linkText(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("TAG")){
				elmt.findElement(By.tagName(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("CLASS")){
				elmt.findElement(By.className(strObjectProperty));
			} else{
			}
			report.updateTestLog("verifyIsElementNotPresent".toUpperCase() + " - (" + strObjName + ")", "("
					+ strObjName + ")" + " Is Present with property : " + strObjectProperty + " within the Element "
					+ strParentElementName + "!!Not expected", Status.FAIL);
			return false;

		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("verifyIsElementNotPresent".toUpperCase() + " - identifying element (" + strObjName
					+ ")", "(" + strObjName + ")" + " with property: " + strObjectProperty
					+ " Is Not Present within the Element " + strParentElementName + " as expected", Status.PASS);
			return true;
		}
	}
	
	
	
	
	/**
	 ************************************************************* 
	 * Function to Retrieve data from dataTable.
	 * 
	 * @param sheetName
	 * 		  Sheet from which data is to be retrieved
	 *            
	 * @param columnName
	 * 		  Column from which data is to be retrieved
	 * 
	 * @param objName
	 * 		  Description of the retrieved data which is to be updated in Test Log
	 * 
	 * @param displayError
	 * 		  Displays error message if true is passed and data is blank in sheet
	 * 		  No error message is displayed if  false is passed
	 *            
	 * @return String
	 * 
	 * @author 387478
	 * 			
	 ************************************************************* 
	 */
	public String getData(String sheetName, String columnName, String objName, boolean displayError)
	{
		String retrievedData ="";
		String defaultValue ="BLANK";

		if(!objName.isEmpty()){
			if(objName.equals("Account Balance"))
				defaultValue = "Zero";
			
			if(objName.equals("Ami"))
				defaultValue = "Yes";
			
			}
		
		try{
			retrievedData =  dataTable.getData(sheetName, columnName);
			//retrievedData =  dataTable.getData("Accounts", "AccountNumber");
			if(displayError){
				if(retrievedData.isEmpty())
					report.updateTestLog("Retrieve data from "+columnName, objName + " is blank in "+ columnName +" column. Setting it to " + defaultValue +" by default.", Status.WARNING);					
			}
		}catch(Exception e){
			if(displayError)
				report.updateTestLog(objName, "Unable to retrieve data from Common Data", Status.WARNING);
		}
		if(retrievedData.isEmpty()){
			if(objName.equals("Account Balance"))
				retrievedData = "0";
			
			if(objName.equals("Ami"))
				retrievedData = "Yes";
		}
		return retrievedData.trim();
	}
	
	/**
	 ************************************************************* 
	 * Function to convert date from Apr 04, 2014 to Apr 4, 2014
	 * 
	 * @author 387478
	 ************************************************************* 
	 */
	public String convertDatetoSingleDigit(String inputdate) throws ParseException{
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
		try{
			Date date = formatter.parse(inputdate);
			String output = new SimpleDateFormat("MMM d, yyyy").format(date);
			return output;
		}catch(Exception e){
			return "";
		}
		
	}
	
	/**
	 ************************************************************* 
	 * Function to verify source of image
	 * 
	 * @param element
	 *        Element from which src is to be extracted
	 *         
	 * @param expectedSource
	 *		  Expected value of src Attribute
	 *
	 *@param objName
	 *       Name of the object to be updated in Test Log
	 * @author 387478
	 * @modified_by Shabbir on 31 Oct
	 * 				
	 * 			
	 * @return void
	 ************************************************************* 
	 */
	
	public void verifyImageSource(WebElement element,String expectedSource, String objName){
		String actualSource="";
		try{
			actualSource = element.getAttribute("src");
			if(actualSource.equals(expectedSource))
				report.updateTestLog("Verify "+objName+" Image", "Verification is success. Image points to correct source.",
						Status.PASS);
			else 
				report.updateTestLog("Verify "+objName+" Image", "Verification Failure. Expected Source:"+ expectedSource+". Actual Source:"+actualSource,
						Status.FAIL);
		}catch(Exception e){
			report.updateTestLog("Verify "+objName+" Image", "Verification Failure. Expected Source:"+ expectedSource+". Actual Source:"+actualSource,
					Status.FAIL);
		}
	}
	
	
	/**
	 ************************************************************* 
	 * Function to Retrieve data from dataTable based on input envrionment.
	 * This function will append QA_,Test_ or Prod_ in columnName based on the environment
	 * 
	 * @param environment
	 *        Envrionment for which data is to be retrieved		  
	 * 
	 * @param sheetName
	 * 		  Sheet from which data is to be retrieved
	 *            
	 * @param columnName
	 * 		  Column from which data is to be retrieved
	 * 
	 * @param objName
	 * 		  Description of the retrieved data which is to be updated in Test Log
	 * 
	 * @param displayError
	 * 		  Displays error message if true is passed and data is blank in sheet
	 * 		  No error message is displayed if  false is passed
	 *            
	 * @return String
	 * 
	 * @author 387478
	 * 			
	 ************************************************************* 
	 */
	public String getData(String environment, String sheetName, String columnName, String objName, boolean displayError){
		String retrievedData ="";
		String defaultValue ="BLANK";
		//Set SheetName based on environemtn
		if(environment.toLowerCase().equals("qa"))
			columnName = "QA_" + columnName;
		else if(environment.toLowerCase().equals("test"))
			columnName = "Test_" + columnName;
		else 
			columnName = "Prod_" + columnName;
			
		
		if(objName.equals("Account Balance"))
			defaultValue = "Zero";
		try{
			retrievedData =  dataTable.getData(sheetName, columnName);
			if(displayError){
				if(retrievedData.isEmpty())
					report.updateTestLog("Retrieve data from "+columnName, objName + " is blank in "+ columnName +" column. Setting it to " + defaultValue +" by default.", Status.WARNING);
			}
		}catch(Exception e){
			if(displayError)
				report.updateTestLog(objName, "Unable to retrieve data from Common Data:" +columnName+ ". Setting it to " + defaultValue, Status.WARNING);
		}
		return retrievedData;
	}
	
	
	
	/**
	 ************************************************************* 
	 * Function to Retrieve data from dataTable.
	 * 
	 * @param sheetName
	 * 		  Sheet from which data is to be retrieved
	 *            
	 * @param columnName
	 * 		  Column from which data is to be retrieved
	 * 
	 * @param objName
	 * 		  Description of the retrieved data which is to be updated in Test Log
	 * 
	 * @param displayError
	 * 		  Displays error message if true is passed and data is blank in sheet
	 * 		  No error message is displayed if  false is passed
	 *            
	 * @return String
	 * 			
	 ************************************************************* 
	 */
	public String validateData(String sheetName, String columnName, String objName)
	{
		String retrievedData = null;
		try{
				retrievedData =  dataTable.getData(sheetName, columnName);
				if(retrievedData.isEmpty())
				{	frameworkParameters.setStopExecution(true);
					throw new FrameworkException("Retrieve data from "+columnName, objName + " is blank in "+ columnName +" column in "+ sheetName +" sheet.");
				}
			}
			catch(Exception e)
			{
				frameworkParameters.setStopExecution(true);
				throw new FrameworkException("Retrieve data from "+columnName, objName + " is blank in "+ columnName +" column in "+ sheetName +" sheet.");
			}
		return retrievedData;
	}
	
	
	/**
	 ************************************************************* 
	 * Function to clear existing text in a field and enter required data.
	 * 
	 * @param ElementName
	 *            The {@link String} object that contains the page element
	 *            identification variable in OR.
	 * @param Text
	 *            The {@link String} object that contains the string to be
	 *            entered in the text field.
	 * @return void
	 ************************************************************* 
	 */
	
	public boolean clearAndEnterText(WebElement elemToUpdate, String strValueToUpdate, String strObjName)
	{
		
		if(!strValueToUpdate.trim().equalsIgnoreCase("IGNORE")){
			try{
				if(elemToUpdate.isDisplayed() && elemToUpdate.isEnabled()){
					elemToUpdate.clear();
					updateAnyElement(elemToUpdate, strValueToUpdate, strObjName);
					return true;
				} else{
					report.updateTestLog("Verify if the Element(" + strObjName + ") is present and update", strObjName
							+ " is not enabled", Status.FAIL);
				}
			} catch(org.openqa.selenium.NoSuchElementException nsee){
				report.updateTestLog("UPDATE ANY ELEMENT : " + strObjName, strObjName
						+ " object does not exist in page", Status.FAIL);
			} catch(Exception e){
				report.updateTestLog("UPDATE ANY ELEMENT", "Error in finding object - " + strObjName
						+ ". Error Description - " + e.toString(), Status.FAIL);
			}
			return false;
		} else
			return true;
	}
	
	/**
	 ************************************************************* 
	 * Function to click a given element
	 * 
	 * @param elemToClick
	 *            The {@link strObjProperty} element to be updated
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean updateAnyElement(WebElement elemToUpdate, String strValueToUpdate, String strObjName){
		
		if(!strValueToUpdate.trim().equalsIgnoreCase("IGNORE")){
			try{
				if(elemToUpdate.isDisplayed() && elemToUpdate.isEnabled()){
					// mouseOverWebElement(elemToUpdate); This is NOT supported
					// in Safari browser. Not required for other browsers too. -
					// Rajesh 5/27/2013
					Thread.sleep(1000);
					try{
						elemToUpdate.click();
					} catch(Exception e){
					}
					elemToUpdate.clear();
					elemToUpdate.sendKeys(strValueToUpdate);
					Thread.sleep(2000);
					report.updateTestLog("Verify if the Element(" + strObjName + ") is present and updated", strObjName
							+ " is present and updated with value : " + strValueToUpdate, Status.PASS);
					return true;
				} else{
					report.updateTestLog("Verify if the Element(" + strObjName + ") is present and updated", strObjName
							+ " is not enabled", Status.FAIL);
				}
			} catch(org.openqa.selenium.NoSuchElementException nsee){
				report.updateTestLog("UPDATE ANY ELEMENT : " + strObjName, strObjName
						+ " object does not exist in page", Status.FAIL);
			} catch(Exception e){
				report.updateTestLog("UPDATE ANY ELEMENT", "Error in finding object - " + strObjName
						+ ". Error Description - " + e.toString(), Status.FAIL);
			}
			return false;
		} else
			return true;
	}
	
	/**
 	 ************************************************************* 
 	 * Function to click a link which opens in same tab and verify
 	 * if correct URL is opened. User is navigated back to the original URL
 	 * after verification is complete
 	 * 
 	 * @author 387478
 	 * @param  element
 	 *            The {@link element} is the WelElement object
 	 *            which is to be clicked
 	 * @param  expectedUrl
 	 *            The {@link expectedUrl} is the expected URL on which the 
 	 *            user should be navigated on clicking the link
 	 * @param  strObjName
 	 *            The {@link strObjName} is used for identifying the object used
 	 *            for reporting purposes.
 	 * @return A boolean value indicating if the user is navigated to correct URL
 	 * @throws InterruptedException 
 	 ************************************************************* 
 	 */
 	public boolean clickAndVerifyUrl(WebElement element, String expectedUrl, String strObjName) throws InterruptedException{
 		if(!clickIfElementPresent(element, strObjName)){
 			report.updateTestLog(strObjName, strObjName + " : Verification is Failure. Expected Url: " + expectedUrl
                     + " Actual : " + driver.getCurrentUrl(), Status.FAIL);
 			return false;
 		}
 			
 		if(driver.getCurrentUrl().equals(expectedUrl)){
 			report.updateTestLog(strObjName, "'" + strObjName + "' : "    + " Verification is Success", Status.PASS);
 			navigateBackFromCurrentPage();
 			
 		}else{
 			report.updateTestLog(strObjName, strObjName + " : Verification is Failure. Expected Url: " + expectedUrl
                     + " Actual : " + driver.getCurrentUrl(), Status.FAIL);
 			navigateBackFromCurrentPage();
 			return false;
 		}	
 		
 		return true;
 	}
 	
 	
	/**
	 ************************************************************* 
	 * Method to check/uncheck a checkbox based on the given option
	 * 
	 * @param elementToSelect
	 *            The {@link elementToSelect} element to be verified
	 * @param valueToSelect
	 *            The {@link strElemStateToVerify} describes the state to be set
	 *            which can be one of: Y/N
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return None
	 ************************************************************* 
	 */
	public void checkAnyElement(WebElement elementToSelect, String strValueToSelect, String strObjName){
	
		String strStateToReport = " ";
		boolean blnValueToSelect = true;
		if(!(strValueToSelect.trim().equalsIgnoreCase("IGNORE"))){
			if(strValueToSelect.trim().equalsIgnoreCase("N")){
				blnValueToSelect = false;
				strStateToReport = " not ";
			}
			if(elementToSelect.isEnabled()){
				if(!blnValueToSelect && !elementToSelect.isSelected()){
					if(blnValueToSelect && elementToSelect.isSelected()){
						elementToSelect.click();
					}
				}
				elementToSelect.click();
				report.updateTestLog("Select the element (" + strObjName + ")", strObjName + " is" + strStateToReport
						+ "checked as required", Status.DONE);

			} else{
				report.updateTestLog("Verify if the Element(" + strObjName + ") is present and selected", strObjName
						+ " object is not enabled", Status.FAIL);
			}
		}
	}
	
	
	
	

	
	
	/**
     ************************************************************* 
     * Function to verify same text is present in both the input strings
     * 
     * @param String expectedString
     * 
     * @param String actualString
     * @param objName
     *        - Name of the object to be updated in the Test log
     * 
     * @author 387478    *   
     *
     ************************************************************* 
      */

     public boolean compareText(String expectedString, String actualString, String objName)
     {
           if(expectedString.equals(actualString)){
                             report.updateTestLog(objName, "'" + objName + "' : " + " Verification is Success", Status.PASS);
                             return true;
                       } else{
                             report.updateTestLog(objName, objName + " : Verification is Failure. Expected : " + expectedString
                                         + " Actual : " + actualString, Status.FAIL);
                             return false;
                       }           
     }
     
     /**
 	 * function to remove special characters in a string
 	 * 
 	 * @param strring
 	 *            to remove the characteres
 	 * @return string
 	 */

 	public String RemoveSpecialcharacters(String str){
 		
 		String st = "";
 		for(int i = 0; i < str.length(); i++){
 			int ch = str.charAt(i);

 			if((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57) || ch==46 || ch=='$')
 			{
 				st = st + (char) ch;
 			}

 		}

 		return st;
 	}
     
     /**
  	 ************************************************************* 
  	 * Function to compare (equals or contains) two strings after removing
  	 * special characters from them
  	 * 
  	 * @param str1
  	 *            ,str2 The two strings that are to be compared
  	 * @param strContainsOrEquals
  	 * 
  	 * @return Boolean
  	 ************************************************************* 
  	 */
  	public Boolean compareRemovingSpecialCharacters(String str1, String strContainsOrEquals, String str2){
  		
  		str1 = RemoveSpecialcharacters(str1.trim().toLowerCase());
  		str2 = RemoveSpecialcharacters(str2.trim().toLowerCase());
  		if(strContainsOrEquals.equalsIgnoreCase("equals")){
  			if(str1.equals(str2))
  				return true;
  			else
  				return false;
  		} else{
  			if(str1.contains(str2))
  				return true;
  			else
  				return false;
  		}
  	}
  	
  	/**
	 * Function to remove special characters in a Amount
	 * It keeps '.' and '-' sign
	 * 
	 * @param strring
	 *            to remove the characteres
	 * @return string
	 */

public String RemoveSpecialcharactersFromAmount(String str){
		
		String st = "";
		for(int i = 0; i < str.length(); i++){
			int ch = str.charAt(i);

			if((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57) || ch==46 || ch==45){
				st = st + (char) ch;
			}

		}

		return st;
	}

/**
 * Function to format a amount
 * Adds Grouping identifier ','
 * Returns a string having two characters after a dot
 * 
 * @param amount
 *            Amount which is to be formatted
 * @return string
 * 
 * @author 387478
 */
public String formatAmount(String amount){
	
	NumberFormat myFormat = NumberFormat.getInstance();
      myFormat.setGroupingUsed(true);	

		amount = RemoveSpecialcharactersFromAmount(amount);
		Double floatamount = Double.parseDouble(amount);
		floatamount = Math.abs(floatamount); 
		amount = myFormat.format(floatamount);
		if(!amount.contains(".")){
			amount = amount + ".00";
			return amount;	
		}
		
		
		if(amount.split("\\.")[1].length()==1)
			return amount +"0";
		
		return amount;
			
}

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {@link WebElement} object.
	 ************************************************************* 
	 */
	public WebElement getElementByProperty(String strObjectProperty, String strFindElementType,boolean displayError){
		
		try{
			if(strFindElementType.equalsIgnoreCase("CSS"))
				return driver.findElement(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				return driver.findElement(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				return driver.findElement(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				return driver.findElement(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				return driver.findElement(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				return driver.findElement(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				return driver.findElement(By.className(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("PARTIALLINKTEXT"))
				return driver.findElement(By.partialLinkText(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			if(displayError){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
			}else
				return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}
	}
	
	/**
     ************************************************************* 
     * Function to verify same text is present in both the input strings
     * 
     * @param String expectedString
     * 
     * @param String actualString
     * @param objName
     *        - Name of the object to be updated in the Test log
     * 
     * @author 387478    *   
     *
     ************************************************************* 
      */

     public boolean verifyText(String expectedString, String actualString, String objName)
     {
           if(expectedString.equals(actualString)){
                             report.updateTestLog(objName, "'" + objName + "' : " + " Verification is Success", Status.PASS);
                             return true;
                       } else{
                             report.updateTestLog(objName, objName + " : Verification is Failure. Expected : " + expectedString
                                         + " Actual : " + actualString, Status.FAIL);
                             return false;
                       }           
     }
     
     /**
      ************************************************************* 
      * Function to find the Date Difference between two Dates
      * 
      * @param String expectedString
      * 
      * @param String actualString
      * @param objName
      *        - Name of the object to be updated in the Test log
      * 
      * @author 387478    *   
      *
      ************************************************************* 
       */

      public long findDateDifference(String dateStart, String dateStop)
      {
	    	SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
	  		Date d1 = null;
	  		Date d2 = null;
	  		
	  		long diff =0;
	  		long diffDays=0;
	   		try 
	  			{
		  			d1 = format.parse(dateStart);
		  			d2 = format.parse(dateStop);
		   			diff = d2.getTime() - d1.getTime();
		  			diffDays = diff / (24 * 60 * 60 * 1000);
		    		return diffDays;
	  			} 
	  			catch (Exception e) 
	  			{
	  				e.printStackTrace();
	  			}
	  		return diffDays;
      }
     
	/**
	 ************************************************************* 
	 * Function to verify whether a given Element is present within the page and
	 * click
	 * 
	 * @param strObjProperty
	 *            The {@link strObjProperty} defines the property value used for
	 *            identifying the object
	 * @param strObjPropertyType
	 *            The {@link strObjPropertyType} describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean clickIfElementPresent(WebElement element, String strObjName){
		
		try{
			if(isElementPresentVerification(element, strObjName))
			{
				element.click();
				Thread.sleep(8000);
				report.updateTestLog("Verify if the Element(" + strObjName + ") is present", "'"+strObjName +"'"
						+ " is present and clicked", Status.PASS);
				return true;
			} else{
				report.updateTestLog("Verify if the Element(" + strObjName + ") is present", "'"+strObjName +"'"
						+ " is not present", Status.FAIL);
				return false;
			}
		} catch(Exception e){
			report.updateTestLog("CLICK IF ELEMENT PRESENT", "Error in method - Error Description - " + e.toString(),
					Status.FAIL);
			return false;
		}
	}


	/**
	 ************************************************************* 
	 * Function to return attibute value
	 * 
	 * @param element
	 *            Element to get value
	 * @param attributeName
	 *            AttributeName to get value
	 * 
	 * @return String value of attribute Name.
	 ************************************************************* 
	 */
	public String getAttributeValue(WebElement element, String attributeName){
		
		try
		{
			return element.getAttribute(attributeName).toString();
		} catch(Exception e){
			report.updateTestLog("GetAttributeValue", "Error in method - Error Description - " + e.toString(),
					Status.FAIL);
			return null;
		}
	}

	

	/**
	 * Method to execute PDB sql queries
	 * 
	 * @param strQuery
	 *            - SQL query to be executed
	 * @return record set for executed query
	 * @throws SQLException
	 */
	public ResultSet exeuctePDBQuery(String strQuery) throws SQLException{
		
		Connection connection = null;
		Statement st;
		ResultSet rs;
		String strServer, strDatabase;
		strServer = properties.getProperty("PDBServer");
		if(strQuery.toLowerCase().contains("web_")){
			strDatabase = properties.getProperty("WebDatabase");
		} else{
			strDatabase = properties.getProperty("PDBDatabase");
		}
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			connection = DriverManager.getConnection("jdbc:odbc:Driver={SQL Server};SERVER=" + strServer
					+ ";Trusted_connection=yes;DATABASE=" + strDatabase);
			st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(strQuery);
			return rs;
		} catch(Exception e){
			if(connection != null){
				connection.close();
			}
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method to execute WCBE query
	 * 
	 * @param strQuery
	 *            - Query to be triggered
	 * @return - record set for triggered WCBE SQL query
	 * @throws SQLException
	 */
	public ResultSet exeucteWCBEQuery(String strQuery) throws SQLException{

		Connection connection = null;
		Statement st;
		ResultSet rs;
		String strServer, strDatabase, strPort, strUsername, strPassword;
		strServer = properties.getProperty("WCBEServer");
		strDatabase = properties.getProperty("WCBEDBName");
		strPort = properties.getProperty("WCBEPort");
		strUsername = properties.getProperty("WCBEUsername");
		strPassword = properties.getProperty("WCBEPassword");
		try{
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			String url = "jdbc:db2://" + strServer + ":" + strPort + "/" + strDatabase;
			connection = DriverManager.getConnection(url, strUsername, strPassword);
			st = connection.createStatement();
			strQuery = strQuery.replace(";", "");
			if(strQuery.toLowerCase().startsWith("update") || strQuery.toLowerCase().startsWith("delete")
					|| strQuery.toLowerCase().startsWith("insert")){
				st.executeUpdate(strQuery);
				return null;
			} else{
				rs = st.executeQuery(strQuery);
				return rs;
			}

		} catch(Exception e){
			if(!strQuery.startsWith("UPDATE userreg")){
				if(connection != null){
					connection.close();
				}
				report.updateTestLog("Execute WCBE query", "Query unsuccessful : " + e.getMessage(), Status.FAIL);
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * @description Method to result customer name with time stamp
	 * @param strCustomUserName
	 *            - Customer name
	 * @return String
	 * @modified_date Dec 4, 2013
	 */
	public String generateCustomUserName(String strCustomUserName){
	
		String strTimeStamp = getCurrentTimeStamp();
		return strCustomUserName + strTimeStamp;
	}

	/**
	 * @description Method to generate new user name
	 * @return String
	 * @modified_date Dec 4, 2013
	 */
	public String generateNewUserName(){
	
		String strTimeStamp = getCurrentTimeStamp();
		return "TestUser" + strTimeStamp;
	}

	/**
	 * @description Method to generate new email address
	 * @return String
	 * @modified_date Dec 4, 2013
	 */
	public String generateUpdateEmailAddr(){
	
		String strTimeStamp = getCurrentTimeStamp();
		return "newuser" + strTimeStamp + "@gmail.com";
	}

	/**
	 * @description Method to generate new user name
	 * @return String
	 * @modified_date Dec 4, 2013
	 */
	public String generateUpdateUserName(){
		
		String strTimeStamp = getCurrentTimeStamp();
		return "UpdateUser" + strTimeStamp;
	}

	/**
	 ************************************************************* 
	 * Function to select a particular Value from any List box.
	 * 
	 * @param ListBoxObject
	 *            The {@link WebElement} object that has reference to the List
	 *            Box.
	 * @param strSelectOption
	 *            The {@link String} object that has the item to be selected.
	 * @return void
	 ************************************************************* 
	 */
	public void genericListBoxOptionSelector(WebElement ListBoxObject, String strSelectOption) throws Exception{
		
		try{
			new Select(ListBoxObject).selectByVisibleText(strSelectOption);
			report.updateTestLog("GENERIC LIST BOX SELECTOR", "The option " + strSelectOption + " is selected.",
					Status.DONE);
		} catch(Exception e){
			report.updateTestLog("GENERIC LIST BOX SELECTOR", "Error in method - Error Description - " + e.toString(),
					Status.FAIL);
		}
	}

	/**
	 * @description Method to get a time-stamp in MMMdd_mm_ss format
	 * @return String
	 * @modified_date Dec 4, 2013
	 */
	public String getCurrentTimeStamp(){
		
		String strTimestamp;
		Date currentDate = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMdd_mm_ss");
		strTimestamp = dateFormatter.format(currentDate);
		return strTimestamp;
	}

	

	/**
	 * @description Method to get eastern time
	 * @return String
	 * @modified_date Dec 4, 2013
	 */
	public String getEasternTimeStamp(){
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("US/Eastern"));
		SimpleDateFormat df = new SimpleDateFormat("E h:mm a '-' zzzz");
		df.setTimeZone(cal.getTimeZone());
		String strEasternTimestamp = df.format(cal.getTime());
		System.out.println(strEasternTimestamp);
		return strEasternTimestamp;
	}

	/**
	 ************************************************************* 
	 * Function to get element attribute
	 * 
	 * @param Element
	 *            The {@link String} object that contains the page element
	 *            identification variable in OR.
	 * @param ElementName
	 *            The {@link String} Attribute name of the element which is to
	 *            be fetched
	 * @return
	 ************************************************************* 
	 */
	public String getElementAttribute(String strObjectProperty, String strFindElementType, String attributeToGet,
			String strObjName){
		
		String attributVal = null;
		try{
			if(isElementPresentVerification(strObjectProperty, strFindElementType, strObjName)){
				attributVal = getElementByProperty(strObjectProperty, strFindElementType).getAttribute(attributeToGet);
			}
		} catch(Exception e){
			report.updateTestLog("", "Error in method - Error Description - " + e.toString(), Status.FAIL);
		}
		return attributVal;
	}

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {@link WebElement} object.
	 ************************************************************* 
	 */
	public WebElement getElementByProperty(String strObjectProperty, String strFindElementType){
		
		try{
			if(strFindElementType.equalsIgnoreCase("CSS"))
				return driver.findElement(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				return driver.findElement(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				return driver.findElement(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				return driver.findElement(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				return driver.findElement(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				return driver.findElement(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				return driver.findElement(By.className(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("PARTIALLINKTEXT"))
				return driver.findElement(By.partialLinkText(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}
	}

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {@link WebElement} object.
	 ************************************************************* 
	 */
	public List<WebElement> getElementsByProperty(String strObjectProperty, String strFindElementType){
		
		try{
			// GlobalVariables.strImplicitWait="1";
			if(strFindElementType.equalsIgnoreCase("CSS"))
				return driver.findElements(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				return driver.findElements(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				return driver.findElements(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				return driver.findElements(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				return driver.findElements(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				return driver.findElements(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				return driver.findElements(By.className(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}
	}

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param webElement
	 *            WebElement for which child elements to be found
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {@link WebElement} object.
	 ************************************************************* 
	 */
	public List<WebElement> getElementsByProperty(WebElement webElement, String strObjectProperty,
			String strFindElementType){
		
		try{
			// GlobalVariables.strImplicitWait="1";
			if(strFindElementType.equalsIgnoreCase("CSS"))
				return webElement.findElements(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				return webElement.findElements(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				return webElement.findElements(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				return webElement.findElements(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				return webElement.findElements(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				return webElement.findElements(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				return webElement.findElements(By.className(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}
	}

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param webElement
	 *            WebElement for which child elements to be found
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {WebElement} object.
	 ************************************************************* 
	 */
	public WebElement getElementInsideElement(WebElement webElement, String strObjectProperty, String strFindElementType){
		
		try{
			// GlobalVariables.strImplicitWait="1";
			if(strFindElementType.equalsIgnoreCase("CSS"))
				return webElement.findElement(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				return webElement.findElement(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				return webElement.findElement(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				return webElement.findElement(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				return webElement.findElement(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				return webElement.findElement(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				return webElement.findElement(By.className(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}
	}

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {@link WebElement} object.
	 ************************************************************* 
	 */
	public List<WebElement> getElementsInsideElement(WebElement elemUsed, String strObjectProperty,
			String strObjectPropertyType){
		
		try{
			// GlobalVariables.strImplicitWait="1";
			if(strObjectPropertyType.equalsIgnoreCase("CSS"))
				return elemUsed.findElements(By.cssSelector(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("XPATH"))
				return elemUsed.findElements(By.xpath(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("ID"))
				return elemUsed.findElements(By.id(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("NAME"))
				return elemUsed.findElements(By.name(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("LINKTEXT"))
				return elemUsed.findElements(By.linkText(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("TAG"))
				return elemUsed.findElements(By.tagName(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("CLASS"))
				return elemUsed.findElements(By.className(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Verify if " + strObjectPropertyType + " - " + strObjectProperty
					+ " is present in an element", "Element with property " + strObjectPropertyType + " - "
					+ strObjectProperty + " not found", Status.FAIL);
			return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strObjectPropertyType + " - " + strObjectProperty
					+ " is present in an element", "Element with property " + strObjectPropertyType + " - "
					+ strObjectProperty + " not found", Status.FAIL);
			return null;
		}
	}

	

	/**
	 * Method To Get The Window Size
	 * 
	 * @author Manoj Venkat
	 * @param Dimension
	 * @return strSize {@link dimSize} Size Of The Window
	 */
	public Dimension getWindowSize(){
		
		Dimension dimSize = driver.manage().window().getSize();
		return dimSize;
	}

	

	

	/**
	 ************************************************************* 
	 * Method to verify an element which is a image ,href for which we get the
	 * String value using attribute.
	 * 
	 * @return A boolean value indicating if the searched Element is found.
	 *         author Lavannya
	 ************************************************************* 
	 */
	public WebElement isElementPresentContainsAttributeText(String strObjectProperty, String strFindElementType,
			String StrObjName, String textToVerify, String attribute) throws IOException{
		
		try{
			if(isElementPresentVerification(strObjectProperty, strFindElementType, StrObjName)){
				getElementByProperty(strObjectProperty, strFindElementType).getAttribute(attribute).contains(
						textToVerify);
				report.updateTestLog("The Element(" + StrObjName + ") is present and Contains the text", textToVerify,
						Status.PASS);
				return null;
			} else{
				report.updateTestLog("The Element(" + StrObjName + ") is not present and does not contain the text",
						textToVerify, Status.FAIL);
				return null;
			}
		} catch(Exception e){
			report.updateTestLog("IS ELEMENT PRESENTCONTAINS TEXT",
					"Error in method - Error Description - " + e.toString(), Status.FAIL);
			return null;
		}
	}

	public WebElement isElementPresentContainsText(String strObjectProperty, String strFindElementType,
			String StrObjName, String textToVerify) throws IOException{
		
		try{
			if(isElementPresentVerification(strObjectProperty, strFindElementType, StrObjName)){
				getElementByProperty(strObjectProperty, strFindElementType).getText().contains(textToVerify);
				report.updateTestLog("The Element(" + StrObjName + ") is present and Contains the text", textToVerify,
						Status.PASS);
				return null;
			} else{
				report.updateTestLog("The Element(" + StrObjName + ") is not present and does not contain the text",
						textToVerify, Status.FAIL);
				return null;
			}
		} catch(Exception e){
			report.updateTestLog("IS ELEMENT PRESENTCONTAINS TEXT",
					"Error in method - Error Description - " + e.toString(), Status.FAIL);
			return null;
		}
	}

	/**
	 ************************************************************* 
	 * Function to verify if an element is present in the application, not using
	 * OR.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean isElementPresentVerification(String strObjectProperty, String strFindElementType, String strObjName)
			throws IOException{
		
		try{
			if(getElementByProperty(strObjectProperty, strFindElementType) != null){
				report.updateTestLog((strObjName + " element is present").toUpperCase(), strObjName
						+ " is verified successfully", Status.PASS);
				return true;
			} else
				return false;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Error in identifying element with string " + strObjectProperty, nsee.toString(),
					Status.FAIL);
			return false;
		} catch(Exception e){
			report.updateTestLog("IS ELEMENT PRESENT VERIFICATION", "Error in identifying object (" + strObjName
					+ ") -" + e.toString(), Status.FAIL);
			return false;
		}
	}
	
	

	/**
	 * @description Method to verify if the text in the element has Font-Weight
	 *              Bold returns
	 * @param elmt
	 *            - element to be verified
	 * @return boolean - true on pass and false on failure
	 * @modified_date Dec 9, 2013
	 */
	public boolean isFontWeightBold(WebElement elmt){

		
		String fontWt = elmt.getCssValue("font-weight");
		Boolean blMatch;
		try{
			int fontVal = Integer.parseInt(fontWt);
			if(fontVal >= 700){
				blMatch = true;
			} else{
				blMatch = false;
			}

		} catch(Exception e){
			if(fontWt.equalsIgnoreCase("bold")){
				blMatch = true;
			} else{
				blMatch = false;
			}
		}

		return blMatch;

	}

	/**
	 ************************************************************* 
	 * Function to verify if an Text is present in the application.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification variable in OR.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean isTextPresent(String text) throws IOException{
		
		/*
		String x = text;
		Asserts asserts = new Asserts();
		try{
			if(asserts.isTextPresent(driver, text)){
				report.updateTestLog("Verification of ", x + "Is Present", Status.PASS);
		 */
		
				return true;
				/*
			} else
				return false;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Error in identifying element with property " + text, nsee.toString(), Status.FAIL);
			return false;
		} catch(Exception e){
			report.updateTestLog("IS ELEMENT PRESENT VERIFICATION",
					"Error in method - Error Description - " + e.toString(), Status.FAIL);
			return false;
		}
		*/
	}

	/**
	 * @description Method to perform mouse hover on given element
	 * @param element
	 *            - element on which mouse hover action needs to be performed
	 * @modified_date Dec 9, 2013
	 */
	public void mouseOver(WebElement element){
		
			try{
				Robot robot = new Robot();
				robot.mouseMove(0, 0);
				Actions builder = new Actions(driver);
				builder.moveToElement(element).build().perform();
			} catch(Exception e){
				report.updateTestLog(("Mouse Over Event doesnt occur").toUpperCase(),
						"Unable to perform mouse hover on element", Status.FAIL);
			}
		
	}

	/**
	 * @description Method to perform mouse hover action on internet explorer
	 * @param element
	 *            - Element on which mouse hover action needs to be performed
	 * @return Nothing
	 * @modified_date Dec 9, 2013
	 */
	public void mouseOverIE(WebElement element){
		
		String code = " function sleep(milliseconds) {"
				+ " var start = new Date().getTime();"
				+ " while ((new Date().getTime() - start) < milliseconds){"
				+
				// Do nothing
				"} }" + "if(document.createEvent) {" + "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initEvent('mouseover', true, false); " + "sleep(50000);"
				+ "arguments[0].dispatchEvent(evObj);" + "arguments[0].click();" + "} "
				+ "else if(document.createEventObject) " + "{ arguments[0].fireEvent('onmouseover'); }";
		((JavascriptExecutor) driver).executeScript(code, element);

	}

	

	/**
	 * @description Method to perform back browser action
	 * @return Nothing
	 * @throws InterruptedException 
	 * @modified_date Dec 9, 2013
	 */
	public void navigateBackFromCurrentPage() throws InterruptedException{
		
		driver.navigate().back();
		Thread.sleep(8000);
		
	}

	/**
	 * @description Method to navigate to specified URL
	 * @param strURL
	 *            - navigation URL
	 * @return Nothing
	 * @modified_date Dec 9, 2013
	 */
	public void navigateToUrl(String strURL){
		
		driver.get(strURL);
		
	}

	/**
	 * @description Method to open a new tab in browser with specified URL
	 * @param url
	 * @modified_date Dec 9, 2013
	 */
	public void openTab(String url){
		
		WebElement a = (WebElement) ((JavascriptExecutor) driver)
				.executeScript(
						"var d=document,a=d.createElement('a');a.target='_blank';a.href=arguments[0];a.innerHTML='.';d.body.appendChild(a);return a",
						url);
		a.click();
	}

	/**
	 ************************************************************* 
	 * Function to get the current Page Load state
	 * 
	 * @param none
	 * @author Ganesh
	 * @return Boolean
	 ************************************************************* 
	 */
	public boolean pageLoadstate(){
		return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
	}

	/**
	 * @description Method to refresh page
	 * @return Nothing
	 * @modified_date Dec 9, 2013
	 */
	public void pageRefresh(){
		
		driver.navigate().refresh();
		//handleFeedback();
	}

	/**
	 ************************************************************* 
	 * Function to read properties file
	 * 
	 * @param propertiesFileName
	 *            The {@link String} object that contains the OR name.
	 * @param propertyName
	 *            The {@link String} object that contains the property name to
	 *            read.
	 * @return The {@link String} object that contains the property value.
	 ************************************************************* 
	 */
	public String readPropertiesFile(String propertiesFileName, String propertyName){
		
		Locale locale = new Locale("en", "US");
		ResourceBundle bundle = ResourceBundle.getBundle(propertiesFileName, locale);
		String propertyValue = bundle.getString(propertyName);
		return propertyValue;

	}

	

	
	
	/**
	 * This method will replace the given query if it has fields <VALUE> with
	 * the given data
	 * 
	 * @param strQuery
	 *            The query which should be modified before execution. Ex:
	 *            "Select price from price where SKU='<VALUE>' and zone_id in
	 *            (select zone_id from zip where zip_prefix='<VALUE>' and
	 *            zip_suffix='<VALUE>')"
	 * @param strData
	 *            Data to be replaced in place of <VALUE> specified in the given
	 *            query PRECEEDED by '&'. Ex: &14336&m5g&212
	 * @return
	 */
	public String replaceQueryWithValue(String strDTValue, String strScenario){
		if(strDTValue.toUpperCase().contains("AS400") || strDTValue.toUpperCase().contains("PDB")
				|| strDTValue.toUpperCase().contains("WCBE")){
			String[] arrData = strScenario.split("&");
			for(int i = 1; i < arrData.length; i++){
				strDTValue = strDTValue.substring(0, strDTValue.indexOf("<VALUE>") + 7).replace("<VALUE>", arrData[i])
						+ strDTValue.substring(strDTValue.indexOf("<VALUE>") + 7);
			}
			return strDTValue;
		} else
			return strDTValue;
	}

	/**
	 ************************************************************* 
	 * Method to select an option directly from combo box/list box
	 * 
	 * @param elementToSelect
	 *            The {@link elementToSelect} element to be verified
	 * @param valueToSelect
	 *            The {@link strElemStateToVerify} describes the state to be
	 *            verified which can be either one of ENABLED/SELECTED/VALUE
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if selecting an option is done.
	 ************************************************************* 
	 */
	public void selectAnyElement(String strObjProperty, String strObjPropertyType, String strValueToSelect,
			String strObjName){
		
		if(!(strValueToSelect.trim().equalsIgnoreCase("IGNORE"))){
			try{
				WebElement elementToSelect = getElementByProperty(strObjProperty, strObjPropertyType);
				selectAnyElement(elementToSelect, strValueToSelect, strObjName);
			} catch(org.openqa.selenium.NoSuchElementException nsee){
				report.updateTestLog("Select Any Element : " + strObjName, strObjName
						+ " object does not exist in page", Status.FAIL);
			} catch(Exception e){
				report.updateTestLog("SELECT ANY ELEMENT", "Error in finding element - " + strObjProperty + ", by: "
						+ strObjPropertyType, Status.FAIL);
			}
		}
	}

	/***
	 ************************************************************* 
	 * Method to select an option directly from combo box/list box
	 * 
	 * @param elementToSelect
	 *            The {@link elementToSelect} element to be verified
	 * @param valueToSelect
	 *            The {@link strElemStateToVerify} describes the state to be
	 *            verified which can be either one of ENABLED/SELECTED/VALUE
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if selecting an option is done.
	 ************************************************************* 
	 */
	public void selectAnyElement(WebElement elementToSelect, String strValueToSelect, String strObjName){
		
		if(!(strValueToSelect.trim().equalsIgnoreCase("IGNORE"))){
			if(elementToSelect.isEnabled()){
				elementToSelect.click();

				Select comSelElement = new Select(elementToSelect);
				try{
					Thread.sleep(3000);
				} catch(InterruptedException e){
				}
				comSelElement.selectByVisibleText(strValueToSelect);

				report.updateTestLog("Verify if the Element(" + strObjName + ") is present and selected", strObjName
						+ " value : '" + strValueToSelect + "' is selected", Status.DONE);
			} else{
				report.updateTestLog("Verify if the Element(" + strObjName + ") is present and selected", strObjName
						+ " object is not enabled", Status.FAIL);
			}
		}
	}

	/***
	 ************************************************************* 
	 * Method to select an option directly from combo box/list box
	 * 
	 * @param elementToSelect
	 *            The {@link elementToSelect} element to be verified
	 * @param indexToSelect
	 *            Index to Select
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if selecting an option is done.
	 ************************************************************* 
	 */
	public void selectAnyElement(WebElement elementToSelect, int indexToSelect, String strObjName){
		
		if(indexToSelect > -1){
			if(elementToSelect.isEnabled()){
				elementToSelect.click();

				Select comSelElement = new Select(elementToSelect);
				try{
					Thread.sleep(3000);
				} catch(InterruptedException e){
				}
				comSelElement.selectByIndex(indexToSelect);

				report.updateTestLog("Verify if the Element(" + strObjName + ") is present and selected", strObjName
						+ " value : '" + indexToSelect + "' is selected", Status.DONE);
			} else{
				report.updateTestLog("Verify if the Element(" + strObjName + ") is present and selected", strObjName
						+ " object is not enabled", Status.FAIL);
			}
		}
	}

	/**
	 * Method To Get the window handle result
	 * 
	 * @param driver
	 *            driver of active browser
	 * @param strWindowTitle
	 *            tab title to be switched to
	 * @return boolean
	 */
	public boolean switchToDifferentWindow(WebDriver driver, String strWindowTitle){
		
		WebDriver popup = null;
		driver.getWindowHandle();
		java.util.Iterator<String> obj = driver.getWindowHandles().iterator();
		// Window handle iterator object initiated
		while(obj.hasNext()){
			String windowHandle = obj.next();
			popup = driver.switchTo().window(windowHandle);
			if(popup.getTitle().contains(strWindowTitle)){
				report.updateTestLog("Switch to window: " + strWindowTitle, "Successfully switched to window "
						+ strWindowTitle, Status.DONE);
				return true;
			}
		}
		report.updateTestLog("Switch to window: " + strWindowTitle, "Window " + strWindowTitle + " not found ",
				Status.FAIL);
		return false;
	}

	/**
	 * @description Method to switch to next window
	 * @throws NoSuchWindowException
	 * @return nothing
	 * @modified_date Dec 12, 2013
	 */
	public void switchWindow() throws NoSuchWindowException{
		
		Set<String> handles = driver.getWindowHandles();
		String current = driver.getWindowHandle();
		handles.remove(current);
		String newTab = handles.iterator().next();
		driver.switchTo().window(newTab);
	}

	/**
	 ************************************************************* 
	 * Function to verify whether a given Element is present within the page and
	 * update the value
	 * 
	 * @param strObjProperty
	 *            The {@link strObjProperty} defines the property value used for
	 *            identifying the object
	 * @param strObjPropertyType
	 *            The {@link strObjPropertyType} describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean updateAnyElement(String strObjProperty, String strObjPropertyType, String strValueToUpdate,
			String strObjName){
		
		if(strValueToUpdate.trim().equalsIgnoreCase("IGNORE")){
			try{
				if(isElementPresentVerification(strObjProperty, strObjPropertyType, strObjName)){
					WebElement elemToClick = getElementByProperty(strObjProperty, strObjPropertyType);
					enterValueInElement(elemToClick, strValueToUpdate, strObjName);
				} else{
					report.updateTestLog("Verify if the Element(" + strObjName + ") is present and updated", strObjName
							+ " is not present", Status.FAIL);
				}
			} catch(org.openqa.selenium.NoSuchElementException nsee){
				report.updateTestLog("UPDATE Any Element : " + strObjName, strObjName
						+ " object does not exist in page", Status.FAIL);
			} catch(Exception e){
				report.updateTestLog("UPDATE ANY ELEMENT", "Error in method - Error Description - " + e.toString(),
						Status.FAIL);
			}
			return false;
		} else
			return true;
	}

	/**
	 ************************************************************* 
	 * Function to click a given element
	 * 
	 * @param elemToClick
	 *            The {@link strObjProperty} element to be updated
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean enterValueInElement(WebElement elemToEnter, String strValueToEnter, String strObjName){
		
		if(!strValueToEnter.trim().equalsIgnoreCase("IGNORE")){
			try{
				if(elemToEnter.isDisplayed() && elemToEnter.isEnabled())
				{
					Thread.sleep(1000);
					try{
						elemToEnter.click();
					} catch(Exception e){
					}
					elemToEnter.clear();
					elemToEnter.sendKeys(strValueToEnter);
					Thread.sleep(2000);
					report.updateTestLog("Verify if the Element(" + strObjName + ") is present", "'"+strObjName+"'"
							+ " is present and entered value : " + strValueToEnter, Status.PASS);
					return true;
				} else{
					report.updateTestLog("Verify if the Element(" + strObjName + ") is present and value is Entered", strObjName
							+ " is not enabled", Status.FAIL);
				}
			} catch(org.openqa.selenium.NoSuchElementException nsee){
				report.updateTestLog("ENTER VALUE IN ELEMENT : " + strObjName, strObjName
						+ " object does not exist in page", Status.FAIL);
			} catch(Exception e){
				report.updateTestLog("ENTER VALUE IN ELEMENT ", "Error in finding object - " + strObjName
						+ ". Error Description - " + e.toString(), Status.FAIL);
			}
			return false;
		} else
			return true;
	}

	public void updateElementUsingJavaScript(WebElement elmt, String strTextToUpdate, String strElemName){
		
		try{
			String str = "\"" + strTextToUpdate + "\"";
			String strCode = "arguments[0].setAttribute(\"value\"," + str + ")";
			((JavascriptExecutor) driver).executeScript(strCode, elmt);
			report.updateTestLog("Update Element " + strElemName + " using JavaScript Executor", strElemName
					+ " updated with value : " + strTextToUpdate, Status.DONE);
		} catch(Exception e){
			report.updateTestLog("Update Element " + strElemName + " using JavaScript Executor",
					"Error occurred while trying to update Element " + e.getMessage(), Status.FAIL);
		}

	}

	

	/**
	 ************************************************************* 
	 * Method to verify an element state based on given input conditions and
	 * report
	 * 
	 * @param elemToVerify
	 *            The {@link strObjProperty} element to be verified
	 * @param strElemStateToVerify
	 *            The {@link strElemStateToVerify} describes the state to be
	 *            verified which can be either one of
	 *            ENABLED/SELECTED/VALUE/SELECTED VALUE. Any value to be
	 *            verified is given as : VALUE-<Value>
	 * @param strExpValue
	 *            The {@link strExpValue} corresponds to each state
	 *            representations of {@link strElemStateToVerify} namely
	 *            Y/N/<the actual text>
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean verifyAndReportElementState(WebElement elemToVerify, String strElemStateToVerify,
			String strExpValue, String strObjName){
		
		String strExpStateToReport = " ";
		String strExpValueToCompare = "", strActValue = "", strActAttribute = "";
		boolean blnExpValue = true;
		if((!strExpValue.trim().equalsIgnoreCase("IGNORE")) && (!strElemStateToVerify.trim().contains("IGNORE"))){
			if(strExpValue.trim().equalsIgnoreCase("N")){
				strExpStateToReport = " not ";
				blnExpValue = false;
			}

			// ****************To verify the element is enabled or
			// not***********
			if(strElemStateToVerify.equalsIgnoreCase("ENABLED")){
				if((elemToVerify.isEnabled() && blnExpValue) || (!elemToVerify.isEnabled() && !blnExpValue)){
					report.updateTestLog("Verify if the Element(" + strObjName + ") is" + strExpStateToReport
							+ "enabled", strObjName + " is" + strExpStateToReport + "enabled", Status.PASS);
					return true;
				}
				// else if ((elemToVerify.isEnabled() &&
				// strExpValue.trim().equalsIgnoreCase("N")) ||
				// (!elemToVerify.isEnabled() &&
				// strExpValue.trim().equalsIgnoreCase("Y")) ){
				else{
					report.updateTestLog("Verify if the Element(" + strObjName + ") is" + strExpStateToReport
							+ "enabled", strObjName + " property do not match as expected : " + strExpStateToReport
							+ "ENABLED", Status.FAIL);
					return false;
				}
			}

			// ****************To verify the element is displayed or
			// not***********
			if(strElemStateToVerify.equalsIgnoreCase("DISPLAYED")){
				if((elemToVerify.isDisplayed() && blnExpValue) || (!elemToVerify.isDisplayed() && !blnExpValue)){
					report.updateTestLog("Verify if the Element(" + strObjName + ") is" + strExpStateToReport
							+ "displayed", strObjName + " is" + strExpStateToReport + "displayed", Status.PASS);
					return true;
				}
				// else if ((elemToVerify.isEnabled() &&
				// strExpValue.trim().equalsIgnoreCase("N")) ||
				// (!elemToVerify.isEnabled() &&
				// strExpValue.trim().equalsIgnoreCase("Y")) ){
				else{
					report.updateTestLog("Verify if the Element(" + strObjName + ") is" + strExpStateToReport
							+ "displayed", strObjName + " property do not match as expected : " + strExpStateToReport
							+ "displayed", Status.FAIL);
					return false;
				}
			}

			// ****************To verify the element is selected or
			// not***********
			else if(strElemStateToVerify.equalsIgnoreCase("SELECTED")){
				if((elemToVerify.isSelected() && blnExpValue) || (!elemToVerify.isSelected() && !blnExpValue)){
					report.updateTestLog("Verify if the Element(" + strObjName + ") is" + strExpStateToReport
							+ "selected/checked", strObjName + " is" + strExpStateToReport + "selected", Status.PASS);
					return true;
				} else{
					report.updateTestLog("Verify if the Element(" + strObjName + ") is" + strExpStateToReport
							+ "selected/checked", strObjName + " property do not match as expected : "
							+ strExpStateToReport + "SELECTED", Status.FAIL);
					return false;
				}
			}

			// ****************To verify the element has the exact
			// value***********
			else if(strElemStateToVerify.substring(0, 6).equalsIgnoreCase("VALUE-")){
				strExpValueToCompare = strElemStateToVerify.substring(6).trim();
				strActValue = elemToVerify.getText().trim();
				strActValue = RemoveSpecialcharacters(strActValue);
				strExpValueToCompare = RemoveSpecialcharacters(strExpValueToCompare);
				if(elemToVerify.getTagName().trim().equalsIgnoreCase("input")){
					strActAttribute = elemToVerify.getAttribute("value").trim();
					strActAttribute = RemoveSpecialcharacters(strActAttribute);
				} else{
					strActAttribute = "";
				}
				if((strActValue.equalsIgnoreCase(strExpValueToCompare) && blnExpValue)
						|| (!strActValue.equalsIgnoreCase(strExpValue.trim()) && !blnExpValue)
						|| (strActAttribute.trim().equalsIgnoreCase(strExpValueToCompare.trim()) && blnExpValue)
						|| (!strActAttribute.trim().equalsIgnoreCase(strExpValueToCompare.trim()) && !blnExpValue)){
					report.updateTestLog("Verify if the Element(" + strObjName + ") value is" + strExpStateToReport
							+ "displayed", strObjName + " value: '" + strExpValueToCompare + "'" + strExpStateToReport
							+ "displayed as expected", Status.PASS);
					return true;
				} else{
					report.updateTestLog("Verify if the Element(" + strObjName + ") value is" + strExpStateToReport
							+ "displayed",
							"Expected: '" + strExpValueToCompare + "' Actual: '" + elemToVerify.getText() + "'",
							Status.FAIL);
					return false;
				}
			}

			// ****************To verify the element contains the expected
			// value***********
			else if(strElemStateToVerify.substring(0, 9).equalsIgnoreCase("CONTAINS-")){
				strExpValueToCompare = strElemStateToVerify.substring(9).trim();
				strActValue = elemToVerify.getText().trim();
				if(elemToVerify.getTagName().trim().equalsIgnoreCase("input")){
					strActAttribute = elemToVerify.getAttribute("value").trim();
				} else{
					strActAttribute = "";
				}
				if((strActValue.contains(strExpValueToCompare) && blnExpValue)
						|| (!strActValue.contains(strExpValue.trim()) && !blnExpValue)
						|| (strActAttribute.contains(strExpValueToCompare) && blnExpValue)
						|| (!strActAttribute.contains(strExpValueToCompare) && !blnExpValue)){
					report.updateTestLog("Verify if the Element(" + strObjName + ")" + strExpStateToReport
							+ "contains value", strObjName + " value: '" + strExpValueToCompare + "'"
							+ strExpStateToReport + "displayed as expected", Status.PASS);
					return true;
				} else{
					report.updateTestLog("Verify if the Element(" + strObjName + ")" + strExpStateToReport
							+ "contains value",
							"Expected: '" + strExpValueToCompare + "' Actual: '" + elemToVerify.getText() + "'",
							Status.FAIL);
					return false;
				}

			}

			// ***************To verify if the selected value of a list/combo
			// box is same**************
			else if(strElemStateToVerify.substring(0, 15).equalsIgnoreCase("SELECTED VALUE-")){
				Select comSelElement = new Select(elemToVerify);
				strExpValueToCompare = strElemStateToVerify.substring(15).trim();
				strActValue = comSelElement.getFirstSelectedOption().getText().trim();
				if((strActValue.contains(strExpValueToCompare) && blnExpValue)
						|| (!strActValue.contains(strExpValueToCompare.trim()) && !blnExpValue)){
					report.updateTestLog("Verify if the Element(" + strObjName + ") value is " + strExpStateToReport
							+ "selected", strObjName + " value: '" + strExpValueToCompare + "' is"
							+ strExpStateToReport + "selected from list", Status.PASS);
					return true;
				} else{
					report.updateTestLog("Verify if the Element(" + strObjName + ") is" + strExpStateToReport
							+ "selected", strObjName + " value : " + strExpValueToCompare + " is" + strExpStateToReport
							+ "SELECTED from list", Status.FAIL);
					return false;
				}
			}

			// ***************To verify if the combo box has the list of given
			// values**************
			else if(strElemStateToVerify.substring(0, 15).equalsIgnoreCase("LIST OF VALUES-")){
				List<String> arExpLst;
				boolean blnResult = false, blnActCompare = true;
				List<WebElement> lstOptions = new Select(elemToVerify).getOptions();
				strExpValueToCompare = strElemStateToVerify.substring(15).trim();

				arExpLst = Arrays.asList(strExpValueToCompare.toLowerCase().split(","));
				List<String> arActLst = new ArrayList<String>();
				for(WebElement elemOption : lstOptions){
					arActLst.add(elemOption.getText().toString().trim().toLowerCase());
				}
				for(int i = 0; i < arExpLst.size(); i++){
					blnActCompare = arActLst.indexOf(arExpLst.get(i).trim()) != -1;
					if((blnActCompare && blnExpValue) || !(blnActCompare || blnExpValue)){
						blnResult = true;
					} else{
						blnResult = false;
						break;
					}

				}
				if(blnResult){
					report.updateTestLog("Verify if the list : (" + strObjName + ") " + strExpStateToReport
							+ "has values", strObjName + " " + strExpStateToReport + " has values : "
							+ strExpValueToCompare + " as expected in the drop down box", Status.PASS);
					return true;
				} else{
					report.updateTestLog("Verify if the list : (" + strObjName + ") " + strExpStateToReport
							+ "has values", strObjName + " " + strExpStateToReport + " has values : "
							+ strExpValueToCompare + " ; Actual: "
							+ arActLst.toArray(new String[arActLst.size()]).toString(), Status.FAIL);
					return false;
				}
			}

		} else
			return true;
		return false;
	}

	/**
	 * Function name: verifyElementAttribute Description: To verify an attribute
	 * of an object Parameters: strPropertyValue - Object property value,
	 * strPropertyBy - Property type strAttributeName - attribute type,
	 * strAttributeValue - expected attribute value Developed by: VaibhavS
	 * */
	public void verifyElementAttribute(String strPropertyValue, String strPropertyBy, String strObjName,
			String strAttributeName, String strAttributeValue){
	
		String strActualValue = getElementAttribute(strPropertyValue, strPropertyBy, strAttributeName, strObjName);
		if(strActualValue.equalsIgnoreCase(strAttributeValue)){
			report.updateTestLog("Attribute Verify", strAttributeName + " attribute for " + strObjName
					+ " verified as " + strAttributeValue, Status.PASS);
		} else{
			report.updateTestLog("Attribute Verify", strAttributeName + " attribute for " + strObjName
					+ " is not verified.", Status.FAIL);
		}
	}

	/**
	 * Method to verify the positioning of two web elements. Always to compare
	 * the second element with first element keeping the first element as
	 * static.
	 * 
	 * @param strPositionToVerify
	 *            Standard values to be given are : LEFT,RIGHT,ABOVE,BELOW
	 * @param elemStatic
	 *            This is the element which is set as source of comparison
	 * @param elemToVerify
	 *            This is the element which is to be compared (whether this
	 *            element is above/below/left/right of elemStatic)
	 * @return boolean returns true if the element is present in required
	 *         condition
	 * 
	 */
	public boolean verifyElementPositions(WebElement elemStatic, String elemStaticName, String strPositionToVerify,
			WebElement elemToVerify, String elemToVerifyName){
		
		int intElem1LocX, intElem1LocY, intElem2LocX, intElem2LocY, intElem1Width, intElem2Width, intElem1Height, intElem2Height;
		boolean blnPositionCheck = false;
		String ErrText = "";
		intElem1LocX = elemStatic.getLocation().x;
		intElem1LocY = elemStatic.getLocation().y;
		intElem2LocX = elemToVerify.getLocation().x;
		intElem2LocY = elemToVerify.getLocation().y;
		intElem1Width = elemStatic.getSize().width;
		intElem2Width = elemToVerify.getSize().width;
		intElem1Height = elemStatic.getSize().height;
		intElem2Height = elemToVerify.getSize().height;

		if(strPositionToVerify.equalsIgnoreCase("LEFT")){
			blnPositionCheck = ((intElem2LocX - (intElem1LocX + intElem1Width)) > -5)
					|| ((intElem2LocX - (intElem1LocX + intElem1Width)) < 5);
			if(!blnPositionCheck){
				ErrText = elemStaticName + " is Not to the Left of " + elemToVerifyName + "." + System.lineSeparator()
						+ "Expected X coordinate of " + elemToVerifyName + ": > " + (intElem1LocX + intElem1Width)
						+ " (i.e., X coordinate + Width of " + elemStaticName + ")." + System.lineSeparator()
						+ "Actual X Coordinate of " + elemToVerifyName + " :" + intElem2LocX;
			}
		} else if(strPositionToVerify.equalsIgnoreCase("RIGHT")){
			blnPositionCheck = ((intElem1LocX - (intElem2LocX + intElem2Width)) > -5)
					|| ((intElem1LocX - (intElem2LocX + intElem2Width)) < 5);
			if(!blnPositionCheck){
				ErrText = elemStaticName + " is Not to the Right of " + elemToVerifyName + "." + System.lineSeparator()
						+ "Expected X coordinate of " + elemStaticName + ": > " + (intElem2LocX + intElem2Width)
						+ " (i.e., X coordinate + Width of " + elemToVerifyName + ")." + System.lineSeparator()
						+ "Actual X Coordinate of " + elemStaticName + " :" + intElem1LocX;
			}
		} else if(strPositionToVerify.equalsIgnoreCase("ABOVE")){

			blnPositionCheck = ((intElem2LocY - (intElem1LocY + intElem1Height)) > -5)
					|| ((intElem2LocY - (intElem1LocY + intElem1Height)) < 5);
			if(!blnPositionCheck){
				ErrText = elemStaticName + " is Not Above " + elemToVerifyName + "." + System.lineSeparator()
						+ "Expected Y coordinate of " + elemToVerifyName + ": > " + (intElem1LocY + intElem1Height)
						+ " (i.e., Y coordinate + Height of " + elemStaticName + ")." + System.lineSeparator()
						+ "Actual Y Coordinate of " + elemToVerifyName + " :" + intElem2LocY;
			}

		} else if(strPositionToVerify.equalsIgnoreCase("BELOW")){
			blnPositionCheck = ((intElem1LocY - (intElem2LocY + intElem2Height)) > -5)
					|| ((intElem1LocY - (intElem2LocY + intElem2Height)) < 5);
			if(!blnPositionCheck){
				ErrText = elemStaticName + " is Not Below " + elemToVerifyName + "." + System.lineSeparator()
						+ "Expected Y coordinate of " + elemStaticName + ": > " + (intElem2LocY + intElem2Height)
						+ " (i.e., Y coordinate + Height of " + elemToVerifyName + ")." + System.lineSeparator()
						+ "Actual Y Coordinate of " + elemStaticName + " :" + intElem1LocY;
			}
		}

		if(blnPositionCheck){
			report.updateTestLog("verifyElementPositions".toUpperCase(), "The element : " + elemStaticName + " is "
					+ strPositionToVerify + " the second element : " + elemToVerifyName + " ; as expected.",
					Status.PASS);
			return true;
		} else{
			report.updateTestLog("verifyElementPositions".toUpperCase(), ErrText, Status.FAIL);
			return false;
		}
	}

	/**
	 ************************************************************* 
	 * Function to verify Same text present in an element
	 * 
	 * @param Element
	 *            The {@Webelement Element} object that contains
	 *            the page element
	 * 
	 * @param textToVerify
	 *            The {@text String} Attribute name of the element which is to
	 *            be verified
	 * @return
	 ************************************************************* 
	 */

	public boolean verifyElementPresentEqualsText(WebElement element, String StrObjName, String textToVerify)	throws IOException
	{
		if(!textToVerify.trim().equalsIgnoreCase("IGNORE")){
			try{
				String pageSource = element.getText();
				String pageSource1 = RemoveSpecialcharacters(pageSource); 
				String textToVerify1 = RemoveSpecialcharacters(textToVerify);
				
				if(pageSource1.equals(textToVerify1))
				{
					report.updateTestLog(StrObjName, "'" + StrObjName + "' : " 	+ " Verification is Success", Status.PASS);
					return true;
				} else{
					report.updateTestLog(StrObjName, StrObjName + " : Verification is Failure. Expected : " + textToVerify
							+ " Actual : " + pageSource, Status.FAIL);
				}

			} catch(Exception e){
				report.updateTestLog(("Error in method description").toUpperCase(), e.toString(), Status.FAIL);
			}
		}
		return false;
	}
	/**
	 ************************************************************* 
	 * Function to verify text is present in an element
	 * 
	 * @param Element
	 *            The {@Webelement Element} object that contains
	 *            the page element
	 * 
	 * @param textToVerify
	 *            The {@text String} Attribute name of the element which is to
	 *            be verified
	 * @return
	 ************************************************************* 
	 */

	public boolean verifyElementPresentContainsText(WebElement element, String StrObjName, String textToVerify)	throws IOException
	{
		if(!textToVerify.trim().equalsIgnoreCase("IGNORE")){
			try{
				String pageSource = element.getText().trim();
				
				//System.out.println("|"+ pageSource +"|");
				//System.out.println("|"+ textToVerify +"|");
				
				if(pageSource.contains(textToVerify))
				{
					report.updateTestLog(StrObjName, "'" + StrObjName + "' : " 	+ " Verification is Success", Status.PASS);
					return true;
				} else{
					report.updateTestLog(StrObjName, StrObjName + " : Verification is Failure. Expected : " + textToVerify
							+ " Actual : " + pageSource, Status.FAIL);
				}

			} catch(Exception e){
				report.updateTestLog(("Error in method description").toUpperCase(), e.toString(), Status.FAIL);
			}
		}
		return false;
	}

	/**
	 ************************************************************* 
	 * Function to verify text present in an element
	 * 
	 * @param Element
	 *            The {@Webelement Element} object that contains
	 *            the page element
	 * 
	 * @param textToVerify
	 *            The {@text String} Attribute name of the element which is to
	 *            be verified
	 * @return
	 * @throws InterruptedException 
	 ************************************************************* 
	 */

	public boolean verifyLinkInWebPage(WebElement element, String StrObjName,String linkToVerify)	throws IOException, InterruptedException
	{
		try
		{
				String oldTab = driver.getWindowHandle();
				element.click();
				ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
			    newTab.remove(oldTab);
			    
			    // change focus to new tab
			    driver.switchTo().window(newTab.get(0));
			    Thread.sleep(6000); 
			   
			    //System.out.println("Actual  :"+driver.getCurrentUrl());
			    //System.out.println("Expected:"+linkToVerify);
			    
			    if(driver.getCurrentUrl().equals(linkToVerify))
			    { 
				    report.updateTestLog(StrObjName, "'" + StrObjName + "' : " 	+ " Verification is Success", Status.PASS);
					
			    }
			    else
			    {
			    	report.updateTestLog(StrObjName, StrObjName + " : Verification is Failure. Expected Url: " + linkToVerify
							+ " Actual : " + driver.getCurrentUrl(), Status.FAIL);
			    	
			    }
			    
			    // Do what you want here, you are in the new tab
			    driver.close();
			    // change focus back to old tab
			    driver.switchTo().window(oldTab);
			    return true;
		} 
		catch(Exception e)
		{
			report.updateTestLog(("Error in method description"), e.toString(), Status.FAIL);
		}
	    return false;
		
	}

	
	/***************************************************************
	 * Function to verify text is not present in an element
	 * 
	 * @author sivka002
	 * @param Element
	 *            The {@Webelement Element} object that contains
	 *            the page element
	 * @param textToVerify
	 *            The {@text String} Attribute name of the element which is to
	 *            be verified
	 * @return boolean true - if element does not contain the text
	 **************************************************************/

	public boolean verifyElementPresentNotContainsText(WebElement element, String StrObjName, String textToVerify)
			throws IOException{
		
		if(!textToVerify.trim().equalsIgnoreCase("IGNORE")){
			try{
				textToVerify = textToVerify.replace(" ", "");
				String pageSource = element.getText().toLowerCase();
				String[] pageSourceLines = pageSource.trim().split("\\n");
				String pageSourceWithoutNewlines = "";
				textToVerify = textToVerify.toLowerCase().replaceAll(" ", "");

				for(String pageSourceLine : pageSourceLines){
					pageSourceWithoutNewlines += pageSourceLine + " ";
				}

				pageSourceWithoutNewlines = pageSourceWithoutNewlines.trim().replaceAll(" ", "").replaceAll("\\$", "");
				Pattern p = Pattern.compile(textToVerify);
				Matcher m = p.matcher(pageSourceWithoutNewlines);

				if(!m.find()){
					report.updateTestLog(StrObjName, "'" + StrObjName + "' value doesnot contain text : "
							+ textToVerify + " Verification is Success", Status.PASS);
					return true;
				} else{
					report.updateTestLog(StrObjName, StrObjName + " value contains text : " + textToVerify + ","
							+ " Actual : " + pageSourceWithoutNewlines, Status.FAIL);
				}

			} catch(Exception e){
				report.updateTestLog(("Error in method description").toUpperCase(), e.toString(), Status.FAIL);
			}
		}
		return false;
	}

	/**
	 ************************************************************* 
	 * Method to verify an element state based on given input conditions
	 * 
	 * @param elemToVerify
	 *            The {@link strObjProperty} element to be verified
	 * @param strElemStateToVerify
	 *            The {@link strElemStateToVerify} describes the state to be
	 *            verified which can be either one of
	 *            ENABLED/SELECTED/TEXT/SELECTED VALUE
	 * @param strExpValue
	 *            The {@link strExpValue} corresponds to each state
	 *            representations of {@link strElemStateToVerify} namely
	 *            Y/N/<the actual text>
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean verifyElementState(WebElement elemToVerify, String strElemStateToVerify, String strExpValue,
			String strObjName) throws IOException{
		
		boolean blnExpValue = true;
		if(!strExpValue.trim().equalsIgnoreCase("IGNORE")){
			if(strExpValue.trim().equalsIgnoreCase("N")){
				blnExpValue = false;
			}
			if(strElemStateToVerify.equalsIgnoreCase("ENABLED")){
				if((elemToVerify.isEnabled() && blnExpValue) || (!elemToVerify.isEnabled() && !blnExpValue))
					return true;
			} else if(strElemStateToVerify.equalsIgnoreCase("SELECTED")){
				if((elemToVerify.isSelected() && blnExpValue) || (!elemToVerify.isSelected() && !blnExpValue))
					return true;
			} else if(strElemStateToVerify.equalsIgnoreCase("VALUE")){
				if((elemToVerify.getText().trim().equalsIgnoreCase(strExpValue.trim()) && blnExpValue)
						|| (!elemToVerify.getText().trim().equalsIgnoreCase(strExpValue.trim()) && !blnExpValue))
					return true;
			} else if(strElemStateToVerify.equalsIgnoreCase("CONTAINS")){
				if((elemToVerify.getText().toUpperCase().contains(strExpValue.trim().toUpperCase()) && blnExpValue)
						|| (!elemToVerify.getText().toUpperCase().contains(strExpValue.trim().toUpperCase()) && !blnExpValue))
					return true;
			} else if(strElemStateToVerify.equalsIgnoreCase("SELECTED VALUE")){
				Select comSelElement = new Select(elemToVerify);
				if((comSelElement.getFirstSelectedOption().getText().trim().equalsIgnoreCase(strExpValue.trim()) && blnExpValue)
						|| (!comSelElement.getFirstSelectedOption().getText().trim()
								.equalsIgnoreCase(strExpValue.trim()) && !blnExpValue))
					return true;
			}
		}
		return false;
	}

	
	/**
	 * Inserts the character ch at the location index of string st
	 * @param st
	 * @param ch
	 * @param index
	 * @return a new string 
	 */
	    public String insertCharAt(String st, char ch, int index){
	        //1 Exception if st == null
	        //2 Exception if index<0 || index>st.length()

	        if (st == null){
	            throw new NullPointerException("Null string!");
	        }

	        if (index < 0 || index > st.length())
	        {
	            throw new IndexOutOfBoundsException("Try to insert at negative location or outside of string");
	        }
	        return st.substring(0, index)+ch+st.substring(index, st.length());
	    }
	

	/**
	 * Description: Function to validate items in a list
	 * 
	 * @param strPropertyValue
	 *            = object property value
	 * @param strPropType
	 *            = type of property to be used
	 * @param strObjName
	 *            = object label for reporting
	 * @param strItemNames
	 *            = items to be verified seperated by semi-colin(;) returns:
	 *            nothing Author: VaibhavS
	 */
	public void verifyItemsInList(String strPropertyValue, String strPropType, String strObjName, String strItemNames){
	
		try{
			if(isElementPresentVerification(strPropertyValue, strPropType, strObjName)){
				WebElement elemToClick = getElementByProperty(strPropertyValue, strPropType);
				// clickAnyElement(elemToClick,strObjName);
				String[] arrListNames = strItemNames.split(";");
				List<WebElement> lstElement = getElementsByProperty(elemToClick, "*", "XPATH");
				for(String strItemName : arrListNames){
					for(WebElement webElement : lstElement){
						String strText = webElement.getText();
						if(strText.equalsIgnoreCase(strItemName)){
							report.updateTestLog("Verify Item " + strItemName + " is present", strItemName
									+ " is present", Status.PASS);
							break;
						}
					}
					if(false){
						report.updateTestLog("Verify Item " + strItemName + " is present", strItemName
								+ " is not present", Status.FAIL);
					}
				}

			} else{
				report.updateTestLog("Verify if the Element(" + strObjName + ") is present", strObjName
						+ " is not present", Status.FAIL);
			}
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("VerifyItemsInList: " + strObjName, strObjName + " object does not exist in page",
					Status.FAIL);
		} catch(Exception e){
			report.updateTestLog("VerifyItemsInList", "Error in method - Error Description - " + e.toString(),
					Status.FAIL);
		}
	}

	/**
	 ************************************************************* 
	 * Method to verify multiple lines in a web-element text
	 * 
	 * @param Element
	 *            The {@Webelement Element} object that contains
	 *            the page element
	 * @param textToVerify
	 *            The {@text String} text value to be verified
	 * @param strObjName
	 *            The name of object for reference
	 * @return
	 ************************************************************* 
	 */
	public boolean verifyMultiLine(WebElement element, String StrObjName, String textToVerify) throws IOException{
		
		boolean blnFound = true;
		if(!textToVerify.trim().equalsIgnoreCase("IGNORE")){
			try{
				String[] arrLinesToVerify = textToVerify.trim().split("\\n");
				String[] arrPageSrcLines = element.getText().trim().split("\\n");
				for(int itPageSrc = 0; itPageSrc < arrPageSrcLines.length; itPageSrc++)
					if(arrPageSrcLines[itPageSrc].trim().equalsIgnoreCase(arrLinesToVerify[0].trim())){
						for(int itLines = 0; itLines < arrLinesToVerify.length; itLines++)
							if(!arrPageSrcLines[itPageSrc + itLines].trim().equalsIgnoreCase(
									arrLinesToVerify[itLines].trim())){
								blnFound = false;
								break;
							}
					} else if(!blnFound && itPageSrc == arrPageSrcLines.length){
						report.updateTestLog("verifyMultiLineText".toUpperCase(), StrObjName
								+ " do not have expected text : " + textToVerify + " ; Actual : " + element.getText(),
								Status.FAIL);
						return false;
					} else if(blnFound){
						report.updateTestLog("verifyMultiLineText".toUpperCase(), StrObjName + " has the text : "
								+ textToVerify + " as expected.", Status.PASS);
						return true;
					}
			} catch(Exception e){
				report.updateTestLog("verifyMultiLineText".toUpperCase(),
						"Error in verifying elements :  " + e.getMessage(), Status.FAIL);
			}
		}
		return false;
	}

	

	public void verifyTextPresent(String strText) throws Exception{
		
		assertTrue(isTextPresent(strText));
		report.updateTestLog("Text Verification", strText + "The Text is present ", Status.PASS);

	}

	public void waitForElementClickable(String xpathVal, long time){
		
		try{
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathVal)));
		} catch(Exception e){
			System.err.print(e.getMessage());
		}
	}


	public boolean waitForElementVisibility(String xpathVal, long time){
		
		try{
			long startTime = System.currentTimeMillis();

			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVal)));
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			report.updateTestLog("Wait For Element - element found", "Waited for " + elapsedTime / 1000
					+ " seconds to find element with xpath : " + xpathVal, Status.DONE);
			return true;
		} catch(Exception e){
			return false;
		}
	}

	/**
	 * Method to synchronize until element is present
	 * @param strElementValue - property value
	 * @param strElementType - element locator type
	 * @param time - synchronization timeout
	 * @return true/false
	 */
	public boolean waitForElementPresence(String strElementValue, String strElementType, long time)
	{
	
		try{
			long startTime = System.currentTimeMillis();
			if(strElementType.equalsIgnoreCase("XPATH")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("ID")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("NAME")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.name(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("LINKTEXT")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("CSS")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(strElementValue)));

			}
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			report.updateTestLog("Wait For Element - element found", "Waited for " + elapsedTime / 1000
					+ " seconds to find element with " + strElementType + " - " + strElementValue, Status.DONE);
			return true;
		} catch(Exception e){
			System.err.print(e.getMessage());
			return false;

		}
	}
	
	public boolean waitForElementVisibility(String strElementValue, String strElementType, long time){
		
		try{
			long startTime = System.currentTimeMillis();
			if(strElementType.equalsIgnoreCase("XPATH")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("ID")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("NAME")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("LINKTEXT")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("CSS")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(strElementValue)));

			}
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			report.updateTestLog("Wait For Element - element found", "Waited for " + elapsedTime / 1000
					+ " seconds to find element with " + strElementType + " - " + strElementValue, Status.DONE);
			return true;
		} catch(Exception e){
			System.err.print(e.getMessage());
			return false;

		}
	}

	public boolean waitForElementVisibilityByLinkText(String strlinkText, long time){
		
		try{
			long startTime = System.currentTimeMillis();
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(strlinkText)));
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			report.updateTestLog("Wait For Element - element found", "Waited for " + elapsedTime / 1000
					+ " seconds to find element with xpath : " + strlinkText, Status.DONE);
			return true;
		} catch(Exception e){
			return false;
		}
	}

	public boolean isElementPresentInsideElement(WebElement parentElement, String strObjectProperty,
			String strFindElementType){
		WebElement elemToFind = null;
		try{

			if(strFindElementType.equalsIgnoreCase("CSS"))
				elemToFind = parentElement.findElement(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				elemToFind = parentElement.findElement(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				elemToFind = parentElement.findElement(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				elemToFind = parentElement.findElement(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				elemToFind = parentElement.findElement(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				elemToFind = parentElement.findElement(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				elemToFind = parentElement.findElement(By.className(strObjectProperty));
			// report.updateTestLog("isElementPresent","Expected...",
			// Status.PASS);

		} catch(org.openqa.selenium.NoSuchElementException nsee){
			System.out.println("Exception in isElementPresent:" + nsee);
			return false;
		}
		// Extra protection...
		if(elemToFind == null){
			return false;
		} else{
			//System.out.println("Found element:" + strObjectProperty);
			return true;
		}
	}
	
	
}
