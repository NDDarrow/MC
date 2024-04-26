window.onload = function() {
  var currentUrl = window.location.href;
  // URL에서 "?" 뒤에 오는 부분을 가져옴
  var queryString = currentUrl.split("?")[1];
  // queryString을 다시 "&"로 분할하여 배열로 변환
  var queryParams = queryString.split("&");
  var genre;

  // 배열을 순회하며 genre 파라미터를 찾음
  queryParams.forEach(function(param) {
    var paramParts = param.split("=");
    if (paramParts[0] === "genre") {
      genre = paramParts[1];
    }
  });

  // genre이 존재하면 해당 장르에 해당하는 li 태그에 "on" 클래스 추가
  if (genre) {
    $('.category li.' + genre).removeClass('off');
    $('.category li.' + genre).addClass('on');
  }
};






