var scrollPosition = 0;

// 페이지 로드 시 스크롤 위치 저장
window.onload = function() {
    // 현재 스크롤 위치 기억
    scrollPosition = window.pageYOffset;
}

// 페이지 이동 시 스크롤 위치 이동 (특정 패턴에 맞는 주소로 이동 시에만 작동)
window.onpopstate = function(event) {
    // 정규식 패턴에 맞는 주소인 경우에만 스크롤 위치 이동
    var pattern = /board/Genre/METAL
    if (pattern.test(window.location.pathname)) {
        // 저장된 스크롤 위치로 이동
        window.scrollTo(0, scrollPosition);
    }
}