document.addEventListener('DOMContentLoaded', function() {
    // Load user details from sessionStorage if available
    loadUserDetails();
    
    // Setup delivery option change listeners
    setupDeliveryOptions();
});

/**
 * Load user details from sessionStorage
 */
function loadUserDetails() {
    // Personal information
    const fullName = sessionStorage.getItem('fullName') || 'John Doe';
    const email = sessionStorage.getItem('email') || 'john.doe@example.com';
    const phone = sessionStorage.getItem('phone') || '+91 9876543210';
    
    // Update display
    document.getElementById('review-fullname').textContent = fullName;
    document.getElementById('review-email').textContent = email;
    document.getElementById('review-phone').textContent = phone;
    
    // Shipping address
    const address = sessionStorage.getItem('address') || '123 Main Street';
    const apartment = sessionStorage.getItem('apartment') || 'Apt 4B';
    const city = sessionStorage.getItem('city') || 'Mumbai';
    const state = sessionStorage.getItem('state') || 'Maharashtra';
    const pincode = sessionStorage.getItem('pincode') || '400001';
    
    // Update display
    document.getElementById('review-address').textContent = address;
    document.getElementById('review-apt').textContent = apartment;
    document.getElementById('review-city').textContent = city;
    document.getElementById('review-state').textContent = state;
    document.getElementById('review-pincode').textContent = pincode;
}

/**
 * Setup delivery option change handlers
 */
function setupDeliveryOptions() {
    const standardDelivery = document.getElementById('standard');
    const expressDelivery = document.getElementById('express');
    const shippingCostElement = document.getElementById('shipping-cost');
    const totalCostElement = document.getElementById('total-cost');
    
    // Base price from the product (without shipping)
    const basePrice = 110999;
    
    // Update total when delivery option changes
    function updateTotal() {
        let shippingCost = 0;
        let totalCost = basePrice;
        
        if (expressDelivery.checked) {
            shippingCost = 299;
            totalCost += shippingCost;
            shippingCostElement.textContent = '₹299';
        } else {
            shippingCostElement.textContent = 'Free';
        }
        
        totalCostElement.textContent = '₹' + totalCost.toLocaleString('en-IN');
    }
    
    // Add event listeners to radio buttons
    standardDelivery.addEventListener('change', updateTotal);
    expressDelivery.addEventListener('change', updateTotal);
    
    // Initialize with default values
    updateTotal();
    
    // Save delivery option when proceed button is clicked
    const proceedButton = document.querySelector('.proceed-button');
    if (proceedButton) {
        proceedButton.addEventListener('click', function() {
            // Save delivery option to sessionStorage
            sessionStorage.setItem('deliveryOption', expressDelivery.checked ? 'express' : 'standard');
            sessionStorage.setItem('shippingCost', expressDelivery.checked ? '299' : '0');
            sessionStorage.setItem('totalCost', basePrice + (expressDelivery.checked ? 299 : 0));
        });
    }
} 