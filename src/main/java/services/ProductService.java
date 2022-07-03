package services;

import domaines.Product;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct(){
        List<Product> products = new ArrayList<>();
        for(Product product: productRepository.findAll()) {
            products.add(product);
        }
        return products;
    }

    public Product findByIdProduct(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new Exception("Product not found"));
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Inventory getProductsData() {
        Inventory inventory = new Inventory();
        for(Product p: productRepository.findAll()) {
            if (inventory.containsKey(p.getNom().toLowerCase())) {
                InventoryItem productItem = inventory.get(p.getNom().toLowerCase());
                if (!"broken".equals(p.getState())) {
                    productItem.setQty(productItem.getQty() + 1);
                    productItem.setTotalPrice(productItem.getTotalPrice() + p.getPrice());
                    productItem.productBarcodes += "," + p.getBarcode();
                }
            } else {
                inventory.put(p.getNom().toLowerCase(), new InventoryItem(p.getNom(), p.getPrice(), 1));
            }
        }
        return inventory;
    }

    public static class Inventory extends HashMap<String, InventoryItem> { }

    @Data
    public static class InventoryItem {
        private final String pName;
        private int qty;
        private float totalPrice;
        private String productBarcodes;

        public InventoryItem(String pName, float totalPrice, int qty) {
            this.pName = pName;
            this.totalPrice = totalPrice;
            this.qty = qty;
        }

    }
}