
window.onload = function() {
  var currentUrl = window.location.href;
  // URL을 "/"로 분할하여 배열로 변환
  var urlParts = currentUrl.split("/");
  // 배열에서 마지막 요소를 가져와서 장르로 사용
  var genre = urlParts[urlParts.length - 1];

  // 해당 장르에 해당하는 li 태그에 "on" 클래스 추가
  $('.category li.' + genre).removeClass('off');
  $('.category li.' + genre).addClass('on');
};
