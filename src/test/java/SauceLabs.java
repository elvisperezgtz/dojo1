import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class SauceLabs {

    WebDriver driver;

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
        Thread.sleep(5000);
        driver.quit();
    }

    @Test
    public void deberiaIniciarSesionCuandoEnviamosCredencialesValidas() throws InterruptedException {
        /**
         * mapeo de elementos
         */
        WebElement usuario = driver.findElement(By.name("user-name"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement botonIniciarSesion = driver.findElement(By.name("login-button"));
        /**
         * Acciones necesarias para llevar la aplicacion al estado deseado
         */
        usuario.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        botonIniciarSesion.click();

        /**
         * validacion o verificacion del resultado esperado vs resultado obtenido
         */
        WebElement carritoCompras = driver.findElement(By.className("shopping_cart_link"));
        Assertions.assertTrue(carritoCompras.isDisplayed());
    }

    @Test
    public void deberiaCoincidirElTitulo() throws InterruptedException {
        Assertions.assertEquals("Swag Labs", driver.getTitle());
    }
}
