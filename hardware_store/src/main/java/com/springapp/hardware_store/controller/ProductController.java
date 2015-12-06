package com.springapp.hardware_store.controller;

import com.springapp.hardware_store.dao.MemberDAO;
import com.springapp.hardware_store.dao.ProductCategoryDAO;
import com.springapp.hardware_store.dao.ProductDAO;
import com.springapp.hardware_store.dao.RatingDAO;
import com.springapp.hardware_store.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by radud on 03/12/2015.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ApplicationContext appContext;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Product> getProducts() {
        ProductDAO productDAO = (ProductDAO) appContext.getBean("productDao");
        List<Product> products = productDAO.findAll();

        return products;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Product getProducts(@PathVariable("id") int id) {
        ProductDAO productDAO = (ProductDAO) appContext.getBean("productDao");
        Product product = productDAO.findById(id);

        return product;
    }

    @RequestMapping(value = "/category/get/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Product> getProductsByCategory(@PathVariable("id") int id) {
        ProductDAO productDAO = (ProductDAO) appContext.getBean("productDao");
        List<Product> products = productDAO.findProductsForCategory(id);

        return products;
    }

    @RequestMapping(value = "/category/get", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ProductCategory> getProductsByCategory() {
        ProductCategoryDAO productCategoryDAO = (ProductCategoryDAO) appContext.getBean("productCategoryDao");
        List<ProductCategory> productCategories = productCategoryDAO.findAll();

        return productCategories;
    }

    @RequestMapping(value = "/rating/get/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Rating> getRatingsForProduct(@PathVariable("id") int id) {
        RatingDAO ratingDAO = (RatingDAO) appContext.getBean("ratingDao");
        List<Rating> ratings = ratingDAO.findRatingsForProduct(id);

        return ratings;
    }

    @RequestMapping(value = "/rating/add/{id}", method = RequestMethod.POST)
    public
    @ResponseBody
    Result getRatingsForProduct(@RequestParam(value = "memberId") int memberId, @RequestBody Rating rating, @PathVariable("id") int id) {
        RatingDAO ratingDAO = (RatingDAO) appContext.getBean("ratingDao");
        MemberDAO memberDAO = (MemberDAO) appContext.getBean("memberDao");
        ProductDAO productDAO = (ProductDAO) appContext.getBean("productDao");

        rating.setMember(memberDAO.findById(memberId));
        rating.setProduct(productDAO.findById(id));
        ratingDAO.save(rating);

        Result result = new Result();
        result.setHasErrors(false);
        result.setMessage("Success");

        return result;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Product deleteProduct(@PathVariable("id") int id) {
        ProductDAO productDAO = (ProductDAO) appContext.getBean("productDao");
        Product product = productDAO.delete(id);

        return product;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    Result register(@RequestBody Product product) {
        ProductDAO productDAO = (ProductDAO) appContext.getBean("productDao");
        ProductCategoryDAO productCategoryDAO = (ProductCategoryDAO) appContext.getBean("productCategoryDao");

        Result result = new Result();

        if (product == null) {
            result.setHasErrors(true);
            result.setMessage("Member is null");
            return result;
        }

        ProductCategory productCategory = productCategoryDAO.getByField("name", product.getCategory().getName());
        product.setCategory(productCategory);

        productDAO.save(product);

        result.setHasErrors(false);
        return result;
    }
}
