/* Main JavaScript for Admin Dashboard */

document.addEventListener('DOMContentLoaded', function() {
    // Initialize tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // Sidebar toggle functionality
    const sidebarCollapseBtn = document.getElementById('sidebarCollapseBtn');
    const sidebar = document.getElementById('sidebar');
    const content = document.getElementById('content');
    
    if (sidebarCollapseBtn) {
        sidebarCollapseBtn.addEventListener('click', function() {
            sidebar.classList.toggle('collapsed');
            content.classList.toggle('expanded');
            
            // Store sidebar state in session storage
            const isCollapsed = sidebar.classList.contains('collapsed');
            sessionStorage.setItem('sidebarCollapsed', isCollapsed);
            
            // Send AJAX request to update session
            updateSidebarState(isCollapsed);
        });
    }
    
    // Initialize sidebar state from session storage
    const storedSidebarState = sessionStorage.getItem('sidebarCollapsed');
    if (storedSidebarState === 'true' && sidebar) {
        sidebar.classList.add('collapsed');
        content.classList.add('expanded');
    }
    
    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });
    
    // Initialize any charts on the page
    initializeCharts();
    
    // Add responsive behavior for tables
    const tables = document.querySelectorAll('.table-responsive');
    tables.forEach(function(table) {
        new PerfectScrollbar(table);
    });
    
    // Initialize datepickers
    const datepickers = document.querySelectorAll('.datepicker');
    datepickers.forEach(function(picker) {
        new Datepicker(picker, {
            format: 'yyyy-mm-dd',
            autohide: true
        });
    });
    
    // Product image preview
    const imageInput = document.getElementById('productImage');
    const imagePreview = document.getElementById('imagePreview');
    
    if (imageInput && imagePreview) {
        imageInput.addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    imagePreview.src = e.target.result;
                    imagePreview.style.display = 'block';
                }
                reader.readAsDataURL(file);
            }
        });
    }
    
    // Order status update via AJAX
    const statusSelects = document.querySelectorAll('.status-select');
    statusSelects.forEach(function(select) {
        select.addEventListener('change', function() {
            const orderId = this.getAttribute('data-order-id');
            const newStatus = this.value;
            
            // Send AJAX request to update order status
            updateOrderStatus(orderId, newStatus);
        });
    });
});

// Function to initialize charts
function initializeCharts() {
    // Revenue Chart
    const revenueChart = document.getElementById('revenueChart');
    if (revenueChart) {
        const ctx = revenueChart.getContext('2d');
        const chartData = JSON.parse(revenueChart.getAttribute('data-chart'));
        
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: chartData.labels,
                datasets: [
                    {
                        label: 'This Year',
                        data: chartData.thisYear,
                        borderColor: '#4e73df',
                        backgroundColor: 'rgba(78, 115, 223, 0.05)',
                        pointBackgroundColor: '#4e73df',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: '#4e73df',
                        borderWidth: 2,
                        fill: true
                    },
                    {
                        label: 'Last Year',
                        data: chartData.lastYear,
                        borderColor: '#1cc88a',
                        backgroundColor: 'rgba(28, 200, 138, 0.05)',
                        pointBackgroundColor: '#1cc88a',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: '#1cc88a',
                        borderWidth: 2,
                        fill: true
                    }
                ]
            },
            options: {
                maintainAspectRatio: false,
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        grid: {
                            drawBorder: false
                        }
                    },
                    x: {
                        grid: {
                            display: false
                        }
                    }
                },
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    },
                    tooltip: {
                        mode: 'index',
                        intersect: false
                    }
                }
            }
        });
    }
    
    // Customer Chart (Pie)
    const customerChart = document.getElementById('customerChart');
    if (customerChart) {
        const ctx = customerChart.getContext('2d');
        const chartData = JSON.parse(customerChart.getAttribute('data-chart'));
        
        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: chartData.labels,
                datasets: [{
                    data: chartData.data,
                    backgroundColor: ['#4e73df', '#1cc88a'],
                    hoverBackgroundColor: ['#2e59d9', '#17a673'],
                    hoverBorderColor: 'rgba(234, 236, 244, 1)'
                }]
            },
            options: {
                maintainAspectRatio: false,
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom'
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return `${context.label}: ${context.raw} (${context.parsed}%)`;
                            }
                        }
                    }
                }
            }
        });
    }
    
    // Category Chart (Bar)
    const categoryChart = document.getElementById('categoryChart');
    if (categoryChart) {
        const ctx = categoryChart.getContext('2d');
        const chartData = JSON.parse(categoryChart.getAttribute('data-chart'));
        
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: chartData.labels,
                datasets: [{
                    label: 'Sales',
                    data: chartData.data,
                    backgroundColor: '#4e73df',
                    hoverBackgroundColor: '#2e59d9',
                    borderWidth: 0
                }]
            },
            options: {
                maintainAspectRatio: false,
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        grid: {
                            drawBorder: false
                        }
                    },
                    x: {
                        grid: {
                            display: false
                        }
                    }
                },
                plugins: {
                    legend: {
                        display: false
                    }
                }
            }
        });
    }
}

// Function to update sidebar state via AJAX
function updateSidebarState(isCollapsed) {
    fetch('/settings/toggle-sidebar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
        },
        body: `collapsed=${isCollapsed}`
    });
}

// Function to update order status via AJAX
function updateOrderStatus(orderId, newStatus) {
    const formData = new FormData();
    formData.append('orderId', orderId);
    formData.append('newStatus', newStatus);
    
    fetch('/orders/update-status', {
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
        },
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // Show success toast
            showToast('Order status updated successfully', 'success');
            
            // Update UI elements if needed
            const statusBadge = document.querySelector(`.order-status-badge[data-order-id="${orderId}"]`);
            if (statusBadge) {
                statusBadge.textContent = newStatus;
                statusBadge.className = `badge order-status-badge status-${newStatus.toLowerCase()}`;
            }
        } else {
            showToast('Failed to update order status', 'error');
        }
    })
    .catch(error => {
        console.error('Error updating order status:', error);
        showToast('An error occurred while updating order status', 'error');
    });
}

// Function to show toast notifications
function showToast(message, type = 'info') {
    const toastContainer = document.getElementById('toastContainer');
    if (!toastContainer) {
        const container = document.createElement('div');
        container.id = 'toastContainer';
        container.className = 'toast-container position-fixed bottom-0 end-0 p-3';
        document.body.appendChild(container);
    }
    
    const toastId = 'toast-' + Date.now();
    const toastHTML = `
        <div id="${toastId}" class="toast align-items-center text-white bg-${type === 'error' ? 'danger' : type}" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    `;
    
    document.getElementById('toastContainer').insertAdjacentHTML('beforeend', toastHTML);
    const toastElement = document.getElementById(toastId);
    const toast = new bootstrap.Toast(toastElement, { delay: 5000 });
    toast.show();
    
    // Remove toast from DOM after it's hidden
    toastElement.addEventListener('hidden.bs.toast', function() {
        toastElement.remove();
    });
}