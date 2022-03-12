#/bin/sh

CODEGEN_JAR=~/wutsi-codegen/wutsi-codegen.jar

API_NAME=wutsi-tracking
API_URL=https://raw.githubusercontent.com/wutsi/wutsi-openapi/master/src/openapi/analytics/v1/tracking_api.yaml
GITHUB_USER=wutsi

echo "Generating code from ${API_URL}"
java -jar ${CODEGEN_JAR} server \
    -in ${API_URL} \
    -out . \
    -name ${API_NAME} \
    -package com.wutsi.analytics.tracking \
    -jdk 11 \
    -github_user ${GITHUB_USER} \
    -github_project ${API_NAME}-server \
    -heroku ${API_NAME}-server \
    -service_logger \
    -service_mqueue

if [ $? -eq 0 ]
then
    echo Code Cleanup...
    mvn antrun:run@ktlint-format
    mvn antrun:run@ktlint-format

else
    echo "FAILED"
    exit -1
fi
