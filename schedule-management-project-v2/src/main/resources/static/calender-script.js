
    const monthNames = [
        "1월", "2월", "3월", "4월", "5월", "6월",
        "7월", "8월", "9월", "10월", "11월", "12월"
    ];
    let sellectDate = new Date();
    let currentDate = new Date();
    let tasks = []; // 날짜별 할 일을 저장하는 객체

    function renderCalendar() {

        document.getElementById('calendar-add').disabled = friendEmail !== '';

        // console.log(tasks);
        const monthYearElement = document.getElementById('month-year');
        const daysContainer = document.getElementById('days-container');

        // 현재 월과 연도 표시
        monthYearElement.textContent = `${monthNames[currentDate.getMonth()]} ${currentDate.getFullYear()}`;

        // 달력 초기화
        daysContainer.innerHTML = '';

        // 현재 월의 첫 번째 날과 마지막 날 구하기
        const firstDay = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
        const lastDay = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 0);

        // 첫 번째 날의 요일
        const firstDayOfWeek = firstDay.getDay();
        // 이전 달의 마지막 날 구하기
        const prevMonthYear = currentDate.getMonth() === 0 ? currentDate.getFullYear() - 1 : currentDate.getFullYear();
        const prevMonth = currentDate.getMonth() === 0 ? 11 : currentDate.getMonth() - 1;
        const prevMonthLastDay = new Date(prevMonthYear, prevMonth + 1, 0);


        for (let i = 0; i < firstDayOfWeek; i++) {
            const prevDay = prevMonthLastDay.getDate() - (firstDayOfWeek - 1 - i); // 이전 달의 마지막 며칠 계산
            const prevDate = new Date(prevMonthLastDay.getFullYear(), prevMonthLastDay.getMonth(), prevDay); // Date 객체 생성

            const dayElement = document.createElement('div');
            dayElement.className = 'day prev-month'; // 이전 달 날짜는 'prev-month' 클래스 추가
            dayElement.textContent = prevDay;

            // 날짜별 할 일 목록을 보여줄 공간
            const taskListContainer = document.createElement('ul');
            taskListContainer.className = 'task-list';
            taskListContainer.id = `task-list-prev-${prevDay}`; // 이전 달 날짜의 할 일 목록 ID 설정
            taskListContainer.style.display = 'block'; // 기본적으로 보이도록 설정
            dayElement.appendChild(taskListContainer);
            // 클릭 이벤트 추가
            dayElement.addEventListener('click', () => {
                sellectDate = prevDate; // 클릭한 날짜를 selectDate에 저장
                console.log( sellectDate); // 선택한 날짜를 콘솔에 출력 (디버깅용)
                // 여기에서 selectDate를 사용하여 추가 작업 수행
                todayListSet();
            });
            daysContainer.appendChild(dayElement);

            // 이전 달의 마지막 날에도 할 일 목록 렌더링
            renderTaskList(prevDate, "prev"); // Date 객체 전달
        }


        // 현재 월의 날짜 추가
        for (let day = 1; day <= lastDay.getDate(); day++) {
            const dayElement = document.createElement('div');
            dayElement.className = 'day';
            dayElement.textContent = day;

            // 날짜별 할 일 목록을 보여줄 공간
            const taskListContainer = document.createElement('ul');
            taskListContainer.className = 'task-list';
            taskListContainer.id = `task-list-${day}`;
            taskListContainer.style.display = 'block'; // 기본적으로 보이도록 설정
            dayElement.appendChild(taskListContainer);

            // 클릭 이벤트 추가
            dayElement.addEventListener('click', () => {
                sellectDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), day); // 클릭한 날짜를 selectDate에 저장
                console.log(sellectDate); // 선택한 날짜를 콘솔에 출력 (디버깅용)
                // 여기에서 selectDate를 사용하여 추가 작업 수행
                todayListSet();
            });

            daysContainer.appendChild(dayElement);
        }

        // 모든 날짜에 대한 할 일 목록 렌더링
        for (let day = 1; day <= lastDay.getDate(); day++) {
            const currentDate = new Date(lastDay.getFullYear(), lastDay.getMonth(), day); // 현재 연도와 월, 그리고 day를 사용하여 Date 객체 생성
            renderTaskList(currentDate, "now");
        }
        // 다음 달의 첫 주를 채우기
        const totalDaysDisplayed = firstDayOfWeek + lastDay.getDate(); // 달력에 채워진 총 날짜 수
        const nextMonthStartDay = 7 - (totalDaysDisplayed % 7); // 다음 달의 첫 날부터 필요한 칸 수

        if (nextMonthStartDay < 7) { // 다음 달로 넘어가는 칸이 필요한 경우만 실행
            for (let day = 1; day <= nextMonthStartDay; day++) {
                const nextDate = new Date(lastDay.getFullYear(), lastDay.getMonth() + 1, day); // 다음 달의 날짜를 Date 객체로 생성

                const dayElement = document.createElement('div');
                dayElement.className = 'day next-month'; // 다음 달 날짜는 'next-month' 클래스 추가
                dayElement.textContent = day;

                // 날짜별 할 일 목록을 보여줄 공간
                const taskListContainer = document.createElement('ul');
                taskListContainer.className = 'task-list';
                taskListContainer.id = `task-list-next-${day}`; // 다음 달 날짜의 할 일 목록 ID 설정
                taskListContainer.style.display = 'block'; // 기본적으로 보이도록 설정
                dayElement.appendChild(taskListContainer);
                // 클릭 이벤트 추가
                dayElement.addEventListener('click', () => {
                    sellectDate = nextDate; // 클릭한 날짜를 selectDate에 저장
                    console.log( sellectDate); // 선택한 날짜를 콘솔에 출력 (디버깅용)
                    // 여기에서 selectDate를 사용하여 추가 작업 수행
                    todayListSet();
                });
                daysContainer.appendChild(dayElement);

                // 다음 달의 첫 날에도 할 일 목록 렌더링
                renderTaskList(nextDate, "next"); // Date 객체 전달
            }
        }

        todayListSet();

    }
    function todayListSet()
    {
        // 현재 날짜 객체 생성
        const selectedDateWithoutTime = sellectDate;
        selectedDateWithoutTime.setHours(0, 0, 0, 0);
        const todayList = document.getElementById('today-list');
        todayList.innerHTML = ''; // 기존 목록 초기화

        // 날짜 형식 설정
        const options = { month: 'long', day: 'numeric' }; // "12월 20일" 형식
        const todayDateString = selectedDateWithoutTime.toLocaleDateString('ko-KR', options);
        const todayDate = document.createElement("li");
        todayDate.textContent = todayDateString;
        todayList.appendChild(todayDate);
        tasks.forEach(({ start_date, end_date ,schedule_name,category_color}) => {

            const startDate = new Date(start_date)
            const endDate = new Date(end_date)
            const startHours = startDate.toString().substring(16, 21); //;
            const endHours = endDate.toString().substring(16, 21);
            // startDate와 endDate의 시간을 0으로 설정
            startDate.setHours(0, 0, 0, 0);
            endDate.setHours(0, 0, 0, 0);
            // console.log("찾음");
            // 현재 날짜가 start_date와 end_date 사이에 있는지 확인
            if (selectedDateWithoutTime >= startDate && selectedDateWithoutTime <= endDate) {

                const mom = document.createElement('li');
                todayList.appendChild(mom);
                // 특정 작업을 여기에 추가 (예: 데이터를 추가)
                // const continaerItem = document.createElement('div');
                const timeItem = document.createElement('span');
                if(selectedDateWithoutTime.getTime() < endDate.getTime() && selectedDateWithoutTime.getTime() > startDate.getTime()){
                    timeItem.textContent = "00:00";
                }
                else if(selectedDateWithoutTime.getTime() === startDate.getTime())
                {
                    timeItem.textContent = startHours.toString();
                }
                else if(selectedDateWithoutTime.getTime() === endDate.getTime())
                {
                    timeItem.textContent = "00:00";
                }
                const listItem = document.createElement('span');
                listItem.textContent = schedule_name;
                if (friendEmail === '') {
                    listItem.style.background = category_color;
                } else {
                    listItem.style.background = "grey";
                }

                mom.appendChild(timeItem);
                mom.appendChild(listItem);
            }
        });
    }
    function renderTaskList(selectedDate, now) {
        const day = selectedDate.getDate(); // 선택된 날짜의 일(day) 추출
        let taskListContainer;

        if (now === "prev") {
            taskListContainer = document.getElementById(`task-list-prev-${day}`);
        } else if (now === "now") {
            taskListContainer = document.getElementById(`task-list-${day}`);
        } else {
            taskListContainer = document.getElementById(`task-list-next-${day}`);
        }

        taskListContainer.innerHTML = ''; // 기존 목록 초기화

        // selectedDate의 시간을 0으로 설정
        const selectedDateWithoutTime = new Date(selectedDate);
        selectedDateWithoutTime.setHours(0, 0, 0, 0);

        // tasks 배열을 돌면서 날짜가 그 범위에 있는지 확인
        tasks.forEach(({ start_date, end_date, schedule_name, category_color }) => {
            const startDate = new Date(start_date);
            const endDate = new Date(end_date);

            // startDate와 endDate의 시간을 0으로 설정
            startDate.setHours(0, 0, 0, 0);
            endDate.setHours(0, 0, 0, 0);

            // 현재 날짜가 start_date와 end_date 사이에 있는지 확인
            if (selectedDateWithoutTime >= startDate && selectedDateWithoutTime <= endDate) {
                console.log("찾음");
                // 특정 작업을 여기에 추가 (예: 데이터를 추가)
                const listItem = document.createElement('li');
                listItem.textContent = schedule_name;
                if (friendEmail === '') {
                    listItem.style.background = category_color;
                } else {
                    listItem.style.background = "grey";
                }

                taskListContainer.appendChild(listItem);
            }
        });
    }


    // 할 일 추가 모달 열기
    function openTaskModal(day) {
        // 모달 열기
        // 특정 작업을 여기에 추가 (예: 데이터를 추가)

        category.forEach(({category_name})=>{
            const categorySelectContainer = document.getElementById('categorySelect');
            const listItem = document.createElement('option');
            listItem.textContent = category_name;
            categorySelectContainer.appendChild(listItem);
            // listItem.style.background = category_color;
        });

        document.getElementById('modal').style.display = 'flex';

    }



    // 모달 닫기 기능
    function closeModal() {
        document.getElementById('modal').style.display = 'none';
    }
    function saveSchedule()
    {
        let categoryName = document.getElementById('categorySelect').value;
        let name = document.getElementById("name").value;
        let startDate = document.getElementById("start_date").value;
        let endDate = document.getElementById("end_date").value;
        let description = document.getElementById("description").value;
        console.log(startDate);
        fetch('/schedules/create',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "categoryName": categoryName,
                "scheduleName": name,
                "startDate": startDate,
                "endDate": endDate,
                "description": description
            })
        }) // 캘린더 파일 경로
            .then(response => {
                if (!response.ok) {
                    throw new Error('네트워크 응답이 좋지 않습니다.');
                }
                return response.toString();
            })
            .then(data => {
                location.reload();
            })
            .catch(error => {
                console.error('문제가 발생했습니다:', error);
            });
    }
    function htmlRender()
    {
        // 이전 달로 이동
        document.getElementById('prev-month').addEventListener('click', () => {
            currentDate.setMonth(currentDate.getMonth() - 1);
            renderCalendar();
        });

        // 다음 달로 이동
        document.getElementById('next-month').addEventListener('click', () => {
            currentDate.setMonth(currentDate.getMonth() + 1);
            renderCalendar();
        });
        document.getElementById('calendar-add').addEventListener('click', () => {
            openTaskModal();
        });




        loadCalenderData();
    }

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
                renderCalendar();
            })
            .catch(error => {
                console.error('문제가 발생했습니다:', error);
                document.getElementById('calendar-container').innerHTML = '<h1>오류</h1><p>캘린더를 불러오는 데 문제가 발생했습니다.</p>';
            });
    }
    function loadFriendCalenderData()
    {
        //email 받기
        fetch('/schedules/get',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "email": friendEmail,
            })
        }) // 캘린더 파일 경로
            .then(response => {
                if (!response.ok) {
                    throw new Error('네트워크 응답이 좋지 않습니다.');
                }
                return response.json();
            })
            .then(data => {
                tasks = data;
                renderCalendar();
            })
            .catch(error => {
                console.error('문제가 발생했습니다:', error);
            });
    }
