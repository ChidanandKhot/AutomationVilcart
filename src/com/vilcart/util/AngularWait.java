package com.vilcart.util;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AngularWait {
	WebDriver driver;
	WebDriverWait wait;
	
	public AngularWait(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}
  
public void untilAngularFinishHttpCalls() {
    final String javaScriptToLoadAngular =
            "var injector = window.angular.element('body').injector();" + 
            "var $http = injector.get('$http');" + 
            "return ($http.pendingRequests.length === 0)";

    ExpectedCondition<Boolean> pendingHttpCallsCondition = new ExpectedCondition<Boolean>() {
        public Boolean apply(WebDriver driver) {
            return ((JavascriptExecutor) driver).executeScript(javaScriptToLoadAngular).equals(true);
        }
    };
    //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // timeout = 20 secs
    wait.until(pendingHttpCallsCondition);
}
private void waitUntilJSReady() {
    try {
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) this.driver)
            .executeScript("return document.readyState").toString().equals("complete");
        boolean jsReady = ((JavascriptExecutor) this.driver).executeScript("return document.readyState").toString().equals("complete");
        if (!jsReady) {
            wait.until(jsLoad);
        }
    } catch (WebDriverException ignored) {
    }
}
private void ajaxComplete() {
	((JavascriptExecutor) this.driver).executeScript("var callback = arguments[arguments.length - 1];"
        + "var xhr = new XMLHttpRequest();" + "xhr.open('GET', '/Ajax_call', true);"
        + "xhr.onreadystatechange = function() {" + "  if (xhr.readyState == 4) {"
        + "    callback(xhr.responseText);" + "  }" + "};" + "xhr.send();");
}
private void waitUntilJQueryReady() {
    Boolean jQueryDefined = (Boolean) ((JavascriptExecutor) this.driver).executeScript("return typeof jQuery != 'undefined'");
    if (jQueryDefined) {
        poll(20);
        waitForJQueryLoad();
        poll(20);
    }
}
private void waitForJQueryLoad() {
    try {
        ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) this.driver)
            .executeScript("return jQuery.active") == 0);
        boolean jqueryReady = (Boolean) ((JavascriptExecutor) this.driver).executeScript("return jQuery.active==0");
        if (!jqueryReady) {
            wait.until(jQueryLoad);
        }
    } catch (WebDriverException ignored) {
    }
}
public void waitUntilAngular5Ready() {
    try {
        Object angular5Check = ((JavascriptExecutor) this.driver).executeScript("return getAllAngularRootElements()[0].attributes['ng-version']");
        if (angular5Check != null) {
            Boolean angularPageLoaded = (Boolean) ((JavascriptExecutor) this.driver).executeScript("return window.getAllAngularTestabilities().findIndex(x=>!x.isStable()) === -1");
            if (!angularPageLoaded) {
                poll(20);
                waitForAngular5Load();
                poll(20);
            }
        }
    } catch (WebDriverException ignored) {
    }
}
private void waitForAngular5Load() {
    String angularReadyScript = "return window.getAllAngularTestabilities().findIndex(x=>!x.isStable()) === -1";
    angularLoads(angularReadyScript);
}
private void angularLoads(String angularReadyScript) {
    try {
    	 ExpectedCondition<Boolean> angularLoad = new ExpectedCondition<Boolean>() {
             public Boolean apply(WebDriver driver) {
                 return ((JavascriptExecutor) driver).executeScript(angularReadyScript).equals(true);
             }
         };
    	
        /*ExpectedCondition<Boolean> angularLoad = driver -> Boolean.valueOf(((JavascriptExecutor) driver)
            .executeScript(angularReadyScript).toString());*/
        boolean angularReady = Boolean.valueOf(((JavascriptExecutor) driver).executeScript(angularReadyScript).toString());
        if (!angularReady) {
        	wait.until(angularLoad);
        }
    } catch (WebDriverException ignored) {
    }
}

private void poll(long milis) {
    try {
        Thread.sleep(milis);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
public void waitAllRequest() {
    waitUntilJSReady();
    ajaxComplete();
    waitUntilJQueryReady();
    //waitUntilAngularReady();
    waitUntilAngular5Ready();
}

}
