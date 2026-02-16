document.addEventListener('DOMContentLoaded', function() {
    // Initialize the payment method selection
    initPaymentMethodSelection();
    
    // Initialize form validation
    initFormValidation();
    
    // Initialize payment form submission
    initPaymentFormSubmission();
});

/**
 * Initialize payment method selection functionality
 */
function initPaymentMethodSelection() {
    const paymentMethods = document.querySelectorAll('.payment-method-header');
    const hiddenRadios = document.querySelectorAll('.hidden-radio');
    
    // Add click event listener to each payment method header
    paymentMethods.forEach(header => {
        header.addEventListener('click', function() {
            // Get the payment method value from the data attribute
            const paymentMethod = this.getAttribute('data-payment-method');
            
            // Find and select the corresponding radio button
            const radio = document.querySelector(`#${paymentMethod}-method`);
            if (radio) {
                radio.checked = true;
                
                // Trigger the change event to update the UI
                const event = new Event('change');
                radio.dispatchEvent(event);
            }
            
            // Update the UI to show the selected payment method
            updatePaymentMethodUI();
            
            // Update the order total based on the selected payment method
            updateOrderTotal();
        });
    });
    
    // Add change event listener to each hidden radio button
    hiddenRadios.forEach(radio => {
        radio.addEventListener('change', function() {
            updatePaymentMethodUI();
            updateOrderTotal();
        });
    });
    
    // Initialize the UI based on the initially selected payment method
    updatePaymentMethodUI();
}

/**
 * Update the payment method UI based on the selected radio button
 */
function updatePaymentMethodUI() {
    const paymentMethods = document.querySelectorAll('.payment-method');
    
    // Remove selected class from all payment methods
    paymentMethods.forEach(method => {
        method.classList.remove('selected');
    });
    
    // Find the selected radio button
    const selectedRadio = document.querySelector('input[name="paymentMethod"]:checked');
    if (selectedRadio) {
        // Get the payment method value
        const paymentMethod = selectedRadio.value;
        
        // Find the payment method element and add the selected class
        const paymentMethodElement = document.querySelector(`.payment-method-header[data-payment-method="${paymentMethod}"]`).closest('.payment-method');
        if (paymentMethodElement) {
            paymentMethodElement.classList.add('selected');
        }
    }
}

/**
 * Update the order total based on the selected payment method
 */
function updateOrderTotal() {
    const selectedPaymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
    const orderTotalElements = document.querySelectorAll('.order-total-value');
    const orderSummaryTotalElements = document.querySelectorAll('.order-summary-total-value');
    const codFeeElements = document.querySelectorAll('.order-summary-item[th\\:if="${paymentForm.paymentMethod == \'cod\'}"]');
    
    // Show/hide COD fee based on the selected payment method
    if (selectedPaymentMethod === 'cod') {
        codFeeElements.forEach(element => {
            element.style.display = 'flex';
        });
    } else {
        codFeeElements.forEach(element => {
            element.style.display = 'none';
        });
    }
    
    // Show the appropriate total based on the selected payment method
    orderTotalElements.forEach(element => {
        const withoutCodElement = element.querySelector('span[th\\:if="${paymentForm.paymentMethod != \'cod\'}"]');
        const withCodElement = element.querySelector('span[th\\:if="${paymentForm.paymentMethod == \'cod\'}"]');
        
        if (withoutCodElement && withCodElement) {
            if (selectedPaymentMethod === 'cod') {
                withoutCodElement.style.display = 'none';
                withCodElement.style.display = 'inline';
            } else {
                withoutCodElement.style.display = 'inline';
                withCodElement.style.display = 'none';
            }
        }
    });
    
    // Do the same for order summary total elements
    orderSummaryTotalElements.forEach(element => {
        const withoutCodElement = element.querySelector('span[th\\:if="${paymentForm.paymentMethod != \'cod\'}"]');
        const withCodElement = element.querySelector('span[th\\:if="${paymentForm.paymentMethod == \'cod\'}"]');
        
        if (withoutCodElement && withCodElement) {
            if (selectedPaymentMethod === 'cod') {
                withoutCodElement.style.display = 'none';
                withCodElement.style.display = 'inline';
            } else {
                withoutCodElement.style.display = 'inline';
                withCodElement.style.display = 'none';
            }
        }
    });
}

/**
 * Initialize form validation for the payment form
 */
