package com.example.userservice.service;

import com.example.userservice.entity.Product;
import com.example.userservice.entity.Rating;
import com.example.userservice.entity.User;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.external_services.ProductService;
import com.example.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generate  unique userid
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        //implement RATING SERVICE CALL: USING REST TEMPLATE
        return userRepository.findAll();
    }

    //get single user
    @Override
    public User getUser(String userId) {
        //get user from database with the help  of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));
        // fetch rating of the above  user from RATING SERVICE
        //http://localhost:28083/ratings/users/50f21243-b569-4ce8-b86d-0bef32f5e7a6
        Rating[] ratingsFromUser= restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{}",ratingsFromUser);

        List<Rating> ratings = Arrays.stream(ratingsFromUser).collect(Collectors.toList());

        List<Rating> ratingList =  ratings.stream().map(rating ->{
            //http://localhost:28082/products/c9e56d4c-dcc9-47da-bbad-af099b0266cd
            //ResponseEntity<Product> forEntity = restTemplate.getForEntity("http://PRODUCT-SERVICE/products/"+rating.getProductId(), Product.class);
            //Product product = forEntity.getBody();
            //logger.info("response status code: {}",forEntity.getStatusCode());

            Product product = productService.getProduct(rating.getProductId());

            rating.setProduct(product);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}