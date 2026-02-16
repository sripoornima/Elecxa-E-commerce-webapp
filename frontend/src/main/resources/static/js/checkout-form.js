document.addEventListener('DOMContentLoaded', function() {
    // Initialize the form field interactions
    initFormFields();
    
    // Initialize form validation
    initFormValidation();
    
    // Initialize form submission
    initFormSubmission();
});

/**
 * Initialize form field interactions
 */
function initFormFields() {
    const formFields = document.querySelectorAll('.form-field');
    
    formFields.forEach(field => {
        const input = field.querySelector('.input-field');
        if (!input) return;
        
        // Add focus event listener to show the floating label
        input.addEventListener('focus', function() {
            field.classList.add('focused');
        });
        
        // Add blur event listener to hide the floating label if the input is empty
        input.addEventListener('blur', function() {
            if (this.value.trim() === '') {
                field.classList.remove('focused');
            }
            
            // Validate the field on blur
            validateField(field);
        });
        
        // Initialize fields that might have values
        if (input.value.trim() !== '') {
            field.classList.add('focused');
        }
    });
}

/**
 * Initialize form validation
 */
function initFormValidation() {
    const form = document.getElementById('checkout-form');
    const inputs = form.querySelectorAll('.input-field');
    
    inputs.forEach(input => {
        input.addEventListener('input', function() {
            const field = this.closest('.form-field');
            if (field) {
                // Remove error class when the user starts typing
                field.classList.remove('error');
                
                // Clear error message
                const errorMessage = field.querySelector('.error-message');
                if (errorMessage) {
                    errorMessage.textContent = '';
                }
            }
        });
    });
}

/**
 * Initialize form submission
 */
function initFormSubmission() {
    const form = document.getElementById('checkout-form');
    
    form.addEventListener('submit', function(event) {
        // Prevent form submission
        event.preventDefault();
        
        // Validate all required fields
        const isValid = validateAllFields();
        
        // If the form is valid, redirect to the next page
        if (isValid) {
            // Store form data in sessionStorage
            saveFormData();
            
            // Get the next page URL
            const proceedButton = document.querySelector('.proceed-button');
            const nextPageUrl = proceedButton.getAttribute('href');
            
            // Navigate to the next page
            window.location.href = nextPageUrl;
        }
    });
}

/**
 * Validate a specific form field
 * @param {HTMLElement} field - The field to validate
 * @returns {boolean} - Whether the field is valid
 */
function validateField(field) {
    const input = field.querySelector('.input-field');
    if (!input) return true;
    
    const isRequired = field.querySelector('.field-label.required') !== null;
    if (!isRequired && input.value.trim() === '') return true;
    
    // Get or create error message element
    let errorMessage = field.querySelector('.error-message');
    if (!errorMessage) {
        errorMessage = document.createElement('div');
        errorMessage.className = 'error-message';
        field.appendChild(errorMessage);
    }
    
    // Clear previous error
    field.classList.remove('error');
    errorMessage.textContent = '';
    
    // Validate required fields
    if (isRequired && input.value.trim() === '') {
        field.classList.add('error');
        errorMessage.textContent = 'This field is required';
        return false;
    }
    
    // Validate specific fields
    const inputId = input.id;
    
    // Email validation
    if (inputId === 'email' && input.value.trim() !== '') {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(input.value.trim())) {
            field.classList.add('error');
            errorMessage.textContent = 'Please enter a valid email address';
            return false;
        }
    }
    
    // Phone validation
    if (inputId === 'phone' && input.value.trim() !== '') {
        const phoneRegex = /^(\+\d{1,3}[ -]?)?\d{10}$/;
        if (!phoneRegex.test(input.value.trim().replace(/\s/g, ''))) {
            field.classList.add('error');
            errorMessage.textContent = 'Please enter a valid phone number';
            return false;
        }
    }
    
    // Pincode validation
    if (inputId === 'pincode' && input.value.trim() !== '') {
        const pincodeRegex = /^\d{6}$/;
        if (!pincodeRegex.test(input.value.trim())) {
            field.classList.add('error');
            errorMessage.textContent = 'Please enter a valid 6-digit pincode';
            return false;
        }
    }
    
    return true;
}

/**
 * Validate all form fields
 * @returns {boolean} - Whether all fields are valid
 */
function validateAllFields() {
    const fields = document.querySelectorAll('.form-field');
    let isValid = true;
    
    fields.forEach(field => {
        if (!validateField(field)) {
            isValid = false;
        }
    });
    
    return isValid;
}

/**
 * Save form data to sessionStorage
 */
function saveFormData() {
    const form = document.getElementById('checkout-form');
    const inputs = form.querySelectorAll('.input-field');
    
    inputs.forEach(input => {
        sessionStorage.setItem(input.id, input.value.trim());
    });
} 