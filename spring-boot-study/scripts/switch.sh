#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
  #statements
  IDLE_PORT=$(find_idle_port)

  echo ">>>>> 전환할 Port: $IDLE_PORT"
  echo ">>>>> Port 전환 "
  # tee명령어오 앞의 내용을 저장하고 출력한다.
  echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc


  echo ">>>>> 엔진엑스 Reload"
  # restart는 끊기는현상이 있으나 reload는 끊김 없이 다시 불러온다. 외부 설정 파일인 service-rul만 바꿨기 때문에 reload가 가능하다.
  sudo service nginx reload
}
