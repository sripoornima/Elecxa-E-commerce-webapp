document.addEventListener('DOMContentLoaded', function() {
	// Load user details from sessionStorage if available
	loadUserData();

	// Tab switching functionality
	const tabs = document.querySelectorAll('.profile-tabs .tab');
	const profileForm = document.querySelector('.profile-form-container');
	const ordersContainer = document.querySelector('.orders-container');
	const ordersList = document.querySelector('.orders-list');
	const wishList = document.querySelector('.wishlist-container');
	let modal = document.getElementById('trackOrderModal');
		
		modal.style.display = 'none';
	// Hide orders container by default
	if (ordersContainer) {
		ordersContainer.style.display = 'none';
	}

	if (wishList) {
		wishList.style.display = 'none';
	}

	tabs.forEach(tab => {
		tab.addEventListener('click', function() {
			// Remove active class from all tabs
			tabs.forEach(t => t.classList.remove('active'));

			// Add active class to clicked tab
			this.classList.add('active');

			// Show/hide appropriate content
			const tabName = this.textContent.toLowerCase().trim();
			console.log(tabName);
			if (tabName.includes('my')) {
				if (profileForm) profileForm.style.display = 'none';
				if (wishList) wishList.style.display = 'none';
				if (ordersContainer) ordersContainer.style.display = 'block';
				if (ordersList) ordersList.style.display = 'block';
				modal.style.display = 'none';

			} else if (tabName == 'wishlist') {
				if (profileForm) profileForm.style.display = 'none';
				if (wishList) wishList.style.display = 'block';
				if (ordersContainer) ordersContainer.style.display = 'none';
				if (ordersList) ordersList.style.display = 'none';
				modal.style.display = 'none';

			} else {
				if (profileForm) profileForm.style.display = 'block';
				if (wishList) wishList.style.display = 'none';
				if (ordersContainer) ordersContainer.style.display = 'none';
				if (ordersList) ordersList.style.display = 'none';
				modal.style.display = 'none';

			}
		});
	});

	// Initialize order filtering
	const filterSelect = document.querySelector('.filter-select');
	const orderCards = document.querySelectorAll('.order-card');
	const ordersSubtitle = document.querySelector('.orders-subtitle');

	if (filterSelect) {
		filterSelect.addEventListener('change', function() {
			const selectedStatus = this.value;
			let visibleCount = 0;

			orderCards.forEach(card => {
				const status = card.querySelector('.order-status').textContent.toLowerCase();

				if (selectedStatus === 'all' || status === selectedStatus) {
					card.style.display = 'block';
					visibleCount++;
				} else {
					card.style.display = 'none';
				}
			});

			// Update orders count
			if (ordersSubtitle) {
				ordersSubtitle.textContent = `${visibleCount} orders found`;
			}
		});
	}

	// Set up form submission
	setupFormSubmission();

	// Setup floating labels
	setupFloatingLabels();
});

/**
 * Load user data from sessionStorage and populate form fields
 */
function loadUserData() {
	// Check if user data exists in sessionStorage
	const userData = JSON.parse(sessionStorage.getItem('userData')) || {};

	// Populate personal details if available
	if (userData.personalInfo) {
		const personalInfo = userData.personalInfo;

		// Fill in form fields with available data
		document.getElementById('fullname').value = personalInfo.fullName || '';
		document.getElementById('email').value = personalInfo.email || '';
		document.getElementById('phone').value = personalInfo.phone || '';
	}

	// Populate shipping address if available
	if (userData.shippingAddress) {
		const address = userData.shippingAddress;

		// Fill in form fields with available data
		document.getElementById('address').value = address.streetAddress || '';
		document.getElementById('apt').value = address.apartment || '';
		document.getElementById('pincode').value = address.pincode || '';
		document.getElementById('city').value = address.city || '';
		document.getElementById('state').value = address.state || '';
	}

	// Trigger input events to adjust labels for filled fields
	document.querySelectorAll('.input-field').forEach(input => {
		if (input.value) {
			const event = new Event('input', { bubbles: true });
			input.dispatchEvent(event);
		}
	});
}

/**
 * Set up form submission to save user data
 */
