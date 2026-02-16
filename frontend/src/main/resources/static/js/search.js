// Modern Search Functionality
document.addEventListener('DOMContentLoaded', function() {
	// Get search elements
	const searchContainers = document.querySelectorAll('.search-container');

	searchContainers.forEach(searchContainer => {
		const searchBar = searchContainer.querySelector('.search-bar');
		const searchClear = searchContainer.querySelector('.search-clear');
		const searchDropdown = searchContainer.querySelector('.search-dropdown');
		const searchDropdownClose = searchContainer.querySelector('.search-dropdown-close');
		const searchIcon = searchContainer.querySelector('.search-icon');
		const categoriesElements = searchContainer.querySelectorAll('.search-category');
		const suggestionItems = searchContainer.querySelectorAll('.search-suggestion-item');
		const productItems = searchContainer.querySelectorAll('.search-product-item');

		if (!searchBar) return;

		// Focus effect
		searchBar.addEventListener('focus', function() {
			searchContainer.classList.add('focused');
			openSearchDropdown();
		});

		searchBar.addEventListener('blur', function() {
			if (searchBar.value.length === 0) {
				searchContainer.classList.remove('focused');
			}
		});

		// Clear search
		if (searchClear) {
			searchClear.addEventListener('click', function(e) {
				e.preventDefault();
				searchBar.value = '';
				searchClear.style.opacity = '0';
				// Focus back on search bar
				searchBar.focus();
				resetSearchResults();
			});
		}

		// Close dropdown on outside click
		document.addEventListener('click', function(e) {
			if (!searchContainer.contains(e.target)) {
				closeSearchDropdown();
				searchContainer.classList.remove('focused');
			}
		});

		// Close dropdown on close button click
		if (searchDropdownClose) {
			searchDropdownClose.addEventListener('click', function(e) {
				e.preventDefault();
				closeSearchDropdown();
			});
		}

		// Clear button visibility on input
		searchBar.addEventListener('input', function() {
			if (this.value.length > 0) {
				searchClear.style.opacity = '1';
				searchProducts(this.value);
			} else {
				searchClear.style.opacity = '0';
				resetSearchResults();
			}
		});

		searchBar.addEventListener('keydown', function(event) {
			if (event.key === "Enter" && this.value.length > 0) {
				searchProduct(this.value);
			}
		});
		// Category click handler
		if (categoriesElements) {
			categoriesElements.forEach(category => {
				category.addEventListener('click', function() {
					const categoryText = this.textContent.trim();
					searchBar.value = categoryText;
					searchClear.style.opacity = '1';
					searchProducts(categoryText);

					// Add ripple effect
					const ripple = document.createElement('span');
					ripple.classList.add('ripple');
					this.appendChild(ripple);

					const rect = this.getBoundingClientRect();
					ripple.style.width = ripple.style.height = Math.max(rect.width, rect.height) + 'px';
					ripple.style.left = (event.clientX - rect.left - ripple.offsetWidth / 2) + 'px';
					ripple.style.top = (event.clientY - rect.top - ripple.offsetHeight / 2) + 'px';

					// Simulate form submission after a short delay
					window.location.href = `/products?category=${encodeURIComponent(categoryText)}&id=-1`;

					// Remove ripple
					setTimeout(() => {
						ripple.remove();
					}, 600);
				});
			});
		}

		// Suggestion click handler
		suggestionItems.forEach(item => {
			item.addEventListener('click', function() {
				const suggestionText = this.querySelector('.search-suggestion-text').textContent.trim();
				searchBar.value = suggestionText;
				searchClear.style.opacity = '1';
				searchProducts(suggestionText);
				// Simulate form submission after a short delay
				window.location.href = `/products?category=${encodeURIComponent(suggestionText)}&id=-1`;
			});
		});

		// Product click handler
		productItems.forEach(item => {
			item.addEventListener('click', function() {
				const productId = this.getAttribute('data-product-id');
				// Navigate to product page
				window.location.href = `/products?id=${encodeURIComponent(productId)}&category=-1`;
			});
		});

		// Search submit handler
		const searchForm = searchContainer.closest('.search-form');
		if (searchForm) {
			searchForm.addEventListener('submit', function(e) {
				e.preventDefault();
				const searchQuery = searchBar.value.trim();
				if (searchQuery) {
					// Add to recent searches (using localStorage)
					addToRecentSearches(searchQuery);

					// Navigate to search results page
					window.location.href = `/products?category=${encodeURIComponent(searchQuery)}&id=-1`;
				}
			});
		}

		// Function to open search dropdown
		function openSearchDropdown() {
			if (searchDropdown) {
				searchDropdown.classList.add('active');
				// Update recent searches
				updateRecentSearches();
				// Show popular products
				updatePopularProducts();
			}
		}

		// Function to close search dropdown
		function closeSearchDropdown() {
			if (searchDropdown) {
				searchDropdown.classList.remove('active');
			}
		}

		// Function to add to recent searches
		function addToRecentSearches(search) {
			let recentSearches = JSON.parse(localStorage.getItem('recentSearches')) || [];

			// Remove if already exists
			recentSearches = recentSearches.filter(item => item !== search);

			// Add to beginning
			recentSearches.unshift(search);

			// Limit to 5
			if (recentSearches.length > 5) {
				recentSearches = recentSearches.slice(0, 5);
			}

			// Save to localStorage
			localStorage.setItem('recentSearches', JSON.stringify(recentSearches));
		}

		// Function to update recent searches
		function updateRecentSearches() {
			const recentSearchesList = searchContainer.querySelector('.recent-searches-list');
			if (recentSearchesList) {
				recentSearchesList.innerHTML = '';

				const recentSearches = JSON.parse(localStorage.getItem('recentSearches')) || [];

				if (recentSearches.length === 0) {
					recentSearchesList.innerHTML = '<div class="no-recent-searches">No recent searches</div>';
					return;
				}

				recentSearches.forEach(search => {
					const li = document.createElement('li');
					li.className = 'search-suggestion-item';
					li.innerHTML = `
                        <div class="search-suggestion-icon">
                            <i class="fas fa-history"></i>
                        </div>
                        <div class="search-suggestion-text">${search}</div>
                    `;

					li.addEventListener('click', function() {
						searchBar.value = search;
						searchClear.style.opacity = '1';
						// Simulate form submission after a short delay
						window.location.href = `/products?category=${encodeURIComponent(search)}&id=-1`;
					});

					recentSearchesList.appendChild(li);
				});
			}
		}
		let products;
		// Product Database (Would typically come from a backend API)

		// Function to update popular products
		async function updatePopularProducts() {
			let token = sessionStorage.getItem("accessToken");

			let response = await fetch('http://localhost:8080/api/products/popularproducts', {
				method: "GET",
				headers: {
					"Content-Type": "application/json",
					"Authorization": `Bearer ${token}`
				},
			})

			products = await response.json();

			const popularProductsContainer = searchContainer.querySelector('.search-popular-products');
			if (popularProductsContainer) {
				popularProductsContainer.innerHTML = '';
				// Show first 4 products as popular
				products.forEach(product => {
					const productEl = document.createElement('div');
					productEl.className = 'search-product-item';
					productEl.setAttribute('data-product-id', product.productId);
					productEl.innerHTML = `
                        <img src="${product.imageUrl}" alt="${product.name}" class="search-product-img">
                        <div class="search-product-info">
                            <div class="search-product-name">${product.name}</div>
                            <div class="search-product-price">₹${formatPrice(product.price)}</div>
                        </div>
                    `;

					productEl.addEventListener('click', function() {
						window.location.href = `/product/${encodeURIComponent(product.productId)}`;
					});

					popularProductsContainer.appendChild(productEl);
				});
			}
		}

		// Function to search products
		function searchProducts(query) {
			if (!query || query.length < 2) return;

			query = query.toLowerCase();

			// Filter products
			const filteredProducts = products.filter(product => {
				return product.name.toLowerCase().includes(query) ||
					product.subcategory.category.name.toLowerCase().includes(query);
			});

			// Update search results
			updateSearchResults(filteredProducts, query);
		}

		function searchProduct(query) {
			window.location.href = `/products?id=-1&category=${query}`;
		}


		// Function to update search results
		function updateSearchResults(filteredProducts, query) {
			const popularProductsContainer = searchContainer.querySelector('.search-popular-products');
			if (popularProductsContainer) {
				popularProductsContainer.innerHTML = '';

				if (filteredProducts.length === 0) {
					popularProductsContainer.innerHTML = `
                        <div class="search-no-results">
                            <i class="fas fa-search"></i>
                            No products found for "${query}"
                        </div>
                    `;
					return;
				}

				// Show filtered products
				filteredProducts.slice(0, 4).forEach(product => {
					const productEl = document.createElement('div');
					productEl.className = 'search-product-item';
					productEl.setAttribute('data-product-id', product.productId);

					productEl.innerHTML = `
                        <img src="${product.imageUrl}" alt="${product.name}" class="search-product-img">
                        <div class="search-product-info">
                            <div class="search-product-name">${highlightText(product.name, query)}</div>
                            <div class="search-product-price">₹${formatPrice(product.price)}</div>
                        </div>
                    `;

					productEl.addEventListener('click', function() {
						window.location.href = `/products?id=${encodeURIComponent(product.productId)}&category=-1`;
					});

					popularProductsContainer.appendChild(productEl);
				});
			}
		}

		// Function to reset search results
		function resetSearchResults() {
			if (searchDropdown && searchDropdown.classList.contains('active')) {
				updatePopularProducts();
			}
		}

		// Function to highlight text
		function highlightText(text, query) {
			const regex = new RegExp(`(${query})`, 'gi');
			return text.replace(regex, '<span class="highlighted">$1</span>');
		}

		// Function to format price
		function formatPrice(price) {
			return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
	});
}); 