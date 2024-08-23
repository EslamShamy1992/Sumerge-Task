package PageObjects;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver driver) {
        super(driver);
    }


    By productsHeader = By.xpath("//*[@id='header_container']/div[2]/span");
    By productItems = By.className("inventory_item");
    By sortDropdown = By.className("product_sort_container");
   public By cartBadge = By.cssSelector(".shopping_cart_container .shopping_cart_badge");
    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutLink = By.id("logout_sidebar_link");


    public boolean isProductsHeaderDisplayed() {
        return driver.findElement(productsHeader).isDisplayed();
    }

    public List<WebElement> getProductItems() {
        return driver.findElements(productItems);
    }

    public void selectSortOption(String visibleText) {
        Select sortSelect = new Select(driver.findElement(sortDropdown));
        sortSelect.selectByVisibleText(visibleText);
    }

    public void addToCart(String productId) {
        driver.findElement(By.id("add-to-cart-" + productId)).click();
    }

    public void removeFromCart(String productId) {
        driver.findElement(By.id("remove-" + productId)).click();
    }

    public boolean isCartBadgeVisible() {
        List<WebElement> cartBadgeElements = driver.findElements(cartBadge);
        return !cartBadgeElements.isEmpty();
    }

    public String getCartBadgeText() {
        return driver.findElement(cartBadge).getText();
    }
    public void clickMenuButton() {
        driver.findElement(menuButton).click();
    }

    public void clickLogout() {
        driver.findElement(logoutLink).click();
    }
}

