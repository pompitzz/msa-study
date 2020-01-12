#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/msa-study
PROJECT_NAME=spring-boot-study

echo ">>>>> Build File 복사 <<<<<"

echo ">>>>> cp $REPOSITORY/zip/*.jar $REPOSITORY"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo ">>>>> 새 애플리케이션 배포 <<<<<"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo ">>>>> JAR NAME: $JAR_NAME"

echo ">>>>> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo ">>>>> $JAR_NAME 실행 <<<<<"
IDLE_PROFILE=$(find_idle_profile)

echo ">>>>> $JAR_NAME 을 profile=$IDLE_PROFILE 로 실행합니다."
nohup java -jar \
-Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/real-application.yml,/home/ec2-user/app/tokenInformation.yml \
-Dspring.profiles.active=$IDLE_PROFILE \
$JAR_NAME >$REPOSITORY/nohup.out 2>&1 &
