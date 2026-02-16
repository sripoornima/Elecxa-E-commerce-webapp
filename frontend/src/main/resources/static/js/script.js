// Wait for the DOM to be fully loaded

async function fillProductDetails(productId) {

	const form = document.getElementById('editProductForm');
	let token = sessionStorage.getItem("accessToken");
	form.action = "/product/edit/" + productId;
	// Fetch product details from the backend using the productId
	let response = await fetch(`http://localhost:8080/api/products/info/${productId}`, {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
			"Authorization": `Bearer ${token}`
		}
	});
	let data = await response.json(); // Assuming the response is JSON

	let product = data;
		// Populate modal fields with product data

		document.getElementById('editproductName').value = product.name;
		document.getElementById('editproductPrice').value = product.price;
		document.getElementById('editproductCategory').value = product.subcategory.category.name;
		document.getElementById('editproductSubcategory').value = product.subcategory.name;
		document.getElementById('editproductDiscount').value = product.discount;
		document.getElementById('editproductBrand').value = product.brand;
		document.getElementById('editproductWarranty').value = product.warranty;
		document.getElementById('editproductStock').value = product.stockQuantity;
		document.getElementById('editproductDescription').value = product.description;

		// Assuming productAttributes is an array of objects with name and value

	

	token = sessionStorage.getItem("accessToken");

	response = await fetch(`http://localhost:8080/api/attributes/product/${productId}`, {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
			"Authorization": `Bearer ${token}`
		}
	});

	let attributes = await response.json();

	attributes.forEach((attribute, index) => {
		document.getElementById(`editproductAttributeName${index}`).value = attribute.attributeName;
		document.getElementById(`editproductAttributeValue${index}`).value = attribute.attributeValue;
	});

}

document.addEventListener('DOMContentLoaded', function() {
	// Mobile menu toggle functionality


	const categoryFilter = document.getElementById("categoryFilter");
	if (categoryFilter) {
		categoryFilter.addEventListener('change', function() {
			window.location.href = "/product/category/" + this.value;

		});
	} else {
		console.error("categoryFilter not found!");
	}

	const subcategoryFilter = document.getElementById("subcategoryFilter");
	if (subcategoryFilter) {
		subcategoryFilter.addEventListener('change', function() {

			window.location.href = "/product/subcategory/" + this.value;

		});
	} else {
		console.error("categoryFilter not found!");
	}

	const mobileMenuToggle = document.createElement('div');
	mobileMenuToggle.className = 'mobile-menu-toggle';
	mobileMenuToggle.innerHTML = '<i class="fas fa-bars"></i>';

	const header = document.querySelector('header .container');
	const nav = document.querySelector('nav');

	header.insertBefore(mobileMenuToggle, nav);

	mobileMenuToggle.addEventListener('click', function() {
		nav.classList.toggle('active');
	});

	// Product image hover effect
	const productImages = document.querySelectorAll('.grid-item img');

	productImages.forEach(img => {
		img.addEventListener('mouseenter', function() {
			this.style.transform = 'scale(1.05)';
			this.style.transition = 'transform 0.3s ease';
		});

		img.addEventListener('mouseleave', function() {
			this.style.transform = 'scale(1)';
		});
	});

	// Smooth scrolling for anchor links
	document.querySelectorAll('a[href^="#"]').forEach(anchor => {
		anchor.addEventListener('click', function(e) {
			e.preventDefault();

			const targetId = this.getAttribute('href');
			if (targetId === '#') return;

			const targetElement = document.querySelector(targetId);
			if (targetElement) {
				window.scrollTo({
					top: targetElement.offsetTop - 100,
					behavior: 'smooth'
				});
			}
		});
	});

	// Add event listener for the 'Explore More' button in the brand banner
	const exploreMoreBtn = document.getElementById('explore-more-btn');
	if (exploreMoreBtn) {
		exploreMoreBtn.addEventListener('click', function() {
			const categoriesSection = document.getElementById('shop-categories');
			if (categoriesSection) {
				categoriesSection.scrollIntoView({
					behavior: 'smooth'
				});
			}
		});
	}

	// Note: Hero slider functionality is now handled by Swiper.js
	// which is initialized in the index.html file
});

// The categories functionality has been moved to the products-page.html file

