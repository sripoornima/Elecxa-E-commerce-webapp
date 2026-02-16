document.addEventListener('DOMContentLoaded', function() {
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

    // Order tracking functionality
    const trackButtons = document.querySelectorAll('.track-order-btn');
    trackButtons.forEach(button => {
        button.addEventListener('click', function() {
            const orderNumber = this.closest('.order-card').querySelector('.order-number').textContent;
            trackOrder(orderNumber);
        });
    });

    // View details functionality
    const viewDetailsButtons = document.querySelectorAll('.view-details-btn');
    viewDetailsButtons.forEach(button => {
        button.addEventListener('click', function() {
            const orderNumber = this.closest('.order-card').querySelector('.order-number').textContent;
            viewOrderDetails(orderNumber);
        });
    });

    // Cancel order functionality
    const cancelButtons = document.querySelectorAll('.cancel-order-btn');
    cancelButtons.forEach(button => {
        button.addEventListener('click', function() {
            const orderNumber = this.closest('.order-card').querySelector('.order-number').textContent;
            cancelOrder(orderNumber);
        });
    });
});

// Track Order Function
function trackOrder(orderNumber) {
    // TODO: Implement actual order tracking logic
    console.log(`Tracking order: ${orderNumber}`);
    alert(`Tracking order: ${orderNumber}`);
}

// View Order Details Function
function viewOrderDetails(orderNumber) {
    // TODO: Implement actual order details view logic
    console.log(`Viewing details for order: ${orderNumber}`);
    alert(`Viewing details for order: ${orderNumber}`);
}

// Cancel Order Function
function cancelOrder(orderNumber) {
    if (confirm(`Are you sure you want to cancel order ${orderNumber}?`)) {
        // TODO: Implement actual order cancellation logic
        console.log(`Cancelling order: ${orderNumber}`);
        alert(`Order ${orderNumber} has been cancelled.`);
    }
} 