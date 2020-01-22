package it.akademija.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


import it.akademija.model.NewProduct;
import it.akademija.model.Product;
import it.akademija.service.ProductService;

@RestController
@Api(value = "product")
@RequestMapping(value = "/api/products")
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Get Products", notes = "Returns list of all products")
	public List<Product> getProducts() {
		return productService.getProducts(); 
	}

	@RequestMapping(path = "/{productID}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Product", notes = "Returns product by ID")
	public Product getProduct(@PathVariable Long productID) {
		return productService.getProduct(productID);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Create product", notes = "Creates product with data")
	@ResponseStatus(HttpStatus.CREATED)
	public void createProduct(
			@ApiParam(value = "Product Data", required = true) @Valid @RequestBody final NewProduct newProduct) {
		productService.createProduct(newProduct);
		System.out.println("Image " + newProduct.getImage());
	}


}
