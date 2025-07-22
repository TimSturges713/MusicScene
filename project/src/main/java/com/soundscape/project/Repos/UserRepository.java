/*
 * @filename: UserRepository.java
 * @author: Timothy Sturges
 * 
 * The User specific repository methods that are used to perform CRUD operations, pre-made by JPA repository and used 
 * by service class and controller class.
 */
package com.soundscape.project.Repos;

import com.soundscape.project.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;






public interface UserRepository extends JpaRepository<User, Long>{
    /*
     * Finds the User in the database based on the userId entered into the method.
     * Implementation was automatically handled by JPARepository.
     * 
     * 
     */
    User findByUserId(long userId);
    User findByUsername(String username);
}
