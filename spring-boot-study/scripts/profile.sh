#!/usr/bin/env bash

# 쉬고 있는 profile 찾기
function find_idle_profile() {
  # 엔직엑스가 바라보고 있는 웹 애플리케이션이 정상적으로 수행중인지 확인하다.
  # 에러가 발생하면 real2를 profile로 사용한다.
  # -s: --silent로 진행내역이나 메시지를 출력하지 않는다.
  # -o: --output을 버린다.
  # -w: --write-out -> http_code로 마지막으로 전송된 응답 코드를 write한다.
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile) #

  if [ ${RESPONSE_CODE} -ge 400 ]; then # 에러 발생으로 real2를 현재 profile로 설정
    CURRENT_PROFILE=real2

  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile) # 에러가 아니라면 profile을 가져온다.

  fi

  if [ ${CURRENT_PROFILE} == real1 ]; then # 현재 profile이 real1이면 IDEL은 real 2가된다.
    IDLE_PROFILE=real2

  else

    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}" # 함수 호출시 이 값을 반환한다.
}

function find_idle_port() {
  #statements
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == real1 ]; then
    echo "8081"
  else
    echo "8082"
  fi
}
