package it.akademija.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.Product;




public interface ProductRepository extends JpaRepository<Product, Long>  {

}
