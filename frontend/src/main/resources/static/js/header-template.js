// Function to initialize the header search bar on all pages
function initializeSearchBar() {
    document.addEventListener('DOMContentLoaded', function() {
        // Get the header-center element
        const headerCenter = document.querySelector('.header-center');
        if (!headerCenter) return;
        
        // Check if the search container already exists
        if (headerCenter.querySelector('.search-container')) return;
        
        // Remove the old search overlap element if it exists
        const oldSearchElement = headerCenter.querySelector('.overlap');
        if (oldSearchElement) {
            oldSearchElement.remove();
        }
        
        // Create the modern search bar
        const searchContainer = document.createElement('div');
        searchContainer.className = 'search-container';
        searchContainer.innerHTML = `
            <form class="search-form">
                <i class="fas fa-search search-icon"></i>
                <input type="text" class="search-bar" placeholder="Search by Categories, Products">
                <i class="fas fa-times search-clear"></i>
            </form>
            
            <!-- Search Dropdown -->
            <div class="search-dropdown">
                <div class="search-dropdown-header">
                    <h3>What are you looking for?</h3>
                    <span class="search-dropdown-close"><i class="fas fa-times"></i></span>
                </div>
                
                <!-- Categories Section -->
                <div class="search-section">
                    <h4 class="search-section-title">Popular Categories</h4>
                    <div class="search-categories">
                        <div class="search-category">Mobile Phones</div>
                        <div class="search-category">Laptops</div>
                        <div class="search-category">Televisions</div>
                        <div class="search-category">Audio Devices</div>
                        <div class="search-category">Wearables</div>
                    </div>
                </div>
                
                <!-- Recent Searches Section -->
                <div class="search-section">
                    <h4 class="search-section-title">Recent Searches</h4>
                    <ul class="search-suggestions-list recent-searches-list">
                        <li class="search-suggestion-item">
                            <div class="search-suggestion-icon">
                                <i class="fas fa-history"></i>
                            </div>
                            <div class="search-suggestion-text">samsung galaxy</div>
                        </li>
                        <li class="search-suggestion-item">
                            <div class="search-suggestion-icon">
                                <i class="fas fa-history"></i>
                            </div>
                            <div class="search-suggestion-text">wireless headphones</div>
                        </li>
                    </ul>
                </div>
                
                <!-- Popular Products Section -->
                <div class="search-section">
                    <h4 class="search-section-title">Popular Products</h4>
                    <div class="search-popular-products">
                        <!-- Will be populated via JavaScript -->
                    </div>
                </div>
                
                <!-- Search Results Container -->
                <div class="search-results-container">
                    <!-- Will be filled with search results -->
                </div>
            </div>
        `;
        
        // Insert the new search container after the menu div
        const menuDiv = headerCenter.querySelector('.div');
        if (menuDiv) {
            menuDiv.insertAdjacentElement('afterend', searchContainer);
        } else {
            headerCenter.appendChild(searchContainer);
        }
        
        // Add the search CSS if it's not already added
        if (!document.querySelector('link[href="../css/search.css"]')) {
            const searchCSS = document.createElement('link');
            searchCSS.rel = 'stylesheet';
            searchCSS.href = '../css/search.css';
            document.head.appendChild(searchCSS);
        }
        
        // Add the search JS if it's not already added
        if (!document.querySelector('script[src="../js/search.js"]')) {
            const searchJS = document.createElement('script');
            searchJS.src = '../js/search.js';
            document.body.appendChild(searchJS);
        }
    });
}

// Run the initializer
initializeSearchBar(); 