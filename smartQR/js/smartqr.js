
chrome.storage.local.get(null, function(items) {
  var url = window.location.href;
  //Collect Data who visit TVNZ ONE NEWS
  if (url.indexOf("https://www.tvnz.co.nz/one-news") != -1) {
    var image = $(".vjs-poster").css("background-image");
    if (!image) {
      image = $(".storyPageImage img").attr("src");
    } else {
      image = image
        .replace("url(", "")
        .replace(")", "")
        .replace(/\"/gi, "");
    }

    if (image && image.indexOf("http")!=-1) {
      $.post(
        "https://ool2dsi8o2.execute-api.ap-southeast-2.amazonaws.com/dev/articles",
        JSON.stringify({
          title: document.title,
          url: url,
          image: image
        })
      );
    }
  }
});
