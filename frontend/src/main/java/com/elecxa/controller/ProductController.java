package com.elecxa.controller;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.elecxa.dto.CategoryDTO;
import com.elecxa.dto.ProductAttributeDTO;
import com.elecxa.dto.ProductDTO;
import com.elecxa.dto.ProductSaveDTO;
import com.elecxa.dto.SubCategoryDTO;
import com.elecxa.service.CategoryService;
import com.elecxa.service.ProductService;
import com.elecxa.service.SubCategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	SubCategoryService subcategoryService;

	private String backendUrl;

	@GetMapping("/dashboard")
	public String showDashboard(Model model, HttpServletRequest request) {
		model.addAttribute("pageTitle", "Dashboard");
		model.addAttribute("currentUri", request.getRequestURI());
		return "admin/dashboard";
	}

	@GetMapping("/orders")
	public String showOrders(Model model, HttpServletRequest request) {
		model.addAttribute("pageTitle", "Orders");
		model.addAttribute("currentUri", request.getRequestURI());
		return "admin/orders";
	}

	@GetMapping("/reports")
	public String showReports(Model model, HttpServletRequest request) {
		model.addAttribute("pageTitle", "Reports");
		model.addAttribute("currentUri", request.getRequestURI());
		return "admin/reports";
	}

	@GetMapping("/settings")
	public String showSettings(Model model, HttpServletRequest request) {
		model.addAttribute("pageTitle", "Settings");
		model.addAttribute("currentUri", request.getRequestURI());
		return "admin/settings";
	}

	@GetMapping
	public String showProductDetails(HttpSession session, Model model, HttpServletRequest request) {

    	String token = (String)session.getAttribute("accessToken");

		if (session.getAttribute("darkMode") == null) {
			session.setAttribute("darkMode", false);
		}
		if (session.getAttribute("sidebarCollapsed") == null) {
			session.setAttribute("sidebarCollapsed", false);
		}

		model.addAttribute("currentUri", request.getRequestURI());
		model.addAttribute("newProduct", new ProductSaveDTO());
		List<CategoryDTO> categories = categoryService.getAllCategories(token);
		model.addAttribute("categories", categories);

		List<SubCategoryDTO> subcategories = subcategoryService.getAllSubCategories(token);
		model.addAttribute("subcategories", subcategories);

		List<ProductDTO> products = productService.getAllProducts(token);
		model.addAttribute("products", products);
		
		return "admin/products";
	}

	@GetMapping("/category/{categoryName}")
	public String showProductDetailsByCategory(HttpSession session, Model model, HttpServletRequest request,
			@PathVariable String categoryName) {
    	String token = (String)session.getAttribute("accessToken");

		if (session.getAttribute("darkMode") == null) {
			session.setAttribute("darkMode", false);
		}
		if (session.getAttribute("sidebarCollapsed") == null) {
			session.setAttribute("sidebarCollapsed", false);
		}

		model.addAttribute("currentUri", request.getRequestURI());
		model.addAttribute("newProduct", new ProductSaveDTO());
		List<CategoryDTO> categories = categoryService.getAllCategories(token);
		model.addAttribute("categories", categories);

		List<SubCategoryDTO> subcategories = subcategoryService.getSubCategoriesByCategory(categoryName , token);
		model.addAttribute("subcategories", subcategories);

		List<ProductDTO> products = productService.getProductByCategory(categoryName , token);
		model.addAttribute("products", products);

		return "admin/products";
	}

	@GetMapping("/subcategory/{subcategoryName}")
	public String showProductDetailsBySubCategory(HttpSession session, Model model, HttpServletRequest request,
			@PathVariable String subcategoryName) {
    	String token = (String)session.getAttribute("accessToken");

		if (session.getAttribute("darkMode") == null) {
			session.setAttribute("darkMode", false);
		}
		if (session.getAttribute("sidebarCollapsed") == null) {
			session.setAttribute("sidebarCollapsed", false);
		}

		model.addAttribute("currentUri", request.getRequestURI());
		model.addAttribute("newProduct", new ProductSaveDTO());

		List<SubCategoryDTO> subcategories = subcategoryService.getAllSubCategories(token);
		model.addAttribute("subcategories", subcategories);

		List<CategoryDTO> categories = categoryService.getAllCategories(token);
		model.addAttribute("categories", categories);
		

		List<ProductDTO> products = productService.getProductsBySubCategory(subcategoryName , token);
		System.out.println(products);
		model.addAttribute("products", products);

		return "admin/products";
	}

	@GetMapping("/{id}")
	public String showProductDetail(@PathVariable Long id, Model model , HttpSession session) {
    	String token = (String)session.getAttribute("accessToken");

		ProductDTO product = productService.getProductsById(id , token);
		model.addAttribute("product", product);
		model.addAttribute("subcategory", product.getSubcategory().getName());
		model.addAttribute("categoryname", product.getSubcategory().getCategory().getName());
		model.addAttribute("categoryId", product.getSubcategory().getCategory().getCategoryId());

		List<ProductAttributeDTO> productAttribute = productService.getProductAttributes(id , token);
		model.addAttribute("generalSpecs", productAttribute.subList(0, 2));
		model.addAttribute("performanceSpecs", productAttribute.subList(2, 4));
		model.addAttribute("displaySpecs", productAttribute.subList(4, 5));
		model.addAttribute("cameraSpecs", productAttribute.subList(5, 6));

		model.addAttribute("finalPrice" , (product.getPrice().subtract((product.getDiscount().divide(new BigDecimal(100))).multiply(product.getPrice()))).intValue());

		return "user/product-info";
	}
	
	
	@PostMapping("/add")
    public String addProduct(@ModelAttribute("newProduct") ProductSaveDTO newProduct , HttpSession session){
    	String token = (String)session.getAttribute("accessToken");

		ProductDTO product = new ProductDTO();
		
		product.setBrand(newProduct.getBrand());
		product.setDescription(newProduct.getDescription());
		product.setDiscount(newProduct.getDiscount());
		product.setImageUrl("..\\img\\products\\"+newProduct.getImageUrl());
		product.setName(newProduct.getName());
		product.setPrice(newProduct.getPrice());
		product.setStockQuantity(newProduct.getStockQuantity());
		product.setWarranty(newProduct.getWarranty());
		
		List<ProductAttributeDTO> attribute = new ArrayList<>();
		for(int i=0;i<16;i+=2) {
			ProductAttributeDTO obj = new ProductAttributeDTO();
			obj.setAttributeName(newProduct.getAttributes()[i]);
			obj.setAttributeValue(newProduct.getAttributes()[i+1]);
			
			attribute.add(obj);

		}
		
		
		product.setSubcategory(subcategoryService.getSubCategoryByName(newProduct.getSubcategory(),token));
		product.getSubcategory().setCategory(categoryService.getCategoryByName(newProduct.getCategory() , token));

		ProductDTO productInfo = productService.addProduct(product , token);
        
		productService.addProductAttributes(productInfo.getProductId(),attribute , token);
        return "redirect:/product";
    }
	
	@GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable long productId , HttpSession session){
    	String token = (String)session.getAttribute("accessToken");

		productService.deleteProduct(productId , token);
		return "redirect:/product";
    }
	
	@PostMapping("/edit/{productId}")
    public String editProduct(@ModelAttribute("newProduct") ProductSaveDTO newProduct ,@PathVariable long productId , Model model , HttpSession session){
    	String token = (String)session.getAttribute("accessToken");

        ProductDTO product = new ProductDTO();
		
		product.setBrand(newProduct.getBrand());
		product.setDescription(newProduct.getDescription());
		product.setDiscount(newProduct.getDiscount());
		if(product.getImageUrl() != null) {
			product.setImageUrl("..\\img\\products\\"+newProduct.getImageUrl());
		}
		else {
			product.setImageUrl("");

		}
		product.setName(newProduct.getName());
		product.setPrice(newProduct.getPrice());
		product.setStockQuantity(newProduct.getStockQuantity());
		product.setWarranty(newProduct.getWarranty());
		
		List<ProductAttributeDTO> attribute = new ArrayList<>();
		for(int i=0;i<newProduct.getAttributes().length;i+=2) {
			ProductAttributeDTO obj = new ProductAttributeDTO();
			obj.setAttributeName(newProduct.getAttributes()[i]);
			obj.setAttributeValue(newProduct.getAttributes()[i+1]);
			
			attribute.add(obj);

		}
		
		System.out.println(attribute);
		ProductDTO productInfo = productService.updateProduct(product , productId , token);
        
		productService.updateProductAttributes(productId,attribute , token);
		
		return "redirect:/product";
    }
}