async function setupFormSubmission() {
	const editButton = document.querySelector('.edit-button');

	editButton.addEventListener('click', async function() {
		// Get form values
		const firstName = document.getElementById('fullname').value.split(" ")[0];
		const lastName = document.getElementById('fullname').value.split(" ")[1];

		const email = document.getElementById('email').value;
		const phone = document.getElementById('phone').value;
		const address = document.getElementById('address').value;
		const apartment = document.getElementById('apt').value;
		const pincode = document.getElementById('pincode').value;
		const city = document.getElementById('city').value;
		const state = document.getElementById('state').value;

		// Validate required fields
		if (!firstName || !lastName || !email || !phone || !address || !pincode || !city || !state) {
			alert('Please fill in all required fields');
			return;
		}

		// Create user data object
		const userData = {
			"firstName": firstName,
			"lastName": lastName,
			"email": email,
			"phone": phone
		}

		const userAddress = {
			"streetAddress": address,
			"landmark": apartment,
			"pincode": pincode,
			"city": city,
			"state": state
		}


		// Save to sessionStorage
		sessionStorage.setItem('userData', JSON.stringify(userData));
		let userId = sessionStorage.getItem("userId");

		let token = sessionStorage.getItem("accessToken");

		await fetch(`/update?id=${userId}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
				"Authorization": `Bearer ${token}`

			},
			body: JSON.stringify(userData)
		})

		await fetch(`/updateaddress?id=1`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
				"Authorization": `Bearer ${token}`

			},
			body: JSON.stringify(userAddress)
		})
	});

}

/**
 * Set up floating labels behavior
 */
function setupFloatingLabels() {
	const inputFields = document.querySelectorAll('.input-field');

	// Add focus and blur events to each input field
	inputFields.forEach(input => {
		// Function to handle focus/blur/input events
		const handleInputState = () => {
			const field = input.closest('.form-field');
			if (input.value.trim() !== '') {
				field.classList.add('filled');
			} else {
				field.classList.remove('filled');
			}
		};

		// Add focus event
		input.addEventListener('focus', () => {
			input.closest('.form-field').classList.add('focused');
		});

		// Add blur event
		input.addEventListener('blur', () => {
			input.closest('.form-field').classList.remove('focused');
			handleInputState();
		});

		// Add input event
		input.addEventListener('input', handleInputState);

		// Initialize state - apply filled class if input has value
		handleInputState();
	});

	// Also check on page load to ensure fields with values have proper styling
	window.addEventListener('load', () => {
		inputFields.forEach(input => {
			const field = input.closest('.form-field');
			if (input.value.trim() !== '') {
				field.classList.add('filled');
				// Ensure the label is positioned correctly
				const label = field.querySelector('label');
				if (label) {
					label.style.top = '-9px';
					label.style.left = '12px';
					label.style.fontSize = '12px';
					label.style.background = 'white';
				}
			}
		});
	});
}

// Track Order Function
function trackOrder(orderNumber) {
	let modal = document.getElementById('trackOrderModal');

	modal.style.display = 'block';
}

function closeModal(){
	let modal = document.getElementById('trackOrderModal');

		modal.style.display = 'none';
}

// View Order Details Function
function viewOrderDetails(productId) {
	window.location.href = `/product/${productId}`;
}

// Cancel Order Function
async function cancelOrder(orderId) {
	let token = sessionStorage.getItem("accessToken");
	let response = await fetch(`http://localhost:8080/api/orders/cancel/${orderId}`, {
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
				"Authorization": `Bearer ${token}`
			},
		});
		
	if(response.ok){
		window.location.href=`profile?id=${sessionStorage.getItem('userId')}`;
	}
		
		
		
	
}

// Enhanced Order Filtering
document.addEventListener('DOMContentLoaded', function() {
	const filterSelect = document.getElementById('orderStatusFilter');
	if (filterSelect) {
		filterSelect.addEventListener('change', function() {
			const status = this.value;
			const orderCards = document.querySelectorAll('.order-card');
            let count = 0;
			orderCards.forEach(card => {
				if (status === 'all') {
					card.style.display = 'block';
					count++;
				} else {
					const cardStatus = card.getAttribute('data-status');
					card.style.display = cardStatus === status ? 'block' : 'none';
					if(cardStatus === status ){
						count++;
					}
				}
			});

			// Update the orders count
			const ordersSubtitle = document.querySelector('.orders-subtitle');
			if (ordersSubtitle) {
				ordersSubtitle.textContent = `${count} orders found`;
			}
		});
	}
}); 