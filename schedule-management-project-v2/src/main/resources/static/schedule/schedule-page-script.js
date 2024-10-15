let tasks = [];
let category = [];
let currentId;
let currentEmail;
loadCalenderData();
loadCategory();

function loadCalenderData()
{
    fetch('/schedules/get') // 캘린더 파일 경로
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            // console.log(data);
            tasks = data;
            renderTaskList();
        })
        .catch(error => {
            console.error('문제가 발생했습니다:', error);
            document.getElementById('calendar-container').innerHTML = '<h1>오류</h1><p>캘린더를 불러오는 데 문제가 발생했습니다.</p>';
        });
}
function renderTaskList()
{
    const scheduleList = document.getElementById('schedule-list');
    tasks.forEach(({category_name,schedule_name,start_date,end_date,description,schedule_id,user_email})=>{
        const listItem = document.createElement('li');

        const listItemTitle = document.createElement('span');
        const listItemCategoryName = document.createElement('span');
        const listItemStartTime = document.createElement('span');
        const listItemEndTime = document.createElement('span');

        listItemTitle.textContent = schedule_name;
        listItemCategoryName.textContent = category_name;
        listItemStartTime.textContent = start_date;
        listItemEndTime.textContent = end_date;

        listItem.appendChild(listItemTitle);
        listItem.appendChild(listItemCategoryName);
        listItem.appendChild(listItemStartTime);
        listItem.appendChild(listItemEndTime);


        // 클릭 이벤트 추가
        listItem.addEventListener('click', () => {
            document.getElementById("schedule-select").value = category_name;
            // id="schedule-title"
            // id="schedule-startDate"
            // id="schedule-endDate"
            document.getElementById("schedule-title").value = listItemTitle.textContent;
            document.getElementById("schedule-startDate").value = listItemStartTime.textContent;
            document.getElementById("schedule-endDate").value = listItemEndTime.textContent;
            document.getElementById("schedule-description").value = description;
            currentId = schedule_id;
            currentEmail = user_email;
            openModal();
        });

        scheduleList.appendChild(listItem);

    });
}
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
            categorySelectSet();
        })
        .catch(error => {
            console.error('문제가 발생했습니다:', error);
        });
}
function categorySelectSet()
{
    const selectSchedule = document.getElementById("schedule-select");
    category.forEach(({category_name})=>{
       const optionSchedule = document.createElement("option");
       optionSchedule.textContent = category_name;
       optionSchedule.value = category_name;
       selectSchedule.appendChild(optionSchedule);
    });
}


function scheduleUpdate()
{
    // @PutMapping("/update/schedule")
    const updatedScheduleDetails = {
        "id" : currentId,
        "userEmail" : currentEmail, // 사용자의 이메일
        "categoryName" : document.getElementById("schedule-select").value, // 카테고리 이름
        "scheduleName" : document.getElementById("schedule-title").value, // 일정 이름
        "startDate" : document.getElementById("schedule-startDate").value, // 시작 날짜
        "endDate" : document.getElementById("schedule-endDate").value, // 종료 날짜
        "description" : document.getElementById("schedule-description").value // 설명
    };
    fetch('/update/schedule',{
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedScheduleDetails),
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
function scheduleDelete(email,id)
{
//@DeleteMapping(value = "/delete/schedule/{email}/{id}")
    email = currentEmail;
    id = currentId;
    fetch(`/delete/schedule/${email}/${id}`,{
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
        });
}

function openModal()
{

    document.getElementById('delete-update-schedule-modal').style.display = 'flex';
}
function closeModal() {
    const modals = document.getElementsByClassName('modal');
    for (let i = 0; i < modals.length; i++) {
        modals[i].style.display = 'none'; // 각 모달의 display 속성을 none으로 설정
    }
    location.reload();
}