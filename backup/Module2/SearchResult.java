package QKART_SANITY_LOGIN.Module1;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResult {
    WebElement parentElement;

    public SearchResult(WebElement SearchResultElement) {
        this.parentElement = SearchResultElement;
    }

    /*
     * Return title of the parentElement denoting the card content section of a
     * search result
     */
    public String getTitleofResult() {
        String titleOfSearchResult = "";
        // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
        // Find the element containing the title (product name) of the search result and
        // assign the extract title text to titleOfSearchResult
        titleOfSearchResult = parentElement.getText();
        return titleOfSearchResult;
    }

    /*
     * Return Boolean denoting if the open size chart operation was successful
     */
    public Boolean openSizechart() {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
                  // Find the link of size chart in the parentElement and click on it
   WebElement openSizechart = parentElement.findElement(By.xpath("//button[text()='Size chart']"));
   
   //WebElement openSizechart = parentElement.findElement(By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div[5]/div/div[1]/button/text()"));
    openSizechart.click();
    Thread.sleep(3000);
    //System.out.println("SIZE CHART BUTTON CLICKED");


            return true;
        } catch (Exception e) {
            System.out.println("Exception while opening Size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the close size chart operation was successful
     */
    public Boolean closeSizeChart(WebDriver driver) {
        try {
            Thread.sleep(2000);
            Actions action = new Actions(driver);

            action.sendKeys(Keys.ESCAPE);
            action.perform();
            Thread.sleep(2000);
            return true;
        } catch (Exception e) {
            System.out.println("Exception while closing the size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean based on if the size chart exists
     */
    public Boolean verifySizeChartExists() {
        Boolean status = false;
        try {
            /*
             * Check if the size chart element exists. If it exists, check if the text of
             * the element is "SIZE CHART". If the text "SIZE CHART" matches for the
             * element, set status = true , else set to false
             */
            WebElement verifySizeChart = parentElement.findElement(By.xpath("//button[text()='Size chart']"));
           status = verifySizeChart.getText().equals("SIZE CHART");
           System.out.println(verifySizeChart.getText());
           System.out.println(status);
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if the table headers and body of the size chart matches the
     * expected values
     */
    public Boolean validateSizeChartContents(List<String> expectedTableHeaders, List<List<String>> expectedTableBody,
            WebDriver driver) {
        Boolean status = true;
        try {
            /*
             * Locate the table element when the size chart modal is open
             * 
             * Validate that the contents of expectedTableHeaders is present as the table
             * header in the same order
             * 
             * Validate that the contents of expectedTableBody are present in the table body
             * in the same order
             */
            Thread.sleep(3000);

            WebElement sizeChart = driver.findElement(By.className("MuiDialog-paperScrollPaper"));           

            WebElement table = sizeChart.findElement(By.tagName("table"));
            
            List<WebElement> tableHeader = table.findElement(By.tagName("thead")).findElements(By.tagName("th"));
            String tempHeaderValue;

            System.out.println(tableHeader.size());

           
                for(int i=0;i<expectedTableHeaders.size();i++){
                    tempHeaderValue = tableHeader.get(i).getText();
                    if(!expectedTableHeaders.get(i).equals(tempHeaderValue)){
                        System.out.println("Failure in header comparision "+expectedTableHeaders.get(i));                        
                        status = false;
                    }
                    System.out.println(tempHeaderValue);
                    

                }

            List<WebElement> tablebodyrow = driver.findElements(By.xpath("//table/tbody/tr")); 
          
            List <WebElement> tablebodycell;
            
            for(int i =0; i<expectedTableBody.size();i++){
                tablebodycell = tablebodyrow.get(i).findElements(By.tagName("td"));
                
             for(int j=0;j<expectedTableBody.get(i).size();j++){
                tempHeaderValue = tablebodycell.get(j).getText();


                if(!expectedTableBody.get(i).get(j).equals(tempHeaderValue)){
                    System.out.println("Failure in body Comparisions:Expected" +expectedTableBody.get(i).get(j)+ "Actual: "+tempHeaderValue);
                    status = false;
                }
             }   
            }
           
            return status;
           
          

        } catch (Exception e) {
            System.out.println("Error while validating chart contents");
            return false;
        }
    }

    /*
     * Return Boolean based on if the Size drop down exists
     */
    public Boolean verifyExistenceofSizeDropdown(WebDriver driver) {
        Boolean status = false;
        try {
            driver.findElement(By.xpath("//*[@id=\"uncontrolled-native\"]")).isDisplayed();
            return status;
        } catch (Exception e) {
            return status;
        }
    }
}