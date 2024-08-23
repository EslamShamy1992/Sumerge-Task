package TestCases;

import Base.BaseTest;
import Config.ConfigUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class testCases extends BaseTest {



    @Test
    public void Login_with_A_Valid_Credentails() {
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        driver.findElement(By.id("user-name")).sendKeys(ConfigUtils.getInstance().getEmail());
        driver.findElement(By.id("password")).sendKeys(ConfigUtils.getInstance().getPassword());
        driver.findElement(By.id("login-button")).click();
        // assert that the user is logged in successfully and products text is displayed
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).isDisplayed());
    }

    @Test
    public void Login_with_InValid_Credentails() {
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        driver.findElement(By.id("user-name")).sendKeys("Invalid uder");
        driver.findElement(By.id("password")).sendKeys("12345678");
        driver.findElement(By.id("login-button")).click();
        //assert that the error message is displayed
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3")).isDisplayed());
    }

    @Test
    public void Validate_that_all_products_Is_Displayed() {
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        driver.findElement(By.id("user-name")).sendKeys(ConfigUtils.getInstance().getEmail());
        driver.findElement(By.id("password")).sendKeys(ConfigUtils.getInstance().getPassword());
        driver.findElement(By.id("login-button")).click();
        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        int expected = 6;
        //Assert that the number of displayed products is 6
        Assert.assertEquals(expected, products.size());
    }

    @Test
    public void Valiate_Product_Details() {
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        driver.findElement(By.id("user-name")).sendKeys(ConfigUtils.getInstance().getEmail());
        driver.findElement(By.id("password")).sendKeys(ConfigUtils.getInstance().getPassword());
        driver.findElement(By.id("login-button")).click();
        List<WebElement> products = driver.findElements(By.className("inventory_item"));

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

    @Test
    public void Validate_Price_Sorting_Low_To_High() {

        driver.get(ConfigUtils.getInstance().getBaseUrl());
        driver.findElement(By.id("user-name")).sendKeys(ConfigUtils.getInstance().getEmail());
        driver.findElement(By.id("password")).sendKeys(ConfigUtils.getInstance().getPassword());
        driver.findElement(By.id("login-button")).click();
        WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
        Select sortSelect = new Select(sortDropdown);
        sortSelect.selectByVisibleText("Price (low to high)");
        List<WebElement> productPrices = driver.findElements(By.className("inventory_item_price"));
        List<Float> prices = new ArrayList<>();
        for (WebElement priceElement : productPrices) {
            String priceText = priceElement.getText().replace("$", "");
            prices.add(Float.parseFloat(priceText));
        }
        List<Float> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);

        // Assert that prices is sorted correctly
        Assert.assertEquals(prices, sortedPrices);
    }

    @Test
    public void Add_Product_To_Cart_And_verify_the_update() {
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        driver.findElement(By.id("user-name")).sendKeys(ConfigUtils.getInstance().getEmail());
        driver.findElement(By.id("password")).sendKeys(ConfigUtils.getInstance().getPassword());
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".shopping_cart_container .shopping_cart_badge")));

        // Verify that the cart is updated
        String itemCount = cartBadge.getText();
        Assert.assertEquals(itemCount, "1");
    }

    @Test
    public void Remove_product_from_cart(){
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        driver.findElement(By.id("user-name")).sendKeys(ConfigUtils.getInstance().getEmail());
        driver.findElement(By.id("password")).sendKeys(ConfigUtils.getInstance().getPassword());
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".shopping_cart_container .shopping_cart_badge")));
        String itemCount = cartBadge.getText();
        // Verify that the cart is updated
        Assert.assertEquals(itemCount, "1");
       driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        List<WebElement> shoppingCart = driver.findElements(By.cssSelector(".shopping_cart_badge"));

        // Assert that cart is empty
        Assert.assertTrue(shoppingCart.isEmpty());
    }

    @Test
    public void verify_update_shopping_cart_badge(){
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        driver.findElement(By.id("user-name")).sendKeys(ConfigUtils.getInstance().getEmail());
        driver.findElement(By.id("password")).sendKeys(ConfigUtils.getInstance().getPassword());
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".shopping_cart_container .shopping_cart_badge")));
        String itemCount = cartBadge.getText();
        // Verify that the cart is updated
        Assert.assertEquals(itemCount, "1");
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        List<WebElement> shoppingCart = driver.findElements(By.cssSelector(".shopping_cart_badge"));

        // Assert that cart is updated after remove the item
        Assert.assertTrue(shoppingCart.isEmpty());
    }

    @Test
    public void Verify_Logout_Functionality(){
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        driver.findElement(By.id("user-name")).sendKeys(ConfigUtils.getInstance().getEmail());
        driver.findElement(By.id("password")).sendKeys(ConfigUtils.getInstance().getPassword());
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();

        //verify that the login button is displayed in login page
        Assert.assertTrue(driver.findElement(By.id("login-button")).isDisplayed());
    }
}
