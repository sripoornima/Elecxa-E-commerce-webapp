document.addEventListener('DOMContentLoaded', function() {
    // Get all tabs and content sections
    const specsTabs = document.querySelectorAll('.specs-tab');
    const specsContents = document.querySelectorAll('.specs-content');
    
    // Add click event listeners to each tab
    specsTabs.forEach(tab => {
        tab.addEventListener('click', () => {
            // Get the data-tab attribute value
            const tabId = tab.getAttribute('data-tab');
            
            // Remove active class from all tabs and content sections
            specsTabs.forEach(t => t.classList.remove('active'));
            specsContents.forEach(c => c.classList.remove('active'));
            
            // Add active class to the clicked tab
            tab.classList.add('active');
            
            // Find and show the corresponding content section
            const activeContent = document.getElementById(`${tabId}-specs`);
            if (activeContent) {
                activeContent.classList.add('active');
            }
        });
    });
}); 