

let friend = [];
let friendEmail = "";
function loadFriend()
{
    fetch('/friends')
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            //사이드 바에 채워놓는 코드 작성
            friend = data;
            friendListSet();
        })
        .catch(error => {
            console.error('문제가 발생했습니다:', error);
            document.getElementById('calendar-container').innerHTML = '<h1>오류</h1><p>캘린더를 불러오는 데 문제가 발생했습니다.</p>';
        });
}

loadFriend();
function friendListSet()
{
    const categoryList = document.getElementById('friend-list');
    friend.forEach(({owner_email,user_email,user_name})=>{
        const listItem = document.createElement('li');
        listItem.textContent = user_name;
        // listItem.style.background = category_color;
        listItem.addEventListener("click",function (){
            friendEmail = user_email;
            loadFriendCalenderData();
        })
        categoryList.appendChild(listItem);

    });

    // listItem.style.background = category_color;
}