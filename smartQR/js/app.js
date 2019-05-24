chrome.tabs.query({}, function(tabs) {      
	var title = "Kiwi UFC star Dan Hooker on his latest victory: 'A lot rides on these fights";	
	for (var i = 0; i < tabs.length; i++) {			
		if (tabs[i].url.indexOf("https://www.tvnz.co.nz/one-news") != -1) {
			title = tabs[i].title;
			break;
		}	
	}	
	console.log("================>" + title);
	var url = 'https://ool2dsi8o2.execute-api.ap-southeast-2.amazonaws.com/dev/articles/'+encodeURI(title);
	$.getJSON(url, function (data) {
		$.each(data, function(i, item){				
			$('#img'+i).attr('src',item.image);
			$('#title'+i).html(item.title);			
			$('#qrd'+i).qrcode({text: item.url});								
		})
	});	  
});






