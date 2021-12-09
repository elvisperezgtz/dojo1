import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;


public class SauceLabs {

    WebDriver driver;
    WebElement campoUsuario;
    WebElement campoPassword;
    WebElement botonIniciarSesion;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
                .concat("\\src\\test\\resources\\drivers\\chromedriver.exe"));
        /**
         *Precondicion: Se debe contar con acceso a la pagina web
         */
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        /**
         * Postcondiciones, acciones necesarias para dejar la aplicacion en el estado original
         * o de acciones de limpieza post prueba
         */
        WebDriverWait wait = new WebDriverWait(driver,5);
        driver.quit();
    }

    @Test
    public void deberiaIniciarSesionCuandoEnviamosCredencialesValidas() throws InterruptedException {

        iniciarSesion("standard_user","secret_sauce");
        WebElement carritoCompras = new WebDriverWait(driver, 20)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.className("shopping_cart_link")));
//        Assertions.assertTrue(carritoCompras.isDisplayed());
        assertThat("El Carrito de compras no debe estar visible",carritoCompras.isDisplayed(), is(false) );
    }

    @Test
    public void deberiaCoincidirElTitulo() throws InterruptedException {
//        Assertions.assertEquals("Swag Labs", driver.getTitle());
        assertThat("El titulo de la pagina debe coincidir", "Swag Labs", is(driver.getTitle()));
    }

    public void iniciarSesion(String usuario, String password) {
        campoUsuario=  driver.findElement(By.name("user-name"));
        campoPassword =  driver.findElement(By.name("password"));
        botonIniciarSesion =driver.findElement(By.name("login-button"));
        campoUsuario.sendKeys(usuario);
        campoPassword.sendKeys(password);
        botonIniciarSesion.click();
        WebElement carritoCompras = new WebDriverWait(driver, 20)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.className("shopping_cart_link")));
    }
    @Test
    public void elPrecioDelArticuloEsInferiorA30Dolares(){
        iniciarSesion("standard_user","secret_sauce");
        String nombreArticulo="Sauce Labs Onesie";
        assertThat("El precio del articulo debe ser menor a $30",obtenerElPrecioArticulo(nombreArticulo) , lessThan(30));
    }
    public int obtenerElPrecioArticulo(String nombreArticulo){
        WebElement precioArticulo = driver.findElement(By.xpath("(//div[contains(text(),'"+nombreArticulo+"')]/following::div[@class='inventory_item_price'])[1]"));
        int valorArticulo=(int)Double.parseDouble(precioArticulo.getText().replace("$",""));
        return  valorArticulo;
    }
}
