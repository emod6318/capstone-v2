

let friend = [];
let friendEmail = "";
// 내 정보 가져오기
// 내 정보 가져오기
let name;
let email;

loadInfo();

loadFriend();
//정보 가져오기
// 정보 가져오기
function loadInfo() {
    return new Promise((resolve, reject) => { // Promise를 반환하도록 수정
        fetch('/my-info')
            .then(response => {
                if (!response.ok) {
                    return reject(new Error('네트워크 응답이 좋지 않습니다.'));
                }
                return response.json();
            })
            .then(data => {
                const emailIn = data["email"];
                const name = data["name"];
                email = emailIn; // email 변수가 어디서 선언되었는지 확인 필요
                document.getElementById("my-name").textContent = name;
                document.getElementById("my-email").textContent = emailIn;
                resolve(data); // 성공적으로 데이터를 로드하면 resolve
            })
            .catch(error => {
                console.error('문제가 발생했습니다:', error);
                document.getElementById('calendar-container').innerHTML = '<h1>오류</h1><p>캘린더를 불러오는 데 문제가 발생했습니다.</p>';
                reject(error); // 오류 발생 시 reject
            });
    });
}

//친구 목록 불러오기
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
        });
}

//친구 목록 넣기
function friendListSet()
{
    const friendList = document.getElementById('friend-list');
    friend.forEach(({owner_email,user_email,user_name})=>{
        const listContainer = document.createElement("li");
        const listItemName = document.createElement('div');
        const listItemEmail = document.createElement('div');
        listItemName.textContent = user_name;
        listItemEmail.textContent = user_email;
        // listItem.style.background = category_color;
        listContainer.appendChild(listItemName);
        listContainer.appendChild(listItemEmail);
        friendList.appendChild(listContainer);

    });
}


//모달 열기
function openModal(name)
{
    if(name === "change-info"){
        document.getElementById("my-email-in").textContent = email;
    }
    else if(name === "delete-friend")
    {
        const selectFriendContainer = document.getElementById("friend-select");
        friend.forEach(({owner_email,user_email,user_name})=>{
            const itemFriendSelect = document.createElement("option")
            itemFriendSelect.textContent = user_name;
            itemFriendSelect.value = user_name;
            selectFriendContainer.appendChild(itemFriendSelect);
        });
    }
    document.getElementById(`${name}-modal`).style.display = 'flex';
}

//정보 Check
function infoCheck()
{
    ///change-name, change=pw
    const updatedUserDetails = {
        name: document.getElementById("get-email").value,
        password: document.getElementById("get-pw").value // 비밀번호는 해싱된 상태여야 합니다.
    };
    fetch('/check',{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedUserDetails),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.toString();
        })
        .then(data => {

                closeModal();
                openModal("change-info");
        })
        .catch(error => {
            console.error('Error updating user:', error);
        });

}

//정보 Update
function infoUpdate()
{
    ///change-name, change=pw
    const updatedUserDetails = {
        name: document.getElementById("change-name").value,
        password: document.getElementById("change-pw").value // 비밀번호는 해싱된 상태여야 합니다.
    };
    fetch('/user/update',{
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedUserDetails),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.json(); // 응답 JSON 파싱
        })
        .then(data => {
            console.log('User updated successfully:', data);
        })
        .catch(error => {
            console.error('Error updating user:', error);
        });
}

//친구 추가
function friendAdd()
{
    ///change-name, change=pw
    const getUserInfo = {
        "myEmail": email,
        "name": document.getElementById("add-name").value,
        "friendEmail": document.getElementById("add-email").value
    };
    fetch(`/friend/create`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(getUserInfo),
    })
        .then(response => {
            if (response.ok) {
                // 요청이 성공하면 처리할 내용
                console.log('User deleted successfully');
                // 추가적인 후처리, 예: UI 업데이트
                // 예: 사용자 목록 재로딩
            } else {
                // 오류 처리
                return response.text().then(errorMessage => {
                    console.error('Failed to delete friend:', errorMessage);
                });
            }
        })
        .catch(error => {
            // 네트워크 오류 처리
            console.error('Error:', error);
        });
}



//삭제 시에 분기점으로 User or Friend
function deleteBtn(name)
{
    if(name === "user")
    {
        console.log("실행 됌");
        deleteUser(email);
    }
    else{
        const selectElement = document.getElementById('friend-select');
        const selectedValue = selectElement.value; // 선택된 값 가져오기
        console.log(selectedValue);
        friend.forEach((inFriend)=>{
            if(inFriend["user_name"]===selectedValue)
            {
                deleteFriend(email,inFriend["user_email"]);
            }
        })
    }
}
//User 삭제
function deleteUser(userEmail) {
    fetch(`/delete/user/${userEmail}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                // 요청이 성공하면 처리할 내용
                console.log('User deleted successfully');

                // 리다이렉트할 주소
                window.location.href = '/';  // 성공 시 홈 페이지로 리다이렉트
            } else {
                // 오류 처리
                return response.text().then(errorMessage => {
                    console.error('Failed to delete user:', errorMessage);
                });
            }
        })
        .catch(error => {
            // 네트워크 오류 처리
            console.error('Error:', error);
        });

}
//Friend 삭제
function deleteFriend(email,friendEmail)
{
    fetch(`/delete/friend/${email}/${friendEmail}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                // 요청이 성공하면 처리할 내용
                console.log('User deleted successfully');
                // 추가적인 후처리, 예: UI 업데이트
                // 예: 사용자 목록 재로딩
            } else {
                // 오류 처리
                return response.text().then(errorMessage => {
                    console.error('Failed to delete friend:', errorMessage);
                });
            }
        })
        .catch(error => {
            // 네트워크 오류 처리
            console.error('Error:', error);
        });
}
// 모달 닫기 기능
function closeModal() {
    const modals = document.getElementsByClassName('modal');
    for (let i = 0; i < modals.length; i++) {
        modals[i].style.display = 'none'; // 각 모달의 display 속성을 none으로 설정
    }
    location.reload();
}