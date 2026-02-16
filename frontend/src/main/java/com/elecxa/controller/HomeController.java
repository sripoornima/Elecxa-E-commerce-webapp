package com.elecxa.controller;

import com.elecxa.dto.CategoryDTO;

import com.elecxa.dto.ProductDTO;
import com.elecxa.dto.SubCategoryDTO;
import com.elecxa.service.CategoryService;
import com.elecxa.service.ProductService;
import com.elecxa.service.SubCategoryService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;
    
    @Autowired
    SubCategoryService subcategoryService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String showHomePage( Model model) {
        return "user/index"; 
    }
    
    @GetMapping("/login")
    public String showHomePage1(@RequestParam int id ,@RequestParam String token, Model model  , HttpSession session) {
    	
    	 Integer userId = id;
         session.setAttribute("accessToken", token);
         session.setAttribute("userId", userId.longValue());
         
        return "user/index"; 
    }
    
    @GetMapping("/signin")
    public String showLoginPage(Model model) {
        return "user/sign-in"; 
    }
    
    
    @GetMapping("/logout")
    public String redirectLoginPage(Model model , HttpSession session) {
    	session.removeAttribute("accessToken");
    	session.removeAttribute("userId");

        return "user/sign-in"; 
    }


    @GetMapping("/products")
    public String showProductsByCategory(@RequestParam(required = false) String category,@RequestParam(required = false) Integer id , Model model , HttpSession session) {
        if (category != null) {
            model.addAttribute("categoryName", category);
        }
    	String token = (String)session.getAttribute("accessToken");

        long categoryId;
        if(id == -1) {
        	categoryId =categoryService.getCategoryByName(category , token).getCategoryId();
        	id = (int) categoryId;
        }
        List<ProductDTO> products = (category != null)
                ? productService.getProductsByCategory(category , id , token)
                : productService.getPopularProducts(token);

        model.addAttribute("products", products);
        model.addAttribute("categoryId",id);
        return "user/products-page";
    }
    
    @GetMapping("/subcategory/products")
    public String showProductsBySubCategory(@RequestParam(required = false) String subcategory,@RequestParam(required = false) Integer id , Model model , HttpSession session) {
    	if (subcategory != null) {
            model.addAttribute("subcategories", subcategory);
        }
    	String token = (String)session.getAttribute("accessToken");

        List<ProductDTO> products = (subcategory != null)
                ? productService.getProductsBySubCategory(subcategory , token)
                : productService.getPopularProducts(token);

        model.addAttribute("products", products);
        model.addAttribute("categoryId",id);
        
        SubCategoryDTO subCategory = categoryService.getCategoryBySubCategory(id , token);
        model.addAttribute("categoryName",subCategory.getCategory().getName());
        model.addAttribute("categoryId",subCategory.getCategory().getCategoryId());

        return "user/products-page";
    }
}