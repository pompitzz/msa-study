#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/msa-study # 기본 저장소 위치를 지정
PROJECT_NAME=spring-boot-study # 프로젝트 네임 지정

echo ">>>>> Build File 복사 <<<<<"

echo ">>>>> cp $REPOSITORY/zip/*.jar $REPOSITORY"

cp $REPOSITORY/zip/*.jar $REPOSITORY/ # S3에서 가져온 jar 파일을 기본 저장소 위치에 복사

echo ">>>>> 새 애플리케이션 배포 <<<<<"
# -t: 최종 수정 시간을 기준으로 출력
# -r: 역순으로 출력
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)  # jar파일중 가장 최근에 복사된 파일을 저장

echo ">>>>> JAR NAME: $JAR_NAME"

echo ">>>>> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo ">>>>> $JAR_NAME 실행 <<<<<"
IDLE_PROFILE=$(find_idle_profile) # profile.sh에서 사용할 profile을 가져온다.

echo ">>>>> $JAR_NAME 을 profile=$IDLE_PROFILE 로 실행합니다."
nohup java -jar \
-Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/real-application.yml,/home/ec2-user/app/tokenInformation.yml \
-Dspring.profiles.active=$IDLE_PROFILE \
$JAR_NAME >$REPOSITORY/nohup.out 2>&1 &
