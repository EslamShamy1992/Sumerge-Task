package TestCases;

import Base.BaseTest;
import Config.ConfigUtils;
import PageObjects.LoginPage;
import PageObjects.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class testCases extends BaseTest {


    LoginPage loginPage;
    ProductPage productPage;

    @Test(priority = 1)
    public void Login_with_A_Valid_Credentails() {
        loginPage= new LoginPage(driver);
        productPage= new ProductPage(driver);
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        loginPage.enterUsername(ConfigUtils.getInstance().getEmail());
        loginPage.enterPassword(ConfigUtils.getInstance().getPassword());
        loginPage.clickLoginButton();
         // assert that the user is logged in successfully and products text is displayed
        Assert.assertTrue(productPage.isProductsHeaderDisplayed());
    }

    @Test(priority = 2)
    public void Login_with_InValid_Credentails() {

        loginPage= new LoginPage(driver);
        productPage= new ProductPage(driver);
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        loginPage.enterUsername("Invalid user");
        loginPage.enterPassword("12345678");
        loginPage.clickLoginButton();
        //assert that the error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());

    }

    @Test(priority = 3)
    public void Validate_that_all_products_Is_Displayed() {
        loginPage= new LoginPage(driver);
        productPage= new ProductPage(driver);
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        loginPage.enterUsername(ConfigUtils.getInstance().getEmail());
        loginPage.enterPassword(ConfigUtils.getInstance().getPassword());
        loginPage.clickLoginButton();
        List<WebElement> products = productPage.getProductItems();
        //Assert that the number of displayed products is 6
        Assert.assertEquals(products.size(), 6);
    }

    @Test(priority = 4)
    public void Valiate_Product_Details() {
        loginPage= new LoginPage(driver);
        productPage= new ProductPage(driver);
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        loginPage.enterUsername(ConfigUtils.getInstance().getEmail());
        loginPage.enterPassword(ConfigUtils.getInstance().getPassword());
        loginPage.clickLoginButton();
        List<WebElement> products = productPage.getProductItems();
        // Expected details
        String[] expectedNames = {"Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt", "Sauce Labs Fleece Jacket", "Sauce Labs Onesie", "Test.allTheThings() T-Shirt (Red)"};
        String[] expectedPrices = {"$29.99", "$9.99", "$15.99", "$49.99", "$7.99", "$15.99"};
        String[] expectedDescriptions = {
                "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.",
                "A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.",
                "Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt.",
                "It's not every day that you come across a midweight quarter-zip fleece jacket capable of handling everything from a relaxing day outdoors to a busy day at the office.",
                "Rib snap infant onesie for the junior automation engineer in development. Reinforced 3-snap bottom closure, two-needle hemmed sleeved and bottom won't unravel.",
                "This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton."
        };

        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);

            String productName = product.findElement(By.className("inventory_item_name")).getText();
            String productPrice = product.findElement(By.className("inventory_item_price")).getText();
            String productDescription = product.findElement(By.className("inventory_item_desc")).getText();

            // Assert the product name, price, and description
            Assert.assertEquals(expectedNames[i], productName);
            Assert.assertEquals(expectedPrices[i], productPrice);
            Assert.assertEquals(expectedDescriptions[i], productDescription);
        }
    }

    @Test(priority = 5)
    public void Validate_Price_Sorting_Low_To_High() {

        loginPage= new LoginPage(driver);
        productPage= new ProductPage(driver);
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        loginPage.enterUsername(ConfigUtils.getInstance().getEmail());
        loginPage.enterPassword(ConfigUtils.getInstance().getPassword());
        loginPage.clickLoginButton();
        productPage.selectSortOption("Price (low to high)");
        List<WebElement> productPrices = productPage.getProductItems();
        List<Float> prices = new ArrayList<>();
        for (WebElement priceElement : productPrices) {
            String priceText = priceElement.findElement(By.className("inventory_item_price")).getText().replace("$", "");
            prices.add(Float.parseFloat(priceText));
        }
        List<Float> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);

        // Assert that prices is sorted correctly
        Assert.assertEquals(prices, sortedPrices);

    }

    @Test(priority = 6)
    public void Add_Product_To_Cart_And_verify_the_update() {
        loginPage= new LoginPage(driver);
        productPage= new ProductPage(driver);
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        loginPage.enterUsername(ConfigUtils.getInstance().getEmail());
        loginPage.enterPassword(ConfigUtils.getInstance().getPassword());
        loginPage.clickLoginButton();
        productPage.addToCart("sauce-labs-backpack");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(productPage.cartBadge));
        // Verify that the cart is updated
        Assert.assertEquals(cartBadge.getText(), "1");
    }

    @Test(priority = 7)
    public void Remove_product_from_cart(){
        loginPage= new LoginPage(driver);
        productPage= new ProductPage(driver);
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        loginPage.enterUsername(ConfigUtils.getInstance().getEmail());
        loginPage.enterPassword(ConfigUtils.getInstance().getPassword());
        loginPage.clickLoginButton();
        productPage.addToCart("sauce-labs-backpack");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(productPage.cartBadge));
        Assert.assertEquals(cartBadge.getText(), "1");
        productPage.removeFromCart("sauce-labs-backpack");
        List<WebElement> shoppingCart = driver.findElements(productPage.cartBadge);
        // Assert that cart is empty
        Assert.assertTrue(shoppingCart.isEmpty());

    }

    @Test(priority = 8)
    public void verify_update_shopping_cart_badge(){
        loginPage= new LoginPage(driver);
        productPage= new ProductPage(driver);
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        loginPage.enterUsername(ConfigUtils.getInstance().getEmail());
        loginPage.enterPassword(ConfigUtils.getInstance().getPassword());
        loginPage.clickLoginButton();
        productPage.addToCart("sauce-labs-backpack");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(productPage.cartBadge));
        // Verify that the cart is updated
        Assert.assertEquals(cartBadge.getText(), "1");
        productPage.removeFromCart("sauce-labs-backpack");
        List<WebElement> shoppingCart = driver.findElements(productPage.cartBadge);
        // Assert that cart is updated after remove the item
        Assert.assertTrue(shoppingCart.isEmpty());
    }
    @Test(priority = 9)
    public void Verify_Logout_Functionality(){
        loginPage= new LoginPage(driver);
        productPage= new ProductPage(driver);
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        loginPage.enterUsername(ConfigUtils.getInstance().getEmail());
        loginPage.enterPassword(ConfigUtils.getInstance().getPassword());
        loginPage.clickLoginButton();
        productPage.clickMenuButton();
        productPage.clickLogout();
        // Verify that the login button is displayed on the login page
        Assert.assertTrue(loginPage.isLoginButtonDisplayed());

    }
}
