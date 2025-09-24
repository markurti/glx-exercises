package com.glologistics.service;

import com.glologistics.model.Product;
import com.glologistics.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import java.util.List;

@Service
@Log4j2
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        log.info("Adding new product: {}", product.getProductName());
        return productRepository.addProduct(product);
    }

    public Product updateProduct(int productId, int quantity, Double price) {
        Product product = productRepository.getProductById(productId);
        if (product != null) {
            if (quantity >= 0) {
                product.setProductQuantityInStock(quantity);
            }
            if (price != null && price > 0) {
                product.setProductPrice(price);
            }
            return productRepository.updateProduct(product);
        }
        log.warn("Product not found with ID: {}", productId);
        return null;
    }

    public void deleteProduct(int productId) {
        log.info("Deleting product with ID: {}", productId);
        productRepository.deleteProduct(productId);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product getProductById(int productId) {
        return productRepository.getProductById(productId);
    }

    public boolean isProductAvailable(int productId, long quantity) {
        Product product = productRepository.getProductById(productId);
        return product != null && product.getProductQuantityInStock() >= quantity;
    }

    public void updateStock(int productId, long quantity) {
        Product product = productRepository.getProductById(productId);
        if (product != null) {
            product.setProductQuantityInStock((int)(product.getProductQuantityInStock() - quantity));
            productRepository.updateProduct(product);
        }
    }
}

