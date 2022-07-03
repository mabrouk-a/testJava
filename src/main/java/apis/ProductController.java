package apis;

import domaines.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.ProductService;

import java.util.List;
@RequestMapping("/product")
@RestController
public class ProductController {


    @Autowired
    private ProductService productService;

    public ProductController() {
    }

    @GetMapping("/")
    public List<Product> findAll() {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> find(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(productService.findByIdProduct(id));
    }

    @PostMapping("/")
    public Product save(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/inventory")
    public ProductService.Inventory getInventory() {
        return productService.getProductsData();
    }
}