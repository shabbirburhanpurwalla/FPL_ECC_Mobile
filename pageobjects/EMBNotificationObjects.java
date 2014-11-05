package pageobjects;

import static pageobjects.ObjectLocator.*;


/**
 * @author 324096
 *
 */
public enum EMBNotificationObjects 
{
	// EMB Notification Email Objects
	// Header Links Objects
	lnk_Login("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[1]/table/tbody/tr/td[2]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Login Link"),
	lnk_PayBill("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[1]/table/tbody/tr/td[3]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Pay Bill Link"),
	lnk_ContactUs("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[1]/table/tbody/tr/td[4]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Contact Us Link"),
	//Email content Objects
	lbl_CustomerName("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[1]/table/tbody/tr/td[6]/div[1]/span",XPATH,"Customer Name"),
	lbl_AccountNumber("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[1]/table/tbody/tr/td[6]/div[2]/span[3]",XPATH,"Account Number"),
	lbl_EmailAddress("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[1]/table/tbody/tr/td[6]/div[4]/span/table/tbody/tr/td/div/span[1]",XPATH,"Email Address"),
	lnk_UpdateEmailAddress("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[1]/table/tbody/tr/td[6]/div[5]/a[1]/span",XPATH,"Update Email Address"),
	
	lbl_ServiceDatesMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[3]/span/table/tbody/tr/td/div/span/table/tbody/tr/td[1]/div",XPATH,"service Dates Message"),
	
	lbl_Hello("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[1]/span[1]",XPATH,"Hello"),
	lbl_CustomerName2("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[1]/span[2]",XPATH,"Customer Name"),
	lbl_BillPayMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[1]/span[4]",XPATH,"Discontinue Message"),
	lbl_BalanceMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[2]",XPATH,"Current Balance Message"),
	
	lnk_PayBillButton("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[2]/td/div/span/table/tbody/tr/td[1]/div/span/table/tbody/tr/td/div/span/a/img",XPATH,"Pay Bill Link"),
	lnk_ViewBill("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[2]/td/div/span/table/tbody/tr/td[2]/div/span/table/tbody/tr/td/div/span/a/img",XPATH,"View Bill Link"),
	lnk_DownloadBill("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[2]/td/div/span/table/tbody/tr/td[3]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Download Bill Link"),
	
	// Security Message Objects
	lbl_SecurityMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[2]/div[1]/span",XPATH,"Security Message"),
	lbl_SecurityLinkText("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[2]/div[1]/a/span",XPATH,"Security Message Link"),
	lnk_SecurityLink("Learn more",LINKTEXT,"Security Link: Learn More"),
	
	lbl_BillViewMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[5]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div",XPATH,"EMB Message"),
	
	//Past Due Email Content
	lbl_PastDueMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[1]",XPATH,"Past Due Message"),
	
	
	//Ready to pay mail content
	lbl_ReadyToPayMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[1]/span[4]",XPATH,"Ready To Pay Meassage"),
	
	// ABP enrollment Message Objects
	lbl_ABPEnrollmentMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[2]/div[2]/span/table/tbody/tr/td/div/span[2]",XPATH,"ABP Enrollment Message"),
	lbl_ABPEnrollmentLinkText("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr[2]/td[2]/div[2]/span/table/tbody/tr/td/div/a/span",XPATH,"ABP Enrollment Link Text"),
	lnk_ABPEnrollmentLink("Enroll today",LINKTEXT,"ABP Enrollment Link"), 
	
	
	
	//Notification Objects
	lbl_EMBNotificationMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[5]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div",XPATH,"EMB Notification Message"),
	lbl_EMBNotificationMessage1("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[7]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[2]/div/span/table/tbody/tr/td/div/span[1]",XPATH,"EMB Notification Message 1"),
	lbl_EMBNotificationMessage2("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[7]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[2]/div/span/table/tbody/tr/td/div/span[2]",XPATH,"EMB Notification Message 2"),
	
	// Register Link Objects
	lbl_RegisterLinkText("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[7]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[2]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Regiser Today link Text"),
	lnk_RegisterLink("Register today",LINKTEXT,"Register Link:Register Today"),
	
	
	// Promotional Messages Objects
	lbl_OHESPromoHeader("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div/span",XPATH,"OHES Promo Header"),
	lbl_OHESMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[2]/td[2]/div/span/table/tbody/tr/td/div",XPATH,"OHES Message"),
	lnk_GetStarted("Start saving",LINKTEXT,"Start Saving Link"),
	lbl_PowertrackerHeader("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[2]/div/span/table/tbody/tr/td/div/span",XPATH,"Power Tracker Header"),
	lbl_PowertrackerMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[2]/td[4]/div/span/table/tbody/tr/td/div/span",XPATH,"Power Tracker Message"),
	lnk_Hereshow("Here's how",LINKTEXT,"Here's how Link"),
	lbl_ResidentialHeader("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[3]/div/span/table/tbody/tr/td/div/span",XPATH,"Residential Header"),
	lbl_ResidentialMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[2]/td[6]/div/span/table/tbody/tr/td/div/span",XPATH,"Residential Message"),
	lnk_Seehow("See how",LINKTEXT,"See how Link"),
	
	// Footer Links Objects
	lnk_EnergyNews("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[9]/table/tbody/tr/td[1]/div/table/tbody/tr/td[1]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Energy News Link"),
	lnk_PrivacyPolicy("Privacy Policy",LINKTEXT,"Privacy Policy Link"),
	lnk_AboutUs("About Us",LINKTEXT,"About Us Link"),
	lbl_CopyRight("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[9]/table/tbody/tr/td[2]/div",XPATH,"Copy Right"),
	lbl_FooterMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[10]/table/tbody/tr/td/div",XPATH,"Footer Message"),
	
	;
	
	
	String strProperty = "";
	ObjectLocator locatorType = null;
	String strObjName = "";
	
	
	
	public String getProperty(){
		return strProperty;
	}

	public ObjectLocator getLocatorType(){
		return locatorType;
	}
	
	public String getObjectName(){
		return strObjName;
	}

	private EMBNotificationObjects(String strPropertyValue, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.locatorType = locatorType;
		this.strObjName = strObjName;
	}

}
