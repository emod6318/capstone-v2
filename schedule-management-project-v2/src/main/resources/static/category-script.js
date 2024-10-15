let category = [];

// 캘린더 HTML 파일을 동적으로 불러오는 함수
function loadCategory() {
    fetch('/categories') // 캘린더 파일 경로
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            //사이드 바에 채워놓는 코드 작성
            category = data;
            categoryListSet();
        })
        .catch(error => {
            console.error('문제가 발생했습니다:', error);
            document.getElementById('calendar-container').innerHTML = '<h1>오류</h1><p>캘린더를 불러오는 데 문제가 발생했습니다.</p>';
        });
}
loadCategory();

function categoryListSet()
{
    const categoryList = document.getElementById('category-list');
    category.forEach(({category_color,category_name})=>{
        const listItem = document.createElement('li');
        listItem.textContent = category_name;
        listItem.style.background = category_color;
        categoryList.appendChild(listItem);

    });

    const todayCategoryList = document.getElementById('today-menu');


    // listItem.style.background = category_color;
}