function initFormValidation() {
    const paymentForm = document.getElementById('payment-form');
    const cardNumberInput = document.getElementById('cardNumber');
    const cardNameInput = document.getElementById('cardName');
    const expiryDateInput = document.getElementById('expiryDate');
    const cvvInput = document.getElementById('cvv');
    const upiIdInput = document.getElementById('upiId');
    const bankNameSelect = document.getElementById('bankName');
    
    // Add input event listener to all form fields for validation
    const formFields = [cardNumberInput, cardNameInput, expiryDateInput, cvvInput, upiIdInput, bankNameSelect];
    formFields.forEach(field => {
        if (field) {
            field.addEventListener('input', function() {
                validateField(this);
            });
            
            field.addEventListener('blur', function() {
                validateField(this);
            });
        }
    });
    
    // Add specific format validation for card number
    if (cardNumberInput) {
        cardNumberInput.addEventListener('input', function() {
            this.value = this.value.replace(/[^\d]/g, '').replace(/(.{4})/g, '$1 ').trim();
            validateField(this);
        });
    }
    
    // Add specific format validation for expiry date
    if (expiryDateInput) {
        expiryDateInput.addEventListener('input', function() {
            this.value = this.value.replace(/[^\d]/g, '');
            if (this.value.length > 2) {
                this.value = this.value.substring(0, 2) + '/' + this.value.substring(2, 4);
            }
            validateField(this);
        });
    }
    
    // Add specific format validation for CVV
    if (cvvInput) {
        cvvInput.addEventListener('input', function() {
            this.value = this.value.replace(/[^\d]/g, '').substring(0, 4);
            validateField(this);
        });
    }
}

/**
 * Validate a specific form field
 * @param {HTMLElement} field - The field to validate
 * @returns {boolean} - Whether the field is valid
 */
function validateField(field) {
    // Get the parent form-field element
    const formField = field.closest('.form-field');
    if (!formField) return true;
    
    // Get the error message element
    let errorElement = formField.querySelector('.error-message');
    if (!errorElement) {
        errorElement = document.createElement('div');
        errorElement.className = 'error-message';
        formField.appendChild(errorElement);
    }
    
    // Reset error state
    formField.classList.remove('error');
    errorElement.textContent = '';
    
    // If the field is empty and required, show an error
    if (field.required && !field.value.trim()) {
        formField.classList.add('error');
        errorElement.textContent = 'This field is required';
        return false;
    }
    
    // Specific validation for each field type
    if (field.id === 'cardNumber' && field.value.trim()) {
        const cardNumber = field.value.replace(/\s/g, '');
        if (!/^\d{13,19}$/.test(cardNumber)) {
            formField.classList.add('error');
            errorElement.textContent = 'Please enter a valid card number';
            return false;
        }
    } else if (field.id === 'expiryDate' && field.value.trim()) {
        const expiry = field.value.trim();
        if (!/^(0[1-9]|1[0-2])\/\d{2}$/.test(expiry)) {
            formField.classList.add('error');
            errorElement.textContent = 'Please enter a valid expiry date (MM/YY)';
            return false;
        }
        
        // Check if the card is expired
        const [month, year] = expiry.split('/');
        const expiryDate = new Date(2000 + parseInt(year), parseInt(month) - 1, 1);
        const today = new Date();
        if (expiryDate < today) {
            formField.classList.add('error');
            errorElement.textContent = 'Card has expired';
            return false;
        }
    } else if (field.id === 'cvv' && field.value.trim()) {
        if (!/^\d{3,4}$/.test(field.value.trim())) {
            formField.classList.add('error');
            errorElement.textContent = 'Please enter a valid CVV';
            return false;
        }
    } else if (field.id === 'upiId' && field.value.trim()) {
        if (!/^[\w.-]+@[\w.-]+$/.test(field.value.trim())) {
            formField.classList.add('error');
            errorElement.textContent = 'Please enter a valid UPI ID';
            return false;
        }
    }
    
    return true;
}

/**
 * Initialize payment form submission handling
 */
function initPaymentFormSubmission() {
    const paymentForm = document.getElementById('payment-form');
    if (!paymentForm) return;
    
    paymentForm.addEventListener('submit', function(event) {
        // Get the selected payment method
        const selectedPaymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
        
        // Validate the required fields based on the selected payment method
        let isValid = true;
        
        if (selectedPaymentMethod === 'card') {
            isValid = validateField(document.getElementById('cardNumber')) && 
                     validateField(document.getElementById('cardName')) && 
                     validateField(document.getElementById('expiryDate')) && 
                     validateField(document.getElementById('cvv'));
        } else if (selectedPaymentMethod === 'upi') {
            isValid = validateField(document.getElementById('upiId'));
        } else if (selectedPaymentMethod === 'netbanking') {
            isValid = validateField(document.getElementById('bankName'));
        }
        
        // If the form is not valid, prevent submission
        if (!isValid) {
            event.preventDefault();
        }
    });
} 