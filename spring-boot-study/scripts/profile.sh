#!/usr/bin/env bash

# 쉬고 있는 profile 찾기

function find_idle_profile() {
  #statements
  RESPONSE_CODE=$(curl -s -o /dev/null -w "{http_code}" http://localhost/profile) #

  if [ ${RESPONSE_CODE} -ge 400 ]; then # 400보다큰 에러면
    CURRENT_PROFILE=real2

  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)

  fi

  if [ ${CURRENT_PROFILE} == real1 ]; then
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
