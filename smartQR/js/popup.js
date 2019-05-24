document.addEventListener('DOMContentLoaded', function() {
    chrome.tabs.query({active:true, windowId:chrome.windows.WINDOW_ID_CURRENT}, function(tabs) {
        if (tabs) {
            qr.canvas({
                size:7,
                level:'L',
                value:tabs[0].url,
                canvas:document.getElementById('image')
            });
        } else {
            alert('something wrong, please try again.')
        }
    })
});
