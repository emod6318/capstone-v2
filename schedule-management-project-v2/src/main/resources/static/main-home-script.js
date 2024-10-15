// 캘린더 HTML 파일을 동적으로 불러오는 함수
function loadCalendar() {
    fetch('/calender') // 캘린더 파일 경로
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.text();
        })
        .then(html => {
            document.getElementById('calendar-container').innerHTML = html;
            htmlRender();

        })
        .catch(error => {
            console.error('문제가 발생했습니다:', error);
            document.getElementById('calendar-container').innerHTML = '<h1>오류</h1><p>캘린더를 불러오는 데 문제가 발생했습니다.</p>';
        });
}

// 페이지가 로드될 때 캘린더를 불러옴
document.addEventListener('DOMContentLoaded', loadCalendar);



