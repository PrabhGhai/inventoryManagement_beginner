package com.test.Test.repos;
import com.test.Test.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    //List<Product> findByDescriptionLike(String s);

    List<Product> findByDescriptionContaining(String s);

    List<Product> findByDescription(String name);

    List<Product> findByName(String name);

    List<Product> findByNameIgnoreCase(String name);

    List<Product> findByDescriptionOrNameContaining(String name,String name2);

    List<Product> findAllByDescriptionOrNameContaining(String name, String name1);

    List<Product> findAllByDescriptionContainingOrNameContaining(String name, String name1);

    List<Product> findAllByDescriptionContainingIgnoreCaseOrNameContainingIgnoreCase(String name, String name1);

    List<Product> findByDescriptionContainingIgnoreCaseOrNameContainingIgnoreCase(String name, String name1);


    //Page<Product> findAll(Pageable pageable);
}
