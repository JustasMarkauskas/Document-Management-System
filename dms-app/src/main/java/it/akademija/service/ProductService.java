package it.akademija.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import it.akademija.dao.ProductRepository;

import it.akademija.model.NewProduct;
import it.akademija.model.Product;


@Service
public class ProductService {

	private ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Transactional(readOnly = true)
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	@Transactional
	public Product getProduct(Long id) {
		return getProducts().stream().filter(p -> p.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find product"));
	}

	@Transactional
	public void createProduct(NewProduct newProduct) {
		Product product = new Product();
		product.setTitle(newProduct.getTitle());
		product.setImage(newProduct.getImage());
		product.setDescription(newProduct.getDescription());
		product.setPrice(newProduct.getPrice());
		product.setQuantity(newProduct.getQuantity());
		productRepository.save(product);
	}

	@Transactional
	public Product updateProduct(Long id, NewProduct newProduct) {
		Product existingProduct = getProduct(id);
		existingProduct.setImage(newProduct.getImage());
		existingProduct.setDescription(newProduct.getDescription());
		existingProduct.setTitle(newProduct.getTitle());
		existingProduct.setPrice(newProduct.getPrice());
		existingProduct.setQuantity(newProduct.getQuantity());
		return existingProduct;

	}



}
