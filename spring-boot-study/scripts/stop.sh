#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)  # 현재 stop.sh가 속해있는 경로를 찾는다.
source ${ABSDIR}/profile.sh # profile.sh 설정들을 여기에 추가한다.

IDLE_PORT=$(find_idle_port) # profile.sh의 find_idel_port를 통해 배포할 보트 번호를 가져온다.

echo ">> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
# lsof: list open file의 약자로 열린 파일 목록을 알려준다.
# lsof -i TCP:8000-8082 -P 이렇게 하면 포트 목록이 나타난다.
# -i: 특정 프로토콜과 포트 정보를 출력
# -t: PID 번호만 출력한다.
# lsof -i  tcp:8081 | awk '{ print $2 }' | tail -n 1 / -t를 사용하지 않으면 이런식으로 PID를 가져와야 할 것이다.
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]; then
  echo ">>>> 현재 구동 중인 애플리케이션 없으므로 종료를 하지 않습니다. <<<<"
else
  echo ">>>> 이전에 구동중인 애플리케이션이 있으므로 종료합니다. <<<<"
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi
