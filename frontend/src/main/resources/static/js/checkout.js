// Store checkout form data in localStorage when proceeding to final checkout
document.addEventListener('DOMContentLoaded', function() {
    // Check if we're on the checkout form page
    const checkoutForm = document.getElementById('checkout-form');
    if (checkoutForm) {
        const proceedButton = document.querySelector('.proceed-button');
        
        proceedButton.addEventListener('click', function(e) {
            // Get form data
            const firstName = document.getElementById('firstName').value;
            const lastName = document.getElementById('lastName').value;
            const email = document.getElementById('email').value;
            const phone = document.getElementById('phone').value;
            const address = document.getElementById('address').value;
            const city = document.getElementById('city').value;
            const state = document.getElementById('state').value;
            const zip = document.getElementById('zip').value;
            
            // Store in localStorage
            const checkoutData = {
                personal: {
                    firstName,
                    lastName,
                    email,
                    phone
                },
                shipping: {
                    address,
                    city,
                    state,
                    zip
                },
                selectedShipping: null
            };
            
            localStorage.setItem('checkoutData', JSON.stringify(checkoutData));
        });
    }
    
    // Check if we're on the final checkout page
    if (window.location.href.includes('checkout-final.html')) {
        loadCheckoutData();
    }
});

// Load checkout data on final checkout page
function loadCheckoutData() {
    const checkoutData = JSON.parse(localStorage.getItem('checkoutData') || '{}');
    
    // Fill in personal information
    if (checkoutData.personal) {
        document.getElementById('display-name').textContent = 
            `${checkoutData.personal.firstName} ${checkoutData.personal.lastName}`;
        document.getElementById('display-email').textContent = checkoutData.personal.email;
        document.getElementById('display-phone').textContent = checkoutData.personal.phone;
    }
    
    // Fill in shipping address
    if (checkoutData.shipping) {
        document.getElementById('display-address').textContent = checkoutData.shipping.address;
        document.getElementById('display-location').textContent = 
            `${checkoutData.shipping.city}, ${checkoutData.shipping.state} ${checkoutData.shipping.zip}`;
    }
    
    // Set up shipping option selection
    const shippingOptions = document.querySelectorAll('.shipping-option');
    shippingOptions.forEach(option => {
        option.addEventListener('click', function() {
            // Remove selected class from all options
            shippingOptions.forEach(opt => opt.classList.remove('selected'));
            
            // Add selected class to clicked option
            this.classList.add('selected');
            
            // Update shipping cost and total
            const shippingCost = parseFloat(this.getAttribute('data-cost'));
            document.getElementById('shipping-cost').textContent = `$${shippingCost.toFixed(2)}`;
            
            // Save selected shipping option to localStorage
            checkoutData.selectedShipping = {
                method: this.getAttribute('data-method'),
                cost: shippingCost
            };
            localStorage.setItem('checkoutData', JSON.stringify(checkoutData));
            
            // Update total
            updateTotal();
        });
    });
    
    // Select default shipping option (first one)
    if (shippingOptions.length > 0) {
        shippingOptions[0].click();
    }
    
    // Update subtotal from cart
    updateTotal();
}

// Calculate and update the total price
function updateTotal() {
    // For demo purposes using fixed subtotal, in a real app this would come from the cart
    const subtotal = 129.99;
    document.getElementById('subtotal').textContent = `$${subtotal.toFixed(2)}`;
    
    const shippingCostText = document.getElementById('shipping-cost').textContent;
    const shippingCost = parseFloat(shippingCostText.replace('$', '')) || 0;
    
    // Calculate tax (assuming 8% tax rate)
    const taxRate = 0.08;
    const tax = subtotal * taxRate;
    document.getElementById('tax').textContent = `$${tax.toFixed(2)}`;
    
    // Calculate total
    const total = subtotal + shippingCost + tax;
    document.getElementById('total').textContent = `$${total.toFixed(2)}`;
} 