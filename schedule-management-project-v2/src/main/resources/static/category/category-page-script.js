let category = [];
let email;
let currentColor = "";
let currentName = "";
loadCategory();
loadInfo();
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
                resolve(data); // 성공적으로 데이터를 로드하면 resolve
            })
            .catch(error => {
                console.error('문제가 발생했습니다:', error);
                reject(error); // 오류 발생 시 reject
            });
    });
}
// 캘린더 HTML 파일을 동적으로 불러오는 함수
function loadCategory() {
    fetch('/categories/count') // 캘린더 파일 경로
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
function categoryListSet()
{
    const categoryList = document.getElementById('category-list');
    category.forEach(({category_color,category_name,count})=>{
        const listItem = document.createElement('li');

        const listItemName = document.createElement('span');
        const listItemColor = document.createElement('span');
        const listItemCount = document.createElement('span');

        listItemName.textContent = category_name;
        listItemColor.style.background = category_color;
        listItemColor.style.display = 'inline-block'; // 색상 박스를 보이게 하기 위해
        listItemColor.style.width = '20px'; // 너비 설정
        listItemColor.style.height = '20px'; // 높이 설정
        listItemCount.textContent = count;

        listItem.appendChild(listItemName);
        listItem.appendChild(listItemColor);
        listItem.appendChild(listItemCount);
        if(listItemName.textContent !== "미등록")
        {
            // 클릭 이벤트 추가
            listItem.addEventListener('click', () => {
                currentName = listItemName.textContent;
                // 배경색을 HEX 형식으로 변환
                const rgbColor = listItemColor.style.backgroundColor;
                currentColor = rgbToHex(rgbColor);
                openModal("update-category");
            });
        }

        categoryList.appendChild(listItem);

    });


    // listItem.style.background = category_color;
}
// RGB를 HEX로 변환하는 함수
function rgbToHex(rgb) {
    const result = rgb.match(/\d+/g);
    return result ? `#${((1 << 24) + (parseInt(result[0]) << 16) + (parseInt(result[1]) << 8) + parseInt(result[2])).toString(16).slice(1)}` : null;
}
//모달 열기
function openModal(name)
{
    if(name === "update-category"){
        // "update-categoryName"
        // "update-categoryColor"
        document.getElementById("update-categoryName").value = currentName;
        document.getElementById("update-categoryColor").value = currentColor;
    }
    else if(name === "delete-category")
    {
        const selectFriendContainer = document.getElementById("category-select");
        category.forEach(({category_name})=>{
            if (category_name !== "미등록") {
                const itemFriendSelect = document.createElement("option")
                itemFriendSelect.textContent = category_name;
                itemFriendSelect.value = category_name;
                selectFriendContainer.appendChild(itemFriendSelect);
            }
        });
    }
    document.getElementById(`${name}-modal`).style.display = 'flex';
}

function closeModal() {
    const modals = document.getElementsByClassName('modal');
    for (let i = 0; i < modals.length; i++) {
        modals[i].style.display = 'none'; // 각 모달의 display 속성을 none으로 설정
    }
    location.reload();
}

function categoryUpdate()
{
    // id="create-categoryName"
    // id="create-categoryColor"
    const updateCategory = {
        name: document.getElementById("update-categoryName").value,
        color: document.getElementById("update-categoryColor").value // 비밀번호는 해싱된 상태여야 합니다.
    };
    fetch('/category/update',{
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            category: {
                name:currentName,
                color:currentColor
            },
            updateCategory: updateCategory
        }),
    })
        .then(response => {
                if (response.ok) {
                    // 요청이 성공하면 처리할 내용
                    console.log('User deleted successfully');
                    // 추가적인 후처리, 예: UI 업데이트
                    // 예: 사용자 목록 재로딩
                    closeModal();
                } else {
                    // 오류 처리
                    return response.text().then(errorMessage => {
                        console.error('Failed to delete friend:', errorMessage);
                    });
                }
            }
        )

}
function categoryAdd()
{
    // id="create-categoryName"
    // id="create-categoryColor"
    const newCategory = {
        name: document.getElementById("create-categoryName").value,
        color: document.getElementById("create-categoryColor").value // 비밀번호는 해싱된 상태여야 합니다.
    };
    fetch('/categories/create',{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newCategory),
    })
        .then(response => {
            if (response.ok) {
                // 요청이 성공하면 처리할 내용
                console.log('User deleted successfully');
                // 추가적인 후처리, 예: UI 업데이트
                // 예: 사용자 목록 재로딩
                closeModal();
            } else {
                // 오류 처리
                return response.text().then(errorMessage => {
                    console.error('Failed to delete friend:', errorMessage);
                });
            }
        }
        )
    location.reload();

}
function deleteBtnClick()
{
    categoryDelete(email,document.getElementById("category-select").value);
}
function categoryDelete(email,name)
{
    // id="create-categoryName"
    // id="create-categoryColor"

    fetch(`/category/delete/${email}/${name}`,{
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => {
            if (response.ok) {
                // 요청이 성공하면 처리할 내용
                console.log('User deleted successfully');
                // 추가적인 후처리, 예: UI 업데이트
                // 예: 사용자 목록 재로딩
                closeModal();
            } else {
                // 오류 처리
                return response.text().then(errorMessage => {
                    console.error('Failed to delete friend:', errorMessage);
                });
            }
        }
    )
}

