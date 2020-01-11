#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)  # 현재 stop.sh가 속해있는 경로를 찾는다.
source ${ABSDIR}/profile.sh # 그 경로에서 profile.sh를 읽는다 (import 같은 것)

IDLE_PORT=$(find_idle_port)

echo ">> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]; then
  echo "> 현재 구동 죽인 애플리케이션 없으므로 종료를 하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi
