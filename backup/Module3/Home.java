package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // SLEEP_STMT_10: Wait for Logout to complete
            // Wait for Logout to Complete
            Thread.sleep(3000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any errors
     */
    public Boolean searchForProduct(String product) {
        try {
            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/div/input"))
                    .clear();
            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/div/input"))
                    .sendKeys(product);
             Thread.sleep(3000);

            // WebDriverWait wait = new WebDriverWait(driver, 30);

            // wait.until(ExpectedConditions.or(

            //         ExpectedConditions.presenceOfElementLocated(

            //                 By.xpath("//h4[text()=' No products found ']")),

            //         ExpectedConditions
            //                 .presenceOfElementLocated(By.xpath("//button[text()='Add to cart']"))));

            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {};
        try {
            searchResults = driver
                    .findElements(By.xpath("//div[@class='MuiCardContent-root css-1qw96cp']"));
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            if (driver.findElement(By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div/h4"))
                    .isDisplayed()) {
                status = true;
            } else {
                status = false;
            }
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            /*
             * Iterate through each product on the page to find the WebElement corresponding to the
             * matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
            driver.findElement(
                    By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div/div/div[2]/button"))
                    .click();
            Thread.sleep(2000);
            // System.out.println("Unable to find the given product");
            return true;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            driver.findElement(By.xpath(
                    "//button[@class='MuiButton-root MuiButton-contained MuiButton-containedPrimary MuiButton-sizeMedium MuiButton-containedSizeMedium MuiButtonBase-root checkout-btn css-177pwqq']"))
                    .click();
            Thread.sleep(3000);
            return true;

        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        try {

            List<WebElement> ItemsInCart = driver.findElements(By.className("css-zgtx0t"));
            WebDriverWait wait = new WebDriverWait(driver, 5);

            int currentQty;

            for (int i = 1; i <= ItemsInCart.size(); i++) {
                if (productName
                        .contains(driver
                                .findElement(By.xpath("(//*[@class='MuiBox-root css-zgtx0t'])[" + i
                                        + "]//*[@class='MuiBox-root css-1gjj37g']/div[1]"))
                                .getText())) {
                    currentQty = Integer.valueOf(ItemsInCart.get(i - 1)
                            .findElement(By.className("css-olyig7")).getText());
                    // Increment or decrement the quantity of the matching product until the current

                    while (currentQty != quantity) {
                        //Thread.sleep(1000);
                        if (currentQty < quantity) {


                            ItemsInCart.get(i - 1).findElements(By.tagName("button")).get(1)
                                    .click();
                            wait.until(ExpectedConditions.textToBePresentInElement(
                                    ItemsInCart.get(i - 1).findElement(By.className("css-olyig7")),
                                    String.valueOf(currentQty + 1)));
                           Thread.sleep(3000);
                            // ItemsInCart.findElements(By.tagName("button")).get(1).click();
                            // WebDriverWait wait = new WebDriverWait(driver, 30);
                            // wait.until(ExpectedConditions.textToBePresentInElement(ItemsInCart.findElements(By.tagName("button")).get(1),String.valueOf(currentQty + 1)));
                            
                            
                        } else {

                            ItemsInCart.get(i - 1).findElements(By.tagName("button")).get(0)
                                    .click();
                                    wait.until(ExpectedConditions.textToBePresentInElement(
                                        ItemsInCart.get(i - 1).findElement(By.className("css-olyig7")),
                                        String.valueOf(currentQty - 1)));
                     
                            Thread.sleep(3000);

                        }
                        currentQty = Integer.valueOf(ItemsInCart.get(i - 1)
                                .findElement(By.xpath("//div[@data-testid='item-qty']")).getText());
                    }
                    return true;

                }
            }
            return false;



        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            WebElement cartParent = driver.findElement(By.className("cart"));
            List<WebElement> cartContents = cartParent.findElements(By.className("css-zgtx0t"));

            ArrayList<String> actualCartContents = new ArrayList<String>() {};
            for (WebElement cartItem : cartContents) {
                actualCartContents.add(
                        cartItem.findElement(By.className("css-1gjj37g")).getText().split("\n")[0]);
            }

            for (String expected : expectedCartContents) {
                if (!actualCartContents.contains(expected)) {
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }
}
